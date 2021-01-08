/*
 * Autor: Brandon Trujillo
 * fecha: 03/01/2021
 */
package pojos;

import java.util.Date;

public class CatalogoDeCuerpoAcademico extends Catalogo{
    private String descripcion;
    private String mision;
    private String responsable;
    private int idLgca;

    public CatalogoDeCuerpoAcademico(String descripcion, String mision, String responsable, int idLgca, int identificacion, String nombre, String estatus, Date fecha) {
        super(identificacion, nombre, estatus, fecha);
        this.descripcion = descripcion;
        this.mision = mision;
        this.responsable = responsable;
        this.idLgca = idLgca;
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

    public String getResponsable() {
        return responsable;
    }

    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }

    public int getIdLgca() {
        return idLgca;
    }

    public void setIdLgca(int idLgca) {
        this.idLgca = idLgca;
    }

  
}
