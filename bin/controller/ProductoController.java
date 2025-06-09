package controller;

import java.sql.*;
import java.util.ArrayList;
import model.Producto;

public class ProductoController {

	public ArrayList<Producto> selectProductosDisponibles() throws SQLException {
		String sql = """
				SELECT nombre,
				       precio,
				       stock
				  FROM producto
				 WHERE stock > 0
				""";

		ArrayList<Producto> productos = new ArrayList<>();
		try (Connection con = Conexion.getConexion();
				PreparedStatement stmt = con.prepareStatement(sql);
				ResultSet rs = stmt.executeQuery()) {

			while (rs.next()) {
				productos.add(new Producto(rs.getString("nombre"), rs.getDouble("precio"), rs.getInt("stock")));
			}
		}
		return productos;
	}
}
