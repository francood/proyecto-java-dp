package restaurante;

import productos.ProductoVendible;

public class ItemPedido implements Item {
    /*
    PRINCIPIO DIP. Pedido depende de Item(interface)
    */
    private ProductoVendible producto;
    private int cantidad;


    public ItemPedido(ProductoVendible producto, int cantidad) {
        this.producto = producto;
        this.cantidad = cantidad;
    }
    @Override
    public ProductoVendible getProducto() {return producto;}

    @Override
    public int getCantidad() {return cantidad;}

    @Override
    public double calcularSubtotal() {
        double total=cantidad*producto.getPrecio();
        
        return total;
    
    }
    
    
    

}
