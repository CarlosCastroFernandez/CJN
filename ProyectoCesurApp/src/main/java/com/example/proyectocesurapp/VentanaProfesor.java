package com.example.proyectocesurapp;

import clase.Alumno;
import clase.Empresa;
import clase.Sesion;
import domain.ActividaDiariaDAOImp;
import domain.AlumnoDAOImp;
import domain.DBConnection;
import domain.EmpresaDAOImp;
import enums.Curso;
import exception.ApellidoConNumero;
import exception.DNIInvalido;
import exception.NombreConNumero;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.MapChangeListener;
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
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.ResourceBundle;
public class VentanaProfesor implements Initializable {
    @javafx.fxml.FXML
    private TableView <Alumno>tabla;
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
    private Alumno alumno;
    private ObservableList<Alumno>obs;
    @javafx.fxml.FXML
    private TextField textNombre;
    @javafx.fxml.FXML
    private TextField textApellidos;
    @javafx.fxml.FXML
    private TextField textEmail;
    @javafx.fxml.FXML
    private TextField textTelefono;
    @javafx.fxml.FXML
    private ComboBox<Curso>comboCurso;
    @javafx.fxml.FXML
    private Spinner spinnerDUAL;
    @javafx.fxml.FXML
    private Spinner spinnerFCT;
    @javafx.fxml.FXML
    private ComboBox <Empresa>comboNombreEmpresa;
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
    private ObservableList<Curso>obsCursos;
    @javafx.fxml.FXML
    private Button botonEmpresa;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        File carpeta=new File ("./imagenes de "+Sesion.getProfesor().getDni());
        if(carpeta.exists()){
            File[]hijo=carpeta.listFiles();
            Image imagenProfe=new Image("file:"+carpeta.getName()+"/"+hijo[0].getName());

            System.out.println(carpeta.getName()+"/"+hijo[0].getName());
            imagen.setImage(imagenProfe);
        }else{
            Image imagenPrincipal = new Image(VentanaProfesor.class.getClassLoader().getResource("com/example/proyectocesurapp/imagenes/usuario.png").toExternalForm());
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
        obsCursos=FXCollections.observableArrayList();
        obsCursos.addAll(Curso.ASIR1,Curso.ASIR2,Curso.DAM1,Curso.DAM2,Curso.DAW1,Curso.DAW2);
        comboCurso.setConverter(new StringConverter<Curso>() {
            @Override
            public String toString(Curso curso) {
                if(curso!=null){
                    return String.valueOf(curso);
                }else{
                    return null;
                }

            }

            @Override
            public Curso fromString(String s) {
                return null;
            }
        });
        comboCurso.setItems(obsCursos);
        comboNombreEmpresa.setConverter(new StringConverter<Empresa>() {
            @Override
            public String toString(Empresa empresa) {
                if (empresa!=null){
                    return empresa.getNombre();
                }else{
                    return "<<Sin empresa>>";
                }

            }

            @Override
            public Empresa fromString(String s) {
                return null;
            }
        });
        comboNombreEmpresa.getItems().addAll((new EmpresaDAOImp(DBConnection.getConnection()).loadAllEnterprise()));
        comboNombreEmpresa.getItems().add(null);
        comboNombreEmpresa.getSelectionModel().selectFirst();

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
        cEmpresa.setCellValueFactory((fila)->{
            String nombre="";
            if(fila.getValue().getEmpresa()==null){
               nombre="<<vacio>>";
            }else{
                nombre=fila.getValue().getEmpresa().getNombre();
            }

            return new SimpleStringProperty(nombre);
        });
        tabla.setOnMousePressed(mouseEvent -> {
            if(mouseEvent.isSecondaryButtonDown()&&alumno!=null){
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
                    HelloApplication.loadFXML("editar-alumno-view.fxml");
                });
                menuItem2.setOnAction(actionEvent -> {
                    System.out.println("Paso por aqui");
                    Alumno alumnoLista=Sesion.getProfesor().getAlumnos().get(Sesion.getProfesor().getAlumnos().indexOf(alumno));

                    ActividaDiariaDAOImp actDIB=new ActividaDiariaDAOImp(DBConnection.getConnection());

                    for(int i=0;i<alumnoLista.getActividadDiaria().size();i++){
                        actDIB.deleteActividad(alumnoLista.getActividadDiaria().get(i));
                    }
                    (new AlumnoDAOImp(DBConnection.getConnection())).delete(alumnoLista);
                    AlumnoDAOImp conexion=new AlumnoDAOImp(DBConnection.getConnection());
                    obs.setAll(conexion.loadAll(Sesion.getProfesor().getId()));


                });

            }
        });
        obs= FXCollections.observableArrayList();
        AlumnoDAOImp conexion=new AlumnoDAOImp(DBConnection.getConnection());
        Sesion.getProfesor().getAlumnos().addAll(conexion.loadAll(Sesion.getProfesor().getId()));
        System.out.println(Sesion.getProfesor().getAlumnos());
        obs.addAll(Sesion.getProfesor().getAlumnos());
        tabla.setItems(obs);
        tabla.getSelectionModel().selectedItemProperty().addListener((observable,t0,t1) -> {
            alumno=t1;
            Sesion.setAlumno(alumno);
        });


        menuItem2.setOnAction(actionEvent -> {
            //Se borra usuario junto con todas las tareas diarias de ese usuario
        });
        spinnerDUAL.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0,270,0,1));
        spinnerFCT.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0,270,0,1));
    }

    @Deprecated
    public void editar(ActionEvent actionEvent) {
        if(alumno==null){
            Alert alerta=new Alert(Alert.AlertType.INFORMATION);
            alerta.setTitle("Seleccion Tabla");
            alerta.setHeaderText("Porfavor Selecciona un alumno en la tabla");
            alerta.showAndWait();
        }else{
            Sesion.setAlumno(alumno);
            HelloApplication.loadFXML("ventana-editar.fxml");
        }

    }

    @Deprecated
    public void borrar(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void nuevoALumno(ActionEvent actionEvent) {
        try {
            Alumno alumno = new Alumno();
            if (!textApellidos.getText().isEmpty() && !textNombre.getText().isEmpty() && !textEmail.getText().isEmpty()
                    && !textTelefono.getText().isEmpty() && !comboCurso.getSelectionModel().getSelectedItem().equals(null)
                    && !contraseñaAlumno.getText().isEmpty() && !textDNI.getText().isEmpty()) {


                textApellidos.getText().strip();

                textNombre.getText().strip();
                alumno.setNombre(textNombre.getText());
                textDNI.getText().strip();
                textEmail.getText().strip();
                alumno.setCorreo(textEmail.getText());
                textTelefono.getText().strip();
                alumno.setTelefono(Integer.valueOf(textTelefono.getText()));
                String fecha = String.valueOf(dateCalender.getValue());
                System.out.println(fecha);
                if(fecha.equals("null")){
                    Alert alerta = new Alert(Alert.AlertType.ERROR);
                    alerta.setTitle("Error");
                    alerta.setHeaderText("Fecha De Nacimiento No Seleccionada");
                    alerta.setContentText("Asegurese de que los datos de la fecha de nacimiento\n sean correctos.");
                    alerta.showAndWait();
                }
                alumno.setFechaNacimiento(fecha);
                alumno.setPassword(contraseñaAlumno.getText());
                alumno.setObservaciones("");
                alumno.setProfesorId(Sesion.getProfesor().getId());
                alumno.setProfesor(Sesion.getProfesor());

                alumno.setDni(textDNI.getText());

                String[] apellidos = textApellidos.getText().split(" ");
                if (apellidos.length != 2) {
                    Alert alerta = new Alert(Alert.AlertType.ERROR);
                    alerta.setTitle("Error");
                    alerta.setHeaderText("Apellidos Mal Transcritos");
                    alerta.setContentText("Asegurese de que los apellidos sean dos y \nseparados por un espacio.");
                    alerta.showAndWait();

                } else {

                    alumno.setApellido1((String) apellidos[0]);
                    alumno.setApellido2(apellidos[1]);

                }
                alumno.setCurso((Curso) comboCurso.getSelectionModel().getSelectedItem());

                if (radioDUal.isSelected()) {
                    alumno.setHorasDUAL(spinnerDUAL.getValue() + "/270");
                } else {
                    alumno.setHorasFCT(spinnerFCT.getValue() + "/270");
                }
                if (comboNombreEmpresa.getValue() == null) {
                    alumno.setEmpresa(null);
                    alumno.setEmpresaId(0);
                } else {
                    Empresa empresa = (Empresa) comboNombreEmpresa.getSelectionModel().getSelectedItem();
                    alumno.setEmpresa(empresa);
                    alumno.setEmpresaId(empresa.getId());
                    empresa.setAlumnos(new ArrayList<>());
                    empresa.getAlumnos().add(alumno);
                    System.out.println(empresa);
                }
                System.out.println(alumno);

                AlumnoDAOImp dao = new AlumnoDAOImp(DBConnection.getConnection());
               Alumno alumnoValido= dao.injection(alumno);
               Sesion.getProfesor().getAlumnos().add(alumnoValido);
                obs.add(alumnoValido);

            }else{
                Alert alerta = new Alert(Alert.AlertType.ERROR);
                alerta.setWidth(300);
                alerta.setTitle("Error");
                alerta.setHeaderText("Campos Incorrectos");
                alerta.setContentText("Asegurese de que los datos de todos los campos son correctos.");
                alerta.showAndWait();
            }

        } catch (NombreConNumero e) {
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Error");
            alerta.setHeaderText("Nombre con Numeros");
            alerta.setContentText("El nombre del alumno no puede contener numeros.");
            alerta.showAndWait();
            throw new RuntimeException(e);
        } catch (DNIInvalido e) {
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Error");
            alerta.setHeaderText("DNI Invalido");
            alerta.setContentText("El DNI es Imposible que pueda existir.");
            alerta.showAndWait();
            throw new RuntimeException(e);
        } catch (ApellidoConNumero e) {
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
        Sesion.setProfesor(null);
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
        FileChooser open=new FileChooser();
        File ruta= open.showOpenDialog(null);
        System.out.println(ruta.getName().substring(ruta.getName().indexOf(".")));
        if(ruta!=null){
            File carpeta=new File("imagenes de "+Sesion.getProfesor().getDni());
            if(!carpeta.exists()&&(ruta.getName().endsWith("jpg")||ruta.getName().endsWith("png")||ruta.getName().endsWith("PNG")||ruta.getName().endsWith("JPG"))){
                try {
                    carpeta.mkdir();
                    Path origen= Path.of(ruta.getAbsolutePath());
                    Path destino= Path.of(carpeta.getName());
                    Path destinoArchivo=destino.resolve(Sesion.getProfesor().getNombre()+" "+Sesion.getProfesor().getApellido1()+ruta.getName().substring(ruta.getName().indexOf(".")));
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
                Path destinoArchivo=destino.resolve(Sesion.getProfesor().getNombre()+" "+Sesion.getProfesor().getApellido1()+ruta.getName().substring(ruta.getName().indexOf(".")));
                try {
                    Files.copy(origen,destinoArchivo, StandardCopyOption.REPLACE_EXISTING);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                imagen.setImage(new Image("file:"+destinoArchivo));
            }
        }
    }

    @javafx.fxml.FXML
    public void goEmpresa(ActionEvent actionEvent) {
        HelloApplication.loadFXML("editar-empresa-view.fxml");
    }
}
