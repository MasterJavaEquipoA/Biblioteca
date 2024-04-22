package com.biblioteca.clases;

public class Libro extends Documento {
private int anhoPublicacion;
private static final int MAX_DURACION_OCA=15;
private static final int MAX_DURACION_SOCIO=30;
public Libro(String codigoAlfaNum, String titulo, int anhoPublicacion) {
	super(codigoAlfaNum, titulo);
	this.anhoPublicacion = anhoPublicacion;
}

}
