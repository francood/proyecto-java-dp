package core.modelo;

public enum TipoCanal {

    /*se declara el enum como clase independiente para evitar que el tipo 
      de canal de venta de la orden dependa de la clase orden. De esta manera no viola el 
      principio DIP
    */
    
    SALON, PARA_LLEVAR, DELIVERY_PROPIO, DELIVERY_EXTERNO
}
