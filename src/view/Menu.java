package view;

import logic.*;
import java.util.Scanner;

import exception.ListaVaciaException;
import util.Consola;
import java.sql.SQLException;

/**
 * Clase que representa el menú principal de la aplicación.
 * Orquesta los distintos flujos de interacción con el usuario: compra, venta,
 * login, registro y navegación básica.
 * 
 * Es el núcleo del flujo lógico en la capa de vista.
 */

public class Menu {

	private Scanner sc = new Scanner (System.in);
	private Consola c = new Consola();
	private ClienteFisicoLogic clienteFisLog = new ClienteFisicoLogic();
	private ClienteVirtualLogic clienteVirLog = new ClienteVirtualLogic();
	private ProductoLogic productoLog = new ProductoLogic();
	private SelectDatos selectDat = new SelectDatos();

	boolean salir = false;

	/**
	 * Constructor por defecto. Inicializa el menú con los componentes predeterminados.
	 */
	public Menu() {

	}

	/**
	 * Constructor con parámetros para inyección de dependencias.
	 * 
	 * @param sc Scanner para entrada de datos.
	 * @param c Consola para salida de mensajes.
	 * @param clienteFisLog Lógica del cliente físico.
	 * @param clienteVirLog Lógica del cliente virtual.
	 * @param productoLog Lógica de productos.
	 * @param selectDat Componente para capturar datos del usuario.
	 */
	public Menu(Scanner sc,Consola c, ClienteFisicoLogic clienteFisLog, 
			ClienteVirtualLogic clienteVirLog,ProductoLogic productoLog,SelectDatos selectDat) {
		this.sc             = sc;
		this.c              = c;
		this.clienteFisLog  = clienteFisLog;
		this.clienteVirLog  = clienteVirLog;
		this.productoLog    = productoLog;
		this.selectDat      = selectDat;
	}

	/**
	 * Muestra el menú principal de la tienda y gestiona la navegación de la misma.
	 *
	 * @throws SQLException         Si ocurre un error al acceder a la base de datos.
	 * @throws ListaVaciaException  Si el listado de productos está vacío y no se puede comprar.
	 */
	public void menu_tienda() throws SQLException, ListaVaciaException {
		int opcionBienvenida = 0;
		do {
			c.mostrarMensaje("Bienvenido a info-shop\n¿Qué va a realizar?\n1.Compra\n2.Venta\n3.Salir");

			opcionBienvenida = sc.nextInt();
			sc.nextLine(); // limpiar buffer

			switch (opcionBienvenida) {
			case 1:
				menu_compra();
				break;
			case 2:
				menu_venta();
				break;
			case 3:
				salir = true;
				break;
			default:
				c.mostrarMensaje("Esa opción no está contemplada. Introduzca una opción válida");
			}
		} while (!salir);
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

	/**
	 * Muestra el menú de venta, preguntando si el usuario está registrado,
	 * y dirige al flujo de login o de registro según su respuesta.
	 *
	 * @throws SQLException         Si ocurre un error al acceder a la base de datos
	 *                               durante el registro de un nuevo usuario.
	 * @throws ListaVaciaException  Si la lista de productos está vacía.
	 * @throws IllegalArgumentException
	 *                               Si la opción introducida no es 1 ("Sí") ni 2 ("No").
	 */
	public void menu_venta() throws SQLException, ListaVaciaException {
		c.mostrarMensaje("El usuario que compra, ¿Está registrado?\n1.Sí\n2.No");

		int opcionVenta = sc.nextInt();
		sc.nextLine(); // limpiar buffer

		switch (opcionVenta) {
		case 1:
			venta_login();
			break;
		case 2:
			venta_registro();
			break;
		default:
			throw new IllegalArgumentException("Opción inesperada: " + opcionVenta);
		}
	}


	/**
	 * Realiza el proceso de venta para un cliente ya registrado:
	 *   1. Valida credenciales mediante login.
	 *   2. Muestra catálogo de productos.
	 *   3. Ejecuta la venta física.
	 *
	 * @throws ListaVaciaException  Si la lista de productos está vacía.
	 */
	public void venta_login() throws ListaVaciaException {
		clienteFisLog.loginFisico(sc, selectDat);
		productoLog.mostrarProductos();
		clienteFisLog.ventaFisica(sc);
	}


	/**
	 * Realiza el flujo de venta para un cliente no registrado:
	 * primero solicita el registro, luego hace login y continúa con la compra.
	 *
	 * @throws SQLException si ocurre un error durante el registro o login.
	 * @throws ListaVaciaException si no hay productos disponibles o hay falta de stock.
	 */
	public void venta_registro() throws SQLException, ListaVaciaException {
		clienteFisLog.RegistroUsuarioFisico(sc);
		clienteFisLog.loginFisico(sc, selectDat);
		productoLog.mostrarProductos();
		clienteFisLog.ventaFisica(sc);
	}


}
