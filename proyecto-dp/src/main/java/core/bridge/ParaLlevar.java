package core.bridge;

import core.modelo.Pedido;


public class ParaLlevar implements CanalImplementor {
    @Override
    public void procesar(Pedido pedido) {
        if (pedido.getCodigoRecojo() == null || pedido.getCodigoRecojo().isEmpty()) {
            throw new RuntimeException("Debe existir código de recojo");
        }
        System.out.println("Pedido para llevar procesado correctamente");
    }

    @Override
    public String getDescripcion() {
        return "Recojo en local";
    }
}