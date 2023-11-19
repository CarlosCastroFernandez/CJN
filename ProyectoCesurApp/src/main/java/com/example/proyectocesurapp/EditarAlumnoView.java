package com.example.proyectocesurapp;

import clase.Alumno;
import clase.Sesion;
import clase.Empresa;
import domain.AlumnoDAOImp;
import domain.DBConnection;
import enums.Curso;
import domain.EmpresaDAOImp;
import enums.TipoPractica;
import exception.ApellidoConNumero;
import exception.DNIInvalido;
import exception.NombreConNumero;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.StringConverter;


import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class EditarAlumnoView implements Initializable {
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
    private ComboBox<Curso> comboCurso;
    @javafx.fxml.FXML
    private ComboBox<Empresa> comboNombreEmpresa;
    @javafx.fxml.FXML
    private Spinner spinnerFCT;
    @javafx.fxml.FXML
    private Spinner spinnerDUAL;
    @javafx.fxml.FXML
    private TextField textApellido1;
    @javafx.fxml.FXML
    private TextField textApellido2;
    @javafx.fxml.FXML
    private ChoiceBox<TipoPractica> choiceTipoPractica;
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
        choiceTipoPractica.setConverter(new StringConverter<TipoPractica>() {
            @Override
            public String toString(TipoPractica tipoPractica) {
                if (tipoPractica != null) {
                    return String.valueOf(tipoPractica);
                } else {
                    return null;
                }

            }

            @Override
            public TipoPractica fromString(String s) {
                return null;
            }
        });
        try {
            comboCurso.setConverter(new StringConverter<Curso>() {
                @Override
                public String toString(Curso curso) {
                    if (curso != null) {
                        return String.valueOf(curso);
                    } else {
                        return null;
                    }

                }

                @Override
                public Curso fromString(String s) {
                    return null;
                }
            });

            comboNombreEmpresa.setConverter(new StringConverter<Empresa>() {
                @Override
                public String toString(Empresa empresa) {
                    if (empresa != null) {
                        return empresa.getName();
                    } else {
                        return "<<Sin Empresa>>";
                    }

                }

                @Override
                public Empresa fromString(String s) {
                    return null;
                }
            });
        } catch (Exception e) {

        }
        choiceTipoPractica.getItems().addAll(TipoPractica.DUAL,TipoPractica.FCT);
        choiceTipoPractica.getSelectionModel().selectedItemProperty().addListener((observableValue, tipoPractica, t1) -> {

                if (t1==TipoPractica.DUAL) {
                    choiceTipoPractica.setValue(TipoPractica.DUAL);
                    spinnerFCT.setVisible(false);
                    spinnerFCT.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 270, 0, 1));
                    spinnerDUAL.setVisible(true);

                } else{
                choiceTipoPractica.setValue(TipoPractica.FCT);
                spinnerDUAL.setVisible(false);
                spinnerDUAL.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 270, 0, 1));
                spinnerFCT.setVisible(true);
            }
        });

        try {
            if (Sesion.getAlumn().getHoursDUAL().contains("/270")) {
                choiceTipoPractica.setValue(TipoPractica.DUAL);
                spinnerFCT.setVisible(false);
            }
        } catch (NullPointerException e) {
            choiceTipoPractica.setValue(TipoPractica.FCT);
            spinnerDUAL.setVisible(false);
        }

        comboCurso.getItems().addAll(Curso.ASIR1, Curso.ASIR2, Curso.DAM1, Curso.DAM2, Curso.DAW1, Curso.DAW2);
        comboCurso.setValue(Sesion.getAlumn().getGrade());
        comboNombreEmpresa.getItems().addAll(new EmpresaDAOImp(DBConnection.getConnection()).loadAllEnterprise());
        comboNombreEmpresa.setValue(Sesion.getAlumn().getEnterprise());
        comboNombreEmpresa.getItems().add(null);
        try{
            String[] horasIniciales = Sesion.getAlumn().getHoursDUAL().split("/");
            if (choiceTipoPractica.getValue().equals(TipoPractica.DUAL)) {
                spinnerDUAL.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 270, Integer.valueOf(horasIniciales[0]), 1));
            } else {
                spinnerFCT.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 270, Integer.valueOf(horasIniciales[0]), 1));
            }
        }catch(Exception e){
            String[] horasIniciales = Sesion.getAlumn().getHoursFCT().split("/");
            if (choiceTipoPractica.getValue().equals(TipoPractica.DUAL)) {
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

                if(choiceTipoPractica.getValue()==TipoPractica.DUAL){
                    Sesion.getAlumn().setHoursDUAL(spinnerDUAL.getValue()+"/270");
                    Sesion.getAlumn().setHoursFCT(null);
                    new AlumnoDAOImp(DBConnection.getConnection()).updateHoras(Sesion.getAlumn(),"horasFCT");
                }else{
                    Sesion.getAlumn().setHoursFCT(spinnerFCT.getValue()+"/270");
                    Sesion.getAlumn().setHoursDUAL(null);
                    new AlumnoDAOImp(DBConnection.getConnection()).updateHoras(Sesion.getAlumn(),"horasDual");
                }
                System.out.println(Sesion.getAlumn());
                Alumno alumno=(new AlumnoDAOImp(DBConnection.getConnection()).update(Sesion.getAlumn()));
            } catch (DNIInvalido | NombreConNumero e) {
                throw new RuntimeException(e);
            } catch (ApellidoConNumero e) {
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

