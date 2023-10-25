package clase;

import domain.ActividadDiariaDAO;
import enums.Curso;
import exception.ApellidoConNumero;
import exception.DNIInvalido;
import exception.NombreConNumero;

import java.util.ArrayList;

public class Alumno extends Usuario {
    private String horasDUAL;
    private String horasFCT;
    private Integer profesorId;
    private Profesor profesor;
    private String fechaNacimiento;
    private Integer empresaId;
    private Empresa empresa;
    private Integer id;
    private Curso curso;
    private String observaciones;
    private ArrayList<ActividadDiaria> actividadDiaria;

    public Alumno(Integer id,String nombre, String apellido1, String apellido2, String password, String correo, String dni, Integer telefono, String horasDUAL,
                  String horasFCT,Integer profesorId,Profesor profe,String fechaNacimiento,Integer empresaId,
                  Empresa empresa,Curso curso,String obs) throws NombreConNumero, ApellidoConNumero, DNIInvalido {
        super(nombre, apellido1, apellido2, password, correo, dni, telefono);
        this.setNombre(nombre);
        this.setId(id);
        this.setApellido1(apellido1);
        this.setApellido2(apellido2);
        this.setPassword(password);
        this.setCorreo(correo);
        this.setDni(dni);
        this.setTelefono(telefono);
        this.horasDUAL = horasDUAL;
        this.horasFCT=horasFCT;
        this.curso=curso;
        this.profesorId=profesorId;
        this.profesor=profe;
        this.empresaId=empresaId;
        this.empresa=empresa;
        this.observaciones=obs;
    }
    public Alumno(Integer id,String nombre, String apellido1, String apellido2, String password, String correo, String dni, Integer telefono, String horasDUAL,
                  String horasFCT,Integer profesorId,String fechaNacimiento,Integer empresaId
            ,Curso curso,String obs) throws NombreConNumero, ApellidoConNumero, DNIInvalido {
        super(nombre, apellido1, apellido2, password, correo, dni, telefono);
        this.setNombre(nombre);
        this.setId(id);
        this.setApellido1(apellido1);
        this.setApellido2(apellido2);
        this.setPassword(password);
        this.setCorreo(correo);
        this.setDni(dni);
        this.setTelefono(telefono);
        this.fechaNacimiento=fechaNacimiento;
        this.horasDUAL = horasDUAL;
        this.horasFCT=horasFCT;
        this.curso=curso;
        this.profesorId=profesorId;
        this.empresaId=empresaId;
        this.observaciones=obs;

    }
    public Alumno(String dni,String contraseña){
    this.actividadDiaria = new ArrayList<ActividadDiaria>();
    }

    public String getHorasDUAL() {
        return horasDUAL;
    }

    public void setHorasDUAL(String horasDUAL) {
        this.horasDUAL = horasDUAL;
    }

    public String getHorasFCT() {
        return horasFCT;
    }

    public void setHorasFCT(String horasFCT) {
        this.horasFCT = horasFCT;
    }

    public Integer getProfesorId() {
        return profesorId;
    }

    public void setProfesorId(Integer profesorId) {
        this.profesorId = profesorId;
    }

    public Profesor getProfesor() {
        return profesor;
    }

    public void setProfesor(Profesor profesor) {
        this.profesor = profesor;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Integer getEmpresaId() {
        return empresaId;
    }

    public void setEmpresaId(Integer empresaId) {
        this.empresaId = empresaId;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    @Override
    public String toString() {
        return super.toString()+" Alumno{" +
                "horasDUAL='" + horasDUAL + '\'' +
                ", horasFCT='" + horasFCT + '\'' +
                ", profesorId=" + profesorId +
                ", profesor=" + profesor +
                ", fechaNacimiento='" + fechaNacimiento + '\'' +
                ", empresaId=" + empresaId +
                ", empresa=" + empresa +
                ", curso=" + curso +
                ", observaciones='" + observaciones + '\'' +
                '}';
    }
}