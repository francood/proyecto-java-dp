package core.modelo;

import core.bridge.CanalImplementor;
import core.estados.EstadoPedido;
import java.util.ArrayList;
import java.util.List;
import core.eventos.Observador;
import core.estados.PendienteState;
import core.exceptions.EstadoInvalidoException;
import core.exceptions.StockInsuficienteException;
import core.memento.Memento;
import core.pagos.MetodoPago;

public class Pedido {
    // SRP: Responsabilidad Única
    private String numeroOrden;
    private TipoCanal canal;
    private List<Item> items;
    private Cliente cliente;
    private String numeroMesa;
    private String direccionDelivery;
    private String codigoRecojo;
    private String repartidor;
    private Sucursal sucursal;
    private MetodoPago metodoPago;
    // Estado e historial
    private EstadoOrden estadoActual;
    private List<EstadoOrden> historialEstados;
    private List<Observador> observadores;
    private CanalImplementor canalImplementor; //bridge
    private EstadoPedido estado; //state

    public Pedido(String numeroOrden, TipoCanal canal, Cliente cliente, Sucursal sucursal) {
        this.numeroOrden = numeroOrden;
        this.canal = canal;
        this.cliente = cliente;
        this.sucursal = sucursal;
        this.estadoActual = EstadoOrden.Pendiente;
        this.historialEstados = new ArrayList<>();
        this.historialEstados.add(estadoActual);
        this.items = new ArrayList<>();
        this.observadores = new ArrayList<>();

        // Inicializar State (por defecto Pendiente)
        this.estado = new PendienteState();
    }

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

    public Sucursal getSucursal() {
        return sucursal;
    }

    //métodos items
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

    //getter y setters
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
    
    public void setMetodoPago(MetodoPago metodoPago) {
    this.metodoPago = metodoPago;
    }

        public MetodoPago getMetodoPago() {
        return metodoPago;
    }

    //Bridge
    public void setCanalImplementor(CanalImplementor implementor) {
        this.canalImplementor = implementor;
    }
    
    public CanalImplementor getCanalImplementor() {
        return canalImplementor;
}

    public void procesarCanal() {
        if (canalImplementor != null) {
            canalImplementor.procesar(this);
        }
    }

    //State
    public EstadoPedido getEstadoState() {
        return estado;
    }

    public void setEstadoState(EstadoPedido nuevoEstado) {
        this.estado = nuevoEstado;
    }

    //Memento
    public Memento guardarEstado() {
        return new Memento(this);
    }

    public void restaurarEstado(Memento memento) {
        this.numeroOrden = memento.getNumeroOrden();
        this.canal = memento.getCanal();
        this.items = new ArrayList<>(memento.getItems());
        this.estadoActual = memento.getEstado();
        this.numeroMesa = memento.getNumeroMesa();
        this.direccionDelivery = memento.getDireccionDelivery();
        this.codigoRecojo = memento.getCodigoRecojo();
        this.repartidor = memento.getRepartidor();
        // (Opcional) también se podría restaurar el historial, pero no se guarda en Memento
    }

    // métodos para cambiar estado del pedido
    public void confirmarPedido() throws StockInsuficienteException {
        if (items.isEmpty()) {
            throw new EstadoInvalidoException("No se puede confirmar un pedido sin items");
        }

        // Verificar y descontar stock (E4)
        Inventario inventario = Inventario.getInstancia();
        inventario.verificarStock(this);
        for (Item item : items) {
            inventario.descontarStock(item.getProducto(), item.getCantidad());
        }

        // Resto del metodo original
        EstadoOrden anterior = this.estadoActual;
        this.estadoActual = EstadoOrden.Confirmado;
        historialEstados.add(estadoActual);
        notificarObservadores(anterior, estadoActual);
    }

    public void enPreparacion() {
        if (estadoActual != EstadoOrden.Confirmado) {
            throw new EstadoInvalidoException("Solo se puede preparar un pedido confirmado");
        }
        EstadoOrden anterior = this.estadoActual;
        this.estadoActual = EstadoOrden.En_Preparacion;
        historialEstados.add(estadoActual);
        notificarObservadores(anterior, estadoActual);
    }

    public void listo() {
        if (estadoActual != EstadoOrden.En_Preparacion) {
            throw new EstadoInvalidoException("Aún no está listo el pedido");
        }
        EstadoOrden anterior = this.estadoActual;
        this.estadoActual = EstadoOrden.Listo;
        historialEstados.add(estadoActual);
        notificarObservadores(anterior, estadoActual);
    }

    public void enviar() {
        if (estadoActual != EstadoOrden.Listo) {
            throw new EstadoInvalidoException("Solo se puede enviar un pedido que esté listo");
        }
        EstadoOrden anterior = this.estadoActual;
        this.estadoActual = EstadoOrden.Enviado;
        historialEstados.add(estadoActual);
        notificarObservadores(anterior, estadoActual);
        PersistenciaPedidos.guardarPedido(this);
    }

    public void cancelar() {
        if (estadoActual == EstadoOrden.Enviado) {
            throw new EstadoInvalidoException("No se puede cancelar un pedido ya enviado");
        }
        EstadoOrden anterior = this.estadoActual;
        items.clear();
        this.estadoActual = EstadoOrden.Cancelado;
        historialEstados.add(estadoActual);
        notificarObservadores(anterior, estadoActual);
        PersistenciaPedidos.guardarPedido(this);
    }

   // MÉTODOS PARA OBSERVADORES
    public void agregarObservador(Observador obs) {
        observadores.add(obs);
    }

    public void removerObservador(Observador obs) {
        observadores.remove(obs);
    }

    private void notificarObservadores(EstadoOrden anterior, EstadoOrden nuevo) {
        for (Observador obs : observadores) {
            obs.actualizar(this, anterior, nuevo);
        }
    }
}