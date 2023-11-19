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
        File carpeta = new File("./imagenes de " + Sesion.getAlumn().getDni());
        if (carpeta.exists()) {
            File[] hijo = carpeta.listFiles();
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
        comboPracticeType.setDisable(true);
        spHoras.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 8, 0, 1));
        cDate.setCellValueFactory((fila) -> {
            String fecha = fila.getValue().getDate();
            return new SimpleStringProperty(fecha);
        });
        cTPractice.setCellValueFactory((fila) -> {
            String tipoPractica = String.valueOf(fila.getValue().getPracticeType());
            return new SimpleStringProperty(tipoPractica);
        });
        cTHours.setCellValueFactory((fila) -> {
            String totalHoras = String.valueOf(fila.getValue().getTotalHours());
            return new SimpleStringProperty(totalHoras);
        });
        cObservations.setCellValueFactory((fila) -> {
            String observaciones = fila.getValue().getObservations();
            return new SimpleStringProperty(observaciones);
        });
        cActivityRealised.setCellValueFactory((fila) -> {
            String tareaRealizada = fila.getValue().getTaskName();
            return new SimpleStringProperty(tareaRealizada);
        });
        tvUser.setOnMousePressed(mouseEvent -> {
            if (mouseEvent.isSecondaryButtonDown() && actividadDiaria != null) {
                contextMenu = new ContextMenu();
                menuItem1 = new MenuItem("Editar");
                contextMenu.getItems().add(menuItem1);
                contextMenu.getItems().add(new SeparatorMenuItem());
                menuItem2 = new MenuItem("Borrar");
                contextMenu.getItems().add(menuItem2);
                tvUser.setContextMenu(contextMenu);
                contextMenu.show(tvUser, mouseEvent.getScreenX(), mouseEvent.getScreenY());
                menuItem1.setOnAction(actionEvent -> HelloApplication.loadFXML("editar-actividadDiaria-view.fxml"));

                menuItem2.setOnAction(actionEvent -> {
                    try{
                        Alert alerta=new Alert(Alert.AlertType.INFORMATION);
                        alerta.setTitle("Eliminar");
                        alerta.setHeaderText("¿Seguro que deseas BORRAR esta actividad?");
                        ButtonType tipo= alerta.showAndWait().get();
                        if(tipo.getButtonData()== ButtonBar.ButtonData.OK_DONE){
                            ActividadDiaria actividadDiariaDelete = Sesion.getAlumn().getActivity().remove(Sesion.getAlumn().getActivity().indexOf(actividadDiaria));
                            observableActividad.setAll(Sesion.getAlumn().getActivity());
                            tvUser.setItems(observableActividad);
                            ActividaDiariaDAOImp delete=new ActividaDiariaDAOImp(DBConnection.getConnection());
                            delete.deleteActividad(actividadDiariaDelete);
                        }
                    }catch(Exception e){

                    }


                });
            }

        });
        //Consulta a bbdd: "Todas las actividades diarias que tiene el alumno logeado".
        Integer idAlumno = Sesion.getAlumn().getId();
        ActividaDiariaDAOImp dao = new ActividaDiariaDAOImp(DBConnection.getConnection());
        Sesion.getAlumn().getActivity().addAll(dao.loadall(idAlumno));
        if (Sesion.getAlumn().getActivity().isEmpty()) {
            Sesion.getAlumn().getActivity().addAll(dao.loadall(idAlumno));
            observableActividad = FXCollections.observableArrayList();
            observableActividad.addAll(Sesion.getAlumn().getActivity());
            tvUser.setItems(observableActividad);
        } else {
            tvUser.getItems().clear();
            Sesion.getAlumn().getActivity().clear();
            Sesion.getAlumn().setActivity(dao.loadall(idAlumno));
            observableActividad = FXCollections.observableArrayList();
            observableActividad.addAll(Sesion.getAlumn().getActivity());
            tvUser.setItems(observableActividad);
        }
        tvUser.getSelectionModel().selectedItemProperty().addListener((observable, t0, t1) -> {
            actividadDiaria = t1;
            Sesion.setActivity(actividadDiaria);
        });
        /* Los datos de esta consulta a Array de Sesion, de este array al observable y del observable a la tabla. */
    }

    @javafx.fxml.FXML
    public void añadirTarea(ActionEvent actionEvent) {
        System.out.println("Entra");
        ActividadDiaria dayActivity1 = new ActividadDiaria();
        if (!comboPracticeType.getSelectionModel().getSelectedItem().equals(null)
                && !fieldActivity.getText().isEmpty() && !taObservations.getText().isEmpty()) {
            String fecha = String.valueOf(dpDate.getValue());
            if (fecha.equals("null")) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Fecha de la actividad nula");
                alert.setContentText("Asegurese de que los datos de la fecha de actividad\n sean correctos.");
                alert.showAndWait();
            }

            dayActivity1.setDate(String.valueOf(dpDate.getValue()));
            dayActivity1.setPracticeType(comboPracticeType.getSelectionModel().getSelectedItem());
            dayActivity1.setTotaHours(spHoras.getValue());
            dayActivity1.setTaskName(fieldActivity.getText().strip());
            dayActivity1.setObservations(taObservations.getText().strip());

            ActividaDiariaDAOImp dao = new ActividaDiariaDAOImp(DBConnection.getConnection());
            dayActivity1=dao.injection(dayActivity1);


            Sesion.getAlumn().getActivity().add(dayActivity1);
            observableActividad.add(dayActivity1);
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
        if (ruta != null) {
            File carpeta = new File("imágenes de " + Sesion.getAlumn().getDni());
            if (!carpeta.exists() && (ruta.getName().endsWith("jpg") || ruta.getName().endsWith("png") || ruta.getName().endsWith("PNG") || ruta.getName().endsWith("JPG"))) {
                try {
                    carpeta.mkdir();
                    Path origen = Path.of(ruta.getAbsolutePath());
                    Path destino = Path.of(carpeta.getName());
                    Path destinoArchivo = destino.resolve(Sesion.getAlumn().getName() + " " + Sesion.getAlumn().getLastName() + ruta.getName().substring(ruta.getName().indexOf(".")));
                    Files.copy(origen, destinoArchivo, StandardCopyOption.REPLACE_EXISTING);
                    ivUser.setImage(new Image("file:" + destinoArchivo));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            } else {
                File[] hijos = carpeta.listFiles();
                hijos[0].delete();
                Path origen = Path.of(ruta.getAbsolutePath());
                Path destino = Path.of(carpeta.getName());
                Path destinoArchivo = destino.resolve(Sesion.getAlumn().getName() + " " + Sesion.getAlumn().getLastName() + ruta.getName().substring(ruta.getName().indexOf(".")));
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

