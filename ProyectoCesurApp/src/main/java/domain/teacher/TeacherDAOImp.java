package domain.teacher;

import classes.Teacher;
import exception.*;
import lombok.extern.java.Log;

import java.sql.*;

/**
 * TeacherDAOImp es una implementación de TeacherDAO que maneja la persistencia
 * de objetos Teacher en la Base de Datos.
 */
@Log
public class TeacherDAOImp implements TeacherDAO {

    /**
     * Conexión con la Base de Datos.
     */
    private static Connection connection;

    /**
     * Query que carga un profesor concreto de la Base de Datos usando su DNI.
     */
    private static final String QUERY_LOAD = "select * from profesor where dni=?";

    /**
     * Query que carga un profesor concreto de la Base de Datos usando su Id.
     */
    private static final String QUERY_LOAD_BY_ID ="select * from profesor where id=?";

    /**
     * Query que inserta en la Base de Datos a un profesor.
     */
    private static final String QUERY_INJECTION ="insert into profesor(dni,nombre,email,apellido1,apellido2,contraseña,telefono)\n" +
            "VALUE (?,?,?,?,?,?,?)";

    /**
     * Constructor que recibe una conexión para la clase TeacherDAOImp.
     *
     * @param c Conexión a la Base de Datos
     */
    public TeacherDAOImp(Connection c){
        connection = c;
    }

    /**
     * Realiza el inicio de sesión para un profesor.
     *
     * @param dni         DNI del maestro que intenta iniciar sesión
     * @param contrasenha Contraseña proporcionada para el inicio de sesión
     * @return Objeto Teacher correspondiente al maestro que ha iniciado sesión
     * @throws NonExistentUser Si el usuario no existe en la Base de Datos
     * @throws IncorrectPassword Si la contraseña proporcionada es incorrecta
     */
    @Override
    public Teacher login(String dni, String contrasenha) throws NonExistentUser, IncorrectPassword {

        Teacher teacher =null;
        try {
            //Preparación y ejecución de la consulta SQL utilizando la conexión proporcionada.
            PreparedStatement preparedStatement = connection.prepareStatement(QUERY_LOAD);
            preparedStatement.setString(1,dni);
            ResultSet resultSet = preparedStatement.executeQuery();

            //Comprueba si existen resultados.
            if(resultSet.next()){

                //Si se encuentra un resultado, se crea un objeto Alumn con los datos obtenidos de la consulta.
                teacher =new Teacher(dni, contrasenha);
                teacher.setId(resultSet.getInt("id"));
                teacher.setDni(dni);
                teacher.setName(resultSet.getString("nombre"));
                teacher.setPassword(resultSet.getString("contraseña"));

                //Comprueba si la contraseña proporcionada no coincide con la contraseña del alumno.
                if(!contrasenha.equals(teacher.getPassword())){
                    throw new IncorrectPassword("Contraseña Incorrecta");
                }

                teacher.setLastName(resultSet.getString("apellido1"));
                teacher.setLastName2(resultSet.getString("apellido2"));
                teacher.setEmail(resultSet.getString("email"));
                teacher.setPhone(resultSet.getInt("telefono"));
            }else{
                log.warning("Usuario no existe");
                throw new NonExistentUser("Usuario no existe");
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
        return teacher;
    }

    /**
     * Carga un maestro basado en su ID.
     *
     * @param id ID del maestro que se desea cargar
     * @return Objeto Teacher correspondiente al maestro cargado
     * @throws NonExistentUser Si no se encuentra el usuario en la Base de Datos
     */
    public Teacher loadTeacherById(Integer id) throws NonExistentUser {
        Teacher teacher =null;
        try {
            //Preparación y ejecución de la consulta SQL utilizando la conexión proporcionada.
            PreparedStatement preparedStatement = connection.prepareStatement(QUERY_LOAD_BY_ID);
            preparedStatement.setInt(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();

            //Comprueba si existen resultados.
            if(resultSet.next()){

                //Creación de un objeto Teacher con los valores obtenidos de la Base de Datos.
                teacher = new Teacher();
                teacher =new Teacher();
                teacher.setId(resultSet.getInt("id"));
                teacher.setDni(resultSet.getString("dni"));
                teacher.setName(resultSet.getString("nombre"));
                teacher.setPassword(resultSet.getString("contraseña"));
                teacher.setLastName(resultSet.getString("apellido1"));
                teacher.setLastName2(resultSet.getString("apellido2"));
                teacher.setEmail(resultSet.getString("email"));
                teacher.setPhone(resultSet.getInt("telefono"));
            }else{
                log.warning("Usuario no existe");
                throw new NonExistentUser("Usuario no existe");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (InvalidDNI e) {
            throw new RuntimeException(e);
        } catch (NameWithNumber e) {
            throw new RuntimeException(e);
        } catch (LastNameWithNumber e) {
            throw new RuntimeException(e);
        }
        return teacher;
    }

    /**
     * Realiza la inserción de un nuevo profesor en la Base de Datos.
     *
     * @param teacher Profesor a insertar
     * @return Teacher insertado con su ID actualizado
     */
    @Override
    public Teacher injection(Teacher teacher) {
        Teacher teacherResult = new Teacher();
        try {
            //Preparación de la consulta SQL utilizando la conexión proporcionada.
            PreparedStatement preparedStatement= connection.prepareStatement(QUERY_INJECTION, Statement.RETURN_GENERATED_KEYS);

            //Establecimiento de los parámetros en la sentencia SQL con los datos del profesor proporcionado.
            preparedStatement.setString(1, teacher.getDni());
            preparedStatement.setString(2, teacher.getName());
            preparedStatement.setString(3, teacher.getEmail());
            preparedStatement.setString(4, teacher.getLastName());
            preparedStatement.setString(5, teacher.getLastName2());
            preparedStatement.setString(6, teacher.getPassword());
            preparedStatement.setInt(7, teacher.getPhone());

            //Ejecuta la sentencia SQL de inserción.
            Integer rows = preparedStatement.executeUpdate();

            //Si la inserción se realiza correctamente.
            if(rows == 1){
                //Obtiene las claves generadas por la inserción.
                ResultSet resultSet = preparedStatement.getGeneratedKeys();
                resultSet.next();

                //Asigna el alumno proporcionado al alumno que se va a devolver.
                teacherResult = teacher;

                //Establece el Id generado para el alumno.
                teacherResult.setId(resultSet.getInt(1));

            }
            preparedStatement.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return teacherResult;
    }
}

