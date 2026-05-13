package restaurante;

public class Local implements CanalProcesador {

    @Override
    public void procesar(Pedido pedido) {

        if (pedido.getNumeroMesa() == null ||
            pedido.getNumeroMesa().isEmpty()) {

            throw new RuntimeException("Debe asignarse una mesa");
        }

        System.out.println("Pedido de local procesado correctamente");
    }
}