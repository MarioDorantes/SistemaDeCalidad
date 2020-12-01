/*
Autor: Mario Dorantes
fechaCreación: 30/11/2020
 */

package JavaFXGUI.Ventanas;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;


public class FXMLVisualizarCatalogoDeEEController implements Initializable {

    @FXML
    private Button btRegistrarCatalogo;
    @FXML
    private Button btActualizarCatalogo;
    @FXML
    private Button btCancelar;

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML
    private void cancelar(javafx.event.ActionEvent event) {
        try{
            Stage stage = (Stage) btCancelar.getScene().getWindow();
            Scene sceneVentanaPrincipalDirectorDeLaFacultad = new Scene(FXMLLoader.load(getClass().getResource("FXMLVentanaPrincipalDirectorDeLaFacultad.fxml")));
            stage.setScene(sceneVentanaPrincipalDirectorDeLaFacultad);
            stage.show(); 
        } catch(IOException ex){
            System.out.println("Error al cargar FXML: "+ex.getMessage()); 
        }
    }
    
    @FXML
    private void registrarCatalogo(javafx.event.ActionEvent event) {
        try{
            Stage stage = (Stage) btRegistrarCatalogo.getScene().getWindow();
            Scene sceneRegistrarCatalogoDeEE = new Scene(FXMLLoader.load(getClass().getResource("FXMLRegistrarCatalogoDeEE.fxml")));
            stage.setScene(sceneRegistrarCatalogoDeEE);
            stage.show(); 
        } catch(IOException ex){
            System.out.println("Error al cargar FXML: "+ex.getMessage()); 
        }
    }
    
    @FXML
    private void actualizarCatalogo(javafx.event.ActionEvent event) {
        try{
            Stage stage = (Stage) btActualizarCatalogo.getScene().getWindow();
            Scene sceneActualizarCatalogoDeEE = new Scene(FXMLLoader.load(getClass().getResource("FXMLActualizarCatalogoDeEE.fxml")));
            stage.setScene(sceneActualizarCatalogoDeEE);
            stage.show(); 
        } catch(IOException ex){
            System.out.println("Error al cargar FXML: "+ex.getMessage()); 
        }
    }
    
}
