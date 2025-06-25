package logic;

import java.sql.SQLException;
import java.util.Scanner;
import model.ClienteVirtual;
import util.Consola;
import view.SelectDatos;
import controller.ClienteVirtualController;

public class ClienteVirtualLogic {

	private Consola c = new Consola();
	private SelectDatos selectDat = new SelectDatos();
	private ClienteVirtualController clienteVirCon = new ClienteVirtualController();
	
	public ClienteVirtualLogic() {
		
	}
	
	public ClienteVirtualLogic(Consola c, SelectDatos selectDat, ClienteVirtualController clienteVirCon) {
        this.c = c;
        this.selectDat = selectDat;
        this.clienteVirCon = clienteVirCon;
    }

	public void loginVirtual(Scanner sc) {
		c.mostrarMensaje("--- Login Cliente ---");
		boolean clienteLogueado = false;

		while (!clienteLogueado) {
			ClienteVirtual cv = selectDat.datos_virtual(sc, c);
			try {
				boolean ok = clienteVirCon.selectClienteVirtual(cv.getCorreo(), cv.getPassword());
				if (ok) {
					c.mostrarMensaje("Login correcto. ¡Bienvenido!");
					clienteLogueado = true;
				} else {
					c.mostrarMensaje("Correo o contraseña incorrectos. Inténtalo de nuevo.");
				}
			} catch (SQLException e) {
				c.mostrarMensaje("Error al acceder a la base de datos: " + e.getMessage());
				break;
			}
		}
	}
}
