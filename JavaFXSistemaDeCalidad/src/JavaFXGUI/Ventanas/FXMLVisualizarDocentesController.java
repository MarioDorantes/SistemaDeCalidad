/*
 *Autor: Brandon Trujillo
 *FechaCreación: 28/11/2020
*/
package JavaFXGUI.Ventanas;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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

public class FXMLVisualizarDocentesController implements Initializable {

    @FXML
    private TableColumn<?, ?> tcNombre;
    @FXML
    private TableColumn<?, ?> tcNumeroDePersonal;
    @FXML
    private TableColumn<?, ?> tcCorreo;
    @FXML
    private TableColumn<?, ?> tcContraseña;
    @FXML
    private TableView<?> tvContenedor;
    @FXML
    private TableColumn<?, ?> tcEstatus;
    
    Alert mostrarAlerta;
    
    @FXML
    private void irARegistrarDocente(ActionEvent e){
        try {
            Stage stage = (Stage) tvContenedor.getScene().getWindow();
            Scene registrarDocente = new Scene(FXMLLoader.load(getClass().getResource("FXMLRegistrarDocente.fxml")));
            stage.setScene(registrarDocente);
            stage.show();
        } catch (IOException ex) {
            mostrarAlerta = Herramientas.creadorDeAlerta("Error", ex.getMessage(), Alert.AlertType.ERROR);
            mostrarAlerta.showAndWait();
        } 
    }
    
    @FXML
    private void irAActualizarDocente(ActionEvent e){
        try {
            Stage stage = (Stage) tvContenedor.getScene().getWindow();
            Scene actualizarDocente = new Scene(FXMLLoader.load(getClass().getResource("FXMLActualizaDocente.fxml")));
            stage.setScene(actualizarDocente);
            stage.show();
        } catch (IOException ex) {
            mostrarAlerta = Herramientas.creadorDeAlerta("Error", ex.getMessage(), Alert.AlertType.ERROR);
            mostrarAlerta.showAndWait();
        }  
    }
    
    @FXML
    private void irAEliminarDocente(ActionEvent e){
        try {
            Stage stage = (Stage) tvContenedor.getScene().getWindow();
            Scene eliminarDocente = new Scene(FXMLLoader.load(getClass().getResource("FXMLEliminarDocente.fxml")));
            stage.setScene(eliminarDocente);
            stage.show();
        } catch (IOException ex) {
            mostrarAlerta = Herramientas.creadorDeAlerta("Error", ex.getMessage(), Alert.AlertType.ERROR);
            mostrarAlerta.showAndWait();
        } 
    }
    
    @FXML
    private void cancelar(ActionEvent e){
        try {
            Stage stage = (Stage) tvContenedor.getScene().getWindow();
            Scene cancelar = new Scene(FXMLLoader.load(getClass().getResource("FXMLVentanaPrincipalDirectorDeLaFacultad.fxml")));
            stage.setScene(cancelar);
            stage.show();
        } catch (IOException ex) {
            mostrarAlerta = Herramientas.creadorDeAlerta("Error", ex.getMessage(), Alert.AlertType.ERROR);
            mostrarAlerta.showAndWait();
        } 
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    } 
   
}
