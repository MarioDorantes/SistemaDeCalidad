/*
 * Autor: Brandon Trujillo
 * fecha: 16/12/2020
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
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import util.Herramientas;

public class FXMLInicioDeSesionController implements Initializable {

    @FXML
    private TextField tfCorreo;
    @FXML
    private PasswordField pfContraseña;
    @FXML
    private Label lbMensajeCorreo;
    @FXML
    private Label lbMensajeContraseña;

    Alert mostrarAlerta;
    String rol = "";
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void clicIniciarSesion(ActionEvent event) {
        lbMensajeCorreo.setText("");
        lbMensajeContraseña.setText("");
        String correo = tfCorreo.getText();
        String contraseña = pfContraseña.getText();
        boolean isCorrecto = true;
        
        if (correo.isEmpty()){
            lbMensajeCorreo.setText("Campo usuario obligatorio");
            isCorrecto = false;
        }
        
        if (contraseña.isEmpty()){
            lbMensajeContraseña.setText("Campo contraseña obligatorio");
            isCorrecto = false;
        }
        
        if(isCorrecto){
            verificarUsuario(correo, contraseña);
        }
    }
    
    private void verificarUsuario(String correo, String contraseña){
        Connection conn = ConectarBD.abrirConexionMySQL();
        if(conn != null){
            try{
                String consulta = "select correo, password, rol.tipoRol from usuario inner join rol "
                    + "on correo = ? and password = ? and usuario.idUsuario = rol.idRol";
                PreparedStatement declaracion = conn.prepareStatement(consulta);
                declaracion.setString(1, correo);
                declaracion.setString(2, contraseña);
                ResultSet resultado = declaracion.executeQuery();
                if(resultado.next()){
                    rol = resultado.getString("rol.tipoRol");
                    mostrarAlerta = Herramientas.creadorDeAlerta("Usuario encontrado", "Bienvenido",  Alert.AlertType.INFORMATION);
                    mostrarAlerta.showAndWait();
                    irAVentanaCorrespondiente();
                }else{
                    mostrarAlerta = Herramientas.creadorDeAlerta("Datos incorrectos", "Verifique por favor", Alert.AlertType.ERROR);
                    mostrarAlerta.showAndWait();
                }
            }catch(SQLException ex){
                mostrarAlerta = Herramientas.creadorDeAlerta("Error", "No se pudo acceder a la base de datos, "
                    + "intente más tarde", Alert.AlertType.ERROR);
                mostrarAlerta.showAndWait();
            }
        }else{
            mostrarAlerta = Herramientas.creadorDeAlerta("Error", "No se pudo conectar con la base de datos, "
                + "intente más tarde", Alert.AlertType.ERROR);
            mostrarAlerta.showAndWait();
        }
    }
    
    private void irAVentanaCorrespondiente(){
        if(rol.equalsIgnoreCase("Docente")){
            try {
                Stage stage = (Stage) tfCorreo.getScene().getWindow();
                Scene ventanaDeDocente = new Scene(FXMLLoader.load(getClass().getResource("FXMLVentanaPrincipalDocente.fxml")));
                stage.setScene(ventanaDeDocente);
                stage.show();
            } catch (IOException ex) {
                mostrarAlerta = Herramientas.creadorDeAlerta("Error", "No se pudo cargar la siguiente ventana, "
                    + "intente más tarde", Alert.AlertType.ERROR);
                mostrarAlerta.showAndWait();
            }
        }
        if(rol.equalsIgnoreCase("Coordinador")){
            try {
                Stage stage = (Stage) tfCorreo.getScene().getWindow();
                Scene ventanaDeCoordinador = new Scene(FXMLLoader.load(getClass().getResource("FXMLVentanaPrincipalCoordinador.fxml")));
                stage.setScene(ventanaDeCoordinador);
                stage.show();
            } catch (IOException ex) {
                mostrarAlerta = Herramientas.creadorDeAlerta("Error", "No se pudo cargar la siguiente ventana, "
                    + "intente más tarde", Alert.AlertType.ERROR);
                mostrarAlerta.showAndWait();
            }
        }
        if(rol.equalsIgnoreCase("Director")){
            try {
                Stage stage = (Stage) tfCorreo.getScene().getWindow();
                Scene ventanaDeDirector = new Scene(FXMLLoader.load(getClass().getResource("FXMLVentanaPrincipalDirectorDeLaFacultad.fxml")));
                stage.setScene(ventanaDeDirector);
                stage.show();
            } catch (IOException ex) {
                mostrarAlerta = Herramientas.creadorDeAlerta("Error", "No se pudo cargar la siguiente ventana, "
                    + "intente más tarde", Alert.AlertType.ERROR);
                mostrarAlerta.showAndWait();
            }
        }
    }
    
}
