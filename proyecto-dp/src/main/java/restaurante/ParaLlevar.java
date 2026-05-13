package restaurante;

public class ParaLlevar implements CanalProcesador {

    @Override
    public void procesar(Pedido pedido) {

        if (pedido.getCodigoRecojo() == null ||
            pedido.getCodigoRecojo().isEmpty()) {

            throw new RuntimeException("Debe existir código de recojo");
        }

        System.out.println("Pedido para llevar procesado correctamente");
    }
}