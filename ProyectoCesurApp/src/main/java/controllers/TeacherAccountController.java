package controllers;

import classes.Sesion;
import classes.Teacher;
import com.example.proyectocesurapp.App;
import domain.DBConnection;
import domain.teacher.TeacherDAOImp;
import exception.InvalidDNI;
import exception.LastNameWithNumber;
import exception.NameWithNumber;
import exception.NonExistentUser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Clase que permite ver y editar la información de la cuenta de un profesor.
 */
public class TeacherAccountController implements Initializable {
    @FXML
    private Button btnSave;
    @FXML
    private Button btnCancel;

    /**
     * TextField del nombre del profesor.
     */
    @FXML
    private TextField tfName;

    /**
     * TextField con el apellido1 del profesor.
     */
    @FXML
    private TextField tfLastName1;

    /**
     * TextField con el apellido2 del profesor.
     */
    @FXML
    private TextField tfLastName2;

    /**
     * TextField con el email del profesor.
     */
    @FXML
    private TextField tfEmail;

    /**
     * TextField con el DNI del profesor.
     */
    @FXML
    private TextField tfDNI;

    /**
     * TextField con el teléfono del profesor.
     */
    @FXML
    private TextField tfTelephone;

    /**
     * PasswordField con la contraseña del profesor.
     */
    @FXML
    private PasswordField pfTeacher;


    /**
     * Método de inicialización del controlador de JavaFX.
     * Se ejecuta automáticamente al inicializar el controlador.
     * Establece valores predeterminados en los campos de texto y contraseña
     * de la interfaz gráfica basándose en la información del profesor almacenada en la sesión actual.
     *
     * @param url            La ubicación utilizada para resolver rutas relativas para recursos.
     * @param resourceBundle Un recurso específico para localizar bundles de recursos.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tfName.setText(Sesion.getTeacher().getName());
        tfDNI.setText(Sesion.getTeacher().getDni());
        tfEmail.setText(Sesion.getTeacher().getEmail());
        tfLastName1.setText(Sesion.getTeacher().getLastName());
        tfLastName2.setText(Sesion.getTeacher().getLastName2());
        tfTelephone.setText("" + Sesion.getTeacher().getPhone());
        pfTeacher.setText(Sesion.getTeacher().getPassword());
    }

    /**
     * Método asociado al evento de guardado de los datos del profesor.
     * Recopila los datos ingresados en la interfaz gráfica para crear un nuevo objeto Teacher,
     * actualiza los datos del profesor en la Base de Datos y realiza la carga de una nueva vista
     * con la información actualizada del profesor.
     *
     * @param actionEvent El evento que activa esta función.
     * @throws RuntimeException si se encuentran excepciones como InvalidDNI, NameWithNumber,
     *         LastNameWithNumber o NonExistentUser durante la ejecución del método.
     */
    @FXML
    public void save(ActionEvent actionEvent) {
        Teacher nuevo = new Teacher();
        try {
            nuevo.setDni(tfDNI.getText());
            nuevo.setEmail(tfEmail.getText());
            nuevo.setName(tfName.getText());
            nuevo.setLastName(tfLastName1.getText());
            nuevo.setLastName2(tfLastName2.getText());
            nuevo.setPassword(pfTeacher.getText());
            nuevo.setPhone(Integer.valueOf(tfTelephone.getText()));
        } catch (InvalidDNI e) {
            throw new RuntimeException(e);
        } catch (NameWithNumber e) {
            throw new RuntimeException(e);
        } catch (LastNameWithNumber e) {
            throw new RuntimeException(e);
        }
        (new TeacherDAOImp(DBConnection.getConnection())).update(nuevo);
        try {
            Sesion.setTeacher(new TeacherDAOImp(DBConnection.getConnection()).loadTeacherById(Sesion.getTeacher().getId()));
            Alert alert =new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Modificado Con Exito");
            alert.showAndWait();
            Sesion.setCount((byte)1);
            App.loadFXML("teacherView-controller.fxml");
        } catch (NonExistentUser e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Cancela el proceso de edición de los datos del profesor y lleva a la ventada de profesor.
     *
     * @param actionEvent El evento que activa esta acción.
     */
    @FXML
    public void cancel(ActionEvent actionEvent) {
        App.loadFXML("teacherView-controller.fxml");
    }


}
