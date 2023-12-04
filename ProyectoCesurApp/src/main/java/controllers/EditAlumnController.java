package controllers;

import classes.Alumn;
import classes.Enterprise;
import classes.Sesion;
import com.example.proyectocesurapp.App;
import domain.alumn.AlumnDAOImp;
import domain.DBConnection;
import enums.Grade;
import domain.enterprise.EnterpriseDAOImp;
import enums.PracticeType;
import exception.LastNameWithNumber;
import exception.InvalidDNI;
import exception.NameWithNumber;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.StringConverter;


import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

/**
 * Clase controladora para editar la información del perfil del alumno.
 */
public class EditAlumnController implements Initializable {
    @FXML
    private Button btnSave;
    @FXML
    private Button btnCancel;

    /**
     * Spinner para las horas dedicadas a las prácticas FCT por el alumno.
     */
    @FXML
    private Spinner spinnerFCT;

    /**
     * TextField para el DNI del alumno.
     */
    @FXML
    private TextField tfDNI;

    /**
     * TextField para el nombre del alumno.
     */
    @FXML
    private TextField tfName;

    /**
     * TextField para el email del alumno.
     */
    @FXML
    private TextField tfEmail;

    /**
     * TextField para el teléfono del alumno.
     */
    @FXML
    private TextField tfTelephone;

    /**
     * DatePicker para la fecha de nacimiento del alumno.
     */
    @FXML
    private DatePicker dpCalendar;

    /**
     * ComboBox para la selección del grado al que pertenece el alumno.
     */
    @FXML
    private ComboBox<Grade> comboGrade;

    /**
     * ComboBox para seleccionar la empresa a la que pertenece el alumno.
     */
    @FXML
    private ComboBox<Enterprise> comboNameEnterprise;

    /**
     * ChoiceBox para seleccionar el tipo de práctica (Dual o FCT).
     */
    @FXML
    private ChoiceBox choicePracticeType;

    /**
     * Spinner para seleccionar el total de horas dedicadas al tipo de prácticas.
     */
    @FXML
    private Spinner spHours;

    /**
     * TextField para el segundo apellido del alumno.
     */
    @FXML
    private TextField tfLastName2;

    /**
     * TextField para el primer apellido del alumno.
     */
    @FXML
    private TextField tfLastName1;

