package com.biblioteca.clases;

public class Documento {

	private String codigoAlfaNum;
	private String titulo;
	private boolean prestado;

	public Documento(String codigoAlfaNum, String titulo) {
		super();
		this.codigoAlfaNum = codigoAlfaNum;
		this.titulo = titulo;
	}

	public Documento() {
	}

	public String getCodigoAlfaNum() {
		return codigoAlfaNum;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setCodigoAlfaNum(String codigoAlfaNum) {
		this.codigoAlfaNum = codigoAlfaNum;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public boolean isPrestado() {
		return prestado;
	}

	public void setPrestado(boolean prestado) {
		this.prestado = prestado;
	}

	@Override
	public String toString() {
		return "codigoAlfaNum=" + codigoAlfaNum + ", titulo=" + titulo;
	}

}
