package com.biblioteca.clases;

public class UsuarioHabitual extends Usuario {

	private final static int MAX_PRESTAMOS = 2;

	public UsuarioHabitual(String dni, String nombre, boolean esSocio) {
		super(dni, nombre, esSocio);

	}

	@Override
	public String toString() {
		return "UsuarioHabitual [getDni()=" + getDni() + ", getNombre()=" + getNombre() + ", getClass()="
				+ getClass().getSimpleName() + ", toString()=" + super.toString() + "]";
	}

}
