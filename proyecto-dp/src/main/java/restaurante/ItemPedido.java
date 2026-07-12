package restaurante;

import productos.prototype.Producto;
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
    
    public String getInstruccionesCocina() {
        StringBuilder sb = new StringBuilder();
        sb.append(producto.getNombre());
        if (producto instanceof Producto) {
            Producto p = (Producto) producto;
            if (!p.getIngredientesQuitados().isEmpty()) {
                sb.append(" (sin: ").append(String.join(", ", p.getIngredientesQuitados())).append(")");
            }
        }
        return sb.toString();
    }


}
