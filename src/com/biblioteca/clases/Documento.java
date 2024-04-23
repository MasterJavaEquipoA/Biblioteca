package com.biblioteca.clases;

import java.util.Scanner;

public class Documento {

	private String codigoAlfaNum;
	private String titulo;
	private boolean prestado;

	public Documento(String codigoAlfaNum, String titulo) {
		super();
		this.codigoAlfaNum = codigoAlfaNum;
		this.titulo = titulo;
		this.prestado = false;
	}

	public String getCodigoAlfaNum() {
		return codigoAlfaNum;
	}

	public String getTitulo() {
		return titulo;
	}

	public boolean isPrestado() {
		return prestado;
	}

	public void setPrestado(boolean prestado) {
		this.prestado = prestado;
	}

	public void setCodigoAlfaNum(String codigoAlfaNum) {
		this.codigoAlfaNum = codigoAlfaNum;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	@Override
	public String toString() {
		return "| Titulo " + this.getTitulo() + " Codigo: " + this.getCodigoAlfaNum() + "Prestado : " + this.prestado
				+ " |";
	}

}
