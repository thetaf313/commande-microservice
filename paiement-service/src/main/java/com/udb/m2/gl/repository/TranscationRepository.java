package com.udb.m2.gl.repository;

import com.udb.m2.gl.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TranscationRepository extends JpaRepository<Transaction, Long> {
}
