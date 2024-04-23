package com.biblioteca.clases;

public class Revista extends Documento{

	
	private final static int MAX_DURACION_SOCIO = 15;
	private final static int MAX_DURACION_OCAS = 5;
	
	public Revista() {
	}
	
	public Revista(String codigoAlfaNum, String titulo) {
		super(codigoAlfaNum, titulo);
		
	}

	public static int getMaxDuracionSocio() {
		return MAX_DURACION_SOCIO;
	}

	public static int getMaxDuracionOcas() {
		return MAX_DURACION_OCAS;
	}

	@Override
	public String toString() {
		return "Revista [getCodigoAlfaNum()=" + getCodigoAlfaNum() + ", getTitulo()=" + getTitulo() + ", toString()="
				+ super.toString() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + "]";
	}
}
