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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;


public class FXMLVisualizarPlanesDeCursoController implements Initializable {

    @FXML
    private Button btCancelar;
    @FXML
    private Button btRegistrarPlan;

   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }   
    
    @FXML
    private void noImplementacion () {
        Alert alertaVacio = new Alert(Alert.AlertType.ERROR);
        alertaVacio.setTitle("Mensaje");
        alertaVacio.setHeaderText(null);
        alertaVacio.setContentText("No se implementaran descargas");
        alertaVacio.showAndWait();
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
    private void registrarPlan () {
        try{
            Stage stage = (Stage) btRegistrarPlan.getScene().getWindow();
            Scene sceneRegistrarPlanDeCurso = new Scene(FXMLLoader.load(getClass().getResource("FXMLRegistrarPlanDeCurso.fxml")));
            stage.setScene(sceneRegistrarPlanDeCurso);
            stage.show();
        } catch(IOException ex){
            System.out.println("Error al cargar FXML: "+ex.getMessage()); 
        }
    }
    
}
