package core.eventos;

import core.modelo.EstadoOrden;
import core.modelo.Pedido;
import core.modelo.TipoCanal;



/**
 *
 * @author leona
 */
public class RepartoObserver implements Observador{

    @Override
    public void actualizar(Pedido pedido, EstadoOrden estadoAnterior, EstadoOrden estadoNuevo) {
        if (estadoNuevo == EstadoOrden.Listo && (pedido.getCanal() == TipoCanal.DELIVERY_PROPIO || pedido.getCanal() == TipoCanal.DELIVERY_EXTERNO)) {
            System.out.printf("Reparto: Asignar repartidor para pedido N°%s", pedido.getNumeroOrden());
        }
    }    
}
