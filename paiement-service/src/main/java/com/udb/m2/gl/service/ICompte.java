package com.udb.m2.gl.service;


import com.udb.m2.gl.model.Compte;

public interface ICompte {
    Compte save(Compte compte);
    Compte findByClientId(long clientId);
}
