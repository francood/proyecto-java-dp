package restaurante.state;

import restaurante.Pedido;

public class ListoState implements EstadoPedido {
    @Override
    public void confirmar(Pedido pedido) {
        throw new IllegalStateException("El pedido ya está listo");
    }

    @Override
    public void preparar(Pedido pedido) {
        throw new IllegalStateException("El pedido ya está listo");
    }

    @Override
    public void listo(Pedido pedido) {
        throw new IllegalStateException("El pedido ya está listo");
    }

    @Override
    public void enviar(Pedido pedido) {
        pedido.setEstadoState(new EnviadoState());
        System.out.println("Pedido enviado");
    }

    @Override
    public void cancelar(Pedido pedido) {
        pedido.setEstadoState(new CanceladoState());
        System.out.println("Pedido cancelado");
    }

    @Override
    public String getNombre() {
        return "Listo";
    }
}