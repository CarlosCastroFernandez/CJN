package exception;

/**
 * La clase UsuarioInexistente representa una excepción personalizada que se lanza cuando un usuario no existe.
 * Hereda de la clase base Exception.
 */
public class NonExistentUser extends Exception{

    /**
     * Constructor de la excepción UsuarioInexistente que recibe un mensaje explicativo.
     *
     * @param message Mensaje descriptivo que detalla la naturaleza del error.
     */
    public NonExistentUser(String message){
        super(message);
    }
}
