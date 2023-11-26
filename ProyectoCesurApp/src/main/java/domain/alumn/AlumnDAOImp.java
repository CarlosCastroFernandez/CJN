package domain.alumn;

import classes.Alumn;
import domain.DBConnection;
import domain.dailyActivity.DailyActivityDAOImp;
import domain.enterprise.EnterpriseDAOImp;
import domain.teacher.TeacherDAOImp;
import enums.Grade;
import exception.*;
import lombok.extern.java.Log;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * AlumnDAOImp es una implementación de AlumnDAO que maneja la persistencia
 * de objetos Alumn en la Base de Datos.
 */
@Log
public class AlumnDAOImp implements AlumnDAO {

    /**
     * Conexión con la Base de Datos.
     */
    private static Connection connection;

    /**
     * Query que carga un alumno concreto de la Base de Datos usando su DNI.
     */
    private static final String QUERY_LOAD = "select * from alumno where dni = ?";

    /**
     * Query que carga todos los alumnos de la Base de Datos de un profesor concreto.
     */
    private static final String QUERY_LOAD_ALL ="select * from alumno where profesor=?";

    /**
     * Query que inserta en la Base de Datos a un alumno.
     */
    private static final String QUERY_INJECTION = "insert into alumno(dni, email, nombre, apellido1, apellido2, telefono, " +
            "contraseña, profesor, empresa, fechaNacimiento, horasDual, horasFCT, curso, observaciones)" + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

    /**
     * Query que hace un update sobre un alumno concreto usando su Id en la Base de Datos.
     */
    private static final String QUERY_UPDATE ="update alumno set dni=?,email=?,nombre=?,apellido1=?,apellido2=?," +
            "telefono=?,empresa=?,fechaNacimiento=?,horasDual=?,horasFCT=?,curso=? where id=?";

    /**
     * Query que borra a un alumno de la Base de Datos en función de su Id.
     */
    private static final String QUERY_DELETE ="delete from alumno where id=?";

    /**
     * Almacena claves y valores de tipo String que definen diferentes queries en función de la opción elegida.
     */
    private static final HashMap<String,String> DECISION;
    static {
        DECISION =new HashMap<>();
        DECISION.put("horasDual","update alumno set horasDual=? where id=?");
        DECISION.put("horasFCT","update alumno set horasFCT=? where id=?");
    }

    /**
     * Constructor que recibe una conexión para la clase AlumnDAOImp.
     *
     * @param c Conexión a la Base de Datos
     */
    public AlumnDAOImp(Connection c){ connection = c;}

