package com.udb.m2.gl.service;

import com.udb.m2.gl.model.Customer;

public interface ICustomer {
    Customer saveCustomer(Customer customer);
    Customer findCustomerById(long id);
    Customer findCustomerByTelephone(String telephone);
}
