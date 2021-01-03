/*
Autor: Mario Dorantes
fechaCreación: 02/12/2020
 */

package JavaFXGUI.Ventanas;

import conexionBD.ConectarBD;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.stage.Stage;
import pojos.CatalogoDeAcademia;
import util.Herramientas;


public class FXMLActualizarCatalogoDeAcademiaController implements Initializable {

    @FXML
    private Button btCancelar;
    @FXML
    private TextField tfNombreAcademia;
    @FXML
    private TextField tfNombreCoordinador;
    @FXML
    private TableColumn colNombreAcademia;
    @FXML
    private TableColumn colNombreCoordinador;
    @FXML
    private RadioButton rbActivo;
    @FXML
    private RadioButton rbInactivo;
    @FXML
    private ComboBox<CatalogoDeAcademia> cbLicenciaturas;
    @FXML
    private TableView<CatalogoDeAcademia> tbTabla;
    
    Alert mostrarAlerta;
    
    private ObservableList<CatalogoDeAcademia> catalogos;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        catalogos = FXCollections.observableArrayList();
        cargarCatalogosDeAcademia();
    } 
    
    @FXML
    private void cancelar(javafx.event.ActionEvent event) {
        mostrarAlerta = Herramientas.creadorDeAlerta("Cancelar", "¿Seguro desea cancelar?", Alert.AlertType.CONFIRMATION);
        Optional<ButtonType> opcionSeleccionada = mostrarAlerta.showAndWait(); 
        
        if(opcionSeleccionada.get() == ButtonType.OK){
            salir();
        }
    }
    
    private void cargarCatalogosDeAcademia(){
        Connection conn = ConectarBD.abrirConexionMySQL();
        
        if(conn != null){
            try{
                String consulta = "SELECT * FROM catalogoDeAcademia;";
                PreparedStatement ps = conn.prepareStatement(consulta);
                ResultSet rs = ps.executeQuery();
                while (rs.next()){
                    CatalogoDeAcademia catalogoA = new CatalogoDeAcademia();
                    catalogoA.setIdCatalogoDeAcademia(rs.getInt("idCatalogoDeAcademia"));
                    catalogoA.setNombreLicenciatura(rs.getString("nombreLicenciatura"));
                    catalogoA.setNombreAcademia(rs.getString("nombreAcademia"));
                    catalogoA.setNombreCoordinador(rs.getString("nombreCoordinador"));                    
                    catalogos.add(catalogoA);
                }
                
                cbLicenciaturas.setItems(catalogos);    
                conn.close();
            } catch(SQLException ex){
                mostrarAlerta = Herramientas.creadorDeAlerta("Error de consulta", ex.getMessage(), Alert.AlertType.ERROR);
                mostrarAlerta.showAndWait();
            }
        }else{
            mostrarAlerta = Herramientas.creadorDeAlerta("Error de conexion a la base de datos", "No hay conexión a la base de datos. Intente más tarde", Alert.AlertType.ERROR);
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
    private void clicFinalizarActualizacion(ActionEvent event) {
    }
    
    private void salir(){
        try{
            Stage stage = (Stage) btCancelar.getScene().getWindow();
            Scene sceneVisualizarCatalogoDeAcademia = new Scene(FXMLLoader.load(getClass().getResource("FXMLVisualizarCatalogoDeAcademia.fxml")));
            stage.setScene(sceneVisualizarCatalogoDeAcademia);
            stage.show(); 
        } catch(IOException ex){
            mostrarAlerta = Herramientas.creadorDeAlerta("Error", "No se pudo cargar la ventana siguiente. Intente más tarde", Alert.AlertType.ERROR);
            mostrarAlerta.showAndWait();  
        }
    }
}
