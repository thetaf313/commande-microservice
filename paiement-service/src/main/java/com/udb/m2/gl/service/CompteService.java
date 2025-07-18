package com.udb.m2.gl.service;

import com.udb.m2.gl.model.Compte;
import com.udb.m2.gl.repository.CompteRepository;
import org.springframework.stereotype.Service;

@Service
public class CompteService implements ICompte{
    private final CompteRepository compteRepository;

    public CompteService(CompteRepository compteRepository) {
        this.compteRepository = compteRepository;
    }

    @Override
    public Compte save(Compte compte) {
        return compteRepository.save(compte);
    }

    @Override
    public Compte findByClientId(long clientId) {
        return compteRepository.findByClientId(clientId);
    }
}
