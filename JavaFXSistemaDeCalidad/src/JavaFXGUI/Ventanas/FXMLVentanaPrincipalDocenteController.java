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
    private Button btSalir;

    Alert mostrarAlerta;
    
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
            mostrarAlerta = Herramientas.creadorDeAlerta("Error", "No se pudo cargar la ventana siguiente. Intente más tarde", Alert.AlertType.ERROR);
            mostrarAlerta.showAndWait(); 
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
            mostrarAlerta = Herramientas.creadorDeAlerta("Error", "No se pudo cargar la ventana siguiente. Intente más tarde", Alert.AlertType.ERROR);
            mostrarAlerta.showAndWait();  
        }
    }

    @FXML
    private void irAVisualizarAvancesProgramaticos(ActionEvent event) {
        try{
            Stage stage = (Stage) btSalir.getScene().getWindow();
            Scene visualizarAvances = new Scene(FXMLLoader.load(getClass().getResource("FXMLVisualizarAvancesProgramaticos.fxml")));
            stage.setScene(visualizarAvances);
            stage.show(); 
        } catch(IOException ex){
            mostrarAlertas("Error", "No fue posible cargar la siguiente ventana, intente más tarde");
        }
    }
    
    public void mostrarAlertas(String titulo, String dialogo){
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle(titulo);
        alerta.setContentText(dialogo);
        alerta.setHeaderText(null);
        alerta.showAndWait();
    }
    
}
