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
import javafx.fxml.FXML;
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

/**
 * Clase controladora de la ventana de gestión de alumnos por parte del profesor.
 */
public class TeacherViewController implements Initializable {
    @FXML
    private Menu menu;
    @FXML
    private MenuBar menuBar;
    @FXML
    private ToggleGroup botones;
    @FXML
    private Button btnNewAlumn;
    @FXML
    private MenuItem menuLogOut;
    @FXML
    private MenuItem menuAccount;
    @FXML
    private MenuItem menuGoOut;
    @FXML
    private Button btnEnterprise;
    /**
     * ComboBox de los grados disponibles.
     */
    @FXML
    private ComboBox cbGrade;

    /**
     * ComboBox de las empresas disponibles.
     */
    @FXML
    private ComboBox cbEnterprise;

    /**
     * TextField del DNI.
     */
    @FXML
    private TextField tfDNI;

    /**
     * DatePicker.
     */
    @FXML
    private DatePicker dpCalendar;

    /**
     * TextField con el nombre del alumno.
     */
    @FXML
    private TextField tfName;

    /**
     * TextField con el apellido del alumno.
     */
    @FXML
    private TextField tfLastName;

    /**
     * TextField con el email del alumno.
     */
    @FXML
    private TextField tfEmail;

    /**
     * TextField con el teléfono del alumno.
     */
    @FXML
    private TextField tfTelephone;

    /**
     * PasswordField con la contraseña del alumno.
     */
    @FXML
    private TextField pfAlumn;

    /**
     * RadioButton de selección para modalidad Dual.
     */
    @FXML
    private RadioButton radioDUal;

    /**
     * RadioButton de selección para FCT.
     */
    @FXML
    private RadioButton radioFCT;

    /**
     * Label para FCT.
     */
    @FXML
    private Label labelFCT;

    /**
     * Label para Dual.
     */
    @FXML
    private Label labelDUAL;

    /**
     * Spinner para selección de horas en DUAL.
     */
    @FXML
    private Spinner spinnerDUAL;

    /**
     * Spinner para selección de horas en FCT.
     */
    @FXML
    private Spinner spinnerFCT;

    /**
     * ImageView para la imagen de perfil del profesor.
     */
    @FXML
    private ImageView image;

    /**
     * Column nombra alumno.
     */
    @FXML
    private TableColumn<Alumn, String> cName;

    /**
     * Column aplellidos alumno.
     */
    @FXML
    private TableColumn<Alumn, String> cLastName;

    /**
     * Column teléfono alumno.
     */
    @FXML
    private TableColumn<Alumn, String> cTelephone;

    /**
     * Column fecha de nacimiento alumno.
     */
    @FXML
    private TableColumn<Alumn, String> cBirthday;

    /**
     * Column grado en el que cursa el alumno.
     */
    @FXML
    private TableColumn<Alumn, String> cGrade;

    /**
     * Column profesor al que se encuentra el alumno asignado.
     */
    @FXML
    private TableColumn<Alumn, String> cTeacher;

    /**
     * Column empresa en la que se encuentra el alumno.
     */
    @FXML
    private TableColumn<Alumn, String> cEnterprise;

    /**
     * Column horas de DUAL que ha cursado el alumno.
     */
    @FXML
    private TableColumn<Alumn, String> cHoursDual;

    /**
     * Column horass de FCT que ha cursado el alumno.
     */
    @FXML
    private TableColumn<Alumn, String> cHoursFCT;

    /**
     * Column email del alumno.
     */
    @FXML
    private TableColumn <Alumn, String> cEmail;

    /**
     * TableView de alumnos.
     */
    @FXML
    private TableView tvAlumn;

    /**
     * Observable de grados que puede cursar el alumno.
     */
    private ObservableList<Grade> observableGrade;

    /**
     * Observable usado de intermediario entre la Base de Datos y la tabla de alumnos en la vista.
     */
    private ObservableList<Alumn> observableAlumn;

