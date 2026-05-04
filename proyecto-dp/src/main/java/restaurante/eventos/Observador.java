package restaurante.eventos;

import restaurante.EstadoOrden;
import restaurante.Pedido;

/**
 *
 * @author leonardo
 */
public interface Observador {
    void actualizar(Pedido pedido, EstadoOrden estadoAnterior, EstadoOrden estadoNuevo);
}
