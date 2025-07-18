package com.udb.m2.gl.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Tracking {

    @Id
    private UUID id;
    @Column(length = 50)
    private String message;
    @Column(length = 30)
    private String statut;
    private long clientId;
    @OneToOne(mappedBy = "tracking")
    private Compte compte;
}
