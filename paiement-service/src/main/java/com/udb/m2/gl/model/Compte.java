package com.udb.m2.gl.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Compte {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "serial")
    private long id;
    @Column(length = 25)
    private String numeroCompte;
    private BigDecimal solde;
    private long clientId;
    @OneToMany(mappedBy = "compte", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, fetch = FetchType.EAGER)
    private List<Transaction> transactions;
    @OneToOne
    @JoinColumn(name = "tracking_id")
    private Tracking tracking;
}
