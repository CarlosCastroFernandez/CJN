package domain.teacher;

import classes.Teacher;
import exception.*;

/**
 * La interfaz ProfesorDAO define métodos para interactuar con el almacenamiento de datos de los profesores.
 */
public interface TeacherDAO {

    /**
     * Carga la información de un profesor basada en su DNI y contraseña.
     *
     * @param dni      Documento Nacional de Identidad (DNI) del profesor.
     * @param password Contraseña asociada al profesor.
     * @return Un objeto Profesor que contiene la información cargada.
     * @throws NonExistentUser    Si el usuario no existe en el sistema.
     * @throws InvalidDNI           Si el DNI proporcionado es inválido.
     * @throws NameWithNumber       Si el nombre contiene números.
     * @throws LastNameWithNumber     Si el apellido contiene números.
     * @throws IncorrectPassword Si la contraseña proporcionada es incorrecta.
     */
    public Teacher login(String dni, String password) throws NonExistentUser, InvalidDNI, NameWithNumber, LastNameWithNumber, IncorrectPassword;

    /**
     * Realiza una inyección de dependencia para un objeto Profesor.
     *
     * @param teacher Objeto de tipo Profesor en el cual se realizará la inyección.
     * @return El objeto Profesor con la inyección de dependencia aplicada.
     */
    public Teacher injection(Teacher teacher);
}
