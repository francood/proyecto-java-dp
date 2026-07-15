
package core.productos;

/**
 *
 * @author Elizabet
 */
public interface ProductoVendible {
    //lo pueden implementar bebidas y alimentos solidos
    String getNombre();
    double getPrecio();

    public String getId();
    
}
