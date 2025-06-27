package util;

import model.Producto;

/**
 * Clase de utilidad para validar productos en el proceso de compra.
 */
public class ValidadorProducto {

	/**
     * Verifica que el producto no sea nulo.
     *
     * @param p Producto a validar.
     * @return true si el producto no es nulo.
     */
    public boolean validarProducto(Producto p) {
        return p != null;
    }
    
    /**
     * Verifica que el nombre del producto no esté vacío ni sea nulo.
     *
     * @param nombre Nombre del producto.
     * @return true si el nombre es válido.
     */
    public boolean validarNombre(String nombre) {
        return nombre != null && !nombre.isBlank();
    }

    /**
     * Comprueba si hay suficiente stock para la cantidad solicitada.
     *
     * @param p Producto que se desea comprar.
     * @param cantidad Cantidad solicitada.
     * @return true si la cantidad es positiva y hay stock suficiente.
     */
    public boolean validarStock(Producto p, int cantidad) {
        return cantidad > 0 && p.getStock() >= cantidad;
    }

}
