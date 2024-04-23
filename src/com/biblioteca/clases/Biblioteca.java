package com.biblioteca.clases;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


import com.biblioteca.connection.Conexion;

public class Biblioteca {

	// Importo la conexion desde mi clase conexion creada previamente
	private static Connection conexion = Conexion.establecerConexion();

	/**
	 * Recibe un código de documento (codigoAlfaNum) Busca en la BD si existe un doc
	 * con ese codigo
	 * 
	 * @param codigo
	 * @return
	 */
	public static Documento seleccionarDocumento(String codigo) {
		String query = "SELECT *" + 
						"FROM documentos " +
						"WHERE codigoAlfaNum = ?;";
		Documento documento = null;
		

		try {
			PreparedStatement stmt = conexion.prepareStatement(query);
			stmt.setString(1, codigo);
			
			ResultSet resultado = stmt.executeQuery();

			if (resultado.next()) {

				if (resultado.getInt("anoPubli") > 0 ) {
					
					documento = new Libro();
					
					if(documento instanceof Libro libro) {
						
						libro.setAnoPubli(resultado.getInt("anoPubli"));
					}
					
				}else {
					documento = new Revista();
				}
				
				documento.setTitulo(resultado.getString("titulo"));
				documento.setCodigoAlfaNum(resultado.getString("codigoAlfaNum"));

			} else {
				System.out.println("No existe documento con ese codigo..");
			}

		} catch (SQLException e) {
			e.getMessage();
		}

		return documento;
	}

	/**
	 * Vincula el objeto documento a un usuario Primero se llama a la funcion que
	 * valida que usuario existe en la BD
	 * 
	 * @param usuario
	 * @param documento
	 */
	public void prestarDocumento(String usuario, Documento documento) {
		Statement stmt;
		if (validarUsuario(usuario)) {

		}
	}

	/**
	 * Valida usuaio en la BD mediante una consulta
	 * 
	 * @param usuario
	 * @return
	 */
	public static boolean validarUsuario(String usuario) {
		String query = "SELECT * FROM usuarios WHERE dni = " + usuario;
		//
		return false;
	}

	/**
	 * Desvincula el documento al usuario
	 * 
	 * @param usuario
	 * @param documento
	 */
	public void devolverDocumento(String usuario, Documento documento) {

	}

	/**
	 * Busca un documento en la BD mediante semejanza con el titulo dado
	 * 
	 * @param titulo
	 * @return
	 */
	public Documento buscarDocumento(String titulo) {
		String query = "SELECT * FROM documentos WHERE titulo LIKE %" + titulo + "%";
		return new Documento(query, titulo);
	}

	// Metodos de biblioteca
	/**
	 * Genera un informe detallado con info sobre los documentos prestados
	 * 
	 * @return
	 */
	public String generarInformesPrestados() {
		String query = "SELECT * FROM prestamos";
		return "";
	}
}
