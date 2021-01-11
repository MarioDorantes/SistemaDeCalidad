/*
 * Autor: Brandon Trujillo
 * fecha: 09-01-2021
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

public class FXMLVentanaPrincipalRepresentanteDeCuerpoAcademicoController implements Initializable {

    @FXML
    private Button btCerrarSesion;
    
    Alert mostrarAlerta;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    

    @FXML
    private void cerrarSesion(ActionEvent event) {
        try{
            Stage stage = (Stage) btCerrarSesion.getScene().getWindow();
            Scene sceneCerrarSesion = new Scene(FXMLLoader.load(getClass().getResource("FXMLInicioDeSesion.fxml")));
            stage.setScene(sceneCerrarSesion);
            stage.show();
        }catch(IOException ex){
            mostrarAlerta = Herramientas.creadorDeAlerta("Error", "No fue posible cerrar la sesión", Alert.AlertType.ERROR);
            mostrarAlerta.showAndWait();
        }
    }
    
}
