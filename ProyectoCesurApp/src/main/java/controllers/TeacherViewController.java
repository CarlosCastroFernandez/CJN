package controllers;

import classes.Alumn;
import classes.Enterprise;
import classes.Sesion;
import com.example.proyectocesurapp.App;
import domain.dailyActivity.DailyActivityDAOImp;
import domain.alumn.AlumnDAOImp;
import domain.DBConnection;
import domain.enterprise.EnterpriseDAOImp;
import enums.Grade;
import exception.LastNameWithNumber;
import exception.InvalidDNI;
import exception.NameWithNumber;
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
import javafx.util.StringConverter;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.ResourceBundle;
public class TeacherViewController implements Initializable {
    @javafx.fxml.FXML
    private TableView <Alumn>tabla;
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
    private TableColumn<Alumn,String> cNombre;
    @javafx.fxml.FXML
    private TableColumn <Alumn,String>cApellidos;
    @javafx.fxml.FXML
    private TableColumn <Alumn,String>cEmail;
    @javafx.fxml.FXML
    private TableColumn <Alumn,String>cTelefono;
    @javafx.fxml.FXML
    private TableColumn<Alumn,String> cFecha;
    @javafx.fxml.FXML
    private TableColumn<Alumn,String> cCurso;
    @javafx.fxml.FXML
    private TableColumn <Alumn,String>cProfesor;
    @javafx.fxml.FXML
    private TableColumn <Alumn,String>cEmpresa;
    @javafx.fxml.FXML
    private TableColumn<Alumn,String> cHorasDUAL;
    @javafx.fxml.FXML
    private TableColumn <Alumn,String>cHorasFCT;
    private Alumn alumn;
    private ObservableList<Alumn>obs;
    @javafx.fxml.FXML
    private TextField textNombre;
    @javafx.fxml.FXML
    private TextField textApellidos;
    @javafx.fxml.FXML
    private TextField textEmail;
    @javafx.fxml.FXML
    private TextField textTelefono;
    @javafx.fxml.FXML
    private ComboBox<Grade>comboCurso;
    @javafx.fxml.FXML
    private Spinner spinnerDUAL;
    @javafx.fxml.FXML
    private Spinner spinnerFCT;
    @javafx.fxml.FXML
    private ComboBox <Enterprise>comboNombreEmpresa;
    @javafx.fxml.FXML
    private DatePicker dateCalender;
    @javafx.fxml.FXML
    private RadioButton radioDUal;
    @javafx.fxml.FXML
    private RadioButton radioFCT;
    @javafx.fxml.FXML
    private ToggleGroup botones;
    @javafx.fxml.FXML
    private Label labelFCT;
    @javafx.fxml.FXML
    private Label labelDUAL;
    @javafx.fxml.FXML
    private TextField contraseñaAlumno;
    @javafx.fxml.FXML
    private TextField textDNI;
    private ContextMenu contextMenu=new ContextMenu();
    private MenuItem menuItem1=new MenuItem();
    private MenuItem menuItem2=new MenuItem();
    private ObservableList<Grade> obsGrades;
    @javafx.fxml.FXML
    private Button botonEmpresa;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        File carpeta=new File ("./imagenes de "+Sesion.getTeacher().getDni());
        if(carpeta.exists()){
            File[]hijo=carpeta.listFiles();
            Image imagenProfe=new Image("file:"+carpeta.getName()+"/"+hijo[0].getName());

            System.out.println(carpeta.getName()+"/"+hijo[0].getName());
            imagen.setImage(imagenProfe);
        }else{
            Image imagenPrincipal = new Image(TeacherViewController.class.getClassLoader().getResource("com/example/proyectocesurapp/imagenes/usuario.png").toExternalForm());
            imagen.setImage(imagenPrincipal);
        }

