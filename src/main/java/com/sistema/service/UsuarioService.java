package com.sistema.service;

import com.sistema.model.Usuario;

public interface UsuarioService {
    boolean validarCredenciales(String usuario, String password);
    Usuario obtenerUsuario(String usuario);
    void cambiarPassword(String usuario, String nuevaPassword);
}