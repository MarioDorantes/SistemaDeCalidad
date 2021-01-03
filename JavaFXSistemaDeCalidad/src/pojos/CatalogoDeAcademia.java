/*
 * Autor: Mario Dorantes
 * fecha de creación: 26/12/2020
 */
package pojos;

public class CatalogoDeAcademia {
    
    private int idCatalogoDeAcademia;
    private String nombreLicenciatura;
    private String nombreAcademia;
    private String nombreCoordinador;
    private String estatus;

    public CatalogoDeAcademia(int idCatalogoDeAcademia, String nombreLicenciatura, String nombreAcademia, String nombreCoordinador, String estatus) {
        this.idCatalogoDeAcademia = idCatalogoDeAcademia;
        this.nombreLicenciatura = nombreLicenciatura;
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

    public String getNombreLicenciatura() {
        return nombreLicenciatura;
    }

    public void setNombreLicenciatura(String nombreLicenciatura) {
        this.nombreLicenciatura = nombreLicenciatura;
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
      
    @Override
    public String toString() {
        return nombreLicenciatura;
    }
    
}
