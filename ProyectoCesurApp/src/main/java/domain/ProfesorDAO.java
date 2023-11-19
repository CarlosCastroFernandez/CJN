package domain;

import clase.Profesor;
import exception.*;

/**
 * La interfaz ProfesorDAO define métodos para interactuar con el almacenamiento de datos de los profesores.
 */
public interface ProfesorDAO {

    /**
     * Carga la información de un profesor basada en su DNI y contraseña.
     *
     * @param dni      Documento Nacional de Identidad (DNI) del profesor.
     * @param password Contraseña asociada al profesor.
     * @return Un objeto Profesor que contiene la información cargada.
     * @throws UsuarioInexistente    Si el usuario no existe en el sistema.
     * @throws DNIInvalido           Si el DNI proporcionado es inválido.
     * @throws NombreConNumero       Si el nombre contiene números.
     * @throws ApellidoConNumero     Si el apellido contiene números.
     * @throws ContrasenhaIncorrecta Si la contraseña proporcionada es incorrecta.
     */
    public Profesor loadTeacher(String dni, String password) throws UsuarioInexistente, DNIInvalido, NombreConNumero, ApellidoConNumero, ContrasenhaIncorrecta;

    /**
     * Realiza una inyección de dependencia para un objeto Profesor.
     *
     * @param teacher Objeto de tipo Profesor en el cual se realizará la inyección.
     * @return El objeto Profesor con la inyección de dependencia aplicada.
     */
    public Profesor injection(Profesor teacher);
}
