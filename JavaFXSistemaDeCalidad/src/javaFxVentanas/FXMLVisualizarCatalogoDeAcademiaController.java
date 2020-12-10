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


public class FXMLVisualizarCatalogoDeAcademiaController implements Initializable {

    @FXML
    private Button btRegistrarCatalogo;
    @FXML
    private Button btActualizarCatalogo;
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
    private void registrarCatalogoDeAcademia(javafx.event.ActionEvent event) {
        try{
            Stage stage = (Stage) btRegistrarCatalogo.getScene().getWindow();
            Scene sceneRegistrarCatalogoDeAcademia = new Scene(FXMLLoader.load(getClass().getResource("FXMLRegistrarCatalogoDeAcademia.fxml")));
            stage.setScene(sceneRegistrarCatalogoDeAcademia);
            stage.show(); 
        } catch(IOException ex){
            mostrarAlerta = Herramientas.creadorDeAlerta("Error", "No se pudo cargar la ventana siguiente. Intente más tarde", Alert.AlertType.ERROR);
            mostrarAlerta.showAndWait();  
        }
    }
    
    @FXML
    private void actualizarCatalogoDeAcademia(javafx.event.ActionEvent event) {
        try{
            Stage stage = (Stage) btActualizarCatalogo.getScene().getWindow();
            Scene sceneActualizarCatalogoDeAcademia = new Scene(FXMLLoader.load(getClass().getResource("FXMLActualizarCatalogoDeAcademia.fxml")));
            stage.setScene(sceneActualizarCatalogoDeAcademia);
            stage.show(); 
        } catch(IOException ex){
            mostrarAlerta = Herramientas.creadorDeAlerta("Error", "No se pudo cargar la ventana siguiente. Intente más tarde", Alert.AlertType.ERROR);
            mostrarAlerta.showAndWait();  
        }
    }
    
}
