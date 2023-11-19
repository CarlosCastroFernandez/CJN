package clase;

import enums.Curso;
import exception.ApellidoConNumero;
import exception.DNIInvalido;
import exception.NombreConNumero;

import java.util.ArrayList;

/**
 * La clase Alumno representa a un estudiante en el sistema educativo.
 * Extiende la clase Usuario.
 */
public class Alumno extends Usuario {

    /**
     * Horas totales de Dual que lleva el alumno.
     */
    private String hoursDUAL;

    /**
     * Horas totales de FCT que lleva el alumno.
     */
    private String hoursFCT;

    /**
     * ID del profesor asociado.
     */
    private Integer teacherID;

    /**
     * Profesor asociado.
     */
    private Profesor teacher;

    /**
     * Fecha de nacimiento del alumno.
     */
    private String birthday;

    /**
     * ID de la empresa asociada al alumno
     */
    private Integer enterpriseID;

    /**
     * Empresa asociada al alumno.
     */
    private Empresa enterprise;

    /**
     * ID del alumno.
     */
    private Integer id;

    /**
     * Curso en el que se encuentra el alumno.
     */
    private Curso grade;

    /**
     * Observaciones.
     */
    private String observations;

    /**
     * Lista de actividades asociadas al alumno.
     */
    private ArrayList<ActividadDiaria> activity;

    /**
     * Constructor de la clase Alumno que recibe múltiples parámetros para inicializar sus atributos.
     * @param id Identificador único del alumno.
     * @param name Nombre del alumno.
     * @param lastName Primer apellido del alumno.
     * @param lastName2 Segundo apellido del alumno.
     * @param password Contraseña del alumno.
     * @param email Correo electrónico del alumno.
     * @param dni Número de identificación del alumno (DNI).
     * @param phone Número de teléfono del alumno.
     * @param hoursDUAL Horas dedicadas al programa DUAL.
     * @param hoursFCT Horas dedicadas a la FCT.
     * @param teacherID ID del profesor a cargo del alumno.
     * @param teacher Profesor a cargo del alumno.
     * @param birthday Fecha de nacimiento del alumno.
     * @param enterpriseID ID de la empresa asociada al alumno.
     * @param enterprise Empresa asociada al alumno.
     * @param grade Curso en el que está inscrito el alumno.
     * @param observations Observaciones adicionales sobre el alumno.
     * @throws NombreConNumero Si el nombre contiene números.
     * @throws ApellidoConNumero Si el apellido contiene números.
     * @throws DNIInvalido Si el formato del DNI es inválido.
     */
    public Alumno(Integer id,String name, String lastName, String lastName2, String password, String email, String dni, Integer phone, String hoursDUAL,
                  String hoursFCT,Integer teacherID, Profesor teacher, String birthday, Integer enterpriseID,
                  Empresa enterprise,Curso grade,String observations) throws NombreConNumero, ApellidoConNumero, DNIInvalido {
        super(id,name, lastName, lastName2, password, email, dni, phone);
        this.setName(name);
        this.setId(id);
        this.setLastName(lastName);
        this.setLastName2(lastName2);
        this.setPassword(password);
        this.setEmail(email);
        this.setDni(dni);
        this.setPhone(phone);
        this.hoursDUAL = hoursDUAL;
        this.hoursFCT = hoursFCT;
        this.grade = grade;
        this.teacherID = teacherID;
        this.teacher = teacher;
        this.enterpriseID = enterpriseID;
        this.enterprise = enterprise;
        this.observations = observations;
        this.activity = new ArrayList<>();
    }

