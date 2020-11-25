/*
Autor: Mario Dorantes
Comentarios aun por verificar 
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


public class FXMLPrincipalDocenteController implements Initializable {

    @FXML
    private Button btSalir;
    @FXML
    private Button btVisualizarPlanesDeCurso;

       
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    
    @FXML
    private void clicSalir(javafx.event.ActionEvent event) {
        Stage stage = (Stage) btSalir.getScene().getWindow();
        stage.close();
    }
    
    @FXML
    private void clicVisualizarPlanesDeCurso(javafx.event.ActionEvent event) {
        try{
            Stage stage = (Stage) btVisualizarPlanesDeCurso.getScene().getWindow();
            Scene sceneVisualizarPlanesDeCurso = new Scene(FXMLLoader.load(getClass().getResource("FXMLVisualizarPlanesDeCurso.fxml")));
            stage.setScene(sceneVisualizarPlanesDeCurso);
            stage.show();
        } catch(IOException ex){
            System.out.println("Error al cargar FXML: "+ex.getMessage()); 
        }
    }
    
}
