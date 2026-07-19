package core.factory;

import core.productos.MenuPersonalizado;
import core.productos.Plato;
import core.productos.Bebida;
import core.productos.Combo;
import core.productos.ProductoVendible;
import dao.ProductoDAO;
import entity.ProductoEntity;

/**
 * Fábrica de menús para DELIVERY.
 * Utiliza productos reales de la base de datos.
 */
public class DeliveryMenuFactory implements MenuAbstractFactory {

    private ProductoDAO productoDAO;

    // Inyección de dependencia
    public DeliveryMenuFactory(ProductoDAO productoDAO) {
        this.productoDAO = productoDAO;
    }

    @Override
    public Plato crearPlatoPrincipal() {
        // Buscar el producto real por nombre o ID
        try {
            ProductoEntity entity = productoDAO.buscarPorNombre("Pizza Familiar");
            if (entity != null) {
                return new Plato(entity.getIdProducto(), entity.getNombre(), entity.getPrecio());
            } else {
                return ProductoFactory.crearPlato("D001", "Pizza Familiar", 35.0);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener producto: " + e.getMessage());
        }
    }

    @Override
    public Bebida crearBebida() {
        try {
            ProductoEntity entity = productoDAO.buscarPorNombre("Limonada Frozen");
            if (entity != null) {
                return new Bebida(entity.getIdProducto(), entity.getNombre(), entity.getPrecio(), "grande");
            } else {
                return ProductoFactory.crearBebida("D002", "Limonada Frozen", 7.0, "grande");
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener producto: " + e.getMessage());
        }
    }

    @Override
    public Combo crearCombo() {
        Combo combo = ProductoFactory.crearCombo("Combo Delivery", 8);
        combo.agregarProducto(crearPlatoPrincipal());
        combo.agregarProducto(crearBebida());
        return combo;
    }

    @Override
    public ProductoVendible crearMenuPersonalizado(ProductoVendible base) {
        MenuPersonalizado menu = ProductoFactory.crearMenuPersonalizado(base);
        menu.agregarExtra("Queso extra", 2.5);
        menu.agregarExtra("Aceitunas", 1.5);
        return menu;
    }
}