package view;

import java.util.Scanner;
import model.ClienteFisico;
import model.ClienteVirtual;
import util.Consola;

/**
 * Clase encargada de solicitar datos básicos para el login de clientes.
 * Permite obtener la información de un cliente físico o virtual mediante consola.
 *
 * En el flujo de compra, se utiliza en la autenticación previa a la venta.
 */
public class SelectDatos {

	public ClienteVirtual datos_virtual(Scanner sc, Consola c) {
		c.mostrarMensaje("Introduce tu correo electrónico");
		String correo = sc.nextLine();

		c.mostrarMensaje("Introduce la contraseña");
		String password = sc.nextLine();

		return new ClienteVirtual (correo, password);
	}

	/**
	 * Solicita los datos de acceso de un cliente físico.
	 * @param sc Scanner para entrada del usuario.
	 * @param c Consola para mostrar mensajes.
	 * @return ClienteFisico con los datos introducidos.
	 */
	public ClienteFisico datos_fisico(Scanner sc, Consola c) {
		c.mostrarMensaje("Dime tu dni");
		String dni = sc.nextLine();

		c.mostrarMensaje("Dime el correo asociado a tu cuenta");
		String correo = sc.nextLine();

		return new ClienteFisico (dni, correo);

	}
}