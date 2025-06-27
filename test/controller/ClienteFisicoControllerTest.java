package controller;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.*;
import java.time.LocalDate;
import java.util.Date;
import model.ClienteFisico;
import util.ValidadorClienteFisico;

import org.junit.jupiter.api.*;

class ClienteFisicoControllerTest {

	private static Connection con;
	private ClienteFisicoController ctrl = new ClienteFisicoController();

	@BeforeEach
	void setUp() throws Exception {
		con = Conexion.getConexion();
		con.setAutoCommit(false);
		ctrl = new ClienteFisicoController(); // Reinicia el Controller cada vez que se utiliza.
	}

	@AfterEach
	void tearDown() throws Exception {
		if (con != null) {
			con.rollback();
			con.close();
		}
	}

	@Test
	void insertarClienteFisico_validaciones() throws Exception {
		// Fecha de alta sin hora, para comparar
		Date hoy = new Date();
		java.sql.Date fechaSql = new java.sql.Date(hoy.getTime());

		ClienteFisico c = new ClienteFisico("12345678R", "test@test.com");
		c.setNombre("Falso");
		c.setApellidos("Test");
		c.setDireccion("Catral");
		c.setTelefono(666666666);
		c.setRol("Comprador");
		c.setFecha_alta(hoy);

		ctrl.insertarClienteFisico(con, c);
		System.out.println("Cliente insertado correctamente.");

		String sql = """
				    SELECT p.nombre, p.apellidos, p.direccion, p.telefono, p.rol,
				           cl.fecha_alta, cf.puntos_establecimiento
				    FROM persona p
				    JOIN cliente cl ON p.id_usuario = cl.id_usuario
				    JOIN cliente_fisico cf ON cl.id_cliente = cf.id_cliente
				    WHERE p.dni = ? AND p.correo = ?
				""";

		try (PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setString(1, c.getDni());
			ps.setString(2, c.getCorreo());
			try (ResultSet rs = ps.executeQuery()) {
				assertTrue(rs.next(), "Debe encontrarse el cliente físico");

				assertEquals("Falso", rs.getString("nombre"));
				assertEquals("Test", rs.getString("apellidos"));
				assertEquals("Catral", rs.getString("direccion"));
				assertEquals(666666666, rs.getInt("telefono"));
				assertEquals("Comprador", rs.getString("rol"));

				// Compara solo año/mes/día
				LocalDate fechaEsperada = fechaSql.toLocalDate();
				LocalDate fechaActual = rs.getDate("fecha_alta").toLocalDate();
				assertEquals(fechaEsperada, fechaActual, "La fecha de alta debe coincidir (sin comparar hora)");

				assertEquals(0, rs.getInt("puntos_establecimiento"));
			}
		}
	}

	@Test
	void insertarClienteFisico_clienteInvalido() throws SQLException {
		ClienteFisico invalido = new ClienteFisico("Adrian123", "adrian123@falso.es");
		invalido.setNombre("");
		invalido.setApellidos("");
		invalido.setDireccion("");
		invalido.setTelefono(799999999);
		invalido.setRol("Comprador");
		invalido.setFecha_alta(new Date());

		try {
			ctrl.insertarClienteFisico(con, invalido);
		} catch (IllegalArgumentException e) {
			assertTrue(e.getMessage().contains("Cliente físico inválido"));
		}
	}
	
	@Test
	void insertarClienteFisico_dni_nulo() throws SQLException {
		ClienteFisico invalido = new ClienteFisico(null, "adrian123@falso.es");
		invalido.setNombre("");
		invalido.setApellidos("");
		invalido.setDireccion("");
		invalido.setTelefono(888888888);
		invalido.setRol("Comprador");
		invalido.setFecha_alta(new Date());

		try {
			ctrl.insertarClienteFisico(con, invalido);
		} catch (IllegalArgumentException e) {
			assertTrue(e.getMessage().contains("Cliente físico inválido"));
		}
	}
	
	@Test
	void insertarClienteFisico_correoMitad() throws SQLException {
		ClienteFisico invalido = new ClienteFisico("12345678R", "adrian.es");
		invalido.setNombre(" ");
		invalido.setApellidos(" ");
		invalido.setDireccion(" ");
		invalido.setTelefono(55555555);
		invalido.setRol("Comprador");
		invalido.setFecha_alta(new Date());

		try {
			ctrl.insertarClienteFisico(con, invalido);
		} catch (IllegalArgumentException e) {
			assertTrue(e.getMessage().contains("Cliente físico inválido"));
		}
	}
	
	@Test
	void insertarClienteFisico_correoSoloArroba() throws SQLException {
		ClienteFisico invalido = new ClienteFisico("12345678R", "adrian@");
		invalido.setNombre(null);
		invalido.setApellidos(null);
		invalido.setDireccion(null);
		invalido.setTelefono(55555555);
		invalido.setRol("Comprador");
		invalido.setFecha_alta(new Date());

		try {
			ctrl.insertarClienteFisico(con, invalido);
		} catch (IllegalArgumentException e) {
			assertTrue(e.getMessage().contains("Cliente físico inválido"));
		}
	}
	
	@Test
	void insertarClienteFisico_correoNull() throws SQLException {
		ClienteFisico invalido = new ClienteFisico("12345678R", null);
		invalido.setNombre(null);
		invalido.setApellidos(null);
		invalido.setDireccion(null);
		invalido.setTelefono(55555555);
		invalido.setRol("Comprador");
		invalido.setFecha_alta(new Date());

		try {
			ctrl.insertarClienteFisico(con, invalido);
		} catch (IllegalArgumentException e) {
			assertTrue(e.getMessage().contains("Cliente físico inválido"));
		}
	}
	
