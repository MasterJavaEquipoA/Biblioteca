package com.biblioteca.clases;
/**
 * 
 * @author Admin
 *
 */
public class Revista extends Documento {

	public final static int MAX_DURACION_SOCIO = 10;
	public final static int MAX_DURACION_OCAS = 5;

	public Revista(String codigoAlfaNum, String titulo) {
		super(codigoAlfaNum, titulo);

	}

	public Revista() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "Revista: " + super.toString();
	}

}
