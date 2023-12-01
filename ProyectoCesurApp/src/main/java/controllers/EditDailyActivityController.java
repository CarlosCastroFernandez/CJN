package controllers;

import classes.DailyActivity;
import classes.Sesion;
import com.example.proyectocesurapp.App;
import domain.dailyActivity.DailyActivityDAOImp;
import domain.DBConnection;
import enums.PracticeType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.StringConverter;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

/**
 * Clase controladora de la edición de una actividad diaria por parte de un alumno.
 */
public class EditDailyActivityController implements Initializable {
    @FXML
    private Button btnSave;
    @FXML
    private Button btnCancel;

    /**
     * DatePicker para la fecha de realización de la actividad diaria.
     */
    @FXML
    private DatePicker dpDate;

    /**
     * ComboBox para el tipo de prácticas de cada actividad.
     */
    @FXML
    private ComboBox<PracticeType> comboPracticeType;

    /**
     * Spinner para las horas dedicadas a cada actividad.
     */
    @FXML
    private Spinner<Integer> spHoras;

    /**
     * TextField para el nombre de la actividad diaria realizada.
     */
    @FXML
    private TextField tfActivity;

    /**
     * TextArea para las observaciones de la actividad diaria realizada.
     */
    @FXML
    private TextArea taObservations;


    /**
     * Método de inicialización asociado al controlador de una vista específica en JavaFX.
     * Se ejecuta automáticamente al inicializar la vista y se encarga de establecer valores
     * predeterminados para los elementos de la interfaz gráfica, como DatePicker y ComboBox.
     *
     * @param url            La ubicación utilizada para resolver rutas relativas para recursos.
     * @param resourceBundle Un recurso específico para localizar bundles de recursos.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dpDate.setValue(LocalDate.parse(Sesion.getActivity().getDate()));
        comboPracticeType.setConverter(new StringConverter<PracticeType>() {
            @Override
            public String toString(PracticeType practiceType) {
                if (practiceType != null) {
                    return String.valueOf(practiceType);
                } else {
                    return null;
                }
            }

            @Override
            public PracticeType fromString(String s) {
                return null;
            }
        });

        comboPracticeType.getItems().addAll(PracticeType.FCT, PracticeType.DUAL);
        comboPracticeType.setValue(Sesion.getActivity().getPracticeType());
        comboPracticeType.setDisable(true);
        tfActivity.setText(Sesion.getActivity().getTaskName());
        taObservations.setText(Sesion.getActivity().getObservations());
        spHoras.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0,8,Sesion.getActivity().getTotalHours(),1));
    }

    /**
     * Se encarga de guardar los nuevos valores para la actividad diaria.
     *
     * @param actionEvent El evento que activa esta función.
     */
    @FXML
    public void save(ActionEvent actionEvent) {
        if (!(dpDate.getValue() == null) &&
        !tfActivity.getText().isEmpty()){
             Sesion.getActivity().setTaskName(tfActivity.getText());
             Sesion.getActivity().setDate(String.valueOf(dpDate.getValue()));
             Sesion.getActivity().setPracticeType(comboPracticeType.getValue());
             Sesion.getActivity().setTotaHours(spHoras.getValue());
             Sesion.getActivity().setObservations(taObservations.getText());
            DailyActivity dailyActivity = (new DailyActivityDAOImp(DBConnection.getConnection()).update(Sesion.getActivity()));
            App.loadFXML("alumnView-controller.fxml");

            //Control de errores si algo falla.
        } else {
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Porfavor comprueba de que los campos estén rellenos");
            alert.showAndWait();
        }
    }

    /**
     * Cancela la edición de la actividad diaria y retorna a la ventana de alumno.
     *
     * @param actionEvent El evento que activa esta función.
     */
    @FXML
    public void cancel(ActionEvent actionEvent) {
        App.loadFXML("alumnView-controller.fxml");
    }

}
