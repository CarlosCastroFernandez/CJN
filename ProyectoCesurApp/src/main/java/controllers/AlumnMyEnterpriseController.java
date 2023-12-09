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
 * Clase Controladora para ver la informacion de la empresa,
 * es decir su nombre de empresa.
 */
public class AlumnMyEnterpriseController implements Initializable {
    /**
     * Tipo de dato ImageView donde se le inserta una imaagen
     */
    @javafx.fxml.FXML
    private ImageView river;
    /**
     * Tipo de dato TextField donde irá la información de la empresa
     */
    @javafx.fxml.FXML
    private TextField txtNameEnterprise;

    /**
     * Metodo initalize donde se inicializarán los adatos a mostrar, en este caso
     * de la empresa
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        /*
        En esta seccion se comprueba si el alumno tiene empresa o no tiene
        si por casualidad el alumno no tuviera empresa en el TextField se mostraría
        la información de <<Sin Empresa>>, en caso contrario se mostraría el nombre de la empresa.
         */
        if(Sesion.getAlumn().getEnterprise()==null){
            txtNameEnterprise.setText("<<Sin Empresa>>");
            txtNameEnterprise.setEditable(false);
        }else{
            txtNameEnterprise.setText(Sesion.getAlumn().getEnterprise().getName());
            txtNameEnterprise.setEditable(false);
        }


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
