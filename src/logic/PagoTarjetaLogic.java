package logic;

import model.PagoTarjeta;
import util.Consola;
import util.ValidadorPagoTarjeta;
import java.util.Scanner;


public class PagoTarjetaLogic implements PagoLogic<PagoTarjeta> {

	private final Consola c = new Consola();
	private final ValidadorPagoTarjeta vpt = new ValidadorPagoTarjeta();

	@Override
	public boolean procesarPago(Scanner sc, PagoTarjeta pago) {
		
		String tarjeta;
		String caducidad;
		String cvv;
		String marca;

		c.mostrarMensaje("El importe a pagar es: " + pago.getTotal() + "€");

		do {
			c.mostrarMensaje("Introduce el número de tarjeta con espacios:");
			tarjeta = sc.nextLine().trim();

			c.mostrarMensaje("Introduce la fecha de caducidad (MM/yy):");
			caducidad = sc.nextLine().trim();

			c.mostrarMensaje("Introduce el código CVV:");
			cvv = sc.nextLine().trim();

			marca = vpt.validarPagoTarjeta(tarjeta, caducidad, cvv);
			if (marca == null) {
				c.mostrarMensaje("Datos de tarjeta incorrectos, asegúrate y vuelve a introducirlos.");
			}
		} while (marca == null);

		c.mostrarMensaje("Tarjeta " + marca + " validada correctamente.");
		c.mostrarMensaje("Procesando pago con tarjeta…");
		c.mostrarMensaje("Pago con tarjeta procesado correctamente.");
		return true;
	}

}
