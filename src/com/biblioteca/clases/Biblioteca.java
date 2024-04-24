package com.biblioteca.clases;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import com.biblioteca.connection.Conexion;

public class Biblioteca {

	// Importo la conexion desde mi clase conexion creada previamente
	private static Connection conexion;

	public Biblioteca() {
		this.conexion = Conexion.establecerConexion();
	}

	/**
	 * Recibe un código de documento (codigoAlfaNum) Busca en la BD si existe un doc
	 * con ese codigo
	 * 
	 * @param codigo
	 * @return
	 */
	public static Documento seleccionarDocumento(String codigo) {
		String query = "SELECT *" + "FROM documentos " + "WHERE codigoAlfaNum = ?;";
		Documento documento = null;

		try {
			PreparedStatement stmt = conexion.prepareStatement(query);
			stmt.setString(1, codigo);

			ResultSet resultado = stmt.executeQuery();

			if (resultado.next()) {

				if (resultado.getInt("anoPubli") > 0) {

					documento = new Libro();

					if (documento instanceof Libro libro) {

						libro.setAnoPubli(resultado.getInt("anoPubli"));
					}

				} else {
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
	 * Valida un usuario mediante dni en la BD
	 * 
	 * @param usuario
	 * @return
	 */
	public static Usuario validarUsuario(String dniUsuario) {
		String query = "SELECT *" + "FROM usuarios " + "WHERE dni = ?;";

		Usuario usuario = null;

		try {
			PreparedStatement stmt = conexion.prepareStatement(query);
			stmt.setString(1, dniUsuario);
			ResultSet resultado = stmt.executeQuery();

			// Si devuelve true es que ha encontrado el dni
			if (resultado.next()) {
				System.out.println("Existe! Usuario Validado");

				if (resultado.getBoolean("socio")) {
					usuario = new Socio();
					System.out.println("El usuario es de tipo socio");

				} else {
					usuario = new Ocasional();
					System.out.println("El usuario es de tipo ocasional");
				}

				usuario.setDni(resultado.getString("dni"));
				usuario.setNombre(resultado.getString("nombre"));

			}

		} catch (SQLException e) {
			e.getMessage();
		}

		return usuario;
	}

	/**
	 * Vincula el objeto documento a un usuario Primero se llama a la funcion que
	 * valida que usuario existe en la BD
	 * 
	 * @param camilo
	 * @param documento
	 */
	public void prestarDocumento(Usuario camilo, Documento documento) {
		
		//Necesito una transaction para modificar ambas tablas
		String queryDocumento = "UPDATE documentos SET prestado = 1 WHERE codigoAlfaNum = '" + documento.getCodigoAlfaNum() + "';";
		
		String queryPrestamo = "INSERT INTO prestamos (dni, codigoAlfaNum, fechaIniPrestamo, fechaFinPrestamo, devuelto)"
								+ " VALUES (?, ?, ?, ?, ?);";
		
		
		try (Statement stmt = conexion.createStatement();
			 PreparedStatement prep = conexion.prepareStatement(queryPrestamo)){
			
			 conexion.setAutoCommit(false);//transaction
			 stmt.execute(queryDocumento);
			 prep.setString(1, camilo.getDni());
			 prep.setString(2, documento.getCodigoAlfaNum());
			 prep.setString(3, LocalDate.now().toString());
			 prep.setString(4, LocalDate.now().plus(15, ChronoUnit.DAYS).toString());
			 prep.setInt(5, 0);
			 
			 prep.execute();
			
			conexion.commit();
		
			System.out.println("Prestamos realizado con exito!");
			System.out.println("Se ha prestado el libro " + documento.getTitulo() + " al usuario " + camilo.getNombre());
		}catch (SQLException e) {
			e.printStackTrace();
			e.getMessage();
		}
	}

	/**
	 * Desvincula el documento al usuario
	 * 
	 * @param usuario
	 * @param documento
	 */
	public void devolverDocumento(Usuario usuario, Documento documento) {
		
		//Vuelvo a necesitar transaction para modificar ambas tablas: Prestamos - Documentos
		
		String queryUpdateDocumentos = "UPDATE documentos SET prestado = 0 WHERE codigoAlfaNum = '" + documento.getCodigoAlfaNum() + "';";
		String queryUpdatePrestamos = "UPDATE prestamos SET devuelto = 1 WHERE codigoAlfaNum = '" + documento.getCodigoAlfaNum() + "';";
		
		try {
			 conexion.setAutoCommit(false);//transaction
			Statement stmt = conexion.createStatement();
			 
			Statement stmt2 = conexion.createStatement();
			
			if (stmt.execute(queryUpdateDocumentos)) {
				
				stmt2.execute(queryUpdatePrestamos);
				System.out.println("Documento devuelto con exito!");
				System.out.println("El usuario " + usuario.getNombre() + " ha devuelto el libro " + documento.getTitulo());
			}else {
				System.out.println("Error al devolver el libro..");
			}
			
			conexion.commit();
			
		} catch (SQLException e) {
			e.printStackTrace();
			e.getMessage();
		}
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