    /**
     * Menu contextual al hacer click derecho en la tabla de alumnos de la vista.
     */
    private ContextMenu contextMenu = new ContextMenu();

    /**
     * Item de menu, Editar.
     */
    private MenuItem menuItem1 = new MenuItem();

    /**
     * Item de menu, Ver actividades.
     */
    private MenuItem menuItem2 = new MenuItem();

    /**
     * Item de menu, Borrar.
     */
    private MenuItem menuItem3 = new MenuItem();

    /**
     * Instancia de alumno.
     */
    private Alumn alumn;


    /**
     * Método de inicialización para el controlador de la vista del profesor.
     * Este método se llama automáticamente después de que se cargue el archivo FXML.
     * Se encarga de inicializar los elementos de la interfaz de usuario, cargar datos y configurar eventos.
     *
     * @param url             Ubicación utilizada para resolver rutas relativas para los recursos.
     * @param resourceBundle  Objeto ResourceBundle que se puede utilizar para localizar cadenas de texto.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Crea la ruta del directorio de imágenes para el profesor actual.
        File directory = new File("./imagenes de " + Sesion.getTeacher().getDni());

        //Verifica si el directorio de imágenes existe.
        if (directory.exists()) {

            //Si existe, obtiene una lista de archivos en ese directorio.
            File[] children = directory.listFiles();

            //Carga la primera imagen del directorio en un objeto Image.
            Image imageTeacher = new Image("file:" + directory.getName() + "/" + children[0].getName());

            //Establece la imagen en un componente ImageView llamado 'image'.
            image.setImage(imageTeacher);

        } else {
            //Si el directorio no existe, carga una imagen predeterminada.
            Image defaultImage = new Image(TeacherViewController.class.getClassLoader().getResource("com/example/proyectocesurapp/imagenes/usuario.png").toExternalForm());

            //Establece la imagen predeterminada en el componente ImageView 'image'.
            image.setImage(defaultImage);
        }

        //Configuración de los spinners y radios de FCT y Dual en caso de que Dual se haya seleccionado.
        radioDUal.setSelected(true);
        spinnerFCT.setVisible(false);
        labelFCT.setVisible(false);
        radioDUal.setOnAction(actionEvent -> {
            spinnerFCT.setVisible(false);
            labelFCT.setVisible(false);
            spinnerDUAL.setVisible(true);
            labelDUAL.setVisible(true);
        });

        //Configuración de los spinners y radios de FCT y Dual en caso de que FCT se haya seleccionado.
        radioFCT.setOnAction(actionEvent -> {
            spinnerDUAL.setVisible(false);
            labelDUAL.setVisible(false);
            spinnerFCT.setVisible(true);
            labelFCT.setVisible(true);
        });

        //Mete en el 'observableGrade' los distintos grados disponibles.
        observableGrade = FXCollections.observableArrayList();
        observableGrade.addAll(Grade.ASIR1, Grade.ASIR2, Grade.DAM1, Grade.DAM2, Grade.DAW1, Grade.DAW2);


        //Interfaz StringConverter para pasar de tipo Grade a cadena y viceversa.
        cbGrade.setConverter(new StringConverter<Grade>() {
            @Override
            public String toString(Grade grade) {
                if(grade != null){
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

        //Rellena el ComboBox 'cbGrade' con el 'observableGrade'.
        cbGrade.setItems(observableGrade);

        //Interfaz StringConverter para pasar de tipo Enterprise a cadena y viceversa.
        cbEnterprise.setConverter(new StringConverter<Enterprise>() {
            @Override
            public String toString(Enterprise enterprise) {
                if (enterprise != null){
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

        //Coge todas las empresas y las mete en el ComboBox 'cbEnterprise'.
        cbEnterprise.getItems().addAll((new EnterpriseDAOImp(DBConnection.getConnection()).loadAll()));
        cbEnterprise.getItems().add(null);
        cbEnterprise.getSelectionModel().selectFirst();

        //Mapeo de columnas de la tabla:
        cName.setCellValueFactory((row) -> {
            String name = row.getValue().getName();
            return new SimpleStringProperty(name);
        });

        cLastName.setCellValueFactory((row) -> {
            String lastName = row.getValue().getLastName()+" "+row.getValue().getLastName2();
            return new SimpleStringProperty(lastName);
        });

        cEmail.setCellValueFactory((row) -> {
            String email = row.getValue().getEmail();
            return new SimpleStringProperty(email);
        });
        cTelephone.setCellValueFactory((row) -> {
            String telephone = String.valueOf(row.getValue().getPhone()) ;
            return new SimpleStringProperty(telephone);
        });
        cBirthday.setCellValueFactory((row) -> {
            String birthday = row.getValue().getBirthday();
            return new SimpleStringProperty(birthday);
        });
        cGrade.setCellValueFactory((row) -> {
            String grade = String.valueOf(row.getValue().getGrade());
            return new SimpleStringProperty(grade);
        });
        cTeacher.setCellValueFactory((row) -> {
            String teacher = row.getValue().getTeacher().getName()+" "+row.getValue().getTeacher().getLastName();
            return new SimpleStringProperty(teacher);
        });
        cHoursDual.setCellValueFactory((row) -> {
            String hoursDual = row.getValue().getHoursDUAL();
            return new SimpleStringProperty(hoursDual);
        });
        cHoursFCT.setCellValueFactory((row) -> {
            String hoursFct = row.getValue().getHoursFCT();
            return new SimpleStringProperty(hoursFct);
        });
        cEnterprise.setCellValueFactory((row) -> {
            String enterprise = "";
            if(row.getValue().getEnterprise() == null){
               enterprise = "<<vacio>>";
            }else{
                enterprise = row.getValue().getEnterprise().getName();
            }
            return new SimpleStringProperty(enterprise);
        });

        //Declaración y seteo de los distintos menús contextuales al hacer click derecho en la tabla de alumnos de la vista.
        tvAlumn.setOnMousePressed(mouseEvent -> {
            if(mouseEvent.isSecondaryButtonDown() && alumn != null){
                contextMenu = new ContextMenu();
                menuItem1 = new MenuItem("Editar");
                contextMenu.getItems().add(menuItem1);
                contextMenu.getItems().add(new SeparatorMenuItem());
                menuItem2 = new MenuItem("Borrar");
                contextMenu.getItems().add(menuItem2);
                contextMenu.getItems().add(new SeparatorMenuItem());
                menuItem3 = new MenuItem("Ver actividades");
                contextMenu.getItems().add(menuItem3);

                tvAlumn.setContextMenu(contextMenu);
                contextMenu.show(tvAlumn,mouseEvent.getScreenX(), mouseEvent.getScreenY());

                menuItem1.setOnAction(actionEvent -> {
                    App.loadFXML("editAlumn-controller.fxml");
                });

                menuItem2.setOnAction(actionEvent -> {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Eliminar");
                        alert.setHeaderText("¿Seguro que deseas BORRAR al alumno?");
                        alert.setContentText("Si ELIMINAS a este alumno boraras TODAS sus ACTIVIDADES");
                        ButtonType type = alert.showAndWait().get();

                        if(type.getButtonData() == ButtonBar.ButtonData.OK_DONE){
                            Alumn alumnIndex = Sesion.getTeacher().getAlumn().get(Sesion.getTeacher().getAlumn().indexOf(alumn));

                            DailyActivityDAOImp actDIB = new DailyActivityDAOImp(DBConnection.getConnection());

                            for(int i = 0; i< alumnIndex.getActivity().size(); i++){
                                actDIB.delete(alumnIndex.getActivity().get(i));
                            }

                            (new AlumnDAOImp(DBConnection.getConnection())).delete(alumnIndex);
                            AlumnDAOImp alumnDAOImp = new AlumnDAOImp(DBConnection.getConnection());
                            observableAlumn.setAll(alumnDAOImp.loadAll(Sesion.getTeacher().getId()));
                        }
                });

                menuItem3.setOnAction(actionEvent -> {
                    App.loadFXML("alumnViewActivity-controller.fxml");
                });
            }
        });

        //Si Sesion.getCount() es igual a 1.
        if (Sesion.getCount() == 1) {

            //Establece el valor de Sesion.getCount() a 0.
            Sesion.setCount((byte) 0);

            //Crea una instancia de AlumnDAOImp utilizando una conexión a la Base de Datos.
            AlumnDAOImp alumnDAOImp = new AlumnDAOImp(DBConnection.getConnection());

            //Crea una nueva lista vacía de alumnos y la establece en el profesor actual de la sesión.
            Sesion.getTeacher().setAlumn(new ArrayList<>());

            //Carga todos los alumnos asociados al profesor actual y los agrega a la lista de alumnos del profesor en la sesión.
            Sesion.getTeacher().getAlumn().addAll(alumnDAOImp.loadAll(Sesion.getTeacher().getId()));

            //Crea un ObservableList llamado 'observableAlumn'.
            observableAlumn = FXCollections.observableArrayList();

            //Agrega todos los alumnos obtenidos a 'observableAlumn'
            observableAlumn.addAll(Sesion.getTeacher().getAlumn());

            //Configura 'tvAlumn' para mostrar los elementos de 'observableAlumn'.
            tvAlumn.setItems(observableAlumn);

        } else {  //Si Sesion.getCount() no es igual a 1.

            //Crea un nuevo ObservableList llamado 'observableAlumn'.
            observableAlumn = FXCollections.observableArrayList();

            //Crea una instancia de AlumnDAOImp utilizando una conexión a la Base de Datos.
            AlumnDAOImp alumnDAOImp = new AlumnDAOImp(DBConnection.getConnection());

            //Elimina todos los elementos de la lista de alumnos del profesor actual en la sesión.
            Sesion.getTeacher().getAlumn().clear();

            //Carga todos los alumnos asociados al profesor actual y los agrega a la lista de alumnos del profesor en la sesión.
            Sesion.getTeacher().getAlumn().addAll(alumnDAOImp.loadAll(Sesion.getTeacher().getId()));

            //Agrega todos los alumnos obtenidos a 'observableAlumn'.
            observableAlumn.addAll(Sesion.getTeacher().getAlumn());

            //Configura 'tvAlumn' para mostrar los elementos de 'observableAlumn'.
            tvAlumn.setItems(observableAlumn);
        }

        //Al pulsar sobre un alumno en la tabla de la vista, lo mete en sesión.
        tvAlumn.getSelectionModel().selectedItemProperty().addListener((observable,t0,t1) -> {
            alumn = (Alumn) t1;
            Sesion.setAlumn(alumn);
        });

        //Establece los spinner y sus valores por defecto, así como sus máximos y mínimos.
        spinnerDUAL.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0,270,0,1));
        spinnerFCT.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0,270,0,1));
    }

    /**
     * Método asociado a un evento de edición.
     * Verifica si se ha seleccionado un alumno en la tabla.
     * Si no se ha seleccionado ningún alumno, muestra una alerta pidiendo seleccionar un alumno.
     * Si se ha seleccionado un alumno, establece el alumno seleccionado en la sesión y carga una ventana de edición.
     *
     * @param actionEvent El evento que activa esta función.
     */
    @Deprecated
    public void edit(ActionEvent actionEvent) {
        if(alumn == null){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Seleccion Tabla");
            alert.setHeaderText("Porfavor Selecciona un alumno en la tabla");
            alert.showAndWait();
        }else{
            Sesion.setAlumn(alumn);
            App.loadFXML("ventana-editar.fxml");
        }

    }

