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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class FXMLActualizarAvancesProgramaticosController implements Initializable {

    @FXML
    private TextField tfNombreCatalogo;
    @FXML
    private TableView<?> tvUnidades;
    @FXML
    private TableColumn<?, ?> tcUnidad;
    @FXML
    private TableColumn<?, ?> tcFechaInicioFinDeUnidad;
    @FXML
    private TableColumn<?, ?> tcTemasDeUnidad;
    @FXML
    private ComboBox<?> cbSeleccionarAvance;
    @FXML
    private TextField tfEstatus;
    @FXML
    private TableView<?> tvAvancesDeUnidad;
    @FXML
    private TableColumn<?, ?> tcUnidadAvance;
    @FXML
    private TableColumn<?, ?> tcPorcentajeDeAvance;
    @FXML
    private TableColumn<?, ?> tcObservaciones;
    @FXML
    private TextField tfExperienciaEducativa;
    @FXML
    private TextField tfNrc;
    @FXML
    private TextField tfLicenciatura;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void cancelar(ActionEvent event) {
        try{
            Stage stage = (Stage) tvUnidades.getScene().getWindow();
            Scene cancelar = new Scene(FXMLLoader.load(getClass().getResource("FXMLVisualizarAvancesProgramaticos.fxml")));
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
