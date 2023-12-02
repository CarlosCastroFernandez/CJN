package controllers;

import classes.Enterprise;
import com.example.proyectocesurapp.App;
import domain.DBConnection;
import domain.enterprise.EnterpriseDAOImp;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class EnterpriseViewController implements Initializable {
    @FXML
    private Button btnClean;
    @FXML
    private Button btnAddEnterprise;
    @FXML
    private Button btnUpdateEnterprise;
    @FXML
    private Button btnDeleteEnterprise;
    @FXML
    private ImageView imageReturn;

    /**
     * TextField para el nombre de la empresa.
     */
    @FXML
    private TextField tfName;

    /**
     * TextField para el teléfono de la empresa.
     */
    @FXML
    private TextField tfTelephone;

    /**
     * TextField para el email de la empresa.
     */
    @FXML
    private TextField tfEmail;

    /**
     * TextField para el responsable de la empresa.
     */
    @FXML
    private TextField tfLeader;

    /**
     * TextArea para las observaciones de la empresa.
     */
    @FXML
    private TextArea taObservations;

    /**
     * Column para el nombre de la empresa.
     */
    @FXML
    private TableColumn<Enterprise, String> cName;

    /**
     * Column para el email de la empresa.
     */
    @FXML
    private TableColumn<Enterprise, String> cEmail;

    /**
     * Column para el teléfono de la empresa.
     */
    @FXML
    private TableColumn<Enterprise, String> cTelephone;

    /**
     * Column para el responsable de la empresa.
     */
    @FXML
    private TableColumn<Enterprise, String> cLeader;

    /**
     * Column para las observaciones de la empresa.
     */
    @FXML
    private TableColumn<Enterprise, String> cObservations;

    /**
     * TableView para pintar las empresas en la vista.
     */
    @FXML
    private TableView<Enterprise> tvEnterprises;

    /**
     * Observable de empresas que sirve de intermediario entre la Base de Datos y la vista.
     */
    private ObservableList<Enterprise> observableEnterprises;

    /**
     * Instancia de empresa.
     */
    private Enterprise enterprise;

    /**
     * Lista de empresas.
     */
    private ArrayList<Enterprise> enterpriseList = new ArrayList<>();

    /**
     * Selection listener de empresas.
     */
    private ChangeListener<Enterprise> selectionListener;


    /**
     * Método que se llama automáticamente al inicializar el controlador de la interfaz.
     *
     * @param url             La ubicación utilizada para resolver rutas relativas para recursos.
     * @param resourceBundle  Un recurso específico para localizar bundles de recursos.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Mapeo de las columnas de la tabla.
        cName.setCellValueFactory((row) -> {
            String name = row.getValue().getName();
            return new SimpleStringProperty(name);
        });
        cEmail.setCellValueFactory((row) -> {
            String email = row.getValue().getEmail();
            return new SimpleStringProperty(email);
        });
        cLeader.setCellValueFactory((row) -> {
            String leader = row.getValue().getBoss();
            return new SimpleStringProperty(leader);
        });
        cTelephone.setCellValueFactory((row) -> {
            String telephone = String.valueOf(row.getValue().getPhone());
            return new SimpleStringProperty(telephone);
        });

        cObservations.setCellValueFactory((row) -> {
            String observations = row.getValue().getObservations();
            return new SimpleStringProperty(observations);
        });

        //Obtiene los datos de la Base de Datos y carga en la tabla.
        EnterpriseDAOImp enterpriseDAOImp = new EnterpriseDAOImp(DBConnection.getConnection());
        enterpriseList.addAll(enterpriseDAOImp.loadAll());
        if (enterpriseList.isEmpty()) {
            enterpriseList.addAll(enterpriseDAOImp.loadAll());
            observableEnterprises = FXCollections.observableArrayList();
            observableEnterprises.addAll(enterpriseList);
            tvEnterprises.setItems(observableEnterprises);
        } else {
            tvEnterprises.getItems().clear();
            observableEnterprises = FXCollections.observableArrayList();
            observableEnterprises.addAll(enterpriseList);
            tvEnterprises.setItems(observableEnterprises);
        }

        //Maneja la selección de elementos en la tabla.
        selectionListener = (observableValue, old, t1) -> {
                enterprise = t1;
                tfTelephone.setText("" + enterprise.getPhone());
                tfEmail.setText(enterprise.getEmail());
                tfName.setText(enterprise.getName());
                tfLeader.setText(enterprise.getBoss());
                taObservations.setText(enterprise.getObservations());
        };
        tvEnterprises.getSelectionModel().selectedItemProperty().addListener(selectionListener);
    }

    /**
     * Método que actualiza los detalles de una empresa, según la información proporcionada en los campos de texto.
     *
     * @param actionEvent Evento de acción que desencadena la actualización de la empresa.
     */
    @FXML
    public void updateEnterprise(ActionEvent actionEvent) {
        try {
            //Verifica si los campos no están vacíos y si hay una empresa seleccionada.
            if (!tfTelephone.getText().isEmpty() && !tfEmail.getText().isEmpty()
                    && !tfName.getText().isEmpty() && !tfLeader.getText().isEmpty()
                    && enterprise != null) {

                //Elimina el listener de selección para evitar interferencias mientras se actualiza la empresa.
                tvEnterprises.getSelectionModel().selectedItemProperty().removeListener(selectionListener);

                //Actualiza los detalles de la empresa con la información de los campos.
                enterprise.setPhone(Integer.valueOf(tfTelephone.getText()));
                enterprise.setEmail(tfEmail.getText());
                enterprise.setName(tfName.getText());
                enterprise.setBoss(tfLeader.getText());
                enterprise.setObservations(taObservations.getText());

                //Actualiza la empresa en la Base de Datos y obtiene la empresa actualizada.
                Enterprise updatedEnterprise = (new EnterpriseDAOImp(DBConnection.getConnection()).update(enterprise));

                //Recarga la lista de empresas desde la Base de Datos.
                enterpriseList = (new EnterpriseDAOImp(DBConnection.getConnection()).loadAll());
                enterprise = updatedEnterprise;

                //Limpia los elementos en la tabla y añade las empresas actualizadas.
                tvEnterprises.getItems().clear();
                observableEnterprises.addAll(enterpriseList);
                enterprise = null;

                //Vuelve a agregar el listener de selección para la tabla.
                tvEnterprises.getSelectionModel().selectedItemProperty().addListener(selectionListener);
            } else {
                //Muestra un mensaje de error si algún campo está vacío o si no se ha seleccionado ninguna empresa.
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Por favor, comprueba que los campos estén rellenos y \n" +
                        "tengas seleccionada una empresa en la tabla");
                alert.showAndWait();
            }
        } catch (NumberFormatException e) {
            //Muestra un mensaje de error si el formato del teléfono no es correcto.
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Comprueba que el campo teléfono sea correcto");
            alert.showAndWait();
        } catch (Exception e) {
            //Muestra un mensaje de error genérico si ocurre una excepción no esperada.
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Ha ocurrido un error inesperado");
            alert.showAndWait();
        }
    }

    /**
     * Método que limpia los campos relacionados con la información de la empresa en la interfaz.
     *
     * @param actionEvent Evento de acción que desencadena la limpieza de los campos de la empresa.
     */
    @FXML
    public void cleanEnterprise(ActionEvent actionEvent) {
        //Establece la variable de empresa como nula y limpia los campos de texto.
        enterprise = null;
        tfTelephone.clear();
        tfEmail.clear();
        tfName.clear();
        tfLeader.clear();
        taObservations.clear();

        //Elimina y vuelve a agregar el listener de selección para la tabla de empresas para evitar interferencias.
        tvEnterprises.getSelectionModel().selectedItemProperty().removeListener(selectionListener);
        tvEnterprises.getSelectionModel().select(null);
        tvEnterprises.getSelectionModel().selectedItemProperty().addListener(selectionListener);
    }

    /**
     * Método que agrega una nueva empresa a la interfaz y a la Base de Datos.
     *
     * @param actionEvent Evento de acción que desencadena la inserción de la empresa.
     */
    @FXML
    public void addEnterprise(ActionEvent actionEvent) {
        try {
            //Verifica si los campos no están vacíos.
            if (!tfTelephone.getText().isEmpty() && !taObservations.getText().isEmpty() &&
                    !tfEmail.getText().isEmpty() && !tfName.getText().isEmpty() && !tfLeader.getText().isEmpty()) {

                //Crea una nueva instancia de Empresa con la información proporcionada.
                Enterprise newEnterprise = new Enterprise();
                newEnterprise.setPhone(Integer.valueOf(tfTelephone.getText()));
                newEnterprise.setEmail(tfEmail.getText());
                newEnterprise.setName(tfName.getText());
                newEnterprise.setBoss(tfLeader.getText());
                newEnterprise.setObservations(taObservations.getText());

                //Agrega la nueva empresa a la Base de Datos y obtiene la empresa añadida.
                Enterprise addedEnterprise = (new EnterpriseDAOImp(DBConnection.getConnection()).injection(newEnterprise));

                //Agrega la empresa a las lista y al Observable.
                enterpriseList.add(addedEnterprise);
                observableEnterprises.add(addedEnterprise);
            } else {
                //Muestra un mensaje de error si algún campo está vacío.
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Por favor, rellene todos los campos vacíos.");
                alert.showAndWait();
            }
        } catch (Exception e) {
            //Muestra un mensaje de error si se produce cualquier otra excepción.
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Esta empresa ya existe, añade otra empresa.");
            ButtonType tipo = alert.showAndWait().get();

            //Una vez hecha la inserción se limpian los campos de texto.
            if (tipo.getButtonData() == ButtonBar.ButtonData.OK_DONE) {
                tfTelephone.clear();
                tfEmail.clear();
                tfName.clear();
                tfLeader.clear();
                taObservations.clear();
            }
            //Desselecciona cualquier elemento seleccionado en la tabla.
            tvEnterprises.getSelectionModel().select(null);
        }
    }

    /**
     * Método que elimina una empresa de la interfaz y de la Base de Datos si está seleccionada.
     *
     * @param actionEvent Evento de acción que desencadena la eliminación de la empresa.
     */
    @FXML
    public void deleteEnterprise(ActionEvent actionEvent) {
        if (enterprise != null) {
            //Pregunta al usuario si está seguro de eliminar la empresa.
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("¿Seguro que deseas eliminar esta empresa?");
            ButtonType type = alert.showAndWait().orElse(ButtonType.CANCEL);

            //Si el usuario confirma la eliminación, procede a eliminar la empresa.
            if (type.getButtonData() == ButtonBar.ButtonData.OK_DONE) {
                //Elimina el listener de selección para evitar interferencias.
                tvEnterprises.getSelectionModel().selectedItemProperty().removeListener(selectionListener);

                //Elimina la empresa de la lista y de la Base de Datos.
                enterpriseList.remove(enterpriseList.get(enterpriseList.indexOf(enterprise)));
                (new EnterpriseDAOImp(DBConnection.getConnection())).delete(enterprise);

                //Actualiza la lista Observable y restablece las selecciones.
                observableEnterprises.setAll(enterpriseList);
                enterprise = null;

                //Vuelve a agregar el listener de selección para la tabla de empresas.
                tvEnterprises.getSelectionModel().selectedItemProperty().addListener(selectionListener);
            }
        } else {
            //Muestra un mensaje de error si no se ha seleccionado ninguna empresa para eliminar.
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Por favor selecciona una empresa en la tabla");
            alert.showAndWait();
        }
    }

    /**
     * Método que permite regresar a la ventana de profesor.
     *
     * @param event Evento que desencadena el regreso a la ventana de profesor.
     */
    @FXML
    public void comeBack(Event event) {
        //Carga la ventana de profesor.
        App.loadFXML("teacherView-controller.fxml");
    }
}

