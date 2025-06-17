package controller;

import java.sql.Connection;
import java.sql.SQLException;

public class TestConexion {
	public static void main(String[] args) {
		try {
			Connection con = Conexion.getConexion();
			System.out.println("¡Conexión exitosa!");
			con.close();
		} catch (SQLException e) {
			System.out.println("Error al conectar con la base de datos: " + e.getMessage());
		}
	}
}
