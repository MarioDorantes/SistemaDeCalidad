/*
 * Autor: Mario Dorantes
 * fecha de creación: 10/01/2020.
 */
package validaciones;


public class Validaciones {
    
    private boolean nombreValido;
    private boolean fechaExamenValida;
    private boolean periodoValido;
    private boolean puntajeObtenidoValido;
    private boolean nombreAcademiaValido;
    private boolean programaValido;
    private boolean nrcValido;
    private boolean creditosValido;
    private boolean bloqueValido;
    private boolean nombreDeLaEEValido;
    
    
    public boolean validarNombre (String nombre) {
        return nombreValido = nombre.matches("^([A-ZÑ]{1}[a-zñ]+[ ]?){2,4}$");
    } 
    
    public boolean validarFechaExamen (String fecha){
        return fechaExamenValida = fecha.matches("^[0-9]{4}[-]{1}[0-9]{1,2}[-]{1}[0-9]{1,2}$");
    }
    
    public boolean validarPeriodo (String periodo){
        return periodoValido = periodo.matches("^[0-9]{6}$");
    }
    
    public boolean validarPuntajeObtenido (String puntajeObtenido){
        return puntajeObtenidoValido = puntajeObtenido.matches("^[0-9]{1,2}[.]{1}[0-9]{2}$");
    }
    
    public boolean validarNombreAcademia (String nombreAcademia){
        return nombreAcademiaValido = nombreAcademia.matches("^([A-ZÑa-zñ]+[ ]?)+$");
    }
    
    public boolean validarPrograma (String programa){
        return programaValido = programa.matches("^[A-ZÑ]{4}[-]{1}[0-9]{2}[-]{1}[A-ZÑ]{1}[-]{1}[A-ZÑ]{2}$");
    }
    
    public boolean validarNrc (String nrc){
        return nrcValido = nrc.matches("^[0-9]{5}$");
    }
    
    public boolean validarCreditos (String creditos){
        return creditosValido = creditos.matches("^[0-9]{1,2}$");
    }
    
    public boolean validarBloque (String bloque){
        return bloqueValido = bloque.matches("^[1|2]{1}$");
    }
    
    public boolean validarNombreDeLaEE (String nombreDeLaEE){
        return nombreDeLaEEValido = nombreDeLaEE.matches("^([A-ZÑa-zñ]+[ ]?)+$");
    }
     
}
