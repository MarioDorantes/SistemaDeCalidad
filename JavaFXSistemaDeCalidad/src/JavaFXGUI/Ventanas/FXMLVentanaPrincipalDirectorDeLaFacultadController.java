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


public class FXMLVentanaPrincipalDirectorDeLaFacultadController implements Initializable {

    @FXML
    private Button btSalir;
    @FXML
    private Button btVisualizarAcademias;
    @FXML
    private Button btVisualizarCatalogoDeAcademia;

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
    private void visualizarCoordinadores(javafx.event.ActionEvent event) {
        try{
            Stage stage = (Stage) btSalir.getScene().getWindow();
            Scene sceneVisualizarCoordinadores = new Scene(FXMLLoader.load(getClass().getResource("FXMLVisualizarCoordinadores.fxml")));
            stage.setScene(sceneVisualizarCoordinadores);
            stage.show(); 
        }catch(IOException ex){
            mostrarAlerta = Herramientas.creadorDeAlerta("Error", ex.getMessage(), Alert.AlertType.ERROR);
            mostrarAlerta.showAndWait();
        }
    }
    
    @FXML
    private void visualizarCatalogoDeAcademia(javafx.event.ActionEvent event) {
        try{
            Stage stage = (Stage) btVisualizarCatalogoDeAcademia.getScene().getWindow();
            Scene sceneVisualizarCatalogoDeAcademia = new Scene(FXMLLoader.load(getClass().getResource("FXMLVisualizarCatalogoDeAcademia.fxml")));
            stage.setScene(sceneVisualizarCatalogoDeAcademia);
            stage.show(); 
        } catch(IOException ex){
            mostrarAlerta = Herramientas.creadorDeAlerta("Error", ex.getMessage(), Alert.AlertType.ERROR);
            mostrarAlerta.showAndWait();
        }
    }
    
    @FXML
    private void visualizarCatalogoDeEE(javafx.event.ActionEvent event) {
        try{
            Stage stage = (Stage) btSalir.getScene().getWindow();
            Scene sceneVisualizarCatalogoDeEE = new Scene(FXMLLoader.load(getClass().getResource("FXMLVisualizarCatalogoDeEE.fxml")));
            stage.setScene(sceneVisualizarCatalogoDeEE);
            stage.show(); 
        } catch(IOException ex){
            mostrarAlerta = Herramientas.creadorDeAlerta("Error", "No se pudo cargar la ventana siguiente. Intente más tarde", Alert.AlertType.ERROR);
            mostrarAlerta.showAndWait(); 
        }
    }
    
    @FXML 
    private void visualizarDocentes(ActionEvent e){
        try{
            Stage stage = (Stage) btSalir.getScene().getWindow();
            Scene sceneVisualizarDocentes = new Scene(FXMLLoader.load(getClass().getResource("FXMLVisualizarDocentes.fxml")));
            stage.setScene(sceneVisualizarDocentes);
            stage.show();
        }catch(IOException ex){
            mostrarAlerta = Herramientas.creadorDeAlerta("Error", ex.getMessage(), Alert.AlertType.ERROR);
            mostrarAlerta.showAndWait();
        }
    }
    
    @FXML
    private void visulizarCatalogoDeAcademicos(ActionEvent e){
        try{
            Stage stage = (Stage) btSalir.getScene().getWindow();
            Scene sceneVisualizarCatalogoAcademicos = new Scene(FXMLLoader.load(getClass().getResource("FXMLVisualizarCatalogoDeAcademicos.fxml")));
            stage.setScene(sceneVisualizarCatalogoAcademicos);
            stage.show();
        }catch(IOException ex){
            mostrarAlerta = Herramientas.creadorDeAlerta("Error", ex.getMessage(), Alert.AlertType.ERROR);
            mostrarAlerta.showAndWait();
        }
    }
    
    @FXML
    private void visualizarCatalogoLGCA(ActionEvent e){
        try{
            Stage stage = (Stage) btSalir.getScene().getWindow();
            Scene sceneVisualizarCatalogoLGCA = new Scene(FXMLLoader.load(getClass().getResource("FXMLVisualizarCatalogoLGCA.fxml")));
            stage.setScene(sceneVisualizarCatalogoLGCA);
            stage.show();
        }catch(IOException ex){
            mostrarAlerta = Herramientas.creadorDeAlerta("Error", ex.getMessage(), Alert.AlertType.ERROR);
            mostrarAlerta.showAndWait();
        }
    }
    
    @FXML
    private void irAObtenerIndicadorDeInvestigacion(ActionEvent event) {
        try{
            Stage stage = (Stage) btSalir.getScene().getWindow();
            Scene obtenerIndicadorDeInvestigacion = new Scene(FXMLLoader.load(getClass().getResource("FXMLObtenerIndicadorDeInvestigacion.fxml")));
            stage.setScene(obtenerIndicadorDeInvestigacion);
            stage.show();
        }catch(IOException ex){
            mostrarAlerta = Herramientas.creadorDeAlerta("Error", ex.getMessage(), Alert.AlertType.ERROR);
            mostrarAlerta.showAndWait();
        }
    }
}
