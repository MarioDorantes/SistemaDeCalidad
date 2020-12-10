/*
 *Autor: Brandon Trujillo
 *fechaCreación: 02/12/2020
 */
package javaFxVentanas;

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
import util.Herramientas;

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
    
    Alert mostrarAlerta;

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
            mostrarAlerta = Herramientas.creadorDeAlerta("Error", ex.getMessage(), Alert.AlertType.ERROR);
            mostrarAlerta.showAndWait();
        }
    }
    
}
