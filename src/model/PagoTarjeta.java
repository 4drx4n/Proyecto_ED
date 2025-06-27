package model;

/**
 * Representa un pago realizado mediante tarjeta.
 * No requiere atributos adicionales más allá del total.
 */
public class PagoTarjeta extends Pago {

    /**
     * Constructor del pago con tarjeta.
     *
     * @param total Importe total a pagar.
     */
    public PagoTarjeta(double total) {
        super(total);
    }
}
