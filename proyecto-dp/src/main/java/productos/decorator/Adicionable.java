package productos.decorator;

import productos.ProductoVendible;
import productos.decorator.ProductoDecorator;

/*
- Decorador concreto que añade un extra con costo a un producto vendible
- Modifica el nombre concatennado el extra y suma su costo

*/
public class Adicionable extends ProductoDecorator implements ProductoVendible {
    
    private String nombreAdicional;
    private double precioAdicional;


    public Adicionable(ProductoVendible productoBase, String nombreAdicional, double precioAdicional) {
        super(productoBase);
        this.nombreAdicional=nombreAdicional;
        this.precioAdicional=precioAdicional;

    }
    
    
    @Override
    public String getNombre() {
        
        return productoBase.getNombre()+" + "+nombreAdicional;
    }

    @Override
    public double getPrecio() {
        return productoBase.getPrecio()+precioAdicional;
    }

}
