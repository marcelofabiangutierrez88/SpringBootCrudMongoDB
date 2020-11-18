package com.marcelo.spring.data.mongodb.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.marcelo.spring.data.mongodb.model.Cuenta;
import com.marcelo.spring.data.mongodb.model.ErrorResponse;
import com.marcelo.spring.data.mongodb.repository.CuentaRepository;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class CuentaController {
	
	private static final Logger logger = Logger.getLogger(UsuarioController.class) ;
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	CuentaRepository cuentaRepository;
	
	public CuentaController(BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}
	
	@GetMapping(value = "/cuentas",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getAllCuentas(@RequestBody(required = false)String nroCuenta) throws Exception{
		try {
			List<Cuenta> cuentas = new ArrayList<Cuenta>();
			cuentaRepository.findAll().forEach(cuentas::add);
			return new ResponseEntity<>(cuentas, HttpStatus.OK);
		} catch (Exception e) {
			logger.error("Error en get cuentas: "+ e.getMessage());
			ErrorResponse error = new ErrorResponse("Error",e.getMessage());
			return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping(value = "/cuentas/{id}",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getOneCuenta (@PathVariable("id") String id){
		Optional<Cuenta> cuentaData = cuentaRepository.findById(id);
		
		if(cuentaData.isPresent()) {
			Cuenta _cuenta = cuentaData.get();
			return new ResponseEntity<>(_cuenta, HttpStatus.OK);
		} else {
			ErrorResponse error = new ErrorResponse("400", "Consulta mal realizada");
			logger.error("Error en get cuentas por id: "+ error);
			return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
		}
	}
	
	@PostMapping(value = "/cuentas",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> createCuenta(@RequestBody Cuenta cuenta) throws Exception{
		try {
			Cuenta _cuenta = cuentaRepository.save(new Cuenta(cuenta.getUsuario(),
					cuenta.setNroCuenta(buildNroCuenta()),
					cuenta.setCbu(buildCbu(buildNroCuenta())),
					cuenta.setAlias(buildAlias(cuenta.getUsuario().getEmail())))
					);
			return new ResponseEntity<>(_cuenta, HttpStatus.CREATED);
		} catch (Exception e) {
			logger.error("Error en post cuenta: "+ e.getMessage());
			ErrorResponse error = new ErrorResponse("400","No se envio un request valido");
			return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
		}
	}
	
	@DeleteMapping(value = "/cuentas/{id}")
	public ResponseEntity<?> deleteCuentaById (@PathVariable("id") String id){
		Optional<Cuenta> cuentaData = cuentaRepository.findById(id);
		if(cuentaData.isPresent()) {
			cuentaRepository.deleteById(id);
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body("OK");
		} else {
			ErrorResponse error = new ErrorResponse("400", "El id enviado: " + id + " no existe en la base de datos");
			logger.error("Error en delete cuenta por id: "+ error);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
		}
	}
	
	@DeleteMapping(value = "/cuentas",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> deleteAllCuentas() throws Exception{
		try {
			cuentaRepository.deleteAll();
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body("OK");
		} catch (Exception e) {
			logger.error("Error en delete cuentas: "+ e.getMessage());
			ErrorResponse error = new ErrorResponse(e.getMessage(), e.getLocalizedMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
		}
	}
	
	private String buildCbu (String param) throws IndexOutOfBoundsException {
		String[] splitted = param.split("-");
		String split = splitted[0]+splitted[4];
		return split;
	}
	
	private String buildNroCuenta () {
		String param = UUID.randomUUID().toString();
		return param ;
	}
	
	private String buildAlias(String param) throws IndexOutOfBoundsException{
		Random r = new Random();
		int c = r.nextInt(26) + (byte)'a'  ;
		String[]splitted = param.split("@");
		String split = (char)c +"-"+splitted[0]+"-"+"alias";
		String splitToUpper = split.replace(split, split.toUpperCase());
		return splitToUpper;
	}
	
}
