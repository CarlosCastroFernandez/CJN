package domain;

import clase.Profesor;
import exception.UsuarioInexistente;

public interface ProfesorDAO {
    public Profesor loadTeacher(String dni, String contrasenha) throws UsuarioInexistente;
    public void injection(Profesor profesor);
}
