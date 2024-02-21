package com.example.cesurspringboot.classes;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "empresa")
/**
 * La clase Empresa representa una entidad empresarial en el sistema.
 */
public class Enterprise {

    /**
     * Identificador único de la empresa.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Correo electrónico de la empresa.
     */
    private String email;

    /**
     * Nombre de la empresa.
     */
    @Column(name="nombre")
    private String name;

    /**
     * Número de teléfono de la empresa.
     */
    @Column(name="telefono")
    private Integer phone;

    /**
     * Persona responsable de la empresa.
     */
    @Column(name="responsable")
    private String boss;

    /**
     * Observaciones sobre la empresa.
     */
    @Column(name="observaciones")
    private String observations;

    /**
     * Alumnos que tiene la empresa asignados.
     */
    @OneToMany(mappedBy = "enterprise",fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Alumn> alumn;

    /**
     * Constructor de la clase Empresa que recibe varios parámetros para inicializar sus atributos.
     *
     * @param id Identificador único de la empresa.
     * @param email Correo electrónico de la empresa.
     * @param name Nombre de la empresa.
     * @param phone Número de teléfono de la empresa.
     * @param boss Persona responsable de la empresa.
     * @param observations Observaciones o notas adicionales sobre la empresa.
     */


    /**
     * Constructor vacío de la clase Empresa.
     */
    public Enterprise() {
    }

    //Getters y Setters de la clase Empresa.
    public List<Alumn> getAlumn() {
        return alumn;
    }

    public void setAlumn(List<Alumn> alumn) {
        this.alumn = alumn;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPhone() {
        return phone;
    }

    public void setPhone(Integer phone) {
        this.phone = phone;
    }

    public String getBoss() {
        return boss;
    }

    public void setBoss(String boss) {
        this.boss = boss;
    }

    public String getObservations() {
        return observations;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }

    /**
     * Override del método toString para obtener una representación en cadena de la clase Empresa.
     *
     * @return Cadena que representa a la empresa con todos sus atributos.
     */
    @Override
    public String toString() {
        return "Empresa{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", nombre='" + name + '\'' +
                ", telefono=" + phone +
                ", responsable='" + boss + '\'' +
                ", observaciones='" + observations + '\'' +
                '}';
    }
}
