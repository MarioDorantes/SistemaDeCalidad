/*
Autor: Brandon Trujillo
FechaDeCreación: 28/11/2020
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
import javafx.stage.Stage;

public class FXMLRegistrarDocenteController implements Initializable {
    
    @FXML
    private Label lbNombre;
    
    private void cancelar(ActionEvent e){
         try {
            Stage stage = (Stage) lbNombre.getScene().getWindow();
            Scene cancelar = new Scene(FXMLLoader.load(getClass().getResource("FXMLVisualizarDocentes.fxml")));
            stage.setScene(cancelar);
            stage.show();
        } catch (IOException ex) {
            mostrarAlertas("Error", "No se pudo cargar la ventana siguiente, intente más tarde");
        } 
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void mostrarAlertas(String titulo, String dialogo){
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(dialogo);
        alerta.showAndWait();
    }
    
}
