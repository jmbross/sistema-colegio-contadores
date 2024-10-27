package com.sistema.dao.impl;

import com.sistema.dao.EmpleadoDAO;
import com.sistema.model.Empleado;
import com.sistema.util.DatabaseConfig;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmpleadoDAOImpl implements EmpleadoDAO {
    
    @Override
    public void guardar(Empleado empleado) {
        String sql = "INSERT INTO empleados (nombre, apellido, dni, fecha_ingreso, salario_base, activo) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setString(1, empleado.getNombre());
            stmt.setString(2, empleado.getApellido());
            stmt.setString(3, empleado.getDni());
            stmt.setDate(4, Date.valueOf(empleado.getFechaIngreso()));
            stmt.setDouble(5, empleado.getSalarioBase());
            stmt.setBoolean(6, empleado.isAct ivo());
            
            stmt.executeUpdate();
            
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    empleado.setId(rs.getLong(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al guardar empleado", e);
        }
    }
    
    @Override
    public void actualizar(Empleado empleado) {
        String sql = "UPDATE empleados SET nombre = ?, apellido = ?, dni = ?, fecha_ingreso = ?, salario_base = ?, activo = ? WHERE id = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, empleado.getNombre());
            stmt.setString(2, empleado.getApellido());
            stmt.setString(3, empleado.getDni());
            stmt.setDate(4, Date.valueOf(empleado.getFechaIngreso()));
            stmt.setDouble(5, empleado.getSalarioBase());
            stmt.setBoolean(6, empleado.isActivo());
            stmt.setLong(7, empleado.getId());
            
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar empleado", e);
        }
    }
    
    @Override
    public void eliminar(Long id) {
        String sql = "DELETE FROM empleados WHERE id = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar empleado", e);
        }
    }
    
    @Override
    public Empleado obtener(Long id) {
        String sql = "SELECT * FROM empleados WHERE id = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, id);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapearEmpleado(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener empleado", e);
        }
        return null;
    }
    
    @Override
    public List<Empleado> listarTodos() {
        String sql = "SELECT * FROM empleados";
        List<Empleado> empleados = new ArrayList<>();
        
        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                empleados.add(mapearEmpleado(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar empleados", e);
        }
        return empleados;
    }
    
    @Override
    public int contarActivos() {
        String sql = "SELECT COUNT(*) FROM empleados WHERE activo = true";
        
        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al contar empleados activos", e);
        }
        return 0;
    }
    
    @Override
    public Empleado buscarPorDni(String dni) {
        String sql = "SELECT * FROM empleados WHERE dni = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, dni);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapearEmpleado(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar empleado por DNI", e);
        }
        return null;
    }
    
    private Empleado mapearEmpleado(ResultSet rs) throws SQLException {
        Empleado empleado = new Empleado();
        empleado.setId(rs.getLong("id"));
        empleado.setNombre(rs.getString("nombre"));
        empleado.setApellido(rs.getString("apellido"));
        empleado.setDni(rs.getString("dni"));
        empleado.setFechaIngreso(rs.getDate("fecha_ingreso").toLocalDate());
        empleado.setSalarioBase(rs.getDouble("salario_base"));
        empleado.setActivo(rs.getBoolean("activo"));
        return empleado;
    }
}