/*
Autor: Brandon trujillo
fechaDeCreación: 28/11/2020
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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.stage.Stage;
import util.Herramientas;

public class FXMLRegistrarCatalogoAcademicosController implements Initializable {

    @FXML
    private Label lbNombre;
    @FXML
    private TableColumn<?, ?> tcNombre;
    @FXML
    private TableColumn<?, ?> tcCorreo;
    @FXML
    private TableColumn<?, ?> tcNumeroDePersonal;
    @FXML
    private TableColumn<?, ?> tcCubiculo;
    @FXML
    private TableColumn<?, ?> tcPrograma;
    
    Alert mostrarAlerta;
    
    private void cancelar(ActionEvent e){
        try{
            Stage stage = (Stage) lbNombre.getScene().getWindow();
            Scene visualizarCatalogoAcademicos = new Scene(FXMLLoader.load(getClass().getResource("FXMLVisualizarCatalogoDeAcademicos.fxml")));
            stage.setScene(visualizarCatalogoAcademicos);
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
