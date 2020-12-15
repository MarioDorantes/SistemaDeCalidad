/*
 *Autor: Brandon Trujillo
 *fechaDeCración: 02/12/2020
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
import util.Herramientas;

public class FXMLActualizarPlanDeTrabajoDeAcademiaController implements Initializable {

    @FXML
    private TextField tfExperienciaEducativa;
    @FXML
    private TextField tfLicenciatura;
    @FXML
    private TextField tfNombrePlan;
    @FXML
    private TextField tfEstatus;
    @FXML
    private ComboBox<?> cbSeleccionarPlan;
    @FXML
    private TableView<?> tvActividades;
    @FXML
    private TableColumn<?, ?> tcActividad;
    @FXML
    private TableColumn<?, ?> tcFecha;
    @FXML
    private TableColumn<?, ?> tcValor;
    @FXML
    private TableView<?> tvExamenes;
    @FXML
    private TableColumn<?, ?> tcTipoExamen;
    @FXML
    private TableColumn<?, ?> tcFechaAplicacion;
    @FXML
    private TableColumn<?, ?> tcTemas;
    @FXML
    private TableColumn<?, ?> tcValorExamen;
    
    Alert mostrarAlerta;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void cancelar(ActionEvent event) {
        try{
            Stage stage = (Stage) tvExamenes.getScene().getWindow();
            Scene cancelar = new Scene(FXMLLoader.load(getClass().getResource("FXMLVisualizarPlanDeTrabajoDeAcademia.fxml")));
            stage.setScene(cancelar);
            stage.show(); 
        } catch(IOException ex){
            mostrarAlerta = Herramientas.creadorDeAlerta("Error", ex.getMessage(), Alert.AlertType.ERROR);
            mostrarAlerta.showAndWait();
        }
    }
    
}
