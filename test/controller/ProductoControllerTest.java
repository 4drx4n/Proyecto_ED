package controller;

import model.Producto;
import org.junit.jupiter.api.*;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.*;
import java.util.List;

class ProductoControllerTest {

    private final ProductoController proc = new ProductoController();

    @BeforeEach
    void setUpStock() throws SQLException {
        try (Connection con = Conexion.getConexion();
             Statement st = con.createStatement()) {
            st.executeUpdate("UPDATE producto SET stock = 10");
        }
    }

    @Test
    void selectProductosDisponibles_todosConStock10() throws SQLException {
        List<Producto> productos = proc.selectProductosDisponibles();
        assertNotNull(productos);
        assertFalse(productos.isEmpty());

        for (Producto p : productos) {
            assertEquals(10, p.getStock(),
                "Cada producto debería tener stock=10 tras el setUpStock()");
            assertNotNull(p.getNombre(), p.getPrecio());
        }
    }

    @Test
    void selectProductosDisponibles_sinStock() throws SQLException {
        // Ponemos stock = 0
        try (Connection con = Conexion.getConexion();
             Statement st = con.createStatement()) {
            st.executeUpdate("UPDATE producto SET stock = 0");
        }

        List<Producto> productos = proc.selectProductosDisponibles();
        assertNotNull(productos);
        assertTrue(productos.isEmpty(),
            "Con stock=0 no debería devolver ningún producto");
    }

    @Test
    void disminuirStock_existente_bien() throws SQLException {
        // 1) Aseguramos que un producto concreto existe y tiene stock=10
        String nombre = proc.selectProductosDisponibles().get(0).getNombre();

        // 2) Llamamos a disminuirStock
        proc.disminuirStock(nombre, 3);

        // 3) Comprobamos que en BD se redujo a 7
        try (Connection con = Conexion.getConexion();
             PreparedStatement st = con.prepareStatement(
                 "SELECT stock FROM producto WHERE nombre = ?"
             )) {
            st.setString(1, nombre);
            try (ResultSet rs = st.executeQuery()) {
                assertTrue(rs.next(), "El producto debe seguir existiendo");
                assertEquals(7, rs.getInt("stock"),
                    "El stock debería haber quedado en 10 - 3 = 7");
            }
        }
    }

}
