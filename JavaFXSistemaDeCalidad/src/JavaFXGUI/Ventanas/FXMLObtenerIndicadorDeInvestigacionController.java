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

public class FXMLObtenerIndicadorDeInvestigacionController implements Initializable {

    @FXML
    private TextField tfNumeroDeAcademicosEnProyectos;
    @FXML
    private TextField tfNumeroDeEstudiantesEnProyectos;
    @FXML
    private TextField tfNumeroDeAprobadosCeneval;
    @FXML
    private TextField tfNumeroAprobadosTrabajoRecepcional;
    
    @FXML
    private void cancelar(ActionEvent event) {
        try{
            Stage stage = (Stage) tfNumeroDeAprobadosCeneval.getScene().getWindow();
            Scene cancelar = new Scene(FXMLLoader.load(getClass().getResource("FXMLVentanaPrincipalDirectorDeLaFacultad.fxml")));
            stage.setScene(cancelar);
            stage.show();
        }catch(IOException ex){
            mostrarAlertas("Error", "No se pudo cargar la ventana siguiente, intente más tarde");
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void mostrarAlertas(String titulo, String dialogo){
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(dialogo);
        alerta.showAndWait();
    }

    
}
