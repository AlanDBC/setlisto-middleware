package com.setlisto.utils;

import java.sql.Connection;
import java.sql.DriverManager;

public class JDBCUtils {

	public static Connection getConnection() {
		// TODO: Configuracion, Gestion de la exception, etc.
		try {
			// Carga el driver JDBC
			Class.forName("com.mysql.cj.jdbc.Driver");
			return DriverManager.getConnection("jdbc:mysql://localhost:3306/setlisto", "root", "abc123.");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}

