package com.example.cesurspringboot.classes;


import com.example.cesurspringboot.classes.enums.Grade;
import jakarta.persistence.*;

import java.sql.Date;
import java.util.List;


@Entity
@Table(name = "alumno")

/**
 * La clase Alumno representa a un estudiante en el sistema educativo.
 * Extiende la clase Usuario.
 */
public class Alumn  {

    /**
     * ID del alumno.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Horas totales de Dual que lleva el alumno.
     */
    @Column(name = "horasdual")
    private String hoursDUAL;

    /**
     * Horas totales de FCT que lleva el alumno.
     */
    @Column(name = "horasfct")
    private String hoursFCT;

    /**
     * ID del profesor asociado.
     */
    @Transient
    private Integer teacherID;

    /**
     * Profesor asociado.
     */
    @JoinColumn(name = "profesor")
    @ManyToOne
    private Teacher teacher;

    /**
     * Fecha de nacimiento del alumno.
     */

    @Column(name = "fechanacimiento")
    private Date birthday;

    /**
     * ID de la empresa asociada al alumno
     */
    @Transient
    private Integer enterpriseID;


    /**
     * Empresa asociada al alumno.
     */
    @JoinColumn(name = "empresa")
    @ManyToOne


    private Enterprise enterprise;

    /**
     * Curso en el que se encuentra el alumno.
     */
    @Column(name = "curso")
    @Enumerated(EnumType.STRING)
    private Grade grade;

    /**
     * Observaciones.
     */
    @Column(name = "observaciones")
    private String observations;

    /**
     * Nombre del usuario.
     */
    @Column(name = "nombre")
    private String name;

    /**
     * Primer apellido del usuario.
     */
    @Column(name = "apellido1")
    private String lastName;

    /**
     * Segundo apellido del usuario.
     */
    @Column(name = "apellido2")
    private String lastName2;

    /**
     * Contraseña del usuario.
     */
    @Column(name = "contraseña")
    private String password;

    /**
     * Correo electrónico del usuario.
     */
    @Column(name = "email")
    private String email;

    /**
     * DNI del usuario.
     */
    @Column(name = "dni")
    private String dni;

    /**
     * Número de teléfono del usuario.
     */
    @Column(name = "telefono")
    private Integer phone;

    /**
     * Lista de actividades asociadas al alumno.
     */
    @OneToMany(mappedBy = "idAlumn", fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    private List<DailyActivity> activity;

    /**
     * Constructor vacío de la clase Alumno.
     * Crea una instancia de Alumno sin inicializar sus atributos.
     */
    public Alumn() {
    }

    //Getters y Setters de la clase Alumno.
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<DailyActivity> getActivity() {
        return activity;
    }

    public void setActivity(List<DailyActivity> activity) {
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

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Integer getEnterpriseID() {
        return enterpriseID;
    }

    public void setEnterpriseID(Integer enterpriseID) {
        this.enterpriseID = enterpriseID;
    }

    public Enterprise getEnterprise() {
        return enterprise;
    }

    public void setEnterprise(Enterprise enterprise) {
        this.enterprise = enterprise;
    }

    public Grade getGrade() {
        return grade;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }

    public String getObservations() {
        return observations;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public String getLastName2() {
        return lastName2;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getDni() {
        return dni;
    }

    public Integer getPhone() {
        return phone;
    }
    public void setPhone(Integer phone) {
        this.phone = phone;
    }

    /**
     * Establece el nombre del usuario.
     * @param name Nombre a establecer para el usuario.
     */
    public void setName(String name) {
        String numbers="0123456789";
        for(int i=0;i<name.length();i++) {
            if(numbers.contains(""+name.charAt(i))) {
            }
        }
        this.name = name;
    }

    /**
     * Establece el primer apellido del usuario.
     * @param lastName Primer apellido a establecer para el usuario.
     */
    public void setLastName(String lastName) {
        String numbers="0123456789";
        for(int i=0;i<lastName.length();i++) {
            if(numbers.contains(""+lastName.charAt(i))) {
            }
        }
        this.lastName = lastName;
    }

    /**
     * Establece el segundo apellido del usuario.
     * @param lastName2 Segundo apellido a establecer para el usuario.
     */
    public void setLastName2(String lastName2)  {
        String numbers="0123456789";
        for(int i=0;i<lastName2.length();i++) {
            if(numbers.contains(""+lastName2.charAt(i))) {
            }
        }
        this.lastName2 = lastName2;
    }

    /**
     * Establece la contraseña del usuario.
     * @param password Contraseña a establecer para el usuario.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Establece el correo electrónico del usuario.
     * @param email Correo electrónico a establecer para el usuario.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Establece el número de identificación del usuario (DNI).
     * @param dni Número de identificación a establecer para el usuario.
     */
    public void setDni(String dni) {
      this.dni=dni;
    }


    /**
     * Establece las observaciones del usuario.
     * @param observations Número de identificación a establecer para el usuario.
     */
    public void setObservations(String observations) {
        this.observations = observations;
    }

}