
package restaurante.productos;

import java.util.List;

/**
 *
 * @author Elizabet
 */
public interface ProductoSolido extends ProductoVendible{ 
    
    //Comportamiento de los productos solidas, para bebida se creara otra
    //Principio OSP
    
    
    //modificaciones sin costo
    void quitarIngredientes(String text);
    void añadirExtras(String text);
    
}
