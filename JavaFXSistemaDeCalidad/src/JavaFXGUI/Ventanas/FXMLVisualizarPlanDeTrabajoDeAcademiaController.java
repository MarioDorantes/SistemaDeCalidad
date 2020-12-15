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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import util.Herramientas;

public class FXMLVisualizarPlanDeTrabajoDeAcademiaController implements Initializable {

    @FXML
    private TableView<?> tvPlanesDeTrabajo;
    @FXML
    private TableColumn<?, ?> tcNombre;
    @FXML
    private TableColumn<?, ?> tcCoordinador;
    @FXML
    private TableColumn<?, ?> tcFechaDeRegistro;
    @FXML
    private TableColumn<?, ?> tcProgramaEducativo;
    
    Alert mostrarAlerta;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void irARegistrarPlan(ActionEvent event) {
        try{
            Stage stage = (Stage) tvPlanesDeTrabajo.getScene().getWindow();
            Scene registrarPlan = new Scene(FXMLLoader.load(getClass().getResource("FXMLRegistrarPlanDeTrabajoDeAcademia.fxml")));
            stage.setScene(registrarPlan);
            stage.show(); 
        } catch(IOException ex){
            mostrarAlerta = Herramientas.creadorDeAlerta("Error", ex.getMessage(), Alert.AlertType.ERROR);
            mostrarAlerta.showAndWait();
        }
    }

    @FXML
    private void irAActualizarPlan(ActionEvent event) {
        try{
            Stage stage = (Stage) tvPlanesDeTrabajo.getScene().getWindow();
            Scene actualizarPlan = new Scene(FXMLLoader.load(getClass().getResource("FXMLActualizarPlanDeTrabajoDeAcademia.fxml")));
            stage.setScene(actualizarPlan);
            stage.show(); 
        } catch(IOException ex){
            mostrarAlerta = Herramientas.creadorDeAlerta("Error", ex.getMessage(), Alert.AlertType.ERROR);
            mostrarAlerta.showAndWait();
        }
    }

    @FXML
    private void cancelar(ActionEvent event) {
        try{
            Stage stage = (Stage) tvPlanesDeTrabajo.getScene().getWindow();
            Scene cancelar = new Scene(FXMLLoader.load(getClass().getResource("FXMLVentanaPrincipalCoordinador.fxml")));
            stage.setScene(cancelar);
            stage.show(); 
        } catch(IOException ex){
            mostrarAlerta = Herramientas.creadorDeAlerta("Error", ex.getMessage(), Alert.AlertType.ERROR);
            mostrarAlerta.showAndWait();
        }
    }
 
}
