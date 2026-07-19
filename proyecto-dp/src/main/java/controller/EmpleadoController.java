package controller;

import dao.EmpleadoDAO;
import entity.EmpleadoEntity;
import java.util.List;

public class EmpleadoController {

    private EmpleadoDAO dao;

    public EmpleadoController() {
        dao = new EmpleadoDAO();
    }

    public void registrar(EmpleadoEntity empleado) throws Exception {
        dao.crear(empleado);
    }

    public List<EmpleadoEntity> listarTodos() throws Exception {
        return dao.listarTodos();
    }

    public EmpleadoEntity buscarPorId(String id) throws Exception {
        return dao.buscarPorId(id);
    }

    public EmpleadoEntity buscarPorUsuario(String usuario) throws Exception {
        return dao.buscarPorUsuario(usuario);
    }

    public boolean validarLogin(String usuario, String clave) throws Exception {
        return dao.validarCredenciales(usuario, clave);
    }
}