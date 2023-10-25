package clase;

import java.util.ArrayList;

public class Sesion {
    private static ArrayList<ActividadDiaria> listaActividades = new ArrayList<>();
    private static Profesor profesor;
    private static Alumno alumno;

    public static Profesor getProfesor() {
        return profesor;
    }

    public static void setProfesor(Profesor profesor) {
        Sesion.profesor = profesor;
    }

    public static Alumno getAlumno() {
        return alumno;
    }

    public static void setAlumno(Alumno alumno) {
        Sesion.alumno = alumno;
    }

    public static ArrayList<ActividadDiaria> getListaActividades() {
        return listaActividades;
    }

    public static void setListaActividades(ArrayList<ActividadDiaria> listaActividades) {
        Sesion.listaActividades = listaActividades;
    }
}
