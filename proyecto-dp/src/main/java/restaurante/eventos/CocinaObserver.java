package restaurante.eventos;

import restaurante.EstadoOrden;
import restaurante.Pedido;

/**
 *
 * @author leonardo
 */
public class CocinaObserver implements Observador {

    @Override
    public void actualizar(Pedido pedido, EstadoOrden estadoAnterior, EstadoOrden estadoNuevo) {
        if (estadoNuevo == EstadoOrden.Confirmado || estadoNuevo == EstadoOrden.En_Preparacion) {
            System.out.println("-------------------- Cocina recibio la orden --------------------");
            System.out.printf("| Pedido N°:%5s"
                            + "| Items:%5s", pedido.getNumeroOrden(), pedido.getItems());
            System.out.println("-----------------------------------------------------------------");
        }
    }    
}
