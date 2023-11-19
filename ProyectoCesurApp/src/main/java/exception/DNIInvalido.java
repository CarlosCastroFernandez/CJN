package exception;

/**
 * La clase DNIInvalido representa una excepción personalizada que se lanza cuando se detecta un DNI inválido.
 * Hereda de la clase base Exception.
 */
public class DNIInvalido extends Exception {

    /**
     * Constructor de la excepción DNIInvalido que recibe un message explicativo.
     *
     * @param message Mensaje descriptivo que detalla la naturaleza del error.
     */
    public DNIInvalido(String message) {
        super(message);
    }
}
