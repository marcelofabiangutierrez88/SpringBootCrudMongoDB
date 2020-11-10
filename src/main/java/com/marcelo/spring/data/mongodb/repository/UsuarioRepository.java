package com.marcelo.spring.data.mongodb.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.marcelo.spring.data.mongodb.model.Usuario;

public interface UsuarioRepository extends MongoRepository<Usuario, String>{

}
