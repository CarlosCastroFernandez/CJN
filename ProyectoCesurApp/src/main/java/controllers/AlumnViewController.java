package controllers;

import classes.DailyActivity;
import classes.Sesion;
import com.example.proyectocesurapp.App;
import domain.dailyActivity.DailyActivityDAOImp;
import domain.DBConnection;
import enums.PracticeType;
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
import java.io.File;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ResourceBundle;

/**
 * Clase controladora de la ventana de alumno, en la que el mismo puede gestionar sus actividades diarias.
 */
public class AlumnViewController implements Initializable {
    @FXML
    private Button btnAdd;
    @FXML
    private MenuBar menu;
    @FXML
    private MenuItem menuLogOut;
    @FXML
    private MenuItem menuItemCount;
    @FXML
    private MenuItem menuGetOut;
    @FXML
    private Button btnClean;

    /**
     * ImageView en la que el alumno puede poner su foto de perfil.
     */
    @FXML
    private ImageView ivUser;

    /**
     * DatePicker con la fecha en la que se realizó la actividad diaria.
     */
    @FXML
    private DatePicker dpDate;

    /**
     * TextArea donde el alumno puede escribir observaciones sobre la actividad diaria realizada.
     */
    @FXML
    private TextArea taObservations;

    /**
     * ComboBox para el tipo de práctica para el que se ha realizado la actividad(Dual o FCT).
     */
    @FXML
    private ComboBox<PracticeType> comboPracticeType;

    /**
     * TextField donde poner el nombre de la actividad realizada.
     */
    @FXML
    private TextField tfActivity;

    /**
     * Spinner para poner las horas que se han dedicado a la actividad.
     */
    @FXML
    private Spinner<Integer> spHoras;

    /**
     * Column con la información de la fecha en la que se realizó la actividad diaria.
     */
    @FXML
    private TableColumn<DailyActivity, String> cDate;

    /**
     * Column con el tipo de práctica (Dual o FCT) de la actividad realizada.
     */
    @FXML
    private TableColumn<DailyActivity, String> cTPractice;

    /**
     * Column con el total de horas dedicadas a la actividad realizada.
     */
    @FXML
    private TableColumn<DailyActivity, String> cTHours;

    /**
     * Column con el nombre de la actividad realizada.
     */
    @FXML
    private TableColumn<DailyActivity, String> cActivityRealised;

    /**
     * Column con las observaciones de la actividad realizada.
     */
    @FXML
    private TableColumn<DailyActivity, String> cObservations;

    /**
     * TableView que muestra las actividades diarias que ha realizado el usuario.
     */
    @FXML
    private TableView<DailyActivity> tvUser;

    /**
     * Observable de actividades diarias que hace de intermediario entre la vista y la Base de Datos.
     */
    private ObservableList<DailyActivity> observableActividad;

    /**
     * Instancia de actividad diaria.
     */
    private DailyActivity dailyActivity;

    /**
     * Menú contextual al hacer click derecho sobre una fila de la tabla.
     */
    private ContextMenu contextMenu = new ContextMenu();

    /**
     * Primera opción del menú contextual.
     */
    private MenuItem menuItem1 = new MenuItem();

    /**
     * Segunda opción del menú contextual.
     */
    private MenuItem menuItem2 = new MenuItem();

