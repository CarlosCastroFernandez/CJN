package clase;

import java.util.ArrayList;

/**
 * La clase Sesion almacena información sobre la sesión actual del sistema.
 * Contiene listas estáticas y variables estáticas para diferentes entidades del sistema.
 */
public class Sesion {

    /**
     * Profesor actual.
     */
    private static Profesor teacher;

    /**
     * Alumno actual.
     */
    private static Alumno alumn;

    /**
     * Actividad diaria actual.
     */
    private static ActividadDiaria activity;


    public static ActividadDiaria getActivity(){
        return activity;
    }

    public static void setActivity(ActividadDiaria activity){
        Sesion.activity = activity;
    }

    public static Profesor getTeacher() {
        return teacher;
    }

    public static void setTeacher(Profesor teacher) {
        Sesion.teacher = teacher;
    }

    public static Alumno getAlumn() {
        return alumn;
    }

    public static void setAlumn(Alumno alumn) {
        Sesion.alumn = alumn;
    }


}
