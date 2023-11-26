package exception;

/**
 * La clase DNIInvalido representa una excepción personalizada que se lanza cuando se detecta un DNI inválido.
 * Hereda de la clase base Exception.
 */
public class InvalidDNI extends Exception {

    /**
     * Constructor de la excepción DNIInvalido que recibe un message explicativo.
     *
     * @param message Mensaje descriptivo que detalla la naturaleza del error.
     */
    public InvalidDNI(String message) {
        super(message);
    }
}
