package com.biblioteca.clases;

public class Usuario {
	private String dni;
	private String nombre;
	private boolean esSocio;

	public Usuario(String dni, String nombre, boolean esSocio) {
		this.dni = dni;
		this.nombre = nombre;
		this.esSocio = esSocio;
	}

	public Usuario(boolean b) {
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		dni = dni;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public boolean isEsSocio() {
		return esSocio;
	}

	public void setEsSocio(boolean esSocio) {
		this.esSocio = esSocio;
	}

}
