package controller;

import core.modelo.Pedido;
import core.modelo.Item;
import core.modelo.ItemPedido;
import core.productos.Adicionable;
import core.productos.Combo;
import core.productos.MenuPersonalizado;
import core.productos.ProductoVendible;
import core.promociones.AplicadorPromociones;
import core.strategy.Promocion;
import dao.DetallePedidoDAO;
import dao.PedidoDAO;
import dao.ProductoDAO;
import entity.DetallePedidoEntity;
import entity.PedidoEntity;
import entity.ProductoEntity;
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

    public int guardarPedido(Pedido pedido, List<Promocion> promociones, Integer calificacion, String comentario, String idEmpleado) throws Exception {
        // 1. Calcular total final (con o sin descuento)
        double totalFinal = pedido.total();
        if (promociones != null && !promociones.isEmpty()) {
            totalFinal = AplicadorPromociones.aplicarMejorPromocion(pedido, promociones);
        }

        // 2. Descontar stock de cada producto (con manejo recursivo de combos/adicionales)
        for (Item item : pedido.getItems()) {
            if (item instanceof ItemPedido) {
                ItemPedido itemPedido = (ItemPedido) item;
                descontarProducto(itemPedido.getProducto(), itemPedido.getCantidad());
            }
        }
        // 3. Construir entidad PedidoEntity
        PedidoEntity entity = new PedidoEntity();
        entity.setIdCliente(pedido.getCliente().getId());
        entity.setIdSucursal(pedido.getSucursal().getId());
        entity.setIdEmpleado(idEmpleado);
        entity.setCanal(pedido.getCanal().toString());
        entity.setTotal(totalFinal);
        entity.setEstado(pedido.getEstado().toString());
        entity.setCalificacion(calificacion != null ? calificacion : 0);
        entity.setComentario(comentario);
        entity.setIdEmpleado(idEmpleado);

        // 4. Guardar pedido y obtener ID
        int idPedido = pedidoDAO.guardar(entity);

        // 5. Guardar detalles
        List<DetallePedidoEntity> detalles = new ArrayList<>();
        for (Item item : pedido.getItems()) {
            if (item instanceof ItemPedido) {
                ItemPedido itemPedido = (ItemPedido) item;
                DetallePedidoEntity detalle = new DetallePedidoEntity();
                detalle.setIdPedido(idPedido);
                detalle.setIdProducto(itemPedido.getProducto().getId());
                detalle.setCantidad(itemPedido.getCantidad());
                detalle.setSubtotal(itemPedido.calcularSubtotal());
                detalles.add(detalle);
            }
        }
        detalleDAO.guardarBatch(detalles, idPedido);

        return idPedido;
    }
    

    private void descontarProducto(ProductoVendible producto, int cantidad) throws Exception {
        // 1. Validaciones básicas
        if (producto == null) {
            throw new Exception("No se puede descontar un producto nulo.");
        }
        if (cantidad <= 0) {
            System.out.println("Cantidad <= 0, no se descuenta nada para: " + producto.getNombre());
            return;
        }

        // 2. Despachar según el tipo
        if (producto instanceof Combo) {
            Combo combo = (Combo) producto;
            System.out.println("Descontando combo: " + combo.getNombre() + " (cantidad: " + cantidad + ")");
            // Descuenta recursivamente cada producto interno del combo
            for (ProductoVendible p : combo.getItems()) {
                descontarProducto(p, cantidad); // La cantidad se aplica a cada item del combo
            }
        } 
        else if (producto instanceof Adicionable) {
            Adicionable adicionable = (Adicionable) producto;
            // El adicional tiene su propio stock
           
            String idAdicional = adicionable.getId();
            if (idAdicional == null || idAdicional.trim().isEmpty()) {
                throw new Exception("El adicional '" + adicionable.getNombre() + "' no tiene ID.");
            }
            productoDAO.descontarStock(idAdicional, cantidad);
        } else if (producto instanceof MenuPersonalizado) {
            MenuPersonalizado menu = (MenuPersonalizado) producto;
            System.out.println("Descontando menú personalizado: " + menu.getNombre() + " -> base: " + menu.getBase().getNombre());
            if (menu.getBase() != null) {
                descontarProducto(menu.getBase(), cantidad);
            } else {
                throw new Exception("El menú personalizado no tiene producto base para descontar.");
            }
        } 
        else {
            // Plato, Bebida o cualquier otro producto físico (leaf node)
            String idProducto = producto.getId();
            if (idProducto == null || idProducto.trim().isEmpty()) {
                throw new Exception("El producto '" + producto.getNombre() + "' no tiene un ID válido para descontar stock.");
            }
            System.out.println("Descontando producto físico: " + producto.getNombre() + " (ID: " + idProducto + ", cantidad: " + cantidad + ")");
            productoDAO.descontarStock(idProducto, cantidad);
        }
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
    
    public int calcularTiempoEstimado(Pedido pedido) {
    if (pedido.getCanalImplementor() != null) {
        return pedido.getCanalImplementor().getTiempoEstimado();
    }
    return 15; // valor por defecto
}
    
    // para la view del cliente
    public int buscarIdPorNumeroOrden(String numeroOrden) throws Exception {
        return pedidoDAO.buscarIdPorNumeroOrden(numeroOrden);
    }

    public void actualizarCalificacion(int idPedido, int calificacion, String comentario) throws Exception {
        pedidoDAO.actualizarCalificacion(idPedido, calificacion, comentario);
    }
}