package model;

public class PagoEfectivo extends Pago {

	private double cambio;

	public PagoEfectivo(double total) {
		super(total);
		this.cambio = 0;
	}

	public double getCambio() {
		return cambio;
	}

	public void setCambio(double cambio) {
		this.cambio = cambio;
	}
}
