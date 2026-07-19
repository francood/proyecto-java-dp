package dao;

import conexion.AccesoDB;
import conexion.AccesoDB_main;
import com.utp.restaurante.entity.SucursalEntity;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class SucursalDAO {

    private Connection cn = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;
    private String sql = "";

    public List<SucursalEntity> listarTodos() throws Exception {
        List<SucursalEntity> lista = new ArrayList<>();
        try {
            cn = AccesoDB.getConnection();
            sql = "SELECT * FROM sucursales ORDER BY nombre";
            ps = cn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                SucursalEntity s = new SucursalEntity();
                s.setIdSucursal(rs.getInt("id_sucursal"));
                s.setNombre(rs.getString("nombre"));
                s.setDireccion(rs.getString("direccion"));
                s.setTelefono(rs.getString("telefono"));
                lista.add(s);
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

    public SucursalEntity buscarPorId(int id) throws Exception {
        SucursalEntity s = null;
        try {
            cn = AccesoDB.getConnection();
            sql = "SELECT * FROM sucursales WHERE id_sucursal = ?";
            ps = cn.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                s = new SucursalEntity();
                s.setIdSucursal(rs.getInt("id_sucursal"));
                s.setNombre(rs.getString("nombre"));
                s.setDireccion(rs.getString("direccion"));
                s.setTelefono(rs.getString("telefono"));
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            throw e;
        } finally {
            cn.close();
        }
        return s;
    }
}