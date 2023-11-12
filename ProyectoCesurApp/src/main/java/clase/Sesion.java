package clase;

import java.util.ArrayList;

public class Sesion {
    private static ArrayList<ActividadDiaria> listaActividades = new ArrayList<>();
    private static ArrayList<Alumno>listaAlumnos=new ArrayList<Alumno>();
    private static  ArrayList<Empresa> listaEmpresas = new ArrayList<>();
    private static Profesor profesor;
    private static Alumno alumno;
    private static ActividadDiaria actividadDiaria;
    private static Empresa empresa;

    public static ArrayList<Empresa> getListaEmpresas() {
        return listaEmpresas;
    }

    public static void setListaEmpresas(ArrayList<Empresa> listaEmpresas) {
        Sesion.listaEmpresas = listaEmpresas;
    }

    public static Empresa getEmpresa(){
        return empresa;
    }

    public static void setEmpresa(Empresa empresa){
        Sesion.empresa = empresa;
    }

    public static ActividadDiaria getActividadDiaria(){
        return actividadDiaria;
    }

    public static void setActividadDiaria(ActividadDiaria actividadDiaria){
        Sesion.actividadDiaria = actividadDiaria;
    }

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
    public static ArrayList<Alumno> getListaAlumno() {
        return listaAlumnos;
    }
    public static void setListaAlumno(ArrayList<Alumno> listaAlumnos) {
        Sesion.listaAlumnos= listaAlumnos;
    }
}
