package productos.factory;

import productos.factory.abstractFactory.MenuAbstractFactory;
import productos.Plato;
import productos.Bebida;
import productos.composite.Combo;
import productos.MenuPersonalizado;
import productos.ProductoVendible;
import productos.factory.ProductoFactory;

/**
 * Fábrica de menús para el canal PARA LLEVAR.
 * Productos prácticos y empaques adecuados.
 */
public class ParaLlevarMenuFactory implements MenuAbstractFactory {

    @Override
    public Plato crearPlatoPrincipal() {
        return ProductoFactory.crearPlato("Pollo a la Brasa", 22.0);
    }

    @Override
    public Bebida crearBebida() {
        return ProductoFactory.crearBebida("Gaseosa", 4.0, "mediano");
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