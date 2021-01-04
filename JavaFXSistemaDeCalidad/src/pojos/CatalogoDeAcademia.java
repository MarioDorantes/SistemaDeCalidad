/*
 * Autor: Mario Dorantes
 * fecha de creación: 26/12/2020
 */
package pojos;

public class CatalogoDeAcademia {
    
    private int idCatalogoDeAcademia;
    private int idLicenciatura;
    private String nombreAcademia;
    private String nombreCoordinador;
    private String estatus;

    public CatalogoDeAcademia(int idCatalogoDeAcademia, int idLicenciatura, String nombreAcademia, String nombreCoordinador, String estatus) {
        this.idCatalogoDeAcademia = idCatalogoDeAcademia;
        this.idLicenciatura = idLicenciatura;
        this.nombreAcademia = nombreAcademia;
        this.nombreCoordinador = nombreCoordinador;
        this.estatus = estatus;
    }

    public CatalogoDeAcademia() {
    }

    public int getIdCatalogoDeAcademia() {
        return idCatalogoDeAcademia;
    }

    public void setIdCatalogoDeAcademia(int idCatalogoDeAcademia) {
        this.idCatalogoDeAcademia = idCatalogoDeAcademia;
    }

    public int getIdLicenciatura() {
        return idLicenciatura;
    }

    public void setIdLicenciatura(int idLicenciatura) {
        this.idLicenciatura = idLicenciatura;
    }

    public String getNombreAcademia() {
        return nombreAcademia;
    }

    public void setNombreAcademia(String nombreAcademia) {
        this.nombreAcademia = nombreAcademia;
    }

    public String getNombreCoordinador() {
        return nombreCoordinador;
    }

    public void setNombreCoordinador(String nombreCoordinador) {
        this.nombreCoordinador = nombreCoordinador;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

}
