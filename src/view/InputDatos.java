package view;

import java.util.Date;
import java.util.Scanner;

import model.ClienteFisico;
import util.Consola;

/**
 * Clase encargada de solicitar al usuario los datos necesarios
 * para registrar un cliente físico. Utiliza la consola como
 * medio de entrada/salida.
 *
 * Se utiliza durante el proceso de registro previo a la venta.
 */
public class InputDatos {

	/**
	 * Solicita al usuario los datos necesarios para crear un cliente físico,
	 * capturando entrada por consola.
	 *
	 * @param sc Scanner para entrada de datos.
	 * @param c Consola para mostrar mensajes.
	 * @return Objeto ClienteFisico con los datos introducidos.
	 */
	public ClienteFisico datos_usuario(Scanner sc, Consola c) {

		c.mostrarMensaje("Dime tu DNI:");
		String dni = sc.nextLine();

		c.mostrarMensaje("Dime tu correo:");
		String correo = sc.nextLine();

		c.mostrarMensaje("Dime tu nombre:");
		String nombre = sc.nextLine();

		c.mostrarMensaje("Dime tu apellidos:");
		String apellidos = sc.nextLine();

		c.mostrarMensaje("Dime tu dirección:");
		String direccion = sc.nextLine();

		c.mostrarMensaje("Dime tu teléfono:");
		int telefono = sc.nextInt();
		sc.nextLine(); //LIMPIAR BUFFER

		String rol = "Comprador";

		Date fecha_alta = new Date(System.currentTimeMillis());

		int puntos_establecimiento = 0;

		return new ClienteFisico(puntos_establecimiento, -1, fecha_alta, -1, dni, correo, nombre, apellidos, direccion, telefono, rol);
	}
}
