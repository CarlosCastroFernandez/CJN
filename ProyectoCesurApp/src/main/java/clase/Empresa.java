package clase;

public class Empresa {
    private String email;
    private String nombre;
    private Integer telefono;
    private String responsable;
    private String observaciones;
    private String alumno;

    public Empresa(String email, String nombre, Integer telefono, String responsable, String observaciones, String alumno) {
        this.email = email;
        this.nombre = nombre;
        this.telefono = telefono;
        this.responsable = responsable;
        this.observaciones = observaciones;
        this.alumno = alumno;
    }

    public String getEmail() {
        return email;
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

    public String getAlumno() {
        return alumno;
    }

    public void setAlumno(String alumno) {
        this.alumno = alumno;
    }
}
