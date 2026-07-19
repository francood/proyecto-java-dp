package core.estados;

import core.modelo.Pedido;


public class PendienteState implements EstadoPedido {
    @Override
    public void confirmar(Pedido pedido) {
        if (pedido.getItems().isEmpty()) {
            throw new IllegalStateException("No se puede confirmar un pedido sin items");
        }
        pedido.setEstadoState(new ConfirmadoState());
        System.out.println("Pedido confirmado");
    }

    @Override
    public void preparar(Pedido pedido) {
        throw new IllegalStateException("No se puede preparar un pedido pendiente");
    }

    @Override
    public void listo(Pedido pedido) {
        throw new IllegalStateException("No se puede marcar listo un pedido pendiente");
    }

    @Override
    public void enviar(Pedido pedido) {
        throw new IllegalStateException("No se puede enviar un pedido pendiente");
    }

    @Override
    public void cancelar(Pedido pedido) {
        pedido.setEstadoState(new CanceladoState());
        System.out.println("Pedido cancelado");
    }

    @Override
    public String getNombre() {
        return "Pendiente";
    }
}