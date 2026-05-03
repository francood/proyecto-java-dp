
package restaurante;

import productos.ProductoVendible;

public interface Item {
    
    /*PRINCIPIO ISP*/

    ProductoVendible getProducto();
    int getCantidad();
    double calcularSubtotal();
    
}
