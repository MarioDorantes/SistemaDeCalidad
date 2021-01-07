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
    private Date fecha;

    public Catalogo(int identificacion, String nombre, String estatus, Date fecha) {
        this.identificacion = identificacion;
        this.nombre = nombre;
        this.estatus = estatus;
        this.fecha = fecha;
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

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    @Override
    public String toString() {
        return nombre;
    }
    
    
}
