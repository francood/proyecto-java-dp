package core.promociones;

import core.modelo.Pedido;
import core.strategy.Promocion;


public class PromocionPorcentaje implements Promocion {

  private double porcentaje;

  public PromocionPorcentaje(double porcentaje) {
    this.porcentaje = porcentaje;
  }

  @Override
  public double aplicar(Pedido pedido) {
    return pedido.total() * (1 - porcentaje / 100);
  }

  @Override
  public String getNombre() {
    return "Descuento del " + porcentaje + "%";
  }
}
