package model;

/**
 * Representa un pago realizado en efectivo.
 * Incluye el cálculo del cambio a devolver al cliente.
 */
public class PagoEfectivo extends Pago {

	private double cambio;

	/**
	 * Constructor del pago en efectivo.
	 *
	 * @param total Importe total a pagar.
	 */
	public PagoEfectivo(double total) {
		super(total);
		this.cambio = 0;
	}

	/**
	 * Devuelve el cambio que se debe entregar al cliente.
	 *
	 * @return Cambio a devolver.
	 */
	public double getCambio() {
		return cambio;
	}

	/**
	 * Establece el cambio a devolver al cliente.
	 *
	 * @param cambio Monto del cambio.
	 */
	public void setCambio(double cambio) {
		this.cambio = cambio;
	}
}