/*
 *Autor: Brandon Trujillo
 *fechaCreación: 28/11/2020
 */
package JavaFXGUI.Ventanas;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import pojos.Docente;
import util.Herramientas;

public class FXMLActualizaDocenteController implements Initializable {
    
    private Docente editarDocente;
    @FXML
    private TextField tfNombre;
    @FXML
    private TextField tfNumeroDePersonal;
    @FXML
    private TextField tfTelefono;
    @FXML
    private TextField tfCorreo;
    @FXML
    private TextField tfConstraseña;
    
    Alert mostrarAlerta;
  
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    } 
    
    @FXML
    private void cancelar(ActionEvent e){
        Stage stage = (Stage) tfNombre.getScene().getWindow();
        stage.close();
    }
    
    public void inicializaCampos(Docente editarDocente){
        this.editarDocente = editarDocente;
        
        obtenerDocenteSeleccionado();
    }
    
    private void obtenerDocenteSeleccionado(){
        tfNombre.setText(editarDocente.getNombre());
        tfNumeroDePersonal.setText(editarDocente.getNumeroPersonal());
        tfTelefono.setText(editarDocente.getTelefono());
        tfCorreo.setText(editarDocente.getCorreo());
        tfConstraseña.setText(editarDocente.getContraseña());
    }
    
   
}