        radioDUal.setSelected(true);
        spinnerFCT.setVisible(false);
        labelFCT.setVisible(false);
        radioDUal.setOnAction(actionEvent -> {
            spinnerFCT.setVisible(false);
            labelFCT.setVisible(false);
            spinnerDUAL.setVisible(true);
            labelDUAL.setVisible(true);
        });
        radioFCT.setOnAction(actionEvent -> {
            spinnerDUAL.setVisible(false);
            labelDUAL.setVisible(false);
            spinnerFCT.setVisible(true);
            labelFCT.setVisible(true);
        });
        obsGrades =FXCollections.observableArrayList();
        obsGrades.addAll(Grade.ASIR1, Grade.ASIR2, Grade.DAM1, Grade.DAM2, Grade.DAW1, Grade.DAW2);
        comboCurso.setConverter(new StringConverter<Grade>() {
            @Override
            public String toString(Grade grade) {
                if(grade !=null){
                    return String.valueOf(grade);
                }else{
                    return null;
                }

            }

            @Override
            public Grade fromString(String s) {
                return null;
            }
        });
        comboCurso.setItems(obsGrades);
        comboNombreEmpresa.setConverter(new StringConverter<Enterprise>() {
            @Override
            public String toString(Enterprise enterprise) {
                if (enterprise !=null){
                    return enterprise.getName();
                }else{
                    return "<<Sin empresa>>";
                }

            }

            @Override
            public Enterprise fromString(String s) {
                return null;
            }
        });
        comboNombreEmpresa.getItems().addAll((new EnterpriseDAOImp(DBConnection.getConnection()).loadAll()));
        comboNombreEmpresa.getItems().add(null);
        comboNombreEmpresa.getSelectionModel().selectFirst();

        cNombre.setCellValueFactory((fila)->{
            String nombre=fila.getValue().getName();
            return new SimpleStringProperty(nombre);
        });
        cApellidos.setCellValueFactory((fila)->{
            String nombre=fila.getValue().getLastName()+" "+fila.getValue().getLastName2();
            return new SimpleStringProperty(nombre);
        });
        cEmail.setCellValueFactory((fila)->{
            String nombre=fila.getValue().getEmail();
            return new SimpleStringProperty(nombre);
        });
        cTelefono.setCellValueFactory((fila)->{
            String nombre=String.valueOf(fila.getValue().getPhone()) ;
            return new SimpleStringProperty(nombre);
        });
        cFecha.setCellValueFactory((fila)->{
            String nombre=fila.getValue().getBirthday();
            return new SimpleStringProperty(nombre);
        });
        cCurso.setCellValueFactory((fila)->{
            String nombre=String.valueOf(fila.getValue().getGrade());
            return new SimpleStringProperty(nombre);
        });
        cProfesor.setCellValueFactory((fila)->{
            String nombre=fila.getValue().getTeacher().getName()+" "+fila.getValue().getTeacher().getLastName();
            return new SimpleStringProperty(nombre);
        });
        cHorasDUAL.setCellValueFactory((fila)->{
            String nombre=fila.getValue().getHoursDUAL();
            return new SimpleStringProperty(nombre);
        });
        cHorasFCT.setCellValueFactory((fila)->{
            String nombre=fila.getValue().getHoursFCT();
            return new SimpleStringProperty(nombre);
        });
        cEmpresa.setCellValueFactory((fila)->{
            String nombre="";
            if(fila.getValue().getEnterprise()==null){
               nombre="<<vacio>>";
            }else{
                nombre=fila.getValue().getEnterprise().getName();
            }

            return new SimpleStringProperty(nombre);
        });
        tabla.setOnMousePressed(mouseEvent -> {
            if(mouseEvent.isSecondaryButtonDown()&& alumn !=null){
                 contextMenu=new ContextMenu();
                 menuItem1=new MenuItem("Editar");
                contextMenu.getItems().add(menuItem1);
                contextMenu.getItems().add(new SeparatorMenuItem());
                 menuItem2=new MenuItem("Borrar");
                contextMenu.getItems().add(menuItem2);
                tabla.setContextMenu(contextMenu);
                contextMenu.show(tabla,mouseEvent.getScreenX(),mouseEvent.getScreenY());
                menuItem1.setOnAction(actionEvent -> {
                    System.out.println("Paso por aqui");
                    App.loadFXML("editAlumn-controller.fxml");
                });
                menuItem2.setOnAction(actionEvent -> {
                    try{
                        Alert alerta=new Alert(Alert.AlertType.INFORMATION);
                        alerta.setTitle("Eliminar");
                        alerta.setHeaderText("¿Seguro que deseas BORRAR al alumno?");
                        alerta.setContentText("Si ELIMINAS a este alumno boraras TODAS sus ACTIVIDADES");
                        ButtonType tipo= alerta.showAndWait().get();
                        if(tipo.getButtonData()== ButtonBar.ButtonData.OK_DONE){
                            System.out.println("Paso por aqui");
                            Alumn alumnLista =Sesion.getTeacher().getAlumn().get(Sesion.getTeacher().getAlumn().indexOf(alumn));

                            DailyActivityDAOImp actDIB=new DailyActivityDAOImp(DBConnection.getConnection());

                            for(int i = 0; i< alumnLista.getActivity().size(); i++){
                                actDIB.delete(alumnLista.getActivity().get(i));
                            }
                            (new AlumnDAOImp(DBConnection.getConnection())).delete(alumnLista);
                            AlumnDAOImp conexion=new AlumnDAOImp(DBConnection.getConnection());
                            obs.setAll(conexion.loadAll(Sesion.getTeacher().getId()));
                        }
                    }catch(Exception e){

                    }




                });

            }
        });
        obs= FXCollections.observableArrayList();
        AlumnDAOImp conexion=new AlumnDAOImp(DBConnection.getConnection());
        Sesion.getTeacher().getAlumn().clear();
        Sesion.getTeacher().getAlumn().addAll(conexion.loadAll(Sesion.getTeacher().getId()));
        System.out.println(Sesion.getTeacher().getAlumn());
        obs.addAll(Sesion.getTeacher().getAlumn());
        tabla.setItems(obs);
        tabla.getSelectionModel().selectedItemProperty().addListener((observable,t0,t1) -> {
            alumn =t1;
            Sesion.setAlumn(alumn);
        });


