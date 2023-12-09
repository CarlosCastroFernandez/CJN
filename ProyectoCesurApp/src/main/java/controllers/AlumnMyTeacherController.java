package controllers;

import classes.Sesion;
import com.example.proyectocesurapp.App;
import javafx.event.Event;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;
/**
 * Clase Controladora para ver la informacion del profesor,
 * es decir su nombre y su apellido.
 */

public class AlumnMyTeacherController implements Initializable {
    /**
     * Tipo de dato TextField donde irá la información del nombre del profesor
     */
    @javafx.fxml.FXML
    private TextField textNameTeacher;
    /**
     * Tipo de dato TextField donde irá la información de los apellidos del profesor
     */
    @javafx.fxml.FXML
    private TextField textSurNameTeacher;
    /**
     * Tipo de dato ImageView donde se le inserta una imaagen
     */
    @javafx.fxml.FXML
    private ImageView river;
    /**
     * Metodo initalize donde se inicializarán los adatos a mostrar, en este caso
     * datos del profesor
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        /* En esta parte se incializan los TextField para poner el nmbre y el apellid
        * del profesor asignado*/
        textNameTeacher.setText(Sesion.getAlumn().getTeacher().getName());
        textSurNameTeacher.setText(Sesion.getAlumn().getTeacher().getLastName()+" "+Sesion.getAlumn().getTeacher().getLastName2());
        /*En esta parte se prohibe la escritura por parte del alumno sobre los TextField*/
        textSurNameTeacher.setEditable(false);
        textNameTeacher.setEditable(false);

    }
    /**
     * Metodo en el que al pinchar en el ImageView te cambia de escena a la escena de
     * "alumnView-controller.fxml"
     * @param event evento OnClick que se le pasa por  parámetros al metodo.
     */
    @javafx.fxml.FXML
    public void river(Event event) {
        /* Cambio de escena*/
        App.loadFXML("alumnView-controller.fxml");
    }
}
