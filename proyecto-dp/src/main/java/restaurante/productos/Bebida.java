package restaurante.productos;
public class Bebida implements ProductoVendible{
    
    private String nombre;
    private double precio;
    private String tamanio;

    public Bebida(String nombre, double precio, String tamanio) {
        this.nombre = nombre;
        this.precio = precio;
        this.tamanio=tamanio;
    }
   
    public void setTamanio(String tamanio){
    this.tamanio=tamanio;
    }

    @Override
    public String getNombre() {
        return nombre;
    }

    @Override
    public double getPrecio() {
        return precio;
    }


}
