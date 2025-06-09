package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
	private static final String URL = "jdbc:mysql://localhost:3306/info_shop";
	private static final String USER = "root";
	private static final String PASS = "r00t1234";

	public static Connection getConexion() throws SQLException {
		return DriverManager.getConnection(URL, USER, PASS);
	}
}
