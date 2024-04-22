package com.biblioteca.clases;

public class Libro extends Documento{

	private int anoPubli;
	
	public Libro(String codigoAlfaNum, String titulo) {
		super(codigoAlfaNum, titulo);
		
	}

	public int getAnoPubli() {
		return anoPubli;
	}

	public void setAnoPubli(int anoPubli) {
		this.anoPubli = anoPubli;
	}

	
}
