/*
 * Autor: Brandon Trujillo
 * fecha: 1/12/2020
 */
package JavaFXGUI.Ventanas;

import conexionBD.ConectarBD;
import interfaz.NotificaCambios;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import pojos.CoordinadorDeAcademia;
import util.Herramientas;
import validaciones.Validaciones;

public class FXMLActualizarCoordinadorController implements Initializable {

    @FXML
    private TextField tfContraseña;
    @FXML
    private TextField tfCorreo;
    @FXML
    private TextField tfTelefono;
    @FXML
    private TextField tfNumeroDePersonal;
    @FXML
    private TextField tfNombre;
    @FXML
    private RadioButton rbDoctorado;
    @FXML
    private ToggleGroup gradosAcademicos;
    @FXML
    private RadioButton rbMaestria;
    @FXML
    private RadioButton rbEspecializacion;
    @FXML
    private RadioButton rbLicenciatura;

    private CoordinadorDeAcademia editarCoordinador;
    Alert mostrarAlerta;
    int idCoordinador = 0;
    boolean registroExitoso = true;
    NotificaCambios notificacion;
    
    String nombreAuxiliar;
    String telefonoAuxiliar;
    String contraseñaAuxiliar;
    String gradoAcademicoAuxiliar;
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void inicializaCampos (NotificaCambios notificacion, CoordinadorDeAcademia editarCoordinador){
        this.notificacion = notificacion;
        this.editarCoordinador = editarCoordinador;
        
        obtenerCoordinadorSeleccionado();
    }

     private void obtenerCoordinadorSeleccionado(){
        idCoordinador = editarCoordinador.getIdentificacion();
        tfNombre.setText(editarCoordinador.getNombre());
        tfNumeroDePersonal.setText(editarCoordinador.getNumeroPersonal());
        tfNumeroDePersonal.setEditable(false);
        tfTelefono.setText(editarCoordinador.getTelefono());
        tfCorreo.setText(editarCoordinador.getCorreo());
        tfCorreo.setEditable(false);
        tfContraseña.setText(editarCoordinador.getContraseña());
        
        if(editarCoordinador.getGradoAcademico().equalsIgnoreCase("Licenciatura")){
            rbLicenciatura.setSelected(true);
        }else if(editarCoordinador.getGradoAcademico().equalsIgnoreCase("Especialización")){
            rbEspecializacion.setSelected(true);
        }else if(editarCoordinador.getGradoAcademico().equalsIgnoreCase("Maestría")){
            rbMaestria.setSelected(true);
        }else if(editarCoordinador.getGradoAcademico().equalsIgnoreCase("Doctorado")){
            rbDoctorado.setSelected(true);
        }
    }
    
    @FXML
    private void cancelar(ActionEvent event) {
        Herramientas.cerrarPantalla(tfContraseña);
    }

