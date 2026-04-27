package restaurante;

public enum EstadoOrden {

    /*se declara el enum como clase independiente para evitar que el estado 
      de la orden dependa de la clase orden. De esta manera no viola el 
      principio DIP
    */
    
    PENDIENTE, CONFIRMADO, EN_PREPARACION, LISTO, ENVIADO, CANCELADO
}
