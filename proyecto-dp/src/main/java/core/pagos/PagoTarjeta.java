package core.pagos;

import core.pagos.MetodoPago;

public class PagoTarjeta implements MetodoPago {
    @Override
    public void procesarPago(double monto) {
        System.out.printf("Procesando pago con tarjeta por S/%.2f... ¡Aprobado!\n", monto);
    }

    @Override
    public String getDescripcion() {
        return "Tarjeta (crédito/débito)";
    }
}