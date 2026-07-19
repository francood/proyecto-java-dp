package core.memento;

import core.modelo.EstadoOrden;
import core.modelo.Item;
import core.modelo.Pedido;
import core.modelo.TipoCanal;
import java.util.ArrayList;
import java.util.List;

public class Memento {
    private String numeroOrden;
    private TipoCanal canal;
    private List<Item> items;
    private EstadoOrden estado;
    private String numeroMesa;
    private String direccionDelivery;
    private String codigoRecojo;
    private String repartidor;

    public Memento(Pedido pedido) {
        this.numeroOrden = pedido.getNumeroOrden();
        this.canal = pedido.getCanal();
        this.items = new ArrayList<>(pedido.getItems());
        this.estado = pedido.getEstado();
        this.numeroMesa = pedido.getNumeroMesa();
        this.direccionDelivery = pedido.getDireccionDelivery();
        this.codigoRecojo = pedido.getCodigoRecojo();
        this.repartidor = pedido.getRepartidor();
    }

    // Getters
    public String getNumeroOrden() { return numeroOrden; }
    public TipoCanal getCanal() { return canal; }
    public List<Item> getItems() { return items; }
    public EstadoOrden getEstado() { return estado; }
    public String getNumeroMesa() { return numeroMesa; }
    public String getDireccionDelivery() { return direccionDelivery; }
    public String getCodigoRecojo() { return codigoRecojo; }
    public String getRepartidor() { return repartidor; }
}