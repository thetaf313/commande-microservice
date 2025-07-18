package com.udb.m2.gl.helper;

import com.udb.m2.gl.common.service.event.KafkaEvent;
import com.udb.m2.gl.dto.*;
import com.udb.m2.gl.exception.CompteServiceException;
import com.udb.m2.gl.kafka.avro.model.CustomerCreateRequestAvroModel;
import com.udb.m2.gl.mapper.CompteServiceMapper;
import com.udb.m2.gl.messaging.CustomerKafkaListener;
import com.udb.m2.gl.messaging.KafkaService;
import com.udb.m2.gl.model.Compte;
import com.udb.m2.gl.model.Tracking;
import com.udb.m2.gl.model.Transaction;
import com.udb.m2.gl.service.ICompte;
import com.udb.m2.gl.service.ITracking;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Component
public class CompteHelper {
    private final KafkaService kafkaService;
    private final ICompte iCompte;
    private final ModelMapper modelMapper;
    private final CompteServiceMapper compteServiceMapper;
    private final ITracking trackingService;
    private final CustomerKafkaListener customerKafkaListener;

    public CompteHelper(KafkaService kafkaService, ICompte iCompte, ModelMapper modelMapper, CompteServiceMapper compteServiceMapper, ITracking trackingService, CustomerKafkaListener customerKafkaListener) {
        this.kafkaService = kafkaService;
        this.iCompte = iCompte;
        this.modelMapper = modelMapper;
        this.compteServiceMapper = compteServiceMapper;
        this.trackingService = trackingService;
        this.customerKafkaListener = customerKafkaListener;
    }

    public CompteCreateResponse createCompte(CompteCreateRequest compteCreateRequest){
        if(compteCreateRequest.getMontant().compareTo(BigDecimal.valueOf(10000)) < 0){
            throw new CompteServiceException("montant initial ["+compteCreateRequest.getMontant()+"] doit être >= 10000");
        }
        Tracking tracking = new Tracking();
        tracking.setId(UUID.randomUUID());
        tracking.setMessage("Creation du compte initiee");
        tracking.setStatut("PENDING");
        trackingService.save(tracking);
        customerKafkaListener.setInitTrackingId(tracking.getId());

        CustomerCreateRequestAvroModel customerCreateRequestAvroModel =
                compteServiceMapper.CustomerCreateRequestTocustomerCreateResponseAvroModel(compteCreateRequest
                        .getCustomerCreateRequest());
        KafkaEvent<CustomerCreateRequestAvroModel> createCustumerEvent = new KafkaEvent<>(customerCreateRequestAvroModel);
        kafkaService.createCustumer(createCustumerEvent);
        return null;
    }

    public CompteCreateResponse getCompteByClientId(long clientId){
        Compte compte = iCompte.findByClientId(clientId);
        if(compte == null){
            throw new CompteServiceException("Compte avec le numéro client "+clientId+" introuvable !");
        }
        return modelMapper.map(compte, CompteCreateResponse.class);
    }

    public DepotCreateResponse depot(DepotCreateRequest depotCreateRequest){
        BigDecimal montant = BigDecimal.ONE;
        try {
            montant = BigDecimal.valueOf(Long.parseLong(depotCreateRequest.getMontant()));
        } catch (NumberFormatException e) {
            throw new CompteServiceException("Montant dépot invalide !");
        }
        if(montant.compareTo(BigDecimal.valueOf(5000)) < 0){
            throw new CompteServiceException("montant dépot ["+montant+"] doit être >= 5000");
        }
        Compte compte = iCompte.findByClientId(Long.parseLong(depotCreateRequest.getClientId()));
        if(compte == null){
            throw new CompteServiceException("Compte client ["+depotCreateRequest.getClientId()+"] introuvable!");
        }
        Transaction transaction = new Transaction();
        transaction.setCompte(compte);
        transaction.setDate(LocalDate.now());
        transaction.setType("DEPOT");
        transaction.setMontant(montant);
        transaction.setDemandeId(0);
        compte.getTransactions().add(transaction);
        compte.setSolde(compte.getSolde().add(montant));
        transaction.setCompte(compte);
        iCompte.save(compte);
        return compteServiceMapper.TransactionToDepotResponse(transaction);
    }


    public TrackingResponse trackingCompte(UUID trackingId) {
        Tracking tracking = trackingService.findById(trackingId);
        if (tracking == null) {
            throw new CompteServiceException("Tracking avec l'ID " + trackingId + " introuvable !");
        }
        TrackingResponse trackingResponse = compteServiceMapper.trackingToTrackingResponse(tracking);
        if (tracking.getCompte() != null) {
            trackingResponse.setCompteCreateResponse(compteServiceMapper.compteToCompteCreateResponse(tracking.getCompte()));
        }
        return trackingResponse;
    }

}
