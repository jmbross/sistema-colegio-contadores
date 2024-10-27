package com.sistema.dao;

import com.sistema.model.Usuario;
import java.util.List;

public interface UsuarioDAO {
    void guardar(Usuario usuario);
    void actualizar(Usuario usuario);
    void eliminar(Long id);
    Usuario obtener(Long id);
    Usuario buscarPorUsuario(String usuario);
    List<Usuario> listarTodos();
}