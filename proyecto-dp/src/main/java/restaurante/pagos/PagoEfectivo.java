package restaurante.pagos;

public class PagoEfectivo implements MetodoPago {
    @Override
    public void procesarPago(double monto) {
        System.out.printf("Pago en efectivo por S/%.2f recibido correctamente.\n", monto);
    }

    @Override
    public String getDescripcion() {
        return "Efectivo";
    }
}