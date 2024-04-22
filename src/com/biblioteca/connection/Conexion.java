package com.biblioteca.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Conexion {

	private static final String url = "jdbc:mysql://localhost:3306/biblioteca";
	private static final String user = "root";
	private static final String password = "root";

	private static Connection conexion;

	public static Connection getConexion() {

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conexion = DriverManager.getConnection(url, user, password);

			System.out.println("Conexion establecida!");
			return conexion;
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Error al conectar con la DB..");
			e.printStackTrace();
			return null;
		}
	}
}
