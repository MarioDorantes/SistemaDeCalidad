/*
Autor: Mario Dorantes
fechaCreación: 25/11/2020
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


public class FXMLVisualizarCenevalController implements Initializable {

    @FXML
    private Button btRegistrar;
    @FXML
    private Button btActualizar;
    @FXML
    private Button btEliminar;
    @FXML
    private Button btCancelar;

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML
    private void cancelar () {
        try{
            Stage stage = (Stage) btCancelar.getScene().getWindow();
            Scene sceneVentanaPrincipalDocente = new Scene(FXMLLoader.load(getClass().getResource("FXMLVentanaPrincipalDocente.fxml")));
            stage.setScene(sceneVentanaPrincipalDocente);
            stage.show();
        } catch(IOException ex){
            System.out.println("Error al cargar FXML: "+ex.getMessage()); 
        }
    }
    
    @FXML
    private void registrarCeneval () {
        try{
            Stage stage = (Stage) btRegistrar.getScene().getWindow();
            Scene sceneRegistrarCeneval = new Scene(FXMLLoader.load(getClass().getResource("FXMLRegistrarCeneval.fxml")));
            stage.setScene(sceneRegistrarCeneval);
            stage.show(); 
        } catch(IOException ex){
            System.out.println("Error al cargar FXML: "+ex.getMessage());
        }
    }
    /*
    @FXML
    private void actualizarCeneval () {
        try{
            Stage stage = (Stage) btActualizar.getScene().getWindow();
            Scene sceneActualizarCeneval = new Scene(FXMLLoader.load(getClass().getResource("FXMLActualizarCeneval.fxml")));
            stage.setScene(sceneActualizarCeneval);
            stage.show(); 
        } catch(IOException ex){
            System.out.println("Error al cargar FXML: "+ex.getMessage());
        }
    }*/
    
    @FXML
    private void eliminarCeneval () {
        try{
            Stage stage = (Stage) btEliminar.getScene().getWindow();
            Scene sceneEliminarCeneval = new Scene(FXMLLoader.load(getClass().getResource("FXMLEliminarCeneval.fxml")));
            stage.setScene(sceneEliminarCeneval);
            stage.show(); 
        } catch(IOException ex){
            System.out.println("Error al cargar FXML: "+ex.getMessage());
        }
    }
    
}
