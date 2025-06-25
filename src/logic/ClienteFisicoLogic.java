package logic;

import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
import controller.ClienteFisicoController;
import exception.ListaVaciaException;
import model.ClienteFisico;
import model.Producto;
import model.PagoEfectivo;
import util.Consola;
import util.ValidadorClienteFisico;
import util.ValidadorProducto;
import view.InputDatos;
import view.SelectDatos;
import model.PagoTarjeta;


public class ClienteFisicoLogic {

	private final ClienteFisicoController clienteFisCon = new ClienteFisicoController();
	private final Consola c = new Consola();
	private final InputDatos inputDat = new InputDatos();
	private ClienteFisico clienteActual;
	private final ProductoLogic prodLog = new ProductoLogic();
	private final PagoEfectivoLogic pagEfeLog = new PagoEfectivoLogic();
	private final ValidadorClienteFisico validadorCliFis = new ValidadorClienteFisico();
	private final PagoTarjetaLogic pagoTarLog = new PagoTarjetaLogic();
	private final PedidoLogic pedLog = new PedidoLogic();


	public void RegistroUsuarioFisico(Scanner sc) throws SQLException {
		ClienteFisico cf;
		boolean datosValidos = false;

		c.mostrarMensaje("Si no está registrado, necesitamos que se registre.");

		do {

			c.mostrarMensaje("--- Registro de Cliente ---");

			cf = inputDat.datos_usuario(sc, c);

			if (validadorCliFis.validarCliente(cf)) {
				datosValidos = true;
			} else {
				c.mostrarMensaje("Algunos datos no son válidos. Por favor, vuelve a introducirlos.");
			}
		} while (!datosValidos);

		c.mostrarMensaje("\n¿Estos datos son correctos?");
		c.mostrarMensaje("DNI: " + cf.getDni());
		c.mostrarMensaje("Correo: " + cf.getCorreo());
		c.mostrarMensaje("Nombre: " + cf.getNombre());
		c.mostrarMensaje("Apellidos: " + cf.getApellidos());
		c.mostrarMensaje("Dirección: " + cf.getDireccion());
		c.mostrarMensaje("Teléfono: " + cf.getTelefono());
		c.mostrarMensaje("1.Sí\n2.No");

		int confirmarDatos = sc.nextInt();
		sc.nextLine(); // limpiar buffer

		if (confirmarDatos == 1) {
			try {
				clienteFisCon.insertarClienteFisico(cf);
				c.mostrarMensaje("Cliente registrado.");
			} catch (SQLException e) {
				c.mostrarMensaje("Error al registrar el cliente: " + e.getMessage());
			}
		} else {
			c.mostrarMensaje("Registro cancelado.");
		}
	}


	public void loginFisico(Scanner sc, SelectDatos sd) {
		c.mostrarMensaje("--- Login Cliente Físico ---");

		ClienteFisico cf;
		String dni;
		String correo;

		while (true) {
			cf = sd.datos_fisico(sc, c);

			dni = cf.getDni().trim().toUpperCase();
			correo = cf.getCorreo().trim().toLowerCase();

			if (validadorCliFis.validarDni(dni) && validadorCliFis.validarCorreo(correo)) {
				break;
			} else {
				c.mostrarMensaje("DNI o correo con formato inválido. Por favor, vuelve a introducirlos.\n");
			}
		}

		try {
			ClienteFisico cfLogueado = clienteFisCon.selectLoginClienteFisico(dni, correo);

			if (cfLogueado != null) {
				c.mostrarMensaje("El cliente está registrado, tiene " + cfLogueado.getPuntos_establecimiento() + " puntos.");
				clienteActual = cfLogueado;

			} else {
				c.mostrarMensaje("Cliente físico no encontrado.");
			}
		} catch (SQLException e) {
			c.mostrarMensaje("Error al acceder a la base de datos: " + e.getMessage());
		}
	}



