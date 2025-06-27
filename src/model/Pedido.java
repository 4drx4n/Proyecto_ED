package model;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * Representa un pedido realizado por un cliente, incluyendo fecha,
 * total y los productos adquiridos con sus respectivas cantidades.
 * 
 * Cada pedido contiene:
 * ID del cliente que lo realiza.
 * Fecha y hora del pedido.
 * Importe total.
 * Detalles del carrito (producto y cantidad).
 */
public class Pedido {

	private int idCliente;
	private LocalDateTime fecha;
	private double total;
	private Map<Integer, Integer> detalles; // id_producto / cantidad

	/**
	 * Constructor del pedido.
	 * 
	 * @param idCliente ID del cliente que realiza el pedido.
	 * @param fecha Fecha y hora en que se realiza el pedido.
	 * @param total Monto total del pedido.
	 * @param detalles Mapa con ID del producto y cantidad pedida.
	 */
	public Pedido(int idCliente, LocalDateTime fecha, double total, Map<Integer, Integer> detalles) {
		this.idCliente = idCliente;
		this.fecha = fecha;
		this.total = total;
		this.detalles = detalles;
	}

	/**
	 * Obtiene el ID del cliente.
	 * @return ID del cliente.
	 */
	public int getIdCliente() {
		return idCliente;
	}

	/**
	 * Establece el ID del cliente.
	 * @param idCliente Nuevo ID del cliente.
	 */
	public void setIdCliente(int idCliente) {
		this.idCliente = idCliente;
	}

	/**
	 * Obtiene la fecha del pedido.
	 * @return Fecha y hora del pedido.
	 */
	public LocalDateTime getFecha() {
		return fecha;
	}

	/**
	 * Establece la fecha del pedido.
	 * @param fecha Nueva fecha del pedido.
	 */
	public void setFecha(LocalDateTime fecha) {
		this.fecha = fecha;
	}

	/**
	 * Obtiene el total del pedido.
	 * @return Monto total.
	 */
	public double getTotal() {
		return total;
	}

	/**
	 * Establece el total del pedido.
	 * @param total Nuevo monto total.
	 */
	public void setTotal(double total) {
		this.total = total;
	}

	/**
	 * Obtiene los detalles del pedido.
	 * @return Mapa de ID de producto y cantidad.
	 */
	public Map<Integer, Integer> getDetalles() {
		return detalles;
	}

	/**
	 * Establece los detalles del pedido.
	 * @param detalles Nuevo mapa de productos y cantidades.
	 */
	public void setDetalles(Map<Integer, Integer> detalles) {
		this.detalles = detalles;
	}
}
