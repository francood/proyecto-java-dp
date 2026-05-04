/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package restaurante.eventos;

import restaurante.EstadoOrden;
import restaurante.Pedido;
import restaurante.TipoCanal;

/**
 *
 * @author leona
 */
public class RepartoOberver implements Observador{

    @Override
    public void actualizar(Pedido pedido, EstadoOrden estadoAnterior, EstadoOrden estadoNuevo) {
        if (estadoNuevo == EstadoOrden.Listo && (pedido.getCanal() == TipoCanal.DELIVERY_PROPIO || pedido.getCanal() == TipoCanal.DELIVERY_EXTERNO)) {
            System.out.printf("Reparto: Asignar repartidor para pedido N°%s", pedido.getNumeroOrden());
        }
    }    
}