    /**
     * Inicializa la interfaz de usuario al cargar la vista.
     * Carga la imagen del usuario si existe, de lo contrario muestra una imagen predeterminada.
     * Configura los elementos de la interfaz gráfica y maneja eventos para la tabla de actividades diarias del alumno.
     *
     * @param url            La ubicación utilizada para resolver rutas relativas de archivos.
     * @param resourceBundle El paquete de recursos utilizado para localizar archivos de recursos.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        File file = new File("./imagenes de " + Sesion.getAlumn().getDni());
        if (file.exists()) {
            File[] children = file.listFiles();
            Image image = new Image("file:" + file.getName() + "/" + children[0].getName());
            ivUser.setImage(image);
        } else {
            Image image = new Image(AlumnViewController.class.getClassLoader().getResource("com/example/proyectocesurapp/imagenes/usuario.png").toExternalForm());
            ivUser.setImage(image);
        }

        /*Establecimiento del ComboBox con el tipo de práctica y del
        * spinner.*/
        ObservableList<PracticeType> practiceType = FXCollections.observableArrayList();
        practiceType.addAll(PracticeType.DUAL, PracticeType.FCT);
        comboPracticeType.setItems(practiceType);
        comboPracticeType.getSelectionModel().selectFirst();
        comboPracticeType.setDisable(true);
        spHoras.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 8, 0, 1));

        //Mapeo de las celdas de la tabla.
        cDate.setCellValueFactory((row) -> {
            String date = row.getValue().getDate();
            return new SimpleStringProperty(date);
        });
        cTPractice.setCellValueFactory((row) -> {
            String tPractice = String.valueOf(row.getValue().getPracticeType());
            return new SimpleStringProperty(tPractice);
        });
        cTHours.setCellValueFactory((row) -> {
            String totalHours = String.valueOf(row.getValue().getTotalHours());
            return new SimpleStringProperty(totalHours);
        });
        cObservations.setCellValueFactory((row) -> {
            String observations = row.getValue().getObservations();
            return new SimpleStringProperty(observations);
        });
        cActivityRealised.setCellValueFactory((row) -> {
            String activityRealised = row.getValue().getTaskName();
            return new SimpleStringProperty(activityRealised);
        });

        //Establecimiento del menú contextual al hacer click derecho sobre una fila de la tabla.
        tvUser.setOnMousePressed(mouseEvent -> {
            if (mouseEvent.isSecondaryButtonDown() && dailyActivity != null) {
                contextMenu = new ContextMenu();
                menuItem1 = new MenuItem("Editar");
                contextMenu.getItems().add(menuItem1);
                contextMenu.getItems().add(new SeparatorMenuItem());
                menuItem2 = new MenuItem("Borrar");
                contextMenu.getItems().add(menuItem2);
                tvUser.setContextMenu(contextMenu);
                contextMenu.show(tvUser, mouseEvent.getScreenX(), mouseEvent.getScreenY());

                menuItem1.setOnAction(actionEvent -> App.loadFXML("editDailyActivity-controller.fxml"));

                menuItem2.setOnAction(actionEvent -> {

                    //Alerta para confirmar la eliminación de la actividad diaria.
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Eliminar");
                        alert.setHeaderText("¿Seguro que deseas BORRAR esta actividad?");
                        ButtonType type = alert.showAndWait().get();

                        //Si el usuario le da al botón de 'Ok' la ctividad se elimina.
                        if(type.getButtonData() == ButtonBar.ButtonData.OK_DONE){
                            DailyActivity dailyActivityDelete = Sesion.getAlumn().getActivity().remove(Sesion.getAlumn().getActivity().indexOf(dailyActivity));
                            observableActividad.setAll(Sesion.getAlumn().getActivity());
                            tvUser.setItems(observableActividad);
                            DailyActivityDAOImp dailyActivityDAOImp = new DailyActivityDAOImp(DBConnection.getConnection());
                            dailyActivityDAOImp.delete(dailyActivityDelete);
                        }
                });
            }
        });

        //Se obtiene el ID del alumno actualmente logueado.
        Integer idAlumn = Sesion.getAlumn().getId();

        //Creación de una instancia de DailyActivityDAOImp para interactuar con la Base de Datos.
        DailyActivityDAOImp dailyActivityDAOImp = new DailyActivityDAOImp(DBConnection.getConnection());

        //Se agregan todas las actividades diarias del alumno obtenidas de la Base de Datos a la lista de actividades del alumno en sesión.
        Sesion.getAlumn().getActivity().addAll(dailyActivityDAOImp.loadall(idAlumn));

        //Si la lista de actividades del alumno en sesión está vacía:
        if (Sesion.getAlumn().getActivity().isEmpty()) {
            //Se agregan nuevamente todas las actividades obtenidas de la Base de Datos a la lista de actividades del alumno en sesión.
            Sesion.getAlumn().getActivity().addAll(dailyActivityDAOImp.loadall(idAlumn));

            //Se crea un nuevo ObservableList para hacer de intermediario y se carga con las actividades del alumno en sesión.
            observableActividad = FXCollections.observableArrayList();
            observableActividad.addAll(Sesion.getAlumn().getActivity());

            //Se rellena la tabla con el Observable.
            tvUser.setItems(observableActividad);
        } else {
            //Si la lista de actividades del alumno en sesión no está vacía:
            //Se limpia la tabla 'tvUser' para eliminar los elementos existentes.
            tvUser.getItems().clear();

            //Se limpia la lista de actividades del alumno en sesión.
            Sesion.getAlumn().getActivity().clear();

            //Se actualiza la lista de actividades del alumno en sesión con las actividades obtenidas de la Base de Datos.
            Sesion.getAlumn().setActivity(dailyActivityDAOImp.loadall(idAlumn));

            //Se crea un nuevo ObservableList para hacer de intermediario y se carga con las actividades del alumno en sesión.
            observableActividad = FXCollections.observableArrayList();
            observableActividad.addAll(Sesion.getAlumn().getActivity());

            //Se rellena la tabla con el Observable.
            tvUser.setItems(observableActividad);
        }

        //Se agrega un listener para detectar cambios en la selección de elementos en la tabla 'tvUser'.
        tvUser.getSelectionModel().selectedItemProperty().addListener((observable, t0, t1) -> {
            dailyActivity = t1;
            Sesion.setActivity(dailyActivity);
        });
    }

    /**
     * Método asociado a la acción de añadir una nueva tarea en la interfaz de usuario y a la Base de Datos.
     *
     * @param actionEvent El evento que desencadena la acción.
     */
    @FXML
    public void addActivity(ActionEvent actionEvent) {
        //Se crea una nueva actividad diaria.
        DailyActivity activity = new DailyActivity();

        //Se comprueba si los campos necesarios no están vacíos y se valida la fecha.
        if (!comboPracticeType.getSelectionModel().getSelectedItem().equals(null)
                && !tfActivity.getText().isEmpty() && !taObservations.getText().isEmpty()) {

            //Se obtiene y valida la fecha de la actividad.
            String fecha = String.valueOf(dpDate.getValue());
            if (fecha.equals("null")) {
                //Si la fecha es nula, se muestra una alerta de error.
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Fecha de la actividad nula");
                alert.setContentText("Asegúrese de que los datos de la fecha de actividad sean correctos.");
                alert.showAndWait();
            }

            //Se establecen los valores de la actividad con los datos ingresados en la vista.
            activity.setDate(String.valueOf(dpDate.getValue()));
            activity.setPracticeType(comboPracticeType.getSelectionModel().getSelectedItem());
            activity.setTotaHours(spHoras.getValue());
            activity.setTaskName(tfActivity.getText().strip());
            activity.setObservations(taObservations.getText().strip());

            //Se guarda la actividad en la Base de Datos.
            DailyActivityDAOImp dao = new DailyActivityDAOImp(DBConnection.getConnection());
            activity = dao.injection(activity);

            //Se añade la actividad a la lista de actividades del alumno en sesión y al ObservableList.
            Sesion.getAlumn().getActivity().add(activity);
            observableActividad.add(activity);
        } else {
            //Si alguno de los campos requeridos está vacío, se muestra una alerta de error.
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setWidth(300);
            alert.setTitle("Error");
            alert.setHeaderText("Campos vacíos");
            alert.setContentText("Asegúrese de que todos los campos hayan sido rellenados");
            alert.showAndWait();
        }
    }

    /**
     * Método asociado al evento de hacer click en la imagen de perfil de la ventana de alumno.
     *
     * @param event El evento que desencadena la acción.
     */
    @FXML
    public void clickImage(Event event) {
        //Se crea un selector de archivo.
        FileChooser fileChooser = new FileChooser();

        //Se muestra la ventana de diálogo para seleccionar un archivo.
        File file = fileChooser.showOpenDialog(null);

        if (file != null) {
            //Se crea un directorio para almacenar las imágenes asociadas al alumno.
            File directory = new File("imagenes de " + Sesion.getAlumn().getDni());

            //Si el directorio no existe y el archivo seleccionado es una imagen (jpg o png):
            if (!directory.exists() && (file.getName().endsWith("jpg") || file.getName().endsWith("png") || file.getName().endsWith("PNG") || file.getName().endsWith("JPG"))) {
                try {
                    //Se crea el directorio.
                    directory.mkdir();

                    //Se copia la imagen seleccionada al directorio del alumno.
                    Path origin = Path.of(file.getAbsolutePath());
                    Path destiny = Path.of(directory.getName());
                    Path destinyFile = destiny.resolve(Sesion.getAlumn().getName() + " " + Sesion.getAlumn().getLastName() + file.getName().substring(file.getName().indexOf(".")));
                    Files.copy(origin, destinyFile, StandardCopyOption.REPLACE_EXISTING);

                    //Se actualiza la imagen en la interfaz de usuario con la nueva imagen seleccionada.
                    ivUser.setImage(new Image("file:" + destinyFile));
                } catch (IOException e) {
                    //Si hay un error en el proceso de copiado, se lanza una excepción.
                    throw new RuntimeException(e);
                }
            } else {
                //Si el directorio existe, se elimina la imagen existente.
                File[] children = directory.listFiles();
                children[0].delete();

                //Se copia la nueva imagen seleccionada al directorio del alumno.
                Path origin = Path.of(file.getAbsolutePath());
                Path destiny = Path.of(directory.getName());
                Path destinyFile = destiny.resolve(Sesion.getAlumn().getName() + " " + Sesion.getAlumn().getLastName() + file.getName().substring(file.getName().indexOf(".")));
                try {
                    Files.copy(origin, destinyFile, StandardCopyOption.REPLACE_EXISTING);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                //Se actualiza la imagen en la interfaz de usuario con la nueva imagen seleccionada.
                ivUser.setImage(new Image("file:" + destinyFile));
            }
        }
    }

    /**
     * Método para volver a la pantalla de Login haciendo LogOut.
     *
     * @param actionEvent El evento que desencadena la acción.
     */
    @FXML
    public void logOut(ActionEvent actionEvent) {
        Sesion.setAlumn(null);
        App.loadFXML("login-controller.fxml");
    }

    /**
     * Método para salir de la aplicación.
     *
     * @param actionEvent El evento que desencadena la acción.
     */
    @FXML
    public void goOut(ActionEvent actionEvent) {
        Sesion.setAlumn(null);
        System.exit(0);
    }

    /**
     * Método para acceder a los datos del perfil del alumno.
     *
     * @param actionEvent El evento que desencadena la acción.
     */
    @FXML
    public void account(ActionEvent actionEvent) {
        App.loadFXML("alumnAccount-controller.fxml");
    }

    /**
     * Método para restablecer todos los campos.
     *
     * @param actionEvent El evento que desencadena la acción.
     */
    @FXML
    public void clean(ActionEvent actionEvent) {
        taObservations.clear();
        dpDate.setValue(null);
        spHoras.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0,8,0,1));
        tfActivity.clear();
    }
}

