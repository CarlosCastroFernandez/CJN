package exception;

/**
 * La clase ApellidoConNumero representa una excepción personalizada que se lanza cuando se detecta un número en un apellido.
 * Hereda de la clase base Exception.
 */
public class ApellidoConNumero extends Exception {

    /**
     * Constructor de la excepción ApellidoConNumero que recibe un mensaje explicativo.
     *
     * @param message Mensaje descriptivo que detalla la naturaleza del error.
     */
    public ApellidoConNumero(String message) {
      super(message);
    }
}
