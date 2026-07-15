package core.strategy;

import core.modelo.Pedido;

public interface Promocion {
    double aplicar(Pedido pedido);
    String getNombre();

    // Método por defecto: por defecto aplica siempre.
    default boolean esAplicable(Pedido pedido) {
        return true;
    }
}