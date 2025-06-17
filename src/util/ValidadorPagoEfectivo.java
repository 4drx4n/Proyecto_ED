package util;

public class ValidadorPagoEfectivo {

	public boolean validarPagoEfectivo(double pagoCliente, double total) {
		return pagoCliente >= total;
	}
}
