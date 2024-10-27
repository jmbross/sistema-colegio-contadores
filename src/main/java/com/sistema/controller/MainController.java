package com.sistema.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import com.sistema.util.AlertUtils;

public class MainController {
    
    @FXML
    private StackPane contentArea;
    
    @FXML
    private Label statusLabel;
    
    @FXML
    public void initialize() {
        cargarDashboard();
    }
    
    private void cargarDashboard() {
        try {
            Parent dashboard = FXMLLoader.load(getClass().getResource("/fxml/dashboard.fxml"));
            contentArea.getChildren().clear();
            contentArea.getChildren().add(dashboard);
        } catch (Exception e) {
            AlertUtils.mostrarError("Error", "Error al cargar el dashboard", e.getMessage());
        }
    }
    
    @FXML
    private void handleLogout(ActionEvent event) {
        try {
            // Cargar la ventana de login
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"));
            Parent root = loader.load();
            
            Stage stage = new Stage();
            stage.setTitle("Login");
            stage.setScene(new Scene(root));
            
            // Cerrar la ventana principal
            Stage mainStage = (Stage) contentArea.getScene().getWindow();
            mainStage.close();
            
            stage.show();
        } catch (Exception e) {
            AlertUtils.mostrarError("Error", "Error al cerrar sesiÃ³n", e.getMessage());
        }
    }
    
    @FXML
    private void handleExit(ActionEvent event) {
        Stage stage = (Stage) contentArea.getScene().getWindow();
        stage.close();
    }
    
    @FXML
    private void showEmpleados(ActionEvent event) {
        // Implementar la carga de la vista de empleados
    }
    
    @FXML
    private void nuevoEmpleado(ActionEvent event) {
        // Implementar la carga del formulario de nuevo empleado
    }
    
    @FXML
    private void nuevaLiquidacion(ActionEvent event) {
        // Implementar la carga del formulario de nueva liquidaciÃ³n
    }
    
    @FXML
    private void verLiquidaciones(ActionEvent event) {
        // Implementar la carga de la vista de liquidaciones
    }
    
    @FXML
    private void generarReporte(ActionEvent event) {
        // Implementar la generaciÃ³n de reportes
    }
}
