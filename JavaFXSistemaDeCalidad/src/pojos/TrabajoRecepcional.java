/*
 * Autor: Brandon Trujillo
 * fecha: 1/12/2020
 */
package pojos;

import java.util.Date;

public class TrabajoRecepcional {
    private int identificacion;
    private int idAlumno;
    private int idDirector;
    private int idCoDirector;
    private int idSinodal;
    private String nombre;
    private Date fechaRegistro;
    private String descripcion;
    private String nombreAlumno;
    private String nombreDirector;
    private String nombreCoDirector;
    private String nombreSinodal;
    private String estatus;

    public TrabajoRecepcional(int identificacion, int idAlumno, int idDirector, int idCoDirector, int idSinodal, String nombre, Date fechaRegistro, String descripcion, String nombreAlumno, String nombreDirector, String nombreCoDirector, String nombreSinodal, String estatus) {
        this.identificacion = identificacion;
        this.idAlumno = idAlumno;
        this.idDirector = idDirector;
        this.idCoDirector = idCoDirector;
        this.idSinodal = idSinodal;
        this.nombre = nombre;
        this.fechaRegistro = fechaRegistro;
        this.descripcion = descripcion;
        this.nombreAlumno = nombreAlumno;
        this.nombreDirector = nombreDirector;
        this.nombreCoDirector = nombreCoDirector;
        this.nombreSinodal = nombreSinodal;
        this.estatus = estatus;
    }

    public TrabajoRecepcional() {
    }

    public int getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(int identificacion) {
        this.identificacion = identificacion;
    }

    public int getIdAlumno() {
        return idAlumno;
    }

    public void setIdAlumno(int idAlumno) {
        this.idAlumno = idAlumno;
    }

    public int getIdDirector() {
        return idDirector;
    }

    public void setIdDirector(int idDirector) {
        this.idDirector = idDirector;
    }

    public int getIdCoDirector() {
        return idCoDirector;
    }

    public void setIdCoDirector(int idCoDirector) {
        this.idCoDirector = idCoDirector;
    }

    public int getIdSinodal() {
        return idSinodal;
    }

    public void setIdSinodal(int idSinodal) {
        this.idSinodal = idSinodal;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getNombreAlumno() {
        return nombreAlumno;
    }

    public void setNombreAlumno(String nombreAlumno) {
        this.nombreAlumno = nombreAlumno;
    }

    public String getNombreDirector() {
        return nombreDirector;
    }

    public void setNombreDirector(String nombreDirector) {
        this.nombreDirector = nombreDirector;
    }

    public String getNombreCoDirector() {
        return nombreCoDirector;
    }

    public void setNombreCoDirector(String nombreCoDirector) {
        this.nombreCoDirector = nombreCoDirector;
    }

    public String getNombreSinodal() {
        return nombreSinodal;
    }

    public void setNombreSinodal(String nombreSinodal) {
        this.nombreSinodal = nombreSinodal;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    
}
