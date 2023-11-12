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
import java.text.SimpleDateFormat;
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
        textNombre.setText(Sesion.getAlumno().getNombre());
        textApellido1.setText(Sesion.getAlumno().getApellido1());
        textApellido2.setText(Sesion.getAlumno().getApellido2());
        textTelefono.setText("" + Sesion.getAlumno().getTelefono());
        textDNI.setText(Sesion.getAlumno().getDni());
        textEmail.setText(Sesion.getAlumno().getCorreo());
        DateTimeFormatter fecha=DateTimeFormatter.ofPattern("yyy-MM-dd");
        String fechaAlumno=Sesion.getAlumno().getFechaNacimiento();
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
                        return empresa.getNombre();
                    } else {
                        return "";
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
            if (Sesion.getAlumno().getHorasDUAL().contains("/270")) {
                choiceTipoPractica.setValue(TipoPractica.DUAL);
                spinnerFCT.setVisible(false);
            }
        } catch (NullPointerException e) {
            choiceTipoPractica.setValue(TipoPractica.FCT);
            spinnerDUAL.setVisible(false);
        }

        comboCurso.getItems().addAll(Curso.ASIR1, Curso.ASIR2, Curso.DAM1, Curso.DAM2, Curso.DAW1, Curso.DAW2);
        comboCurso.setValue(Sesion.getAlumno().getCurso());
        comboNombreEmpresa.getItems().addAll(new EmpresaDAOImp(DBConnection.getConnection()).loadAllEnterprise());
        comboNombreEmpresa.setValue(Sesion.getAlumno().getEmpresa());
        try{
            String[] horasIniciales = Sesion.getAlumno().getHorasDUAL().split("/");
            if (choiceTipoPractica.getValue().equals(TipoPractica.DUAL)) {
                spinnerDUAL.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 270, Integer.valueOf(horasIniciales[0]), 1));
            } else {
                spinnerFCT.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 270, Integer.valueOf(horasIniciales[0]), 1));
            }
        }catch(Exception e){
            String[] horasIniciales = Sesion.getAlumno().getHorasFCT().split("/");
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
                Sesion.getAlumno().setDni(textDNI.getText());
                Sesion.getAlumno().setNombre(textNombre.getText());
                Sesion.getAlumno().setApellido1(textApellido1.getText());
                Sesion.getAlumno().setApellido2(textApellido2.getText());
                Sesion.getAlumno().setCorreo(textEmail.getText());
                Sesion.getAlumno().setFechaNacimiento(String.valueOf(dateCalender.getValue()));
                Sesion.getAlumno().setTelefono(Integer.valueOf(textTelefono.getText()));
                Sesion.getAlumno().setCurso(comboCurso.getValue());
                Sesion.getAlumno().setEmpresa(comboNombreEmpresa.getValue());
                if(choiceTipoPractica.getValue()==TipoPractica.DUAL){
                    Sesion.getAlumno().setHorasDUAL(spinnerDUAL.getValue()+"/270");
                    Sesion.getAlumno().setHorasFCT(null);
                    new AlumnoDAOImp(DBConnection.getConnection()).updateHoras(Sesion.getAlumno(),"horasFCT");
                }else{
                    Sesion.getAlumno().setHorasFCT(spinnerFCT.getValue()+"/270");
                    Sesion.getAlumno().setHorasDUAL(null);
                    new AlumnoDAOImp(DBConnection.getConnection()).updateHoras(Sesion.getAlumno(),"horasDual");
                }

                Alumno alumno=(new AlumnoDAOImp(DBConnection.getConnection()).update(Sesion.getAlumno()));
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

