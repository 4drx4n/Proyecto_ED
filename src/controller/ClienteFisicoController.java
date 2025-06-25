package controller;

import java.sql.*;
import model.ClienteFisico;
import util.ValidadorClienteFisico;

public class ClienteFisicoController {

	private final ValidadorClienteFisico validador = new ValidadorClienteFisico();

	public void insertarClienteFisico(ClienteFisico c) throws SQLException {
		try (Connection con = Conexion.getConexion()) {
			insertarClienteFisico(con, c);
		}
	}

	public void insertarClienteFisico(Connection con, ClienteFisico c) throws SQLException {
		// VALIDACIONES PARA TEST
		if (!validador.validarCliente(c)) {
			throw new IllegalArgumentException("Cliente físico inválido" + c);
		}

		// 2) Insert en persona
		String sqlPersona =
				"INSERT INTO persona (dni, correo, nombre, apellidos, direccion, telefono, rol) " +
						"VALUES (?, ?, ?, ?, ?, ?, ?)";
		try (PreparedStatement psPersona = con.prepareStatement(
				sqlPersona, PreparedStatement.RETURN_GENERATED_KEYS)) {
			psPersona.setString(1, c.getDni());
			psPersona.setString(2, c.getCorreo());
			psPersona.setString(3, c.getNombre());
			psPersona.setString(4, c.getApellidos());
			psPersona.setString(5, c.getDireccion());
			psPersona.setInt(6, c.getTelefono());
			psPersona.setString(7, c.getRol());

			if (psPersona.executeUpdate() == 0) {
				throw new SQLException("No se pudo insertar en la tabla persona.");
			}

			try (ResultSet rsPersona = psPersona.getGeneratedKeys()) {
				if (!rsPersona.next()) {
					throw new SQLException("No se pudo recuperar el ID de usuario.");
				}
				int idUsuario = rsPersona.getInt(1);

				// 3) Insert en cliente
				String sqlCliente =
						"INSERT INTO cliente (id_usuario, fecha_alta) VALUES (?, ?)";
				try (PreparedStatement psCliente = con.prepareStatement(
						sqlCliente, PreparedStatement.RETURN_GENERATED_KEYS)) {
					psCliente.setInt(1, idUsuario);
					psCliente.setDate(2, new java.sql.Date(c.getFecha_alta().getTime()));

					if (psCliente.executeUpdate() == 0) {
						throw new SQLException("No se pudo insertar en la tabla cliente.");
					}

					try (ResultSet rsCliente = psCliente.getGeneratedKeys()) {
						if (!rsCliente.next()) {
							throw new SQLException("No se pudo recuperar el ID del cliente.");
						}
						int idCliente = rsCliente.getInt(1);

						// 4) Insert en cliente_fisico
						String sqlFisico =
								"INSERT INTO cliente_fisico (id_cliente, puntos_establecimiento) VALUES (?, ?)";
						try (PreparedStatement psFisico = con.prepareStatement(sqlFisico)) {
							psFisico.setInt(1, idCliente);
							psFisico.setInt(2, 0);
							if (psFisico.executeUpdate() == 0) {
								throw new SQLException("No se pudo insertar en la tabla cliente_fisico.");
							}
						}
					}
				}
			}
		} catch (SQLException e) {
			System.err.println("Error al insertar cliente físico: " + e.getMessage());
			throw e;
		}
	}

	public ClienteFisico selectLoginClienteFisico(String dni, String correo) throws SQLException {
		String sql = """
				    SELECT c.id_cliente, cf.puntos_establecimiento
				    FROM persona p
				    JOIN cliente c ON p.id_usuario = c.id_usuario
				    JOIN cliente_fisico cf ON c.id_cliente = cf.id_cliente
				    WHERE p.dni = ?
				      AND p.correo = ?
				""";

		try (Connection con = Conexion.getConexion();
				PreparedStatement stmt = con.prepareStatement(sql)) {

			stmt.setString(1, dni);
			stmt.setString(2, correo);

			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					ClienteFisico cf = new ClienteFisico();
					cf.setDni(dni);
					cf.setCorreo(correo);
					cf.setId_cliente(rs.getInt("id_cliente"));
					cf.setPuntos_establecimiento(rs.getInt("puntos_establecimiento"));
					return cf;
				}
				return null;
			}
		}
	}

	public int selectPuntosEstablecimiento(String dni) throws SQLException {
		String sql = """
				    SELECT cf.puntos_establecimiento
				    FROM persona p
				    JOIN cliente c ON p.id_usuario = c.id_usuario
				    JOIN cliente_fisico cf ON c.id_cliente = cf.id_cliente
				    WHERE p.dni = ?
				""";

		try (Connection con = Conexion.getConexion();
				PreparedStatement stmt = con.prepareStatement(sql)) {

			stmt.setString(1, dni);
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					return rs.getInt("puntos_establecimiento");
				}
				return 0;
			}
		}
	}

	public void actualizarPuntos(String dni, int cantidad, boolean sumar) throws SQLException {
		String operacion = sumar ? "+" : "-";
		String sql = "UPDATE cliente_fisico cf " +
				"JOIN cliente c ON cf.id_cliente = c.id_cliente " +
				"JOIN persona p ON c.id_usuario = p.id_usuario " +
				"SET cf.puntos_establecimiento = cf.puntos_establecimiento " + operacion + " ? " +
				"WHERE p.dni = ?";

		try (Connection con = Conexion.getConexion();
				PreparedStatement stmt = con.prepareStatement(sql)) {

			stmt.setInt(1, cantidad);
			stmt.setString(2, dni);
			stmt.executeUpdate();
		}
	}
}
