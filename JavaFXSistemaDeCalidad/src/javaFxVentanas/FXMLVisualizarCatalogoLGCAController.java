/*
 *Autor: Brandon Trujillo
 *fechaDeCreación: 01/12/2020
 */
package javaFxVentanas;

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

public class FXMLVisualizarCatalogoLGCAController implements Initializable {

    @FXML
    private TableView<?> tvContenedor;
    @FXML
    private TableColumn<?, ?> tcClave;
    @FXML
    private TableColumn<?, ?> tcFechaRegistro;

    Alert mostrarAlerta;
    
    @FXML
    private void irARegistrarCatalogo(ActionEvent e){
        try{
            Stage stage = (Stage) tvContenedor.getScene().getWindow();
            Scene registrarCatalogo = new Scene(FXMLLoader.load(getClass().getResource("FXMLRegistrarCatalogoLGCA.fxml")));
            stage.setScene(registrarCatalogo);
            stage.show();
        }catch(IOException ex){
            mostrarAlerta = Herramientas.creadorDeAlerta("Error", ex.getMessage(), Alert.AlertType.ERROR);
            mostrarAlerta.showAndWait();
        }
    }
    
    @FXML
    private void irAActualizarCatalogo(ActionEvent e){
        try{
            Stage stage = (Stage) tvContenedor.getScene().getWindow();
            Scene actualizarCatalogo = new Scene(FXMLLoader.load(getClass().getResource("FXMLActualizarCatalogoLGCA.fxml")));
            stage.setScene(actualizarCatalogo);
            stage.show();
        }catch(IOException ex){
            mostrarAlerta = Herramientas.creadorDeAlerta("Error", ex.getMessage(), Alert.AlertType.ERROR);
            mostrarAlerta.showAndWait();
        }
    }
    
    @FXML
    private void cancelar(ActionEvent e){
        try{
            Stage stage = (Stage) tvContenedor.getScene().getWindow();
            Scene cancelar = new Scene(FXMLLoader.load(getClass().getResource("FXMLVentanaPrincipalDirectorDeLaFacultad.fxml")));
            stage.setScene(cancelar);
            stage.show();
        }catch(IOException ex){
            mostrarAlerta = Herramientas.creadorDeAlerta("Error", ex.getMessage(), Alert.AlertType.ERROR);
            mostrarAlerta.showAndWait();
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
