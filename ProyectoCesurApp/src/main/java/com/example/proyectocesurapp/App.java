package com.example.proyectocesurapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {
    private static Stage myStage;
    @Override
    public void start(Stage stage) throws IOException {
        myStage=stage;
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("login-controller.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1125, 700);
        Image icono=new Image(App.class.getResource("imagenes/IconCesur.png").toExternalForm());
        stage.getIcons().add(icono);
        stage.setTitle("Cesur");
        stage.setScene(scene);
        stage.show();
    }
    public static void loadFXML(String ruta){
        if(ruta.equals("alumnMyEnterprise-controller.fxml")||ruta.equals("alumnMyTeacher-controller.fxml")){
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(ruta));
                Scene scene = new Scene(fxmlLoader.load(), 600, 770);
                Image icono=new Image(App.class.getResource("imagenes/IconCesur.png").toExternalForm());
                myStage.getIcons().add(icono);
                myStage.setResizable(false);
                myStage.setTitle("Cesur");
                myStage.setScene(scene);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }else{
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(ruta));
                Scene scene = new Scene(fxmlLoader.load(), 1137, 770);
                Image icono=new Image(App.class.getResource("imagenes/IconCesur.png").toExternalForm());
                myStage.getIcons().add(icono);
                myStage.setResizable(true);
                myStage.setTitle("Cesur");
                myStage.setScene(scene);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        }




    public static void main(String[] args) {
        launch();
    }
}