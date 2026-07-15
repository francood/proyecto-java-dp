package core.factory;

import core.productos.MenuPersonalizado;
import core.factory.MenuAbstractFactory;
import core.productos.Plato;
import core.productos.Bebida;
import core.productos.Combo;
import core.productos.ProductoVendible;

/**
 * Fábrica de menús para el canal SALÓN.
 * Productos más elaborados y presentación en mesa.
 */
public class SalonMenuFactory implements MenuAbstractFactory {

    @Override
    public Plato crearPlatoPrincipal() {
        return ProductoFactory.crearPlato("Lomo Saltado", 28.0);
    }

    @Override
    public Bebida crearBebida() {
        return ProductoFactory.crearBebida("Chicha Morada", 6.0, "grande");
    }

    @Override
    public Combo crearCombo() {
        Combo combo = ProductoFactory.crearCombo("Combo Ejecutivo Salón", 10);
        combo.agregarProducto(crearPlatoPrincipal());
        combo.agregarProducto(crearBebida());
        return combo;
    }

    @Override
    public ProductoVendible crearMenuPersonalizado(ProductoVendible base) {
        MenuPersonalizado menu = ProductoFactory.crearMenuPersonalizado(base);
        // Agregar extras típicos de salón
        menu.agregarExtra("Pan de ajo", 3.0);
        menu.agregarExtra("Ensalada", 4.0);
        return menu;
    }
}