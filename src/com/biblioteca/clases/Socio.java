package com.biblioteca.clases;
/**
 * 
 * @author Admin
 *
 */
public class Socio extends Usuario {

	public final static int MAX_PRESTAMOS = 20;

	public Socio(String dni, String nombre) {
		super(dni, nombre, true);

	}

	public Socio() {
		super(true);
	}

	@Override
	public String toString() {
		return "Socio [getDni()=" + getDni() + ", getNombre()=" + getNombre() + ", getClass()="
				+ getClass().getSimpleName() + ", toString()=" + super.toString() + "]";
	}

}
