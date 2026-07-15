package core.estados;

import core.modelo.Pedido;

public class ConfirmadoState implements EstadoPedido {
    @Override
    public void confirmar(Pedido pedido) {
        throw new IllegalStateException("El pedido ya está confirmado");
    }

    @Override
    public void preparar(Pedido pedido) {
        pedido.setEstadoState(new EnPreparacionState());
        System.out.println("Pedido en preparación");
    }

    @Override
    public void listo(Pedido pedido) {
        throw new IllegalStateException("El pedido aún no está en preparación");
    }

    @Override
    public void enviar(Pedido pedido) {
        throw new IllegalStateException("El pedido aún no está listo");
    }

    @Override
    public void cancelar(Pedido pedido) {
        pedido.setEstadoState(new CanceladoState());
        System.out.println("Pedido cancelado");
    }

    @Override
    public String getNombre() {
        return "Confirmado";
    }
}