    /**
     * Constructor de la clase Alumno que inicializa los atributos de un estudiante con parámetros específicos.
     * @param id Identificador único del alumno.
     * @param name Nombre del alumno.
     * @param lastName Primer apellido del alumno.
     * @param lastName2 Segundo apellido del alumno.
     * @param password Contraseña del alumno.
     * @param email Correo electrónico del alumno.
     * @param dni Número de identificación del alumno (DNI).
     * @param phone Número de teléfono del alumno.
     * @param hoursDUAL Horas dedicadas al programa DUAL.
     * @param hoursFCT Horas dedicadas a la FCT.
     * @param teacherID ID del profesor a cargo del alumno.
     * @param birthday Fecha de nacimiento del alumno.
     * @param enterpriseID ID de la empresa asociada al alumno.
     * @param grade Curso en el que está inscrito el alumno.
     * @param observations Observaciones adicionales sobre el alumno.
     * @throws NombreConNumero Si el nombre contiene números.
     * @throws ApellidoConNumero Si el apellido contiene números.
     * @throws DNIInvalido Si el formato del DNI es inválido.
     */
    public Alumno(Integer id, String name, String lastName, String lastName2, String password, String email, String dni, Integer phone, String hoursDUAL,
                  String hoursFCT, Integer teacherID, String birthday, Integer enterpriseID
            , Curso grade, String observations) throws NombreConNumero, ApellidoConNumero, DNIInvalido {
        super(id, name, lastName, lastName2, password, email, dni, phone);
        this.setName(name);
        this.setId(id);
        this.setLastName(lastName);
        this.setLastName2(lastName2);
        this.setPassword(password);
        this.setEmail(email);
        this.setDni(dni);
        this.setPhone(phone);
        this.birthday = birthday;
        this.hoursDUAL = hoursDUAL;
        this.hoursFCT = hoursFCT;
        this.grade = grade;
        this.teacherID = teacherID;
        this.enterpriseID = enterpriseID;
        this.observations = observations;
        this.activity = new ArrayList<>();

    }

    /**
     * Constructor de la clase Alumno que inicializa un estudiante con su DNI y contraseña.
     * Crea una lista vacía de actividades diarias para el alumno.
     *
     * @param dni Número de identificación del alumno (DNI).
     * @param password Contraseña del alumno.
     */
    public Alumno(String dni, String password){
        this.activity = new ArrayList<ActividadDiaria>();
    }

    /**
     * Constructor vacío de la clase Alumno.
     * Crea una instancia de Alumno sin inicializar sus atributos.
     */
    public Alumno() {

    }

    //Getters y Setters de la clase Alumno.
    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public ArrayList<ActividadDiaria> getActivity() {
        return activity;
    }

    public void setActivity(ArrayList<ActividadDiaria> activity) {
        this.activity = activity;
    }

    public String getHoursDUAL() {
        return hoursDUAL;
    }

    public void setHoursDUAL(String hoursDUAL) {
        this.hoursDUAL = hoursDUAL;
    }

    public String getHoursFCT() {
        return hoursFCT;
    }

    public void setHoursFCT(String hoursFCT) {
        this.hoursFCT = hoursFCT;
    }

    public Integer getTeacherID() {
        return teacherID;
    }

    public void setTeacherID(Integer teacherID) {
        this.teacherID = teacherID;
    }

    public Profesor getTeacher() {
        return teacher;
    }

    public void setTeacher(Profesor teacher) {
        this.teacher = teacher;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public Integer getEnterpriseID() {
        return enterpriseID;
    }

    public void setEnterpriseID(Integer enterpriseID) {
        this.enterpriseID = enterpriseID;
    }

    public Empresa getEnterprise() {
        return enterprise;
    }

    public void setEnterprise(Empresa enterprise) {
        this.enterprise = enterprise;
    }

    public Curso getGrade() {
        return grade;
    }

    public void setGrade(Curso grade) {
        this.grade = grade;
    }

    public String getObservations() {
        return observations;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }

    /**
     * Override del método toString para obtener una representación en cadena de la clase Alumno.
     * @return Cadena que representa al alumno con todos sus atributos.
     */
    @Override
    public String toString() {
        return super.toString()+" Alumno{" +
                "horasDUAL='" + hoursDUAL + '\'' +
                ", horasFCT='" + hoursFCT + '\'' +
                ", profesorId=" + teacherID +
                ", profesor=" + teacher +
                ", fechaNacimiento='" + birthday + '\'' +
                ", empresaId=" + enterpriseID +
                ", empresa=" + enterprise +
                ", curso=" + grade +
                ", observaciones='" + observations + '\'' +
                '}';
    }
}