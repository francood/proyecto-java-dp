package dao;

import conexion.AccesoDB;
import conexion.AccesoDB_main;
import entity.EmpleadoEntity;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmpleadoDAO {

    private Connection cn = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;
    private String sql = "";

    private String generarCodigo() throws SQLException {
        String cod = "";
        String sqlSelect = "SELECT valor FROM control WHERE parametro = 'Empleados'";
        String sqlUpdate = "UPDATE control SET valor = valor + 1 WHERE parametro = 'Empleados'";

        ps = cn.prepareStatement(sqlSelect);
        rs = ps.executeQuery();
        if (rs.next()) {
            int cont = rs.getInt(1);
            cod = String.format("E%03d", cont);
        }
        rs.close();
        ps.close();

        ps = cn.prepareStatement(sqlUpdate);
        ps.executeUpdate();
        ps.close();

        return cod;
    }

    public void crear(EmpleadoEntity o) throws Exception {
        try {
            cn = AccesoDB.getConnection();
            cn.setAutoCommit(false);

            String cod = generarCodigo();
            o.setIdEmpleado(cod);

            sql = "INSERT INTO empleados (id_empleado, nombre, apellidos, email, usuario, clave) VALUES (?, ?, ?, ?, ?, ?)";
            ps = cn.prepareStatement(sql);
            ps.setString(1, o.getIdEmpleado());
            ps.setString(2, o.getNombre());
            ps.setString(3, o.getApellidos());
            ps.setString(4, o.getEmail());
            ps.setString(5, o.getUsuario());
            ps.setString(6, o.getClave());
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

    public List<EmpleadoEntity> listarTodos() throws Exception {
        List<EmpleadoEntity> lista = new ArrayList<>();
        try {
            cn = AccesoDB.getConnection();
            sql = "SELECT * FROM empleados ORDER BY apellidos";
            ps = cn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                EmpleadoEntity e = new EmpleadoEntity();
                e.setIdEmpleado(rs.getString("id_empleado"));
                e.setNombre(rs.getString("nombre"));
                e.setApellidos(rs.getString("apellidos"));
                e.setEmail(rs.getString("email"));
                e.setUsuario(rs.getString("usuario"));
                e.setClave(rs.getString("clave"));
                lista.add(e);
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

    public EmpleadoEntity buscarPorId(String id) throws Exception {
        EmpleadoEntity e = null;
        try {
            cn = AccesoDB.getConnection();
            sql = "SELECT * FROM empleados WHERE id_empleado = ?";
            ps = cn.prepareStatement(sql);
            ps.setString(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                e = new EmpleadoEntity();
                e.setIdEmpleado(rs.getString("id_empleado"));
                e.setNombre(rs.getString("nombre"));
                e.setApellidos(rs.getString("apellidos"));
                e.setEmail(rs.getString("email"));
                e.setUsuario(rs.getString("usuario"));
                e.setClave(rs.getString("clave"));
            }
            rs.close();
            ps.close();
        } catch (Exception ex) {
            throw ex;
        } finally {
            cn.close();
        }
        return e;
    }

    public EmpleadoEntity buscarPorUsuario(String usuario) throws Exception {
        EmpleadoEntity e = null;
        try {
            cn = AccesoDB.getConnection();
            sql = "SELECT * FROM empleados WHERE usuario = ?";
            ps = cn.prepareStatement(sql);
            ps.setString(1, usuario);
            rs = ps.executeQuery();
            if (rs.next()) {
                e = new EmpleadoEntity();
                e.setIdEmpleado(rs.getString("id_empleado"));
                e.setNombre(rs.getString("nombre"));
                e.setApellidos(rs.getString("apellidos"));
                e.setEmail(rs.getString("email"));
                e.setUsuario(rs.getString("usuario"));
                e.setClave(rs.getString("clave"));
            }
            rs.close();
            ps.close();
        } catch (Exception ex) {
            throw ex;
        } finally {
            cn.close();
        }
        return e;
    }

    public boolean validarCredenciales(String usuario, String clave) throws Exception {
        boolean valido = false;
        try {
            cn = AccesoDB.getConnection();
            sql = "SELECT * FROM empleados WHERE usuario = ? AND clave = ?";
            ps = cn.prepareStatement(sql);
            ps.setString(1, usuario);
            ps.setString(2, clave);
            rs = ps.executeQuery();
            valido = rs.next();
            rs.close();
            ps.close();
        } catch (Exception e) {
            throw e;
        } finally {
            cn.close();
        }
        return valido;
    }

    public void actualizar(EmpleadoEntity o) throws Exception {
        try {
            cn = AccesoDB.getConnection();
            sql = "UPDATE empleados SET nombre = ?, apellidos = ?, "
                    + "email = ?, usuario = ?, clave = ? "
                    + "WHERE id_empleado = ?";
            ps = cn.prepareStatement(sql);
            ps.setString(1, o.getNombre());
            ps.setString(2, o.getApellidos());
            ps.setString(3, o.getEmail());
            ps.setString(4, o.getUsuario());
            ps.setString(5, o.getClave());
            ps.setString(6, o.getIdEmpleado());
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
            sql = "DELETE FROM empleados WHERE id_empleado = ?";
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
