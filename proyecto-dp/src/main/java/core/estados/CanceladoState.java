package core.estados;

import core.modelo.Pedido;

public class CanceladoState implements EstadoPedido {
    @Override
    public void confirmar(Pedido pedido) {
        throw new IllegalStateException("No se puede confirmar un pedido cancelado");
    }

    @Override
    public void preparar(Pedido pedido) {
        throw new IllegalStateException("No se puede preparar un pedido cancelado");
    }

    @Override
    public void listo(Pedido pedido) {
        throw new IllegalStateException("No se puede marcar listo un pedido cancelado");
    }

    @Override
    public void enviar(Pedido pedido) {
        throw new IllegalStateException("No se puede enviar un pedido cancelado");
    }

    @Override
    public void cancelar(Pedido pedido) {
        throw new IllegalStateException("El pedido ya está cancelado");
    }

    @Override
    public String getNombre() {
        return "Cancelado";
    }
}