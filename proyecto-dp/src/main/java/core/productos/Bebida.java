package core.productos;

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

    @Override
    public String getId() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }


}
