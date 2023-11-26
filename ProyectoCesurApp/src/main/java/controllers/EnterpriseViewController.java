package controllers;

import classes.Enterprise;
import domain.DBConnection;
import domain.enterprise.EnterpriseDAOImp;
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

public class EnterpriseViewController implements Initializable {

    @javafx.fxml.FXML
    private TextField txtNombre;
    @javafx.fxml.FXML
    private TextField txtTelefono;
    @javafx.fxml.FXML
    private TextField txtResponsable;
    @javafx.fxml.FXML
    private TextArea txtObservaciones;
    @javafx.fxml.FXML
    private TableView<Enterprise> tvEmpresas;
    @javafx.fxml.FXML
    private TableColumn<Enterprise, String> cNombre;
    @javafx.fxml.FXML
    private TableColumn<Enterprise, String> cTelefono;
    @javafx.fxml.FXML
    private TableColumn<Enterprise, String> cEmail;
    @javafx.fxml.FXML
    private TableColumn<Enterprise, String> cResponsable;
    @javafx.fxml.FXML
    private TableColumn<Enterprise, String> cObservaciones;
    private ObservableList<Enterprise> observableEnterprises;
    private Enterprise enterprise;
    @javafx.fxml.FXML
    private Button botonLimpiar;
    @javafx.fxml.FXML
    private Button botonAñadirEmpresa;
    @javafx.fxml.FXML
    private Button botonActualizar;
    private ArrayList<Enterprise> listaEnterprises =new ArrayList<>();
    private ChangeListener<Enterprise> selectionListener;
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

        EnterpriseDAOImp dao = new EnterpriseDAOImp(DBConnection.getConnection());
        listaEnterprises.addAll(dao.loadAll());
        if (listaEnterprises.isEmpty()) {
            listaEnterprises.addAll(dao.loadAll());
            observableEnterprises = FXCollections.observableArrayList();
            observableEnterprises.addAll(listaEnterprises);
            tvEmpresas.setItems(observableEnterprises);
        } else {
            tvEmpresas.getItems().clear();
            observableEnterprises = FXCollections.observableArrayList();
            observableEnterprises.addAll(listaEnterprises);
            tvEmpresas.setItems(observableEnterprises);
        }

        selectionListener = (observableValue, old, t1) -> {

                System.out.println("PASO POR AQUI");
                enterprise = t1;
                txtTelefono.setText(""+ enterprise.getPhone());
                txtEmail.setText(enterprise.getEmail());
                txtNombre.setText(enterprise.getName());
                txtResponsable.setText(enterprise.getBoss());
                txtObservaciones.setText(enterprise.getObservations());
        };
        tvEmpresas.getSelectionModel().selectedItemProperty().addListener(selectionListener);
    }

    @javafx.fxml.FXML
    public void actualizar(ActionEvent actionEvent) {
                try{
                    if (!txtTelefono.getText().isEmpty() && !txtEmail.getText().isEmpty()
                            && !txtNombre.getText().isEmpty() && !txtResponsable.getText().isEmpty()
                            && enterprise !=null) {
                        tvEmpresas.getSelectionModel().selectedItemProperty().removeListener(selectionListener);

                        enterprise.setPhone(Integer.valueOf(txtTelefono.getText()));
                        enterprise.setEmail(txtEmail.getText());
                        enterprise.setName(txtNombre.getText());
                        enterprise.setBoss(txtResponsable.getText());
                        enterprise.setObservations(txtObservaciones.getText());
                        Enterprise enterpriseActualizada = (new EnterpriseDAOImp(DBConnection.getConnection()).update(enterprise));

                        listaEnterprises = (new EnterpriseDAOImp(DBConnection.getConnection()).loadAll());
                        enterprise = enterpriseActualizada;
                        System.out.println(listaEnterprises);
                        tvEmpresas.getItems().clear();
                        observableEnterprises.addAll(listaEnterprises);
                        enterprise =null;
                        tvEmpresas.getSelectionModel().selectedItemProperty().addListener(selectionListener);
                    } else {
                        Alert alerta = new Alert(Alert.AlertType.ERROR);
                        alerta.setTitle("Error");
                        alerta.setHeaderText("Porfavor comprueba de que los campos estén rellenos y \n" +
                                "tengas seleccionado/a una empresa en la tabla");
                        alerta.showAndWait();
                    }
                }catch(Exception e){
                    Alert alerta = new Alert(Alert.AlertType.ERROR);
                    alerta.setTitle("Error");
                    alerta.setHeaderText("Comprueba que el campo telefono sea correcto");
                    alerta.showAndWait();
                }

        }



    @javafx.fxml.FXML
    public void limpiar(ActionEvent actionEvent) {
        enterprise =null;
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
            if(!txtTelefono.getText().isEmpty()&&!txtObservaciones.getText().isEmpty()&&!txtEmail.getText().isEmpty()
            &&!txtNombre.getText().isEmpty()&&!txtResponsable.getText().isEmpty()){
                Enterprise enterpriseNueva =new Enterprise();
                enterpriseNueva.setPhone(Integer.valueOf(txtTelefono.getText()));
                enterpriseNueva.setEmail(txtEmail.getText());
                enterpriseNueva.setName(txtNombre.getText());
                enterpriseNueva.setBoss(txtResponsable.getText());
                enterpriseNueva.setObservations(txtObservaciones.getText());
                Enterprise enterpriseAnhadida =(new EnterpriseDAOImp(DBConnection.getConnection()).injection(enterpriseNueva));
                listaEnterprises.add(enterpriseAnhadida);
                observableEnterprises.add(enterpriseAnhadida);
            }else{
                Alert alerta = new Alert(Alert.AlertType.ERROR);
                alerta.setTitle("Error");
                alerta.setHeaderText("Por favor rellene todos los campos vacíos.");
                alerta.showAndWait();
            }

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
        if(enterprise !=null){
            try{
                Alert alerta=new Alert(Alert.AlertType.ERROR);
                alerta.setHeaderText("¿Seguro que deseas eliminar esta empresa?");
                ButtonType tipo= alerta.showAndWait().get();
                if(tipo.getButtonData()== ButtonBar.ButtonData.OK_DONE){
                    tvEmpresas.getSelectionModel().selectedItemProperty().removeListener(selectionListener);
                    System.out.println(enterprise);
                    listaEnterprises.remove(listaEnterprises.get(listaEnterprises.indexOf(enterprise)));
                    (new EnterpriseDAOImp(DBConnection.getConnection())).delete(enterprise);
                    observableEnterprises.setAll(listaEnterprises);
                    enterprise =null;
                    tvEmpresas.getSelectionModel().selectedItemProperty().addListener(selectionListener);
                }
            }catch(Exception e){

            }


        }else{
            Alert alerta=new Alert(Alert.AlertType.ERROR);
            alerta.setHeaderText("Porfavor selecciona una empresa en la tabla");
            alerta.showAndWait();

        }


    }
}

