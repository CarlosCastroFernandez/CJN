package domain;

import clase.Alumno;

import java.util.ArrayList;

public interface AlumnoDAO {
    public Alumno loadActivity(String nombre);
    public ArrayList<Alumno> loadAll(Integer id);
    public Alumno saveActivity (Alumno a);
    public Alumno updateActivity (Alumno a);
    public void removeActivity (Alumno a);
}
