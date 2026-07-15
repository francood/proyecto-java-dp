package core.productos;

/*PRINCIPIO LSP . Plato extiende a producto*/
//Platos a la carta
public class Plato extends Producto{

    public Plato(String nombre, double precio) {
        super(nombre, precio);
    }

    @Override
    public String getId() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    

}
