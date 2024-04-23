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
		this.con = Conexion.getConexion();

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
				doc.setPrestado(rs.getInt("prestado") > 0);
				selecDoc.close();
			}

		} catch (SQLException e) {

			e.printStackTrace();
		}
		return doc;
	}

	public void prestarDocumento(String dni, Documento documento) {
		if (!documento.isPrestado()) {
			Usuario usuario = validarUsuario(dni);
			if (usuario != null) {

				String queryUpdate = "UPDATE documentos SET prestado = 1 WHERE codigoAlfaNum = "
						+ documento.getCodigoAlfaNum();
				String queryInsert = "INSERT INTO prestamos(dni,codigoAlfaNum,fechaIniPrestamo,fechaFinPrestamo,"
						+ "devuelto) VALUES (?,?,?,?,?);";
				LocalDate localDateHoy = LocalDate.now();
				LocalDate finPrestamo;
				if (usuario.isEsSocio()) {
					finPrestamo = documento instanceof Revista
							? localDateHoy.plus(Revista.MAX_DURACION_SOCIO, ChronoUnit.DAYS)
							: localDateHoy.plus(Libro.MAX_DURACION_SOCIO, ChronoUnit.DAYS);

				} else {
					finPrestamo = documento instanceof Revista
							? localDateHoy.plus(Revista.MAX_DURACION_OCAS, ChronoUnit.DAYS)
							: localDateHoy.plus(Libro.MAX_DURACION_OCAS, ChronoUnit.DAYS);
				}
				try (Statement stmt = con.createStatement();
						PreparedStatement pStmt = con.prepareStatement(queryInsert)) {
					con.setAutoCommit(false);
					stmt.execute(queryUpdate);
					pStmt.setString(1, dni);
					pStmt.setString(2, documento.getCodigoAlfaNum());
					pStmt.setString(3, localDateHoy.toString());
					pStmt.setString(4, finPrestamo.toString());
					pStmt.setInt(5, 0);
					pStmt.execute();
					con.commit();
					System.out.println("Éxito " + documento.getTitulo() + " prestado a " + usuario.getNombre());
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
		if (documento.isPrestado()) {
			Usuario usuario = validarUsuario(dni);
			if (usuario instanceof Socio || usuario instanceof Ocasional) {

				String queryUpdate = "UPDATE documentos SET prestado = 0 WHERE codigoAlfaNum = "
						+ documento.getCodigoAlfaNum();
				String queryInsert = "UPDATE prestamos SET devuelto = 1 WHERE dni = ? AND codigoAlfaNum = ?";

				try (Statement stmt = con.createStatement();
						PreparedStatement pStmt = con.prepareStatement(queryInsert)) {
					con.setAutoCommit(false);
					stmt.execute(queryUpdate);
					pStmt.setString(1, dni);
					pStmt.setString(2, documento.getCodigoAlfaNum());
					pStmt.execute();

					con.commit();

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
		String query = "SELECT * FROM documentos WHERE titulo LIKE '%" + titulo + "%'";
		Documento doc = null;
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			if (rs.next()) {
				if (rs.getInt("anoPubli") > 0) {
					doc = new Libro();
				} else {
					doc = new Revista();
				}
				doc.setCodigoAlfaNum(rs.getString("codigoAlfaNum"));
				doc.setTitulo(rs.getString("titulo"));
				doc.setPrestado(rs.getInt("prestado") > 0);
				if (doc instanceof Libro libro) {
					libro = (Libro) doc;
					libro.setAnoPubli(rs.getInt("anoPubli"));
					doc = libro;
				}
				System.out.println("Exito, encontrado: " + doc.toString());
				stmt.close();
			} else {
				System.out.println("No se ha encontrado el libro");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return doc;
	}

	public String generarInformesPrestados() {
		String query = "SELECT d.titulo,p.codigoAlfaNum,p.fechaIniPrestamo,p.fechaFinPrestamo,d.anoPubli,"
				+ "u.dni, u.socio FROM usuarios u JOIN prestamos p ON u.dni = p.dni JOIN documentos d ON "
				+ "p.codigoAlfaNum = d.codigoAlfaNum";
		String informe = "";
		try (Statement stmt = con.createStatement()) {
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				long inicio = rs.getDate("p.fechaIniPrestamo").getTime();
				long fin = rs.getDate("p.fechaFinPrestamo").getTime();
				String days = String.valueOf((fin - inicio) / 86400000);
				String socio = rs.getInt("u.socio") > 0 ? "Socio" : "Usuario Ocasional";
				String linea = String.format("%s (%s)  Cod:%s  Plazo:%s dias  Prestado a:%s(%s) \n",
						rs.getString("d.titulo"), rs.getString("d.anoPubli"), rs.getString("p.codigoAlfaNum"), days,
						rs.getString("u.dni"), socio);
				informe += linea;

			}
			System.out.println("-----------Informe generado----------");
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return informe;
	}

	public boolean salir(int numero) {
		if (numero > 0) {
			return true;
		} else {
			try {
				con.close();
				return false;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return true;

	}

	private Usuario validarUsuario(String dni) {

		String query = "SELECT * FROM usuarios WHERE dni = ?";
		Usuario usuario = null;
		try {
			PreparedStatement valUsua = con.prepareStatement(query);
			valUsua.setString(1, dni);
			ResultSet rs = valUsua.executeQuery();
			if (rs.next()) {
				if (rs.getBoolean("socio")) {
					usuario = new Socio();
				} else {
					usuario = new Ocasional();
				}
				usuario.setDni(rs.getString("dni"));
				usuario.setNombre(rs.getString("nombre"));
				usuario.setEsSocio(rs.getBoolean("socio"));
			}
		} catch (SQLException e1) {

			e1.printStackTrace();
		}
		return usuario;
	}

}
