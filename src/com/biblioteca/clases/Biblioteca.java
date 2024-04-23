package com.biblioteca.clases;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.biblioteca.connection.Conexion;

public class Biblioteca {

	//Importo la conexion desde mi clase conexion creada previamente
	private static Connection conexion = Conexion.establecerConexion()
	
	/**
	 * Recibe un código de documento (codigoAlfaNum)
	 * Busca en la BD si existe un doc con ese codigo
	 * @param codigo
	 * @return
	 */
	public static Documento seleccionarDocumento(String codigo) {
		String query = 	"SELECT *"
						+ "FROM documentos "
						+ "WHERE codigoAlfaNum = " + codigo;
		
		try {
			PreparedStatement stmt = conexion.prepareStatement(query);
			ResultSet resultado = stmt.executeQuery();
			
			if (resultado.next()) {
				
				System.out.println("El libro que has seleccionado existe en nuesta BD!");
				System.out.println(resultado.toString());
				
			}else {
				System.out.println("No existe documento con ese codigo..");
			}
			
		} catch (SQLException e) {
			e.getMessage();
		}
		
		return new Documento();
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
