package com.biblioteca.clases;

import java.sql.Statement;

public class Biblioteca {

	public Documento seleccionarDocumento(String codigo) {
		String query = "SELECT * FROM documentos WHERE codigoAlfaNum = " + codigo;
		// Falta codigo
		// Documento doc = new Libro();
		// Documento doc = new Revista();
		return new Documento(codigo, query);
	}

	public void prestarDocumento(String usuario, Documento documento) {
		Statement stmt ;
		if(validarUsuario(usuario)) {
			
		}
	}

	private boolean validarUsuario(String usuario) {
		String query = "SELECT * FROM usuarios WHERE dni = "+usuario;
		//
		return false;
	}

	public void devolverDocumento(String usuario, Documento documento) {

	}

	public Documento buscarDocumento(String titulo) {
		String query = "SELECT * FROM documentos WHERE titulo LIKE %" + titulo + "%";
		return new Documento(query, titulo);
	}
//biblioteca
	public String generarInformesPrestados() {
		String query = "SELECT * FROM prestamos";
		return "";
	}
}
