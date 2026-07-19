package core.eventos;

import core.modelo.EstadoOrden;
import core.modelo.Item;
import core.modelo.ItemPedido;
import core.modelo.Pedido;


/**
 *
 * @author leonardo
 */
public class CocinaObserver implements Observador {

    @Override
    public void actualizar(Pedido pedido, EstadoOrden estadoAnterior, EstadoOrden estadoNuevo) {
        if (estadoNuevo == EstadoOrden.Confirmado || estadoNuevo == EstadoOrden.En_Preparacion) {
            System.out.println("-------------------- Cocina recibio la orden --------------------");
            System.out.printf("| Pedido N°:%5s\n", pedido.getNumeroOrden());
            for (Item item : pedido.getItems()) {
                if (item instanceof ItemPedido) {
                    System.out.println("| " + ((ItemPedido) item).getInstruccionesCocina());
                }
            }
            System.out.println("-----------------------------------------------------------------");
        }
    }    
}
