package com.biblioteca.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
	
	private static String schema = "biblioteca";
	private static final String url = "jdbc:mysql://localhost:3306" + schema;
	private static final String user = "root";
	private static final String password = "root";
	
	private Connection conexion;
	
	public Conexion() {
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conexion = DriverManager.getConnection(url, user, password);
			System.out.println("Conexion establecida!");
			
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Error al conectar con la DB..");
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Será llamado desde otras clases para realizar consultas
	 * Devuelve la conexion jdbc a sql
	 * @return
	 */
	public Connection obtenerConexion() {
		return conexion;
	}
}
	