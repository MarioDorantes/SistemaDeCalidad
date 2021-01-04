/*
 * Autor: Brandon Trujillo
 * fecha de creación: 09/12/2020
 */
package pojos;

public class Docente extends PersonalUniversitario{
    private String gradoAcademico;

    public Docente(String gradoAcademico, int identificacion, String telefono, String numeroPersonal, String nombre, String correo, String contraseña) {
        super(identificacion, telefono, numeroPersonal, nombre, correo, contraseña);
        this.gradoAcademico = gradoAcademico;
    }

    public Docente() {
    }

    public String getGradoAcademico() {
        return gradoAcademico;
    }

    public void setGradoAcademico(String gradoAcademico) {
        this.gradoAcademico = gradoAcademico;
    }

}
