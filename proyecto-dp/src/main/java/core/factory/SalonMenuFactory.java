package core.factory;

import core.productos.MenuPersonalizado;
import core.factory.MenuAbstractFactory;
import core.productos.Plato;
import core.productos.Bebida;
import core.productos.Combo;
import core.productos.ProductoVendible;
import dao.ProductoDAO;
import entity.ProductoEntity;

/**
 * Fábrica de menús para el canal SALÓN.
 * Productos más elaborados y presentación en mesa.
 */
public class SalonMenuFactory implements MenuAbstractFactory {
    
    private ProductoDAO productoDAO;
    
    public SalonMenuFactory(ProductoDAO productoDAO){
        this.productoDAO = productoDAO;
    }

    @Override
    public Plato crearPlatoPrincipal() {
        try {
            // Buscar el producto real por nombre (debe existir en la BD)
            ProductoEntity entity = productoDAO.buscarPorNombre("Lomo Saltado");
            if (entity != null) {
                return new Plato(entity.getIdProducto(), entity.getNombre(), entity.getPrecio());
            } else {
                throw new RuntimeException("Producto 'Lomo Saltado' no encontrado en la base de datos.");
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener producto: " + e.getMessage(), e);
        }
    }

    @Override
    public Bebida crearBebida() {
        try {
            ProductoEntity entity = productoDAO.buscarPorNombre("Chicha Morada");
            if (entity != null) {
                return new Bebida(entity.getIdProducto(), entity.getNombre(), entity.getPrecio(), "grande");
            } else {
                throw new RuntimeException("Producto 'Chicha Morada' no encontrado en la base de datos.");
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener producto: " + e.getMessage(), e);
        }
    }

    @Override
    public Combo crearCombo() {
        // El combo se crea con los productos reales obtenidos arriba
        Combo combo = ProductoFactory.crearCombo("Combo Ejecutivo Salón", 10);
        combo.agregarProducto(crearPlatoPrincipal());
        combo.agregarProducto(crearBebida());
        return combo;
    }

    @Override
    public ProductoVendible crearMenuPersonalizado(ProductoVendible base) {
        MenuPersonalizado menu = ProductoFactory.crearMenuPersonalizado(base);
        // Extras típicos de salón
        menu.agregarExtra("Pan de ajo", 3.0);
        menu.agregarExtra("Ensalada", 4.0);
        return menu;
    }
}