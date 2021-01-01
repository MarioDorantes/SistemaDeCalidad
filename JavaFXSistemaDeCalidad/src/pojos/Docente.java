/*
 * Autor: Brandon Trujillo
 * fecha de creación: 09/12/2020
 */
package pojos;

public class Docente extends PersonalUniversitario{
    private String correo;
    private String contraseña;

    public Docente(int identificacion, String telefono, String numeroPersonal, String nombre) {
        super(identificacion, telefono, numeroPersonal, nombre);
    }

    public Docente(String correo, String contraseña) {
        this.correo = correo;
        this.contraseña = contraseña;
    }

    public Docente() {
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

    
    
}
