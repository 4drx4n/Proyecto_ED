package util;

import model.Producto;

public class ValidadorProducto {

    public boolean validarProducto(Producto p) {
        return p != null;
    }
    
    public boolean validarNombre(String nombre) {
        return nombre != null && !nombre.isBlank();
    }

    public boolean validarStock(Producto p, int cantidad) {
        return cantidad > 0 && p.getStock() >= cantidad;
    }

}
