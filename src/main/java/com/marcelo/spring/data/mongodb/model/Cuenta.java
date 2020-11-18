package com.marcelo.spring.data.mongodb.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * @author marcelo
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Document(collection = "cuentas")
public class Cuenta {
	@Id
	private String id;
	private String nroCuenta;
	private Usuario usuario;
	private String cbu;
	private String alias;

	public Cuenta() {}
	
	public Cuenta(Usuario usuario, String nroCuenta, String cbu, String alias) {
		this.usuario = usuario;
		this.nroCuenta = nroCuenta;
		this.cbu = cbu;
		this.alias = alias;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getNroCuenta() {
		return nroCuenta;
	}

	public String setNroCuenta(String nroCuenta) {
		this.nroCuenta = nroCuenta;
		return nroCuenta;
	}

	public String getCbu() {
		return cbu;
	}

	public String setCbu(String cbu) {
		this.cbu = cbu;
		return cbu;
	}

	public String getAlias() {
		return alias;
	}

	public String setAlias(String alias) {
		this.alias = alias;
		return alias;
	}
	
	

}
