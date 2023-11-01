package domain;

import clase.Alumno;
import exception.ContrasenhaIncorrecta;
import exception.UsuarioInexistente;

import java.util.ArrayList;

public interface AlumnoDAO {
    public Alumno loadActivity(String dni, String contrasenha) throws ContrasenhaIncorrecta, UsuarioInexistente;
    public ArrayList<Alumno> loadAll(Integer id);
    public Alumno saveActivity (Alumno a);
    public Alumno updateActivity (Alumno a);
    public void removeActivity (Alumno a);
    public Alumno injection(Alumno alumno);
}
