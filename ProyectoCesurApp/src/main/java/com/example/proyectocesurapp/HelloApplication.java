package com.example.proyectocesurapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    private static Stage myStage;
    @Override
    public void start(Stage stage) throws IOException {
        myStage=stage;
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("registro.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 500, 400);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }
    public static void loadFXML(String ruta){

        try {

            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(ruta));
            Scene scene = new Scene(fxmlLoader.load(), 1125, 600);
            myStage.setScene(scene);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static void nuevaVentana(String ruta){

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(ruta));
            Scene scene = new Scene(fxmlLoader.load(), 1125, 600);
            Stage ventana=new Stage();
            ventana.setScene(scene);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        launch();
    }
}