package domain.alumn;

import classes.Alumn;
import exception.IncorrectPassword;
import exception.NonExistentUser;

import java.util.ArrayList;

/**
 * La interfaz AlumnoDAO define métodos para interactuar con el almacenamiento de datos de los alumnos.
 */
public interface AlumnDAO {

    /**
     * Carga la actividad de un alumno basado en su DNI y contraseña.
     *
     * @param dni      Documento Nacional de Identidad (DNI) del alumno.
     * @param password Contraseña asociada al alumno.
     * @return Un objeto Alumno que contiene la actividad cargada.
     * @throws IncorrectPassword Si la contraseña proporcionada es incorrecta.
     * @throws NonExistentUser    Si el usuario no existe en el sistema.
     */
    public Alumn login(String dni, String password) throws IncorrectPassword, NonExistentUser;

    /**
     * Carga todas las actividades asociadas a un ID de alumno específico.
     *
     * @param id Identificador único del alumno.
     * @return Una lista de objetos Alumno que contienen las actividades asociadas al ID proporcionado.
     */
    public ArrayList<Alumn> loadAll(Integer id);

    /**
     * Actualiza la información de un alumno en el almacenamiento de datos.
     *
     * @param alumn Objeto de tipo Alumno que se va a actualizar.
     * @return El objeto Alumno actualizado.
     */
    public Alumn saveActivity (Alumn alumn);

    /**
     * Actualiza la información de un alumno en el almacenamiento de datos.
     *
     * @param alumn Objeto de tipo Alumno que se va a actualizar.
     * @return El objeto Alumno actualizado.
     */
    public Alumn update (Alumn alumn);

    /**
     * Elimina una actividad específica de un alumno en el almacenamiento de datos.
     *
     * @param alumn Objeto de tipo Alumno del cual se eliminará la actividad.
     * @param attr  Atributo que identifica la actividad a eliminar.
     */
    public void removeActivity (Alumn alumn, String attr);

    /**
     * Realiza una inyección de dependencia para un objeto Alumno.
     *
     * @param alumn Objeto de tipo Alumno en el cual se realizará la inyección.
     * @return El objeto Alumno con la inyección de dependencia aplicada.
     */
    public Alumn injection(Alumn alumn);
}