	@Test
	void insertarClienteFisico_nombreNull() throws SQLException {
		ClienteFisico invalido = new ClienteFisico("12345678R", "adrian123@falso.es");
		invalido.setNombre(null);
		invalido.setApellidos("");
		invalido.setDireccion("");
		invalido.setTelefono(799999999);
		invalido.setRol("Comprador");
		invalido.setFecha_alta(new Date());

		try {
			ctrl.insertarClienteFisico(con, invalido);
		} catch (IllegalArgumentException e) {
			assertTrue(e.getMessage().contains("Cliente físico inválido"));
		}
	}
	
	@Test
	void insertarClienteFisico_nombreEspacio() throws SQLException {
		ClienteFisico invalido = new ClienteFisico("12345678R", "adrian123@falso.es");
		invalido.setNombre(" ");
		invalido.setApellidos("");
		invalido.setDireccion("");
		invalido.setTelefono(799999999);
		invalido.setRol("Comprador");
		invalido.setFecha_alta(new Date());

		try {
			ctrl.insertarClienteFisico(con, invalido);
		} catch (IllegalArgumentException e) {
			assertTrue(e.getMessage().contains("Cliente físico inválido"));
		}
	}
	
	@Test
	void insertarClienteFisico_apellidosNull() throws SQLException {
		ClienteFisico invalido = new ClienteFisico("12345678R", "adrian123@falso.es");
		invalido.setNombre("Adrian");
		invalido.setApellidos(null);
		invalido.setDireccion("");
		invalido.setTelefono(799999999);
		invalido.setRol("Comprador");
		invalido.setFecha_alta(new Date());

		try {
			ctrl.insertarClienteFisico(con, invalido);
		} catch (IllegalArgumentException e) {
			assertTrue(e.getMessage().contains("Cliente físico inválido"));
		}
	}
	
	@Test
	void insertarClienteFisico_apellidosEspacio() throws SQLException {
		ClienteFisico invalido = new ClienteFisico("12345678R", "adrian123@falso.es");
		invalido.setNombre("Adrian");
		invalido.setApellidos(" ");
		invalido.setDireccion("");
		invalido.setTelefono(799999999);
		invalido.setRol("Comprador");
		invalido.setFecha_alta(new Date());

		try {
			ctrl.insertarClienteFisico(con, invalido);
		} catch (IllegalArgumentException e) {
			assertTrue(e.getMessage().contains("Cliente físico inválido"));
		}
	}
	
	@Test
	void insertarClienteFisico_direccionNull() throws SQLException {
		ClienteFisico invalido = new ClienteFisico("12345678R", "adrian123@falso.es");
		invalido.setNombre("Adrian");
		invalido.setApellidos("Aguilar");
		invalido.setDireccion(null);
		invalido.setTelefono(799999999);
		invalido.setRol("Comprador");
		invalido.setFecha_alta(new Date());

		try {
			ctrl.insertarClienteFisico(con, invalido);
		} catch (IllegalArgumentException e) {
			assertTrue(e.getMessage().contains("Cliente físico inválido"));
		}
	}
	
	@Test
	void insertarClienteFisico_direccionEspacio() throws SQLException {
		ClienteFisico invalido = new ClienteFisico("12345678R", "adrian123@falso.es");
		invalido.setNombre("Adrian");
		invalido.setApellidos("Aguilar");
		invalido.setDireccion(" ");
		invalido.setTelefono(799999999);
		invalido.setRol("Comprador");
		invalido.setFecha_alta(new Date());

		try {
			ctrl.insertarClienteFisico(con, invalido);
		} catch (IllegalArgumentException e) {
			assertTrue(e.getMessage().contains("Cliente físico inválido"));
		}
	}
	
	@Test
	void insertarClienteFisico_telefonoCinco() throws SQLException {
		ClienteFisico invalido = new ClienteFisico("12345678R", "adrian123@falso.es");
		invalido.setNombre("Adrian");
		invalido.setApellidos("Aguilar");
		invalido.setDireccion("Catral");
		invalido.setTelefono(555555555);
		invalido.setRol("Comprador");
		invalido.setFecha_alta(new Date());

		try {
			ctrl.insertarClienteFisico(con, invalido);
		} catch (IllegalArgumentException e) {
			assertTrue(e.getMessage().contains("Cliente físico inválido"));
		}
	}
	
	@Test
	void insertarClienteFisico_telefonoOcho() throws SQLException {
		ClienteFisico invalido = new ClienteFisico("12345678R", "adrian123@falso.es");
		invalido.setNombre("Adrian");
		invalido.setApellidos("Aguilar");
		invalido.setDireccion("Catral");
		invalido.setTelefono(888888888);
		invalido.setRol("Comprador");
		invalido.setFecha_alta(new Date());

		try {
			ctrl.insertarClienteFisico(con, invalido);
		} catch (IllegalArgumentException e) {
			assertTrue(e.getMessage().contains("Cliente físico inválido"));
		}
	}
	
	@Test
	void insertarClienteFisico_objetoNull() {
	    ValidadorClienteFisico validador = new ValidadorClienteFisico();
	    assertFalse(validador.validarCliente(null), "Debe devolver false si el objeto ClienteFisico es null");
	}

}