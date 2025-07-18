package com.udb.m2.gl.controller;


import com.udb.m2.gl.dto.*;
import com.udb.m2.gl.helper.CompteHelper;
import com.udb.m2.gl.messaging.CustomerKafkaListener;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

//@CrossOrigin
@RestController
@RequestMapping("/api/compte")
public class CompteController {

    private final CompteHelper compteHelper;

    private final CustomerKafkaListener customerResponseKafkaListener;
    private final ModelMapper modelMapper;

    public CompteController(CompteHelper compteHelper, CustomerKafkaListener customerResponseKafkaListener, ModelMapper modelMapper) {
        this.compteHelper = compteHelper;
        this.customerResponseKafkaListener = customerResponseKafkaListener;
        this.modelMapper = modelMapper;
    }

    @PostMapping
    public void createCompte(@RequestBody CompteCreateRequest compteCreateRequest) throws InterruptedException {
        customerResponseKafkaListener.initCompteCreateRequest(compteCreateRequest);
        compteHelper.createCompte(compteCreateRequest);
    }

    @PostMapping("/tracking/{trackingId}")
    public TrackingResponse trackingCreateCompte(@PathVariable UUID trackingId) {
        return compteHelper.trackingCompte(trackingId);
    }

    @PostMapping("/depot")
    public DepotCreateResponse createDepot(@RequestBody DepotCreateRequest depotCreateRequest) throws InterruptedException {
        return compteHelper.depot(depotCreateRequest);
    }

    @GetMapping("/{clientId}")
    public CompteCreateResponse getCompte(@PathVariable long clientId){
        return compteHelper.getCompteByClientId(clientId);
    }
}
