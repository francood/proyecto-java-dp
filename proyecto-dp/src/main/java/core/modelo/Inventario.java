package core.modelo;

import core.exceptions.StockInsuficienteException;
import core.productos.ProductoVendible;
import java.util.HashMap;
import java.util.Map;

public class Inventario {
    private static Inventario instancia;
    private Map<String, ProductoStock> stock;

    private Inventario() {
        stock = new HashMap<>();
    }

    public static Inventario getInstancia() {
        if (instancia == null) {
            instancia = new Inventario();
        }
        return instancia;
    }

    public void agregarStock(ProductoVendible producto, int cantidad) {
        String clave = producto.getNombre();
        if (stock.containsKey(clave)) {
            ProductoStock ps = stock.get(clave);
            ps.setCantidad(ps.getCantidad() + cantidad);
        } else {
            stock.put(clave, new ProductoStock(producto, cantidad));
        }
    }

    public void descontarStock(ProductoVendible producto, int cantidad) throws StockInsuficienteException {
        String clave = producto.getNombre();
        ProductoStock ps = stock.get(clave);
        if (ps == null || ps.getCantidad() < cantidad) {
            throw new StockInsuficienteException("Stock insuficiente para " + clave +
                ". Disponible: " + (ps != null ? ps.getCantidad() : 0) + ", requerido: " + cantidad);
        }
        ps.setCantidad(ps.getCantidad() - cantidad);
    }

    public void verificarStock(Pedido pedido) throws StockInsuficienteException {
        for (Item item : pedido.getItems()) {
            String clave = item.getProducto().getNombre();
            ProductoStock ps = stock.get(clave);
            if (ps == null || ps.getCantidad() < item.getCantidad()) {
                throw new StockInsuficienteException("Stock insuficiente para " + clave +
                    ". Disponible: " + (ps != null ? ps.getCantidad() : 0) + ", requerido: " + item.getCantidad());
            }
        }
    }

    public void mostrarInventario() {
        System.out.println("=== INVENTARIO ACTUAL ===");
        for (ProductoStock ps : stock.values()) {
            System.out.println("  " + ps.getProducto().getNombre() + ": " + ps.getCantidad() + " unidades");
        }
        System.out.println("==========================");
    }
}