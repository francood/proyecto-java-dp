package core.eventos;

import core.modelo.EstadoOrden;
import core.modelo.Pedido;

public class ClienteNotificadorObserver implements Observador {

    @Override
    public void actualizar(Pedido pedido, EstadoOrden estadoAnterior, EstadoOrden estadoNuevo) {
        String nombreCliente = pedido.getCliente().getNombre();

        if (estadoNuevo == EstadoOrden.Listo) {
            System.out.printf("Notificando a %s: ¡Tu pedido N°%s ya está LISTO para recoger!️\n",
                    nombreCliente, pedido.getNumeroOrden());
        } else if (estadoNuevo == EstadoOrden.Enviado) {
            System.out.printf("Notificando a %s: ¡Tu pedido N°%s está EN CAMINO!\n",
                    nombreCliente, pedido.getNumeroOrden());
        }
    }
}