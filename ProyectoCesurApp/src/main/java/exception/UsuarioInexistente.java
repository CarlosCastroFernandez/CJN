package exception;

public class UsuarioInexistente extends Exception{
    public UsuarioInexistente(String mensaje){
        super(mensaje);
    }
}
