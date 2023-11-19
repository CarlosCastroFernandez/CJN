package com.example.proyectocesurapp;

import clase.Sesion;
import domain.AlumnoDAOImp;
import domain.DBConnection;
import domain.ProfesorDAOImp;
import exception.*;
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
        AlumnoDAOImp daoA=new AlumnoDAOImp(DBConnection.getConnection());
        try {
            Sesion.setTeacher(dao.loadTeacher(usuarioDni,usuarioContrasenha));

            System.out.println(Sesion.getTeacher());
            Alert alerta=new Alert(Alert.AlertType.CONFIRMATION);
            alerta.setTitle("Login");
            alerta.setHeaderText("Bienvenido "+Sesion.getTeacher().getName());
            alerta.setContentText("Inicio Correcto");
            alerta.showAndWait();
            HelloApplication.loadFXML("ventanaProfesor.fxml");

        } catch (UsuarioInexistente e) {
            try {
                Sesion.setAlumn(daoA.loadActivity(usuarioDni,usuarioContrasenha));
                HelloApplication.loadFXML("ventanaAlumno.fxml");
            } catch (ContrasenhaIncorrecta ex) {
                throw new RuntimeException(ex);
            } catch (UsuarioInexistente ex) {
                Alert alerta=new Alert(Alert.AlertType.ERROR);
                alerta.setTitle("Error");
                alerta.setHeaderText("Usuario Inexistente.");
                alerta.showAndWait();
                throw new RuntimeException(ex);
            }
        } catch (ContrasenhaIncorrecta e) {
            Alert alerta=new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Error");
            alerta.setHeaderText("Contraseña incorrecta.");
            alerta.showAndWait();
            throw new RuntimeException(e);
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
