package clase;

import java.util.ArrayList;

/**
 * La clase Sesion almacena información sobre la sesión actual del sistema.
 * Contiene listas estáticas y variables estáticas para diferentes entidades del sistema.
 */
public class Sesion {
    /**
     * Lista de actividades diarias.
     */
    private static ArrayList<ActividadDiaria> activities = new ArrayList<>();

    /**
     * Lista de alumnos.
     */
   private static ArrayList<Alumno> alumns =new ArrayList<Alumno>();

    /**
     * Lista de empresas.
     */
  private static  ArrayList<Empresa> enterprises = new ArrayList<>();

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

    /**
     * Empresa actual.
     */
    private static Empresa enterprise;

    //Getters y Setters de la clase Empresa.
   /* public static ArrayList<Empresa> getEnterprise() {
        return enterprises;
    }

    public static void setEnterprise(ArrayList<Empresa> enterprises) {
        Sesion.enterprises = enterprises;
    }

    public static Empresa getEmpresa(){
        return enterprise;
    }

    public static void setEnterprise(Empresa enterprise){
        Sesion.enterprise = enterprise;
    }*/

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

   public static ArrayList<ActividadDiaria> getActivities() {
        return activities;
    }

    public static void setActivities(ArrayList<ActividadDiaria> activities) {
        Sesion.activities = activities;
    }

   /* public static ArrayList<Alumno> getAlumns() {
        return alumns;
    }

    public static void setAlumns(ArrayList<Alumno> alumns) {
        Sesion.alumns = alumns;
    }*/
}
