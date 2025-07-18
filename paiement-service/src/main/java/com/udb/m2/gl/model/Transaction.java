package com.udb.m2.gl.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "serial")
    private long id;
    private LocalDate date;
    private BigDecimal montant;
    private String type;
    private long demandeId;
    @ManyToOne
    @JoinColumn(name = "compte_id")
    private Compte compte;
}
