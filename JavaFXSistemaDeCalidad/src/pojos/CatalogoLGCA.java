/*
 * Autor: Brandon Trujillo
 * fecha: 03/01/2021
 */
package pojos;

import java.util.Date;

public class CatalogoLGCA extends Catalogo {
    private String clave;
    private String adscripcion;
    private String grado;
    private String unidadAdscripcion;

    public CatalogoLGCA(String clave, String adscripcion, String grado, String unidadAdscripcion, int identificacion, String nombre, String estatus, Date fecha) {
        super(identificacion, nombre, estatus, fecha);
        this.clave = clave;
        this.adscripcion = adscripcion;
        this.grado = grado;
        this.unidadAdscripcion = unidadAdscripcion;
    }
    
    public CatalogoLGCA() {
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getAdscripcion() {
        return adscripcion;
    }

    public void setAdscripcion(String adscripcion) {
        this.adscripcion = adscripcion;
    }

    public String getGrado() {
        return grado;
    }

    public void setGrado(String grado) {
        this.grado = grado;
    }

    public String getUnidadAdscripcion() {
        return unidadAdscripcion;
    }

    public void setUnidadAdscripcion(String unidadAdscripcion) {
        this.unidadAdscripcion = unidadAdscripcion;
    }
}
