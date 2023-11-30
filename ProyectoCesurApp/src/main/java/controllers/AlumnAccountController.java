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
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class AlumnAccountController implements Initializable {
    @javafx.fxml.FXML
    private TextField txtName;
    @javafx.fxml.FXML
    private TextField txtSurname1;
    @javafx.fxml.FXML
    private TextField txtSurname2;
    @javafx.fxml.FXML
    private TextField txtEmail;
    @javafx.fxml.FXML
    private TextField txtDNI;
    @javafx.fxml.FXML
    private TextField txtPhone;
    @javafx.fxml.FXML
    private PasswordField txtPassword;
    @javafx.fxml.FXML
    private Button botonKeep;
    @javafx.fxml.FXML
    private Button botonCancel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        txtName.setText(Sesion.getAlumn().getName());
        txtDNI.setText(Sesion.getAlumn().getDni());
        txtEmail.setText(Sesion.getAlumn().getEmail());
        txtSurname1.setText(Sesion.getAlumn().getLastName());
        txtSurname2.setText(Sesion.getAlumn().getLastName2());
        txtPhone.setText(""+ Sesion.getAlumn().getPhone());
        txtPassword.setText(Sesion.getAlumn().getPassword());
    }

    @javafx.fxml.FXML
    public void save(ActionEvent actionEvent) {
        Alumn nuevo=new Alumn();
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
        (new AlumnDAOImp(DBConnection.getConnection())).updateAccount(nuevo);

            Sesion.setAlumn(new AlumnDAOImp(DBConnection.getConnection()).load(Sesion.getAlumn().getId()));
            Alert alerta=new Alert(Alert.AlertType.INFORMATION);
            alerta.setHeaderText("Modificado Con Exito");
            alerta.showAndWait();
            App.loadFXML("alumnView-controller.fxml");

    }

    @javafx.fxml.FXML
    public void cancel(ActionEvent actionEvent) {
        App.loadFXML("alumnView-controller.fxml");
    }
}
