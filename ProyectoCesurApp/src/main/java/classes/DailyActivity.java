package classes;

import enums.PracticeType;

/**
 * La clase ActividadDiaria representa una actividad diaria realizada por un alumno.
 */
public class DailyActivity {

    /**
     * Identificador único de la actividad.
     */
    private Integer id;

    /**
     * Identificador del alumno asociado a la actividad.
     */
    private Integer idAlumn;

    /**
     * Fecha de la actividad.
     */
    private String date;

    /**
     * Tipo de práctica a la que pertenece la actividad.
     */
    private PracticeType practiceType;

    /**
     * Total de horas dedicadas a la actividad.
     */
    private Integer totalHours;

    /**
     * Nombre de la actividad realizada.
     */
    private String taskName;

    /**
     * Observaciones adicionales.
     */
    private String observations;

    /**
     * Constructor de la clase ActividadDiaria.
     * @param id Identificador único de la actividad.
     * @param idAlumn Identificador del alumno asociado a la actividad.
     * @param date Fecha de la actividad en formato de cadena.
     * @param practiceType Tipo de práctica realizada (enum TipoPractica).
     * @param totalHours Total de horas dedicadas a la actividad.
     * @param taskName Nombre de la actividad realizada.
     * @param observations Observaciones adicionales sobre la actividad.
     */
    public DailyActivity(Integer id, Integer idAlumn, String date, PracticeType practiceType, Integer totalHours, String taskName, String observations) {
        this.id = id;
        this.idAlumn = idAlumn;
        this.date = date;
        this.practiceType = practiceType;
        this.totalHours = totalHours;
        this.taskName = taskName;
        this.observations = observations;
    }

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

    public Integer getIdAlumn() {
        return idAlumn;
    }

    public void setIdAlumn(Integer idAlumn) {
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


    public Integer getTotalHours() {
        return totalHours;
    }

    public void setTotaHours(Integer totalHours) {
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
