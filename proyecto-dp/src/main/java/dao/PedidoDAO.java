package dao;

import com.utp.restaurante.database.AccesoDB;
import entity.PedidoEntity;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
        try {
            cn = AccesoDB.getConnection();
            cn.setAutoCommit(false);

            sql = "INSERT INTO pedidos (id_cliente, id_sucursal, canal, total, estado, fecha) VALUES (?, ?, ?, ?, ?, NOW())";
            ps = cn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, o.getIdCliente());
            ps.setInt(2, o.getIdSucursal());
            ps.setString(3, o.getCanal());
            ps.setDouble(4, o.getTotal());
            ps.setString(5, o.getEstado());
            ps.executeUpdate();

            rs = ps.getGeneratedKeys();
            if (rs.next()) {
                idGenerado = rs.getInt(1);
                o.setIdPedido(idGenerado);
            }
            rs.close();
            ps.close();

            cn.commit();
        } catch (Exception e) {
            try { cn.rollback(); } catch (Exception ex) {}
            throw e;
        } finally {
            cn.close();
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
                p.setCanal(rs.getString("canal"));
                p.setTotal(rs.getDouble("total"));
                p.setEstado(rs.getString("estado"));
                p.setFecha(rs.getString("fecha"));
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
                p.setCanal(rs.getString("canal"));
                p.setTotal(rs.getDouble("total"));
                p.setEstado(rs.getString("estado"));
                p.setFecha(rs.getString("fecha"));
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
                p.setCanal(rs.getString("canal"));
                p.setTotal(rs.getDouble("total"));
                p.setEstado(rs.getString("estado"));
                p.setFecha(rs.getString("fecha"));
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
}