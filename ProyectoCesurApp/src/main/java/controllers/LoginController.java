package controllers;

import classes.Sesion;
import com.example.proyectocesurapp.App;
import domain.alumn.AlumnDAOImp;
import domain.DBConnection;
import domain.teacher.TeacherDAOImp;
import exception.*;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @javafx.fxml.FXML
    private TextField txtEmail; //Field del DNI
    @javafx.fxml.FXML
    private PasswordField txtPassword;
    @javafx.fxml.FXML
    private Button botonInicio;

    @javafx.fxml.FXML
    public void iniciarSesion(ActionEvent actionEvent) {
        String usuarioDni = txtEmail.getText();
        String usuarioContrasenha = txtPassword.getText();
        TeacherDAOImp dao=new TeacherDAOImp(DBConnection.getConnection());
        AlumnDAOImp daoA=new AlumnDAOImp(DBConnection.getConnection());
        try {
            Sesion.setTeacher(dao.login(usuarioDni,usuarioContrasenha));

            System.out.println(Sesion.getTeacher());
            Alert alerta=new Alert(Alert.AlertType.CONFIRMATION);
            alerta.setTitle("Login");
            alerta.setHeaderText("Bienvenido "+Sesion.getTeacher().getName());
            alerta.setContentText("Inicio Correcto");
            alerta.showAndWait();
            App.loadFXML("teacherView-controller.fxml");

        } catch (NonExistentUser e) {
            try {
                Sesion.setAlumn(daoA.login(usuarioDni,usuarioContrasenha));
                App.loadFXML("alumnView-controller.fxml");
            } catch (IncorrectPassword ex) {
                throw new RuntimeException(ex);
            } catch (NonExistentUser ex) {
                Alert alerta=new Alert(Alert.AlertType.ERROR);
                alerta.setTitle("Error");
                alerta.setHeaderText("Usuario Inexistente.");
                alerta.showAndWait();
                throw new RuntimeException(ex);
            }
        } catch (IncorrectPassword e) {
            Alert alerta=new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Error");
            alerta.setHeaderText("Contraseña incorrecta.");
            alerta.showAndWait();
            throw new RuntimeException(e);
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
