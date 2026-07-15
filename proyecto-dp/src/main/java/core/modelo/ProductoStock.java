package core.modelo;

import core.productos.ProductoVendible;

public class ProductoStock {
    private ProductoVendible producto;
    private int cantidad;

    public ProductoStock(ProductoVendible producto, int cantidad) {
        this.producto = producto;
        this.cantidad = cantidad;
    }

    public ProductoVendible getProducto() { return producto; }
    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }
}