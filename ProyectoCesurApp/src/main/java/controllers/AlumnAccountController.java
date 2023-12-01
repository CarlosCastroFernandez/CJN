package controllers;

import classes.Alumn;
import classes.Sesion;
import classes.Teacher;
import com.example.proyectocesurapp.App;
import domain.DBConnection;
import domain.alumn.AlumnDAOImp;
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
 * Clase que permite ver y editar la información de la cuenta de un alumno.
 */
public class AlumnAccountController implements Initializable {
    @FXML
    private Button btnSave;
    @FXML
    private Button btnCancel;

    @FXML
    private TextField tfName;
    @FXML
    private TextField tfLastName1;
    @FXML
    private TextField tfLastName2;
    @FXML
    private TextField tfEmail;
    @FXML
    private TextField tfDNI;
    @FXML
    private TextField tfTelephone;
    @FXML
    private PasswordField pfAlumn;


    /**
     * Método de inicialización del controlador de JavaFX.
     * Se ejecuta automáticamente al inicializar el controlador.
     * Establece valores predeterminados en los campos de texto y contraseña
     * de la interfaz gráfica basándose en la información del alumno almacenada en la sesión actual.
     *
     * @param url            La ubicación utilizada para resolver rutas relativas para recursos.
     * @param resourceBundle Un recurso específico para localizar bundles de recursos.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tfName.setText(Sesion.getAlumn().getName());
        tfDNI.setText(Sesion.getAlumn().getDni());
        tfEmail.setText(Sesion.getAlumn().getEmail());
        tfLastName1.setText(Sesion.getAlumn().getLastName());
        tfLastName2.setText(Sesion.getAlumn().getLastName2());
        tfTelephone.setText("" + Sesion.getAlumn().getPhone());
        pfAlumn.setText(Sesion.getAlumn().getPassword());
    }

    /**
     * Método asociado al evento de guardado de los datos del alumno.
     * Recopila los datos ingresados en la interfaz gráfica para crear un nuevo objeto Alumn,
     * actualiza los datos del alumno en la Base de Datos y realiza la carga de una nueva vista
     * con la información actualizada del alumno.
     *
     * @param actionEvent El evento que activa esta función.
     * @throws RuntimeException si se encuentran excepciones como InvalidDNI, NameWithNumber,
     *         LastNameWithNumber o NonExistentUser durante la ejecución del método.
     */
    @FXML
    public void save(ActionEvent actionEvent) {
        Alumn nuevo=new Alumn();
        try {
            nuevo.setDni(tfDNI.getText());
            nuevo.setEmail(tfEmail.getText());
            nuevo.setName(tfName.getText());
            nuevo.setLastName(tfLastName1.getText());
            nuevo.setLastName2(tfLastName2.getText());
            nuevo.setPassword(pfAlumn.getText());
            nuevo.setPhone(Integer.valueOf(tfTelephone.getText()));
        } catch (InvalidDNI e) {
            throw new RuntimeException(e);
        } catch (NameWithNumber e) {
            throw new RuntimeException(e);
        } catch (LastNameWithNumber e) {
            throw new RuntimeException(e);
        }
        (new AlumnDAOImp(DBConnection.getConnection())).updateAccount(nuevo);

            Sesion.setAlumn(new AlumnDAOImp(DBConnection.getConnection()).load(Sesion.getAlumn().getId()));
            Alert alerta=new Alert(Alert.AlertType.INFORMATION);
            alerta.setHeaderText("Modificado Con Exito");
            alerta.showAndWait();
            App.loadFXML("alumnView-controller.fxml");

    }

    /**
     * Cancela el proceso de edición de los datos del profesor y lleva a la ventada de alumno.
     *
     * @param actionEvent El evento que activa esta acción.
     */
    @FXML
    public void cancel(ActionEvent actionEvent) {
        App.loadFXML("alumnView-controller.fxml");
    }
}
