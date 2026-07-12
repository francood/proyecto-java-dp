package productos.composite;

import java.util.ArrayList;
import java.util.List;
import productos.ProductoVendible;

public class Combo implements ProductoVendible{

        private String nombre;
        private List<ProductoVendible> items;
        private double descuentoPorcentaje;
        
        public Combo(String nombre, double descuentoPorcentaje){
        this.nombre=nombre;
        this.descuentoPorcentaje=descuentoPorcentaje;;
        this.items=new ArrayList<>();
        
        }
        
        public void agregarProducto(ProductoVendible producto){
        items.add(producto);
        }

    @Override
    public String getNombre() {
    return nombre;
    }

    @Override
    public double getPrecio() {
    double suma=0;
    for(ProductoVendible p:items){
    suma=suma+p.getPrecio();
        }
    return suma *(1-descuentoPorcentaje/100);
    }
    
    public List<ProductoVendible> getItems(){
    return items;
    }
    
    public void mostrarEstructura(String indent) {
        System.out.println(indent + "Combo: " + nombre + " (descuento " + descuentoPorcentaje + "%)");
        for (ProductoVendible p : items) {
            if (p instanceof Combo) {
                ((Combo) p).mostrarEstructura(indent + "  ");
            } else {
                System.out.println(indent + "  - " + p.getNombre() + " (S/" + p.getPrecio() + ")");
            }
        }
    }
}
