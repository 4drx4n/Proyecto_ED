package view;

import java.sql.SQLException;
import java.util.Scanner;

import exception.ListaVaciaException;

/**
 * Clase principal que inicia la ejecución de la aplicación.
 * Lanza el menú principal de la tienda y gestiona el cierre del escáner.
 * 
 * Esta clase sirve como punto de entrada al programa (método main).
 * Desde aquí se accede al menú principal que permite iniciar los diferentes flujos del sistema.
 * 
 * @author Adrian Aguilar
 * @version 1.0
 */

public class App {

	/**
	 * Método principal que inicia el programa.
	 * Crea el menú de la tienda y lo ejecuta, capturando posibles excepciones SQL
	 * o de lista vacía durante los procesos de venta.
	 * 
	 * @param args Argumentos de línea de comandos (no se utilizan).
	 * @throws SQLException si ocurre un error en las operaciones con la base de datos.
	 * @throws ListaVaciaException si no hay productos disponibles en el flujo de compra.
	 */
	public static void main(String[] args) throws SQLException, ListaVaciaException {
		Menu m = new Menu();
		Scanner sc = new Scanner(System.in);

		m.menu_tienda();
		sc.close();
	}
}
