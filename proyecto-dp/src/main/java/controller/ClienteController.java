package controller;

import com.utp.restaurante.entity.ClienteEntity;
import dao.ClienteDAO;
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
}