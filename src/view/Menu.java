package view;

import logic.*;
import java.util.Scanner;

import exception.ListaVaciaException;
import util.Consola;
import java.sql.SQLException;


public class Menu {

	private Scanner sc = new Scanner (System.in);
	private Consola c = new Consola();
	private ClienteFisicoLogic clienteFisLog = new ClienteFisicoLogic();
	private ClienteVirtualLogic clienteVirLog = new ClienteVirtualLogic();
	private ProductoLogic productoLog = new ProductoLogic();
	private SelectDatos selectDat = new SelectDatos();

	boolean salir = false;

	public Menu() {

	}

	public Menu(Scanner sc,Consola c, ClienteFisicoLogic clienteFisLog, 
			ClienteVirtualLogic clienteVirLog,ProductoLogic productoLog,SelectDatos selectDat) {
		this.sc             = sc;
		this.c              = c;
		this.clienteFisLog  = clienteFisLog;
		this.clienteVirLog  = clienteVirLog;
		this.productoLog    = productoLog;
		this.selectDat      = selectDat;
	}

	public void menu_tienda() throws SQLException, ListaVaciaException {

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

	public void menu_venta() throws SQLException, ListaVaciaException {

		c.mostrarMensaje("El usuario que compra, ¿Está registrado?\n1.Sí\n2.No");

		int opcionVenta = sc.nextInt();
		sc.nextLine();//LIMPIARBUFFER

		switch (opcionVenta) {
		case 1: {
			venta_login();
			//salir = true;
			break;

		}case 2:{
			venta_registro();
			//salir = true;
			break;

		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + opcionVenta);
		}
	}

	public void venta_login() throws ListaVaciaException {

		clienteFisLog.loginFisico(sc, selectDat);

		productoLog.mostrarProductos();

		clienteFisLog.ventaFisica(sc);
		
	}

	public void venta_registro() throws SQLException, ListaVaciaException {

		clienteFisLog.RegistroUsuarioFisico(sc);

		clienteFisLog.loginFisico(sc, selectDat);

		productoLog.mostrarProductos();

		clienteFisLog.ventaFisica(sc);


	}

}
