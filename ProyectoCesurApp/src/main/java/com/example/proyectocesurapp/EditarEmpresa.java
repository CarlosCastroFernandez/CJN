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
    private PasswordField txtEmail;
    @javafx.fxml.FXML
    private Button botonBorrar;

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
                txtTelefono.setText(""+empresa.getTelefono());
                txtEmail.setText(empresa.getEmail());
                txtNombre.setText(empresa.getNombre());
                txtResponsable.setText(empresa.getResponsable());
                txtObservaciones.setText(empresa.getObservaciones());
        };
        tvEmpresas.getSelectionModel().selectedItemProperty().addListener(selectionListener);
    }

    @javafx.fxml.FXML
    public void actualizar(ActionEvent actionEvent) {
        tvEmpresas.getSelectionModel().selectedItemProperty().removeListener(selectionListener);
            if (!txtTelefono.getText().isEmpty() && !txtEmail.getText().isEmpty()
                    && !txtNombre.getText().isEmpty() && !txtResponsable.getText().isEmpty()) {
                System.out.println(empresa.toString());
                empresa.setTelefono(Integer.valueOf(txtTelefono.getText()));
                empresa.setEmail(txtEmail.getText());
                empresa.setNombre(txtNombre.getText());
                empresa.setResponsable(txtResponsable.getText());
                empresa.setObservaciones(txtObservaciones.getText());
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
        tvEmpresas.getSelectionModel().select(null);
    }

    @javafx.fxml.FXML
    public void añadirEmpresa(ActionEvent actionEvent) {

        try{
            Empresa empresaNueva=new Empresa();
            empresaNueva.setTelefono(Integer.valueOf(txtTelefono.getText()));
            empresaNueva.setEmail(txtEmail.getText());
            empresaNueva.setNombre(txtNombre.getText());
            empresaNueva.setResponsable(txtResponsable.getText());
            empresaNueva.setObservaciones(txtObservaciones.getText());
            Empresa empresaAnhadida =(new EmpresaDAOImp(DBConnection.getConnection()).insert(empresaNueva));
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

