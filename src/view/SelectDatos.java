package view;

import java.util.Scanner;
import model.ClienteFisico;
import model.ClienteVirtual;
import util.Consola;

public class SelectDatos {

	public ClienteVirtual datos_virtual(Scanner sc, Consola c) {
		c.mostrarMensaje("Introduce tu correo electrónico");
		String correo = sc.nextLine();

		c.mostrarMensaje("Introduce la contraseña");
		String password = sc.nextLine();

		return new ClienteVirtual (correo, password);
	}

	public ClienteFisico datos_fisico(Scanner sc, Consola c) {
		c.mostrarMensaje("Dime tu dni");
		String dni = sc.nextLine();

		c.mostrarMensaje("Dime el correo asociado a tu cuenta");
		String correo = sc.nextLine();

		return new ClienteFisico (dni, correo);

	}
}