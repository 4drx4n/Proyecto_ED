package logic;

import java.sql.SQLException;
import java.util.Scanner;
import controller.ClienteFisicoController;
import model.ClienteFisico;
import model.PagoEfectivo;
import model.Producto;
import util.Consola;
import view.InputDatos;
import view.SelectDatos;

public class ClienteFisicoLogic {

	private final ClienteFisicoController clienteFisCon = new ClienteFisicoController();
	private final Consola c = new Consola();
	private final InputDatos inputDat = new InputDatos();
	private ClienteFisico clienteActual;
	private ProductoLogic productoLog = new ProductoLogic();
	private PagoEfectivoLogic pel = new PagoEfectivoLogic();

	public void RegistroUsuarioFisico(Scanner sc) throws SQLException {

		c.mostrarMensaje("Si no está registrado, necesitamos que se registre.");
		c.mostrarMensaje("--- Registro de Cliente ---");

		ClienteFisico cf = inputDat.datos_usuario(sc, c);

		c.mostrarMensaje("\n¿Estos datos son correctos?");
		c.mostrarMensaje("DNI: " + cf.getDni());
		c.mostrarMensaje("Correo: " + cf.getCorreo());
		c.mostrarMensaje("Nombre: " + cf.getNombre());
		c.mostrarMensaje("Apellidos: " + cf.getApellidos());
		c.mostrarMensaje("Dirección: " + cf.getDireccion());
		c.mostrarMensaje("Teléfono: " + cf.getTelefono());
		c.mostrarMensaje("Rol: " + cf.getRol());
		c.mostrarMensaje("1.Si\n2.No");

		int confirmarDatos = 0;

		confirmarDatos = sc.nextInt();
		sc.nextLine(); //LIMPIAR BUFFER

		if (confirmarDatos == 1) {
			try {
				clienteFisCon.insertarClienteFisico(cf);
				c.mostrarMensaje("Cliente registrado");
			} catch (SQLException e) {
				c.mostrarMensaje("Error al registrar el cliente" + e.getStackTrace());
			}
		} else if (confirmarDatos == 2) {
			c.mostrarMensaje("Registro cancelado.");
		}else {
			c.mostrarMensaje("Opción no válid, registro anulado");
		}
	}

	public void loginFisico(Scanner sc, SelectDatos sd) {
		c.mostrarMensaje("--- Login Cliente Físico ---");

		ClienteFisico cf = sd.datos_fisico(sc, c);

		String dni    = cf.getDni().trim().toUpperCase();
		String correo = cf.getCorreo().trim().toLowerCase();

		try {
			Integer puntos = clienteFisCon.selectLoginClienteFisico(dni, correo);
			if (puntos != null) {

				c.mostrarMensaje("El cliente está registrado, tiene " + puntos + " puntos.");
				clienteActual = cf;
				clienteActual.setPuntos_establecimiento(puntos);
			} else {
				c.mostrarMensaje("Cliente físico no encontrado");
			}
		} catch (SQLException e) {
			c.mostrarMensaje("Error al acceder a la base de datos: " + e.getMessage());
		}
	}

	public void ventaFisica(Scanner sc) {

		c.mostrarMensaje("¿Qué producto desea comprar?");

		String nombre = sc.nextLine();
		Producto p = productoLog.obtenerProductoPorNombre(nombre);

		if (p == null) {
			c.mostrarMensaje("Producto no encontrado.");
			return;
		}

		c.mostrarMensaje("¿Cuántas unidades desea?");

		int cantidad = sc.nextInt();
		sc.nextLine();

		double total = Math.round(p.getPrecio() * cantidad * 100.0) / 100.0;
		int[] puntosUsados = new int[1];

		if (clienteActual.getPuntos_establecimiento() >= 100) {
			total = calcularTotalConDescuento(total, puntosUsados);
		} else {
			c.mostrarMensaje("No tienes suficientes puntos para usarlos como descuento.");
		}

		PagoEfectivo pe = new PagoEfectivo(total);
		boolean pagado = pel.procesarPago(sc, pe);

		if (pagado) {
			int puntos = (int) total;
			sumarPuntos(puntos);

			if (puntosUsados[0] > 0) {
				restarPuntos(puntosUsados[0]);
			}

			c.mostrarMensaje("Compra realizada con éxito.");
			c.mostrarMensaje("Has ganado " + puntos + " puntos.");
		} else {
			c.mostrarMensaje("No se pudo completar el pago.");
		}
	}

	public int obtenerPuntos() {
		try {
			return clienteFisCon.selectPuntosEstablecimiento(clienteActual.getDni());
		} catch (SQLException e) {
			c.mostrarMensaje("Error al obtener los puntos: " + e.getMessage());
			return 0;
		}
	}

	public void sumarPuntos(int cantidad) {
		try {
			clienteFisCon.actualizarPuntos(clienteActual.getDni(), cantidad, true);
		} catch (SQLException e) {
			c.mostrarMensaje("Error al sumar puntos: " + e.getMessage());
		}
	}

	public void restarPuntos(int cantidad) {
		try {
			clienteFisCon.actualizarPuntos(clienteActual.getDni(), cantidad, false);
		} catch (SQLException e) {
			c.mostrarMensaje("Error al restar puntos: " + e.getMessage());
		}
	}

	public double calcularTotalConDescuento(double total, int[] puntosUsados) {
		int puntos = clienteActual.getPuntos_establecimiento();
		int puntosDescuento = (puntos / 100) * 100;

		if (puntosDescuento == 0) {
			c.mostrarMensaje("No tienes puntos suficientes para aplicar descuento.");
			puntosUsados[0] = 0;
			return total;
		}

		double porcentajeDescuento = (puntosDescuento / 100) * 0.02; // 2% por cada 100 puntos
		double descuento = porcentajeDescuento * total;
		double totalConDescuento = Math.round((total - descuento) * 100.0) / 100.0;

		c.mostrarMensaje("Se ha aplicado un descuento del " + Math.round(porcentajeDescuento * 100) +
				"% usando " + puntosDescuento + " puntos.");
		c.mostrarMensaje("Nuevo total con descuento: " + totalConDescuento + "€");

		puntosUsados[0] = puntosDescuento;
		return totalConDescuento;
	}
}
