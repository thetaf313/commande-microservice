package com.udb.m2.gl.mapper;

import com.udb.m2.gl.kafka.avro.model.CustomerCreateRequestAvroModel;
import com.udb.m2.gl.model.Customer;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {

    public Customer customerCreateRequestAvromModelToCustomer(CustomerCreateRequestAvroModel customerCreateRequestAvroModel){
        return  Customer.builder()
                .id(0)
                .telephone(customerCreateRequestAvroModel.getTel())
                .nom(customerCreateRequestAvroModel.getNom())
                .prenom(customerCreateRequestAvroModel.getPrenom())
                .dateNaissance(customerCreateRequestAvroModel.getDateNaissance())
                .build();
    }
}
