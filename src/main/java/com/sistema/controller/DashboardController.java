package com.sistema.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import com.sistema.model.Liquidacion;
import com.sistema.service.EmpleadoService;
import com.sistema.service.LiquidacionService;
import com.sistema.util.AlertUtils;

public class DashboardController {
    
    @FXML
    private Label empleadosActivosLabel;
    
    @FXML
    private Label liquidacionesMesLabel;
    
    @FXML
    private Label totalLiquidadoLabel;
    
    @FXML
    private TableView<Liquidacion> ultimasLiquidacionesTable;
    
    @FXML
    private TableColumn<Liquidacion, String> fechaColumn;
    
    @FXML
    private TableColumn<Liquidacion, String> empleadoColumn;
    
    @FXML
    private TableColumn<Liquidacion, Double> montoColumn;
    
    @FXML
    private TableColumn<Liquidacion, String> estadoColumn;
    
    private EmpleadoService empleadoService;
    private LiquidacionService liquidacionService;
    
    public DashboardController() {
        empleadoService = new EmpleadoService();
        liquidacionService = new LiquidacionService();
    }
    
    @FXML
    public void initialize() {
        try {
            // Configurar las columnas de la tabla
            configurarColumnas();
            
            // Cargar datos
            cargarEstadisticas();
            cargarUltimasLiquidaciones();
        } catch (Exception e) {
            AlertUtils.mostrarError("Error", "Error al cargar el dashboard", e.getMessage());
        }
    }
    
    private void configurarColumnas() {
        fechaColumn.setCellValueFactory(cellData -> 
            new SimpleStringProperty(cellData.getValue().getFecha().toString()));
        
        empleadoColumn.setCellValueFactory(cellData -> 
            new SimpleStringProperty(cellData.getValue().getEmpleado().getNombreCompleto()));
        
        montoColumn.setCellValueFactory(cellData -> 
            new SimpleObjectProperty<>(cellData.getValue().getMonto()));
        
        estadoColumn.setCellValueFactory(cellData -> 
            new SimpleStringProperty(cellData.getValue().getEstado()));
    }
    
    private void cargarEstadisticas() {
        try {
            int empleadosActivos = empleadoService.contarEmpleadosActivos();
            int liquidacionesMes = liquidacionService.contarLiquidacionesMes();
            double totalLiquidado = liquidacionService.calcularTotalLiquida Mes();
            
            empleadosActivosLabel.setText(String.valueOf(empleadosActivos));
            liquidacionesMesLabel.setText(String.valueOf(liquidacionesMes));
            totalLiquidadoLabel.setText(String.format("%.2f", totalLiquidado));
        } catch (Exception e) {
            AlertUtils.mostrarError("Error", "Error al cargar estadÃ­sticas", e.getMessage());
        }
    }
    
    private void cargarUltimasLiquidaciones() {
        try {
            ObservableList<Liquidacion> liquidaciones = FXCollections.observableArrayList();
            liquidaciones.addAll(liquidacionService.obtenerUltimasLiquidaciones());
            
            ultimasLiquidacionesTable.setItems(liquidaciones);
        } catch (Exception e) {
            AlertUtils.mostrarError("Error", "Error al cargar Ãºltimas liquidaciones", e.getMessage());
        }
    }
}
