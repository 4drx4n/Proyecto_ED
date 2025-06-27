// Archivo: logic/PagoLogic.java
package logic;

import java.util.Scanner;

/**
 * Interfaz genérica que define el contrato para la lógica de procesamiento
 * de pagos. Se implementa en clases como {@code PagoEfectivoLogic} y {@code PagoTarjetaLogic}.
 *
 * @param <P> Tipo de objeto de pago (por ejemplo, PagoEfectivo o PagoTarjeta)
 */
public interface PagoLogic<P> {
	/**
	 * Procesa el pago de un objeto P.
	 * @param sc  Scanner para leer datos del usuario
	 * @param pago  objeto de pago
	 * @return true si el pago fue exitoso
	 */
	boolean procesarPago(Scanner sc, P pago);
}
