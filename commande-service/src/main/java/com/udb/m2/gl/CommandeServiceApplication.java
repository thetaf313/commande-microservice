package com.udb.m2.gl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class CommandeServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(CommandeServiceApplication.class, args);

    }
}