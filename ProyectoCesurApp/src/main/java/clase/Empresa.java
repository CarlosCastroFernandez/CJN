package clase;

import java.util.ArrayList;

public class Empresa {
    private Integer id;
    private String email;
    private String nombre;
    private Integer telefono;
    private String responsable;
    private String observaciones;
    private ArrayList<Alumno>alumnos;

    public ArrayList<Alumno> getAlumnos() {
        return alumnos;
    }

    public void setAlumnos(ArrayList<Alumno> alumnos) {
        this.alumnos = alumnos;
    }

    public Empresa(Integer id, String email, String nombre, Integer telefono, String responsable, String observaciones) {
        this.id=id;
        this.email = email;
        this.nombre = nombre;
        this.telefono = telefono;
        this.responsable = responsable;
        this.observaciones = observaciones;
        this.alumnos=null;
    }

    public String getEmail() {
        return email;
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getTelefono() {
        return telefono;
    }

    public void setTelefono(Integer telefono) {
        this.telefono = telefono;
    }

    public String getResponsable() {
        return responsable;
    }

    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }


}
