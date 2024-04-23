package com.biblioteca.clases;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.sql.Date;
import java.sql.Connection;

import com.biblioteca.connection.Conexion;

/**
 * Metodos que utilizan queries y transacciones a la BBDD biblioteca
 * 
 * @author Admin
 *
 */
public class Biblioteca {
	private static final String ANO_PUBLI = "anoPubli";
	private Connection con;

	public Biblioteca() throws SQLException {
		this.con = Conexion.getConexion();

	}

	/**
	 * 
	 * @param codigo codigo id del documento que se quiere seleccionar
	 * @return Documento (Libro o Revista) que coincide en la BBDD o un objeto nulo
	 *         en su defecto
	 */
	public Documento seleccionarDocumento(String codigo) {
		String query = "SELECT * FROM documentos WHERE codigoAlfaNum = ?";
		Documento doc = null;
		try (PreparedStatement selecDoc = con.prepareStatement(query)) {
			selecDoc.setString(1, codigo);
			ResultSet rs = selecDoc.executeQuery();
			if (rs.next()) {
				if (rs.getInt(ANO_PUBLI) > 0) {
					doc = new Libro();

				} else {
					doc = new Revista();
				}
				if (doc instanceof Libro libro) {
					libro.setAnoPubli(rs.getInt(ANO_PUBLI));

				}
				doc.setCodigoAlfaNum(rs.getString("codigoAlfaNum"));
				doc.setTitulo(rs.getString("titulo"));
				doc.setPrestado(rs.getBoolean("prestado"));
				rs.close();
			}

		} catch (SQLException e) {

			e.printStackTrace();
		}
		return doc;
	}

	/**
	 * Metodo para realizar las operaciones necesarias para prestar un libro
	 * 
	 * @param dni       en formato cadena
	 * @param documento previamente selecionado
	 */
	public void prestarDocumento(String dni, Documento documento) {
		if (!documento.isPrestado()) {
			Usuario usuario = validarUsuario(dni);
			if (usuario != null) {

				String queryUpdate = "UPDATE documentos SET prestado = 1 WHERE codigoAlfaNum = '"
						+ documento.getCodigoAlfaNum() + "'";
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

	/**
	 * Metodo para realizar las operaciones necesarias para devolver un libro
	 * 
	 * @param dni       en formato cadena
	 * @param documento previamente selecionado
	 */
	public void devolverDocumento(String dni, Documento documento) {
		if (documento.isPrestado()) {
			Usuario usuario = validarUsuario(dni);
			if (usuario != null) {

				String queryUpdate = "UPDATE documentos SET prestado = 0 WHERE codigoAlfaNum = '"
						+ documento.getCodigoAlfaNum() + "';";
				String queryInsert = "UPDATE prestamos SET devuelto = 1 , fechaFinPrestamo = ? WHERE dni = ? AND codigoAlfaNum = ?";

				try (Statement stmt = con.createStatement();
						PreparedStatement pStmt = con.prepareStatement(queryInsert)) {
					con.setAutoCommit(false);
					stmt.execute(queryUpdate);
					pStmt.setDate(1, Date.valueOf(LocalDate.now()));
					pStmt.setString(2, dni);
					pStmt.setString(3, documento.getCodigoAlfaNum());
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

	/**
	 * Busca un documento segun el titulo
	 * 
	 * @param titulo en formato cadena del libro, puede ser parcial
	 * @return Documento que coincide con la busqueda o nulo en su defecto
	 */
	public List<Documento> buscarDocumento(String titulo) {
		String query = "SELECT * FROM documentos WHERE titulo LIKE '%" + titulo + "%'";
		List<Documento> listaDoc = new LinkedList<>();
		try (Statement stmt = con.createStatement()) {
			ResultSet rs = stmt.executeQuery(query);
			Documento doc = null;
			while (rs.next()) {
				if (rs.getInt(ANO_PUBLI) > 0) {
					doc = new Libro();
				} else {
					doc = new Revista();
				}
				doc.setCodigoAlfaNum(rs.getString("codigoAlfaNum"));
				doc.setTitulo(rs.getString("titulo"));
				doc.setPrestado(rs.getBoolean("prestado"));
				if (doc instanceof Libro libro) {
					libro.setAnoPubli(rs.getInt(ANO_PUBLI));
				}
				System.out.println("Exito, encontrado: " + doc.toString());
				listaDoc.add(doc);
			}
			rs.close();

			System.out.println("No se ha encontrado el libro");

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return listaDoc;
	}

	/**
	 * Realiza una query con triple join a la BBDD para obtener informacion de
	 * documentos prestados
	 * 
	 * @return cadena con formato que contiene todo el historial de de documentos
	 *         prestados
	 */
	public String generarInformesPrestados() {
		StringBuilder query = new StringBuilder();
		query.append("SELECT d.titulo,p.codigoAlfaNum,p.fechaIniPrestamo,p.fechaFinPrestamo,d.anoPubli,");
		query.append("u.dni, u.socio FROM usuarios u JOIN prestamos p ON u.dni = p.dni JOIN documentos d ON ");
		query.append("p.codigoAlfaNum = d.codigoAlfaNum");
		StringBuilder informe = new StringBuilder();
		try (Statement stmt = con.createStatement()) {
			ResultSet rs = stmt.executeQuery(query.toString());
			while (rs.next()) {
				long inicio = rs.getDate("p.fechaIniPrestamo").getTime();
				long fin = rs.getDate("p.fechaFinPrestamo").getTime();
				String days = String.valueOf((fin - inicio) / 86400000);
				String socio = rs.getInt("u.socio") > 0 ? "Socio" : "Usuario Ocasional";
				String linea = String.format("%s (%s)  Cod:%s  Plazo:%s dias  Prestado a:%s(%s) \n",
						rs.getString("d.titulo"), rs.getString("d.anoPubli") == null ? "" : rs.getString("d.anoPubli"),
						rs.getString("p.codigoAlfaNum"), days, rs.getString("u.dni"), socio);
				informe.append(linea);

			}
			System.out.println("-----------Informe generado----------");
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return informe.toString();
	}

	/**
	 * Cierra la conexion a la BBDD
	 * 
	 * @param numero de opcion indicado en el JOPtionPane
	 * @return booleano para continuar o no el bucle de operaciones
	 */
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

	/**
	 * Metodo que valida el dni de un usuario
	 * 
	 * @param dni en formato cadena de un usuario
	 * @return el usuario coincidente de la BBDD o en su defecto nulo
	 */
	private Usuario validarUsuario(String dni) {

		String query = "SELECT * FROM usuarios WHERE dni = ?";
		Usuario usuario = null;
		try (PreparedStatement valUsua = con.prepareStatement(query)) {
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
			}
			rs.close();
		} catch (SQLException e1) {

			e1.printStackTrace();
		}
		return usuario;
	}

}
