/*
 *Autor: Brandon Trujillo
 *fechaDeCreación: 01/12/2020
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import util.Herramientas;

public class FXMLRegistrarCatalogoLGCAController implements Initializable {

    @FXML
    private TableView<?> tvContenedor;
    @FXML
    private TableColumn<?, ?> tcClave;
    @FXML
    private TableColumn<?, ?> tcGradoDeConsolidacion;
    @FXML
    private TableColumn<?, ?> tcDesDeAdscripcion;
    @FXML
    private TableColumn<?, ?> tcUnidadDeAdscripcion;
    @FXML
    private TextField tfResponsable;
    @FXML
    private TextField tfCorreoDeResponsable;
    @FXML
    private TextField tfUnidadDeAdscripcion;
    @FXML
    private TextField tfDesDeAdscripcion;
    @FXML
    private TextField tfGradoConsolidacion;
    @FXML
    private TextField tfClave;
    @FXML
    private TextField tfNombreCatalogo;
    @FXML
    private TextField tfFechaRegistroCatalogo;

    Alert mostrarAlerta;
    
     private void cancelar(ActionEvent e){
        try{
            Stage stage = (Stage) tvContenedor.getScene().getWindow();
            Scene cancelar = new Scene(FXMLLoader.load(getClass().getResource("FXMLVisualizarCatalogoLGCA.fxml")));
            stage.setScene(cancelar);
            stage.show();
        }catch(IOException ex){
            mostrarAlerta = Herramientas.creadorDeAlerta("Error", ex.getMessage(), Alert.AlertType.ERROR);
            mostrarAlerta.showAndWait();
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }   
    
}
