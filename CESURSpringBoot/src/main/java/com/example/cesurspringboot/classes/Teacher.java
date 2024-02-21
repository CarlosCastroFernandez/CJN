package com.example.cesurspringboot.classes;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * La clase Profesor representa un docente en el sistema educativo.
 * Extiende la clase Usuario.
 */
@Entity
@Table(name = "profesor")
public class Teacher  {

@OneToMany(mappedBy = "teacher",fetch = FetchType.EAGER,cascade = CascadeType.ALL)
@JsonIgnore
    private List<Alumn> alumn;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
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
    private String email;
    /**
     * DNI del usuario.
     */
    private String dni;
    /**
     * Número de teléfono del usuario.
     */
    @Column(name = "telefono")
    private Integer phone;


    public Integer getId() {
        return id;
    }


    public void setId(Integer id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public String getLastName() {
        return lastName;
    }


    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


    public String getLastName2() {
        return lastName2;
    }


    public void setLastName2(String lastName2) {
        this.lastName2 = lastName2;
    }


    public String getPassword() {
        return password;
    }


    public void setPassword(String password) {
        this.password = password;
    }


    public String getEmail() {
        return email;
    }


    public void setEmail(String email) {
        this.email = email;
    }


    public String getDni() {
        return dni;
    }


    public void setDni(String dni) {
        this.dni = dni;
    }


    public Integer getPhone() {
        return phone;
    }


    public void setPhone(Integer phone) {
        this.phone = phone;
    }

    /**
     * Constructor de la clase Profesor que recibe nombre de usuario y contraseña.
     * Inicializa la lista de alumnos como una lista vacía.
     *
     * @param name Nombre del profesor.
     * @param password Contraseña del profesor.
     */
    public Teacher(String name, String password)   {
        this.alumn = new ArrayList<Alumn>();
    }

    /**
     * Constructor personalizado de la clase Teacher.
     *
     * @param alumn     Lista de alumnos asignados al profesor.
     * @param id        Identificador único del profesor.
     * @param name      Nombre del profesor.
     * @param lastName  Primer apellido del profesor.
     * @param lastName2 Segundo apellido del profesor.
     * @param password  Contraseña del profesor.
     * @param email     Correo electrónico del profesor.
     * @param dni       Documento Nacional de Identidad (DNI) del profesor.
     * @param phone     Número de teléfono del profesor.
     */
    public Teacher(ArrayList<Alumn> alumn, Integer id, String name, String lastName, String lastName2, String password, String email, String dni, Integer phone) {
        this.alumn = alumn;
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.lastName2 = lastName2;
        this.password = password;
        this.email = email;
        this.dni = dni;
        this.phone = phone;
    }

    /**
     * Constructor vacío de la clase Profesor.
     */
    public Teacher(){
    }

    //Getters y Setters de la clase Profesor.
    public List<Alumn> getAlumn() {
        return alumn;
    }

    public void setAlumn(List<Alumn> alumn) {
        this.alumn = alumn;
    }
}
