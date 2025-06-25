package controller;

import java.sql.*;
import java.util.ArrayList;
import model.Producto;

public class ProductoController {

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
