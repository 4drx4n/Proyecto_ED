package exception;

import util.Consola;

/**
 * Excepción personalizada que se lanza cuando una lista esperada está vacía
 * y se requiere que contenga elementos para continuar un proceso.
 * 
 * Al lanzarse, muestra un mensaje de error a través de la consola.
 * 
 * Esta excepción es utilizada principalmente en el flujo de venta
 * cuando no hay productos disponibles para mostrar o vender.
 * 
 */
public class ListaVaciaException extends Exception {

	private static final long serialVersionUID = -7096153568931227923L;

	Consola c = new Consola();

	/**
	 * Crea una nueva excepción indicando que la lista está vacía.
	 *
	 * @param mensaje Mensaje que será mostrado como error en consola.
	 */
	public ListaVaciaException(String mensaje) {
		c.mostrarError(mensaje);
	}
}