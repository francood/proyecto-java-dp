package core.bridge;

import core.modelo.Pedido;

public interface CanalImplementor {
    void procesar(Pedido pedido);
    String getDescripcion();
    int getTiempoEstimado();
}