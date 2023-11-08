package com.example.proyectocesurapp;

import clase.Sesion;
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
    private ComboBox comboPracticeType;
    @javafx.fxml.FXML
    private Spinner spHoras;
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
        dpDate.setValue(LocalDate.parse(Sesion.getActividadDiaria().getFecha()));
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
        comboPracticeType.setValue(Sesion.getActividadDiaria().getTipoPractica());
        fieldActivity.setText(Sesion.getActividadDiaria().getNombreTarea());
        taObservations.setText(Sesion.getActividadDiaria().getObservaciones());
        spHoras.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0,8,Sesion.getActividadDiaria().getTotalHoras(),1));

    }

    @javafx.fxml.FXML
    public void guardar(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void cancelar(ActionEvent actionEvent) {

    }
}
