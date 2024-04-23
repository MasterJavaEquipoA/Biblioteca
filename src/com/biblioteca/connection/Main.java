package com.biblioteca.connection;

import java.sql.*;
import java.util.Scanner;

import javax.sql.*;

import com.biblioteca.clases.Documento;
import com.biblioteca.clases.Libro;
import com.biblioteca.clases.Revista;
import com.biblioteca.clases.Usuario;

public class Main {

	public static void main(String[] args) throws SQLException {

		String url = "jdbc:mysql://localhost:3306/biblioteca";
		String username = "root";
		String pass = "root";

		String sqlDatosUsuarios = "SELECT * FROM USUARIOS";
		String sqlDatosDocumentos = "SELECT * FROM documentos";

		try {
			Connection c = DriverManager.getConnection(url, username, pass);
			System.out.println("Conexion establecida con exito");
			Statement stmt = c.createStatement();

			try {

				Scanner teclado = new Scanner(System.in);
				int opcion;

				do {
					System.out.println("1. Buscar usuario");
					System.out.println("2. Listar usuarios");
					System.out.println("3. Ver documentos prestados");
					System.out.println("4. Buscar documento");
					System.out.println("5. Listar personas con documentos alquilados");
					System.out.println("6. Salir");
					System.out.print("Elige una opción: ");
					opcion = teclado.nextInt();

					switch (opcion) {
					case 1:
						// Código para buscar usuario
						System.out.println("Introduzca el dni del usuario a buscar");
						Scanner teclado1 = new Scanner(System.in);
						String dniBuscar = teclado1.nextLine();
						String sqlBuscarUsuarios = "SELECT * FROM usuarios WHERE dni ='" + dniBuscar + "';";

						ResultSet rs1 = stmt.executeQuery(sqlBuscarUsuarios);

						while (rs1.next()) {

							String dni = rs1.getString("dni");
							String nombre = rs1.getString("nombre");
							boolean socio = rs1.getBoolean("socio");

							Usuario user = new Usuario(dni, nombre, socio);
							System.out.println(user);
						}

						break;
					case 2:
						// Código para listar usuarios

						ResultSet rs2 = stmt.executeQuery(sqlDatosUsuarios);

						while (rs2.next()) {
							String dni = rs2.getString("dni");
							String nombre = rs2.getString("nombre");
							boolean socio = rs2.getBoolean("socio");
							System.out.println(dni + "---------" + nombre + "---------" + socio);
						}
						break;
					case 3:
						// Código para ver documento

						ResultSet rs3 = stmt.executeQuery(sqlDatosDocumentos);
						while (rs3.next()) {
							String codigoAlfaNum = rs3.getString("codigoAlfaNum");
							String titulo = rs3.getString("titulo");
							int anoPubli = rs3.getInt("anoPubli");
							boolean prestado = rs3.getBoolean("prestado");

							System.out.println(
									codigoAlfaNum + "--" + titulo + "---------" + anoPubli + "---------" + prestado);
						}
						break;
					case 4:
						// Código para buscar documento
						System.out.println("Introduzca el codigo del documento a buscar");
						Scanner teclado2 = new Scanner(System.in);
						String codigo = teclado2.nextLine();
						String sqlBuscarDocumentos = "SELECT * FROM documentos WHERE codigoAlfaNum ='" + codigo + "';";

						ResultSet rs4 = stmt.executeQuery(sqlBuscarDocumentos);

						while (rs4.next()) {

							String codigoAlfaNum = rs4.getString("codigoAlfaNum");
							String titulo = rs4.getString("titulo");
							int anoPubli = rs4.getInt("anoPubli");
							boolean prestado = rs4.getBoolean("prestado");

							if (anoPubli > 0) {
								Documento doc = new Libro(codigoAlfaNum, titulo, anoPubli);
								doc.setPrestado(prestado);
								System.out.println(doc);

							} else {
								Documento doc = new Revista(codigoAlfaNum, titulo);
								System.out.println(doc);

							}
						}

						break;
					case 5:
						// Código para listar personas con libros alquilados

						String sqlUsuarios_Documentos = "SELECT u.dni,p.codigoAlfaNum,p.fechaIniPrestamo,p.fechaFinPrestamo,p.devuelto, u.socio FROM usuarios u JOIN prestamos p ON u.dni = p.dni";
						ResultSet rs5 = stmt.executeQuery(sqlUsuarios_Documentos);
						while (rs5.next()) {
							String dni = rs5.getString(1);
							String codAlfaNum = rs5.getString(2);
							Date fechaIni = rs5.getDate("fechaIniPrestamo");
							Date fechaFin = rs5.getDate("fechaFinPrestamo");
							boolean devuelto = rs5.getBoolean(5);
							boolean socio = rs5.getBoolean(6);
							System.out.println(
									"Dni: " + dni + " CodigoDocumento: " + codAlfaNum + " Fecha de Prestamo: " + fechaIni
											+ " Fecha de Retorno: " + fechaFin + " Devolucion: " + devuelto + " Socio: " + socio);
						}
						break;
					case 6:
						System.out.println("Saliendo...");
						break;
					default:
						System.out.println("Opción no válida. Por favor, elige una opción del 1 al 6.");
						break;
					}
				} while (opcion != 6);
				teclado.close();

			} catch (Exception e) {
				System.out.println(e.getMessage());
			} finally {
				System.out.println("Fallo al realizar la query");

			}

		} finally {
			System.out.println("La conexion se ha cerrado ");

		}
	}

}
