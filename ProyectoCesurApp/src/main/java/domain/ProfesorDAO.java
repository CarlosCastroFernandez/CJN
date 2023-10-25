package domain;

import clase.Profesor;
import exception.*;

public interface ProfesorDAO {
    public Profesor loadTeacher(String dni, String contrasenha) throws UsuarioInexistente, DNIInvalido, NombreConNumero, ApellidoConNumero, ContrasenhaIncorrecta;
    public void injection(Profesor profesor);
}
