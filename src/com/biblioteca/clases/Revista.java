package com.biblioteca.clases;

public class Revista extends Documento {

	private final static int MAX_DIAS_SOCIO = 15;
	private final static int MAX_DIAS_NOSOCIO = 5;

	public Revista(String codigoAlfaNum, String titulo) {
		super(codigoAlfaNum, titulo);

	}

	public static int getMaxDiasSocio() {
		return MAX_DIAS_SOCIO;
	}

	public static int getMaxDiasNosocio() {
		return MAX_DIAS_NOSOCIO;
	}

	@Override
	public String toString() {
		return "| Titulo " + this.getTitulo() + " Codigo: " + this.getCodigoAlfaNum() + " |";
	}
}
