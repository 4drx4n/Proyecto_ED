package view;

import logic.*;
import model.ClienteFisico;
import controller.ClienteFisicoController;
import java.util.Scanner;
import util.Consola;
import java.sql.SQLException;


public class Menu {
	
	private final Scanner sc = new Scanner (System.in);
	private final Consola c = new Consola();
	private final ClienteFisicoLogic clienteFisLog = new ClienteFisicoLogic();
	private final ClienteVirtualLogic clienteVirLog = new ClienteVirtualLogic();
	private final InputDatos inputDat = new InputDatos();
	private final SelectDatos selectDat = new SelectDatos();
	private final ClienteFisicoController clienteFisCon = new ClienteFisicoController();
	private final ProductoLogic productoLog = new ProductoLogic();

	public Menu() {

	}

	public void menu_tienda(Scanner sc) throws SQLException {

		boolean salir = false;
		int opcionBienvenida = 0;
		do {
			c.mostrarMensaje("Bienvenido a info-shop\n¿Qué va a realizar?\n1.Compra\n2.Venta");

			opcionBienvenida = sc.nextInt();
			sc.nextLine(); //LIMPIARBUFFER

			switch (opcionBienvenida) {
			case 1: {
				menu_compra();

			}case 2: {
				menu_venta();
				break;

			}default:
				throw new IllegalArgumentException("Unexpected value: " + opcionBienvenida);
			}

		}while (!salir);
	}

	public void menu_compra() {

		c.mostrarMensaje("Ha seleccionado compra.\n \n¿Está registrado?\n1.Sí\n2.No");
		int opcionRegistro = sc.nextInt();
		sc.nextLine();//LIMPIARBUFFER

		switch (opcionRegistro) {
		case 1: {
			compra_login();
			break;

		}case 2:{
			// Sin implementar compra_registro();
			break;

		}default:
			throw new IllegalArgumentException("Unexpected value: " + opcionRegistro);
		}
	}

	public void compra_login() {
		c.mostrarMensaje("--- Login Cliente ---");
		clienteVirLog.loginVirtual();

	}

	public void compra_registro() {
		/*Se queda por si se diera la futura implementación,
		 *se registraría el mismo usuario virtual
		 */
	}

	public void menu_venta() throws SQLException {

		c.mostrarMensaje("El usuario que compra, ¿Está registrado?\n1.Sí\n2.No");

		int opcionVenta = sc.nextInt();
		sc.nextLine();//LIMPIARBUFFER

		switch (opcionVenta) {
		case 1: {
			venta_login();
			break;
			
		}case 2:{
			venta_registro();
			break;
			
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + opcionVenta);
		}
	}

	public void venta_login() {
		c.mostrarMensaje("--- Login Cliente Físico ---");

		clienteFisLog.loginFisico(selectDat);
	}

	public void venta_registro() throws SQLException {

		c.mostrarMensaje("Si no está registrado, necesitamos que se registre.");
		c.mostrarMensaje("--- Registro de Cliente ---");
		
		ClienteFisico cf = inputDat.datos_usuario(sc, c);

		clienteFisLog.RegistroUsuarioFisico(sc, c, cf, clienteFisCon);
		
		productoLog.mostrarProductos();
	}
	


}
