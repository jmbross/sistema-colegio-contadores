package com.sistema.dao.impl;

import com.sistema.dao.LiquidacionDAO;
import com.sistema.model.Liquidacion;
import com.sistema.util.DatabaseConfig;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class LiquidacionDAOImpl implements LiquidacionDAO {
    
    @Override
    public void guardar(Liquidacion liquidacion) {
        String sql = "INSERT INTO liquidaciones (empleado_id, fecha, sueldo_bruto, deducciones, sueldo_neto) " +
                    "VALUES (?, ?, ?, ?, ? )";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setLong(1, liquidacion.getEmpleado().getId());
            stmt.setDate(2, Date.valueOf(liquidacion.getFecha()));
            stmt.setDouble(3, liquidacion.getSueldoBruto());
            stmt.setDouble(4, liquidacion.getDeducciones());
            stmt.setDouble(5, liquidacion.getSueldoNeto());
            
            stmt.executeUpdate();
            
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    liquidacion.setId(rs.getLong(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al guardar liquidaciÃ³n", e);
        }
    }
    
    @Override
    public void actualizar(Liquidacion liquidacion) {
        String sql = "UPDATE liquidaciones SET empleado_id = ?, fecha = ?, sueldo_bruto = ?, deducciones = ?, sueldo_neto = ? WHERE id = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, liquidacion.getEmpleado().getId());
            stmt.setDate(2, Date.valueOf(liquidacion.getFecha()));
            stmt.setDouble(3, liquidacion.getSueldoBruto());
            stmt.setDouble(4, liquidacion.getDeducciones());
            stmt.setDouble(5, liquidacion.getSueldoNeto());
            stmt.setLong(6, liquidacion.getId());
            
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar liquidaciÃ³n", e);
        }
    }
    
    @Override
    public void eliminar(Long id) {
        String sql = "DELETE FROM liquidaciones WHERE id = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar liquidaciÃ³n", e);
        }
    }
    
    @Override
    public Liquidacion obtener(Long id) {
        String sql = "SELECT * FROM liquidaciones WHERE id = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, id);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapearLiquidacion(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener liquidaciÃ³n", e);
        }
        return null;
    }
    
    @Override
    public List<Liquidacion> listarTodas() {
        String sql = "SELECT * FROM liquidaciones";
        List<Liquidacion> liquidaciones = new ArrayList<>();
        
        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                liquidaciones.add(mapearLiquidacion(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar liquidaciones", e);
        }
        return liquidaciones;
    }
    
    @Override
    public List<Liquidacion> obtenerUltimas(int cantidad) {
        String sql = "SELECT * FROM liquidaciones ORDER BY fecha DESC LIMIT ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, cantidad);
            
            try (ResultSet rs = stmt.executeQuery()) {
                List<Liquidacion> liquidaciones = new ArrayList<>();
                while (rs.next()) {
                    liquidaciones.add(mapearLiquidacion(rs));
                }
                return liquidaciones;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener Ãºltimas liquidaciones", e);
        }
    }
    
    @Override
    public int contarLiquidacionesMes() {
        String sql = "SELECT COUNT(*) FROM liquidaciones WHERE fecha >= ? AND fecha < ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            LocalDate inicioMes = LocalDate.now().withDayOfMonth(1);
            LocalDate finMes = inicioMes.plusMonths(1);
            
            stmt.setDate(1, Date.valueOf(inicioMes));
            stmt.setDate(2, Date.valueOf(finMes));
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al contar liquidaciones del mes", e);
        }
        return 0;
    }
    
    @Override
    public double calcularTotalMes() {
        String sql = "SELECT SUM(sueldo_neto) FROM liquidaciones WHERE fecha >= ? AND fecha < ?";
        
        try (Connection conn = Database Config.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            LocalDate inicioMes = LocalDate.now().withDayOfMonth(1);
            LocalDate finMes = inicioMes.plusMonths(1);
            
            stmt.setDate(1, Date.valueOf(inicioMes));
            stmt.setDate(2, Date.valueOf(finMes));
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble(1);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al calcular total del mes", e);
        }
        return 0.0;
    }
    
    @Override
    public List<Liquidacion> buscarPorEmpleado(Long empleadoId) {
        String sql = "SELECT * FROM liquidaciones WHERE empleado_id = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, empleadoId);
            
            try (ResultSet rs = stmt.executeQuery()) {
                List<Liquidacion> liquidaciones = new ArrayList<>();
                while (rs.next()) {
                    liquidaciones.add(mapearLiquidacion(rs));
                }
                return liquidaciones;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar liquidaciones por empleado", e);
        }
    }
    
    private Liquidacion mapearLiquidacion(ResultSet rs) throws SQLException {
        Liquidacion liquidacion = new Liquidacion();
        liquidacion.setId(rs.getLong("id"));
        liquidacion.setEmpleado(new Empleado(rs.getLong("empleado_id")));
        liquidacion.setFecha(rs.getDate("fecha").toLocalDate());
        liquidacion.setSueldoBruto(rs.getDouble("sueldo_bruto"));
        liquidacion.setDeducciones(rs.getDouble("deducciones"));
        liquidacion.setSueldoNeto(rs.getDouble("sueldo_neto"));
        return liquidacion;
    }
}