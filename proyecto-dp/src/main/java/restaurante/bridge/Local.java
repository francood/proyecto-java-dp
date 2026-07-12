package restaurante.bridge;

import restaurante.Pedido;
import restaurante.bridge.CanalImplementor;

public class Local implements CanalImplementor {
    @Override
    public void procesar(Pedido pedido) {
        if (pedido.getNumeroMesa() == null || pedido.getNumeroMesa().isEmpty()) {
            throw new RuntimeException("Debe asignarse una mesa");
        }
        System.out.println("Pedido de local procesado correctamente");
    }

    @Override
    public String getDescripcion() {
        return "Atención en mesa";
    }
}