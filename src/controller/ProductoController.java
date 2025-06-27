package controller;

import java.sql.*;
import java.util.ArrayList;
import model.Producto;

/**
 * Controlador encargado del acceso a datos de productos.
 * Permite consultar los productos disponibles, buscar por ID
 * y actualizar el stock tras una venta.
 *
 * Se utiliza durante el proceso de selección de productos y
 * confirmación del pedido en el flujo de compra.
 */
public class ProductoController {

	/**
	 * Devuelve una lista de productos que tienen stock disponible.
	 *
	 * @return Lista de productos disponibles.
	 * @throws SQLException si ocurre un error al acceder a la base de datos.
	 */
	public ArrayList<Producto> selectProductosDisponibles() throws SQLException {
		String sql = """
				SELECT id_producto, nombre, precio, stock, categoria
				  FROM producto
				 WHERE stock >= 0
				""";

		ArrayList<Producto> productosSelect = new ArrayList<>();
		try (Connection con = Conexion.getConexion();
				PreparedStatement stmt = con.prepareStatement(sql);
				ResultSet rs = stmt.executeQuery()) {

			while (rs.next()) {
				productosSelect.add(new Producto(
						rs.getInt("id_producto"),
						rs.getString("nombre"),
						rs.getDouble("precio"),
						rs.getInt("stock"),
						rs.getString("categoria")
						));
			}
		}
		return productosSelect;
	}

	/**
	 * Devuelve un producto específico por su ID.
	 *
	 * @param idProducto ID del producto.
	 * @return Producto correspondiente o null si no se encuentra.
	 * @throws SQLException si ocurre un error de consulta.
	 */
	public Producto selectProductoId(int idProducto) throws SQLException {
		String sql = """
				SELECT id_producto, nombre, precio, stock, categoria
				  FROM producto
				 WHERE id_producto = ?
				""";

		try (Connection con = Conexion.getConexion();
				PreparedStatement stmt = con.prepareStatement(sql)) {

			stmt.setInt(1, idProducto);

			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					return new Producto(
							rs.getInt("id_producto"),
							rs.getString("nombre"),
							rs.getDouble("precio"),
							rs.getInt("stock"),
							rs.getString("categoria")
							);
				} else {
					return null;
				}
			}
		}
	}

	/**
	 * Disminuye el stock del producto indicado en la base de datos.
	 *
	 * @param nombre Nombre del producto.
	 * @param cantidad Cantidad a restar.
	 * @throws SQLException si ocurre un error en la actualización.
	 */
	public void disminuirStock(String nombre, int cantidad) throws SQLException {
		String sql = "UPDATE producto SET stock = stock - ? WHERE nombre = ?";
		try (Connection con = Conexion.getConexion();
				PreparedStatement stmt = con.prepareStatement(sql)) {
			stmt.setInt(1, cantidad);
			stmt.setString(2, nombre);
			stmt.executeUpdate();
		}
	}
}
