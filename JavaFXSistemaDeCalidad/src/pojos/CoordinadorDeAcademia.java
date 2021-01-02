/*
 * Autor: Brandon Trujillo
 * fecha: 01/01/2021
 */
package pojos;

public class CoordinadorDeAcademia extends PersonalUniversitario {
    private String correo;
    private String contraseña;

    public CoordinadorDeAcademia(String correo, String contraseña, int identificacion, String telefono, String numeroPersonal, String nombre) {
        super(identificacion, telefono, numeroPersonal, nombre);
        this.correo = correo;
        this.contraseña = contraseña;
    }

    public CoordinadorDeAcademia() {
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