    /**
     * Método para eliminar alumnos.
     *
     * @param actionEvent El evento que activa esta función.
     */
    @Deprecated
    public void delete(ActionEvent actionEvent) {
        //Do nothing.
    }

    /**
     * Método asociado al evento de creación de un nuevo alumno.
     * Recopila datos de entrada de la interfaz de usuario para crear un nuevo objeto Alumn.
     * Verifica la validez de los campos ingresados y muestra alertas en caso de campos incorrectos o faltantes.
     * Después de verificar la validez de los datos, crea un nuevo objeto Alumn y lo agrega a la sesión actual del profesor.
     *
     * @param actionEvent El evento que activa esta función.
     * @throws NameWithNumber Si el nombre del alumno contiene números.
     * @throws InvalidDNI Si el DNI del alumno es inválido.
     * @throws LastNameWithNumber Si el apellido del alumno contiene números.
     */
    @FXML
    public void newAlumn(ActionEvent actionEvent) {
        try {
            /*Código para recopilar datos de la interfaz de usuario y crear un nuevo objeto Alumn
            * junto a verificación de la validez de los datos ingresados y manejo de excepciones en caso de errores.*/
            Alumn alumn = new Alumn();
            if (!tfLastName.getText().isEmpty() && !tfName.getText().isEmpty() && !tfEmail.getText().isEmpty()
                    && !tfTelephone.getText().isEmpty() && !cbGrade.getSelectionModel().getSelectedItem().equals(null)
                    && !pfAlumn.getText().isEmpty() && !tfDNI.getText().isEmpty()) {

                tfLastName.getText().strip();
                tfName.getText().strip();
                alumn.setName(tfName.getText());
                tfDNI.getText().strip();
                tfEmail.getText().strip();
                alumn.setEmail(tfEmail.getText());
                tfTelephone.getText().strip();
                alumn.setPhone(Integer.valueOf(tfTelephone.getText()));
                String fecha = String.valueOf(dpCalendar.getValue());

                if(fecha.equals("null")){
                    Alert alerta = new Alert(Alert.AlertType.ERROR);
                    alerta.setTitle("Error");
                    alerta.setHeaderText("Fecha De Nacimiento No Seleccionada");
                    alerta.setContentText("Asegurese de que los datos de la fecha de nacimiento\n sean correctos.");
                    alerta.showAndWait();
                }

                alumn.setBirthday(fecha);
                alumn.setPassword(pfAlumn.getText());
                alumn.setObservations("");
                alumn.setTeacherID(Sesion.getTeacher().getId());
                alumn.setTeacher(Sesion.getTeacher());
                alumn.setDni(tfDNI.getText());
                alumn.setActivity(new ArrayList<>());

                String[] apellidos = tfLastName.getText().split(" ");

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

                alumn.setGrade((Grade) cbGrade.getSelectionModel().getSelectedItem());

                if (radioDUal.isSelected()) {
                    alumn.setHoursDUAL(spinnerDUAL.getValue() + "/270");
                } else {
                    alumn.setHoursFCT(spinnerFCT.getValue() + "/270");
                }

                if (cbEnterprise.getValue() == null) {
                    alumn.setEnterprise(null);
                    alumn.setEnterpriseID(0);
                } else {
                    Enterprise enterprise = (Enterprise) cbEnterprise.getSelectionModel().getSelectedItem();
                    System.out.println(enterprise);
                    alumn.setEnterprise(enterprise);
                    alumn.setEnterpriseID(enterprise.getId());
                    enterprise.setAlumn(new ArrayList<>());
                    enterprise.getAlumn().add(alumn);
                }

                AlumnDAOImp alumnDAOImp = new AlumnDAOImp(DBConnection.getConnection());
               Alumn alumnValido = alumnDAOImp.injection(alumn);
               Sesion.getTeacher().getAlumn().add(alumnValido);
               observableAlumn.add(alumnValido);

            }else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setWidth(300);
                alert.setTitle("Error");
                alert.setHeaderText("Campos Incorrectos");
                alert.setContentText("Asegurese de que los datos de todos los campos son correctos.");
                alert.showAndWait();
            }

        } catch (NameWithNumber e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Nombre con Numeros");
            alert.setContentText("El nombre del alumno no puede contener numeros.");
            alert.showAndWait();
            throw new RuntimeException(e);
        } catch (InvalidDNI e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("DNI Invalido");
            alert.setContentText("El DNI es Imposible que pueda existir.");
            alert.showAndWait();
            throw new RuntimeException(e);
        } catch (LastNameWithNumber e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Apellido Con Numeros");
            alert.setContentText("El Apellido del alumno no puede contener numeros.");
            alert.showAndWait();
            throw new RuntimeException(e);
        }
    }

    /**
     * Permite hacer LogOut del profesor en sesión.
     *
     * @param actionEvent El evento que activa esta función.
     */
    @FXML
    public void logOut(ActionEvent actionEvent) {
        Sesion.setTeacher(null);
        App.loadFXML("login-controller.fxml");
    }

    /**
     * Permite ver la información de la cuenta del profesor.
     *
     * @param actionEvent El evento que activa esta función.
     */
    @FXML
    public void account(ActionEvent actionEvent) {
        App.loadFXML("teacherAccount-controller.fxml");
    }

    /**
     * Permite salir de la aplicación.
     *
     * @param actionEvent El evento que activa esta función.
     */
    @FXML
    public void goOut(ActionEvent actionEvent) {
        System.exit(0);
    }

    /**
     * Permite cambiar la imagen de perfil del profesor.
     *
     * @param event El evento que activa esta función.
     */
    @FXML
    public void clickImage(Event event) {
        //Abre un FileChooser para seleccionar un archivo de imagen.
        FileChooser fileChooser = new FileChooser();
        File route = fileChooser.showOpenDialog(null);

        //Verifica si se seleccionó un archivo.
        if (route != null) {

            //Crea el directorio para almacenar las imágenes del profesor actual.
            File directory = new File("imagenes de " + Sesion.getTeacher().getDni());

            //Comprueba si el directorio no existe y si el archivo seleccionado es una imagen (jpg o png).
            if (!directory.exists() && (route.getName().endsWith("jpg") || route.getName().endsWith("png") || route.getName().endsWith("PNG") || route.getName().endsWith("JPG"))) {
                try {

                    //Crea el directorio.
                    directory.mkdir();

                    //Obtiene la ruta de origen y destino para copiar la imagen seleccionada.
                    Path origin = Path.of(route.getAbsolutePath());
                    Path destiny = Path.of(directory.getName());
                    Path destinyFile = destiny.resolve(Sesion.getTeacher().getName() + " " + Sesion.getTeacher().getLastName() + route.getName().substring(route.getName().indexOf(".")));

                    //Copia la imagen seleccionada al directorio del profesor y establece la imagen en el componente ImageView.
                    Files.copy(origin, destinyFile, StandardCopyOption.REPLACE_EXISTING);
                    image.setImage(new Image("file:" + destinyFile));

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else { //Si el directorio existe o el archivo no es una imagen válido (jpg o png).

                //Obtiene los archivos existentes en el directorio y elimina el primero.
                File[] children = directory.listFiles();
                children[0].delete();

                //Obtiene la ruta de origen y destino para copiar la imagen seleccionada.
                Path origin = Path.of(route.getAbsolutePath());
                Path destiny = Path.of(directory.getName());
                Path destinyFile = destiny.resolve(Sesion.getTeacher().getName() + " " + Sesion.getTeacher().getLastName() + route.getName().substring(route.getName().indexOf(".")));

                try {
                    //Copia la imagen seleccionada al directorio del profesor y establece la imagen en el componente ImageView.
                    Files.copy(origin, destinyFile, StandardCopyOption.REPLACE_EXISTING);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    image.setImage(new Image("file:" + destinyFile));
                }
            }

    }

    /**
     * Método que sirve para ir a la ventana de gestión de empresas.
     *
     * @param actionEvent Evento que activa esta función.
     */
    @FXML
    public void goEnterprise(ActionEvent actionEvent) {
        App.loadFXML("enterpriseView-controller.fxml");
    }

}
