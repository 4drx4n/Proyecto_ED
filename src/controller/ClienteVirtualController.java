package controller;

import java.sql.*;

public class ClienteVirtualController {

	public boolean selectClienteVirtual(String correo, String contrasenya) throws SQLException {
		String sql = """
				    SELECT p.id_usuario
				    FROM persona p
				    JOIN cliente c ON p.id_usuario = c.id_usuario
				    JOIN cliente_virtual cv ON c.id_cliente = cv.id_cliente
				    WHERE p.correo = ? AND cv.contrasenya = ?
					 """;


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