package com.biblioteca.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
	
	private static String schema = "biblioteca";
	private static final String url = "jdbc:mysql://localhost:3306" + schema;
	private static final String user = "root";
	private static final String password = "root";
	
	private static Connection conexion;
	
	/**
	 * Establece la conexion y la devuelve, para ser usada en otras clases
	 * @return
	 */
	public static Connection establecerConexion() {
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conexion = DriverManager.getConnection(url, user, password);
			System.out.println("Conexion establecida!");
			
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Error al conectar con la DB..");
			e.printStackTrace();
		}
		
		return conexion;
	}
}
	