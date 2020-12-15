/*
 * Autor: Mario Dorantes
 * fecha de creación: 15/12/2020
 */
package pojos;

import java.util.Date;

public class Ceneval {
    
    private int idCeneval;
    private int idAlumno;
    private Date fechaExamen;
    private String periodo;
    private float puntaje;

    public Ceneval(int idCeneval, int idAlumno, Date fechaExamen, String periodo, float puntaje) {
        this.idCeneval = idCeneval;
        this.idAlumno = idAlumno;
        this.fechaExamen = fechaExamen;
        this.periodo = periodo;
        this.puntaje = puntaje;
    }

    public Ceneval() {
    }

    public int getIdCeneval() {
        return idCeneval;
    }

    public void setIdCeneval(int idCeneval) {
        this.idCeneval = idCeneval;
    }

    public int getIdAlumno() {
        return idAlumno;
    }

    public void setIdAlumno(int idAlumno) {
        this.idAlumno = idAlumno;
    }

    public Date getFechaExamen() {
        return fechaExamen;
    }

    public void setFechaExamen(Date fechaExamen) {
        this.fechaExamen = fechaExamen;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public float getPuntaje() {
        return puntaje;
    }

    public void setPuntaje(float puntaje) {
        this.puntaje = puntaje;
    }
    
            
}
