package logic;

import controller.PedidoController;
import model.Pedido;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * Clase de lógica de negocio que gestiona operaciones relacionadas con pedidos.
 * Se comunica con el controlador para guardar pedidos en la base de datos.
 *
 */
public class PedidoLogic {

    private final PedidoController pedCon = new PedidoController();
    
    /**
     * Crea una nueva instancia de la lógica de pedidos.
     */
    public PedidoLogic() {
        
    }

    /**
     * Guarda un nuevo pedido con la información del cliente, el total
     * y los productos comprados. Asigna automáticamente la fecha y hora actuales.
     *
     * @param idCliente ID del cliente que realiza el pedido.
     * @param total Importe total de la compra.
     * @param carrito Mapa con los IDs de producto y cantidades compradas.
     */
    public void guardarPedido(int idCliente, double total, Map<Integer, Integer> carrito) {
        Pedido pedido = new Pedido(idCliente, LocalDateTime.now(), total, carrito);
        try {
            pedCon.insertarPedido(pedido);
        } catch (SQLException e) {
            System.err.println("Error al guardar el pedido: " + e.getMessage());
        }
    }

}
