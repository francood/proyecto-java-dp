package restaurante.eventos;

import restaurante.EstadoOrden;
import restaurante.Pedido;

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
