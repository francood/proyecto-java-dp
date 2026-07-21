package controller;

import dao.ClienteDAO;
import entity.ClienteEntity;
import java.util.List;

public class ClienteController {

    private ClienteDAO dao;

    public ClienteController() {
        dao = new ClienteDAO();
    }

    public List<ClienteEntity> listarTodos() throws Exception {
        return dao.listarTodos();
    }

    public ClienteEntity buscarPorId(String id) throws Exception {
        return dao.buscarPorId(id);
    }

    public void registrar(ClienteEntity cliente) throws Exception {
        dao.crear(cliente);
    }

    public void actualizar(ClienteEntity cliente) throws Exception {
        dao.actualizar(cliente);
    }

    public void eliminar(String id) throws Exception {
        dao.eliminar(id);
    }
}
