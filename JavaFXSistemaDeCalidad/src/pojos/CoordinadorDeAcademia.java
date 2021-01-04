/*
 * Autor: Brandon Trujillo
 * fecha: 01/01/2021
 */
package pojos;

public class CoordinadorDeAcademia extends PersonalUniversitario {
   private String gradoAcademico;

    public CoordinadorDeAcademia(String gradoAcademico, int identificacion, String telefono, String numeroPersonal, String nombre, String correo, String contraseña) {
        super(identificacion, telefono, numeroPersonal, nombre, correo, contraseña);
        this.gradoAcademico = gradoAcademico;
    }

    public CoordinadorDeAcademia() {
    }

    public String getGradoAcademico() {
        return gradoAcademico;
    }

    public void setGradoAcademico(String gradoAcademico) {
        this.gradoAcademico = gradoAcademico;
    }
}
