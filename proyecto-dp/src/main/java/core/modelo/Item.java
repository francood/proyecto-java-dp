
package core.modelo;

import core.productos.ProductoVendible;

public interface Item {
    
    /*PRINCIPIO ISP*/

    ProductoVendible getProducto();
    int getCantidad();
    double calcularSubtotal();
    
}
