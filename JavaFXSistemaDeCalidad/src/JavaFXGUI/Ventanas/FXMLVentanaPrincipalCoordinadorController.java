/*
 *Autor: Brandon Trujillo
 *fechaDeCración: 02/12/2020
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

public class FXMLVentanaPrincipalCoordinadorController implements Initializable {

    @FXML
    private Button btPlanDeTrabajo;
    
    Alert mostrarAlerta;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void irAVisualizarPlanDeTrabajo(ActionEvent event) {
        try{
            Stage stage = (Stage) btPlanDeTrabajo.getScene().getWindow();
            Scene visualizarPlanDeTrabajo = new Scene(FXMLLoader.load(getClass().getResource("FXMLVisualizarPlanDeTrabajoDeAcademia.fxml")));
            stage.setScene(visualizarPlanDeTrabajo);
            stage.show(); 
        } catch(IOException ex){
            mostrarAlerta = Herramientas.creadorDeAlerta("Error", ex.getMessage(), Alert.AlertType.ERROR);
            mostrarAlerta.showAndWait();
        }
    }
}
