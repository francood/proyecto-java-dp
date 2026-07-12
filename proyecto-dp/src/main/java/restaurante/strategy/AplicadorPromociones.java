package restaurante.strategy;

import restaurante.Pedido;
import java.util.List;
import restaurante.strategy.Promocion;

public class AplicadorPromociones {

    /**
     * Aplica la mejor promoción (la que deja el total más bajo)
     * entre todas las promociones aplicables.
     */
    public static double aplicarMejorPromocion(Pedido pedido, List<Promocion> promociones) {
        double mejorTotal = pedido.total();
        String nombreMejor = "Sin promoción";

        for (Promocion promo : promociones) {
            if (promo.esAplicable(pedido)) {
                double totalConPromo = promo.aplicar(pedido);
                if (totalConPromo < mejorTotal) {
                    mejorTotal = totalConPromo;
                    nombreMejor = promo.getNombre();
                }
            }
        }

        System.out.println("🏷️ Promoción aplicada: " + nombreMejor);
        return mejorTotal;
    }
}