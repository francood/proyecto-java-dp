package core.eventos;

import core.modelo.EstadoOrden;
import core.modelo.Pedido;

/**
 *
 * @author leonardo
 */
public class CajaObserver implements Observador {

    @Override
    public void actualizar(Pedido pedido, EstadoOrden estadoAnterior, EstadoOrden estadoNuevo) {
        switch  (estadoNuevo){            
            case (EstadoOrden.Confirmado):
                System.out.printf("Caja: Registrar pago de %f soles", pedido.total());
                break;                
            case (EstadoOrden.Cancelado):
                System.out.println("Caja: Revertir cobro / cancelar transaccion");
                break;
            default:
                System.out.println("Estado desconocido...");        
        }
    }    
}
