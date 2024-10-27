package com.sistema.service;

import com.sistema.model.Empleado;
import java.util.List;

public interface EmpleadoService {
    void guardarEmpleado(Empleado empleado);
    void actualizarEmpleado(Empleado empleado);
    void eliminarEmpleado(Long id);
    Empleado obtenerEmpleado(Long id);
    List<Empleado> listarEmpleados();
    int contarEmpleadosActivos();
}