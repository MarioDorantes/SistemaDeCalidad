/*
 *Autor: Brandon Trujillo
 *fechaCreación: 02/12/2020
 */
package JavaFXGUI.Ventanas;

import conexionBD.ConectarBD;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import util.Herramientas;

public class FXMLRegistrarDocenteController implements Initializable {

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
    boolean registroExitoso = true;
    String nombreAuxiliar;
    String numeroPersonalAuxiliar;
    String telefonoAuxiliar;
    String correoAuxiliar;
    String contraseñaAuxiliar;
    int idRol = 0;
    int idAcademico = 0;
  
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    
    @FXML
    private void cancelar(ActionEvent event) {
        Herramientas.cerrarPantalla(tfNombre);
    }

    @FXML
    private void clicRegistrar(ActionEvent event) {
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
            registrarAcademico(numeroPersonalAuxiliar, nombreAuxiliar, telefonoAuxiliar);
            if(idRol > 0 && idAcademico > 0){
                registrarUsuario(correoAuxiliar, contraseñaAuxiliar, idRol, idAcademico);
            }else{
                mostrarAlerta = Herramientas.creadorDeAlerta("Error", "No fue posible completar el registro, "
                    + "intente más tarde", Alert.AlertType.ERROR);
                mostrarAlerta.showAndWait();
            }
            
            if(registroExitoso){
                mostrarAlerta = Herramientas.creadorDeAlerta("Mensaje", "Registro exitoso", Alert.AlertType.INFORMATION);
                mostrarAlerta.showAndWait();
                Herramientas.cerrarPantalla(tfNombre);
            }else{
                mostrarAlerta = Herramientas.creadorDeAlerta("Error", "No fue posible completar el registro, "
                    + "intente más tarde", Alert.AlertType.ERROR);
                mostrarAlerta.showAndWait();
            }
        }
    }
    
    private void registrarAcademico(String numeroDePersonal, String nombre, String telefono){
        Connection conn = ConectarBD.abrirConexionMySQL();
        if(conn != null){
            try{
                String consulta = "INSERT INTO academico(numeroPersonal, nombre, telefono) VALUES (?, ?, ?)";
                PreparedStatement declaracion = conn.prepareStatement(consulta);
                declaracion.setString(1, numeroDePersonal);
                declaracion.setString(2, nombre);
                declaracion.setString(3, telefono);
                int resultado = declaracion.executeUpdate();
                if(resultado == 0){
                    registroExitoso = false;
                }else{
                    registrarRolAcademico();
                }
                conn.close();
            }catch(SQLException ex){
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
    
    private void registrarRolAcademico() throws SQLException{
        Connection conn = ConectarBD.abrirConexionMySQL();
        if(conn != null){
                String consulta = "INSERT INTO rol (tipoRol) VALUES ('Docente')";
                PreparedStatement declaracion = conn.prepareStatement(consulta);
                int resultado = declaracion.executeUpdate();
                if(resultado == 0){
                    registroExitoso = false;
                }else{
                    obtenerIdAcademico(numeroPersonalAuxiliar);
                    obtenerIdRolAcademico(idAcademico);
                }
                conn.close();
        }else{
            mostrarAlerta = Herramientas.creadorDeAlerta("Error de conexión", "No fue posible conectar con la base de datos"
                + "en este momento, intente más tarde", Alert.AlertType.ERROR);
            mostrarAlerta.showAndWait();
        }
    }
        
    private int obtenerIdAcademico(String numeroDePersonal) throws SQLException{
        Connection conn = ConectarBD.abrirConexionMySQL();
        if(conn != null){
                String consulta = "SELECT idAcademico FROM academico WHERE numeroPersonal = ?";
                PreparedStatement declaracion = conn.prepareStatement(consulta);
                declaracion.setString(1, numeroDePersonal);
                ResultSet resultado = declaracion.executeQuery();
                if(resultado.next()){
                    idAcademico = resultado.getInt("idAcademico");
                }
                conn.close();
        }else{
            mostrarAlerta = Herramientas.creadorDeAlerta("Error de conexión", "No fue posible conectar con la base de datos"
                + "en este momento, intente más tarde", Alert.AlertType.ERROR);
            mostrarAlerta.showAndWait();
        }
        return idAcademico;
    }
    
    private int obtenerIdRolAcademico(int idRolAcademico) throws SQLException{
        Connection conn = ConectarBD.abrirConexionMySQL();
        if(conn != null){
                String consulta = "SELECT idRol FROM rol WHERE idRol = ?";
                PreparedStatement declaracion = conn.prepareStatement(consulta);
                declaracion.setInt(1, idRolAcademico);
                ResultSet resultado = declaracion.executeQuery();
                if(resultado.next()){
                    idRol = resultado.getInt("idRol");
                }
                conn.close();
        }else{
            mostrarAlerta = Herramientas.creadorDeAlerta("Error de conexión", "No fue posible conectar con la base de datos"
                + "en este momento, intente más tarde", Alert.AlertType.ERROR);
            mostrarAlerta.showAndWait();
        }
        return idRol;
    }
    
    private void registrarUsuario(String correo, String contraseña, int idRol, int idAcademico){
        Connection conn = ConectarBD.abrirConexionMySQL();
        if(conn != null){
            try{
                String consulta = "INSERT INTO usuario(correo, password, idRol, idAcademico) VALUES (?, ?, ?, ?)";
                PreparedStatement declaracion = conn.prepareStatement(consulta);
                declaracion.setString(1, correo);
                declaracion.setString(2, contraseña);
                declaracion.setInt(3, idRol);
                declaracion.setInt(4, idAcademico);
                int resultado = declaracion.executeUpdate();
                if(resultado == 0){
                    registroExitoso = false;
                }
                conn.close();
            }catch(SQLException ex){
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
    
}
