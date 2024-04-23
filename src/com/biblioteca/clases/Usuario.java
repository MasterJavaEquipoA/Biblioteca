package com.biblioteca.clases;

/**
 * 
 * @author Admin
 *
 */
public abstract class Usuario {
	private String dni;
	private String nombre;

	protected Usuario(String dni, String nombre) {
		this.dni = dni;
		this.nombre = nombre;
	}

	protected Usuario() {
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

}
