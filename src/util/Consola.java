package util;

public class Consola {

	public Consola() {

	}

	public void mostrarMensaje(String mensaje) {
		System.out.println(mensaje);
	}
	
	public void mostrarError(String mensaje) {
		System.err.println(mensaje);
	}
}
