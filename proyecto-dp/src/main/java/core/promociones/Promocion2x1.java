package core.promociones;

import core.modelo.Item;
import core.modelo.Pedido;
import core.strategy.Promocion;


public class Promocion2x1 implements Promocion {
    @Override
    public double aplicar(Pedido pedido) {
        double menor = pedido.getItems().stream()
            .mapToDouble(Item::calcularSubtotal)
            .min().orElse(0);
        return pedido.total() - menor;
    }

    @Override
    public String getNombre() {
        return "Promocion 2x1 (descuento del item mas barato)";
    }
}