	public void ventaFisica(Scanner sc) throws ListaVaciaException {

	    if (clienteActual == null) {
	        c.mostrarMensaje("Debes iniciar sesión antes de comprar.");
	        return;
	    }

	    ValidadorProducto valProd = new ValidadorProducto();
	    TreeMap<Integer, Integer> carrito = new TreeMap<>();
	    double total = 0.0;
	    int[] puntosUsados = new int[1];

	    do {
	        Producto p = null;
	        String nombre;

	        do {
	            c.mostrarMensaje("¿Qué producto desea comprar?");
	            nombre = sc.nextLine();

	            if (!valProd.validarNombre(nombre)) {
	                c.mostrarMensaje("Nombre no válido. Intenta de nuevo.");
	                continue;
	            }

	            p = prodLog.obtenerProductoPorNombre(nombre);
	            if (p == null) {
	                c.mostrarMensaje("Producto no encontrado. Intenta de nuevo.");
	            }
	        } while (p == null);

	        int cantidad = 0;
	        do {
	            c.mostrarMensaje("¿Cuántas unidades desea?");
	            try {
	                cantidad = sc.nextInt();
	                sc.nextLine();
	                if (!valProd.validarStock(p, cantidad)) {
	                    c.mostrarMensaje("Cantidad no válida o insuficiente stock. (Stock disponible: " + p.getStock() + ")");
	                    if (p.getStock() == 0) {
	                        throw new ListaVaciaException("No hay stock del producto seleccionado");
	                    }
	                }
	            } catch (InputMismatchException e) {
	                c.mostrarMensaje("Entrada no válida. Inténtalo con un número.");
	                sc.nextLine();
	            }
	        } while (!valProd.validarStock(p, cantidad));

	        int idProducto = p.getIdProducto();
	        int existente = carrito.getOrDefault(idProducto, 0);
	        carrito.put(idProducto, existente + cantidad);

	        c.mostrarMensaje("¿Desea añadir otro producto?\n1. Sí\n2. No");
	        int seguir = sc.nextInt();
	        sc.nextLine();
	        if (seguir != 1) break;

	    } while (true);

	    total = 0.0;
	    c.mostrarMensaje("Resumen de compra:");
	    for (Map.Entry<Integer, Integer> detalles_carro : carrito.entrySet()) {
	        Producto producto = prodLog.obtenerProductoPorId(detalles_carro.getKey());
	        int cantidad = detalles_carro.getValue();
	        double subtotal = producto.getPrecio() * cantidad;
	        total += subtotal;
	        c.mostrarMensaje("- " + producto.getNombre() + ": " + cantidad + " x " + producto.getPrecio() + "€ = " + subtotal + "€");
	    }
	    c.mostrarMensaje("Total a pagar: " + total + "€");

	    if (clienteActual.getPuntos_establecimiento() >= 100) {
	        c.mostrarMensaje("El cliente puede utilizar sus puntos actuales: " + clienteActual.getPuntos_establecimiento() +
	                " para conseguir un descuento ¿Quiere utilizarlos?\n1.Si\n2.No");
	        int opcionDescuento = sc.nextInt();
	        sc.nextLine();
	        if (opcionDescuento == 1) {
	            total = calcularTotalConDescuento(total, puntosUsados);
	        }
	    }

	    c.mostrarMensaje("Elija método de pago:\n1. Efectivo\n2. Tarjeta");
	    int opcionPago = sc.nextInt();
	    sc.nextLine();
	    
	    boolean pagado = false;
	    switch (opcionPago) {
	        case 1: {
	            PagoEfectivo pe = new PagoEfectivo(total);
	            pagado = pagEfeLog.procesarPago(sc, pe);
	            break;
	        }
	        case 2: {
	            PagoTarjeta pt = new PagoTarjeta(total);
	            pagado = pagoTarLog.procesarPago(sc, pt);
	            break;
	        }
	        default: {
	            c.mostrarMensaje("Opción de pago no válida.");
	            return;
	        }
	    }

	    if (pagado) {
	        int puntosGanados = (int) total;
	        sumarPuntos(puntosGanados);
	        if (puntosUsados[0] > 0) {
	            restarPuntos(puntosUsados[0]);
	        }
	        for (Map.Entry<Integer, Integer> entry : carrito.entrySet()) {
	            Producto producto = prodLog.obtenerProductoPorId(entry.getKey());
	            int cantidad = entry.getValue();
	            prodLog.disminuirStock(producto, cantidad);
	        }
	        c.mostrarMensaje("Compra realizada con éxito.");
	        c.mostrarMensaje("Has ganado " + puntosGanados + " puntos.");
	        pedLog.guardarPedido(clienteActual.getId_cliente(), total, carrito);
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
