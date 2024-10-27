package com.sistema.service.impl;

import com.sistema.dao.UsuarioDAO;
import com.sistema.model.Usuario;
import com.sistema.service.UsuarioService;
import com.sistema.util.PasswordUtils;

public class UsuarioServiceImpl implements UsuarioService {
    
    private final UsuarioDAO usuarioDAO;
    
    public UsuarioServiceImpl() {
        this.usuarioDAO = new UsuarioDAOImpl();
    }
    
    @Override
    public boolean validarCredenciales(String usuario, String password) {
        Usuario user = usuarioDAO.buscarPorUsuario(usuario);
        if (user == null) {
            return false;
        }
        return PasswordUtils.verificarPassword(password, user.getPassword());
    }
    
    @Override
    public Usuario obtenerUsuario(String usuario) {
        return usuarioDAO.buscarPorUsuario(usuario);
    }
    
    @Override
    public void cambiarPassword(String usuario, String nuevaPassword) {
        Usuario user = usuarioDAO.buscarPorUsuario(usuario);
        if (user != null) {
            String hashedPassword = PasswordUtils.hashPassword(nuevaPassword);
            user.setPassword(hashedPassword);
            usuarioDAO.actualizar(user);
        }
    }
}