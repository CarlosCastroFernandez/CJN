package exception;

/**
 * La clase NombreConNumero representa una excepción personalizada que se lanza cuando se detecta un número en un nombre.
 * Hereda de la clase base Exception.
 */
public class NombreConNumero extends Exception{

    /**
     * Constructor de la excepción NombreConNumero que recibe un mensaje explicativo.
     *
     * @param message Mensaje descriptivo que detalla la naturaleza del error.
     */
    public NombreConNumero(String message){
        super(message);
    }
}
