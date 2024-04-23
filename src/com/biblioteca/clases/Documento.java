package com.biblioteca.clases;

public class Documento {

	private String codigoAlfaNum;
	private String titulo;
	
	public Documento() {
		
	}
	
	public Documento(String codigoAlfaNum, String titulo) {
		super();
		this.codigoAlfaNum = codigoAlfaNum;
		this.titulo = titulo;
	}
	
	public String getCodigoAlfaNum() {
		return codigoAlfaNum;
	}
	
	public String getTitulo() {
		return titulo;
	}
	
	@Override
	public String toString() {
		return "codigoAlfaNum=" + codigoAlfaNum + ", titulo=" + titulo;
	}
}
