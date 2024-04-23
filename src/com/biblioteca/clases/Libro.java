package com.biblioteca.clases;

public class Libro extends Documento {

	private int anoPubli;
	private final static int MAX_DIAS_SOCIO = 30;
	private final static int MAX_DIAS_NOSOCIO = 15;

	public Libro(String codigoAlfaNum, String titulo, int ano) {
		super(codigoAlfaNum, titulo);
		this.anoPubli = ano;
	}

	public int getAnoPubli() {
		return anoPubli;
	}

	public void setAnoPubli(int anoPubli) {
		this.anoPubli = anoPubli;
	}

	public static int getMaxDiasSocio() {
		return MAX_DIAS_SOCIO;
	}

	public static int getMaxDiasNosocio() {
		return MAX_DIAS_NOSOCIO;
	}

	@Override
	public String toString() {
		return "| Titulo " + this.getTitulo() + ", Año: " + this.getAnoPubli() + " Codigo: " + this.getCodigoAlfaNum()
				+ " |";
	}

}
