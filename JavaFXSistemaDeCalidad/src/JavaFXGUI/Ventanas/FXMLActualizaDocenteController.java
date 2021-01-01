/*
 *Autor: Brandon Trujillo
 *fechaCreación: 28/11/2020
 */
package JavaFXGUI.Ventanas;

import conexionBD.ConectarBD;
import interfaz.NotificaCambios;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import pojos.Docente;
import util.Herramientas;

public class FXMLActualizaDocenteController implements Initializable {
    
    private Docente editarDocente;
    @FXML
    private TextField tfNombre;
    @FXML
    private TextField tfNumeroDePersonal;
    @FXML
    private TextField tfTelefono;
    @FXML
    private TextField tfCorreo;
    @FXML
    private TextField tfContraseña;
    
    Alert mostrarAlerta;
    
    int idDocente = 0;
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
    
    @FXML
    private void cancelar(ActionEvent e){
        Herramientas.cerrarPantalla(tfNombre);
    }
    
    public void inicializaCampos(NotificaCambios notificacion, Docente editarDocente){
        this.notificacion = notificacion;
        this.editarDocente = editarDocente;
        
        obtenerDocenteSeleccionado();
    }
    
    private void obtenerDocenteSeleccionado(){
        idDocente = editarDocente.getIdentificacion();
        tfNombre.setText(editarDocente.getNombre());
        tfNumeroDePersonal.setText(editarDocente.getNumeroPersonal());
        tfTelefono.setText(editarDocente.getTelefono());
        tfCorreo.setText(editarDocente.getCorreo());
        tfContraseña.setText(editarDocente.getContraseña());
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
            if(idDocente > 0){
                actualizarAcademico(nombreAuxiliar, numeroPersonalAuxiliar, telefonoAuxiliar, idDocente);
            }else{
                registroExitoso = false;
                mostrarAlerta = Herramientas.creadorDeAlerta("Error", "No fue posible completar el registro, "
                    + "intente más tarde", Alert.AlertType.ERROR);
                mostrarAlerta.showAndWait();
            }
            
        }
    }
    
    private void actualizarAcademico(String nombre, String numeroDePersonal, String telefono, int idDocente){
        Connection conn = ConectarBD.abrirConexionMySQL();
        if(conn != null){
            try{
                String consulta = "UPDATE academico set nombre = ?, numeroPersonal = ?, telefono = ? WHERE idAcademico = ?";
                PreparedStatement declaracion = conn.prepareStatement(consulta);
                declaracion.setString(1, nombre);
                declaracion.setString(2, numeroDePersonal);
                declaracion.setString(3, telefono);
                declaracion.setInt(4, idDocente);
                int resultado = declaracion.executeUpdate();
                if(resultado == 0){
                    registroExitoso = false;
                }else{
                    actualizarUsuario(correoAuxiliar, contraseñaAuxiliar, idDocente);
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
    
    private void actualizarUsuario(String correo, String contraseña, int idDocente) throws SQLException{
         Connection conn = ConectarBD.abrirConexionMySQL();
        if(conn != null){
                String consulta = "UPDATE usuario set correo = ?, password = ? WHERE idAcademico = ?";
                PreparedStatement declaracion = conn.prepareStatement(consulta);
                declaracion.setString(1, correo);
                declaracion.setString(2, contraseña);
                declaracion.setInt(3, idDocente);
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
