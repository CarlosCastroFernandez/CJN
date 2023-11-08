package com.example.proyectocesurapp;

import clase.Profesor;
import domain.DBConnection;
import domain.ProfesorDAOImp;
import exception.ApellidoConNumero;
import exception.DNIInvalido;
import exception.NombreConNumero;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Registro implements Initializable {
    @javafx.fxml.FXML
    private TextField txtEmail;
    @javafx.fxml.FXML
    private PasswordField passField;
    @javafx.fxml.FXML
    private TextField txtNombre;
    @javafx.fxml.FXML
    private TextField txtApellido1;
    @javafx.fxml.FXML
    private TextField txtApellido2;
    @javafx.fxml.FXML
    private TextField txtTelefono;
    @javafx.fxml.FXML
    private Button botonRegistro;
    @javafx.fxml.FXML
    private Button botonCancelar;
    @javafx.fxml.FXML
    private TextField txtDNI;
    @javafx.fxml.FXML
    private ImageView imagenRegistro;
    @javafx.fxml.FXML
    private Pane pane;

    @javafx.fxml.FXML
    public void registrarse(ActionEvent actionEvent) {
        String dni=txtDNI.getText();
        String nombre=txtNombre.getText();
        String apellido1=txtApellido1.getText();
        String apellido2=txtApellido2.getText();
        String email=txtEmail.getText();
        Integer telefono=Integer.valueOf(txtTelefono.getText());
        String contraseña=passField.getText();
        try {
            Profesor profe=new Profesor(nombre,apellido1,apellido2,contraseña,email,dni,telefono);
            ProfesorDAOImp dao=new ProfesorDAOImp(DBConnection.getConnection());
            dao.injection(profe);
        } catch (NombreConNumero e) {
            Alert alerta=new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Error");
            alerta.setHeaderText("Nombre con número.");
            alerta.showAndWait();
            throw new RuntimeException(e);
        } catch (ApellidoConNumero e) {
            Alert alerta=new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Error");
            alerta.setHeaderText("Apellido con número.");
            alerta.showAndWait();
            throw new RuntimeException(e);
        } catch (DNIInvalido e) {
            Alert alerta=new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Error");
            alerta.setHeaderText("DNI inválido.");
            alerta.showAndWait();
            throw new RuntimeException(e);
        }
        HelloApplication.loadFXML("login.fxml");

    }

    @javafx.fxml.FXML
    public void cancelar(ActionEvent actionEvent) {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
