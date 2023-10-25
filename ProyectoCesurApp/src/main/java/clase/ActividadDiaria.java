package clase;

import enums.TipoPractica;

public class ActividadDiaria {
    private String idAlumno;
    private String fecha;
    private TipoPractica tipoPractica;
    private int totalHoras;
    private String nombreTarea;
    private String observaciones;

    public ActividadDiaria(String idAlumno, String fecha, TipoPractica tipoPractica, int totalHoras, String nombreTarea, String observaciones) {
        this.idAlumno = idAlumno;
        this.fecha = fecha;
        this.tipoPractica = tipoPractica;
        this.totalHoras = totalHoras;
        this.nombreTarea = nombreTarea;
        this.observaciones = observaciones;
    }

    public ActividadDiaria(){

    }

    public String getIdAlumno() {
        return idAlumno;
    }

    public void setIdAlumno(String idAlumno) {
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

    public int getTotalHoras() {
        return totalHoras;
    }

    public void setTotalHoras(int totalHoras) {
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
