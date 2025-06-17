package logic;

import model.PagoEfectivo;
import util.Consola;
import util.ValidadorPagoEfectivo;

import java.util.Scanner;

public class PagoEfectivoLogic {

	private final Consola c = new Consola();
	private final ValidadorPagoEfectivo vpe = new ValidadorPagoEfectivo();

	public boolean procesarPago(Scanner sc, PagoEfectivo pago) {
		c.mostrarMensaje("El total a pagar es: " + pago.getTotal() + "€");

		double pagoCliente;

		do {
			c.mostrarMensaje("¿Cuánto dinero entrega el cliente?");
			pagoCliente = sc.nextDouble();
			sc.nextLine(); // limpiar buffer

			if (!vpe.validarPagoEfectivo(pagoCliente, pago.getTotal())) {
				c.mostrarMensaje("El dinero entregado es insuficiente. Debe entregar al menos " + pago.getTotal() + "€.\n");
			}

		} while (!vpe.validarPagoEfectivo(pagoCliente, pago.getTotal()));

		double cambio = Math.round((pagoCliente - pago.getTotal()) * 100.0) / 100.0;
		pago.setCambio(cambio);

		c.mostrarMensaje("Pago recibido correctamente.\nCambio a devolver: \"" + cambio + "\"€");

		return true;
	}

	public void realizarPagoEfectivo(Scanner sc, double totalCompra) {
		PagoEfectivo pe = new PagoEfectivo(totalCompra);
		PagoEfectivoLogic pel = new PagoEfectivoLogic();

		boolean pagado = pel.procesarPago(sc, pe);

		if (pagado) {
			c.mostrarMensaje("Compra realizada con éxito. Gracias por su pago.");
		} else {
			c.mostrarMensaje("No se pudo completar el pago.");
		}
	}

}