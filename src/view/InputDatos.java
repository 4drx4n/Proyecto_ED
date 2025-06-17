package view;

import java.util.Date;
import java.util.Scanner;

import model.ClienteFisico;
import util.Consola;

public class InputDatos {

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
