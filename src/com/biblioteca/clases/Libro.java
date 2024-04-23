package com.biblioteca.clases;

public class Libro extends Documento {

	public final static int MAX_DURACION_SOCIO = 30;
	public final static int MAX_DURACION_OCAS = 15;
	private int anoPubli;

	public Libro() {
		super();
	}

	public Libro(String codigoAlfaNum, String titulo, int anoPubli) {
		super(codigoAlfaNum, titulo);
		this.anoPubli = anoPubli;
	}

	public int getAnoPubli() {
		return anoPubli;
	}

	public void setAnoPubli(int anoPubli) {
		this.anoPubli = anoPubli;
	}

	@Override
	public String toString() {
		return "Libro: " + super.toString() + " anoPubli=" + anoPubli + ".";
	}

}
