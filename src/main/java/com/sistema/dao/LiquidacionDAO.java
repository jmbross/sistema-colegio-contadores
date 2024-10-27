package com.sistema.dao;

import com.sistema.model.Liquidacion;
import java.util.List;
import java.time.LocalDate;

public interface LiquidacionDAO {
    void guardar(Liquidacion liquidacion);
    void actualizar(Liquidacion liquidacion);
    void eliminar(Long id);
    Liquidacion obtener(Long id);
    List<Liquidacion> listarTodas();
    List<Liquidacion> obtenerUltimas(int cantidad);
    int contarLiquidacionesMes();
    double calcularTotalMes();
    List<Liquidacion> buscarPorEmpleado(Long empleadoId);
}