        spinnerDUAL.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0,270,0,1));
        spinnerFCT.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0,270,0,1));
    }

    @Deprecated
    public void editar(ActionEvent actionEvent) {
        if(alumn ==null){
            Alert alerta=new Alert(Alert.AlertType.INFORMATION);
            alerta.setTitle("Seleccion Tabla");
            alerta.setHeaderText("Porfavor Selecciona un alumno en la tabla");
            alerta.showAndWait();
        }else{
            Sesion.setAlumn(alumn);
            App.loadFXML("ventana-editar.fxml");
        }

    }

    @Deprecated
    public void borrar(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void nuevoALumno(ActionEvent actionEvent) {
        try {
            Alumn alumn = new Alumn();
            if (!textApellidos.getText().isEmpty() && !textNombre.getText().isEmpty() && !textEmail.getText().isEmpty()
                    && !textTelefono.getText().isEmpty() && !comboCurso.getSelectionModel().getSelectedItem().equals(null)
                    && !contraseñaAlumno.getText().isEmpty() && !textDNI.getText().isEmpty()) {


                textApellidos.getText().strip();

                textNombre.getText().strip();
                alumn.setName(textNombre.getText());
                textDNI.getText().strip();
                textEmail.getText().strip();
                alumn.setEmail(textEmail.getText());
                textTelefono.getText().strip();
                alumn.setPhone(Integer.valueOf(textTelefono.getText()));
                String fecha = String.valueOf(dateCalender.getValue());
                System.out.println(fecha);
                if(fecha.equals("null")){
                    Alert alerta = new Alert(Alert.AlertType.ERROR);
                    alerta.setTitle("Error");
                    alerta.setHeaderText("Fecha De Nacimiento No Seleccionada");
                    alerta.setContentText("Asegurese de que los datos de la fecha de nacimiento\n sean correctos.");
                    alerta.showAndWait();
                }
                alumn.setBirthday(fecha);
                alumn.setPassword(contraseñaAlumno.getText());
                alumn.setObservations("");
                alumn.setTeacherID(Sesion.getTeacher().getId());
                alumn.setTeacher(Sesion.getTeacher());

                alumn.setDni(textDNI.getText());

                String[] apellidos = textApellidos.getText().split(" ");
                if (apellidos.length != 2) {
                    Alert alerta = new Alert(Alert.AlertType.ERROR);
                    alerta.setTitle("Error");
                    alerta.setHeaderText("Apellidos Mal Transcritos");
                    alerta.setContentText("Asegurese de que los apellidos sean dos y \nseparados por un espacio.");
                    alerta.showAndWait();

                } else {

                    alumn.setLastName((String) apellidos[0]);
                    alumn.setLastName2(apellidos[1]);

                }
                alumn.setGrade((Grade) comboCurso.getSelectionModel().getSelectedItem());

                if (radioDUal.isSelected()) {
                    alumn.setHoursDUAL(spinnerDUAL.getValue() + "/270");
                } else {
                    alumn.setHoursFCT(spinnerFCT.getValue() + "/270");
                }
                if (comboNombreEmpresa.getValue() == null) {
                    alumn.setEnterprise(null);
                    alumn.setEnterpriseID(0);
                } else {
                    Enterprise enterprise = (Enterprise) comboNombreEmpresa.getSelectionModel().getSelectedItem();
                    alumn.setEnterprise(enterprise);
                    alumn.setEnterpriseID(enterprise.getId());
                    enterprise.setAlumn(new ArrayList<>());
                    enterprise.getAlumn().add(alumn);
                    System.out.println(enterprise);
                }
                System.out.println(alumn);

                AlumnDAOImp dao = new AlumnDAOImp(DBConnection.getConnection());
               Alumn alumnValido = dao.injection(alumn);
               Sesion.getTeacher().getAlumn().add(alumnValido);
                obs.add(alumnValido);

            }else{
                Alert alerta = new Alert(Alert.AlertType.ERROR);
                alerta.setWidth(300);
                alerta.setTitle("Error");
                alerta.setHeaderText("Campos Incorrectos");
                alerta.setContentText("Asegurese de que los datos de todos los campos son correctos.");
                alerta.showAndWait();
            }

        } catch (NameWithNumber e) {
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Error");
            alerta.setHeaderText("Nombre con Numeros");
            alerta.setContentText("El nombre del alumno no puede contener numeros.");
            alerta.showAndWait();
            throw new RuntimeException(e);
        } catch (InvalidDNI e) {
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Error");
            alerta.setHeaderText("DNI Invalido");
            alerta.setContentText("El DNI es Imposible que pueda existir.");
            alerta.showAndWait();
            throw new RuntimeException(e);
        } catch (LastNameWithNumber e) {
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Error");
            alerta.setHeaderText("Apellido Con Numeros");
            alerta.setContentText("El Apellido del alumno no puede contener numeros.");
            alerta.showAndWait();
            throw new RuntimeException(e);
        }
    }

    @javafx.fxml.FXML
    public void logout(ActionEvent actionEvent) {
        Sesion.setTeacher(null);
        App.loadFXML("login-controller.fxml");
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
        try{
            FileChooser open=new FileChooser();
            File ruta= open.showOpenDialog(null);
            System.out.println(ruta.getName().substring(ruta.getName().indexOf(".")));
            if(ruta!=null){
                File carpeta=new File("imagenes de "+Sesion.getTeacher().getDni());
                if(!carpeta.exists()&&(ruta.getName().endsWith("jpg")||ruta.getName().endsWith("png")||ruta.getName().endsWith("PNG")||ruta.getName().endsWith("JPG"))){
                    try {
                        carpeta.mkdir();
                        Path origen= Path.of(ruta.getAbsolutePath());
                        Path destino= Path.of(carpeta.getName());
                        Path destinoArchivo=destino.resolve(Sesion.getTeacher().getName()+" "+Sesion.getTeacher().getLastName()+ruta.getName().substring(ruta.getName().indexOf(".")));
                        Files.copy(origen,destinoArchivo, StandardCopyOption.REPLACE_EXISTING);
                        imagen.setImage(new Image("file:"+destinoArchivo));

                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }else{
                    File[]hijos=carpeta.listFiles();
                    hijos[0].delete();
                    Path origen= Path.of(ruta.getAbsolutePath());
                    Path destino= Path.of(carpeta.getName());
                    Path destinoArchivo=destino.resolve(Sesion.getTeacher().getName()+" "+Sesion.getTeacher().getLastName()+ruta.getName().substring(ruta.getName().indexOf(".")));
                    try {
                        Files.copy(origen,destinoArchivo, StandardCopyOption.REPLACE_EXISTING);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    imagen.setImage(new Image("file:"+destinoArchivo));
                }
            }
        }catch(Exception e){

        }

    }

    @javafx.fxml.FXML
    public void goEmpresa(ActionEvent actionEvent) {
        App.loadFXML("enterpriseView-controller.fxml");
    }
}
