/*
Autor: Brandon Trujillo
fechaCreación: 28/11/2020
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
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author tbc_h
 */
public class FXMLActualizarCatalogoDeAcademicosController implements Initializable {

    @FXML
    private TableColumn<?, ?> tcNombre;
    @FXML
    private TableColumn<?, ?> tcCorreo;
    @FXML
    private TableColumn<?, ?> tcNumeroDePersonal;
    @FXML
    private TableColumn<?, ?> tcCubiculo;
    @FXML
    private TableColumn<?, ?> tcPrograma;
    @FXML
    private TextField tfNombreCatalogo;

    @FXML
    private void cancelar(ActionEvent e){
        try{
            Stage stage = (Stage) tfNombreCatalogo.getScene().getWindow();
            Scene visualizarCatalogoAcademicos = new Scene(FXMLLoader.load(getClass().getResource("FXMLVisualizarCatalogoDeAcademicos.fxml")));
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
