module com.example.proyectocesurapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires lombok;

    opens com.example.proyectocesurapp.imagenes;
    opens com.example.proyectocesurapp to javafx.fxml;
    exports com.example.proyectocesurapp;
}