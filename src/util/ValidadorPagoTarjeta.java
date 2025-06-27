package util;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

public class ValidadorPagoTarjeta {

    public String validarTarjeta(String tarjeta) {
        if (tarjeta == null) return null;

        if (tarjeta.length() == 17 && tarjeta.charAt(0) == '3') {
            if (tarjeta.charAt(4) == ' ' && tarjeta.charAt(11) == ' '
                && tarjeta.replace(" ", "").matches("\\d{15}")) {
                return "American Express";
            }
        }

        if (tarjeta.length() == 19 && (tarjeta.charAt(0) == '4' || tarjeta.charAt(0) == '5')) {
            if (tarjeta.charAt(4) == ' ' && tarjeta.charAt(9) == ' ' && tarjeta.charAt(14) == ' '
                && tarjeta.replace(" ", "").matches("\\d{16}")) {
                return tarjeta.charAt(0) == '4' ? "Visa" : "MasterCard";
            }
        }

        return null;
    }

    public boolean validarCVV(String cvv, String marca) {
        if (cvv == null) return false;
        if ("American Express".equals(marca)) {
            return cvv.matches("\\d{4}");
        } else {
            return cvv.matches("\\d{3}");
        }
    }


    public String validarPagoTarjeta(String tarjeta, String caducidad, String cvv) {
        String marca = validarTarjeta(tarjeta);
        if (marca == null) return null;

        try {
            YearMonth fechaActual = YearMonth.parse(caducidad,
                DateTimeFormatter.ofPattern("MM/yy"));
            if (fechaActual.isBefore(YearMonth.now())) return null;
        } catch (Exception ex) {
            return null;
        }

        if (!validarCVV(cvv, marca)) {
            return null;
        }

        return marca;
    }
}
