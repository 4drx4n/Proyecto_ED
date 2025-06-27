package util;

import model.ClienteFisico;

/**
 * Clase encargada de validar los datos de un cliente físico.
 * Comprueba el formato del DNI, correo, teléfono, y campos obligatorios.
 *
 * Se emplea tanto en el proceso de registro como en la verificación previa a insertar
 * el cliente en la base de datos.
 */
public class ValidadorClienteFisico {

	/**
	 * Validar dni.
	 *
	 * @param dni
	 * @return true, si es correcto.
	 */
	public boolean validarDni(String dni) {
		return dni != null && dni.matches("^[0-9]{8}[A-Za-z]$");
	}

	/**
	 * Validar correo.
	 *
	 * @param correo electrónico
	 * @return true, si es correcto.
	 */
	public boolean validarCorreo(String correo) {
		return correo != null && correo.matches("^(.+)@(.+)$");
	}

	/**
	 * Validar telefono.
	 *
	 * @param telefono
	 * @return true, si es correcto.
	 */
	public boolean validarTelefono(int telefono) {
		return telefono >= 600000000 && telefono <= 799999999;
	}

	/**
	 * Valida que todos los datos del cliente físico sean correctos
	 * según las reglas definidas (DNI, correo, etc.).
	 *
	 * @param cf Cliente físico a validar.
	 * @return true si todos los datos son válidos, false en caso contrario.
	 */
	public boolean validarCliente(ClienteFisico cf) {
		return cf != null &&
				validarDni(cf.getDni()) &&
				validarCorreo(cf.getCorreo()) &&
				validarTelefono(cf.getTelefono()) &&
				cf.getNombre() != null && !cf.getNombre().isBlank() &&
				cf.getApellidos() != null && !cf.getApellidos().isBlank() &&
				cf.getDireccion() != null && !cf.getDireccion().isBlank();
	}

}
