package restaurante;

import java.util.ArrayList;
import java.util.List;

public class Pedido {
    
    /*SRP PRINCIPIO DE RESPONSABILIDAD UNICA*/
    
    private String numeroOrden;
    private EstadoOrden estado;
    private TipoCanal canal;
    private List<Item> items;
    private Cliente cliente;

    public Pedido(String numeroOrden, TipoCanal canal, Cliente cliente) {
        this.numeroOrden = numeroOrden;
        this.canal = canal;
        this.cliente=cliente;
        this.estado = EstadoOrden.PENDIENTE;
        this.items= new ArrayList<>();
    }
    //Getters
    public String getNumeroOrden() {return numeroOrden;}
    public EstadoOrden getEstado() {return estado;}
    public TipoCanal getCanal() {return canal;}
    public Cliente getCliente(){return cliente;}
    public List<Item>getItems(){
        return items;
    }
   
    
    //Métodos
    
    public void agregarItem(Item item){items.add(item);}
    
    public double total(){
    double suma = 0;
    for (Item item : items) {
        suma += item.calcularSubtotal();
    }
    return suma;
    }
    
    //Métodos para cambiar el estad del pedido
    public void confirmarPedido(){
        if (items.isEmpty()) System.out.println("No se puede confirmar un pedido sin items");
        else this.estado=EstadoOrden.CONFIRMADO;
    }
    
    public void enPreparacion(){
        if(estado!=EstadoOrden.CONFIRMADO) System.out.println("Solo se puede preparar un pedido confirmado");
        else this.estado=EstadoOrden.EN_PREPARACION;
    }
    public void listo(){
        if(estado!=EstadoOrden.EN_PREPARACION) System.out.println("Aún no está listo el pedido");
        else this.estado=EstadoOrden.LISTO;
    }
    
    public void cancelar(){
        items.clear();
        this.estado=EstadoOrden.CANCELADO;
    }

}
