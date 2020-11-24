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

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@Api(tags = "API fintech Make Bank Cuentas", value = "Integracion de conocimientos con SpringBoot")
public class CuentaController {
	
	private static final Logger logger = Logger.getLogger(UsuarioController.class) ;
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	CuentaRepository cuentaRepository;
	
	public CuentaController(BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}
	
	@GetMapping(value = "/cuentas",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation("Obtener todas las cuentas")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Se obtienen todas las cuentas registradas", response = ResponseEntity.class)})
	public ResponseEntity<?> getAllCuentas(@RequestBody(required = false)String nroCuenta){
			List<Cuenta> cuentas = new ArrayList<Cuenta>();
			cuentaRepository.findAll().forEach(cuentas::add);
			return new ResponseEntity<>(cuentas, HttpStatus.OK);
	}
	
	@GetMapping(value = "/cuentas/{id}")
	@ApiOperation("Obtener una cuenta en particular por id")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "se obtiene una cuenta en particular por id", response = ResponseEntity.class)})
	public ResponseEntity<?> getOneCuenta (@PathVariable("id") String id){
		Optional<Cuenta> cuentaData = cuentaRepository.findById(id);
			if(cuentaData.isPresent()) {
				Cuenta _cuenta = cuentaData.get();
				return ResponseEntity.status(HttpStatus.OK).body(_cuenta);
			} else {
				ErrorResponse error = new ErrorResponse("400", "El id enviado: " + id + " no existe en la base de datos");
				logger.error("Error en get cuentas por id: "+ error);
				return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
			}
	}
	
	@PostMapping(value = "/cuentas",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation("Crear una cuenta")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Se crea una cuenta", response = ResponseEntity.class)})
	public ResponseEntity<?> createCuenta(@RequestBody Cuenta cuenta){
			Cuenta _cuenta = cuentaRepository.save(new Cuenta(cuenta.getUsuario(),
					cuenta.setNroCuenta(buildNroCuenta()),
					cuenta.setCbu(buildCbu(buildNroCuenta())),
					cuenta.setAlias(buildAlias(cuenta.getUsuario().getEmail())))
					);
			return new ResponseEntity<>(_cuenta, HttpStatus.CREATED);
	}
	
	@DeleteMapping(value = "/cuentas/{id}")
	@ApiOperation("Elimina una cuenta")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Se elimina una cuenta particular por id", response = ResponseEntity.class)})
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
	@ApiOperation("Eliminar todas las cuentas")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Se eliminan todas las cuentas registradas", response = ResponseEntity.class)
	})
	public ResponseEntity<?> deleteAllCuentas(){
			cuentaRepository.deleteAll();
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body("OK");
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
