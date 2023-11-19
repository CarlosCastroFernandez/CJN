package exception;

/**
 * La clase ImagenInvalida representa una excepción personalizada que se lanza cuando se encuentra una imagen inválida.
 * Hereda de la clase base Exception.
 */
public class ImagenInvalida extends Exception{

    /**
     * Constructor de la excepción ImagenInvalida que recibe un mensaje explicativo.
     *
     * @param message Mensaje descriptivo que detalla la naturaleza del error.
     */
    public ImagenInvalida(String message){
        super(message);
    }
}
