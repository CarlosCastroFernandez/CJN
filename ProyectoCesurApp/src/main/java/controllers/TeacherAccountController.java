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
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class TeacherAccountController implements Initializable {
    @javafx.fxml.FXML
    private TextField txtName;
    @javafx.fxml.FXML
    private TextField txtEmail;
    @javafx.fxml.FXML
    private TextField txtDNI;
    @javafx.fxml.FXML
    private TextField txtPhone;
    @javafx.fxml.FXML
    private Button botonKeep;
    @javafx.fxml.FXML
    private Button botonCancel;
    @javafx.fxml.FXML
    private PasswordField txtPassword;
    @javafx.fxml.FXML
    private TextField txtSurname1;
    @javafx.fxml.FXML
    private TextField txtSurname2;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        txtName.setText(Sesion.getTeacher().getName());
        txtDNI.setText(Sesion.getTeacher().getDni());
        txtEmail.setText(Sesion.getTeacher().getEmail());
        txtSurname1.setText(Sesion.getTeacher().getLastName());
        txtSurname2.setText(Sesion.getTeacher().getLastName2());
        txtPhone.setText(""+ Sesion.getTeacher().getPhone());
        txtPassword.setText(Sesion.getTeacher().getPassword());


    }

    @javafx.fxml.FXML
    public void save(ActionEvent actionEvent) {
        Teacher nuevo=new Teacher();
        try {
            nuevo.setDni(txtDNI.getText());
            nuevo.setEmail(txtEmail.getText());
            nuevo.setName(txtName.getText());
            nuevo.setLastName(txtSurname1.getText());
            nuevo.setLastName2(txtSurname2.getText());
            nuevo.setPassword(txtPassword.getText());
            nuevo.setPhone(Integer.valueOf(txtPhone.getText()));
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
            Alert alerta=new Alert(Alert.AlertType.INFORMATION);
            alerta.setHeaderText("Modificado Con Exito");
            alerta.showAndWait();
            Sesion.setCount((byte)1);
            App.loadFXML("teacherView-controller.fxml");
        } catch (NonExistentUser e) {
            throw new RuntimeException(e);
        }
    }

    @javafx.fxml.FXML
    public void cancel(ActionEvent actionEvent) {
        App.loadFXML("teacherView-controller.fxml");
    }


}
