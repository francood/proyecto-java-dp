package restaurante.strategy;

import restaurante.Pedido;
import restaurante.TipoCanal;
import restaurante.strategy.Promocion;

public class PromocionPorCanal implements Promocion {
    private TipoCanal canalRequerido;
    private Promocion promocionInterna;

    public PromocionPorCanal(TipoCanal canalRequerido, Promocion promocionInterna) {
        this.canalRequerido = canalRequerido;
        this.promocionInterna = promocionInterna;
    }

    @Override
    public double aplicar(Pedido pedido) {
        return promocionInterna.aplicar(pedido);
    }

    @Override
    public String getNombre() {
        return promocionInterna.getNombre() + " (solo para " + canalRequerido + ")";
    }

    @Override
    public boolean esAplicable(Pedido pedido) {
        return pedido.getCanal() == canalRequerido;
    }
}