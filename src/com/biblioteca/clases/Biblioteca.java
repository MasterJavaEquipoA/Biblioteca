package com.biblioteca.clases;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.sql.Connection;

import com.biblioteca.connection.Conexion;

public class Biblioteca {
	private Connection con;

	public Biblioteca() throws SQLException {
		this.con = new Conexion().getConexion();

	}

	public Documento seleccionarDocumento(String codigo) {
		String query = "SELECT * FROM documentos WHERE codigoAlfaNum = ?";
		Documento doc = null;
		try {
			PreparedStatement selecDoc = con.prepareStatement(query);
			selecDoc.setString(1, codigo);
			ResultSet rs = selecDoc.executeQuery();
			if (rs.next()) {
				if (rs.getInt("anoPubli") > 0) {
					doc = new Libro();
				} else {
					doc = new Revista();
				}
				doc.setCodigoAlfaNum(rs.getString("codigoAlfaNum"));
				doc.setTitulo(rs.getString("titulo"));
				doc.setPrestado(rs.getBoolean("prestado"));
			}

		} catch (SQLException e) {

			e.printStackTrace();
		}
		return doc;
	}

	public void prestarDocumento(String dni, Documento documento) {
		if (documento.isPrestado()) {
			Usuario usuario = validarUsuario(dni);
			if (usuario instanceof Socio || usuario instanceof Ocasional) {

				String queryUpdate = "UPDATE documentos SET prestado = true";
				String queryInsert = "INSERT INTO prestamos(dni,codigoAlfaNum,fechaIniPrestamo,fechaFinPrestamo,"
						+ "devuelto) VALUES (?,?,?,?,?);";
				LocalDate localDateHoy = LocalDate.now();
				LocalDate finPrestamo;
				if (usuario instanceof Socio) {
					finPrestamo = documento instanceof Revista
							? localDateHoy.plus(Revista.MAX_DURACION_SOCIO, ChronoUnit.DAYS)
							: localDateHoy.plus(Libro.MAX_DURACION_SOCIO, ChronoUnit.DAYS);

				} else {
					finPrestamo = documento instanceof Revista
							? localDateHoy.plus(Revista.MAX_DURACION_OCAS, ChronoUnit.DAYS)
							: localDateHoy.plus(Libro.MAX_DURACION_OCAS, ChronoUnit.DAYS);
				}
				try {
					con.setAutoCommit(false);
					Statement stmt = con.createStatement();
					stmt.execute(queryUpdate);
					PreparedStatement Pstmt = con.prepareStatement(queryInsert);
					Pstmt.setString(1, dni);
					Pstmt.setString(2, documento.getCodigoAlfaNum());
					Pstmt.setString(3, localDateHoy.toString());
					Pstmt.setString(4, finPrestamo.toString());
					Pstmt.setBoolean(5, false);
					Pstmt.executeQuery();
					con.commit();
					stmt.close();
					Pstmt.close();
					System.out.println("Exito " + documento.getTitulo() + " prestado a " + usuario.getNombre());
				} catch (SQLException e) {
					e.printStackTrace();
					System.out.println("No se ha podido prestar el libro");
				}

			} else {
				System.out.println("El usuario no es valido");
			}
		} else {
			System.out.println("El documento " + documento.getTitulo() + " ya está prestado.");
		}

	}

	public void devolverDocumento(String dni, Documento documento) {
		if (!documento.isPrestado()) {
			Usuario usuario = validarUsuario(dni);
			if (usuario instanceof Socio || usuario instanceof Ocasional) {

				String queryUpdate = "UPDATE documentos SET prestado = false WHERE codigoAlfaNum = "
						+ documento.getCodigoAlfaNum();
				String queryInsert = "UPDATE prestamos SET devuelto = true WHERE dni = ? AND codigoAlfaNum = ?";

				try {
					con.setAutoCommit(false);
					Statement stmt = con.createStatement();
					stmt.execute(queryUpdate);
					PreparedStatement Pstmt = con.prepareStatement(queryInsert);
					Pstmt.setString(1, dni);
					Pstmt.setString(2, documento.getCodigoAlfaNum());
					Pstmt.executeQuery();

					con.commit();
					stmt.close();
					Pstmt.close();

					System.out.println("Exito " + documento.getTitulo() + " devuelto de: " + usuario.getNombre());
				} catch (SQLException e) {
					e.printStackTrace();
					System.out.println("No se ha podido devolver el libro");
				}

			} else {
				System.out.println("El usuario no es valido");
			}
		} else {
			System.out.println("El documento " + documento.getTitulo() + " no está prestado.");
		}
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

	private Usuario validarUsuario(String dni) {

		String query = "SELECT * FROM usuarios WHERE dni = ?";
		Usuario usuario = null;
		try {
			PreparedStatement valUsua = con.prepareStatement(query);
			valUsua.setString(1, dni);
			ResultSet rs = valUsua.executeQuery();
			if (rs.next()) {
				if (rs.getBoolean("usuario")) {
					usuario = new Socio();
				} else {
					usuario = new Ocasional();
				}
				usuario.setDni(rs.getString("dni"));
				usuario.setNombre(rs.getString("nombre"));
			}
		} catch (SQLException e1) {

			e1.printStackTrace();
		}
		return usuario;
	}
}
