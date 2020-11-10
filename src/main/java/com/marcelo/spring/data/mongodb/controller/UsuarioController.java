package com.marcelo.spring.data.mongodb.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.marcelo.spring.data.mongodb.model.ErrorResponse;
import com.marcelo.spring.data.mongodb.model.Usuario;
import com.marcelo.spring.data.mongodb.repository.UsuarioRepository;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class UsuarioController {
	
	@Autowired
	UsuarioRepository usuarioRepository;
	
	@GetMapping("/usuarios")
	public ResponseEntity<List<Usuario>> getAllUsuarios(@RequestParam(required = false) String nombre){
		try {
			List<Usuario> usuarios = new ArrayList<Usuario>();
			usuarioRepository.findAll().forEach(usuarios::add);
			return new ResponseEntity<>(usuarios, HttpStatus.OK);			
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/usuarios/{id}")
	public ResponseEntity<Usuario> getOneUsuario(@PathVariable("id") String id){
		Optional<Usuario> usuarioData = usuarioRepository.findById(id);
		
		if (usuarioData.isPresent()) {
			Usuario _usuario = usuarioData.get();
			return new ResponseEntity<>(_usuario, HttpStatus.OK);
		} else {
			ErrorResponse error = new ErrorResponse();
			error.setCodigo("1");
			error.setDescripcion("a");
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("/usuarios")
	public ResponseEntity<Usuario> createUsuario(@RequestBody Usuario usuario){
		if(usuario==null) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
		try {
			Usuario _usuario = usuarioRepository.save(new Usuario(usuario.getNombre(),usuario.getApellido(), usuario.getCuit(), usuario.getDni(),usuario.getEmail()));
			return new ResponseEntity<>(_usuario, HttpStatus.CREATED);			
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping("/usuarios/{id}")
	public ResponseEntity<Usuario>UpdateUsuario(@PathVariable("id") String id, @RequestBody Usuario usuario){
		Optional<Usuario> usuarioData = usuarioRepository.findById(id);
		
		if(usuarioData.isPresent()) {
			Usuario _usuario = usuarioData.get();
			_usuario.setNombre(usuario.getNombre());
			_usuario.setApellido(usuario.getApellido());
			_usuario.setDni(usuario.getDni());
			_usuario.setCuit(usuario.getDni());
			_usuario.setEmail(usuario.getEmail());
			return new ResponseEntity<>(usuarioRepository.save(_usuario), HttpStatus.OK);
		}else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@DeleteMapping("/usuarios/{id}")
	public ResponseEntity<HttpStatus> deleteUsuario(@PathVariable("id") String id){
		try {
			usuarioRepository.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping("/usuarios")
	public ResponseEntity<HttpStatus> deleteAllUsuarios (){
		try {
			usuarioRepository.deleteAll();
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
