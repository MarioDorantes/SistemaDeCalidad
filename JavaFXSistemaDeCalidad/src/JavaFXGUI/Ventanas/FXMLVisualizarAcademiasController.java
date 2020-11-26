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


public class FXMLVisualizarAcademiasController implements Initializable {

    @FXML
    private Button btRegistrarAcademia;
    @FXML
    private Button btActualizarAcademia;
    @FXML
    private Button btEliminarAcademia;
    @FXML
    private Button btCancelar;

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML
    private void cancelar(javafx.event.ActionEvent event) {
        try{
            Stage stage = (Stage) btCancelar.getScene().getWindow();
            Scene sceneVentanaPrincipalDirectorDeLaFacultad = new Scene(FXMLLoader.load(getClass().getResource("FXMLVentanaPrincipalDirectorDeLaFacultad.fxml")));
            stage.setScene(sceneVentanaPrincipalDirectorDeLaFacultad);
            stage.show(); 
        } catch(IOException ex){
            System.out.println("Error al cargar FXML: "+ex.getMessage()); 
        }
    }
    
    @FXML
    private void registrarAcademia(javafx.event.ActionEvent event) {
        try{
            Stage stage = (Stage) btRegistrarAcademia.getScene().getWindow();
            Scene sceneRegistrarAcademia = new Scene(FXMLLoader.load(getClass().getResource("FXMLRegistrarAcademia.fxml")));
            stage.setScene(sceneRegistrarAcademia);
            stage.show(); 
        } catch(IOException ex){
            System.out.println("Error al cargar FXML: "+ex.getMessage()); 
        }
    }
    
    @FXML
    private void eliminarAcademia(javafx.event.ActionEvent event) {
        try{
            Stage stage = (Stage) btEliminarAcademia.getScene().getWindow();
            Scene sceneEliminarAcademia = new Scene(FXMLLoader.load(getClass().getResource("FXMLEliminarAcademia.fxml")));
            stage.setScene(sceneEliminarAcademia);
            stage.show(); 
        } catch(IOException ex){
            System.out.println("Error al cargar FXML: "+ex.getMessage()); 
        }
    }
    
}
