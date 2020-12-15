/*
 * Autor: Mario Dorantes
 * fecha de creación: 15/12/2020
 */
package pojos;

public class Estudiante {
    
    private int idEstudiante;
    private String nombre;
    private String matricula;
    private String correo;

    public Estudiante(int idEstudiante, String nombre, String matricula, String correo) {
        this.idEstudiante = idEstudiante;
        this.nombre = nombre;
        this.matricula = matricula;
        this.correo = correo;
    }

    public Estudiante() {
    }

    public int getIdEstudiante() {
        return idEstudiante;
    }

    public void setIdEstudiante(int idEstudiante) {
        this.idEstudiante = idEstudiante;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }
    
    //Para que se vean en el combo 
    @Override
    public String toString() {
        return nombre;
    }
    
    
}
