package model;

import java.util.Date;

public class ClienteVirtual extends Cliente {
	
	private String contrasenya;

	public ClienteVirtual() {
		super();
	}

	public ClienteVirtual(String contrasenya, int id_cliente, Date fecha_alta,int id_usuario, String dni, String correo, String nombre, String apellidos,
			String direccion, int telefono, String rol) {
		super(id_cliente, fecha_alta, id_usuario, dni, correo, nombre, apellidos, direccion, telefono, rol);
		this.contrasenya = contrasenya;
	}

	public ClienteVirtual(String correo, String password) {
		//ESTE CONSTRUCTOR SE VA UTILIZAR PARA VERIFICAR CORREO Y CONTRASEÑA (LOGIN)
		super();
		this.setCorreo(correo);
		this.setPassword(password);
	}

	public String getPassword() {
		return contrasenya;
	}

	public void setPassword(String password) {
		this.contrasenya = password;
	}
	
}
