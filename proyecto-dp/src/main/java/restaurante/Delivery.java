package restaurante;

public class Delivery implements CanalProcesador {

    @Override
    public void procesar(Pedido pedido) {

        if (pedido.getDireccionDelivery() == null ||
            pedido.getDireccionDelivery().isEmpty()) {

            throw new RuntimeException("El pedido delivery necesita dirección");
        }

        System.out.println("Delivery procesado correctamente");
    }
}