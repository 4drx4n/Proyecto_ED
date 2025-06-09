package view;

import java.sql.SQLException;
import java.util.Scanner;

public class App {

	public static void main(String[] args) throws SQLException {
		Menu m = new Menu();
		Scanner sc = new Scanner(System.in);
		
		m.menu_tienda(sc);
		sc.close();
	}
}
