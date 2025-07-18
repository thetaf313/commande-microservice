package com.udb.m2.gl.repository;

import com.udb.m2.gl.model.Compte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompteRepository extends JpaRepository<Compte, Long> {
    Compte findByClientId(long clientId);
}
