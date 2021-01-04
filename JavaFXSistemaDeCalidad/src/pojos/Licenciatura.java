/*
 * Autor: Mario Dorantes
 * fecha de creación: 03/01/2021
 */
package pojos;

public class Licenciatura {
    
    private int idLicenciatura;
    private String nombreLicenciatura;

    public Licenciatura(int idLicenciatura, String nombreLicenciatura) {
        this.idLicenciatura = idLicenciatura;
        this.nombreLicenciatura = nombreLicenciatura;
    }

    public Licenciatura() {
    }

    public int getIdLicenciatura() {
        return idLicenciatura;
    }

    public void setIdLicenciatura(int idLicenciatura) {
        this.idLicenciatura = idLicenciatura;
    }

    public String getNombreLicenciatura() {
        return nombreLicenciatura;
    }

    public void setNombreLicenciatura(String nombreLicenciatura) {
        this.nombreLicenciatura = nombreLicenciatura;
    }

    @Override
    public String toString() {
        return nombreLicenciatura;
    }
    
    
    
}
