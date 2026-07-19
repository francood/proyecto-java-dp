package entity;

public class PedidoEntity {

    private int idPedido;
    private String idCliente;
    private int idSucursal;
    private String idEmpleado;
    private String canal;
    private double total;
    private String estado;
    private String fecha;
    private Integer calificacion; // 1-5 estrellas, o null si no es calificado
    private String comentario; 
    public PedidoEntity() {
    }

    public PedidoEntity(int idPedido, String idCliente, int idSucursal, String canal, double total, String estado, String fecha) {
        this.idPedido = idPedido;
        this.idCliente = idCliente;
        this.idSucursal = idSucursal;
        this.canal = canal;
        this.total = total;
        this.estado = estado;
        this.fecha = fecha;
    }

    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public String getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(String idCliente) {
        this.idCliente = idCliente;
    }

    public int getIdSucursal() {
        return idSucursal;
    }

    public void setIdSucursal(int idSucursal) {
        this.idSucursal = idSucursal;
    }

    public String getCanal() {
        return canal;
    }

    public void setCanal(String canal) {
        this.canal = canal;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public Integer getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(Integer calificacion) {
        this.calificacion = calificacion;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public String getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(String idEmpleado) {
        this.idEmpleado = idEmpleado;
    }
    
    
}