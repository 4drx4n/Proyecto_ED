package controller;

import java.sql.*;
import model.ClienteFisico;
import util.ValidadorClienteFisico;

/**
 * Controlador de acceso a datos para clientes físicos.
 * Gestiona operaciones como el registro, login, obtención y actualización
 * de puntos en la base de datos.
 *
 * Forma parte central del flujo de compra y registro de clientes físicos.
 */
public class ClienteFisicoController {

	private final ValidadorClienteFisico validador = new ValidadorClienteFisico();

	/**
	 * Inserta un nuevo cliente físico en la base de datos.
	 *
	 * @param c Cliente físico a registrar.
	 * @throws SQLException si ocurre un error al realizar la inserción.
	 */
	public void insertarClienteFisico(ClienteFisico c) throws SQLException {
		try (Connection con = Conexion.getConexion()) {
			insertarClienteFisico(con, c);
		}
	}

	/**
	 * UTIL para testear.
	 * Inserta un cliente físico en la base de datos utilizando una conexión existente.
	 *
	 * @param con Conexión abierta a la base de datos.
	 * @param c Cliente físico a insertar.
	 * @throws SQLException si ocurre un error durante la inserción.
	 */
	public void insertarClienteFisico(Connection con, ClienteFisico c) throws SQLException {
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

	/**
	 * Busca un cliente físico en la base de datos según DNI y correo.
	 *
	 * @param dni DNI del cliente.
	 * @param correo Correo electrónico del cliente.
	 * @return Cliente físico encontrado o null si no existe.
	 * @throws SQLException si ocurre un error al acceder a la base de datos.
	 */
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

	/**
	 * Devuelve los puntos de establecimiento de un cliente dado su DNI.
	 *
	 * @param dni DNI del cliente.
	 * @return Número de puntos actuales.
	 * @throws SQLException si ocurre un error de acceso a base de datos.
	 */
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

	/**
	 * Actualiza los puntos del cliente en la base de datos.
	 *
	 * @param dni DNI del cliente.
	 * @param cantidad Cantidad de puntos a sumar o restar.
	 * @param sumar true para sumar puntos, false para restarlos.
	 * @throws SQLException si ocurre un error al actualizar.
	 */
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
