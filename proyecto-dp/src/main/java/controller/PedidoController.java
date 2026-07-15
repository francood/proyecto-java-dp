package controller;

import core.modelo.Pedido;
import core.modelo.Item;
import core.modelo.ItemPedido;
import core.promociones.AplicadorPromociones;
import core.strategy.Promocion;
import dao.DetallePedidoDAO;
import dao.PedidoDAO;
import dao.ProductoDAO;
import entity.DetallePedidoEntity;
import entity.PedidoEntity;
import java.util.ArrayList;
import java.util.List;

public class PedidoController {

    private PedidoDAO pedidoDAO;
    private DetallePedidoDAO detalleDAO;
    private ProductoDAO productoDAO;

    public PedidoController() {
        pedidoDAO = new PedidoDAO();
        detalleDAO = new DetallePedidoDAO();
        productoDAO = new ProductoDAO();
    }

    public int guardarPedido(Pedido pedido, List<Promocion> promociones) throws Exception {
        // 1. Calcular total final (con o sin descuento)
        double totalFinal = pedido.total();
        if (promociones != null && !promociones.isEmpty()) {
            totalFinal = AplicadorPromociones.aplicarMejorPromocion(pedido, promociones);
        }

        // 2. Descontar stock de cada producto (usando el nombre para obtener el ID)
        for (Item item : pedido.getItems()) {
            if (item instanceof ItemPedido) {
                ItemPedido itemPedido = (ItemPedido) item;
                String nombreProducto = itemPedido.getProducto().getNombre();
                // Buscar el producto por nombre para obtener su ID (asume nombres únicos)
                String idProducto = productoDAO.buscarPorNombre(nombreProducto).getIdProducto();
                int cantidad = itemPedido.getCantidad();
                productoDAO.descontarStock(idProducto, cantidad);
            }
        }

        // 3. Construir entidad PedidoEntity
        PedidoEntity entity = new PedidoEntity();
        entity.setIdCliente(pedido.getCliente().getId());
        entity.setIdSucursal(pedido.getSucursal().getId());
        entity.setCanal(pedido.getCanal().toString());
        entity.setTotal(totalFinal); // Total con descuento
        entity.setEstado(pedido.getEstado().toString());

        // 4. Guardar pedido y obtener ID
        int idPedido = pedidoDAO.guardar(entity);

        // 5. Guardar detalles
        List<DetallePedidoEntity> detalles = new ArrayList<>();
        for (Item item : pedido.getItems()) {
            if (item instanceof ItemPedido) {
                ItemPedido itemPedido = (ItemPedido) item;
                String nombreProducto = itemPedido.getProducto().getNombre();
                String idProducto = productoDAO.buscarPorNombre(nombreProducto).getIdProducto();
                DetallePedidoEntity detalle = new DetallePedidoEntity();
                detalle.setIdPedido(idPedido);
                detalle.setIdProducto(idProducto);
                detalle.setCantidad(itemPedido.getCantidad());
                detalle.setSubtotal(itemPedido.calcularSubtotal());
                detalles.add(detalle);
            }
        }
        detalleDAO.guardarBatch(detalles, idPedido);

        return idPedido;
    }

    public List<PedidoEntity> listarTodos() throws Exception {
        return pedidoDAO.listarTodos();
    }

    public List<PedidoEntity> listarPorCliente(String idCliente) throws Exception {
        return pedidoDAO.listarPorCliente(idCliente);
    }

    public PedidoEntity buscarPorId(int id) throws Exception {
        return pedidoDAO.buscarPorId(id);
    }

    public List<DetallePedidoEntity> listarDetallesPorPedido(int idPedido) throws Exception {
        return detalleDAO.listarPorPedido(idPedido);
    }
}