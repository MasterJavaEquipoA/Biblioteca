package com.biblioteca.principal;

import java.sql.SQLException;

import javax.swing.JOptionPane;

import com.biblioteca.clases.Biblioteca;
import com.biblioteca.clases.Documento;

/**
 * Ejecucion de las acciones del proyecto de biblioteca
 * 
 * @author Admin
 *
 */
public class Principal {

	public static void main(String[] args) {
		Biblioteca biblio = crearBiblioteca();
		String textoOpciones = "Seleccione una opcion: \n1.-Selecciona documento.\n2.-Busca documento.\n3.-Informe de prestamos. \n0.-Salir.";
		String textoPrestamo = "Seleccione una opcion: \n1.-Prestar documento.\n2.-Devolver documento.\n0.-Salir.";
		int opcion;
		Documento doc = null;
		do {
			opcion = Integer.valueOf(JOptionPane.showInputDialog(textoOpciones));
			switch (opcion) {
			case 1:
				doc = biblio.seleccionarDocumento(
						JOptionPane.showInputDialog("Escribe el codigo alfanumerico del documento:"));
				break;
			case 2:
				doc = biblio.buscarDocumento(
						JOptionPane.showInputDialog("Escribe el titulo del documento que deseas buscar:"));
				break;
			case 3:
				JOptionPane.showMessageDialog(null, biblio.generarInformesPrestados());
				break;
			default:
				break;
			}
			if (doc != null) {
				opcion = Integer.valueOf(JOptionPane.showInputDialog(textoPrestamo));
				switch (opcion) {
				case 1:
					biblio.prestarDocumento(JOptionPane.showInputDialog("Prestando: Escriba el dni del socio"), doc);
					break;
				case 2:
					biblio.devolverDocumento(JOptionPane.showInputDialog("Devolviendo: Escriba el dni del socio"), doc);
					break;
				default:
					break;
				}
			}

		} while (biblio.salir(opcion));

	}

	private static Biblioteca crearBiblioteca() {
		try {
			return new Biblioteca();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}
