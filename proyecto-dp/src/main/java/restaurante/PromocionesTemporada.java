package restaurante;

import java.util.List;
import restaurante.Item;

public class PromocionesTemporada{
    private String nombre;
    private double descuentoPorcentaje;

    public PromocionesTemporada(String nombre, double descuentoPorcentaje) {
        this.nombre = nombre;
        this.descuentoPorcentaje = descuentoPorcentaje;
    }

    public double aplicarPromocion(List<Item>items){
        double subtotal=0;
        for (Item item:items) {
            subtotal=subtotal+item.calcularSubtotal();
        }
        
        double descuento=subtotal*descuentoPorcentaje/100;
        return subtotal-descuento;
    }

}
