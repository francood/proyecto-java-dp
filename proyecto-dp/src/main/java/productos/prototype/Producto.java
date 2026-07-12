package productos.prototype;

import java.util.ArrayList;
import java.util.List;
import productos.ProductoVendible;

public abstract class Producto implements ProductoVendible, Cloneable {

    private String nombre;
    private double precio;
    private List<String> ingredientesQuitados = new ArrayList<>();

    public Producto(String nombre, double precio) {
        this.nombre = nombre;
        this.precio = precio;
    }

    @Override
    public String getNombre() {
        return nombre;
    }

    @Override
    public double getPrecio() {
        return precio;
    }

    public List<String> getIngredientesQuitados() {
        return ingredientesQuitados;
    }

    public void quitarIngredientes(String text) {
        ingredientesQuitados.add(text);
    }

    /**
     * Implementación del patrón Prototype.
     * Crea una copia profunda del producto.
     */
    public Producto clonar() {
        try {
            Producto clon = (Producto) super.clone();
            // Copia profunda de la lista de ingredientes
            clon.ingredientesQuitados = new ArrayList<>(this.ingredientesQuitados);
            return clon;
        } catch (CloneNotSupportedException e) {
            // Fallback: crear una nueva instancia (dependiendo de la subclase)
            // En este caso, lanzamos RuntimeException para no complicar.
            throw new RuntimeException("Error al clonar producto", e);
        }
    }
}