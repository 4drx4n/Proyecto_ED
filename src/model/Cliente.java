package model;

import java.util.Date;

public abstract class Cliente extends Persona {

	private int id_cliente;
	private Date fecha_alta;
	
	
	public Cliente() {
		super();
	}
	public Cliente(int id_cliente, Date fecha_alta, int id_usuario, String dni, String correo, String nombre, String apellidos, String direccion, int telefono,String rol) {
		super(id_usuario, dni, correo, nombre, apellidos, direccion, telefono, rol);
		this.id_cliente = id_cliente;
		this.fecha_alta = fecha_alta;
	}
	public int getId_cliente() {
		return id_cliente;
	}
	public void setId_cliente(int id_cliente) {
		this.id_cliente = id_cliente;
	}
	public Date getFecha_alta() {
		return fecha_alta;
	}
	public void setFecha_alta(Date fecha_alta) {
		this.fecha_alta = fecha_alta;
	}
	
}
