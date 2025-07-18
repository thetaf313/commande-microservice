package com.udb.m2.gl.service;


import com.udb.m2.gl.model.Transaction;
import com.udb.m2.gl.repository.TranscationRepository;
import org.springframework.stereotype.Service;

@Service
public class TransactionService implements ITransaction{
    private final TranscationRepository transcationRepository;

    public TransactionService(TranscationRepository transcationRepository) {
        this.transcationRepository = transcationRepository;
    }

    @Override
    public Transaction save(Transaction transaction) {
        return transcationRepository.save(transaction);
    }
}
