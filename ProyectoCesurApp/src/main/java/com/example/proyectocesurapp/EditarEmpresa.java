package com.example.proyectocesurapp;

import clase.Empresa;
import clase.Sesion;
import domain.ActividaDiariaDAOImp;
import domain.DBConnection;
import domain.EmpresaDAOImp;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class EditarEmpresa implements Initializable {

    @javafx.fxml.FXML
    private TextField txtNombre;
    @javafx.fxml.FXML
    private TextField txtTelefono;
    @javafx.fxml.FXML
    private PasswordField txtEmail;
    @javafx.fxml.FXML
    private TextField txtResponsable;
    @javafx.fxml.FXML
    private TextArea txtObservaciones;
    @javafx.fxml.FXML
    private TableView<Empresa> tvEmpresas;
    @javafx.fxml.FXML
    private TableColumn<Empresa, String> cNombre;
    @javafx.fxml.FXML
    private TableColumn<Empresa, String> cTelefono;
    @javafx.fxml.FXML
    private TableColumn<Empresa, String> cEmail;
    @javafx.fxml.FXML
    private TableColumn<Empresa, String> cResponsable;
    @javafx.fxml.FXML
    private TableColumn<Empresa, String> cObservaciones;
    private ObservableList<Empresa> observableEmpresas;
    private Empresa empresa;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cNombre.setCellValueFactory((fila) -> {
            String nombre = fila.getValue().getNombre();
            return new SimpleStringProperty(nombre);
        });
        cEmail.setCellValueFactory((fila) -> {
            String email = fila.getValue().getEmail();
            return new SimpleStringProperty(email);
        });
        cResponsable.setCellValueFactory((fila) -> {
            String responsable = fila.getValue().getResponsable();
            return new SimpleStringProperty(responsable);
        });
        cTelefono.setCellValueFactory((fila) -> {
            String cTelefono = String.valueOf(fila.getValue().getTelefono());
            return new SimpleStringProperty(cTelefono);
        });

        cObservaciones.setCellValueFactory((fila) -> {
            String cObservaciones = fila.getValue().getObservaciones();
            return new SimpleStringProperty(cObservaciones);
        });

        EmpresaDAOImp dao = new EmpresaDAOImp(DBConnection.getConnection());
        if (Sesion.getListaEmpresas().isEmpty()) {
            Sesion.setListaEmpresas(dao.loadAllEnterprise());
            observableEmpresas = FXCollections.observableArrayList();
            observableEmpresas.addAll(Sesion.getListaEmpresas());
            tvEmpresas.setItems(observableEmpresas);
        } else {
            tvEmpresas.getItems().clear();
            Sesion.setListaEmpresas(dao.loadAllEnterprise());
            observableEmpresas = FXCollections.observableArrayList();
            observableEmpresas.addAll(Sesion.getListaEmpresas());
            tvEmpresas.setItems(observableEmpresas);
        }
        tvEmpresas.getSelectionModel().selectedItemProperty().addListener((observableValue, old, t1) -> {
            empresa = t1;
        });

        txtTelefono.setText(String.valueOf(Sesion.getEmpresa().getTelefono()));
        txtEmail.setText(Sesion.getEmpresa().getEmail());
        txtNombre.setText(Sesion.getEmpresa().getNombre());
        txtResponsable.setText(Sesion.getEmpresa().getResponsable());
        txtObservaciones.setText(Sesion.getEmpresa().getObservaciones());
    }

    @javafx.fxml.FXML
    public void guardar(ActionEvent actionEvent) {
        if (empresa != null) {
            if (!txtTelefono.getText().isEmpty() && !txtEmail.getText().isEmpty()
                    && !txtNombre.getText().isEmpty() && !txtResponsable.getText().isEmpty()) {
                Sesion.getEmpresa().setTelefono(Integer.valueOf(txtTelefono.getText()));
                Sesion.getEmpresa().setEmail(txtEmail.getText());
                Sesion.getEmpresa().setNombre(txtNombre.getText());
                Sesion.getEmpresa().setResponsable(txtResponsable.getText());
                Sesion.getEmpresa().setObservaciones(txtObservaciones.getText());
                Empresa empresa = (new EmpresaDAOImp(DBConnection.getConnection()).update(Sesion.getEmpresa()));
            } else {
                Alert alerta = new Alert(Alert.AlertType.ERROR);
                alerta.setTitle("Error");
                alerta.setHeaderText("Porfavor comprueba de que los campos estén rellenos");
                alerta.showAndWait();
            }
        } else {
            if (!txtTelefono.getText().isEmpty() && !txtEmail.getText().isEmpty()
                    && !txtNombre.getText().isEmpty() && !txtResponsable.getText().isEmpty()) {
                Sesion.getEmpresa().setTelefono(Integer.valueOf(txtTelefono.getText()));
                Sesion.getEmpresa().setEmail(txtEmail.getText());
                Sesion.getEmpresa().setNombre(txtNombre.getText());
                Sesion.getEmpresa().setResponsable(txtResponsable.getText());
                Sesion.getEmpresa().setObservaciones(txtObservaciones.getText());
                //Empresa empresita = //SE PONE INJECTION PARA INSERTAR LA EMPRESA NUEVA
            } else {
                //////
            }
        }
    }

    @javafx.fxml.FXML
    public void cancelar (ActionEvent actionEvent){

    }
}

