/*
 * Autor: Brandon Trujillo
 * fecha: 03/01/2021
 */
package pojos;

import java.util.Date;

public class CatalogoDeCuerpoAcademico extends Catalogo{
    private String descripcion;
    private String mision;
    private String nombreResponsable;

    public CatalogoDeCuerpoAcademico(String descripcion, String mision, String nombreResponsable, int identificacion, String nombre, String estatus, Date fechaRegistro) {
        super(identificacion, nombre, estatus, fechaRegistro);
        this.descripcion = descripcion;
        this.mision = mision;
        this.nombreResponsable = nombreResponsable;
    }

    public CatalogoDeCuerpoAcademico() {
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getMision() {
        return mision;
    }

    public void setMision(String mision) {
        this.mision = mision;
    }

    public String getNombreResponsable() {
        return nombreResponsable;
    }

    public void setNombreResponsable(String nombreResponsable) {
        this.nombreResponsable = nombreResponsable;
    }   

}
