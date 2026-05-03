package productos;

import java.util.ArrayList;
import java.util.List;

//Principio OSP
//Proporciona los atributos comunes de un producto sólido

public abstract class Producto implements ProductoVendible{
    
    private String nombre;
    private double precio;
    private List<String> ingredientesQuitados = new ArrayList();
    private List<String> ingredientesAñadidos = new ArrayList();
    
    public Producto(String nombre,double precio){
        this.nombre=nombre;
        this.precio=precio;
    }
    //sobreescribe los metodos de la interfaz ProductoVendible
  
    @Override
    public String getNombre(){
    return nombre;
    }
    
    @Override
    public double getPrecio(){
    return precio;
    }
    
    //Comportamiento de los productos solidos, para bebida se creara otra
  
    //modificaciones sin costo
    
    public void añadirExtras(String text) {
        ingredientesAñadidos.add(text);
    }

    public void quitarIngredientes(String text) {
        ingredientesQuitados.add(text);
    }

}