    /**
     * Logea un estudiante (Alumn) basado en el DNI y la contraseña proporcionados.
     *
     * @param dni         DNI del estudiante
     * @param password Contraseña del estudiante
     * @return Alumn cargado desde la base de datos
     * @throws IncorrectPassword Si la contraseña proporcionada es incorrecta
     * @throws NonExistentUser   Si el estudiante no existe
     */
    @Override
    public Alumn login(String dni, String password) throws IncorrectPassword, NonExistentUser {
        Alumn outPut = null;
        try {
            //Preparación y ejecución de la consulta SQL utilizando la conexión proporcionada.
            PreparedStatement preparedStatement = connection.prepareStatement(QUERY_LOAD);
            preparedStatement.setString(1,dni);
            ResultSet resultSet = preparedStatement.executeQuery();

            //Comprueba si existen resultados.
            if(resultSet.next()){

                //Si se encuentra un resultado, se crea un objeto Alumn con los datos obtenidos de la consulta.
                outPut = new Alumn( resultSet.getInt("id"), resultSet.getString("nombre"), resultSet.getString("apellido1"), resultSet.getNString("apellido2"),
                        resultSet.getString("contraseña"),resultSet.getString("email"), resultSet.getString("dni"),
                        resultSet.getInt("telefono"),resultSet.getString("horasDual"),resultSet.getString("horasFCT"),
                        resultSet.getInt("profesor"),String.valueOf(resultSet.getDate("fechaNacimiento")), resultSet.getInt("empresa"),
                        Grade.valueOf(resultSet.getString("curso")) ,resultSet.getString("observaciones"));

                //Se carga el profesor asociado al alumno.
                outPut.setTeacher(new TeacherDAOImp(DBConnection.getConnection()).loadTeacherById(resultSet.getInt("profesor")));

                //Comprueba si la contraseña proporcionada no coincide con la contraseña del alumno.
                if(!password.equals(outPut.getPassword())){
                    throw new IncorrectPassword("Contraseña Incorrecta");
                }
            }else{
                throw new NonExistentUser("No Existe El Usuario");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (NameWithNumber e) {
            throw new RuntimeException(e);
        } catch (InvalidDNI e) {
            throw new RuntimeException(e);
        } catch (LastNameWithNumber e) {
            throw new RuntimeException(e);
        }
        return outPut;
    }

    /**
     * Carga todos los alumnos asociados a un profesor.
     *
     * @param id ID del profesor
     * @return ArrayList de Alumn cargados desde la base de datos
     */
    @Override
    public ArrayList<Alumn> loadAll(Integer id) {
        ArrayList<Alumn> outPut = new ArrayList<Alumn>();
        try {
            //Preparación y ejecución de la consulta SQL utilizando la conexión proporcionada.
            PreparedStatement preparedStatement = connection.prepareStatement(QUERY_LOAD_ALL);
            preparedStatement.setInt(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();

            //Da formato a la fecha.
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

            //Itera a través de los resultados obtenidos en la consulta.
            while(resultSet.next()){

                //Crea un objeto Alumn con los datos obtenidos de la consulta.
                Alumn alumn =new Alumn( resultSet.getInt("id"), resultSet.getString("nombre"), resultSet.getString("apellido1"), resultSet.getString("apellido2"),
                        resultSet.getString("contraseña"),resultSet.getString("email"), resultSet.getString("dni"),
                        resultSet.getInt("telefono"),resultSet.getString("horasDual"),resultSet.getString("horasFCT"),
                        resultSet.getInt("profesor"),simpleDateFormat.format(resultSet.getDate("fechaNacimiento")), resultSet.getInt("empresa"),
                        Grade.valueOf(resultSet.getString("curso")) ,resultSet.getString("observaciones"));

                //Verifica si el alumno tiene un Id de empresa y establece la asociada.
                if(alumn.getEnterpriseID() != 0){
                    alumn.setEnterprise((new EnterpriseDAOImp(DBConnection.getConnection()).load(alumn.getEnterpriseID())));
                }

                //Crea un objeto DailyActivityDAOImp para cargar las actividades diarias del estudiante.
                DailyActivityDAOImp dailyActivityDAOImp = new DailyActivityDAOImp(DBConnection.getConnection());
                alumn.setActivity(dailyActivityDAOImp.loadall(alumn.getId()));

                //Establece el profesor asociado al alumno.
                alumn.setTeacher(new TeacherDAOImp(DBConnection.getConnection()).loadTeacherById(id));

                //Agrega el alumno a la salida.
                outPut.add(alumn);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (NameWithNumber e) {
            throw new RuntimeException(e);
        } catch (InvalidDNI e) {
            throw new RuntimeException(e);
        } catch (LastNameWithNumber e) {
            throw new RuntimeException(e);
        } catch (NonExistentUser e) {
            throw new RuntimeException(e);
        }
        return outPut;
    }

    /**
     * Guarda una actividad de un alumno en la Base de Datos.
     *
     * @param a Actividad a guardar
     * @return Alumn con la actividad guardada
     */
    @Override
    public Alumn saveActivity(Alumn a) {
        //Do nothing.
        return null;
    }

    /**
     * Actualiza los datos de un estudiante en la Base de Datos.
     *
     * @param a Alumn con los datos actualizados
     * @return Alumn con los datos actualizados
     */
    @Override
    public Alumn update(Alumn a) {
        Alumn alumn = a;
        try {
            //Preparación de la consulta SQL utilizando la conexión proporcionada.
            PreparedStatement preparedStatement = connection.prepareStatement(QUERY_UPDATE);

            //Establecimiento de los parámetros en la sentencia SQL con los datos del alumno proporcionado.
            preparedStatement.setString(1,a.getDni());
            preparedStatement.setString(2,a.getEmail());
            preparedStatement.setString(3,a.getName());
            preparedStatement.setString(4,a.getLastName());
            preparedStatement.setString(5,a.getLastName2());
            preparedStatement.setInt(6,a.getPhone());
            preparedStatement.setInt(7,a.getEnterpriseID());
            preparedStatement.setString(8,a.getBirthday());
            preparedStatement.setString(9,a.getHoursDUAL());
            preparedStatement.setString(10,a.getHoursFCT());
            preparedStatement.setString(11,String.valueOf(a.getGrade()));
            preparedStatement.setInt(12,a.getId());

            //Ejecuta la sentencia SQL de actualización.
            Integer rows = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return alumn;
    }

    /**
     * Elimina una actividad específica de un alumno en la Base de Datos.
     *
     * @param a    Estudiante del cual se eliminará la actividad
     * @param attr Atributo de la actividad a eliminar ('horasDual' o 'horasFCT')
     */
    @Override
    public void removeActivity(Alumn a, String attr) {
        try {
            //Preparación de la consulta SQL utilizando la conexión proporcionada.
            PreparedStatement preparedStatement = connection.prepareStatement(DECISION.get(attr));
            preparedStatement.setInt(1, a.getId());

            //Ejecuta la sentencia SQL de eliminación de actividad diaria.
            Integer rows = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Actualiza las horas de prácticas ('horasDual' o 'horasFCT') de un alumno en la Base de Datos.
     *
     * @param a    Alumno al que se actualizarán las horas de prácticas
     * @param attr Atributo ('horasDual' o 'horasFCT') que se actualizará
     */
    public void updateHours(Alumn a, String attr) {
        try {
            //Preparación de la consulta SQL utilizando la conexión proporcionada.
            PreparedStatement preparedStatement = connection.prepareStatement(DECISION.get(attr));

            //Verifica el atributo para establecer el valor correspondiente en la sentencia SQL entre horas de Dual o FCT.
            if(attr.equals("horasDual")){
                preparedStatement.setString(1,"");
                preparedStatement.setInt(2,a.getId());
            }else {
                preparedStatement.setString(1,"");
                preparedStatement.setInt(2,a.getId());
            }

            //Ejecuta la sentencia SQL de actualización de horas.
            Integer rows = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Realiza la inserción de un nuevo alumno en la Base de Datos.
     *
     * @param a Estudiante a insertar
     * @return Alumn insertado con su ID actualizado
     */
    @Override
    public Alumn injection(Alumn a) {
        Alumn alumn = null;
        try {
            //Preparación de la consulta SQL utilizando la conexión proporcionada.
            PreparedStatement preparedStatement = connection.prepareStatement(QUERY_INJECTION, Statement.RETURN_GENERATED_KEYS);

            //Establecimiento de los parámetros en la sentencia SQL con los datos del alumno proporcionado.
            preparedStatement.setString(1, a.getDni());
            preparedStatement.setString(2, a.getEmail());
            preparedStatement.setString(3, a.getName());
            preparedStatement.setString(4, a.getLastName());
            preparedStatement.setString(5, a.getLastName2());
            preparedStatement.setInt(6, a.getPhone());
            preparedStatement.setString(7, a.getPassword());
            preparedStatement.setInt(8, a.getTeacherID());
            preparedStatement.setInt(9, a.getEnterpriseID());
            preparedStatement.setString(10, a.getBirthday());
            preparedStatement.setString(11, a.getHoursDUAL());
            preparedStatement.setString(12, a.getHoursFCT());
            preparedStatement.setString(13, a.getGrade().name());
            preparedStatement.setString(14, a.getObservations());

            //Ejecuta la sentencia SQL de inserción.
            Integer rows = preparedStatement.executeUpdate();

            //Si la inserción se realiza correctamente.
            if(rows == 1){
                //Obtiene las claves generadas por la inserción.
                ResultSet resultSet = preparedStatement.getGeneratedKeys();
                resultSet.next();

                //Asigna el alumno proporcionado al alumno que se va a devolver.
                alumn = a;

                //Establece el Id generado para el alumno.
                alumn.setId(resultSet.getInt(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return alumn;
    }

    /**
     * Elimina un alumno de la Base de Datos.
     *
     * @param a Estudiante a eliminar
     */
    public void delete(Alumn a){
        try {
            //Preparación de la consulta SQL utilizando la conexión proporcionada.
            PreparedStatement preparedStatement = connection.prepareStatement(QUERY_DELETE);
            preparedStatement.setInt(1, a.getId());

            //Ejecuta la sentencia SQL de eliminación de un alumno.
            Integer rows = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
