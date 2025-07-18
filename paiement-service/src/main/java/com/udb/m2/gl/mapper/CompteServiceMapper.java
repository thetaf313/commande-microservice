package com.udb.m2.gl.mapper;



import com.udb.m2.gl.dto.CompteCreateResponse;
import com.udb.m2.gl.dto.CustomerCreateRequest;
import com.udb.m2.gl.dto.DepotCreateResponse;
import com.udb.m2.gl.dto.TrackingResponse;
import com.udb.m2.gl.kafka.avro.model.CustomerCreateRequestAvroModel;
import com.udb.m2.gl.kafka.avro.model.PaiementCreateRequestAvroModel;
import com.udb.m2.gl.model.Compte;
import com.udb.m2.gl.model.Tracking;
import com.udb.m2.gl.model.Transaction;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class CompteServiceMapper {

    public CustomerCreateRequestAvroModel CustomerCreateRequestTocustomerCreateResponseAvroModel(CustomerCreateRequest customerCreateRequest){
        return CustomerCreateRequestAvroModel.newBuilder()
                .setNom(customerCreateRequest.getNom())
                .setPrenom(customerCreateRequest.getPrenom())
                .setDateNaissance(customerCreateRequest.getDateNaissance())
                .setTel(customerCreateRequest.getTel())
                .build();
    }
    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

    public DepotCreateResponse TransactionToDepotResponse(Transaction transaction){
        return DepotCreateResponse.builder()
                .date(transaction.getDate())
                .compteNo(transaction.getCompte().getNumeroCompte())
                .type(transaction.getType())
                .clientId(transaction.getCompte().getClientId())
                .montant(transaction.getMontant())
                .build();
    }

    public Transaction PaiementCreateRequestAvroModelToTransaction(PaiementCreateRequestAvroModel paiementCreateRequestAvroModel){
        return Transaction.builder()
                .date(LocalDate.now())
                .type("PAIEMENT")
                .compte(null)
                .montant(paiementCreateRequestAvroModel.getMontant())
                .demandeId(Long.parseLong(paiementCreateRequestAvroModel.getDemandeId()))
                .build();
    }

    public TrackingResponse trackingToTrackingResponse(Tracking tracking) {
        return TrackingResponse.builder()
                .message(tracking.getMessage())
                .statut(tracking.getStatut())
                .build();
    }

    public CompteCreateResponse compteToCompteCreateResponse(Compte compte) {
        return CompteCreateResponse.builder()
                .numeroCompte(compte.getNumeroCompte())
                .clientId(compte.getClientId() + "")
                .solde(compte.getSolde())
                .build();
    }


}
