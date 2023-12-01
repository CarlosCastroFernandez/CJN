package controllers;

import classes.Alumn;
import classes.DailyActivity;
import classes.Sesion;
import com.example.proyectocesurapp.App;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class LookActivtyController implements Initializable {
    @FXML
    private ImageView imageBack;

    /**
     * Column de la fecha en la que se subió la actividad diaria.
     */
    @FXML
    private TableColumn <DailyActivity,String>cDate;

    /**
     * Column del tipo de práctica de la actividad diaria.
     */
    @FXML
    private TableColumn <DailyActivity,String> cTPractice;

    /**
     * Column de las horas tomadas para realizar la actividad diaria.
     */
    @FXML
    private TableColumn  <DailyActivity,String>cTHours;

    /**
     * Column con el nombre de la actividad diaria realizada.
     */
    @FXML
    private TableColumn <DailyActivity,String> cActivityRealised;

    /**
     * Column con las observaciones acerca de la ctividad diaria.
     */
    @FXML
    private TableColumn <DailyActivity,String> cObservations;

    /**
     * TableView que carga la tabla de actividades diarias de la vista.
     */
    @FXML
    private TableView <DailyActivity> tvUser;


    /**
     * Método de inicialización asociado al controlador de una vista específica en JavaFX.
     * Se ejecuta automáticamente al inicializar la vista y se encarga de realizar el mapeo
     * de las columnas de una tabla en la interfaz gráfica con los atributos correspondientes
     * de un objeto de tipo Activity, así como de añadir las actividades del alumno a la tabla de la vista.
     *
     * @param url            La ubicación utilizada para resolver rutas relativas para recursos.
     * @param resourceBundle Un recurso específico para localizar bundles de recursos.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Mapeo de columnas.
        cDate.setCellValueFactory((row) -> {
            String date = row.getValue().getDate();
            return new SimpleStringProperty(date);
        });
        cTPractice.setCellValueFactory((row) -> {
            String practiceType = String.valueOf(row.getValue().getPracticeType());
            return new SimpleStringProperty(practiceType);
        });
        cTHours.setCellValueFactory((row) -> {
            String totalHours = String.valueOf(row.getValue().getTotalHours());
            return new SimpleStringProperty(totalHours);
        });
        cObservations.setCellValueFactory((row) -> {
            String observations = row.getValue().getObservations();
            return new SimpleStringProperty(observations);
        });
        cActivityRealised.setCellValueFactory((row) -> {
            String activityRealised = row.getValue().getTaskName();
            return new SimpleStringProperty(activityRealised);
        });

        //Se añaden las actividades a la tabla de la vista.
        tvUser.getItems().addAll(Sesion.getAlumn().getActivity());

    }

    /**
     * Vuelve a la ventana de profesor.
     *
     * @param event El evento que activa esta acción.
     */
    @FXML
    public void back(Event event) {
        App.loadFXML("teacherView-controller.fxml");
    }
}
