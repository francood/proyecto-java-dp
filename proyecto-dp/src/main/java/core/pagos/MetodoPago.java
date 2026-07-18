package core.pagos;

public interface MetodoPago {
    void procesarPago(double monto);
    String getDescripcion();
}