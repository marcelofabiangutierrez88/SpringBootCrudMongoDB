package com.marcelo.spring.data.mongodb.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collation = "cuentas")
public class Cuenta {
	@Id
	private String id;
	private String nroCuenta;
	private Usuario usuario;

	public Cuenta() {}
	
	public Cuenta(Usuario usuario, String nroCuenta) {
		this.usuario = usuario;
		this.nroCuenta = nroCuenta;
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

	public void setNroCuenta(String nroCuenta) {
		this.nroCuenta = nroCuenta;
	}
}
