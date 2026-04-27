package restaurante.productos;

import java.util.ArrayList;
import java.util.List;

//Proporciona los atributos comunes de un producto sólido
public abstract class Producto implements ProductoSolido{
    
    private String nombre;
    private double precio;
    private List<String> ingredientesQuitados = new ArrayList();
    private List<String> ingredientesAñadidos = new ArrayList();
    
    public Producto(String nombre,double precio){
        this.nombre=nombre;
        this.precio=precio;
    }
    
    public String getNombre(){
    return nombre;
    }
    
    public double getPrecio(){
    return precio;
    }
    
    @Override
    public void añadirExtras(String text) {
        ingredientesAñadidos.add(text);
    }

    @Override
    public void quitarIngredientes(String text) {
        ingredientesQuitados.add(text);
    }

}
