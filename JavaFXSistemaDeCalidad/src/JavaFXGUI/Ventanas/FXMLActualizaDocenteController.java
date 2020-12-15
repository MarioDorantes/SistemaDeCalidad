/*
 *Autor: Brandon Trujillo
 *fechaCreación: 28/11/2020
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
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import util.Herramientas;

public class FXMLActualizaDocenteController implements Initializable {

    @FXML
    private ComboBox<?> cbSeleccionarDocente;
    
    Alert mostrarAlerta;

    @FXML
    private void cancelar(ActionEvent e){
        try {
            Stage stage = (Stage) cbSeleccionarDocente.getScene().getWindow();
            Scene cancelar = new Scene(FXMLLoader.load(getClass().getResource("FXMLVisualizarDocentes.fxml")));
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
