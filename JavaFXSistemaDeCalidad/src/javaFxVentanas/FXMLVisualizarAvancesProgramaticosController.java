/*
 *Autor: Brandon Trujillo
 *fechaCreación: 02/12/2020
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

public class FXMLVisualizarAvancesProgramaticosController implements Initializable {

    @FXML
    private TableView<?> tvAvances;
    @FXML
    private TableColumn<?, ?> tcNombreDelAvance;
    @FXML
    private TableColumn<?, ?> tcExperienciaEducativa;
    @FXML
    private TableColumn<?, ?> tcNrc;
    @FXML
    private TableColumn<?, ?> tcDcente;
    @FXML
    private TableColumn<?, ?> tcFechaRegistro;
    
    Alert mostrarAlerta;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void irARegistrarAvance(ActionEvent event) {
        try{
            Stage stage = (Stage) tvAvances.getScene().getWindow();
            Scene registrarAvance = new Scene(FXMLLoader.load(getClass().getResource("FXMLRegistrarAvancesProgramaticos.fxml")));
            stage.setScene(registrarAvance);
            stage.show(); 
        } catch(IOException ex){
            mostrarAlerta = Herramientas.creadorDeAlerta("Error", ex.getMessage(), Alert.AlertType.ERROR);
            mostrarAlerta.showAndWait();
        }
    }

    @FXML
    private void irAActualizarAvance(ActionEvent event) {
        try{
            Stage stage = (Stage) tvAvances.getScene().getWindow();
            Scene actualizarAvance = new Scene(FXMLLoader.load(getClass().getResource("FXMLActualizarAvancesProgramaticos.fxml")));
            stage.setScene(actualizarAvance);
            stage.show(); 
        } catch(IOException ex){
            mostrarAlerta = Herramientas.creadorDeAlerta("Error", ex.getMessage(), Alert.AlertType.ERROR);
            mostrarAlerta.showAndWait();
        }
    }

    @FXML
    private void cancelar(ActionEvent event) {
        try{
            Stage stage = (Stage) tvAvances.getScene().getWindow();
            Scene cancelar = new Scene(FXMLLoader.load(getClass().getResource("FXMLVentanaPrincipalDocente.fxml")));
            stage.setScene(cancelar);
            stage.show(); 
        } catch(IOException ex){
            mostrarAlerta = Herramientas.creadorDeAlerta("Error", ex.getMessage(), Alert.AlertType.ERROR);
            mostrarAlerta.showAndWait();
        }
    }

}
