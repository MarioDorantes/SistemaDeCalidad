/*
 *Autor: Brandon Trujillo
 *fechaDeCreacion: 01/12/2020
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
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import util.Herramientas;

public class FXMLObtenerIndicadorDeInvestigacionController implements Initializable {

    @FXML
    private TextField tfNumeroDeAcademicosEnProyectos;
    @FXML
    private TextField tfNumeroDeEstudiantesEnProyectos;
    @FXML
    private TextField tfNumeroDeAprobadosCeneval;
    @FXML
    private TextField tfNumeroAprobadosTrabajoRecepcional;
    
    Alert mostrarAlerta;
    
    @FXML
    private void cancelar(ActionEvent event) {
        try{
            Stage stage = (Stage) tfNumeroDeAprobadosCeneval.getScene().getWindow();
            Scene cancelar = new Scene(FXMLLoader.load(getClass().getResource("FXMLVentanaPrincipalDirectorDeLaFacultad.fxml")));
            stage.setScene(cancelar);
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
