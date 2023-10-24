package com.example.proyectocesurapp;

import clase.Sesion;
import domain.DBConnection;
import domain.ProfesorDAOImp;
import exception.UsuarioInexistente;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class Login implements Initializable {
    @javafx.fxml.FXML
    private TextField txtEmail; //Field del DNI
    @javafx.fxml.FXML
    private PasswordField txtPassword;
    @javafx.fxml.FXML
    private Button botonInicio;

    @javafx.fxml.FXML
    public void iniciarSesion(ActionEvent actionEvent) {
        String usuarioDni = txtEmail.getText();
        String usuarioContrasenha = txtPassword.getText();
        ProfesorDAOImp dao=new ProfesorDAOImp(DBConnection.getConnection());
        try {
            Sesion.setProfesor(dao.loadTeacher(usuarioDni,usuarioContrasenha));
            System.out.println(Sesion.getProfesor());
            Alert alerta=new Alert(Alert.AlertType.CONFIRMATION);
            alerta.setTitle("Login");
            alerta.setHeaderText("Bienvenido "+Sesion.getProfesor().getNombre());
            alerta.setContentText("Inicio Correcto");
            alerta.showAndWait();
            HelloApplication.loadFXML("ventanaProfesor.fxml");

        } catch (UsuarioInexistente e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
