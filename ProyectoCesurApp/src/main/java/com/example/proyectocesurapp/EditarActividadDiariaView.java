package com.example.proyectocesurapp;

import clase.ActividadDiaria;
import clase.Sesion;
import domain.ActividaDiariaDAOImp;
import domain.DBConnection;
import enums.TipoPractica;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.StringConverter;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class EditarActividadDiariaView implements Initializable {
    @javafx.fxml.FXML
    private DatePicker dpDate;
    @javafx.fxml.FXML
    private ComboBox<TipoPractica> comboPracticeType;
    @javafx.fxml.FXML
    private Spinner<Integer> spHoras;
    @javafx.fxml.FXML
    private TextField fieldActivity;
    @javafx.fxml.FXML
    private TextArea taObservations;
    @javafx.fxml.FXML
    private Button btnGuardar;
    @javafx.fxml.FXML
    private Button btnCancelar;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dpDate.setValue(LocalDate.parse(Sesion.getActivity().getDate()));
        comboPracticeType.setConverter(new StringConverter<TipoPractica>() {
            @Override
            public String toString(TipoPractica tipoPractica) {
                if (tipoPractica != null) {
                    return String.valueOf(tipoPractica);
                } else {
                    return null;
                }
            }

            @Override
            public TipoPractica fromString(String s) {
                return null;
            }
        });

        comboPracticeType.getItems().addAll(TipoPractica.FCT,TipoPractica.DUAL);
        comboPracticeType.setValue(Sesion.getActivity().getPracticeType());
        comboPracticeType.setDisable(true);
        fieldActivity.setText(Sesion.getActivity().getTaskName());
        taObservations.setText(Sesion.getActivity().getObservations());
        spHoras.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0,8,Sesion.getActivity().getTotalHours(),1));
    }

    @javafx.fxml.FXML
    public void guardar(ActionEvent actionEvent) {
        if (!(dpDate.getValue() == null) &&
        !fieldActivity.getText().isEmpty()){
             Sesion.getActivity().setTaskName(fieldActivity.getText());
             Sesion.getActivity().setDate(String.valueOf(dpDate.getValue()));
             Sesion.getActivity().setPracticeType(comboPracticeType.getValue());
             Sesion.getActivity().setTotaHours(spHoras.getValue());
             Sesion.getActivity().setObservations(taObservations.getText());
            ActividadDiaria actividadDiaria = (new ActividaDiariaDAOImp(DBConnection.getConnection()).update(Sesion.getActivity()));
            HelloApplication.loadFXML("ventanaAlumno.fxml");
        } else {
            Alert alerta=new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Error");
            alerta.setHeaderText("Porfavor comprueba de que los campos estén rellenos");
            alerta.showAndWait();
        }
    }

    @javafx.fxml.FXML
    public void cancelar(ActionEvent actionEvent) {
        HelloApplication.loadFXML("ventanaAlumno.fxml");
    }
}
