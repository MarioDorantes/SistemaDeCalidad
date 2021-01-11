/*
Autor: Mario Dorantes
fechaCreación: 11/01/2020
 */

package JavaFXGUI.Ventanas;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class FXMLCatalogoEstudiantesController implements Initializable {

    @FXML
    private TextField tfNombreEstudiante;
    @FXML
    private TextField tfMatricula;
    @FXML
    private TextField tfCorreo;
    @FXML
    private TableView<?> tbTablaEstudiantes;
    @FXML
    private TableColumn colNombreEstudiante;
    @FXML
    private TableColumn colMatricula;
    @FXML
    private TableColumn colCorreo;
    @FXML
    private Button btSalir;

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    

    @FXML
    private void clicRegistrar(ActionEvent event) {
    }

    @FXML
    private void clicEditar(ActionEvent event) {
    }

    @FXML
    private void clicActualizar(ActionEvent event) {
    }

    @FXML
    private void clicEliminar(ActionEvent event) {
    }

    @FXML
    private void clicSalir(ActionEvent event) {
    }
    
}
