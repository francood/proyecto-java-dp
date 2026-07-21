package dao;

import conexion.AccesoDB;
import conexion.AccesoDB_main;
import entity.PedidoEntity;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import entity.EmpleadoEntity;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PedidoDAO {

    private Connection cn = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;
    private String sql = "";

    // Guarda un pedido y devuelve el ID generado (AUTO_INCREMENT)
    
    public int guardar(PedidoEntity o) throws Exception {
    int idGenerado = 0;
    String sql = "INSERT INTO pedidos (id_cliente, id_sucursal, id_empleado, canal, total, estado, fecha, calificacion, comentario) " +
                 "VALUES (?, ?, ?, ?, ?, ?, GETDATE(), ?, ?)";
    try (Connection cn = AccesoDB.getConnection();
         PreparedStatement ps = cn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
        
        ps.setString(1, o.getIdCliente());
        ps.setInt(2, o.getIdSucursal());
        ps.setString(3, o.getIdEmpleado()); // puede ser null
        ps.setString(4, o.getCanal());
        ps.setDouble(5, o.getTotal());
        ps.setString(6, o.getEstado());
        // La fecha se pone con GETDATE() en la sentencia, no la pasamos como parámetro
        ps.setObject(7, o.getCalificacion() != null ? o.getCalificacion() : 0); // columna calificacion
        ps.setString(8, o.getComentario()); // columna comentario

        ps.executeUpdate();

        try (ResultSet rs = ps.getGeneratedKeys()) {
            if (rs.next()) {
                idGenerado = rs.getInt(1);
                o.setIdPedido(idGenerado);
            }
        }
    }
    return idGenerado;
}

    public List<PedidoEntity> listarTodos() throws Exception {
        List<PedidoEntity> lista = new ArrayList<>();
        try {
            cn = AccesoDB.getConnection();
            sql = "SELECT * FROM pedidos ORDER BY fecha DESC";
            ps = cn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                PedidoEntity p = new PedidoEntity();
                p.setIdPedido(rs.getInt("id_pedido"));
                p.setIdCliente(rs.getString("id_cliente"));
                p.setIdSucursal(rs.getInt("id_sucursal"));
                p.setIdEmpleado(rs.getString("id_empleado"));
                p.setCanal(rs.getString("canal"));
                p.setTotal(rs.getDouble("total"));
                p.setEstado(rs.getString("estado"));
                p.setFecha(rs.getString("fecha"));
                p.setCalificacion(rs.getInt("calificacion")); // puede ser 0 si es null
                p.setComentario(rs.getString("comentario"));
                lista.add(p);
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            throw e;
        } finally {
            cn.close();
        }
        return lista;
    }

    public List<PedidoEntity> listarPorCliente(String idCliente) throws Exception {
        List<PedidoEntity> lista = new ArrayList<>();
        try {
            cn = AccesoDB.getConnection();
            sql = "SELECT * FROM pedidos WHERE id_cliente = ? ORDER BY fecha DESC";
            ps = cn.prepareStatement(sql);
            ps.setString(1, idCliente);
            rs = ps.executeQuery();
            while (rs.next()) {
                PedidoEntity p = new PedidoEntity();
                p.setIdPedido(rs.getInt("id_pedido"));
                p.setIdCliente(rs.getString("id_cliente"));
                p.setIdSucursal(rs.getInt("id_sucursal"));
                p.setIdEmpleado(rs.getString("id_empleado"));
                p.setCanal(rs.getString("canal"));
                p.setTotal(rs.getDouble("total"));
                p.setEstado(rs.getString("estado"));
                p.setFecha(rs.getString("fecha"));
                p.setCalificacion(rs.getInt("calificacion")); // puede ser 0 si es null
                p.setComentario(rs.getString("comentario"));
                lista.add(p);
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            throw e;
        } finally {
            cn.close();
        }
        return lista;
    }

    public PedidoEntity buscarPorId(int id) throws Exception {
        PedidoEntity p = null;
        try {
            cn = AccesoDB.getConnection();
            sql = "SELECT * FROM pedidos WHERE id_pedido = ?";
            ps = cn.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                p = new PedidoEntity();
                p.setIdPedido(rs.getInt("id_pedido"));
                p.setIdCliente(rs.getString("id_cliente"));
                p.setIdSucursal(rs.getInt("id_sucursal"));
                p.setIdEmpleado(rs.getString("id_empleado"));
                p.setCanal(rs.getString("canal"));
                p.setTotal(rs.getDouble("total"));
                p.setEstado(rs.getString("estado"));
                p.setFecha(rs.getString("fecha"));
                p.setCalificacion(rs.getInt("calificacion")); // puede ser 0 si es null
                p.setComentario(rs.getString("comentario"));
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            throw e;
        } finally {
            cn.close();
        }
        return p;
    }
    
    public int buscarIdPorNumeroOrden(String numeroOrden) throws Exception {
    String sql = "SELECT id_pedido FROM pedidos WHERE numero_orden = ?";
    try (Connection cn = AccesoDB.getConnection();
         PreparedStatement ps = cn.prepareStatement(sql)) {
        ps.setString(1, numeroOrden);
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("id_pedido");
            }
        }
    }
    return 0;
}
    //para la view del cliente
    public void actualizarCalificacion(int idPedido, int calificacion, String comentario) throws Exception {
        String sql = "UPDATE pedidos SET calificacion = ?, comentario = ? WHERE id_pedido = ?";
        try (Connection cn = AccesoDB.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, calificacion);
            ps.setString(2, comentario);
            ps.setInt(3, idPedido);
            ps.executeUpdate();
        }
    }
    
   public void actualizarEstado(int idPedido, String nuevoEstado) throws Exception {
    String sql = "UPDATE pedidos SET estado = ? WHERE id_pedido = ?";
    try (Connection cn = AccesoDB.getConnection();
         PreparedStatement ps = cn.prepareStatement(sql)) {
        ps.setString(1, nuevoEstado);
        ps.setInt(2, idPedido);
        ps.executeUpdate();
    }
}
    
    public List<Object[]> obtenerTopProductos(int limite) throws Exception {
        List<Object[]> top = new ArrayList<>();
        String sql = "SELECT p.nombre, SUM(dp.cantidad) AS total_vendido " +
                     "FROM detalle_pedido dp " +
                     "JOIN productos p ON dp.id_producto = p.id_producto " +
                     "GROUP BY p.id_producto, p.nombre " +
                     "ORDER BY total_vendido DESC " +
                     "LIMIT ?";
        try (Connection cn = AccesoDB.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, limite);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Object[] fila = new Object[2];
                    fila[0] = rs.getString("nombre");
                    fila[1] = rs.getInt("total_vendido");
                    top.add(fila);
                }
            }
        }
        return top;
    }
}

