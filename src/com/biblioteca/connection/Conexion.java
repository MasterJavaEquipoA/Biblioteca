package com.biblioteca.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Conexion al servidor SQL
 * 
 * @author Admin
 *
 */
public class Conexion {

	private static final String URL = "jdbc:mysql://localhost:3306/biblioteca";
	private static final String USER = "root";
	private static final String PASSWORD = "root";

	public static Connection getConexion() {

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conexion = DriverManager.getConnection(URL, USER, PASSWORD);
			System.out.println("Conexion establecida!");
			return conexion;
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Error al conectar con la DB..");
			e.printStackTrace();
			return null;
		}
	}
}
