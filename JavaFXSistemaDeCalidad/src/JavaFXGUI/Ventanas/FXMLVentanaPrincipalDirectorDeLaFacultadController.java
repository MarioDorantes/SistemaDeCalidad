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
    @FXML
    private Button btVisualizarCatalogoDeAcademia;

    
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
    
    @FXML
    private void visualizarCatalogoDeAcademia(javafx.event.ActionEvent event) {
        try{
            Stage stage = (Stage) btVisualizarCatalogoDeAcademia.getScene().getWindow();
            Scene sceneVisualizarCatalogoDeAcademia = new Scene(FXMLLoader.load(getClass().getResource("FXMLVisualizarCatalogoDeAcademia.fxml")));
            stage.setScene(sceneVisualizarCatalogoDeAcademia);
            stage.show(); 
        } catch(IOException ex){
            System.out.println("Error al cargar FXML: "+ex.getMessage()); 
        }
    }
    /*
    @FXML
    private void visualizarCatalogoDeEE(javafx.event.ActionEvent event) {
        try{
            Stage stage = (Stage) btVisualizarCatalogoDeEE.getScene().getWindow();
            Scene sceneVisualizarCatalogoDeEE = new Scene(FXMLLoader.load(getClass().getResource("FXMLVisualizarCatalogoDeEE.fxml")));
            stage.setScene(sceneVisualizarCatalogoDeEE);
            stage.show(); 
        } catch(IOException ex){
            System.out.println("Error al cargar FXML: "+ex.getMessage()); 
        }
    }*/
    
    
}
