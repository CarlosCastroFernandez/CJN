package controllers;

import classes.Teacher;
import com.example.proyectocesurapp.App;
import domain.DBConnection;
import domain.teacher.TeacherDAOImp;
import exception.LastNameWithNumber;
import exception.InvalidDNI;
import exception.NameWithNumber;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

public class TeacherRegisterController implements Initializable {
    @javafx.fxml.FXML
    private TextField txtEmail;
    @javafx.fxml.FXML
    private PasswordField passField;
    @javafx.fxml.FXML
    private TextField txtNombre;
    @javafx.fxml.FXML
    private TextField txtApellido1;
    @javafx.fxml.FXML
    private TextField txtApellido2;
    @javafx.fxml.FXML
    private TextField txtTelefono;
    @javafx.fxml.FXML
    private Button botonRegistro;
    @javafx.fxml.FXML
    private Button botonCancelar;
    @javafx.fxml.FXML
    private TextField txtDNI;
    @javafx.fxml.FXML
    private ImageView imagenRegistro;
    @javafx.fxml.FXML
    private Pane pane;

    @javafx.fxml.FXML
    public void registrarse(ActionEvent actionEvent) {
        String dni=txtDNI.getText();
        String nombre=txtNombre.getText();
        String apellido1=txtApellido1.getText();
        String apellido2=txtApellido2.getText();
        String email=txtEmail.getText();
        Integer telefono=Integer.valueOf(txtTelefono.getText());
        String contraseña=passField.getText();
        try {
            Teacher profe=new Teacher(null,nombre,apellido1,apellido2,contraseña,email,dni,telefono);
            TeacherDAOImp dao=new TeacherDAOImp(DBConnection.getConnection());
            profe=dao.injection(profe);
        } catch (NameWithNumber e) {
            Alert alerta=new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Error");
            alerta.setHeaderText("Nombre con número.");
            alerta.showAndWait();
            throw new RuntimeException(e);
        } catch (LastNameWithNumber e) {
            Alert alerta=new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Error");
            alerta.setHeaderText("Apellido con número.");
            alerta.showAndWait();
            throw new RuntimeException(e);
        } catch (InvalidDNI e) {
            Alert alerta=new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Error");
            alerta.setHeaderText("DNI inválido.");
            alerta.showAndWait();
            throw new RuntimeException(e);
        }
        App.loadFXML("login-controller.fxml");

    }

    @javafx.fxml.FXML
    public void cancelar(ActionEvent actionEvent) {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
