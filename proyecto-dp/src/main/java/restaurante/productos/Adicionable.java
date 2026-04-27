package restaurante.productos;
public class Adicionable implements ProductoVendible {
    
    private String nombre;
    private double precio;


    public Adicionable(String nombre, double precio) {
        this.nombre = nombre;
        this.precio = precio;

    }
    
    
    @Override
    public String getNombre() {return nombre;}

    @Override
    public double getPrecio() {return precio;}

}
