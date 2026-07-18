package dao;

import conexion.AccesoDB;
import conexion.AccesoDB_main;
import entity.DetallePedidoEntity;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DetallePedidoDAO {

    private Connection cn = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;
    private String sql = "";

    public void guardar(DetallePedidoEntity o) throws Exception {
        try {
            cn = AccesoDB.getConnection();
            sql = "INSERT INTO detalle_pedido (id_pedido, id_producto, cantidad, subtotal) VALUES (?, ?, ?, ?)";
            ps = cn.prepareStatement(sql);
            ps.setInt(1, o.getIdPedido());
            ps.setString(2, o.getIdProducto());
            ps.setInt(3, o.getCantidad());
            ps.setDouble(4, o.getSubtotal());
            ps.executeUpdate();
            ps.close();
        } catch (Exception e) {
            throw e;
        } finally {
            cn.close();
        }
    }

    public void guardarBatch(List<DetallePedidoEntity> lista, int idPedido) throws Exception {
        try {
            cn = AccesoDB.getConnection();
            cn.setAutoCommit(false);

            sql = "INSERT INTO detalle_pedido (id_pedido, id_producto, cantidad, subtotal) VALUES (?, ?, ?, ?)";
            ps = cn.prepareStatement(sql);

            for (DetallePedidoEntity d : lista) {
                d.setIdPedido(idPedido);
                ps.setInt(1, d.getIdPedido());
                ps.setString(2, d.getIdProducto());
                ps.setInt(3, d.getCantidad());
                ps.setDouble(4, d.getSubtotal());
                ps.addBatch();
            }
            ps.executeBatch();
            ps.close();

            cn.commit();
        } catch (Exception e) {
            try { cn.rollback(); } catch (Exception ex) {}
            throw e;
        } finally {
            cn.close();
        }
    }

    public List<DetallePedidoEntity> listarPorPedido(int idPedido) throws Exception {
        List<DetallePedidoEntity> lista = new ArrayList<>();
        try {
            cn = AccesoDB.getConnection();
            sql = "SELECT * FROM detalle_pedido WHERE id_pedido = ?";
            ps = cn.prepareStatement(sql);
            ps.setInt(1, idPedido);
            rs = ps.executeQuery();
            while (rs.next()) {
                DetallePedidoEntity d = new DetallePedidoEntity();
                d.setIdDetalle(rs.getInt("id_detalle"));
                d.setIdPedido(rs.getInt("id_pedido"));
                d.setIdProducto(rs.getString("id_producto"));
                d.setCantidad(rs.getInt("cantidad"));
                d.setSubtotal(rs.getDouble("subtotal"));
                lista.add(d);
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
}