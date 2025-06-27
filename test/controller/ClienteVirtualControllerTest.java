package controller;

import org.junit.jupiter.api.Test;
import java.sql.SQLException;
import static org.junit.jupiter.api.Assertions.*;

class ClienteVirtualControllerTest {

    @Test
    void credencialesReales() throws SQLException {
        ClienteVirtualController clivc = new ClienteVirtualController();
        boolean existe = clivc.selectClienteVirtual("virtual@campico.org", "123456");
        assertTrue(existe, "La cuenta real existe en la BBDD");
    }

    @Test
    void credencialesFalsas() throws SQLException {
        ClienteVirtualController clivc = new ClienteVirtualController();
        boolean existe = clivc.selectClienteVirtual("no@existe.com", "badpass");
        assertFalse(existe, "Retorna false");
    }
}
