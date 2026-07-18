package entity;

public class ProductoEntity {

    private String idProducto;
    private String nombre;
    private double precio;
    private int stock;
    private String tipo; //plato - bebida - combo
    private String tamanio; //solo para bebida

    public ProductoEntity() {
    }

    public ProductoEntity(String idProducto, String nombre, double precio, int stock) {
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
    }
    
    public ProductoEntity(String idProducto, String nombre, double precio, int stock, String tipo, String tamanio) {
    this.idProducto = idProducto;
    this.nombre = nombre;
    this.precio = precio;
    this.stock = stock;
    this.tipo = tipo;
    this.tamanio = tamanio;
}

    public String getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(String idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    public String getTamanio() {
        return tamanio;
    }

    public void setTamanio(String tamanio) {
        this.tamanio = tamanio;
    }
}