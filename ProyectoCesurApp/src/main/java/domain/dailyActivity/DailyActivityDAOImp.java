package domain.dailyActivity;

import classes.DailyActivity;
import classes.Sesion;
import enums.PracticeType;
import lombok.extern.java.Log;

import java.sql.*;
import java.util.ArrayList;

/**
 * DailyActivityDAOImp es una implementación de DailyActivityDAO que maneja la persistencia
 * de objetos DailyActivity en la Base de Datos.
 */
@Log
public class DailyActivityDAOImp implements DailyActivityDAO {

    /**
     * Conexión con la Base de Datos.
     */
    private static Connection connection;

    /**
     * Query que carga una actividad concreta de la Base de Datos usando el Id del alumno asociado.
     */
    private static final String QUERY_LOAD = "select * from actividadDiaria where alumnoId = ?";

    /**
     * Query que inserta en la Base de Datos una actividad.
     */
    private static final String QUERY_INJECTION = "insert into actividadDiaria(nombre, totalHoras, observaciones, tipoPractica, fecha, alumnoId)\n"+"VALUE (?, ?, ?, ?, ?, ?)";

    /**
     * Query que hace un update sobre una actividad concreta en la Base de Datos.
     */
    private static final String QUERY_UPDATE = "update actividadDiaria set nombre=?, totalHoras=?, observaciones=?, tipoPractica=?, fecha=?, alumnoId=?";

    /**
     * Query que borra a una actividad de la Base de Datos en función de su Id.
     */
    private static final String QUERY_DELETE = "delete from actividadDiaria where id=?";

    /**
     * Constructor que recibe una conexión para la clase DailyActivityDAOImp.
     *
     * @param c Conexión a la base de datos
     */
   public DailyActivityDAOImp(Connection c){
       connection = c;
   }

    /**
     * Carga todas las actividades diarias de un alumno basado en su ID.
     *
     * @param id ID del alumno para cargar sus actividades diarias
     * @return Lista de actividades diarias del alumno
     */
    @Override
    public ArrayList<DailyActivity> loadall(Integer id) {
        ArrayList<DailyActivity> dailyActivity = new ArrayList<>();
        try {
            //Preparación y ejecución de la consulta SQL utilizando la conexión proporcionada.
            PreparedStatement preparedStatement = connection.prepareStatement(QUERY_LOAD);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            //Itera a través de los resultados obtenidos en la consulta.
            while (resultSet.next()) {

                //Creación de un objeto DailyActivity para almacenar cada registro de actividad diaria.
                DailyActivity dailyActivityResult = new DailyActivity();

                //Asignación de los valores obtenidos de la Base de Datos al objeto DailyActivity.
                dailyActivityResult.setId(resultSet.getInt("id"));
                dailyActivityResult.setIdAlumn(resultSet.getInt("alumnoId"));
                dailyActivityResult.setDate(String.valueOf(resultSet.getDate("fecha")));
                dailyActivityResult.setPracticeType(PracticeType.valueOf(resultSet.getString("tipoPractica")));
                dailyActivityResult.setTotaHours(resultSet.getInt("totalHoras"));
                dailyActivityResult.setTaskName(resultSet.getString("nombre"));
                dailyActivityResult.setObservations(resultSet.getString("observaciones"));

                //Agregación del objeto creado con sus valores a la lista de actividades diarias.
                dailyActivity.add(dailyActivityResult);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return dailyActivity;
    }

    /**
     * Inserta una nueva actividad diaria en la base de datos.
     *
     * @param dailyActivity Actividad diaria que se va a insertar en la base de datos
     * @return Actividad diaria con su ID actualizado después de la inserción
     */
    @Override
    public DailyActivity injection(DailyActivity dailyActivity) {
       DailyActivity dailyActivityResult = null;
       try {
           //Preparación de la consulta SQL utilizando la conexión proporcionada.
           PreparedStatement preparedStatement = connection.prepareStatement(QUERY_INJECTION, Statement.RETURN_GENERATED_KEYS);

           //Establecimiento de los parámetros en la sentencia SQL con los datos de la actividad proporcionada.
           preparedStatement.setString(1, dailyActivity.getTaskName());
           preparedStatement.setInt(2, dailyActivity.getTotalHours());
           preparedStatement.setString(3, dailyActivity.getObservations());
           preparedStatement.setString(4, String.valueOf(dailyActivity.getPracticeType()));
           preparedStatement.setString(5, dailyActivity.getDate());
           preparedStatement.setInt(6,Sesion.getAlumn().getId());
           dailyActivityResult = dailyActivity;

           //Ejecuta la sentencia SQL de inserción.
           Integer rows = preparedStatement.executeUpdate();

           //Si la inserción se realiza correctamente.
           if(rows == 1){

               //Obtiene las claves generadas por la inserción.
               ResultSet resultSet = preparedStatement.getGeneratedKeys();
               resultSet.next();

               //Establece el Id generado para el alumno.
               Integer number = resultSet.getInt(1);
               dailyActivityResult.setId(number);
           }
           preparedStatement.close();

       } catch (SQLException e){
           throw new RuntimeException(e);
       }
       return dailyActivityResult;
    }

    /**
     * Actualiza una actividad diaria en la base de datos.
     *
     * @param dailyActivity Actividad diaria que se va a actualizar en la base de datos
     * @return Actividad diaria actualizada
     */
    @Override
    public DailyActivity update(DailyActivity dailyActivity) {
        DailyActivity dailyActivityResult = dailyActivity;
        try{
            //Preparación de la consulta SQL utilizando la conexión proporcionada.
            PreparedStatement preparedStatement = connection.prepareStatement(QUERY_UPDATE);

            //Establecimiento de los parámetros en la sentencia SQL con los datos de la actividad proporcionada.
            preparedStatement.setString(1, dailyActivityResult.getTaskName());
            preparedStatement.setInt(2, dailyActivityResult.getTotalHours());
            preparedStatement.setString(3, dailyActivityResult.getObservations());
            preparedStatement.setString(4, String.valueOf(dailyActivityResult.getPracticeType()));
            preparedStatement.setString(5, dailyActivityResult.getDate());
            preparedStatement.setInt(6, Sesion.getAlumn().getId());

            //Ejecuta la sentencia SQL de actualización.
            Integer rows = preparedStatement.executeUpdate();
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
        return dailyActivityResult;
    }

    /**
     * Elimina una actividad diaria de la base de datos.
     *
     * @param acD Actividad diaria que se va a eliminar de la base de datos
     */
    public void delete(DailyActivity acD){
        try {
            //Preparación de la consulta SQL utilizando la conexión proporcionada.
            PreparedStatement preparedStatement = connection.prepareStatement(QUERY_DELETE);
            preparedStatement.setInt(1,acD.getId());

            //Ejecuta la sentencia SQL de eliminación.
            Integer rows = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
