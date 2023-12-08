package controllers;

import classes.Sesion;
import com.example.proyectocesurapp.App;
import javafx.event.Event;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class AlumnMyTeacherController implements Initializable {
    @javafx.fxml.FXML
    private TextField textNameTeacher;
    @javafx.fxml.FXML
    private TextField textSurNameTeacher;
    @javafx.fxml.FXML
    private ImageView river;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        textNameTeacher.setText(Sesion.getAlumn().getTeacher().getName());
        textSurNameTeacher.setText(Sesion.getAlumn().getTeacher().getLastName()+" "+Sesion.getAlumn().getTeacher().getLastName2());
        textSurNameTeacher.setEditable(false);
        textNameTeacher.setEditable(false);

    }

    @javafx.fxml.FXML
    public void river(Event event) {
        App.loadFXML("alumnView-controller.fxml");
    }
}
