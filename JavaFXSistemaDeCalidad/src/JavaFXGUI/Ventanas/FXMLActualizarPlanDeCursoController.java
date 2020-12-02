/*
Autor: Mario Dorantes
fechaCreación: 01/12/2020
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


public class FXMLActualizarPlanDeCursoController implements Initializable {

    @FXML
    private Button btActualizarPlan;
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
            Scene sceneVisualizarPlanesDeCurso = new Scene(FXMLLoader.load(getClass().getResource("FXMLVisualizarPlanesDeCurso.fxml")));
            stage.setScene(sceneVisualizarPlanesDeCurso);
            stage.show();
        } catch(IOException ex){
            System.out.println("Error al cargar FXML: "+ex.getMessage()); 
        }
    }
    
    //En el JAVAFXVISTAS esta como hacer que solo se seleccione un radio button, ver 
    
}