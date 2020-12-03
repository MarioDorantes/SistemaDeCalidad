/*
 *Autor: Brandon Trujillo
 *fechaCreación: 02/12/2020
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

public class FXMLVisualizarAvancesProgramaticosController implements Initializable {

    @FXML
    private TableView<?> tvAvances;
    @FXML
    private TableColumn<?, ?> tcNombreDelAvance;
    @FXML
    private TableColumn<?, ?> tcExperienciaEducativa;
    @FXML
    private TableColumn<?, ?> tcNrc;
    @FXML
    private TableColumn<?, ?> tcDcente;
    @FXML
    private TableColumn<?, ?> tcFechaRegistro;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void irARegistrarAvance(ActionEvent event) {
        try{
            Stage stage = (Stage) tvAvances.getScene().getWindow();
            Scene registrarAvance = new Scene(FXMLLoader.load(getClass().getResource("FXMLRegistrarAvancesProgramaticos.fxml")));
            stage.setScene(registrarAvance);
            stage.show(); 
        } catch(IOException ex){
            mostrarAlertas("Error", "No fue posible cargar la siguiente ventana, intente más tarde");
        }
    }

    @FXML
    private void irAActualizarAvance(ActionEvent event) {
        try{
            Stage stage = (Stage) tvAvances.getScene().getWindow();
            Scene actualizarAvance = new Scene(FXMLLoader.load(getClass().getResource("FXMLActualizarAvancesProgramaticos.fxml")));
            stage.setScene(actualizarAvance);
            stage.show(); 
        } catch(IOException ex){
            mostrarAlertas("Error", "No fue posible cargar la siguiente ventana, intente más tarde");
        }
    }

    @FXML
    private void cancelar(ActionEvent event) {
        try{
            Stage stage = (Stage) tvAvances.getScene().getWindow();
            Scene cancelar = new Scene(FXMLLoader.load(getClass().getResource("FXMLVentanaPrincipalDocente.fxml")));
            stage.setScene(cancelar);
            stage.show(); 
        } catch(IOException ex){
            mostrarAlertas("Error", "No fue posible cargar la siguiente ventana, intente más tarde");
        }
    }
    
    public void mostrarAlertas(String titulo, String dialogo){
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle(titulo);
        alerta.setContentText(dialogo);
        alerta.setHeaderText(null);
        alerta.showAndWait();
    }
    
}
