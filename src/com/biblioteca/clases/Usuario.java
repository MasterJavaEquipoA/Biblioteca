package com.biblioteca.clases;

public class Usuario {
	private String dni;
	private String nombre;
	private boolean esSocio;
	private final static int MAX_DOC_SOCIO = 20;
	private final static int MAX_DOC_NOSOCIO = 2;

	public Usuario(String dni, String nombre, boolean esSocio) {
		this.dni = dni;
		this.nombre = nombre;
		this.esSocio = esSocio;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
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

	public static int getMaxDocSocio() {
		return MAX_DOC_SOCIO;
	}

	public static int getMaxDocNosocio() {
		return MAX_DOC_NOSOCIO;
	}

	@Override
	public String toString() {
		return "| Usuario: " + dni + ", nombre: " + nombre + ", Socio: " + esSocio + "|";
	}

}
