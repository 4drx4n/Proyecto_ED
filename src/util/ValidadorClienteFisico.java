package util;

import model.ClienteFisico;

public class ValidadorClienteFisico {

    public boolean validarDni(String dni) {
        return dni != null && dni.matches("^[0-9]{8}[A-Za-z]$");
    }

    public boolean validarCorreo(String correo) {
        return correo != null && correo.matches("^(.+)@(.+)$");
    }

    public boolean validarTelefono(int telefono) {
        return telefono >= 600000000 && telefono <= 799999999;
    }

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
