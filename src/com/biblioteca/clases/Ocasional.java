package com.biblioteca.clases;

public class Ocasional extends Usuario{
	
	private final static int MAX_PRESTAMOS = 2;
	
	
	public Ocasional(String dni, String nombre, boolean esSocio) {
		super(dni, nombre, esSocio);
	}


	public static int getMaxPrestamos() {
		return MAX_PRESTAMOS;
	}


	@Override
	public String toString() {
		return "Ocasional [getDni()=" + getDni() + ", getNombre()=" + getNombre() + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}
}
