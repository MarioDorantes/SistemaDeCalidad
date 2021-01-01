/*
 * Autor: Brandon Trujillo
 * fecha: 01/01/2020
 */
package pojos;

public class PersonalUniversitario {
    private int identificacion;
    private String telefono;
    private String numeroPersonal;
    private String nombre;

    public PersonalUniversitario(int identificacion, String telefono, String numeroPersonal, String nombre) {
        this.identificacion = identificacion;
        this.telefono = telefono;
        this.numeroPersonal = numeroPersonal;
        this.nombre = nombre;
    }

    public PersonalUniversitario() {
    }

    public int getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(int identificacion) {
        this.identificacion = identificacion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getNumeroPersonal() {
        return numeroPersonal;
    }

    public void setNumeroPersonal(String numeroPersonal) {
        this.numeroPersonal = numeroPersonal;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

   
    
}
