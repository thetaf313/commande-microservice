package com.udb.m2.gl.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerResponse {
    private long id;
    private String nom;
    private String prenom;
    private String dateNaissance;
    private String telephone;
}
