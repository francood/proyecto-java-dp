package productos.factory;

import productos.factory.abstractFactory.MenuAbstractFactory;
import productos.Plato;
import productos.Bebida;
import productos.composite.Combo;
import productos.MenuPersonalizado;
import productos.ProductoVendible;
import productos.factory.ProductoFactory;

/**
 * Fábrica de menús para DELIVERY.
 * Productos que resisten bien el transporte.
 */
public class DeliveryMenuFactory implements MenuAbstractFactory {

    @Override
    public Plato crearPlatoPrincipal() {
        return ProductoFactory.crearPlato("Pizza Familiar", 35.0);
    }

    @Override
    public Bebida crearBebida() {
        return ProductoFactory.crearBebida("Limonada Frozen", 7.0, "grande");
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
        // Extras típicos de delivery
        menu.agregarExtra("Queso extra", 2.5);
        menu.agregarExtra("Aceitunas", 1.5);
        return menu;
    }
}