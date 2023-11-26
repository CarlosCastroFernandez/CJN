package exception;

/**
 * La clase ContrasenhaIncorrecta representa una excepción personalizada que se lanza cuando la contraseña es incorrecta.
 * Hereda de la clase base Exception.
 */
public class IncorrectPassword extends Exception {

    /**
     * Constructor de la excepción ContrasenhaIncorrecta que recibe un mensaje explicativo.
     *
     * @param message Mensaje descriptivo que detalla la naturaleza del error.
     */
    public IncorrectPassword(String message) {
    super(message);
    }
}
