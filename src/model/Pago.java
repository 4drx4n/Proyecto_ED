package model;

/**
 * Clase abstracta que representa un pago realizado por un cliente.
 * Contiene el importe total a pagar.
 */
public abstract class Pago {

	private double total;

	/**
	 * Constructor del pago.
	 *
	 * @param total Importe total a pagar.
	 */
	public Pago(double total) {
		this.total = total;
	}

	/**
	 * Devuelve el importe total.
	 *
	 * @return Total a pagar.
	 */
	public double getTotal() {
		return total;
	}

	/**
	 * Establece un nuevo importe total.
	 *
	 * @param total Nuevo total.
	 */
	public void setTotal(double total) {
		this.total = total;
	}
}