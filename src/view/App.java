package view;

import java.sql.SQLException;
import java.util.Scanner;

import exception.ListaVaciaException;

public class App {

	public static void main(String[] args) throws SQLException, ListaVaciaException {
		Menu m = new Menu();
		Scanner sc = new Scanner(System.in);

		m.menu_tienda();
		sc.close();
	}
}
