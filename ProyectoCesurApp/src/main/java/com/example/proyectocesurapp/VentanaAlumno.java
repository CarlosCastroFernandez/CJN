package com.example.proyectocesurapp;

import clase.ActividadDiaria;
import clase.Sesion;
import domain.ActividaDiariaDAOImp;
import domain.DBConnection;
import enums.TipoPractica;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class VentanaAlumno implements Initializable {
    @javafx.fxml.FXML
    private ImageView ivUser;
    @javafx.fxml.FXML
    private DatePicker dpDate;
    @javafx.fxml.FXML
    private TextArea taObservations;
    @javafx.fxml.FXML
    private Button btnAdd;
    @javafx.fxml.FXML
    private TableView<ActividadDiaria> tvUser;
    @javafx.fxml.FXML
    private TableColumn<ActividadDiaria, String> cDate;
    @javafx.fxml.FXML
    private TableColumn<ActividadDiaria, String> cTPractice;
    @javafx.fxml.FXML
    private TableColumn<ActividadDiaria, String> cTHours;
    @javafx.fxml.FXML
    private TableColumn<ActividadDiaria, String> cActivityRealised;
    @javafx.fxml.FXML
    private TableColumn<ActividadDiaria, String> cObservations;
    @javafx.fxml.FXML
    private MenuBar menu;
    @javafx.fxml.FXML
    private ComboBox<TipoPractica> comboPracticeType;
    @javafx.fxml.FXML
    private Spinner<Integer> spHoras;
    @javafx.fxml.FXML
    private TextField fieldActivity;
    private ObservableList<ActividadDiaria> observableActividad;

    @javafx.fxml.FXML
    public void activityInsert(ActionEvent actionEvent) {
        ActividadDiaria dayActivity = new ActividadDiaria();
        dayActivity.setFecha(String.valueOf(dpDate.getValue()));
        dayActivity.setTipoPractica(comboPracticeType.getSelectionModel().getSelectedItem());
        dayActivity.setTotalHoras(spHoras.getValue());
        dayActivity.setNombreTarea(fieldActivity.getText());
        dayActivity.setObservaciones(taObservations.getText());
        ActividaDiariaDAOImp dao = new ActividaDiariaDAOImp(DBConnection.getConnection());
        Sesion.setActividadDiaria(dao.insercion(dayActivity));
        Sesion.getListaActividades().add(dayActivity);
        observableActividad.add(dayActivity);
        tvUser.getItems().add(dayActivity);

    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<TipoPractica> practiceType = FXCollections.observableArrayList();
        practiceType.addAll(TipoPractica.DUAL, TipoPractica.FCT);
        comboPracticeType.setItems(practiceType);
        comboPracticeType.getSelectionModel().selectFirst();
        spHoras.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0,8,0,1));
        cDate.setCellValueFactory((fila)->{
            String fecha=fila.getValue().getFecha();
            return new SimpleStringProperty(fecha);
        });
        cTPractice.setCellValueFactory( (fila) -> {
            String tipoPractica =String.valueOf(fila.getValue().getTipoPractica());
            return new SimpleStringProperty(tipoPractica);
        });
        cTHours.setCellValueFactory( (fila) -> {
            String totalHoras = String.valueOf(fila.getValue().getTotalHoras());
            return new SimpleStringProperty(totalHoras);
        });
        cObservations.setCellValueFactory( (fila) -> {
            String observaciones=fila.getValue().getObservaciones();
            return new SimpleStringProperty(observaciones);
        });
        cActivityRealised.setCellValueFactory( (fila) -> {
            String tareaRealizada = fila.getValue().getNombreTarea();
            return new SimpleStringProperty(tareaRealizada);
        });

        //Consulta a bbdd: "Todas las actividades diarias que tiene el alumno logeado".
        Integer idAlumno = Sesion.getAlumno().getId();
        ActividaDiariaDAOImp dao=new ActividaDiariaDAOImp(DBConnection.getConnection());
        Sesion.setListaActividades(dao.loadall(idAlumno));
        observableActividad = FXCollections.observableArrayList();
        observableActividad.addAll(Sesion.getListaActividades());
        tvUser.setItems(observableActividad);
        /* Los datos de esta consulta a Array de Sesion, de este array al observable y del observable a la tabla. */
    }


}
