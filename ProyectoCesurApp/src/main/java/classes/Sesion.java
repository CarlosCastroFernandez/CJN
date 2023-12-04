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

    /**
     * Contador de sesión utilizado para diversas operaciones internas.
     */
    private static Byte count = 0;

    /**
     * Obtiene el contador de sesión actual.
     *
     * @return El contador de sesión actual.
     */
    public static Byte getCount() {
        return count;
    }

    /**
     * Establece el contador de sesión actual.
     *
     * @param count El nuevo valor para el contador de sesión.
     */
    public static void setCount(Byte count) {
        Sesion.count = count;
    }

    /**
     * Obtiene la actividad diaria actual.
     *
     * @return La actividad diaria actual.
     */
    public static DailyActivity getActivity() {
        return activity;
    }

    /**
     * Establece la actividad diaria actual.
     *
     * @param activity La nueva actividad diaria actual.
     */
    public static void setActivity(DailyActivity activity) {
        Sesion.activity = activity;
    }

    /**
     * Obtiene el profesor actual.
     *
     * @return El profesor actual.
     */
    public static Teacher getTeacher() {
        return teacher;
    }

    /**
     * Establece el profesor actual.
     *
     * @param teacher El nuevo profesor actual.
     */
    public static void setTeacher(Teacher teacher) {
        Sesion.teacher = teacher;
    }

    /**
     * Obtiene el alumno actual.
     *
     * @return El alumno actual.
     */
    public static Alumn getAlumn() {
        return alumn;
    }

    /**
     * Establece el alumno actual.
     *
     * @param alumn El nuevo alumno actual.
     */
    public static void setAlumn(Alumn alumn) {
        Sesion.alumn = alumn;
    }
}
