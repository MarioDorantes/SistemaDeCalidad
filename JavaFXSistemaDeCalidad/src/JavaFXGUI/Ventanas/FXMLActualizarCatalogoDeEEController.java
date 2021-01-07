/*
Autor: Mario Dorantes
FechaDeCreación: 02/12/2020
 */

package JavaFXGUI.Ventanas;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import pojos.CatalogoDeEE;
import pojos.Licenciatura;
import util.Herramientas;


public class FXMLActualizarCatalogoDeEEController implements Initializable {

    @FXML
    private Button btCancelar;
    @FXML
    private TextField tfNombreEE;
    @FXML
    private TextField tfPeriodo;
    @FXML
    private TextField tfBloque;
    @FXML
    private TextField tfCreditos;
    @FXML
    private TextField tfNrc;
    @FXML
    private TextField tfPrograma;
    @FXML
    private TableView<CatalogoDeEE> tbTabla;
    @FXML
    private TableColumn colPrograma;
    @FXML
    private TableColumn  colNrc;
    @FXML
    private TableColumn colNombreDeLaEE;
    @FXML
    private TableColumn colCreditos;
    @FXML
    private TableColumn colBloque;
    @FXML
    private TableColumn colPeriodo;
    @FXML
    private RadioButton rbActivo;
    @FXML
    private ToggleGroup tgEstatus;
    @FXML
    private RadioButton rbInactivo;
    @FXML
    private ComboBox<Licenciatura> cbLicenciaturas;
    
    Alert mostrarAlerta;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML
    private void cancelar(javafx.event.ActionEvent event) {
        mostrarAlerta = Herramientas.creadorDeAlerta("Cancelar", "¿Seguro desea cancelar?", Alert.AlertType.CONFIRMATION);
        Optional<ButtonType> opcionSeleccionada = mostrarAlerta.showAndWait(); 
        
        if(opcionSeleccionada.get() == ButtonType.OK){
            salir();
        }
    }
    
    private void salir(){
        try{
            Stage stage = (Stage) btCancelar.getScene().getWindow();
            Scene sceneVisualizarCatalogoDeEE = new Scene(FXMLLoader.load(getClass().getResource("FXMLVisualizarCatalogoDeEE.fxml")));
            stage.setScene(sceneVisualizarCatalogoDeEE);
            stage.show(); 
        } catch(IOException ex){
            mostrarAlerta = Herramientas.creadorDeAlerta("Error", "No se pudo cargar la ventana siguiente. Intente más tarde", Alert.AlertType.ERROR);
            mostrarAlerta.showAndWait();  
        }
    }

    @FXML
    private void clicActualizar(ActionEvent event) {
    }

    @FXML
    private void clicEliminar(ActionEvent event) {
    }

    @FXML
    private void clicFinalizar(ActionEvent event) {
    }

    @FXML
    private void clicEditar(ActionEvent event) {
    }
}
