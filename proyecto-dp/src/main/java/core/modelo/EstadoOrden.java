package core.modelo;

public enum EstadoOrden {

    /*se declara el enum como clase independiente para evitar que el estado 
      de la orden dependa de la clase orden. De esta manera no viola el 
      principio DIP
    */
    
    Pendiente, Confirmado, En_Preparacion, Listo, Enviado, Cancelado
}
