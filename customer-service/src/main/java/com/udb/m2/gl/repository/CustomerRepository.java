package com.udb.m2.gl.repository;

import com.udb.m2.gl.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Customer findByTelephone(String telephone);
}
