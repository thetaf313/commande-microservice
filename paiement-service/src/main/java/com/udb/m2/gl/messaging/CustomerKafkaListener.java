package com.udb.m2.gl.messaging;


import com.udb.m2.gl.dto.CompteCreateRequest;
import com.udb.m2.gl.kafka.avro.model.CustomerCreateResponseAvroModel;
import com.udb.m2.gl.kafka.avro.model.CustomerStatut;
import com.udb.m2.gl.model.Compte;
import com.udb.m2.gl.model.Tracking;
import com.udb.m2.gl.model.Transaction;
import com.udb.m2.gl.service.CompteService;
import com.udb.m2.gl.service.ITracking;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Component
public class CustomerKafkaListener implements KafkaConsumer<CustomerCreateResponseAvroModel> {

    private CompteCreateRequest compteCreateRequest;
    private final CompteService compteService;
    private UUID trackingId;
    private final ITracking trackingService;
    //private CustomerCreateResponseAvroModel customerCreateResponseAvroModel;


    public CustomerKafkaListener(CompteService compteService, ITracking trackingService) {
        this.compteService = compteService;
        this.trackingService = trackingService;
    }

    public void initCompteCreateRequest(CompteCreateRequest compteCreateRequest){
        this.compteCreateRequest = compteCreateRequest;
    }


    /*
    public CustomerCreateResponseAvroModel getStatut(){
        return customerCreateResponseAvroModel;
    }
    */
    @Override
    @KafkaListener(id = "${kafka-consumer-config.customer-group-id}", topics = "${topics.customer-create-topic-response-name}")
    public void receive(@Payload CustomerCreateResponseAvroModel message,
                        @Header(KafkaHeaders.RECEIVED_KEY) String key,
                        @Header(KafkaHeaders.RECEIVED_PARTITION) Integer partition,
                        @Header(KafkaHeaders.OFFSET) Long offset) {
        getData(message);
    }

    @Transactional
    public void getData(CustomerCreateResponseAvroModel customerCreateResponseAvroModel){
        Tracking tracking = trackingService.findById(trackingId);
        if(customerCreateResponseAvroModel.getCustomerStatut().equals(CustomerStatut.CREATED)){
            // créate compte
            Compte compte = new Compte();
            compte.setClientId(Long.parseLong(customerCreateResponseAvroModel.getClientId()));
            compte.setNumeroCompte("C-"+String.format("%08d", Integer.parseInt(customerCreateResponseAvroModel.getClientId())));
            compte.setSolde(compteCreateRequest.getMontant());
            Transaction transaction = new Transaction();
            transaction.setCompte(compte);
            transaction.setDate(LocalDate.now());
            transaction.setType("DEPOT");
            transaction.setMontant(compteCreateRequest.getMontant());
            transaction.setDemandeId(0);
            compte.setTransactions(List.of(transaction));
            compteService.save(compte);
            tracking.setMessage("Le compte a ete cree avec succes !");
            tracking.setStatut("COMPLETED");
            tracking.setClientId(Long.parseLong(customerCreateResponseAvroModel.getClientId()));
            tracking.setCompte(compte);
        }
        else {
            // update tracking
            tracking.setMessage("La creation du compte a échoué car un compte avec ce telephone existe déjà!");
            tracking.setStatut("FAILED");
            tracking.setClientId(Long.parseLong(customerCreateResponseAvroModel.getClientId()));
        }
        trackingService.save(tracking);

    }

    public void setInitTrackingId(UUID id) {
        this.trackingId = id;
    }
}
