package com.marcelo.spring.data.mongodb.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Document(collection = "usuarios")
public class Usuario {
	@Id
	private String id;
	private String nombre;
	private String apellido;
	@Indexed(unique=true)
	private String dni;
	@Indexed(unique=true)
	private String cuit;
	@Indexed(unique=true)
	private String email;
	private String password;
	
	public Usuario() {}
	
	public Usuario(String nombre, String apellido, String dni, String cuit, String email, String password) {
		this.apellido = apellido;
		this.nombre = nombre;
		this.dni = dni;
		this.cuit = cuit;
		this.email = email;
		this.password = password;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getCuit() {
		return cuit;
	}

	public void setCuit(String cuit) {
		this.cuit = cuit;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "Usuario [id=" + id + ", nombre=" + nombre + ", apellido=" + apellido + ", dni=" + dni + ", cuit=" + cuit
				+ ", email=" + email + "]";
	}
}
