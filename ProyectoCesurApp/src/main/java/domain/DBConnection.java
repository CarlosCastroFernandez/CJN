package domain;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * La clase DBConnection maneja la conexión a la base de datos.
 */
public class DBConnection {
    /**
     * Conexión con la Base de Datos.
     */
    private static Connection connection;

    /**
     * Log para la conexión con la Base de Datos.
     */
    private static Logger logger;

    static{
        logger = Logger.getLogger(DBConnection.class.getName());
        Properties cfg = new Properties();
        try {
            //Cargando la configuración desde el archivo config.properties.
            InputStream is = DBConnection.class.getClassLoader().getResourceAsStream("config.properties");
            cfg.load(is);
            logger.info("Configuración cargada");
        } catch (IOException e) {
            logger.severe("Error procesando configuración" + e.getMessage());
            throw new RuntimeException(e);
        }

        try{
            connection = DriverManager.getConnection("jdbc:mysql://" + cfg.get("host") + ":" + cfg.get("port") + "/" + cfg.get("dbname"),(String)cfg.get("user"),(String)cfg.get("pass"));
            logger.info("Succesull connection to database");
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    /**
     * Obtiene la conexión a la base de datos.
     *
     * @return La conexión a la base de datos.
     */
    public static Connection getConnection(){
        return connection;
    }
}
