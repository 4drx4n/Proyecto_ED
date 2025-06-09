package controller;

import java.sql.*;

public class ClienteVirtualController {

	public boolean selectClienteVirtual(String correo, String contrasenya) throws SQLException {
		String sql = """
				SELECT p.id_usuario
				FROM cliente_virtual cv
				JOIN persona p ON cv.id_usuario = p.id_usuario
				WHERE p.correo = ? AND cv.contrasenya = ?""";


		try (Connection con = Conexion.getConexion();
				PreparedStatement stmt = con.prepareStatement(sql)) {

			stmt.setString(1, correo);
			stmt.setString(2, contrasenya);

			try (ResultSet rs = stmt.executeQuery()) {
				return rs.next();
			}
		}
	}
}