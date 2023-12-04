package controllers;

import classes.Sesion;
import com.example.proyectocesurapp.App;
import domain.alumn.AlumnDAOImp;
import domain.DBConnection;
import domain.teacher.TeacherDAOImp;
import exception.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Clase controladora de la ventana de Login.
 */
public class LoginController implements Initializable {
    @FXML
    private Button btnLogin;
    @FXML
    private Button btnRegister;

    /**
     * TextField para que el usuario introduzca su DNI.
     */
    @FXML
    private TextField tfDNI;

    /**
     * PasswordField para que el usuario introduzca su contraseña.
     */
    @FXML
    private PasswordField pfPassword;


    /**
     * Método que maneja el evento de inicio de sesión.
     *
     * @param actionEvent Evento de acción que desencadena el inicio de sesión.
     */
    @FXML
    public void login(ActionEvent actionEvent) {
        //Obtiene el DNI y la contraseña ingresados por el usuario.
        String userDNI = tfDNI.getText();
        String userPassword = pfPassword.getText();

        //Se inicializan los 'DAOImp' para profesores y alumnos.
        TeacherDAOImp teacherDAOImp = new TeacherDAOImp(DBConnection.getConnection());
        AlumnDAOImp alumnDAOImp = new AlumnDAOImp(DBConnection.getConnection());

        try {
            //Intenta iniciar sesión como profesor.
            Sesion.setTeacher(teacherDAOImp.login(userDNI, userPassword));

            //Muestra un mensaje de bienvenida y carga la ventana de profesor si el inicio es correcto.
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Login");
            alert.setHeaderText("Bienvenido " + Sesion.getTeacher().getName());
            alert.setContentText("Inicio Correcto");
            alert.showAndWait();
            App.loadFXML("teacherView-controller.fxml");

        } catch (NonExistentUser e) {
            try {
                //Si el inicio de sesión como profesor falla, intenta iniciar sesión como alumno.
                Sesion.setAlumn(alumnDAOImp.login(userDNI, userPassword));
                //Carga la vista de alumno si el inicio tiene éxito.
                App.loadFXML("alumnView-controller.fxml");
            } catch (IncorrectPassword ex) {
                //Si la contraseña del alumno es incorrecta, lanza una excepción.
                throw new RuntimeException(ex);
            } catch (NonExistentUser ex) {
                //Si el usuario alumno no existe, muestra un mensaje de error y lanza una excepción.
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Usuario Inexistente.");
                alert.showAndWait();
                throw new RuntimeException(ex);
            }
        } catch (IncorrectPassword e) {
            //Si la contraseña del profesor es incorrecta, muestra un mensaje de error y lanza una excepción.
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Contraseña incorrecta.");
            alert.showAndWait();
            throw new RuntimeException(e);
        }
    }

    /**
     * Método que maneja el evento de registro por parte del profesor.
     *
     * @param actionEvent Evento de acción que desencadena el registro.
     */
    @FXML
    public void register(ActionEvent actionEvent) {
        //Clave de acceso para que el profesor pueda registrarse.
        String cadena = "@cesur1234";

        //Crea un cuadro de diálogo para solicitar la clave.
        TextInputDialog text = new TextInputDialog();
        text.setTitle("Comprobación");
        text.setHeaderText("Ingrese la clave");

        //Muestra el cuadro de diálogo y maneja la entrada del usuario
        text.showAndWait().ifPresent(clave -> {
            if (!clave.equals(cadena)) { //Comprueba si la clave ingresada no coincide con la clave predefinida.
                //Si la clave es incorrecta, muestra un mensaje de error
                Alert alerta = new Alert(Alert.AlertType.ERROR);
                alerta.setTitle("Error");
                alerta.setHeaderText("Clave incorrecta");
                alerta.showAndWait();
            } else {
                //Si la clave es correcta, carga la ventana de registro de profesores.
                App.loadFXML("teacherRegister-controller.fxml");
            }
        });
    }

    /**
     * Método que se llama automáticamente al inicializar el controlador de la interfaz.
     *
     * @param url             La ubicación utilizada para resolver rutas relativas para recursos.
     * @param resourceBundle  Un recurso específico para localizar bundles de recursos.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Do nothing.
    }
}
