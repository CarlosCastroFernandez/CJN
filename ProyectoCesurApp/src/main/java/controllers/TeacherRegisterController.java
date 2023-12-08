package controllers;

import classes.Teacher;
import com.example.proyectocesurapp.App;
import domain.DBConnection;
import domain.teacher.TeacherDAOImp;
import exception.LastNameWithNumber;
import exception.InvalidDNI;
import exception.NameWithNumber;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Calse que establece la ventana de registro de profesor.
 */
public class TeacherRegisterController implements Initializable {
    @FXML
    private Button btnRegister;
    @FXML
    private Button btnCancel;

    /**
     * TextField para el email del profesor.
     */
    @FXML
    private TextField tfEmail;

    /**
     * TextField para el DNI del profesor.
     */
    @FXML
    private TextField tfDNI;

    /**
     * TextField para el nombre del profesor.
     */
    @FXML
    private TextField tfName;

    /**
     * PasswordField para la contraseña del profesor.
     */
    @FXML
    private PasswordField pfTeacher;

    /**
     * TextField para el apellido1 del profesor.
     */
    @FXML
    private TextField tfLastName1;

    /**
     * PasswordField para el apellido2 del profesor.
     */
    @FXML
    private TextField tfLastName2;

    /**
     * TextField para el teléfono del profesor.
     */
    @FXML
    private TextField tfTelephone;

    /**
     * Permite el registro de un profesor en la Base de Datos.
     *
     * @param actionEvent El evento que activa esta función.
     */
    @FXML
    public void registrarse(ActionEvent actionEvent) {
        try{
            //Obtiene los valores de los diferentes campos de texto en la interfaz de usuario.
            String dni = tfDNI.getText();
            String name = tfName.getText();
            String lastName1 = tfLastName1.getText();
            String lastName2 = tfLastName2.getText();
            String email = tfEmail.getText();
            Integer telephone = Integer.valueOf(tfTelephone.getText());
            String password = pfTeacher.getText();
            if(tfDNI.getText().isEmpty()||tfName.getText().isEmpty()|| tfLastName1.getText().isEmpty()||tfLastName2.getText().isEmpty()||tfEmail.getText().isEmpty()||tfTelephone.getText().isEmpty()||pfTeacher.getText().isEmpty()){
                Alert alert=new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Comprueba de que no haya ningun campo VACIO");
                alert.showAndWait();

            }else{
                try {
                    //Crea un nuevo objeto Teacher con los valores recopilados.
                    Teacher teacher = new Teacher(null, name, lastName1, lastName2, password, email, dni, telephone);

                    //Crea una instancia de TeacherDAOImp utilizando una conexión a la Base de Datos.
                    TeacherDAOImp teacherDAOImp = new TeacherDAOImp(DBConnection.getConnection());

                    //Inserta el nuevo objeto Teacher en la Base de Datos.
                    teacher = teacherDAOImp.injection(teacher);

                    //Establecimiento de distintas alertas para el control de errores en cada caso.
                } catch (NameWithNumber e) {
                    Alert alert=new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Nombre con número.");
                    alert.showAndWait();
                    throw new RuntimeException(e);
                } catch (LastNameWithNumber e) {
                    Alert alert=new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Apellido con número.");
                    alert.showAndWait();
                    throw new RuntimeException(e);
                } catch (InvalidDNI e) {
                    Alert alert=new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("DNI inválido.");
                    alert.showAndWait();
                    throw new RuntimeException(e);
                }
                Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Exito");
                alert.setHeaderText("Registro con EXITO.");
                alert.showAndWait();

                //Una vez acaba el registro se vuelve a la ventana de Login.
                App.loadFXML("login-controller.fxml");
            }
        }catch (NumberFormatException e){
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Numero de telefono INVALIDO.");
            alert.setContentText("Introduce uno real");
            alert.showAndWait();
            throw new RuntimeException(e);
        }

    }

    /**
     * Cancela la operación dde registro y vuelve a la ventana de Login.
     *
     * @param actionEvent El evento que activa esta función.
     */
    @FXML
    public void cancel(ActionEvent actionEvent) {
        App.loadFXML("login-controller.fxml");
    }


    /**
     * Método llamado automáticamente cuando se inicializa el controlador de JavaFX.
     * Se implementa como parte de la interfaz Initializable.
     * En esta implementación, el método no realiza ninguna acción específica.
     * Es comúnmente utilizado para realizar inicializaciones adicionales de componentes
     * o datos en el controlador de la interfaz de usuario de JavaFX.
     *
     * @param url            La ubicación utilizada para resolver rutas relativas para recursos.
     * @param resourceBundle Un recurso específico para localizar bundles de recursos.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Do nothing.
    }
}
