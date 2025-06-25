/*package logic;

import model.PagoTransferencia;
import util.Consola;
import java.util.Scanner;

public class PagoTransferenciaLogic implements PagoLogic<PagoTransferencia> {

    private final Consola c = new Consola();

    @Override
    public boolean procesarPago(Scanner sc, PagoTransferencia pago) {
        c.mostrarMensaje("El importe a transferir es: " + pago.getTotal() + "€");

        c.mostrarMensaje("Introduce el IBAN donde se cobrará:");
        String iban = sc.nextLine().trim();

        c.mostrarMensaje("Introduce el nombre del titular de la cuenta:");
        String titular = sc.nextLine().trim();

        c.mostrarMensaje("Introduce el concepto de la transferencia:");
        String concepto = sc.nextLine().trim();

        c.mostrarMensaje("Transferencia completada correctamente.\n");
        return true;
    }
}
*/