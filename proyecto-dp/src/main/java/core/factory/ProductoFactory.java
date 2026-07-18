package core.factory;

import core.productos.MenuPersonalizado;
import core.productos.Adicionable;
import core.productos.Bebida;
import core.productos.Combo;
import core.productos.Plato;
import core.productos.ProductoVendible;

/**
 * Fábrica para crear productos vendibles.
 * Centraliza la creación de objetos, facilitando cambios futuros.
 */
public class ProductoFactory {

    /**
     * Crea un plato.
     */
    public static Plato crearPlato(String id, String nombre, double precio) {
        return new Plato(id, nombre, precio);
    }

    /**
     * Crea una bebida.
     */
    public static Bebida crearBebida(String id, String nombre, double precio, String tamanio) {
        return new Bebida(id,nombre, precio, tamanio);
    }

    /**
     * Crea un combo con descuento.
     */
    public static Combo crearCombo(String nombre, double descuentoPorcentaje) {
        return new Combo(nombre, descuentoPorcentaje);
    }

    /**
     * Crea un menú personalizado a partir de un producto base.
     */
    public static MenuPersonalizado crearMenuPersonalizado(ProductoVendible base) {
        return new MenuPersonalizado(base);
    }

    /**
     * Crea un producto con un extra aplicado (Decorator).
     */
    public static ProductoVendible agregarExtra(ProductoVendible base, String idAdicional, String nombreExtra, double costo) {
        return new Adicionable(base, idAdicional, nombreExtra, costo);
}
}