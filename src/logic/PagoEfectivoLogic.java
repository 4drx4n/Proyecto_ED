package logic;

import model.PagoEfectivo;
import util.Consola;
import util.ValidadorPagoEfectivo;
import java.util.Scanner;

/**
 * Lógica de pago mediante efectivo.
 * Verifica que el cliente entregue suficiente dinero y calcula el cambio.
 */
public class PagoEfectivoLogic implements PagoLogic<PagoEfectivo> {

	private final Consola c = new Consola();
	private final ValidadorPagoEfectivo vpe = new ValidadorPagoEfectivo();

	/**
	 * Procesa el pago en efectivo, solicitando al cliente la cantidad entregada
	 * y validando que sea suficiente. Calcula y muestra el cambio.
	 *
	 * @param sc Scanner para la entrada del usuario.
	 * @param pago Objeto que contiene el total a pagar.
	 * @return true si el pago se ha realizado correctamente.
	 */
	@Override
	public boolean procesarPago(Scanner sc, PagoEfectivo pago) {
		c.mostrarMensaje("El total a pagar es: " + pago.getTotal() + "€");

		double pagoCliente;
		do {
			c.mostrarMensaje("¿Cuánto dinero entrega el cliente?");
			pagoCliente = sc.nextDouble();
			sc.nextLine(); // limpiar buffer

			if (!vpe.validarPagoEfectivo(pagoCliente, pago.getTotal())) {
				c.mostrarMensaje(
						"El dinero entregado es insuficiente. Debe entregar al menos " 
								+ pago.getTotal() + "€.\n"
						);
			}
		} while (!vpe.validarPagoEfectivo(pagoCliente, pago.getTotal()));

		double cambio = Math.round((pagoCliente - pago.getTotal()) * 100.0) / 100.0;
		pago.setCambio(cambio);

		c.mostrarMensaje("Pago recibido correctamente.\nCambio a devolver: " 
				+ cambio + "€");
		return true;
	}
}
