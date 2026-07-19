package core.factory;

import core.productos.Plato;
import core.productos.Bebida;
import core.productos.Combo;
import core.productos.ProductoVendible;

/**
 * Fábrica abstracta para crear familias de productos según el canal.
 * Cada canal tiene su propio menú base.
 */
public interface MenuAbstractFactory {

    /**
     * Crea el plato principal recomendado para el canal.
     */
    Plato crearPlatoPrincipal();

    /**
     * Crea la bebida recomendada para el canal.
     */
    Bebida crearBebida();

    /**
     * Crea el combo recomendado para el canal.
     */
    Combo crearCombo();

    /**
     * Crea un menú personalizado según el canal.
     */
    ProductoVendible crearMenuPersonalizado(ProductoVendible base);
}