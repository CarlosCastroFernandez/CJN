package com.example.cesurspringboot.classes;


import com.example.cesurspringboot.classes.enums.PracticeType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.hibernate.annotations.Cascade;

/**
 * La clase ActividadDiaria representa una actividad diaria realizada por un alumno.
 */
@Entity
@Table(name = "actividaddiaria")
public class DailyActivity {

    /**
     * Identificador único de la actividad.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Identificador del alumno asociado a la actividad.
     */
    @JoinColumn(name = "alumnoid")
    @ManyToOne(cascade = CascadeType.REMOVE)
    @JsonIgnore
    private Alumn idAlumn;

    /**
     * Fecha de la actividad.
     */
    @Column(name="fecha")
    private String date;

    /**
     * Tipo de práctica a la que pertenece la actividad.
     */
    @Column(name="tipopractica")
    @Enumerated(EnumType.STRING)
    private PracticeType practiceType;

    /**
     * Total de horas dedicadas a la actividad.
     */
    @Column(name="totalhoras")
    private Long totalHours;

    /**
     * Nombre de la actividad realizada.
     */
    @Column(name="nombre")
    private String taskName;

    /**
     * Observaciones adicionales.
     */
    @Column(name = "observaciones")
    private String observations;



    /**
     * Constructor por defecto de la clase ActividadDiaria.
     */
    public DailyActivity() {
    }

    // Getters y setters para los atributos de la clase
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Alumn getIdAlumn() {
        return idAlumn;
    }

    public void setIdAlumn(Alumn idAlumn) {
        this.idAlumn = idAlumn;
    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


    public PracticeType getPracticeType() {
        return practiceType;
    }

    public void setPracticeType(PracticeType practiceType) {
        this.practiceType = practiceType;
    }


    public Long getTotalHours() {
        return totalHours;
    }

    public void setTotalHours(Long totalHours) {
        this.totalHours = totalHours;
    }


    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }


    public String getObservations() {
        return observations;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }

    /**
     * Override del método toString para obtener una representación en cadena de la clase ActividadDiaria.
     * @return Cadena que representa la actividad diaria con todos sus atributos.
     */
    @Override
    public String toString() {
        return "ActividadDiaria{" +
                "id=" + id +
                ", idAlumno=" + idAlumn +
                ", fecha='" + date + '\'' +
                ", tipoPractica=" + practiceType +
                ", totalHoras=" + totalHours +
                ", nombreTarea='" + taskName + '\'' +
                ", observaciones='" + observations + '\'' +
                '}';
    }
}
