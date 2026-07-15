package core.facade;

import core.builder.PedidoBuilder;
import core.eventos.AlmacenObserver;
import core.eventos.CajaObserver;
import core.eventos.CocinaObserver;
import core.eventos.RepartoObserver;
import core.exceptions.EstadoInvalidoException;
import core.modelo.Cliente;
import core.modelo.Item;
import core.modelo.Pedido;
import core.modelo.Sucursal;
import core.modelo.TipoCanal;
import core.promociones.PromocionPorcentaje;
import core.strategy.Promocion;
import java.util.List;



/**
 * Fachada que simplifica la interacción con el sistema de pedidos.
 * Oculta la complejidad de observadores, promociones y cambios de estado.
 */
public class RestauranteFacade {

    // Observadores por defecto
    private CocinaObserver cocinaObserver;
    private CajaObserver cajaObserver;
    private AlmacenObserver almacenObserver;
    private RepartoObserver repartoObserver;

    public RestauranteFacade() {
        this.cocinaObserver = new CocinaObserver();
        this.cajaObserver = new CajaObserver();
        this.almacenObserver = new AlmacenObserver();
        this.repartoObserver = new RepartoObserver();
    }

    /**
     * Crea y procesa un pedido completo:
     * - Crea el pedido con los datos básicos.
     * - Agrega los items.
     * - Registra los observadores.
     * - Confirma, prepara y deja listo el pedido.
     * - Aplica una promoción por defecto (5%).
     */
    public Pedido procesarPedido(
            String numeroOrden,
            TipoCanal canal,
            Cliente cliente,
            Sucursal sucursal,
            List<Item> items,
            String numeroMesa,
            String direccionDelivery,
            String codigoRecojo) {

        // 1. Construir el pedido usando el Builder
        PedidoBuilder builder = new PedidoBuilder()
                .conNumeroOrden(numeroOrden)
                .conCanal(canal)
                .conCliente(cliente)
                .conSucursal(sucursal);

        // Datos opcionales según canal
        if (numeroMesa != null) builder.conNumeroMesa(numeroMesa);
        if (direccionDelivery != null) builder.conDireccionDelivery(direccionDelivery);
        if (codigoRecojo != null) builder.conCodigoRecojo(codigoRecojo);

        for (Item item : items) {
            builder.agregarItem(item);
        }

        Pedido pedido = builder.build();

        // 2. Registrar observadores
        pedido.agregarObservador(cocinaObserver);
        pedido.agregarObservador(cajaObserver);
        pedido.agregarObservador(almacenObserver);
        pedido.agregarObservador(repartoObserver);

        // 3. Procesar el pedido (flujo automático)
        try {
            pedido.confirmarPedido();
            pedido.enPreparacion();
            pedido.listo();
            // Si es delivery, se envía
            if (canal == TipoCanal.DELIVERY_PROPIO || canal == TipoCanal.DELIVERY_EXTERNO) {
                pedido.enviar();
            }
        } catch (EstadoInvalidoException e) {
            System.err.println("Error en flujo de pedido: " + e.getMessage());
        }

        return pedido;
    }

    /**
     * Calcula el total con la mejor promoción disponible.
     */
    public double calcularTotalConPromocion(Pedido pedido, List<Promocion> promociones) {
        double mejorTotal = pedido.total();
        for (Promocion promo : promociones) {
            double totalConPromo = promo.aplicar(pedido);
            if (totalConPromo < mejorTotal) {
                mejorTotal = totalConPromo;
            }
        }
        return mejorTotal;
    }

    /**
     * Método simplificado con promoción fija del 5%.
     */
    public double calcularTotalConPromocionBase(Pedido pedido) {
        Promocion promocionBase = new PromocionPorcentaje(5);
        return promocionBase.aplicar(pedido);
    }
}