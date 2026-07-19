package controller;

import com.utp.restaurante.entity.SucursalEntity;
import dao.SucursalDAO;
import java.util.List;

public class SucursalController {

    private SucursalDAO dao;

    public SucursalController() {
        dao = new SucursalDAO();
    }

    public List<SucursalEntity> listarTodos() throws Exception {
        return dao.listarTodos();
    }

    public SucursalEntity buscarPorId(int id) throws Exception {
        return dao.buscarPorId(id);
    }
}