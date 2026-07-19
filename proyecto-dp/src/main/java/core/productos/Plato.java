package core.productos;

/*PRINCIPIO LSP . Plato extiende a producto*/
//Platos a la carta
public class Plato extends Producto{

    public Plato(String id, String nombre, double precio) {
        super(id, nombre, precio);
    }
}
