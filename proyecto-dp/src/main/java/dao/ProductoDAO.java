package dao;

import conexion.AccesoDB;
import conexion.AccesoDB_main;
import entity.ProductoEntity;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAO {

    private Connection cn = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;
    private String sql = "";

    private String generarCodigo() throws SQLException {
        String cod = "";
        String sqlSelect = "SELECT valor FROM control WHERE parametro = 'Productos'";
        String sqlUpdate = "UPDATE control SET valor = valor + 1 WHERE parametro = 'Productos'";

        ps = cn.prepareStatement(sqlSelect);
        rs = ps.executeQuery();
        if (rs.next()) {
            int cont = rs.getInt(1);
            cod = String.format("P%03d", cont);
        }
        rs.close();
        ps.close();

        ps = cn.prepareStatement(sqlUpdate);
        ps.executeUpdate();
        ps.close();

        return cod;
    }

    public void crear(ProductoEntity o) throws Exception {
        try {
            cn = AccesoDB.getConnection();
            cn.setAutoCommit(false);

            String cod = generarCodigo();
            o.setIdProducto(cod);

            sql = "INSERT INTO productos (id_producto, nombre, precio, stock, tipo, tamanio) VALUES (?, ?, ?, ?, ?, ?)";
            ps = cn.prepareStatement(sql);
            ps.setString(1, o.getIdProducto());
            ps.setString(2, o.getNombre());
            ps.setDouble(3, o.getPrecio());
            ps.setInt(4, o.getStock());
            ps.setString(5, o.getTipo() != null ? o.getTipo() : "PLATO");
            ps.setString(6, o.getTamanio()); // puede ser null
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

    public List<ProductoEntity> listarTodos() throws Exception {
        List<ProductoEntity> lista = new ArrayList<>();
        try {
            cn = AccesoDB.getConnection();
            sql = "SELECT id_producto, nombre, precio, stock, tipo, tamanio FROM productos ORDER BY nombre";
            ps = cn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                ProductoEntity p = new ProductoEntity();
                p.setIdProducto(rs.getString("id_producto"));
                p.setNombre(rs.getString("nombre"));
                p.setPrecio(rs.getDouble("precio"));
                p.setStock(rs.getInt("stock"));
                p.setTipo(rs.getString("tipo"));
                p.setTamanio(rs.getString("tamanio"));
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

    public ProductoEntity buscarPorId(String id) throws Exception {
        ProductoEntity p = null;
        try {
            cn = AccesoDB.getConnection();
            sql = "SELECT id_producto, nombre, precio, stock, tipo, tamanio FROM productos WHERE id_producto = ?";
            ps = cn.prepareStatement(sql);
            ps.setString(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                p = new ProductoEntity();
                p.setIdProducto(rs.getString("id_producto"));
                p.setNombre(rs.getString("nombre"));
                p.setPrecio(rs.getDouble("precio"));
                p.setStock(rs.getInt("stock"));
                p.setTipo(rs.getString("tipo"));
                p.setTamanio(rs.getString("tamanio"));
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

    public ProductoEntity buscarPorNombre(String nombre) throws Exception {
        ProductoEntity p = null;
        try {
            cn = AccesoDB.getConnection();
            sql = "SELECT id_producto, nombre, precio, stock, tipo, tamanio FROM productos WHERE nombre = ?";
            ps = cn.prepareStatement(sql);
            ps.setString(1, nombre);
            rs = ps.executeQuery();
            if (rs.next()) {
                p = new ProductoEntity();
                p.setIdProducto(rs.getString("id_producto"));
                p.setNombre(rs.getString("nombre"));
                p.setPrecio(rs.getDouble("precio"));
                p.setStock(rs.getInt("stock"));
                p.setTipo(rs.getString("tipo"));
                p.setTamanio(rs.getString("tamanio"));
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

        public void eliminar(String idProducto) throws Exception {
            String sql = "DELETE FROM productos WHERE id_producto = ?";
        try (Connection cn = AccesoDB.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, idProducto);
            ps.executeUpdate();
        }
    }

    public void actualizarStock(String idProducto, int nuevoStock) throws Exception {
        try {
            cn = AccesoDB.getConnection();
            sql = "UPDATE productos SET stock = ? WHERE id_producto = ?";
            ps = cn.prepareStatement(sql);
            ps.setInt(1, nuevoStock);
            ps.setString(2, idProducto);
            ps.executeUpdate();
            ps.close();
        } catch (Exception e) {
            throw e;
        } finally {
            cn.close();
        }
    }

    public void descontarStock(String idProducto, int cantidad) throws Exception {
    String sql = "UPDATE productos SET stock = stock - ? WHERE id_producto = ? AND stock >= ?";
    try (Connection cn = AccesoDB.getConnection();
         PreparedStatement ps = cn.prepareStatement(sql)) {
        ps.setInt(1, cantidad);
        ps.setString(2, idProducto);
        ps.setInt(3, cantidad);
        int filas = ps.executeUpdate();
        if (filas == 0) {
            throw new Exception("Stock insuficiente para el producto: " + idProducto);
        }
    }
}
    
    public void actualizar(ProductoEntity o) throws Exception {
        String sql = "UPDATE productos SET nombre=?, precio=?, stock=?, tipo=?, tamanio=? WHERE id_producto=?";
        try (Connection cn = AccesoDB.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, o.getNombre());
            ps.setDouble(2, o.getPrecio());
            ps.setInt(3, o.getStock());
            ps.setString(4, o.getTipo());
            ps.setString(5, o.getTamanio());
            ps.setString(6, o.getIdProducto());
            ps.executeUpdate();
        }
    }
}
