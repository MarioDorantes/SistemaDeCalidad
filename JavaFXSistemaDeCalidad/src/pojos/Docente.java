/*
 * Autor: Brandon Trujillo
 * fecha de creación: 09/12/2020
 */
package pojos;

public class Docente {
    private int idDocente;
    private String numeroDePersonal;
    private String nombre;
    private String correo;
    private String contraseña;
    private String telefono;

    public Docente(int idDocente, String numeroDePersonal, String nombre, String correo, String contraseña, String telefono) {
        this.idDocente = idDocente;
        this.numeroDePersonal = numeroDePersonal;
        this.nombre = nombre;
        this.correo = correo;
        this.contraseña = contraseña;
        this.telefono = telefono;
    }

    public Docente() {
    }

    public int getIdDocente() {
        return idDocente;
    }

    public void setIdDocente(int idDocente) {
        this.idDocente = idDocente;
    }

    public String getNumeroDePersonal() {
        return numeroDePersonal;
    }

    public void setNumeroDePersonal(String numeroDePersonal) {
        this.numeroDePersonal = numeroDePersonal;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }    
}
