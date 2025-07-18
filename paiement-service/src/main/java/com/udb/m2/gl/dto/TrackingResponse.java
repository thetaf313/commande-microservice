package com.udb.m2.gl.dto;

import com.udb.m2.gl.model.Compte;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class TrackingResponse {

    private String message;
    private String statut;
    private long clientId;
    private CompteCreateResponse compteCreateResponse;
}
