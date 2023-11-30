package classes;

/**
 * La clase Sesion almacena información sobre la sesión actual del sistema.
 * Contiene listas estáticas y variables estáticas para diferentes entidades del sistema.
 */
public class Sesion {

    /**
     * Profesor actual.
     */
    private static Teacher teacher;

    /**
     * Alumno actual.
     */
    private static Alumn alumn;

    /**
     * Actividad diaria actual.
     */
    private static DailyActivity activity;
    private static Byte count=0;

    public static Byte getCount() {
        return count;
    }

    public static void setCount(Byte count) {
        Sesion.count = count;
    }

    public static DailyActivity getActivity(){
        return activity;
    }

    public static void setActivity(DailyActivity activity){
        Sesion.activity = activity;
    }

    public static Teacher getTeacher() {
        return teacher;
    }

    public static void setTeacher(Teacher teacher) {
        Sesion.teacher = teacher;
    }

    public static Alumn getAlumn() {
        return alumn;
    }

    public static void setAlumn(Alumn alumn) {
        Sesion.alumn = alumn;
    }


}
