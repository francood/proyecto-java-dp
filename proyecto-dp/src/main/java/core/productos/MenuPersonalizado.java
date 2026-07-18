package core.productos;

import java.util.ArrayList;
import java.util.List;

/*

*/
public class MenuPersonalizado implements ProductoVendible {
    
    private String id;
    private ProductoVendible base;
    private List<String> nombresExtras;
    private List<Double> costosExtras;
    private List<String> ingredientesQuitados;
    private static int contador = 0;

    public MenuPersonalizado(ProductoVendible base) {
        this.id = base.getId()+"PER"+(++contador);
        this.base = base;
        this.nombresExtras = new ArrayList<>();
        this.costosExtras = new ArrayList<>();
        this.ingredientesQuitados = new ArrayList<>();
    }
    
    public ProductoVendible getBase() {
    return base;
}

    public void agregarExtra(String nombre, double costo) {
        nombresExtras.add(nombre);
        costosExtras.add(costo);
    }

    public void quitarIngrediente(String ingrediente) {
        ingredientesQuitados.add(ingrediente);
    }
    
    @Override
    public String getNombre() {
     return base.getNombre() + " personalizado";
    }

    @Override
    public double getPrecio() {
        double total = base.getPrecio();
        for(double costo: costosExtras){
        total= total+costo;
        }
    return total;
    }

    @Override
    public String getId() {
        return id;
    }
}
