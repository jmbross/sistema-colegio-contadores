package com.sistema.service.impl;

import com.sistema.dao.EmpleadoDAO;
import com.sistema.model.Empleado;
import com.sistema.service.EmpleadoService;
import java.util.List;

public class EmpleadoServiceImpl implements EmpleadoService {
    
    private final EmpleadoDAO empleadoDAO;
    
    public EmpleadoServiceImpl() {
        this.empleadoDAO = new EmpleadoDAOImpl();
    }
    
    @Override
    public void guardarEmpleado(Empleado empleado) {
        validarEmpleado(empleado);
        empleadoDAO.guardar(empleado);
    }
    
    @Override
    public void actualizarEmpleado(Empleado empleado) {
        validarEmpleado(empleado);
        empleadoDAO.actualizar(empleado);
    }
    
    @Override
    public void eliminarEmpleado(Long id) {
        empleadoDAO.eliminar(id);
    }
    
    @Override
    public Empleado obtenerEmpleado(Long id) {
        return empleadoDAO.obtener(id);
    }
    
    @Override
    public List<Empleado> listarEmpleados() {
        return empleadoDAO.listarTodos();
    }
    
    @Override
    public int contarEmpleadosActivos() {
        return empleadoDAO.contarActivos();
    }
    
    private void validarEmpleado(Empleado empleado) {
        if (empleado.getNombre() == null || empleado.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del empleado es requerido");
        }
        if (empleado.getDni() == null || empleado.getDni().trim().isEmpty()) {
            throw new IllegalArgumentException("El DNI del empleado es requerido");
        }
        // Agregar mÃ¡s validaciones segÃºn necesidades
    }
}