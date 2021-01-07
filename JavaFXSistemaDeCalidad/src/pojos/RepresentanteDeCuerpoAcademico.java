/*
Autor: Brandon trujillo
fechaDeCreación: 28/11/2020
 */
package pojos;

public class RepresentanteDeCuerpoAcademico extends PersonalUniversitario {
     private String gradoAcademico;

    public RepresentanteDeCuerpoAcademico(String gradoAcademico, int identificacion, String telefono, String numeroPersonal, String nombre, String correo, String contraseña) {
        super(identificacion, telefono, numeroPersonal, nombre, correo, contraseña);
        this.gradoAcademico = gradoAcademico;
    }

    public RepresentanteDeCuerpoAcademico() {
    }

    public String getGradoAcademico() {
        return gradoAcademico;
    }

    public void setGradoAcademico(String gradoAcademico) {
        this.gradoAcademico = gradoAcademico;
    }

}
