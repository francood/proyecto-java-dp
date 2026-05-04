package restaurante.eventos;

import restaurante.EstadoOrden;
import restaurante.Pedido;

/**
 *
 * @author leonardo
 */
public class AlmacenObserver implements Observador{

    @Override
    public void actualizar(Pedido pedido, EstadoOrden estadoAnterior, EstadoOrden estadoNuevo) {
        if (estadoNuevo == EstadoOrden.Confirmado) {
            System.out.printf("Almacen: Descontar insumos del pedido N°%s", pedido.getNumeroOrden());
        }
    }    
}