    /**
     * Inicializa los campos de la interfaz de usuario con los datos del alumno actual al cargar la ventana.
     *
     * @param url            La ubicación utilizada para resolver rutas relativas de archivos.
     * @param resourceBundle El paquete de recursos utilizado para localizar archivos de recursos.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Configura los campos de texto con los datos del alumno.
        tfName.setText(Sesion.getAlumn().getName());
        tfLastName1.setText(Sesion.getAlumn().getLastName());
        tfLastName2.setText(Sesion.getAlumn().getLastName2());
        tfTelephone.setText("" + Sesion.getAlumn().getPhone());
        tfDNI.setText(Sesion.getAlumn().getDni());
        tfEmail.setText(Sesion.getAlumn().getEmail());

        //Configura el DatePicker con la fecha de nacimiento del alumno.
        DateTimeFormatter date = DateTimeFormatter.ofPattern("yyy-MM-dd");
        String alumnDate = Sesion.getAlumn().getBirthday();
        LocalDate converterDate = LocalDate.parse(alumnDate,date);
        dpCalendar.setValue(converterDate);

        //Configura las opciones de ChoiceBox y ComboBox.
        choicePracticeType.setConverter(new StringConverter<PracticeType>() {
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
            comboGrade.setConverter(new StringConverter<Grade>() {
                @Override
                public String toString(Grade grade) {
                    if (grade != null) {
                        return String.valueOf(grade);
                    } else {
                        return null;
                    }
                }
                @Override
                public Grade fromString(String s) {
                    return null;
                }
            });

            comboNameEnterprise.setConverter(new StringConverter<Enterprise>() {
                @Override
                public String toString(Enterprise enterprise) {
                    if (enterprise != null) {
                        return enterprise.getName();
                    } else {
                        return "<<Sin Empresa>>";
                    }
                }
                @Override
                public Enterprise fromString(String s) {
                    return null;
                }
            });
        choicePracticeType.getItems().addAll(PracticeType.DUAL, PracticeType.FCT);
        choicePracticeType.getSelectionModel().selectedItemProperty().addListener((observableValue, tipoPractica, t1) -> {

                if (t1 == PracticeType.DUAL) {
                    choicePracticeType.setValue(PracticeType.DUAL);
                    spinnerFCT.setVisible(false);
                    spinnerFCT.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 270, 0, 1));
                    spHours.setVisible(true);
                } else{
                choicePracticeType.setValue(PracticeType.FCT);
                spHours.setVisible(false);
                spHours.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 270, 0, 1));
                spinnerFCT.setVisible(true);
            }
        });

        try {
            if (Sesion.getAlumn().getHoursDUAL().contains("/270")) {
                choicePracticeType.setValue(PracticeType.DUAL);
                spinnerFCT.setVisible(false);
            }
        } catch (NullPointerException e) {
            choicePracticeType.setValue(PracticeType.FCT);
            spHours.setVisible(false);
        }
        comboGrade.getItems().addAll(Grade.ASIR1, Grade.ASIR2, Grade.DAM1, Grade.DAM2, Grade.DAW1, Grade.DAW2);
        comboGrade.setValue(Sesion.getAlumn().getGrade());
        comboNameEnterprise.getItems().addAll(new EnterpriseDAOImp(DBConnection.getConnection()).loadAll());
        comboNameEnterprise.setValue(Sesion.getAlumn().getEnterprise());
        comboNameEnterprise.getItems().add(null);
        try{
            String[] initialHours = Sesion.getAlumn().getHoursDUAL().split("/");
            if (choicePracticeType.getValue().equals(PracticeType.DUAL)) {
                spHours.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 270, Integer.valueOf(initialHours[0]), 1));
            } else {
                spinnerFCT.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 270, Integer.valueOf(initialHours[0]), 1));
            }
        }catch(Exception e){
            String[] initialHours = Sesion.getAlumn().getHoursFCT().split("/");
            if (choicePracticeType.getValue().equals(PracticeType.DUAL)) {
                spHours.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 270, Integer.valueOf(initialHours[0]), 1));
            } else {
                spinnerFCT.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 270, Integer.valueOf(initialHours[0]), 1));
            }
        }
    }

    /**
     * Método asociado al evento de guardar los datos del alumno en la interfaz de usuario y en la Base de Datos.
     *
     * @param actionEvent El evento que desencadena la acción.
     */
    @FXML
    public void save(ActionEvent actionEvent) {
        //Verifica si todos los campos requeridos no están vacíos.
        if (!tfDNI.getText().isEmpty() && !tfName.getText().isEmpty() && !tfEmail.getText().isEmpty()
                && !tfLastName1.getText().isEmpty() && !tfLastName2.getText().isEmpty() && !(dpCalendar.getValue() == null)
                && !tfTelephone.getText().isEmpty()) {

            try {
                //Actualiza los datos del alumno con los valores de los campos en la interfaz.
                Sesion.getAlumn().setDni(tfDNI.getText());
                Sesion.getAlumn().setName(tfName.getText());
                Sesion.getAlumn().setLastName(tfLastName1.getText());
                Sesion.getAlumn().setLastName2(tfLastName2.getText());
                Sesion.getAlumn().setEmail(tfEmail.getText());
                Sesion.getAlumn().setBirthday(String.valueOf(dpCalendar.getValue()));
                Sesion.getAlumn().setPhone(Integer.valueOf(tfTelephone.getText()));
                Sesion.getAlumn().setGrade(comboGrade.getValue());

                //Verifica si la empresa seleccionada es nula y actualiza los datos del alumno en consecuencia.
                if (comboNameEnterprise.getValue() == null) {
                    Sesion.getAlumn().setEnterpriseID(0);
                    Sesion.getAlumn().setEnterprise(null);
                } else {
                    Sesion.getAlumn().setEnterprise(comboNameEnterprise.getValue());
                    Sesion.getAlumn().setEnterpriseID(comboNameEnterprise.getValue().getId());
                }

                //Verifica el tipo de práctica seleccionada y actualiza las horas correspondientes del alumno.
                if (choicePracticeType.getValue() == PracticeType.DUAL) {
                    Sesion.getAlumn().setHoursDUAL(spHours.getValue() + "/270");
                    Sesion.getAlumn().setHoursFCT(null);
                    new AlumnDAOImp(DBConnection.getConnection()).updateHours(Sesion.getAlumn(), "horasFCT");
                } else {
                    Sesion.getAlumn().setHoursFCT(spinnerFCT.getValue() + "/270");
                    Sesion.getAlumn().setHoursDUAL(null);
                    new AlumnDAOImp(DBConnection.getConnection()).updateHours(Sesion.getAlumn(), "horasDual");
                }

                //Realiza la actualización en la Base de Datos.
                Alumn alumn = (new AlumnDAOImp(DBConnection.getConnection()).update(Sesion.getAlumn()));

                //Redirige a la ventana de profesor después de guardar los datos del alumno.
                App.loadFXML("teacherView-controller.fxml");
            } catch (InvalidDNI | NameWithNumber e) {
                //En caso de error, se lanza una excepción.
                throw new RuntimeException(e);
            } catch (LastNameWithNumber e) {
                throw new RuntimeException(e);
            }

        } else {
            //Si hay campos vacíos, muestra una alerta de error.
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Por favor, verifica que todos los campos estén rellenados");
            alert.showAndWait();
        }
    }

    /**
     * Método para cancelar la edición de los datos del alumno y que te devuelve a la ventana de profesor.
     *
     * @param actionEvent El evento que desencadena la acción.
     */
    @FXML
    public void cancel(ActionEvent actionEvent) {
        Sesion.setAlumn(null);
        App.loadFXML("teacherView-controller.fxml");
    }
}

