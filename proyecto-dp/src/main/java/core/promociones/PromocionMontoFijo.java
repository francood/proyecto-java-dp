package core.promociones;

import core.modelo.Pedido;
import core.strategy.Promocion;


public class PromocionMontoFijo implements Promocion {

  private double monto;

  public PromocionMontoFijo(double monto) {
    this.monto = monto;
  }

  @Override
  public double aplicar(Pedido pedido) {
    return Math.max(0, pedido.total() - monto);
  }

  @Override
  public String getNombre() {
    return "Descuento fijo de $" + monto;
  }
}
