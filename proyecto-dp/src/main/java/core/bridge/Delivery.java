package core.bridge;

import core.modelo.Pedido;


public class Delivery implements CanalImplementor {
    @Override
    public void procesar(Pedido pedido) {
        if (pedido.getDireccionDelivery() == null || pedido.getDireccionDelivery().isEmpty()) {
            throw new RuntimeException("El pedido delivery necesita dirección");
        }
        System.out.println("Delivery procesado correctamente");
    }

    @Override
    public String getDescripcion() {
        return "Entrega a domicilio";
    }
}