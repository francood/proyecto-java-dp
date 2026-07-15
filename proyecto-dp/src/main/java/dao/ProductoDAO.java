package dao;

import com.utp.restaurante.database.AccesoDB;
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

            sql = "INSERT INTO productos (id_producto, nombre, precio, stock) VALUES (?, ?, ?, ?)";
            ps = cn.prepareStatement(sql);
            ps.setString(1, o.getIdProducto());
            ps.setString(2, o.getNombre());
            ps.setDouble(3, o.getPrecio());
            ps.setInt(4, o.getStock());
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
            sql = "SELECT * FROM productos ORDER BY nombre";
            ps = cn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                ProductoEntity p = new ProductoEntity();
                p.setIdProducto(rs.getString("id_producto"));
                p.setNombre(rs.getString("nombre"));
                p.setPrecio(rs.getDouble("precio"));
                p.setStock(rs.getInt("stock"));
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
            sql = "SELECT * FROM productos WHERE id_producto = ?";
            ps = cn.prepareStatement(sql);
            ps.setString(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                p = new ProductoEntity();
                p.setIdProducto(rs.getString("id_producto"));
                p.setNombre(rs.getString("nombre"));
                p.setPrecio(rs.getDouble("precio"));
                p.setStock(rs.getInt("stock"));
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
            sql = "SELECT * FROM productos WHERE nombre = ?";
            ps = cn.prepareStatement(sql);
            ps.setString(1, nombre);
            rs = ps.executeQuery();
            if (rs.next()) {
                p = new ProductoEntity();
                p.setIdProducto(rs.getString("id_producto"));
                p.setNombre(rs.getString("nombre"));
                p.setPrecio(rs.getDouble("precio"));
                p.setStock(rs.getInt("stock"));
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
        try {
            cn = AccesoDB.getConnection();
            cn.setAutoCommit(false);

            // Verificar stock actual
            sql = "SELECT stock FROM productos WHERE id_producto = ? FOR UPDATE";
            ps = cn.prepareStatement(sql);
            ps.setString(1, idProducto);
            rs = ps.executeQuery();
            if (rs.next()) {
                int stockActual = rs.getInt("stock");
                if (stockActual < cantidad) {
                    throw new Exception("Stock insuficiente para el producto: " + idProducto);
                }
                int nuevoStock = stockActual - cantidad;
                sql = "UPDATE productos SET stock = ? WHERE id_producto = ?";
                ps = cn.prepareStatement(sql);
                ps.setInt(1, nuevoStock);
                ps.setString(2, idProducto);
                ps.executeUpdate();
            } else {
                throw new Exception("Producto no encontrado: " + idProducto);
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
    }
}