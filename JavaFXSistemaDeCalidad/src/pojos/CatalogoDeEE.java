/*
Autor: Mario Dorantes
FechaDeCreación: 05/01/2021
 */

package pojos;

public class CatalogoDeEE {
    
    private int idCatalogoDeEE;
    private int idLicenciatura;
    private String programa;
    private String nrc;
    private String nombreDeLaEE;
    private String creditos;
    private String bloque;
    private String periodo;

    public CatalogoDeEE(int idCatalogoDeEE, int idLicenciatura, String programa, String nrc, String nombreDeLaEE, String creditos, String bloque, String periodo) {
        this.idCatalogoDeEE = idCatalogoDeEE;
        this.idLicenciatura = idLicenciatura;
        this.programa = programa;
        this.nrc = nrc;
        this.nombreDeLaEE = nombreDeLaEE;
        this.creditos = creditos;
        this.bloque = bloque;
        this.periodo = periodo;
    }

    public CatalogoDeEE() {
    }

    public int getIdCatalogoDeEE() {
        return idCatalogoDeEE;
    }

    public void setIdCatalogoDeEE(int idCatalogoDeEE) {
        this.idCatalogoDeEE = idCatalogoDeEE;
    }

    public int getIdLicenciatura() {
        return idLicenciatura;
    }

    public void setIdLicenciatura(int idLicenciatura) {
        this.idLicenciatura = idLicenciatura;
    }

    public String getPrograma() {
        return programa;
    }

    public void setPrograma(String programa) {
        this.programa = programa;
    }

    public String getNrc() {
        return nrc;
    }

    public void setNrc(String nrc) {
        this.nrc = nrc;
    }

    public String getNombreDeLaEE() {
        return nombreDeLaEE;
    }

    public void setNombreDeLaEE(String nombreDeLaEE) {
        this.nombreDeLaEE = nombreDeLaEE;
    }

    public String getCreditos() {
        return creditos;
    }

    public void setCreditos(String creditos) {
        this.creditos = creditos;
    }

    public String getBloque() {
        return bloque;
    }

    public void setBloque(String bloque) {
        this.bloque = bloque;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }
    
    
    
}
