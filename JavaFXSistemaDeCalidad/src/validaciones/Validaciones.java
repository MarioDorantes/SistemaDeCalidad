/*
 * Autor: Mario Dorantes
 * fecha de creación: 10/01/2020.
 */
package validaciones;


public class Validaciones {
    
    /*Recomiendo poner aqui el metodo de validacion ya cuando funcione al 100, 
    mientras en otro proyecto hay que probar para evitar conflictos en el repo*/
    
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
        return nombreValido = nombre.matches("^([A-Z]{1}[a-z]+[ ]?){2,4}$");
    } 
    
    
 
   
    
    
}
