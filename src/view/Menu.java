package view;

import logic.*;
import java.util.Scanner;
import util.Consola;
import java.sql.SQLException;


public class Menu {

	private final Scanner sc = new Scanner (System.in);
	private final Consola c = new Consola();
	private final ClienteFisicoLogic clienteFisLog = new ClienteFisicoLogic();
	private final ClienteVirtualLogic clienteVirLog = new ClienteVirtualLogic();
	private final ProductoLogic productoLog = new ProductoLogic();
	private final SelectDatos selectDat = new SelectDatos();



	public Menu() {

	}

	public void menu_tienda() throws SQLException {
		boolean salir = false;
		int opcionBienvenida = 0;
		do {
			c.mostrarMensaje("Bienvenido a info-shop\n¿Qué va a realizar?\n1.Compra\n2.Venta\n3.Salir");

			opcionBienvenida = sc.nextInt();
			sc.nextLine(); //LIMPIARBUFFER

			switch (opcionBienvenida) {
			case 1: {
				menu_compra();
				break;
			}case 2: {
				menu_venta();
				break;

			}case 3:{
				salir = true;
				break;
			}
			default:
				c.mostrarMensaje("Esa opción no está contemplada. Introduzca una opción válida");
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
		clienteVirLog.loginVirtual(sc);

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

		clienteFisLog.loginFisico(sc, selectDat);

		productoLog.mostrarProductos();

		clienteFisLog.ventaFisica(sc);
	}

	public void venta_registro() throws SQLException {

		clienteFisLog.RegistroUsuarioFisico(sc);

		productoLog.mostrarProductos();


	}



}
