/*
Autor: Brandon Trujillo
fechaDeCreación: 28/11/2020
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

public class FXMLVisualizarCatalogoDeAcademicosController implements Initializable {

    @FXML
    private TableView<?> tvContenedor;
    @FXML
    private TableColumn<?, ?> tcNombreDeCatalogo;
    @FXML
    private TableColumn<?, ?> tcFechaDeRegistroCatalogo;

    private void cancelar(ActionEvent e){
        try{
            Stage stage = (Stage) tvContenedor.getScene().getWindow();
            Scene visualizarCatalogoAcademicos = new Scene(FXMLLoader.load(getClass().getResource("FXMLVentanaPrincipalDirectorDeLaFacultad.fxml")));
            stage.setScene(visualizarCatalogoAcademicos);
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
