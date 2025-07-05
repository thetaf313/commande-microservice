package com.udb.m2.gl.service;

import com.udb.m2.gl.model.Customer;
import com.udb.m2.gl.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService implements ICustomer {

//    @Autowired
//    private CustomerRepository customerRepository;
    private final CustomerRepository customerRepository;  // recommended

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Customer saveCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public Customer findCustomerById(long id) {
        return customerRepository.findById(id).orElse(null);
    }

    @Override
    public Customer findCustomerByTelephone(String telephone) {
        return customerRepository.findByTelephone(telephone);
    }
}
