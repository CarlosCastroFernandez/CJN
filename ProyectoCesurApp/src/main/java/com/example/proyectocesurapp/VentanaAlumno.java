package com.example.proyectocesurapp;

import clase.ActividadDiaria;
import clase.Sesion;
import domain.ActividaDiariaDAOImp;
import domain.DBConnection;
import enums.TipoPractica;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import java.io.File;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ResourceBundle;

public class VentanaAlumno implements Initializable {
    @javafx.fxml.FXML
    private ImageView ivUser;
    @javafx.fxml.FXML
    private DatePicker dpDate;
    @javafx.fxml.FXML
    private TextArea taObservations;
    @javafx.fxml.FXML
    private Button btnAdd;
    @javafx.fxml.FXML
    private TableView<ActividadDiaria> tvUser;
    @javafx.fxml.FXML
    private TableColumn<ActividadDiaria, String> cDate;
    @javafx.fxml.FXML
    private TableColumn<ActividadDiaria, String> cTPractice;
    @javafx.fxml.FXML
    private TableColumn<ActividadDiaria, String> cTHours;
    @javafx.fxml.FXML
    private TableColumn<ActividadDiaria, String> cActivityRealised;
    @javafx.fxml.FXML
    private TableColumn<ActividadDiaria, String> cObservations;
    @javafx.fxml.FXML
    private MenuBar menu;
    @javafx.fxml.FXML
    private ComboBox<TipoPractica> comboPracticeType;
    @javafx.fxml.FXML
    private Spinner<Integer> spHoras;
    @javafx.fxml.FXML
    private TextField fieldActivity;
    private ObservableList<ActividadDiaria> observableActividad;
    private ActividadDiaria actividadDiaria;
    private ContextMenu contextMenu = new ContextMenu();
    private MenuItem menuItem1 = new MenuItem();
    private MenuItem menuItem2 = new MenuItem();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        File carpeta = new File("./imagenes de "+ Sesion.getAlumno().getDni());
        if (carpeta.exists()){
            File[]hijo = carpeta.listFiles();
            Image imagenAlumno = new Image("file:" + carpeta.getName() + "/" + hijo[0].getName());

            System.out.println(carpeta.getName() + "/" + hijo[0].getName());
            ivUser.setImage(imagenAlumno);
        } else {
            Image imagenPrincipal = new Image(VentanaAlumno.class.getClassLoader().getResource("com/example/proyectocesurapp/imagenes/usuario.png").toExternalForm());
            ivUser.setImage(imagenPrincipal);
        }

