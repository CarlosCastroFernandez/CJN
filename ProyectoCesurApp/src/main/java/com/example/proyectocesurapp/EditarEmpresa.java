package com.example.proyectocesurapp;

import clase.Empresa;
import domain.DBConnection;
import domain.EmpresaDAOImp;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class EditarEmpresa implements Initializable {

    @javafx.fxml.FXML
    private TextField txtNombre;
    @javafx.fxml.FXML
    private TextField txtTelefono;
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
    @javafx.fxml.FXML
    private Button botonLimpiar;
    @javafx.fxml.FXML
    private Button botonAñadirEmpresa;
    @javafx.fxml.FXML
    private Button botonActualizar;
    private ArrayList<Empresa>listaEmpresas=new ArrayList<>();
    private ChangeListener<Empresa> selectionListener;
    @javafx.fxml.FXML
    private Button botonBorrar;
    @javafx.fxml.FXML
    private TextField txtEmail;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cNombre.setCellValueFactory((fila) -> {
            String nombre = fila.getValue().getName();
            return new SimpleStringProperty(nombre);
        });
        cEmail.setCellValueFactory((fila) -> {
            String email = fila.getValue().getEmail();
            return new SimpleStringProperty(email);
        });
        cResponsable.setCellValueFactory((fila) -> {
            String responsable = fila.getValue().getBoss();
            return new SimpleStringProperty(responsable);
        });
        cTelefono.setCellValueFactory((fila) -> {
            String cTelefono = String.valueOf(fila.getValue().getPhone());
            return new SimpleStringProperty(cTelefono);
        });

        cObservaciones.setCellValueFactory((fila) -> {
            String cObservaciones = fila.getValue().getObservations();
            return new SimpleStringProperty(cObservaciones);
        });

        EmpresaDAOImp dao = new EmpresaDAOImp(DBConnection.getConnection());
        listaEmpresas.addAll(dao.loadAllEnterprise());
        if (listaEmpresas.isEmpty()) {
            listaEmpresas.addAll(dao.loadAllEnterprise());
            observableEmpresas = FXCollections.observableArrayList();
            observableEmpresas.addAll(listaEmpresas);
            tvEmpresas.setItems(observableEmpresas);
        } else {
            tvEmpresas.getItems().clear();
            observableEmpresas = FXCollections.observableArrayList();
            observableEmpresas.addAll(listaEmpresas);
            tvEmpresas.setItems(observableEmpresas);
        }

        selectionListener = (observableValue, old, t1) -> {

                System.out.println("PASO POR AQUI");
                empresa = t1;
                txtTelefono.setText(""+empresa.getPhone());
                txtEmail.setText(empresa.getEmail());
                txtNombre.setText(empresa.getName());
                txtResponsable.setText(empresa.getBoss());
                txtObservaciones.setText(empresa.getObservations());
        };
        tvEmpresas.getSelectionModel().selectedItemProperty().addListener(selectionListener);
    }

    @javafx.fxml.FXML
    public void actualizar(ActionEvent actionEvent) {
        tvEmpresas.getSelectionModel().selectedItemProperty().removeListener(selectionListener);
            if (!txtTelefono.getText().isEmpty() && !txtEmail.getText().isEmpty()
                    && !txtNombre.getText().isEmpty() && !txtResponsable.getText().isEmpty()) {
                System.out.println(empresa.toString());
                empresa.setPhone(Integer.valueOf(txtTelefono.getText()));
                empresa.setEmail(txtEmail.getText());
                empresa.setName(txtNombre.getText());
                empresa.setBoss(txtResponsable.getText());
                empresa.setObservations(txtObservaciones.getText());
                Empresa empresaActualizada = (new EmpresaDAOImp(DBConnection.getConnection()).update(empresa));

                listaEmpresas = (new EmpresaDAOImp(DBConnection.getConnection()).loadAllEnterprise());
                empresa=empresaActualizada;
                System.out.println(listaEmpresas);
                tvEmpresas.getItems().clear();
                observableEmpresas.addAll(listaEmpresas);
            tvEmpresas.getSelectionModel().selectedItemProperty().addListener(selectionListener);
            } else {
                Alert alerta = new Alert(Alert.AlertType.ERROR);
                alerta.setTitle("Error");
                alerta.setHeaderText("Porfavor comprueba de que los campos estén rellenos");
                alerta.showAndWait();
            }
        }


    @Deprecated
    public void cancelar (ActionEvent actionEvent){

    }

    @javafx.fxml.FXML
    public void limpiar(ActionEvent actionEvent) {
        txtTelefono.clear();
        txtEmail.clear();
        txtNombre.clear();
        txtResponsable.clear();
        txtObservaciones.clear();
        tvEmpresas.getSelectionModel().selectedItemProperty().removeListener(selectionListener);
        tvEmpresas.getSelectionModel().select(null);
        tvEmpresas.getSelectionModel().selectedItemProperty().addListener(selectionListener);

    }

    @javafx.fxml.FXML
    public void añadirEmpresa(ActionEvent actionEvent) {

        try{
            Empresa empresaNueva=new Empresa();
            empresaNueva.setPhone(Integer.valueOf(txtTelefono.getText()));
            empresaNueva.setEmail(txtEmail.getText());
            empresaNueva.setName(txtNombre.getText());
            empresaNueva.setBoss(txtResponsable.getText());
            empresaNueva.setObservations(txtObservaciones.getText());
            Empresa empresaAnhadida =(new EmpresaDAOImp(DBConnection.getConnection()).injection(empresaNueva));
            observableEmpresas.add(empresaAnhadida);
        }catch(Exception e){
            e.printStackTrace();
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Error");
            alerta.setHeaderText("Esta empresa ya existe, añade otra empresa.");
            ButtonType tipo= alerta.showAndWait().get();
            if(tipo.getButtonData()== ButtonBar.ButtonData.OK_DONE){
                txtTelefono.clear();
                txtEmail.clear();
                txtNombre.clear();
                txtResponsable.clear();
                txtObservaciones.clear();
            }
        }

    }

    @javafx.fxml.FXML
    public void borrar(ActionEvent actionEvent) {
        tvEmpresas.getSelectionModel().selectedItemProperty().removeListener(selectionListener);
        listaEmpresas.remove(listaEmpresas.get(listaEmpresas.indexOf(empresa)));
        (new EmpresaDAOImp(DBConnection.getConnection())).delete(empresa);
        observableEmpresas.setAll(listaEmpresas);
        tvEmpresas.getSelectionModel().selectedItemProperty().addListener(selectionListener);

    }
}

