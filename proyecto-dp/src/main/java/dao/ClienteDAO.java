package dao;

import conexion.AccesoDB;
import conexion.AccesoDB_main;
import com.utp.restaurante.entity.ClienteEntity;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {

    private Connection cn = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;
    private String sql = "";

    // Genera un ID autoincrementable con formato "C001", "C002", ...
    private String generarCodigo() throws SQLException {
        String cod = "";
        String sqlSelect = "SELECT valor FROM control WHERE parametro = 'Clientes'";
        String sqlUpdate = "UPDATE control SET valor = valor + 1 WHERE parametro = 'Clientes'";

        ps = cn.prepareStatement(sqlSelect);
        rs = ps.executeQuery();
        if (rs.next()) {
            int cont = rs.getInt(1);
            cod = String.format("C%03d", cont);
        }
        rs.close();
        ps.close();

        ps = cn.prepareStatement(sqlUpdate);
        ps.executeUpdate();
        ps.close();

        return cod;
    }

    public void crear(ClienteEntity o) throws Exception {
        try {
            cn = AccesoDB.getConnection();
            cn.setAutoCommit(false);

            String cod = generarCodigo();
            o.setIdCliente(cod);

            sql = "INSERT INTO clientes (id_cliente, nombre, direccion, telefono) VALUES (?, ?, ?, ?)";
            ps = cn.prepareStatement(sql);
            ps.setString(1, o.getIdCliente());
            ps.setString(2, o.getNombre());
            ps.setString(3, o.getDireccion());
            ps.setString(4, o.getTelefono());
            ps.executeUpdate();
            ps.close();

            cn.commit();
        } catch (Exception e) {
            try { cn.rollback(); } catch (Exception ex) {}
            throw e;
        } finally {
            cn.close();
        }
    }

    public List<ClienteEntity> listarTodos() throws Exception {
        List<ClienteEntity> lista = new ArrayList<>();
        try {
            cn = AccesoDB.getConnection();
            sql = "SELECT * FROM clientes ORDER BY nombre";
            ps = cn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                ClienteEntity cli = new ClienteEntity();
                cli.setIdCliente(rs.getString("id_cliente"));
                cli.setNombre(rs.getString("nombre"));
                cli.setDireccion(rs.getString("direccion"));
                cli.setTelefono(rs.getString("telefono"));
                lista.add(cli);
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

    public ClienteEntity buscarPorId(String id) throws Exception {
        ClienteEntity cli = null;
        try {
            cn = AccesoDB.getConnection();
            sql = "SELECT * FROM clientes WHERE id_cliente = ?";
            ps = cn.prepareStatement(sql);
            ps.setString(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                cli = new ClienteEntity();
                cli.setIdCliente(rs.getString("id_cliente"));
                cli.setNombre(rs.getString("nombre"));
                cli.setDireccion(rs.getString("direccion"));
                cli.setTelefono(rs.getString("telefono"));
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            throw e;
        } finally {
            cn.close();
        }
        return cli;
    }

    
    public void actualizar(ClienteEntity o) throws Exception {
        try {
            cn = AccesoDB.getConnection();
            sql = "UPDATE clientes SET nombre = ?, direccion = ?, telefono = ? "
                    + "WHERE id_cliente = ?";
            ps = cn.prepareStatement(sql);
            ps.setString(1, o.getNombre());
            ps.setString(2, o.getDireccion());
            ps.setString(3, o.getTelefono());
            ps.setString(4, o.getIdCliente());
            ps.executeUpdate();
            ps.close();
        } catch (Exception e) {
            throw e;
        } finally {
            cn.close();
        }
    }
    public void eliminar(String id) throws Exception {
        try {
            cn = AccesoDB.getConnection();
            sql = "DELETE FROM clientes WHERE id_cliente = ?";
            ps = cn.prepareStatement(sql);
            ps.setString(1, id);
            ps.executeUpdate();
            ps.close();
        } catch (Exception e) {
            throw e;
        } finally {
            cn.close();
        }
    }
}
