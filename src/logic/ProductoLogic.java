package logic;

import java.sql.SQLException;
import java.util.ArrayList;
import controller.ProductoController;
import model.Producto;
import util.Consola;

public class ProductoLogic {

	private final ProductoController productoCon = new ProductoController();
	private final Consola c = new Consola();


	public ArrayList<Producto> listarProductos() {
		try {
			return productoCon.selectProductosDisponibles();
		} catch (SQLException e) {
			System.err.println("Error al obtener productos: " + e.getMessage());
			return new ArrayList<>();
		}
	}

	public void mostrarProductos() {
		c.mostrarMensaje("--- Productos disponibles ---");
		ArrayList<Producto> lista = listarProductos();
		if (lista.isEmpty()) {
			c.mostrarMensaje("No hay productos disponibles.");
		} else {
			int i = 1;
			for (Producto p : lista) {
				c.mostrarMensaje(
						i++ + ". " +
								p.getNombre() +
								" — " + p.getPrecio() + "€" +
								" — Stock: " + p.getStock()
						);
			}
		}
	}

	public Producto obtenerProductoPorNombre(String nombreBuscado) {
		String productoBuscadoTrim = nombreBuscado.trim();

		for (Producto p : listarProductos()) {
			String nombreProductoLimpio = p.getNombre().trim();
			if (nombreProductoLimpio.equalsIgnoreCase(productoBuscadoTrim)) {
				return p;
			}
		}
		return null;
	}

}