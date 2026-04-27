
package restaurante;

import restaurante.productos.ProductoVendible;

public interface Item {
    
    /*PRINCIPIO ISP*/

    ProductoVendible getProducto();
    int getCantidad();
    double calcularSubtotal();
    
}
