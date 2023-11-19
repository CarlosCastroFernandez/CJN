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
    private static Connection connection;
    private static Logger logger;

    static{
        logger = Logger.getLogger(DBConnection.class.getName());
        String url ;
        String user ;
        String password ;
        Properties cfg = new Properties();
        try {
            // Cargando la configuración desde el archivo config.properties.
            InputStream is = DBConnection.class.getClassLoader().getResourceAsStream("config.properties");
            cfg.load(is);

            // Estableciendo la conexión con la base de datos utilizando la configuración cargada.
            logger.info("Configuración cargada");
            //url = "jdbc:mysql://" + cfg.get("host") + ":" + cfg.get("port") + "/" + cfg.get("dbname");
            //user = (String)cfg.get("user");
            //password = (String)cfg.get("pass");
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
