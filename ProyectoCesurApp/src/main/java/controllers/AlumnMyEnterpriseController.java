package controllers;

import classes.Sesion;
import com.example.proyectocesurapp.App;
import javafx.event.Event;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class AlumnMyEnterpriseController implements Initializable {
    @javafx.fxml.FXML
    private ImageView river;
    @javafx.fxml.FXML
    private TextField txtNameEnterprise;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println(Sesion.getAlumn());
        if(Sesion.getAlumn().getEnterprise()==null){
            txtNameEnterprise.setText("<<Sin Empresa>>");
            txtNameEnterprise.setEditable(false);
        }else{
            txtNameEnterprise.setText(Sesion.getAlumn().getEnterprise().getName());
            txtNameEnterprise.setEditable(false);
        }


    }

    @javafx.fxml.FXML
    public void river(Event event) {
        App.loadFXML("alumnView-controller.fxml");
    }
}
