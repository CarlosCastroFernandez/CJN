package com.example.proyectocesurapp;

import clase.Empresa;
import clase.Sesion;
import domain.DBConnection;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class EditarEmpresa implements Initializable {
    @javafx.fxml.FXML
    private TextField textTelefono;
    @javafx.fxml.FXML
    private Button btnGuardar;
    @javafx.fxml.FXML
    private Button btnCancelar;
    @javafx.fxml.FXML
    private TextField textEmail;
    @javafx.fxml.FXML
    private TextField textNombre;
    @javafx.fxml.FXML
    private TextField textResponsable;
    @javafx.fxml.FXML
    private TextArea textObs;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        textTelefono.setText(String.valueOf(Sesion.getEmpresa().getTelefono()));
        textEmail.setText(Sesion.getEmpresa().getEmail());
        textNombre.setText(Sesion.getEmpresa().getNombre());
        textResponsable.setText(Sesion.getEmpresa().getResponsable());
        textObs.setText(Sesion.getEmpresa().getObservaciones());
    }

    @javafx.fxml.FXML
    public void guardar(ActionEvent actionEvent) {
       if (!textTelefono.getText().isEmpty() && !textEmail.getText().isEmpty()
       && !textNombre.getText().isEmpty() && !textResponsable.getText().isEmpty()){
           Sesion.getEmpresa().setTelefono(Integer.valueOf(textTelefono.getText()));
           Sesion.getEmpresa().setEmail(textEmail.getText());
           Sesion.getEmpresa().setNombre(textNombre.getText());
           Sesion.getEmpresa().setResponsable(textResponsable.getText());
           Sesion.getEmpresa().setObservaciones(textObs.getText());
          // Empresa empresa = (new Empresa(DBConnection.getConnection()).update(Sesion.getEmpresa()));
       }

    }

    @javafx.fxml.FXML
    public void cancelar(ActionEvent actionEvent) {

    }
}
