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
import util.Herramientas;


public class FXMLVisualizarPlanesDeCursoController implements Initializable {

    @FXML
    private Button btCancelar;
    @FXML
    private Button btRegistrarPlan;
    @FXML
    private Button btActualizarPlan;

   
    Alert mostrarAlerta;
    
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
            mostrarAlerta = Herramientas.creadorDeAlerta("Error", "No se pudo cargar la ventana siguiente. Intente más tarde", Alert.AlertType.ERROR);
            mostrarAlerta.showAndWait();  
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
            mostrarAlerta = Herramientas.creadorDeAlerta("Error", "No se pudo cargar la ventana siguiente. Intente más tarde", Alert.AlertType.ERROR);
            mostrarAlerta.showAndWait();  
        }
    }
    
    @FXML
    private void actualizarPlan () {
        try{
            Stage stage = (Stage) btActualizarPlan.getScene().getWindow();
            Scene sceneActualizarPlanDeCurso = new Scene(FXMLLoader.load(getClass().getResource("FXMLActualizarPlanDeCurso.fxml")));
            stage.setScene(sceneActualizarPlanDeCurso);
            stage.show();
        } catch(IOException ex){
            mostrarAlerta = Herramientas.creadorDeAlerta("Error", "No se pudo cargar la ventana siguiente. Intente más tarde", Alert.AlertType.ERROR);
            mostrarAlerta.showAndWait();  
        }
    }
    
}
