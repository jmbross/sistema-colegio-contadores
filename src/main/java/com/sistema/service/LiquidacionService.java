package com.sistema.service;

import com.sistema.model.Liquidacion;
import java.util.List;
import java.time.LocalDate;

public interface LiquidacionService {
    void generarLiquidacion(Liquidacion liquidacion);
    void actualizarLiquidacion(Liquidacion liquidacion);
    void eliminarLiquidacion(Long id);
    Liquidacion obtenerLiquidacion(Long id);
    List<Liquidacion> listarLiquidaciones();
    List<Liquidacion> obtenerUltimasLiquidaciones();
    int contarLiquidacionesMes();
    double calcularTotalLiquidadoMes();
}