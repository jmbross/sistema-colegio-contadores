package com.sistema.dao;

import com.sistema.model.Empleado;
import java.util.List;

public interface EmpleadoDAO {
    void guardar(Empleado empleado);
    void actualizar(Empleado empleado);
    void eliminar(Long id);
    Empleado obtener(Long id);
    List<Empleado> listarTodos();
    int contarActivos();
    Empleado buscarPorDni(String dni);
}
