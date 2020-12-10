/*
 *Autor: Brandon Trujillo
 *fechaDeCreación: 28/11/2020
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

public class FXMLVisualizarCatalogoDeAcademicosController implements Initializable {

    @FXML
    private TableView<?> tvContenedor;
    @FXML
    private TableColumn<?, ?> tcNombreDeCatalogo;
    @FXML
    private TableColumn<?, ?> tcFechaDeRegistroCatalogo;
    
    Alert mostrarAlerta;

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
    
    @FXML
    private void irARegistrarCatalogo(ActionEvent event) {
        try{
            Stage stage = (Stage) tvContenedor.getScene().getWindow();
            Scene registrarCatalogo = new Scene(FXMLLoader.load(getClass().getResource("FXMLRegistrarCatalogoAcademicos.fxml")));
            stage.setScene(registrarCatalogo);
            stage.show();
        }catch(IOException ex){
            mostrarAlerta = Herramientas.creadorDeAlerta("Error", ex.getMessage(), Alert.AlertType.ERROR);
            mostrarAlerta.showAndWait();
        }
    }

    @FXML
    private void irAActualizarCatalogo(ActionEvent event) {
        try{
            Stage stage = (Stage) tvContenedor.getScene().getWindow();
            Scene actualizarCatalogo = new Scene(FXMLLoader.load(getClass().getResource("FXMLActualizarCatalogoDeAcademicos.fxml")));
            stage.setScene(actualizarCatalogo);
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
