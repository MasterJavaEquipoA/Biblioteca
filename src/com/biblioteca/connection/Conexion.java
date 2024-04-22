package com.biblioteca.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Conexion {

	static final String url = "jdbc:mysql://localhost:3306/biblioteca";
	static final String user = "root";
	static final String password = "root";
	
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
}
	