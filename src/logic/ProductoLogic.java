package logic;

import java.sql.SQLException;
import java.util.ArrayList;
import controller.ProductoController;
import exception.ListaVaciaException;
import model.Producto;
import util.Consola;

public class ProductoLogic {

	private final ProductoController productoCon = new ProductoController();
	private final Consola c = new Consola();

	public Producto obtenerProductoPorId(int idProducto) {
		try {
			return productoCon.selectProductoId(idProducto);
		} catch (SQLException e) {
			c.mostrarMensaje("Error al obtener producto por ID: " + e.getMessage());
			return null;
		}
	}

	public ArrayList<Producto> listarProductos() {
		try {
			return productoCon.selectProductosDisponibles();
		} catch (SQLException e) {
			System.err.println("Error al obtener productos: " + e.getMessage());
			return new ArrayList<>();
		}
	}

	public void mostrarProductos() throws ListaVaciaException {
		c.mostrarMensaje("--- Productos disponibles ---");
		ArrayList<Producto> lista = listarProductos();
		if (lista.isEmpty()) {
			throw new ListaVaciaException("No hay productos disponibles que comprar, se aborta la operación.");
		} else {
			int i = 1;
			for (Producto p : lista) {
				c.mostrarMensaje(
						i++ + ". " +
								p.getNombre() +
								" — " + p.getPrecio() + "€"
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

	public void disminuirStock(Producto p, int cantidad) {
		int nuevoStock = p.getStock() - cantidad;
		p.setStock(nuevoStock);
		try {
			productoCon.disminuirStock(p.getNombre(), cantidad);
		} catch (SQLException e) {
			c.mostrarMensaje("Error al actualizar el stock en BD: " + e.getMessage());
		}
	}

}