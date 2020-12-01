/*
Autor: Mario Dorantes
FechaDeCreación: 30/11/2020
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


public class FXMLRegistrarCatalogoDeEEController implements Initializable {

    @FXML
    private Button btRegistrarCatalogo;
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
            Scene sceneVisualizarCatalogoDeEE = new Scene(FXMLLoader.load(getClass().getResource("FXMLVisualizarCatalogoDeEE.fxml")));
            stage.setScene(sceneVisualizarCatalogoDeEE);
            stage.show(); 
        } catch(IOException ex){
            System.out.println("Error al cargar FXML: "+ex.getMessage()); 
        }
    }

    
    
}
