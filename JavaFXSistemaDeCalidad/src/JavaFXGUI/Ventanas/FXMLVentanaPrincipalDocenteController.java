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


public class FXMLVentanaPrincipalDocenteController implements Initializable {

    @FXML
    private Button btVisualizarPlan;
    @FXML
    private Button btVisualizarCeneval;
    @FXML
    private Button btSalir;

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML
    private void salir(javafx.event.ActionEvent event) {
        Stage stage = (Stage) btSalir.getScene().getWindow();
        stage.close();
    }
    
    @FXML
    private void visualizarPlanesDeCurso(javafx.event.ActionEvent event) {
        try{
            Stage stage = (Stage) btVisualizarPlan.getScene().getWindow();
            Scene sceneVisualizarPlanesDeCurso = new Scene(FXMLLoader.load(getClass().getResource("FXMLVisualizarPlanesDeCurso.fxml")));
            stage.setScene(sceneVisualizarPlanesDeCurso);
            stage.show();
        } catch(IOException ex){
            System.out.println("Error al cargar FXML: "+ex.getMessage()); 
        }
    }
    
    @FXML
    private void visualizarCeneval(javafx.event.ActionEvent event) {
        try{
            Stage stage = (Stage) btVisualizarCeneval.getScene().getWindow();
            Scene sceneVisualizarCeneval = new Scene(FXMLLoader.load(getClass().getResource("FXMLVisualizarCeneval.fxml")));
            stage.setScene(sceneVisualizarCeneval);
            stage.show(); 
        } catch(IOException ex){
            System.out.println("Error al cargar FXML: "+ex.getMessage()); 
        }
    }
    
}
