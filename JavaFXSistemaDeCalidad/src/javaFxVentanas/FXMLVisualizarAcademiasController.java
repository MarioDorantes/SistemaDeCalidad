/*
Autor: Mario Dorantes
fechaCreación: 25/11/2020
 */

package javaFxVentanas;

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


public class FXMLVisualizarAcademiasController implements Initializable {

    @FXML
    private Button btRegistrarAcademia;
    @FXML
    private Button btActualizarAcademia;
    @FXML
    private Button btEliminarAcademia;
    @FXML
    private Button btCancelar;

    Alert mostrarAlerta;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML
    private void cancelar(javafx.event.ActionEvent event) {
        try{
            Stage stage = (Stage) btCancelar.getScene().getWindow();
            Scene sceneVentanaPrincipalDirectorDeLaFacultad = new Scene(FXMLLoader.load(getClass().getResource("FXMLVentanaPrincipalDirectorDeLaFacultad.fxml")));
            stage.setScene(sceneVentanaPrincipalDirectorDeLaFacultad);
            stage.show(); 
        } catch(IOException ex){
            mostrarAlerta = Herramientas.creadorDeAlerta("Error", "No se pudo cargar la ventana siguiente. Intente más tarde", Alert.AlertType.ERROR);
            mostrarAlerta.showAndWait();  
        }
    }
    
    @FXML
    private void registrarAcademia(javafx.event.ActionEvent event) {
        try{
            Stage stage = (Stage) btRegistrarAcademia.getScene().getWindow();
            Scene sceneRegistrarAcademia = new Scene(FXMLLoader.load(getClass().getResource("FXMLRegistrarAcademia.fxml")));
            stage.setScene(sceneRegistrarAcademia);
            stage.show(); 
        } catch(IOException ex){
            mostrarAlerta = Herramientas.creadorDeAlerta("Error", "No se pudo cargar la ventana siguiente. Intente más tarde", Alert.AlertType.ERROR);
            mostrarAlerta.showAndWait(); 
        }
    }
    
    @FXML
    private void actualizarAcademia(javafx.event.ActionEvent event) {
        try{
            Stage stage = (Stage) btActualizarAcademia.getScene().getWindow();
            Scene sceneActualizarAcademia = new Scene(FXMLLoader.load(getClass().getResource("FXMLActualizarAcademia.fxml")));
            stage.setScene(sceneActualizarAcademia);
            stage.show(); 
        } catch(IOException ex){
            mostrarAlerta = Herramientas.creadorDeAlerta("Error", "No se pudo cargar la ventana siguiente. Intente más tarde", Alert.AlertType.ERROR);
            mostrarAlerta.showAndWait(); 
        }
    }
    
    @FXML
    private void eliminarAcademia(javafx.event.ActionEvent event) {
        try{
            Stage stage = (Stage) btEliminarAcademia.getScene().getWindow();
            Scene sceneEliminarAcademia = new Scene(FXMLLoader.load(getClass().getResource("FXMLEliminarAcademia.fxml")));
            stage.setScene(sceneEliminarAcademia);
            stage.show(); 
        } catch(IOException ex){
            mostrarAlerta = Herramientas.creadorDeAlerta("Error", "No se pudo cargar la ventana siguiente. Intente más tarde", Alert.AlertType.ERROR);
            mostrarAlerta.showAndWait(); 
        }
    }
    
}
