package com.marcelo.spring.data.mongodb.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@Api(tags = "API fintech Make Bank Usuarios", value = "Integracion de conocimientos con SpringBoot")
public class UsuarioController {
	
	private static final Logger logger = Logger.getLogger(UsuarioController.class) ;
	private BCryptPasswordEncoder bCryptPasswordEncoder; 
	
	@Autowired
	UsuarioRepository usuarioRepository;
	
	public UsuarioController(BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}
	
	@GetMapping("/usuarios")
	@ApiOperation("Obtener todos los usuarios")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Se obtienen todos los usuarios registrados", response = ResponseEntity.class)
	})
	public ResponseEntity<List<Usuario>> getAllUsuarios(@RequestParam(required = false) String nombre){
			List<Usuario> usuarios = new ArrayList<Usuario>();
			if(usuarioRepository.findAll().isEmpty()) {
				return ResponseEntity.status(HttpStatus.OK).body(usuarios);
			}
			usuarioRepository.findAll().forEach(usuarios::add);
			return new ResponseEntity<>(usuarios, HttpStatus.OK);
	}
	
	@GetMapping("/usuarios/{id}")
	@ApiOperation("Obtener usuario por id")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Se obtiene el usuario por su id", response = ResponseEntity.class)
	})
	public ResponseEntity<Usuario> getOneUsuario(@PathVariable("id") String id){
		Optional<Usuario> usuarioData = usuarioRepository.findById(id);
		
		if (usuarioData.isPresent()) {
			Usuario _usuario = usuarioData.get();
			return new ResponseEntity<>(_usuario, HttpStatus.OK);
		} else {
			logger.error("Error en get usuarios por id: ");
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping(value="/usuarios", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation("crear un usuario")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Crear un usuario", response = ResponseEntity.class)
	})
	public ResponseEntity<?> createUsuario(@RequestBody Usuario usuario){
		if(usuario==null) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
			Usuario _usuario = usuarioRepository.save(new Usuario(usuario.getNombre(),usuario.getApellido(), usuario.getCuit(), 
					usuario.getDni(),usuario.getEmail(), bCryptPasswordEncoder.encode(usuario.getPassword())));
			return new ResponseEntity<>(_usuario, HttpStatus.CREATED);
	}
	
	@PutMapping("/usuarios/{id}")
	@ApiOperation("update usuario filtrandolo por id")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Se obtienen el usuario actualizado", response = ResponseEntity.class)
	})
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
			logger.error("Error en put usuarios: ");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@DeleteMapping("/usuarios/{id}")
	@ApiOperation("elimina un usuario por id")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Se elimina el usuario", response = ResponseEntity.class)
	})
	public ResponseEntity<HttpStatus> deleteUsuario(@PathVariable("id") String id){
			usuarioRepository.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@DeleteMapping("/usuarios")
	@ApiOperation("Eliminar todos los usuarios")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Se eliminan todos los usuarios registrados", response = ResponseEntity.class)
	})
	public ResponseEntity<HttpStatus> deleteAllUsuarios (){
			usuarioRepository.deleteAll();
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
