package core.estados;

import core.modelo.Pedido;

public class EnviadoState implements EstadoPedido {
    @Override
    public void confirmar(Pedido pedido) {
        throw new IllegalStateException("El pedido ya fue enviado");
    }

    @Override
    public void preparar(Pedido pedido) {
        throw new IllegalStateException("El pedido ya fue enviado");
    }

    @Override
    public void listo(Pedido pedido) {
        throw new IllegalStateException("El pedido ya fue enviado");
    }

    @Override
    public void enviar(Pedido pedido) {
        throw new IllegalStateException("El pedido ya fue enviado");
    }

    @Override
    public void cancelar(Pedido pedido) {
        throw new IllegalStateException("No se puede cancelar un pedido enviado");
    }

    @Override
    public String getNombre() {
        return "Enviado";
    }
}