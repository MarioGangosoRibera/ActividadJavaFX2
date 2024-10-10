package org.example.ejercicio;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.fxml.Initializable;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LoginController implements Initializable{
    @FXML
    private TextField txtUsuario;

    @FXML
    private PasswordField txtContraseña;

    @FXML
    private Button btnInicio;

    @FXML
    private Button btnRegistro;

    private UsuarioDAO usuarioDAO;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        usuarioDAO = new UsuarioDAO();
        try{
            usuarioDAO.conectar();
        } catch (Exception e) {
            e.toString();
        }
        btnInicio.setOnAction(event -> {
            String usuario=txtUsuario.getText();
            String contraseña=txtContraseña.getText();

            try {
                if (usuarioDAO.iniciarSesion(usuario, contraseña)){
                    System.out.println("Inicio de sesion exitoso");
                    //Cerrar la ventana y mostrar la principal
                }else{
                    System.out.println("Error al iniciar sesión: usuario o contraseña incorrectos");
                }
            } catch (SQLException e) {
                System.out.println("Error al iniciar sesión");
            }
        });

        btnRegistro.setOnAction(event -> {
            String usuario=txtUsuario.getText();
            String contraseña=txtContraseña.getText();

            try{
                if (!usuarioDAO.existeUsuario(usuario)){
                    usuarioDAO.registrarUsuario(usuario, contraseña);
                    System.out.println("Registro exitoso");
                }else {
                    System.out.println("Error al registrar al usuario");
                }
            } catch (SQLException e) {
                e.toString();
            }
        });
    }

}