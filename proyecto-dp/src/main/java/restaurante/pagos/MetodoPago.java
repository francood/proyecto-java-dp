package restaurante.pagos;

public interface MetodoPago {
    void procesarPago(double monto);
    String getDescripcion();
}