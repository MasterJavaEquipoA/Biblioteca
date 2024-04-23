package com.biblioteca.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connex {
	static String url = "jdbc:mysql://localhost:3306/biblioteca";
	static String username = "root";
	static String pass = "root";
	
	static Connection conexion;

	public static Connection crearConexion() throws SQLException {
		Connection conexion = DriverManager.getConnection(url, username, pass);
		return conexion;
		
	}
	
}
