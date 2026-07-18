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
 * Fábrica de menús para el canal PARA LLEVAR.
 * Productos prácticos y empaques adecuados.
 */
public class ParaLlevarMenuFactory implements MenuAbstractFactory {

    private ProductoDAO productoDAO;

    public ParaLlevarMenuFactory(ProductoDAO productoDAO) {
        this.productoDAO = productoDAO;
    }

    @Override
    public Plato crearPlatoPrincipal() {
        try {
            ProductoEntity entity = productoDAO.buscarPorNombre("Pollo a la Brasa");
            if (entity != null) {
                return new Plato(entity.getIdProducto(), entity.getNombre(), entity.getPrecio());
            } else {
                throw new RuntimeException("Producto 'Pollo a la Brasa' no encontrado en la base de datos.");
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener producto: " + e.getMessage(), e);
        }
    }

    @Override
    public Bebida crearBebida() {
        try {
            ProductoEntity entity = productoDAO.buscarPorNombre("Gaseosa");
            if (entity != null) {
                return new Bebida(entity.getIdProducto(), entity.getNombre(), entity.getPrecio(), "mediano");
            } else {
                throw new RuntimeException("Producto 'Gaseosa' no encontrado en la base de datos.");
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener producto: " + e.getMessage(), e);
        }
    }

    @Override
    public Combo crearCombo() {
        Combo combo = ProductoFactory.crearCombo("Combo Para Llevar", 5);
        combo.agregarProducto(crearPlatoPrincipal());
        combo.agregarProducto(crearBebida());
        return combo;
    }

    @Override
    public ProductoVendible crearMenuPersonalizado(ProductoVendible base) {
        MenuPersonalizado menu = ProductoFactory.crearMenuPersonalizado(base);
        // Extras típicos para llevar
        menu.agregarExtra("Salsa extra", 1.5);
        menu.agregarExtra("Papas fritas", 3.5);
        return menu;
    }
}