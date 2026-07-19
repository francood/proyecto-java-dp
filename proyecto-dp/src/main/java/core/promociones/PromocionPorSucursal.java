package core.promociones;

import core.modelo.Pedido;
import core.modelo.Sucursal;
import core.strategy.Promocion;

public class PromocionPorSucursal implements Promocion {
    private Sucursal sucursalRequerida;
    private Promocion promocionInterna;

    public PromocionPorSucursal(Sucursal sucursalRequerida, Promocion promocionInterna) {
        this.sucursalRequerida = sucursalRequerida;
        this.promocionInterna = promocionInterna;
    }

    @Override
    public double aplicar(Pedido pedido) {
        return promocionInterna.aplicar(pedido);
    }

    @Override
    public String getNombre() {
        return promocionInterna.getNombre() + " (solo para " + sucursalRequerida.getNombre() + ")";
    }

    @Override
    public boolean esAplicable(Pedido pedido) {
        return pedido.getSucursal() != null &&
               pedido.getSucursal().getId() == sucursalRequerida.getId();
    }
}