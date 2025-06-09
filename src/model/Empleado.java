package model;

import java.util.Date;

public class Empleado extends Persona {

	private int cod_empleado;
	private int seguridad_social;
	private Date fecha_contrato;

	public Empleado() {
		super();
	}

	public Empleado(int cod_empleado, int seguridad_social,Date fecha_contrato,int id_usuario, String dni, String correo, String nombre, String apellidos, String direccion, int telefono, String rol) {
		super(id_usuario, dni, correo, nombre, apellidos, direccion, telefono, rol);
		this.cod_empleado = cod_empleado;
		this.seguridad_social = seguridad_social;
		this.fecha_contrato = fecha_contrato;
	}
	

	public int getCod_empleado() {
		return cod_empleado;
	}

	public void setCod_empleado(int cod_empleado) {
		this.cod_empleado = cod_empleado;
	}

	public int getSeguridad_social() {
		return seguridad_social;
	}

	public void setSeguridad_social(int seguridad_social) {
		this.seguridad_social = seguridad_social;
	}

	public Date getFecha_contrato() {
		return fecha_contrato;
	}

	public void setFecha_contrato(Date fecha_contrato) {
		this.fecha_contrato = fecha_contrato;
	}
	
}
