package com.biblioteca.clases;

/**
 * 
 * @author Admin
 *
 */
public class Ocasional extends Usuario {

	public static final int MAX_PRESTAMOS = 2;

	public Ocasional(String dni, String nombre) {
		super(dni, nombre);
	}

	public Ocasional() {
		super();
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
