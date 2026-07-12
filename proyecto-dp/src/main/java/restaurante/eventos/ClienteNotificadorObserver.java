package restaurante.eventos;

import restaurante.EstadoOrden;
import restaurante.Pedido;

/**
 *
 * @author leonardo
 */
public class ClienteNotificadorObserver implements Observador{

    @Override
    public void actualizar(Pedido pedido, EstadoOrden estadoAnterior, EstadoOrden estadoNuevo) {
        if (estadoNuevo == EstadoOrden.Listo) {
            System.out.println("Notificando a " + pedido.getCliente().getNombre() + ": tu pedido esta listo.");
        }else if (estadoNuevo == EstadoOrden.Enviado) {
            System.out.println("Su pedido va en camino.");
        }
    }
    
}
