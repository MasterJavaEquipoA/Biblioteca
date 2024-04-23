package com.biblioteca.clases;

public class Socio extends Usuario {

	private final static int MAX_PRESTAMOS = 20;
	
	public Socio() {
		
	}

	public Socio(String dni, String nombre, boolean esSocio) {
		super(dni, nombre, esSocio);

	}

	@Override
	public String toString() {
		return "Socio [getDni()=" + getDni() + ", getNombre()=" + getNombre() + ", getClass()="
				+ getClass().getSimpleName() + ", toString()=" + super.toString() + "]";
	}

}
