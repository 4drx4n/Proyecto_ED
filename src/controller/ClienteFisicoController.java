package controller;

import java.sql.*;
import model.ClienteFisico;

public class ClienteFisicoController {

	public void insertarClienteFisico(ClienteFisico c) throws SQLException {
		Connection con = Conexion.getConexion();

		try {
			// 1. Insertar en persona
			String sqlPersona = "INSERT INTO persona (dni, correo, nombre, apellidos, direccion, telefono, rol) VALUES (?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement persona = con.prepareStatement(sqlPersona, PreparedStatement.RETURN_GENERATED_KEYS);
			persona.setString(1, c.getDni());
			persona.setString(2, c.getCorreo());
			persona.setString(3, c.getNombre());
			persona.setString(4, c.getApellidos());
			persona.setString(5, c.getDireccion());
			persona.setInt(6, c.getTelefono());
			persona.setString(7, c.getRol());

			int filasPersona = persona.executeUpdate();
			if (filasPersona == 0) {
				throw new SQLException("No se pudo insertar en la tabla persona.");
			}

			ResultSet rs = persona.getGeneratedKeys();
			if (!rs.next()) {
				throw new SQLException("No se pudo recuperar el ID de usuario.");
			}

			int idUsuario = rs.getInt(1);

			// 2. Insertar en cliente
			String sqlCliente = "INSERT INTO cliente (id_usuario, fecha_alta) VALUES (?, ?)";
			PreparedStatement cliente = con.prepareStatement(sqlCliente, PreparedStatement.RETURN_GENERATED_KEYS);
			cliente.setInt(1, idUsuario);
			cliente.setDate(2, new Date(c.getFecha_alta().getTime()));

			int filasCliente = cliente.executeUpdate();
			if (filasCliente == 0) {
				throw new SQLException("No se pudo insertar en la tabla cliente.");
			}

			ResultSet rsCliente = cliente.getGeneratedKeys();
			if (!rsCliente.next()) {
				throw new SQLException("No se pudo recuperar el ID del cliente.");
			}

			// 3. Insertar en cliente_fisico
			String sqlFisico = "INSERT INTO cliente_fisico (id_cliente, puntos_establecimiento) VALUES (?, ?)";
			PreparedStatement fisico = con.prepareStatement(sqlFisico);
			fisico.setInt(1, rsCliente.getInt(1));
			fisico.setInt(2, c.getPuntos_establecimiento());
			fisico.executeUpdate();

		} catch (SQLException e) {
			System.err.println("Error al insertar cliente físico: " + e.getMessage());
			throw e;
		} finally {
			con.close();
		}
	}

	public Integer selectLoginClienteFisico(String dni, String correo) throws SQLException {

		String sql = """
				  SELECT cf.puntos_establecimiento
				  FROM persona p
				  JOIN cliente c  ON p.id_usuario = c.id_usuario
				  JOIN cliente_fisico cf ON c.id_cliente = cf.id_cliente
				  WHERE p.dni    = ?
				    AND p.correo = ?
				""";

		try (Connection con = Conexion.getConexion();
				PreparedStatement stmt = con.prepareStatement(sql)) {

			stmt.setString(1, dni);
			stmt.setString(2, correo);

			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					return rs.getInt("puntos_establecimiento");
				} else {
					return null;  // cliente no encontrado
				}
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
				} else {
					return 0;
				}
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

