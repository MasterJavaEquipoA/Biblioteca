package com.biblioteca.clases;

public class Biblioteca {

	public void seleccionarDocumento(String codigo) {
		String query = "SELECT * FROM documentos WHERE codigoAlfaNum = " + codigo;
		// Falta codigo
		// Documento doc = new Libro();
		// Documento doc = new Revista();
	}

	public void prestarDocumento(Usuario usuario, Documento documento) {

	}

	public void devolverDocumento(Usuario usuario, Documento documento) {

	}

	public Documento buscarDocumento(String titulo) {
		String query = "SELECT * FROM documentos WHERE titulo LIKE %" + titulo + "%";
		return new Documento(query, titulo);
	}

	public String generarInformesPrestados() {
		String query = "SELECT * FROM prestamos";
		return "";
	}
}
