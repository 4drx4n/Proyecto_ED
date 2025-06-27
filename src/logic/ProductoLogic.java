package logic;

import java.sql.SQLException;
import java.util.ArrayList;
import controller.ProductoController;
import exception.ListaVaciaException;
import model.Producto;
import util.Consola;

/**
 * Lógica de negocio relacionada con la gestión de productos.
 * Permite obtener productos, listarlos y actualizar su stock.
 */
public class ProductoLogic {

	private final ProductoController productoCon = new ProductoController();
	private final Consola c = new Consola();

	/**
	 * Devuelve un producto según su ID.
	 *
	 * @param idProducto ID del producto.
	 * @return Producto correspondiente o null si ocurre un error.
	 */
	public Producto obtenerProductoPorId(int idProducto) {
		try {
			return productoCon.selectProductoId(idProducto);
		} catch (SQLException e) {
			c.mostrarMensaje("Error al obtener producto por ID: " + e.getMessage());
			return null;
		}
	}

	/**
	 * Devuelve una lista de productos disponibles.
	 *
	 * @return Lista de productos o vacía si ocurre un error.
	 */
	public ArrayList<Producto> listarProductos() {
		try {
			return productoCon.selectProductosDisponibles();
		} catch (SQLException e) {
			System.err.println("Error al obtener productos: " + e.getMessage());
			return new ArrayList<>();
		}
	}

	/**
	 * Muestra por consola todos los productos disponibles.
	 *
	 * @throws ListaVaciaException si no hay productos para listar.
	 */
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


	/**
	 * Busca un producto por nombre (ignorando mayúsculas y espacios).
	 *
	 * @param nombreBuscado Nombre del producto.
	 * @return Producto encontrado o null si no existe.
	 */
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

	/**
	 * Disminuye el stock del producto indicado en la base de datos y en memoria.
	 *
	 * @param p Producto a actualizar.
	 * @param cantidad Cantidad a restar.
	 */
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