package core.productos;

public class Bebida implements ProductoVendible{
    
    private String id;
    private String nombre;
    private double precio;
    private String tamanio;

    public Bebida(String id, String nombre, double precio, String tamanio) {
        this.id=id;
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

    @Override
    public String getId() {
        return id;
    }


}
