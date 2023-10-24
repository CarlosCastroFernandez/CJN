package clase;

public class Sesion {
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
}
