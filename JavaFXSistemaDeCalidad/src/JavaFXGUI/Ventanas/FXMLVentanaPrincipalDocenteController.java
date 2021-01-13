/*
Autor: Mario Dorantes
fechaCreación: 25/11/2020
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
import javafx.scene.control.Button;
import javafx.stage.Stage;
import util.Herramientas;


public class FXMLVentanaPrincipalDocenteController implements Initializable {

    @FXML
    private Button btVisualizarPlan;
    @FXML
    private Button btVisualizarCeneval;
    @FXML
    private Button btCerrarSesion;
    
    Alert mostrarAlerta;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void visualizarPlanesDeCurso(javafx.event.ActionEvent event) {
        mostrarAlerta = Herramientas.creadorDeAlerta("Sin implementación", "No se implemento esta funcionalidad", Alert.AlertType.ERROR);
        mostrarAlerta.showAndWait();
    }
    
    @FXML
    private void visualizarCeneval(javafx.event.ActionEvent event) {
        try{
            Stage stage = (Stage) btVisualizarCeneval.getScene().getWindow();
            Scene sceneVisualizarCeneval = new Scene(FXMLLoader.load(getClass().getResource("FXMLVisualizarCeneval.fxml")));
            stage.setScene(sceneVisualizarCeneval);
            stage.show(); 
        } catch(IOException ex){
            mostrarAlerta = Herramientas.creadorDeAlerta("Error", "No se pudo cargar la ventana siguiente. Intente más tarde", Alert.AlertType.ERROR);
            mostrarAlerta.showAndWait();  
        }
    }

    @FXML
    private void irAVisualizarAvancesProgramaticos(ActionEvent event) {
        mostrarAlerta = Herramientas.creadorDeAlerta("Sin implementación", "No se implemento esta funcionalidad", Alert.AlertType.ERROR);
        mostrarAlerta.showAndWait();
    }

    @FXML
    private void cerrarSesion(ActionEvent event) {
        try{
            Stage stage = (Stage) btCerrarSesion.getScene().getWindow();
            Scene sceneCerrarSesion = new Scene(FXMLLoader.load(getClass().getResource("FXMLInicioDeSesion.fxml")));
            stage.setScene(sceneCerrarSesion);
            stage.show();
        }catch(IOException ex){
            mostrarAlerta = Herramientas.creadorDeAlerta("Error", "No fue posible cerrar la sesión", Alert.AlertType.ERROR);
            mostrarAlerta.showAndWait();
        }
    }
    
}