    @FXML
    private void clicActualizar(ActionEvent event) {
        tfNombre.setStyle("-fx-border-color: ;");
        tfNumeroDePersonal.setStyle("-fx-border-color: ;");
        tfTelefono.setStyle("-fx-border-color: ;");
        tfCorreo.setStyle("-fx-border-color: ;");
        tfContraseña.setStyle("-fx-border-color: ;");
        
        boolean esCorrecto = true;
        Validaciones datoAValidar = new Validaciones();
        
        nombreAuxiliar = tfNombre.getText();
        telefonoAuxiliar = tfTelefono.getText();
        contraseñaAuxiliar = tfContraseña.getText();
        
        if((nombreAuxiliar.isEmpty()) || (!datoAValidar.validarNombre(nombreAuxiliar))){
            esCorrecto = false;
            tfNombre.setStyle("-fx-border-color: red;");
        }
        if((telefonoAuxiliar.isEmpty()) || (!datoAValidar.validarTelefono(telefonoAuxiliar))){
            esCorrecto = false;
            tfTelefono.setStyle("-fx-border-color: red;");
        }
        if((contraseñaAuxiliar.isEmpty()) || (!datoAValidar.validarContraseña(contraseñaAuxiliar))){
            esCorrecto = false;
            tfContraseña.setStyle("-fx-border-color: red;");
        }
        if(rbLicenciatura.isSelected()){
            gradoAcademicoAuxiliar = rbLicenciatura.getText();
        }else if(rbEspecializacion.isSelected()){
            gradoAcademicoAuxiliar = rbEspecializacion.getText();
        }else if(rbMaestria.isSelected()){
            gradoAcademicoAuxiliar = rbMaestria.getText();
        }else if(rbDoctorado.isSelected()){
            gradoAcademicoAuxiliar = rbDoctorado.getText();
        }
        
        if(esCorrecto){
            if(idCoordinador> 0){
                actualizarAcademico(nombreAuxiliar, telefonoAuxiliar, gradoAcademicoAuxiliar, idCoordinador);
            }else{
                registroExitoso = false;
            }
                
            if(registroExitoso){
                mostrarAlerta = Herramientas.creadorDeAlerta("Mensaje", "Actualizacion exitosa", Alert.AlertType.INFORMATION);
                mostrarAlerta.showAndWait();
                Herramientas.cerrarPantalla(tfNombre);
                notificacion.refrescarTabla(true);
            }else{
                mostrarAlerta = Herramientas.creadorDeAlerta("Error", "No fue posible completar el registro, "
                    + "intente más tarde", Alert.AlertType.ERROR);
                mostrarAlerta.showAndWait();
            } 
                
        }else{
            mostrarAlerta = Herramientas.creadorDeAlerta("Atención", "Campos incorrectos o vacíos,"
                + " verifique por favor", Alert.AlertType.WARNING);
            mostrarAlerta.showAndWait();
        }
    }
    
    private void actualizarAcademico(String nombre, String telefono, String gradoAcademico, int idCoordinador){
        Connection conn = ConectarBD.abrirConexionMySQL();
        if(conn != null){
            try{
                String consulta = "UPDATE academico set nombre = ?,  telefono = ?, "
                    + "gradoAcademico = ? WHERE idAcademico = ?";
                PreparedStatement declaracion = conn.prepareStatement(consulta);
                declaracion.setString(1, nombre);
                declaracion.setString(2, telefono);
                declaracion.setString(3, gradoAcademico);
                declaracion.setInt(4, idCoordinador);
                int resultado = declaracion.executeUpdate();
                if(resultado == 0){
                    registroExitoso = false;
                }else{
                    actualizarUsuario(contraseñaAuxiliar, idCoordinador);
                }
                conn.close();
            }catch(SQLException ex){
                registroExitoso = false;
                mostrarAlerta = Herramientas.creadorDeAlerta("Error de consulta", "No fue posible acceder a la base de datos "
                    + "en este momento, intente más tarde", Alert.AlertType.ERROR);
                mostrarAlerta.showAndWait();
                Herramientas.cerrarPantalla(tfContraseña);
            }
        }else{
            registroExitoso = false;
            mostrarAlerta = Herramientas.creadorDeAlerta("Error de conexión", "No fue posible conectar con la base de datos"
                + "en este momento, intente más tarde", Alert.AlertType.ERROR);
            mostrarAlerta.showAndWait();
            Herramientas.cerrarPantalla(tfContraseña);
        }
    }
    
    private void actualizarUsuario(String contraseña, int idDocente) throws SQLException{
        Connection conn = ConectarBD.abrirConexionMySQL();
        if(conn != null){
                String consulta = "UPDATE usuario set password = ? WHERE idAcademico = ?";
                PreparedStatement declaracion = conn.prepareStatement(consulta);
                declaracion.setString(1, contraseña);
                declaracion.setInt(2, idDocente);
                int resultado = declaracion.executeUpdate();
                if(resultado == 0){
                    registroExitoso = false;
                }
                conn.close();
        }else{
            registroExitoso = false;
            mostrarAlerta = Herramientas.creadorDeAlerta("Error de conexión", "No fue posible conectar con la base de datos"
                + "en este momento, intente más tarde", Alert.AlertType.ERROR);
            mostrarAlerta.showAndWait();
            Herramientas.cerrarPantalla(tfContraseña);
        }
    }
    
}
