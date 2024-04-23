package com.biblioteca.clases;

import java.util.Scanner;

import javax.swing.JOptionPane;

import com.biblioteca.connection.Conexion;

public class Main {
	public static void main(String[] args) {
		
		// Genero menú
		Biblioteca biblio = new Biblioteca();
		Usuario camilo = new Usuario();
		Documento documentoActual = new Documento();
		Scanner scanner = new Scanner(System.in);
		String opcion;
		boolean salir = false;

		while (!salir) {
			System.out.printf("""
				-- Menú Biblioteca --
                A. Seleccionar Documento
                B. Validar Usuario
                C. Prestar Documento
                D. Devolver Documento
                E. Buscar Documento
                F. Generar Informe
                S. Salir
                Seleccione una opción:
                 """);
			opcion = scanner.next();
			
			switch (opcion.toLowerCase()) {

			case "a": {// Seleccionar Documento
				System.out.println("Comprobando que el documento con codigo '131859855-9' existe ... ");
				System.out.println("Existe! Asignado a Documento Actual");
				documentoActual = Biblioteca.seleccionarDocumento("131859855-9");
				break;
			}

			case "b": {// Validar Usuario
				System.out.println("Comprobando que existe el usuario con DNI: '56734598D' ...");
				camilo = Biblioteca.validarUsuario("56734598D");
				break;
			}
			case "c": {// Prestar Documento
				System.out.println("Prestando documento actual a usuario con DNI: '56734598D' ...");
				biblio.prestarDocumento(camilo, documentoActual);
				break;
			}
			case "d": {// Devolver Documento

				break;
			}
			case "e": {// Buscar Documento

				break;
			}
			case "f": {// Generar Informe

				break;
			}
			case "s": {// Salir de menu
				salir = true;
				break;
			}
			default:
				JOptionPane.showMessageDialog(null, "Opción inválida");
				break;
			}

		}

	}
}
