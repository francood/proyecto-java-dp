package restaurante.state;

import restaurante.Pedido;

public class EnPreparacionState implements EstadoPedido {
    @Override
    public void confirmar(Pedido pedido) {
        throw new IllegalStateException("El pedido ya está en preparación");
    }

    @Override
    public void preparar(Pedido pedido) {
        throw new IllegalStateException("El pedido ya está en preparación");
    }

    @Override
    public void listo(Pedido pedido) {
        pedido.setEstadoState(new ListoState());
        System.out.println("Pedido listo");
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
        return "En preparación";
    }
}