package com.example.proyectocesurapp;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class VentanaAlumno implements Initializable {
    @javafx.fxml.FXML
    private Button btnLogOut;
    @javafx.fxml.FXML
    private ImageView ivUser;
    @javafx.fxml.FXML
    private DatePicker dpDate;
    @javafx.fxml.FXML
    private ComboBox tpCombo;
    @javafx.fxml.FXML
    private TextField fHours;
    @javafx.fxml.FXML
    private TextArea taActivityRealised;
    @javafx.fxml.FXML
    private TextArea taObservations;
    @javafx.fxml.FXML
    private Button btnAdd;
    @javafx.fxml.FXML
    private TableView tvUser;
    @javafx.fxml.FXML
    private TableColumn cDate;
    @javafx.fxml.FXML
    private TableColumn cTPractice;
    @javafx.fxml.FXML
    private TableColumn cTHours;
    @javafx.fxml.FXML
    private TableColumn cActivityRealised;
    @javafx.fxml.FXML
    private TableColumn cObservations;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @javafx.fxml.FXML
    public void activityInsert(ActionEvent actionEvent) {
    }
}
