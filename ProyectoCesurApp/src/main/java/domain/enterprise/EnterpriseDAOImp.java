package domain.enterprise;

import classes.Enterprise;
import lombok.extern.java.Log;

import java.sql.*;
import java.util.ArrayList;

/**
 * EnterpriseDAOImp es una implementación de EnterpriseDAO que maneja la persistencia
 * de objetos Enterpise en la Base de Datos.
 */
@Log
public class EnterpriseDAOImp implements EnterpriseDAO {

    /**
     * Conexión con la Base de Datos.
     */
    private static Connection connection;

    /**
     * Query que carga una empresa concreta de la Base de Datos usando su Id.
     */
    private static final String QUERY_LOAD = "select * from empresa where id = ?";

    /**
     * Query que carga todas las empresas de la Base de Datos.
     */
    private static final String QUERY_LOAD_ALL = "select * from empresa";

    /**
     * Query que hace un update sobre una empresa concreta usando su Id en la Base de Datos.
     */
    private static final String QUERY_UPDATE = "update empresa set email=?, nombre=?, telefono=?, responsable=?, observaciones=? where id=?";

    /**
     * Query que inserta en la Base de Datos a una empresa.
     */
    private static final String QUERY_INJECTION = "insert into empresa(email, nombre, telefono, responsable, observaciones)" + "values (?,?,?,?,?)";

    /**
     * Query que borra a una empresa de la Base de Datos en función de su Id.
     */
    private static final String QUERY_DELETE = "delete from empresa where id = ?";

    /**
     * Constructor que recibe una conexión para la clase EnterpriseDAOImp.
     *
     * @param c Conexión a la Base de Datos
     */
    public EnterpriseDAOImp(Connection c) {
        connection = c;
    }

    /**
     * Carga una empresa basada en su Id desde la base de Datos.
     *
     * @param id ID de la empresa que se va a cargar
     * @return Objeto Enterprise correspondiente a la empresa cargada desde la Base de Datos
     */
    @Override
    public Enterprise load(Integer id){
        Enterprise enterprise = null;
        try {
            //Preparación y ejecución de la consulta SQL utilizando la conexión proporcionada.
            PreparedStatement preparedStatement = connection.prepareStatement(QUERY_LOAD);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            //Comprueba si existen resultados.
            if (resultSet.next()){
                //Creación de un objeto Enterprise con los valores obtenidos de la Base de Datos.
                enterprise = new Enterprise(resultSet.getInt("id"),
                        resultSet.getString("email"),
                        resultSet.getString("nombre"),
                        resultSet.getInt("telefono"),
                        resultSet.getString("responsable"),
                        resultSet.getString("observaciones"));
            } else {
                log.warning("La empresa no existe");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return enterprise;
    }

    /**
     * Carga todas las empresas desde la Base de Datos.
     *
     * @return Lista de objetos Enterprise que representan todas las empresas cargadas desde la Base de Datos
     */
    @Override
    public ArrayList<Enterprise> loadAll(){
        Enterprise enterprise;
        ArrayList<Enterprise> outPut = new ArrayList<>();
        try{
            //Preparación y ejecución de la consulta SQL utilizando la conexión proporcionada.
            PreparedStatement pst = connection.prepareStatement(QUERY_LOAD_ALL);
            ResultSet rs = pst.executeQuery();

            //Itera a través de los resultados obtenidos en la consulta.
            while (rs.next()){
                //Creación de un objeto Enterprise con los valores obtenidos de la Base de Datos y agregándolo a la lista.
                enterprise = new Enterprise(rs.getInt("id"),
                        rs.getString("email"),
                        rs.getString("nombre"),
                        rs.getInt("telefono"),
                        rs.getString("responsable"),
                        rs.getString("observaciones"));

                //Agregación del objeto Enterprise a la lista de todas las empresas.
                outPut.add(enterprise);
            }

        } catch (SQLException e){
            throw new RuntimeException(e);
        }
        return outPut;
    }

    /**
     * Actualiza los datos de una empresa en la Base de Datos.
     *
     * @param enterprise Empresa con los datos actualizados que se van a actualizar en la Base de Datos
     * @return Objeto Enterprise con los datos actualizados
     */
    @Override
    public Enterprise update(Enterprise enterprise) {
        Enterprise enterpriseResult = enterprise;
        try{
            //Preparación de la consulta SQL utilizando la conexión proporcionada.
            PreparedStatement preparedStatement = connection.prepareStatement(QUERY_UPDATE);

            //Establecimiento de los parámetros en la sentencia SQL con los datos del alumno proporcionado.
            preparedStatement.setString(1, enterpriseResult.getEmail());
            preparedStatement.setString(2, enterpriseResult.getName());
            preparedStatement.setInt(3, enterpriseResult.getPhone());
            preparedStatement.setString(4, enterpriseResult.getBoss());
            preparedStatement.setString(5, enterpriseResult.getObservations());
            preparedStatement.setInt(6, enterpriseResult.getId());

            //Ejecuta la sentencia SQL de actualización.
            Integer rows = preparedStatement.executeUpdate();
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
        return enterpriseResult;
    }

    /**
     * Realiza la inserción de una nueva empresa en la Base de Datos.
     *
     * @param enterprise Empresa a insertar
     * @return Enterprise insertada con su ID actualizado
     */
    @Override
    public Enterprise injection(Enterprise enterprise) {
        Enterprise enterpriseResult = null;
        try {
            //Preparación de la consulta SQL utilizando la conexión proporcionada.
            PreparedStatement preparedStatement = connection.prepareStatement(QUERY_INJECTION, Statement.RETURN_GENERATED_KEYS);

            //Establecimiento de los parámetros en la sentencia SQL con los datos del alumno proporcionado.
            preparedStatement.setString(1, enterprise.getEmail());
            preparedStatement.setString(2, enterprise.getName());
            preparedStatement.setInt(3, enterprise.getPhone());
            preparedStatement.setString(4, enterprise.getBoss());
            preparedStatement.setString(5, enterprise.getObservations());

            //Ejecuta la sentencia SQL de inserción.
            Integer rows = preparedStatement.executeUpdate();

            //Si la inserción se realiza correctamente.
            if (rows == 1){

                //Obtiene las claves generadas por la inserción.
                ResultSet resultSet = preparedStatement.getGeneratedKeys();
                resultSet.next();

                //Asigna el alumno proporcionado al alumno que se va a devolver.
                enterpriseResult = enterprise;

                //Establece el Id generado para el alumno.
                enterpriseResult.setId(resultSet.getInt(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return enterpriseResult;
    }

    /**
     * Elimina una empresa de la Base de Datos.
     *
     * @param enterprise Empresa que se va a eliminar de la Base de Datos
     */
    @Override
    public void delete(Enterprise enterprise) {
        try {
            //Preparación de la consulta SQL utilizando la conexión proporcionada.
            PreparedStatement preparedStatement = connection.prepareStatement(QUERY_DELETE);
            preparedStatement.setInt(1, enterprise.getId());

            //Ejecuta la sentencia SQL de eliminación.
            Integer rows = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
