
package restaurante.promociones;

import restaurante.Pedido;

public interface Promocion {
    double aplicar(Pedido pedido);
    String getNombre();
}
