/*
 * Autor: Mario Dorantes
 * fecha de creación: 10/01/2020.
 */
package validaciones;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Validaciones {
    
    private boolean nombreValido;
    private boolean periodoValido;
    private boolean puntajeObtenidoValido;
    private boolean nombreAcademiaValido;
    private boolean programaValido;
    private boolean nrcValido;
    private boolean creditosValido;
    private boolean bloqueValido;
    private boolean nombreDeLaEEValido;
    private boolean correoValido;
    private boolean matriculaValida;
    private boolean estatusValido;
    private boolean textoValido;
    private boolean claveValida;
    private boolean gradoConsolidacionValido;
    private boolean telefonoValido;
    private boolean contraseñaValida;
    private boolean numeroPersonalValido;
    
    
    public boolean validarNombre (String nombre) {
        return nombreValido = nombre.matches("^([A-ZÑÁÉÍÓÚ]{1}[a-zñáéíóú]+[ ]?){2,4}$");
    } 
    
    public boolean validarFecha(String fecha){
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        formato.setLenient(false);
        try {
           formato.parse(fecha);
           return true;
        } catch (ParseException ex) {
           return false;
        }
    }
    
    public boolean validarPeriodo (String periodo){
        return periodoValido = periodo.matches("^[0-9]{6}$");
    }
    
    public boolean validarPuntajeObtenido (String puntajeObtenido){
        return puntajeObtenidoValido = puntajeObtenido.matches("^[0-9]{1,2}[.]{1}[0-9]{2}$");
    }
    
    public boolean validarNombreAcademia (String nombreAcademia){
        return nombreAcademiaValido = nombreAcademia.matches("^([A-ZÑÁÉÍÓÚa-zñáéíóú]+[ ]?)+$");
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
        return nombreDeLaEEValido = nombreDeLaEE.matches("^([A-ZÑÁÉÍÓÚa-zñáéíóú]+[ ]?)+$");
    }
    
    public boolean validarCorreo (String correo){
        return correoValido = correo.matches("^[(A-Z)(a-z)(0-9)]{5,20}[@]{1}[g|m|a|i|l|e|s|t|u|d|n]+[.|c|o|m|x|u|v]{3,6}$");
    }
    
    public boolean validarMatricula (String matricula){
        return matriculaValida = matricula.matches("^[z|Z]{1}[s|S]{1}[0-9]{8}$");
    }
    
    public boolean validarEstatus(String estatus){
        return estatusValido = estatus.matches("^[Activo|Inactivo]{6,8}$");
    }
    
    public boolean validarTextos(String texto){
        return textoValido = texto.matches("^[A-ZÑÁÉÍÓa-zñáéíóú0-9.,:; ]{1,200}$");
    }
    
    public boolean validarClave(String clave){
        return claveValida = clave.matches("^[UV]{2}[0-9]{1,3}$");
    }
    
    public boolean validarGrado(String grado){
        return gradoConsolidacionValido = grado.matches("^[0-9]{1}$");
    }
    
    public boolean validarTelefono(String telefono){
        return telefonoValido = telefono.matches("^[0-9]{10}$");
    }
    
    public boolean validarContraseña(String contraseña){
        return contraseñaValida = contraseña.matches("^[A-ZÑÁÉÍÓa-zñáéíóú0-9.,:; -_#*+]{8,10}$");
    }
    
    public boolean validarNumeroDePersonal(String numeroDePersonal){
        return numeroPersonalValido = numeroDePersonal.matches("^[0-9]{5}$");
    }
     
}
