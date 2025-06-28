package model;

import java.util.Date;

/**
 * Representa un cliente físico (persona real) que puede acumular puntos por compras.
 * Extiende de la clase Cliente.
 */
public class ClienteFisico extends Cliente {

	private int puntos_establecimiento;

	/**
	 * Constructor por defecto.
	 */
	public ClienteFisico() {
		super();
	}

	/**
	 * Constructor completo con puntos y todos los atributos heredados.
	 * @param puntos_establecimiento Puntos acumulados por el cliente en el establecimiento.
	 * @param id_cliente ID del cliente.
	 * @param fecha_alta Fecha en la que el cliente fue registrado.
	 * @param id_usuario ID del usuario asociado.
	 * @param dni DNI del cliente.
	 * @param correo Correo electrónico del cliente.
	 * @param nombre Nombre del cliente.
	 * @param apellidos Apellidos del cliente.
	 * @param direccion Dirección del cliente.
	 * @param telefono Teléfono de contacto del cliente.
	 * @param rol Rol del cliente.
	 */
	public ClienteFisico(int puntos_establecimiento, int id_cliente, Date fecha_alta, int id_usuario, String dni,
			String correo, String nombre, String apellidos, String direccion, int telefono, String rol) {
		super(id_cliente, fecha_alta, id_usuario, dni, correo, nombre, apellidos, direccion, telefono, rol);
		this.puntos_establecimiento = puntos_establecimiento;
	}

	/**
	 * Constructor sin puntos, pero con todos los atributos heredados.
	 * @param id_cliente ID del cliente.
	 * @param fecha_alta Fecha de alta.
	 * @param id_usuario ID del usuario.
	 * @param dni DNI.
	 * @param correo Correo electrónico.
	 * @param nombre Nombre.
	 * @param apellidos Apellidos.
	 * @param direccion Dirección.
	 * @param telefono Teléfono.
	 * @param rol Rol del cliente.
	 */
	public ClienteFisico(int id_cliente, Date fecha_alta, int id_usuario, String dni, String correo, String nombre,
			String apellidos, String direccion, int telefono, String rol) {
		super(id_cliente, fecha_alta, id_usuario, dni, correo, nombre, apellidos, direccion, telefono, rol);
	}

	/**
	 * Constructor usado normalmente para login o validaciones.
	 * @param dni DNI del cliente.
	 * @param correo Correo electrónico del cliente.
	 */
	public ClienteFisico(String dni, String correo) {
		super();
		this.setDni(dni);
		this.setCorreo(correo);
	}

	/** @return los puntos acumulados del cliente */
	public int getPuntos_establecimiento() {
		return puntos_establecimiento;
	}

	/** @param puntos_establecimiento los puntos a establecer */
	public void setPuntos_establecimiento(int puntos_establecimiento) {
		this.puntos_establecimiento = puntos_establecimiento;
	}
}
