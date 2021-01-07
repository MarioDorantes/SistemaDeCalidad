/*
 *Autor: Brandon Trujillo
 *fechaCreación: 28/11/2020
 */
package JavaFXGUI.Ventanas;

import interfaz.NotificaCambios;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;

public class FXMLActualizarCatalogoDeCuerpoAcademicoController implements Initializable {

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
    private ComboBox<?> cbSeleccionarCatalogo;
    
    Alert mostrarAlerta;
    NotificaCambios notificacion;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    
    public void inicializaCampos(NotificaCambios notificacion){
        
    }
    
    @FXML
    private void cancelar(ActionEvent event) {
    }
    
}
