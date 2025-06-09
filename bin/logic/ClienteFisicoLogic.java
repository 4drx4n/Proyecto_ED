package logic;

import java.sql.SQLException;
import java.util.Scanner;
import controller.ClienteFisicoController;
import model.ClienteFisico;
import util.Consola;
import view.SelectDatos;

public class ClienteFisicoLogic {

	private final ClienteFisicoController cc = new ClienteFisicoController();
    private final Scanner sc = new Scanner(System.in);
    private final Consola c = new Consola();

	public void RegistroUsuarioFisico(Scanner sc, Consola c, ClienteFisico cf, ClienteFisicoController cc) throws SQLException {

		c.mostrarMensaje("\n¿Estos datos son correctos?");
		c.mostrarMensaje("DNI: " + cf.getDni());
		c.mostrarMensaje("Correo: " + cf.getCorreo());
		c.mostrarMensaje("Nombre: " + cf.getNombre());
		c.mostrarMensaje("Apellidos: " + cf.getApellidos());
		c.mostrarMensaje("Dirección: " + cf.getDireccion());
		c.mostrarMensaje("Teléfono: " + cf.getTelefono());
		c.mostrarMensaje("Rol: " + cf.getRol());
		c.mostrarMensaje("1.Si\n2.No");

		int confirmarDatos = 0;

		confirmarDatos = sc.nextInt();
		sc.nextLine(); //LIMPIAR BUFFER

		if (confirmarDatos == 1) {
			try {
				cc.insertarClienteFisico(cf);
				c.mostrarMensaje("Cliente registrado");
			} catch (SQLException e) {
				c.mostrarMensaje("Error al registrar el cliente" + e.getStackTrace());
			}
		} else if (confirmarDatos == 2) {
			c.mostrarMensaje("Registro cancelado.");
		}else {
			c.mostrarMensaje("Opción no válid, registro anulado");
		}
	}

	public void loginFisico(SelectDatos sd) {
		ClienteFisico cf = sd.datos_fisico(sc, c);
		
		String dni    = cf.getDni().trim().toUpperCase();
		String correo = cf.getCorreo().trim().toLowerCase();

		try {
			Integer puntos = cc.selectLoginClienteFisico(dni, correo);
			if (puntos != null) {
				c.mostrarMensaje("El cliente está registrado, tiene " + puntos + " puntos.");
			} else {
				c.mostrarMensaje("Cliente no encontrado");
			}
		} catch (SQLException e) {
			c.mostrarMensaje("Error al acceder a la base de datos: " + e.getMessage());
		}
	}



	public void sumar_puntos() {

	}

	public void restar_puntos() {

	}
}
