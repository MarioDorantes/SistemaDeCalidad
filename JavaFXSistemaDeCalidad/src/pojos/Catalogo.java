/*
 * Autor: Brandon Trujillo
 * fecha: 04/01/2021
 */
package pojos;

import java.util.Date;

public class Catalogo {
    private int identificacion;
    private String nombre;
    private String estatus;
    private Date fechaRegistro;

    public Catalogo(int identificacion, String nombre, String estatus, Date fechaRegistro) {
        this.identificacion = identificacion;
        this.nombre = nombre;
        this.estatus = estatus;
        this.fechaRegistro = fechaRegistro;
    }

    public Catalogo() {
    }

    public int getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(int identificacion) {
        this.identificacion = identificacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    @Override
    public String toString() {
        return nombre;
    }
    
    
}
