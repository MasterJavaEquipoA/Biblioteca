package com.biblioteca.clases;

public class Libro extends Documento{

	private int anoPubli;
	
	public Libro() {

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
		return "Libro [anoPubli=" + anoPubli + ", getCodigoAlfaNum()=" + getCodigoAlfaNum() + ", getTitulo()="
				+ getTitulo() + ", toString()=" + super.toString() + ", getClass()=" + getClass() + ", hashCode()="
				+ hashCode() + "]";
	}
}
