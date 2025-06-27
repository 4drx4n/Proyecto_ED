package util;

/**
 * Clase auxiliar para mostrar mensajes en consola.
 * Simplifica la salida estándar y permite distinguir mensajes normales de errores.
 *
 * Se utiliza en toda la aplicación, especialmente durante el registro, login
 * y proceso de compra.
 */
public class Consola {

	public Consola() {

	}

	/**
	 * Muestra un mensaje estándar en la consola.
	 * @param mensaje Mensaje a mostrar por salida estándar.
	 */
	public void mostrarMensaje(String mensaje) {
		System.out.println(mensaje);
	}

	/**
	 * Muestra un mensaje de error en la consola.
	 * @param mensaje Mensaje a mostrar por salida de error.
	 */
	public void mostrarError(String mensaje) {
		System.err.println(mensaje);
	}
}
