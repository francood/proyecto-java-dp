package restaurante;


import java.util.ArrayList;
import java.util.List;
import restaurante.productos.Adicionable;
import restaurante.productos.ProductoVendible;

public class ItemPedido implements Item {
    /*
    PRINCIPIO DIP. Pedido depende de Item(interface)
    */
    private ProductoVendible producto;
    private int cantidad;
    private List<Adicionable>adicionables;

    public ItemPedido(ProductoVendible producto, int cantidad) {
        this.producto = producto;
        this.cantidad = cantidad;
        this.adicionables=new ArrayList<>();
    }
    
        public void agregarAdicionable(Adicionable ad){
        adicionables.add(ad);
    }
    

    @Override
    public ProductoVendible getProducto() {return producto;}

    @Override
    public int getCantidad() {return cantidad;}

    @Override
    public double calcularSubtotal() {
        double total=cantidad*producto.getPrecio();
        
        for(Adicionable ad: adicionables){
            total+=ad.getPrecio()*cantidad;
        }
        return total;
    
    }
    
    
    

}
