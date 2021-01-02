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
import javafx.scene.control.TextField;
import pojos.CoordinadorDeAcademia;
import util.Herramientas;

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

    private CoordinadorDeAcademia editarCoordinador;
    Alert mostrarAlerta;
    int idCoordinador = 0;
    boolean registroExitoso = true;
    NotificaCambios notificacion;
    
    String nombreAuxiliar;
    String numeroPersonalAuxiliar;
    String telefonoAuxiliar;
    String correoAuxiliar;
    String contraseñaAuxiliar;
    
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
        tfTelefono.setText(editarCoordinador.getTelefono());
        tfCorreo.setText(editarCoordinador.getCorreo());
        tfContraseña.setText(editarCoordinador.getContraseña());
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
        nombreAuxiliar = tfNombre.getText();
        numeroPersonalAuxiliar = tfNumeroDePersonal.getText();
        telefonoAuxiliar = tfTelefono.getText();
        correoAuxiliar = tfCorreo.getText();
        contraseñaAuxiliar = tfContraseña.getText();
        
        if(nombreAuxiliar.isEmpty()){
            esCorrecto = false;
            tfNombre.setStyle("-fx-border-color: red;");
        }
        if(numeroPersonalAuxiliar.isEmpty()){
            esCorrecto = false;
            tfNumeroDePersonal.setStyle("-fx-border-color: red;");
        }
        if(telefonoAuxiliar.isEmpty()){
            esCorrecto = false;
            tfTelefono.setStyle("-fx-border-color: red;");
        }
        if(correoAuxiliar.isEmpty()){
            esCorrecto = false;
            tfCorreo.setStyle("-fx-border-color: red;");
        }
        if(contraseñaAuxiliar.isEmpty()){
            esCorrecto = false;
            tfContraseña.setStyle("-fx-border-color: red;");
        }
        
        if(esCorrecto){
            if(idCoordinador > 0){
                actualizarAcademico(nombreAuxiliar, numeroPersonalAuxiliar, telefonoAuxiliar, idCoordinador);
            }else{
                registroExitoso = false;
                mostrarAlerta = Herramientas.creadorDeAlerta("Error", "No fue posible completar el registro, "
                    + "intente más tarde", Alert.AlertType.ERROR);
                mostrarAlerta.showAndWait();
            }
            
        }
    }
    
    private void actualizarAcademico(String nombre, String numeroDePersonal, String telefono, int idCoordinador){
        Connection conn = ConectarBD.abrirConexionMySQL();
        if(conn != null){
            try{
                String consulta = "UPDATE academico set nombre = ?, numeroPersonal = ?, telefono = ? WHERE idAcademico = ?";
                PreparedStatement declaracion = conn.prepareStatement(consulta);
                declaracion.setString(1, nombre);
                declaracion.setString(2, numeroDePersonal);
                declaracion.setString(3, telefono);
                declaracion.setInt(4, idCoordinador);
                int resultado = declaracion.executeUpdate();
                if(resultado == 0){
                    registroExitoso = false;
                }else{
                    actualizarUsuario(correoAuxiliar, contraseñaAuxiliar, idCoordinador);
                }
                conn.close();
            }catch(SQLException ex){
                registroExitoso = false;
                mostrarAlerta = Herramientas.creadorDeAlerta("Error de consulta", "No fue posible acceder a la base de datos "
                    + "en este momento, intente más tarde", Alert.AlertType.ERROR);
                mostrarAlerta.showAndWait();
            }
        }else{
            mostrarAlerta = Herramientas.creadorDeAlerta("Error de conexión", "No fue posible conectar con la base de datos"
                + "en este momento, intente más tarde", Alert.AlertType.ERROR);
            mostrarAlerta.showAndWait();
        }
    }
    
    private void actualizarUsuario(String correo, String contraseña, int idCoordinador) throws SQLException{
         Connection conn = ConectarBD.abrirConexionMySQL();
        if(conn != null){
                String consulta = "UPDATE usuario set correo = ?, password = ? WHERE idAcademico = ?";
                PreparedStatement declaracion = conn.prepareStatement(consulta);
                declaracion.setString(1, correo);
                declaracion.setString(2, contraseña);
                declaracion.setInt(3, idCoordinador);
                int resultado = declaracion.executeUpdate();
                if(resultado == 0){
                    registroExitoso = false;
                }else{       
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
                }
                conn.close();
        }else{
            mostrarAlerta = Herramientas.creadorDeAlerta("Error de conexión", "No fue posible conectar con la base de datos"
                + "en este momento, intente más tarde", Alert.AlertType.ERROR);
            mostrarAlerta.showAndWait();
        }
    }
    
}
