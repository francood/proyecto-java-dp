package restaurante;

import java.util.ArrayList;
import java.util.List;

public class Pedido {

    /*SRP PRINCIPIO DE RESPONSABILIDAD UNICA*/
    private String numeroOrden;
    private TipoCanal canal;
    private List<Item> items;
    private Cliente cliente;
    private String numeroMesa;
    private String direccionDelivery;
    private String codigoRecojo;
    private String repartidor;
    private Restaurante sucursal;
    private EstadoOrden estadoActual;
    private List<EstadoOrden> historialEstados;

    public Pedido(String numeroOrden, TipoCanal canal, Cliente cliente, Restaurante sucursal) {
        this.numeroOrden = numeroOrden;
        this.canal = canal;
        this.cliente = cliente;
        this.sucursal = sucursal;
        this.estadoActual = EstadoOrden.Pendiente;
        this.historialEstados = new ArrayList<>();
        this.historialEstados.add(estadoActual);
        this.items = new ArrayList<>();
    }

    //Getters
    public String getNumeroOrden() {
        return numeroOrden;
    }

    public EstadoOrden getEstado() {
        return estadoActual;
    }

    public TipoCanal getCanal() {
        return canal;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public List<Item> getItems() {
        return items;
    }

    public List<EstadoOrden> getHistorialEstados() {
        return historialEstados;
    }

    //Métodos
    public void agregarItem(Item item) {
        items.add(item);
    }

    public double total() {
        double suma = 0;
        for (Item item : items) {
            suma += item.calcularSubtotal();
        }
        return suma;
    }
    //Getters y Setters|A3|
    public String getNumeroMesa() {
        return numeroMesa;
    }

    public void setNumeroMesa(String numeroMesa) {
        this.numeroMesa = numeroMesa;
    }

    public String getDireccionDelivery() {
        return direccionDelivery;
    }

    public void setDireccionDelivery(String direccionDelivery) {
        this.direccionDelivery = direccionDelivery;
    }

    public String getCodigoRecojo() {
        return codigoRecojo;
    }

    public void setCodigoRecojo(String codigoRecojo) {
        this.codigoRecojo = codigoRecojo;
    }

    public String getRepartidor() {
        return repartidor;
    }

    public void setRepartidor(String repartidor) {
        this.repartidor = repartidor;
    }

    //Métodos para cambiar el estad del pedido
    public void confirmarPedido() {
        if (items.isEmpty()) {
            throw new EstadoInvalidoException("No se puede confirmar un pedido sin items");
        }
        estadoActual = EstadoOrden.Confirmado;
        historialEstados.add(estadoActual);
    }

    public void enPreparacion() {
        if (estadoActual != EstadoOrden.Confirmado) {
            throw new EstadoInvalidoException("Solo se puede preparar un pedido confirmado");
        }
        estadoActual = EstadoOrden.En_Preparacion;
        historialEstados.add(estadoActual);
    }

    public void listo() {
        if (estadoActual != EstadoOrden.En_Preparacion) {
            throw new EstadoInvalidoException("Aún no está listo el pedido");
        }
        estadoActual = EstadoOrden.Listo;
        historialEstados.add(estadoActual);
    }

    public void cancelar() {
        items.clear();
        estadoActual = EstadoOrden.Cancelado;
        historialEstados.add(estadoActual);
    }

}
