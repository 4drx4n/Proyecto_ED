package controller;

import model.Pedido;
import java.sql.*;
import java.util.Map;

/**
 * Controlador que gestiona la persistencia de pedidos en la base de datos.
 * Inserta tanto el pedido como sus líneas de detalle (productos y cantidades).
 *
 * Es el componente final del flujo de compra, responsable de registrar la venta.
 */
public class PedidoController {

	/**
	 * Crea una nueva instancia del controlador de pedidos.
	 */
	public PedidoController() {
		// Constructor por defecto
	}


	/**
	 * Inserta un nuevo pedido en la base de datos.
	 *
	 * @param p Pedido a guardar.
	 * @throws SQLException si ocurre un error durante la inserción.
	 */
	public void insertarPedido(Pedido p) throws SQLException {
		String sqlPedido = "INSERT INTO pedido (id_cliente, fecha, total) VALUES (?, ?, ?)";
		String sqlLinea = "INSERT INTO detalle_pedido (pedido_id, id_producto, cantidad) VALUES (?, ?, ?)";

		try (Connection con = Conexion.getConexion();
				PreparedStatement ps = con.prepareStatement(sqlPedido, PreparedStatement.RETURN_GENERATED_KEYS)) {

			// Insertar pedido
			ps.setInt(1, p.getIdCliente());
			ps.setTimestamp(2, Timestamp.valueOf(p.getFecha()));
			ps.setDouble(3, p.getTotal());

			int filas = ps.executeUpdate();
			if (filas == 0) throw new SQLException("No se pudo insertar el pedido.");

			try (ResultSet rs = ps.getGeneratedKeys()) {
				if (!rs.next()) throw new SQLException("No se pudo obtener el ID del pedido.");
				int idPedido = rs.getInt(1);

				// Insertar líneas del pedido
				try (PreparedStatement psLinea = con.prepareStatement(sqlLinea)) {
					for (Map.Entry<Integer, Integer> entry : p.getDetalles().entrySet()) {
						psLinea.setInt(1, idPedido);
						psLinea.setInt(2, entry.getKey());
						psLinea.setInt(3, entry.getValue());
						psLinea.addBatch();
					}
					psLinea.executeBatch();
				}
			}
		}
	}
}
