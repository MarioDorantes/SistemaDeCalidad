/*
 *Autor: Brandon Trujillo
 *fechaCreación: 02/12/2020
 */
package JavaFXGUI.Ventanas;

import java.io.IOException;
import java.net.URL;
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
    private Label lbNombre;
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

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    
    @FXML
    private void cancelar(ActionEvent event) {
        try {
            Stage stage = (Stage) tfNombre.getScene().getWindow();
            Scene cancelar = new Scene(FXMLLoader.load(getClass().getResource("FXMLVisualizarDocentes.fxml")));
            stage.setScene(cancelar);
            stage.show();
        } catch (IOException ex) {
            mostrarAlerta = Herramientas.creadorDeAlerta("Error", ex.getMessage(), Alert.AlertType.ERROR);
            mostrarAlerta.showAndWait();
        }
    }

    @FXML
    private void clicRegistrar(ActionEvent event) {
        tfNombre.setStyle("-fx-border-color: ;");
        tfNumeroDePersonal.setStyle("-fx-border-color: ;");
        tfTelefono.setStyle("-fx-border-color: ;");
        tfCorreo.setStyle("-fx-border-color: ;");
        tfContraseña.setStyle("-fx-border-color: ;");
        
        boolean esCorrecto = true;
        String nombreAuxiliar = tfNombre.getText();
        String numeroPersonalAuxiliar = tfNumeroDePersonal.getText();
        String telefonoAuxiliar = tfTelefono.getText();
        String correoAuxiliar = tfCorreo.getText();
        String contraseñaAuxiliar = tfContraseña.getText();
        
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
            registrarDocente(nombreAuxiliar, numeroPersonalAuxiliar, telefonoAuxiliar, correoAuxiliar, contraseñaAuxiliar);
        }else{
            
        }
    }
    
    private void registrarDocente(String nombre, String numeroDePersonal, String telefono, String correo, String contraseña){
        
    }
    
}