        ObservableList<TipoPractica> practiceType = FXCollections.observableArrayList();
        practiceType.addAll(TipoPractica.DUAL, TipoPractica.FCT);
        comboPracticeType.setItems(practiceType);
        comboPracticeType.getSelectionModel().selectFirst();
        spHoras.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0,8,0,1));
        cDate.setCellValueFactory((fila)->{
            String fecha=fila.getValue().getFecha();
            return new SimpleStringProperty(fecha);
        });
        cTPractice.setCellValueFactory( (fila) -> {
            String tipoPractica =String.valueOf(fila.getValue().getTipoPractica());
            return new SimpleStringProperty(tipoPractica);
        });
        cTHours.setCellValueFactory( (fila) -> {
            String totalHoras = String.valueOf(fila.getValue().getTotalHoras());
            return new SimpleStringProperty(totalHoras);
        });
        cObservations.setCellValueFactory( (fila) -> {
            String observaciones=fila.getValue().getObservaciones();
            return new SimpleStringProperty(observaciones);
        });
        cActivityRealised.setCellValueFactory( (fila) -> {
            String tareaRealizada = fila.getValue().getNombreTarea();
            return new SimpleStringProperty(tareaRealizada);
        });
        tvUser.setOnMousePressed(mouseEvent -> {
            if (mouseEvent.isSecondaryButtonDown() && actividadDiaria != null){
                contextMenu = new ContextMenu();
                menuItem1 = new MenuItem("Editar");
                contextMenu.getItems().add(menuItem1);
                contextMenu.getItems().add(new SeparatorMenuItem());
                menuItem2 = new MenuItem("Borrar");
                contextMenu.getItems().add(menuItem2);
                tvUser.setContextMenu(contextMenu);
                contextMenu.show(tvUser, mouseEvent.getScreenX(), mouseEvent.getScreenY());
                menuItem1.setOnAction(actionEvent -> {
                    System.out.println("Paso por aquí");
                    HelloApplication.loadFXML("editar-actividadDiaria-view.fxml");
                });
            }
        });

        //Consulta a bbdd: "Todas las actividades diarias que tiene el alumno logeado".
        Integer idAlumno = Sesion.getAlumno().getId();
        ActividaDiariaDAOImp dao=new ActividaDiariaDAOImp(DBConnection.getConnection());
        if(Sesion.getListaActividades().isEmpty()){
            Sesion.setListaActividades(dao.loadall(idAlumno));
            observableActividad = FXCollections.observableArrayList();
            observableActividad.addAll(Sesion.getListaActividades());
            tvUser.setItems(observableActividad);
        }else{
            tvUser.getItems().clear();
            Sesion.setListaActividades(dao.loadall(idAlumno));
            observableActividad = FXCollections.observableArrayList();
            observableActividad.addAll(Sesion.getListaActividades());
            tvUser.setItems(observableActividad);
        }
        tvUser.getSelectionModel().selectedItemProperty().addListener((observable, t0, t1) -> {
            actividadDiaria = t1;
            Sesion.setActividadDiaria(actividadDiaria);
        });


        /* Los datos de esta consulta a Array de Sesion, de este array al observable y del observable a la tabla. */
    }


    @javafx.fxml.FXML
    public void añadirTarea(ActionEvent actionEvent) {
            System.out.println("Entra");
            ActividadDiaria dayActivity = new ActividadDiaria();
            if (!comboPracticeType.getSelectionModel().getSelectedItem().equals(null)
            && !fieldActivity.getText().isEmpty() && !taObservations.getText().isEmpty()) {
                String fecha = String.valueOf(dpDate.getValue());
                if (fecha.equals("null")){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Fecha de la actividad nula");
                    alert.setContentText("Asegurese de que los datos de la fecha de actividad\n sean correctos.");
                    alert.showAndWait();
                }

                dayActivity.setFecha(String.valueOf(dpDate.getValue()));
                dayActivity.setTipoPractica(comboPracticeType.getSelectionModel().getSelectedItem());
                dayActivity.setTotalHoras(spHoras.getValue());
                dayActivity.setNombreTarea(fieldActivity.getText().strip());
                dayActivity.setObservaciones(taObservations.getText().strip());

                ActividaDiariaDAOImp dao = new ActividaDiariaDAOImp(DBConnection.getConnection());
                Sesion.setActividadDiaria(dao.insercion(dayActivity));
                System.out.println(Sesion.getActividadDiaria().toString());
                Sesion.getListaActividades().add(Sesion.getActividadDiaria());
                observableActividad.add(Sesion.getActividadDiaria());
                System.out.println("Sale.");

            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setWidth(300);
                alert.setTitle("Error");
                alert.setHeaderText("Campos vacíos");
                alert.setContentText("Asegúrese de que todos los campos hayan sido rellenados");
                alert.showAndWait();
            }
    }

    @javafx.fxml.FXML
    public void clickImage(Event event) {
        FileChooser open = new FileChooser();
        File ruta = open.showOpenDialog(null);
        System.out.println(ruta.getName().substring(ruta.getName().indexOf(".")));
        if (ruta != null){
            File carpeta = new File("imágenes de " + Sesion.getAlumno().getDni());
            if (!carpeta.exists() && (ruta.getName().endsWith("jpg") || ruta.getName().endsWith("png") || ruta.getName().endsWith("PNG") || ruta.getName().endsWith("JPG"))){
                try{
                    carpeta.mkdir();
                    Path origen = Path.of(ruta.getAbsolutePath());
                    Path destino = Path.of(carpeta.getName());
                    Path destinoArchivo = destino.resolve(Sesion.getAlumno().getNombre() + " " + Sesion.getAlumno().getApellido1() + ruta.getName().substring(ruta.getName().indexOf(".")));
                    Files.copy(origen, destinoArchivo, StandardCopyOption.REPLACE_EXISTING);
                    ivUser.setImage(new Image("file:" + destinoArchivo));
                } catch (IOException e){
                    throw new RuntimeException(e);
                }
            } else {
                File[]hijos = carpeta.listFiles();
                hijos[0].delete();
                Path origen = Path.of(ruta.getAbsolutePath());
                Path destino = Path.of(carpeta.getName());
                Path destinoArchivo =  destino.resolve(Sesion.getAlumno().getNombre() + " " + Sesion.getAlumno().getApellido1() + ruta.getName().substring(ruta.getName().indexOf(".")));
                try {
                   Files.copy(origen, destinoArchivo, StandardCopyOption.REPLACE_EXISTING);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                ivUser.setImage(new Image("file:" + destinoArchivo));
            }
        }
    }
}
