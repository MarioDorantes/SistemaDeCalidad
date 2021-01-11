/*
Autor: Mario Dorantes
fechaCreación: 25/11/2020
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


public class FXMLVentanaPrincipalDirectorDeLaFacultadController implements Initializable {
    
    @FXML
    private Button btCerrarSesion;
    @FXML
    private Button btVisualizarCatalogoDeAcademia;

    Alert mostrarAlerta;
    @FXML
    private Button btVisualizarAcademias;
    @FXML
    private Button btCatalogoEstudiantes;
   
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML
    private void cerrarSesion(javafx.event.ActionEvent event) {
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
    
    @FXML
    private void visualizarCoordinadores(javafx.event.ActionEvent event) {
        try{
            Stage stage = (Stage) btCerrarSesion.getScene().getWindow();
            Scene sceneVisualizarCoordinadores = new Scene(FXMLLoader.load(getClass().getResource("FXMLVisualizarCoordinadores.fxml")));
            stage.setScene(sceneVisualizarCoordinadores);
            stage.show(); 
        }catch(IOException ex){
            mostrarAlerta = Herramientas.creadorDeAlerta("Error", "No se pudo cargar la ventana siguiente,"
                + " intente más tarde", Alert.AlertType.ERROR);
            mostrarAlerta.showAndWait();
        }
    }
    
    @FXML
    private void visualizarRepresentantesDeCuerpoAcademico(ActionEvent e){
        try{
            Stage stage = (Stage) btCerrarSesion.getScene().getWindow();
            Scene sceneVisualizarRepresentantes = new Scene(FXMLLoader.load(getClass().getResource("FXMLVisualizarRepresentantesDeCuerpoAcademico.fxml")));
            stage.setScene(sceneVisualizarRepresentantes);
            stage.show();
        }catch(IOException ex){
            mostrarAlerta = Herramientas.creadorDeAlerta("Error", "No se pudo cargar la ventana siguiente, "
                + "intente más tarde", Alert.AlertType.ERROR);
            mostrarAlerta.showAndWait();
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
            mostrarAlerta = Herramientas.creadorDeAlerta("Error", "No se pudo cargar la ventana siguiente,"
                + " intente más tarde", Alert.AlertType.ERROR);
            mostrarAlerta.showAndWait();
        }
    }
    
    @FXML
    private void visualizarCatalogoDeEE(javafx.event.ActionEvent event) {
        try{
            Stage stage = (Stage) btCerrarSesion.getScene().getWindow();
            Scene sceneVisualizarCatalogoDeEE = new Scene(FXMLLoader.load(getClass().getResource("FXMLVisualizarCatalogoDeEE.fxml")));
            stage.setScene(sceneVisualizarCatalogoDeEE);
            stage.show(); 
        } catch(IOException ex){
            mostrarAlerta = Herramientas.creadorDeAlerta("Error", "No se pudo cargar la ventana siguiente,"
                + " Intente más tarde", Alert.AlertType.ERROR);
            mostrarAlerta.showAndWait(); 
        }
    }
    
    @FXML 
    private void visualizarDocentes(ActionEvent e){
        try{
            Stage stage = (Stage) btCerrarSesion.getScene().getWindow();
            Scene sceneVisualizarDocentes = new Scene(FXMLLoader.load(getClass().getResource("FXMLVisualizarDocentes.fxml")));
            stage.setScene(sceneVisualizarDocentes);
            stage.show();
        }catch(IOException ex){
            mostrarAlerta = Herramientas.creadorDeAlerta("Error", "No se pudo cargar la ventana siguiente,"
                + " intente más tarde", Alert.AlertType.ERROR);
            mostrarAlerta.showAndWait();
        }
    }
    
    @FXML
    private void visulizarCatalogoDeAcademicos(ActionEvent e){
        try{
            Stage stage = (Stage) btCerrarSesion.getScene().getWindow();
            Scene sceneVisualizarCatalogoAcademicos = new Scene(FXMLLoader.load(getClass().getResource("FXMLVisualizarCatalogoDeCuerpoAcademico.fxml")));
            stage.setScene(sceneVisualizarCatalogoAcademicos);
            stage.show();
        }catch(IOException ex){
            mostrarAlerta = Herramientas.creadorDeAlerta("Error","No se pudo cargar la ventana siguiente,"
                + " intente más tarde", Alert.AlertType.ERROR);
            mostrarAlerta.showAndWait();
        }
    }
    
    @FXML
    private void visualizarCatalogoLGCA(ActionEvent e){
        try{
            Stage stage = (Stage) btCerrarSesion.getScene().getWindow();
            Scene sceneVisualizarCatalogoLGCA = new Scene(FXMLLoader.load(getClass().getResource("FXMLVisualizarCatalogoLGCA.fxml")));
            stage.setScene(sceneVisualizarCatalogoLGCA);
            stage.show();
        }catch(IOException ex){
            mostrarAlerta = Herramientas.creadorDeAlerta("Error", "No se pudo cargar la ventana siguiente,"
                + " intente más tarde", Alert.AlertType.ERROR);
            mostrarAlerta.showAndWait();
        }
    }
    
    @FXML
    private void visualizarCatalogoDeEstudiantes(ActionEvent e){
        try{
            Stage stage = (Stage) btCatalogoEstudiantes.getScene().getWindow();
            Scene sceneVisualizarCatalogoEstudiantes = new Scene(FXMLLoader.load(getClass().getResource("FXMLCatalogoEstudiantes.fxml")));
            stage.setScene(sceneVisualizarCatalogoEstudiantes);
            stage.show();
        }catch(IOException ex){
            mostrarAlerta = Herramientas.creadorDeAlerta("Error", "No se pudo cargar la ventana siguiente,"
                + " intente más tarde", Alert.AlertType.ERROR);
            mostrarAlerta.showAndWait();
        }
    }
    
}
