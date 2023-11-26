package controllers;

import classes.Alumn;
import classes.Enterprise;
import classes.Sesion;
import com.example.proyectocesurapp.App;
import domain.alumn.AlumnDAOImp;
import domain.DBConnection;
import enums.Grade;
import domain.enterprise.EnterpriseDAOImp;
import enums.PracticeType;
import exception.LastNameWithNumber;
import exception.InvalidDNI;
import exception.NameWithNumber;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.StringConverter;


import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class EditAlumnController implements Initializable {
    @javafx.fxml.FXML
    private TextField textDNI;
    @javafx.fxml.FXML
    private TextField textNombre;
    @javafx.fxml.FXML
    private TextField textEmail;
    @javafx.fxml.FXML
    private TextField textTelefono;
    @javafx.fxml.FXML
    private DatePicker dateCalender;
    @javafx.fxml.FXML
    private ComboBox<Grade> comboCurso;
    @javafx.fxml.FXML
    private ComboBox<Enterprise> comboNombreEmpresa;
    @javafx.fxml.FXML
    private Spinner spinnerFCT;
    @javafx.fxml.FXML
    private Spinner spinnerDUAL;
    @javafx.fxml.FXML
    private TextField textApellido1;
    @javafx.fxml.FXML
    private TextField textApellido2;
    @javafx.fxml.FXML
    private ChoiceBox<PracticeType> choiceTipoPractica;
    @javafx.fxml.FXML
    private Button btnGuardar;
    @javafx.fxml.FXML
    private Button btnCancelar;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        textNombre.setText(Sesion.getAlumn().getName());
        textApellido1.setText(Sesion.getAlumn().getLastName());
        textApellido2.setText(Sesion.getAlumn().getLastName2());
        textTelefono.setText("" + Sesion.getAlumn().getPhone());
        textDNI.setText(Sesion.getAlumn().getDni());
        textEmail.setText(Sesion.getAlumn().getEmail());
        DateTimeFormatter fecha=DateTimeFormatter.ofPattern("yyy-MM-dd");
        String fechaAlumno=Sesion.getAlumn().getBirthday();
        LocalDate converterFecha=LocalDate.parse(fechaAlumno,fecha);
        dateCalender.setValue(converterFecha);
        choiceTipoPractica.setConverter(new StringConverter<PracticeType>() {
            @Override
            public String toString(PracticeType practiceType) {
                if (practiceType != null) {
                    return String.valueOf(practiceType);
                } else {
                    return null;
                }

            }

            @Override
            public PracticeType fromString(String s) {
                return null;
            }
        });
        try {
            comboCurso.setConverter(new StringConverter<Grade>() {
                @Override
                public String toString(Grade grade) {
                    if (grade != null) {
                        return String.valueOf(grade);
                    } else {
                        return null;
                    }

                }

                @Override
                public Grade fromString(String s) {
                    return null;
                }
            });

            comboNombreEmpresa.setConverter(new StringConverter<Enterprise>() {
                @Override
                public String toString(Enterprise enterprise) {
                    if (enterprise != null) {
                        return enterprise.getName();
                    } else {
                        return "<<Sin Empresa>>";
                    }

                }

                @Override
                public Enterprise fromString(String s) {
                    return null;
                }
            });
        } catch (Exception e) {

        }
        choiceTipoPractica.getItems().addAll(PracticeType.DUAL, PracticeType.FCT);
        choiceTipoPractica.getSelectionModel().selectedItemProperty().addListener((observableValue, tipoPractica, t1) -> {

                if (t1== PracticeType.DUAL) {
                    choiceTipoPractica.setValue(PracticeType.DUAL);
                    spinnerFCT.setVisible(false);
                    spinnerFCT.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 270, 0, 1));
                    spinnerDUAL.setVisible(true);

                } else{
                choiceTipoPractica.setValue(PracticeType.FCT);
                spinnerDUAL.setVisible(false);
                spinnerDUAL.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 270, 0, 1));
                spinnerFCT.setVisible(true);
            }
        });

        try {
            if (Sesion.getAlumn().getHoursDUAL().contains("/270")) {
                choiceTipoPractica.setValue(PracticeType.DUAL);
                spinnerFCT.setVisible(false);
            }
        } catch (NullPointerException e) {
            choiceTipoPractica.setValue(PracticeType.FCT);
            spinnerDUAL.setVisible(false);
        }

        comboCurso.getItems().addAll(Grade.ASIR1, Grade.ASIR2, Grade.DAM1, Grade.DAM2, Grade.DAW1, Grade.DAW2);
        comboCurso.setValue(Sesion.getAlumn().getGrade());
        comboNombreEmpresa.getItems().addAll(new EnterpriseDAOImp(DBConnection.getConnection()).loadAll());
        comboNombreEmpresa.setValue(Sesion.getAlumn().getEnterprise());
        comboNombreEmpresa.getItems().add(null);
        try{
            String[] horasIniciales = Sesion.getAlumn().getHoursDUAL().split("/");
            if (choiceTipoPractica.getValue().equals(PracticeType.DUAL)) {
                spinnerDUAL.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 270, Integer.valueOf(horasIniciales[0]), 1));
            } else {
                spinnerFCT.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 270, Integer.valueOf(horasIniciales[0]), 1));
            }
        }catch(Exception e){
            String[] horasIniciales = Sesion.getAlumn().getHoursFCT().split("/");
            if (choiceTipoPractica.getValue().equals(PracticeType.DUAL)) {
                spinnerDUAL.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 270, Integer.valueOf(horasIniciales[0]), 1));
            } else {
                spinnerFCT.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 270, Integer.valueOf(horasIniciales[0]), 1));
            }
        }
    }

    @javafx.fxml.FXML
    public void guardar(ActionEvent actionEvent) {
        if(!textDNI.getText().isEmpty()&&!textNombre.getText().isEmpty()&&!textEmail.getText().isEmpty()
        &&!textApellido1.getText().isEmpty()&&!textApellido2.getText().isEmpty()&&!(dateCalender.getValue()==null) &&
        !textTelefono.getText().isEmpty()){
            try {
                Sesion.getAlumn().setDni(textDNI.getText());
                Sesion.getAlumn().setName(textNombre.getText());
                Sesion.getAlumn().setLastName(textApellido1.getText());
                Sesion.getAlumn().setLastName2(textApellido2.getText());
                Sesion.getAlumn().setEmail(textEmail.getText());
                Sesion.getAlumn().setBirthday(String.valueOf(dateCalender.getValue()));
                Sesion.getAlumn().setPhone(Integer.valueOf(textTelefono.getText()));
                Sesion.getAlumn().setGrade(comboCurso.getValue());
                if(comboNombreEmpresa.getValue()==null){
                    Sesion.getAlumn().setEnterpriseID(0);
                    Sesion.getAlumn().setEnterprise(null);
                }else{
                    Sesion.getAlumn().setEnterprise(comboNombreEmpresa.getValue());
                    Sesion.getAlumn().setEnterpriseID(comboNombreEmpresa.getValue().getId());
                }

                if(choiceTipoPractica.getValue()== PracticeType.DUAL){
                    Sesion.getAlumn().setHoursDUAL(spinnerDUAL.getValue()+"/270");
                    Sesion.getAlumn().setHoursFCT(null);
                    new AlumnDAOImp(DBConnection.getConnection()).updateHours(Sesion.getAlumn(),"horasFCT");
                }else{
                    Sesion.getAlumn().setHoursFCT(spinnerFCT.getValue()+"/270");
                    Sesion.getAlumn().setHoursDUAL(null);
                    new AlumnDAOImp(DBConnection.getConnection()).updateHours(Sesion.getAlumn(),"horasDual");
                }
                System.out.println(Sesion.getAlumn());
                Alumn alumn =(new AlumnDAOImp(DBConnection.getConnection()).update(Sesion.getAlumn()));
                App.loadFXML("teacherView-controller.fxml");
            } catch (InvalidDNI | NameWithNumber e) {
                throw new RuntimeException(e);
            } catch (LastNameWithNumber e) {
                throw new RuntimeException(e);
            }

        }else{
            Alert alerta=new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Error");
            alerta.setHeaderText("Porfavor comprueba de que los campos esten rellenos");
            alerta.showAndWait();
        }
            }

    @javafx.fxml.FXML
    public void cancelar(ActionEvent actionEvent) {

    }
}

