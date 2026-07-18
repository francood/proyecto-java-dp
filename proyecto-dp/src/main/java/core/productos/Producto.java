package core.productos;

import java.util.ArrayList;
import java.util.List;
import core.productos.ProductoVendible;

public abstract class Producto implements ProductoVendible, Cloneable {
    
    private String id;
    private String nombre;
    private double precio;
    private List<String> ingredientesQuitados = new ArrayList<>();

    public Producto(String id, String nombre, double precio) {
        this.id=id;
        this.nombre = nombre;
        this.precio = precio;
    }
    
    @Override
    public String getId() {
        return id;
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
            // Copia de la lista de ingredientes
            clon.ingredientesQuitados = new ArrayList<>(this.ingredientesQuitados);
            return clon;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException("Error al clonar producto", e);
        }
    }
}