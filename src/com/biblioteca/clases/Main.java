package com.biblioteca.clases;

import com.biblioteca.connection.Conexion;

public class Main {
	public static void main(String[] args) {
		
		Documento documentoActual = new Documento();
		
		System.out.println("Comprobamos que el documento con codigo '131859855-9' ");
		System.out.println("Si existe lo asignamos a documento actual");
		
		documentoActual = Biblioteca.seleccionarDocumento("131859855-9");
		
		
		
		
	}
}
