package restaurante.builder;

import restaurante.TipoCanal;
import java.util.ArrayList;
import java.util.List;
import restaurante.Cliente;
import restaurante.Item;
import restaurante.Pedido;
import restaurante.Sucursal;

/**
 * Builder para construir objetos Pedido paso a paso.
 * Evita constructores con muchos parámetros y permite agregar items
 * antes de construir el pedido.
 */
public class PedidoBuilder {

    // Campos obligatorios
    private String numeroOrden;
    private TipoCanal canal;
    private Cliente cliente;
    private Sucursal sucursal;

    // Campos opcionales
    private String numeroMesa;
    private String direccionDelivery;
    private String codigoRecojo;
    private String repartidor;

    // Items del pedido
    private List<Item> items = new ArrayList<>();

    // Métodos para campos obligatorios (obligatorios)
    public PedidoBuilder conNumeroOrden(String numeroOrden) {
        this.numeroOrden = numeroOrden;
        return this;
    }

    public PedidoBuilder conCanal(TipoCanal canal) {
        this.canal = canal;
        return this;
    }

    public PedidoBuilder conCliente(Cliente cliente) {
        this.cliente = cliente;
        return this;
    }

    public PedidoBuilder conSucursal(Sucursal sucursal) {
        this.sucursal = sucursal;
        return this;
    }

    // Métodos para campos opcionales (fluidos)
    public PedidoBuilder conNumeroMesa(String numeroMesa) {
        this.numeroMesa = numeroMesa;
        return this;
    }

    public PedidoBuilder conDireccionDelivery(String direccionDelivery) {
        this.direccionDelivery = direccionDelivery;
        return this;
    }

    public PedidoBuilder conCodigoRecojo(String codigoRecojo) {
        this.codigoRecojo = codigoRecojo;
        return this;
    }

    public PedidoBuilder conRepartidor(String repartidor) {
        this.repartidor = repartidor;
        return this;
    }

    // Método para agregar items (puede ser encadenado)
    public PedidoBuilder agregarItem(Item item) {
        this.items.add(item);
        return this;
    }

    // Método build: construye el Pedido y asigna los atributos
    public Pedido build() {
        // Validación básica
        if (numeroOrden == null || canal == null || cliente == null || sucursal == null) {
            throw new IllegalArgumentException("Faltan campos obligatorios para crear el pedido");
        }

        // Crear el pedido con el constructor existente
        Pedido pedido = new Pedido(numeroOrden, canal, cliente, sucursal);

        // Asignar atributos opcionales
        if (numeroMesa != null) pedido.setNumeroMesa(numeroMesa);
        if (direccionDelivery != null) pedido.setDireccionDelivery(direccionDelivery);
        if (codigoRecojo != null) pedido.setCodigoRecojo(codigoRecojo);
        if (repartidor != null) pedido.setRepartidor(repartidor);

        // Agregar items
        for (Item item : items) {
            pedido.agregarItem(item);
        }

        return pedido;
    }
}