package clase;

import enums.TipoPractica;

public class ActividadDiaria {
    private Integer id;
    private Integer idAlumno;
    private String fecha;
    private TipoPractica tipoPractica;
    private Integer totalHoras;
    private String nombreTarea;
    private String observaciones;

    public ActividadDiaria(Integer id, Integer idAlumno, String fecha, TipoPractica tipoPractica, Integer totalHoras, String nombreTarea, String observaciones) {
        this.id = id;
        this.idAlumno = idAlumno;
        this.fecha = fecha;
        this.tipoPractica = tipoPractica;
        this.totalHoras = totalHoras;
        this.nombreTarea = nombreTarea;
        this.observaciones = observaciones;
    }

    public ActividadDiaria(){

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdAlumno() {
        return idAlumno;
    }

    public void setIdAlumno(Integer idAlumno) {
        this.idAlumno = idAlumno;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public TipoPractica getTipoPractica() {
        return tipoPractica;
    }

    public void setTipoPractica(TipoPractica tipoPractica) {
        this.tipoPractica = tipoPractica;
    }

    public Integer getTotalHoras() {
        return totalHoras;
    }

    public void setTotalHoras(Integer totalHoras) {
        this.totalHoras = totalHoras;
    }

    public String getNombreTarea() {
        return nombreTarea;
    }

    public void setNombreTarea(String nombreTarea) {
        this.nombreTarea = nombreTarea;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
}
