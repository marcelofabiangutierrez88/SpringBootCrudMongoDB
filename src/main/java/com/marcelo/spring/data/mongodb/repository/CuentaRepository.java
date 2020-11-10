package com.marcelo.spring.data.mongodb.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.marcelo.spring.data.mongodb.model.Cuenta;

public interface CuentaRepository extends MongoRepository<Cuenta, String> {

}
