package com.example.proyectocesurapp;

import clase.Alumno;
import clase.Sesion;
import domain.AlumnoDAOImp;
import domain.DBConnection;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
public class VentanaProfesor implements Initializable {
    @javafx.fxml.FXML
    private TableView <Alumno>tabla;
    @javafx.fxml.FXML
    private Button btnEditar;
    @javafx.fxml.FXML
    private Button btnBorrar;
    @javafx.fxml.FXML
    private Button btnNuevoAlumno;
    @javafx.fxml.FXML
    private Menu menu;
    @javafx.fxml.FXML
    private MenuItem menuLogout;
    @javafx.fxml.FXML
    private MenuItem menuCuenta;
    @javafx.fxml.FXML
    private MenuItem menuSalir;
    @javafx.fxml.FXML
    private ImageView imagen;
    @javafx.fxml.FXML
    private MenuBar menuBar;
    @javafx.fxml.FXML
    private TableColumn<Alumno,String> cNombre;
    @javafx.fxml.FXML
    private TableColumn <Alumno,String>cApellidos;
    @javafx.fxml.FXML
    private TableColumn <Alumno,String>cEmail;
    @javafx.fxml.FXML
    private TableColumn <Alumno,String>cTelefono;
    @javafx.fxml.FXML
    private TableColumn<Alumno,String> cFecha;
    @javafx.fxml.FXML
    private TableColumn<Alumno,String> cCurso;
    @javafx.fxml.FXML
    private TableColumn <Alumno,String>cProfesor;
    @javafx.fxml.FXML
    private TableColumn <Alumno,String>cEmpresa;
    @javafx.fxml.FXML
    private TableColumn<Alumno,String> cHorasDUAL;
    @javafx.fxml.FXML
    private TableColumn <Alumno,String>cHorasFCT;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cNombre.setCellValueFactory((fila)->{
            String nombre=fila.getValue().getNombre();
            return new SimpleStringProperty(nombre);
        });
        cApellidos.setCellValueFactory((fila)->{
            String nombre=fila.getValue().getApellido1()+" "+fila.getValue().getApellido2();
            return new SimpleStringProperty(nombre);
        });
        cEmail.setCellValueFactory((fila)->{
            String nombre=fila.getValue().getCorreo();
            return new SimpleStringProperty(nombre);
        });
        cTelefono.setCellValueFactory((fila)->{
            String nombre=String.valueOf(fila.getValue().getTelefono()) ;
            return new SimpleStringProperty(nombre);
        });
        cFecha.setCellValueFactory((fila)->{
            String nombre=fila.getValue().getFechaNacimiento();
            return new SimpleStringProperty(nombre);
        });
        cCurso.setCellValueFactory((fila)->{
            String nombre=String.valueOf(fila.getValue().getCurso());
            return new SimpleStringProperty(nombre);
        });
        cProfesor.setCellValueFactory((fila)->{
            String nombre=fila.getValue().getProfesor().getNombre()+" "+fila.getValue().getProfesor().getApellido1();
            return new SimpleStringProperty(nombre);
        });

        cHorasDUAL.setCellValueFactory((fila)->{
            String nombre=fila.getValue().getHorasDUAL();
            return new SimpleStringProperty(nombre);
        });
        cHorasFCT.setCellValueFactory((fila)->{
            String nombre=fila.getValue().getHorasFCT();
            return new SimpleStringProperty(nombre);
        });
        AlumnoDAOImp conexion=new AlumnoDAOImp(DBConnection.getConnection());
        ArrayList<Alumno>alumnos=conexion.loadAll(Sesion.getProfesor().getId());
        for(int i=0;i<alumnos.size();i++){
            Sesion.getProfesor().getAlumnos().add(alumnos.get(i));
            tabla.getItems().add(alumnos.get(i));
        }



    }

    @javafx.fxml.FXML
    public void editar(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void borrar(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void nuevoALumno(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void logout(ActionEvent actionEvent) {
        HelloApplication.loadFXML("login.fxml");
    }

    @javafx.fxml.FXML
    public void cuenta(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void salir(ActionEvent actionEvent) {
        System.exit(0);
    }

    @javafx.fxml.FXML
    public void clickImagen(Event event) {
    }
}
