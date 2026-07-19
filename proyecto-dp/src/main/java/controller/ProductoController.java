package controller;

import dao.ProductoDAO;
import entity.ProductoEntity;
import java.util.List;

public class ProductoController {

    private ProductoDAO dao;

    public ProductoController() {
        dao = new ProductoDAO();
    }

    public List<ProductoEntity> listarTodos() throws Exception {
        return dao.listarTodos();
    }

    public ProductoEntity buscarPorId(String id) throws Exception {
        return dao.buscarPorId(id);
    }

    public ProductoEntity buscarPorNombre(String nombre) throws Exception {
        return dao.buscarPorNombre(nombre);
    }

    public void registrar(ProductoEntity producto) throws Exception {
        dao.crear(producto);
    }

    public void actualizarStock(String idProducto, int nuevoStock) throws Exception {
        dao.actualizarStock(idProducto, nuevoStock);
    }

    public void descontarStock(String idProducto, int cantidad) throws Exception {
        dao.descontarStock(idProducto, cantidad);
    }
    
        public void eliminar(String idProducto) throws Exception {
        dao.eliminar(idProducto);
    }
}
