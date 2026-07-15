package core.eventos;

import core.modelo.EstadoOrden;
import core.modelo.Pedido;


/**
 *
 * @author leonardo
 */
public interface Observador {
    void actualizar(Pedido pedido, EstadoOrden estadoAnterior, EstadoOrden estadoNuevo);
}
