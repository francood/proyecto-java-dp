package core.estados;

import core.modelo.Pedido;


public interface EstadoPedido {
    void confirmar(Pedido pedido);
    void preparar(Pedido pedido);
    void listo(Pedido pedido);
    void enviar(Pedido pedido);
    void cancelar(Pedido pedido);
    String getNombre();
}