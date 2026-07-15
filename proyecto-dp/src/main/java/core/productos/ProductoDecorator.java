package core.productos;

/*
- Clase base que contiene un producto y le añade funcionalidades extras
- Implementa el patrón Decorator
- La clase Adicionable sobreescribe sus metodos

*/
public abstract class ProductoDecorator implements ProductoVendible {
    
    protected ProductoVendible productoBase;
    
    public ProductoDecorator(ProductoVendible productoBase ){
    this.productoBase=productoBase;
    
    }

    @Override
    public String getNombre() {
        return productoBase.getNombre();
    }

    @Override
    public double getPrecio() {
        return productoBase.getPrecio();
    }

}
