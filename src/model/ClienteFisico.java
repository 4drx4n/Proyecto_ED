package model;

import java.util.Date;

public class ClienteFisico extends Cliente {

	private int puntos_establecimiento;

	public ClienteFisico() {
		super();
	}

	public ClienteFisico(int puntos_establecimiento, int id_cliente, Date fecha_alta,int id_usuario, String dni, String correo, String nombre, String apellidos,
			String direccion, int telefono, String rol) {
		super(id_cliente, fecha_alta, id_usuario, dni, correo, nombre, apellidos, direccion, telefono, rol);
		this.puntos_establecimiento = puntos_establecimiento;
	}

	public ClienteFisico(String dni, String correo) {
		super();
		this.setDni(dni);
		this.setCorreo(correo);
	}

	public int getPuntos_establecimiento() {
		return puntos_establecimiento;
	}

	public void setPuntos_establecimiento(int puntos_establecimiento) {
		this.puntos_establecimiento = puntos_establecimiento;
	}
	
	
}
