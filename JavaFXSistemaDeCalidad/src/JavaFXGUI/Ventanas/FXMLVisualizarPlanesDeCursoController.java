/*
Autor: Mario Dorantes
Comentarios aun por verificar 
*/

package JavaFXGUI.Ventanas;

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


public class FXMLVisualizarPlanesDeCursoController implements Initializable {

    @FXML
    private Button btCancelar;

   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }   
    
    @FXML
    private void noImplementacion () {
        Alert alertaVacio = new Alert(Alert.AlertType.ERROR);
        alertaVacio.setTitle("Mensaje");
        alertaVacio.setHeaderText(null);
        alertaVacio.setContentText("No se implementaran descargas");
        alertaVacio.showAndWait();
    }
    
    @FXML
    private void cancelar () {
        try{
            Stage stage = (Stage) btCancelar.getScene().getWindow();
            Scene sceneVisualizarPlanesDeCurso = new Scene(FXMLLoader.load(getClass().getResource("FXMLPrincipalDocente.fxml")));
            stage.setScene(sceneVisualizarPlanesDeCurso);
            stage.show();
        } catch(IOException ex){
            System.out.println("Error al cargar FXML: "+ex.getMessage()); 
        }
    }
    
}
