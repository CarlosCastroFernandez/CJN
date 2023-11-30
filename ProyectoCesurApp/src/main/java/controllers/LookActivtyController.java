package controllers;

import classes.Alumn;
import classes.DailyActivity;
import classes.Sesion;
import com.example.proyectocesurapp.App;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.Event;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class LookActivtyController implements Initializable {
    @javafx.fxml.FXML
    private TableView <DailyActivity>tvUser;
    @javafx.fxml.FXML
    private TableColumn <DailyActivity,String>cDate;
    @javafx.fxml.FXML
    private TableColumn <DailyActivity,String> cTPractice;
    @javafx.fxml.FXML
    private TableColumn  <DailyActivity,String>cTHours;
    @javafx.fxml.FXML
    private TableColumn <DailyActivity,String> cActivityRealised;
    @javafx.fxml.FXML
    private TableColumn <DailyActivity,String> cObservations;
    @javafx.fxml.FXML
    private ImageView imageBack;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cDate.setCellValueFactory((fila) -> {
            String fecha = fila.getValue().getDate();
            return new SimpleStringProperty(fecha);
        });
        cTPractice.setCellValueFactory((fila) -> {
            String tipoPractica = String.valueOf(fila.getValue().getPracticeType());
            return new SimpleStringProperty(tipoPractica);
        });
        cTHours.setCellValueFactory((fila) -> {
            String totalHoras = String.valueOf(fila.getValue().getTotalHours());
            return new SimpleStringProperty(totalHoras);
        });
        cObservations.setCellValueFactory((fila) -> {
            String observaciones = fila.getValue().getObservations();
            return new SimpleStringProperty(observaciones);
        });
        cActivityRealised.setCellValueFactory((fila) -> {
            String tareaRealizada = fila.getValue().getTaskName();
            return new SimpleStringProperty(tareaRealizada);
        });
        tvUser.getItems().addAll(Sesion.getAlumn().getActivity());

    }

    @javafx.fxml.FXML
    public void back(Event event) {
        App.loadFXML("teacherView-controller.fxml");
    }
}
