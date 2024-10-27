package com.sistema.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import com.sistema.service.UsuarioService;
import com.sistema.util.AlertUtils;

public class LoginController {
    
    @FXML
    private TextField usuarioField;
    
    @FXML
    private PasswordField passwordField;
    
    @FXML
    private Label mensajeError;
    
    private UsuarioService usuarioService;
    
    public LoginController() {
        usuarioService = new UsuarioService();
    }
    
    @FXML
    private void handleLogin(ActionEvent event) {
        String usuario = usuarioField.getText();
        String password = passwordField.getText();
        
        if (usuario.isEmpty() || password.isEmpty()) {
            mensajeError.setText("Por favor complete todos los campos");
            return;
        }
        
        try {
            if (usuarioService.validarCredenciales(usuario, password)) {
                // Cargar la ventana principal
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/main.fxml"));
                Parent root = loader.load();
                
                Stage stage = new Stage();
                stage.setTitle("Sistema de LiquidaciÃ³n de Sueldos");
                stage.setScene(new Scene(root));
                stage.setMaximized(true);
                
                // Cerrar la ventana de login
                Stage loginStage = (Stage) usuarioField.getScene().getWindow();
                loginStage.close();
                
                stage.show();
            } else {
                mensajeError.setText("Usuario o contraseÃ±a incorrectos");
            }
        } catch (Exception e) {
            AlertUtils.mostrarError("Error", "Error al iniciar sesiÃ³n", e.getMessage());
        }
    }
}
