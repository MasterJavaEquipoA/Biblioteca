package com.biblioteca.clases;
/**
 * 
 * @author Admin
 *
 */
public class Ocasional extends Usuario{
	
	public final static int MAX_PRESTAMOS = 2;
	
	
	public Ocasional(String dni, String nombre) {
		super(dni, nombre, false);
	}


	public Ocasional() {
		super(false);
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
