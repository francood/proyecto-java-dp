package restaurante.bridge;

import restaurante.Pedido;

public interface CanalImplementor {
    void procesar(Pedido pedido);
    String getDescripcion();
}