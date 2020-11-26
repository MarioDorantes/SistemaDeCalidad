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
import javafx.scene.control.Button;
import javafx.stage.Stage;


public class FXMLVentanaPrincipalDirectorDeLaFacultadController implements Initializable {

    @FXML
    private Button btSalir;
    @FXML
    private Button btVisualizarAcademias;

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML
    private void salir(javafx.event.ActionEvent event) {
        Stage stage = (Stage) btSalir.getScene().getWindow();
        stage.close();
    }
    
    @FXML
    private void visualizarAcademias(javafx.event.ActionEvent event) {
        try{
            Stage stage = (Stage) btVisualizarAcademias.getScene().getWindow();
            Scene sceneVisualizarAcademias = new Scene(FXMLLoader.load(getClass().getResource("FXMLVisualizarAcademias.fxml")));
            stage.setScene(sceneVisualizarAcademias);
            stage.show(); 
        } catch(IOException ex){
            System.out.println("Error al cargar FXML: "+ex.getMessage()); 
        }
    }
    
    
}
