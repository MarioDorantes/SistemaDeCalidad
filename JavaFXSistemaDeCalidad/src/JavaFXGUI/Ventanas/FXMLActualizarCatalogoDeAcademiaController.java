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
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import pojos.CatalogoDeAcademia;
import pojos.Licenciatura;
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
    private ComboBox<Licenciatura> cbLicenciaturas;
    @FXML
    private TableView<CatalogoDeAcademia> tbTabla;
    
    Alert mostrarAlerta;
    
    private ObservableList<CatalogoDeAcademia> catalogos;
    private ObservableList<Licenciatura> licenciaturas;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        catalogos = FXCollections.observableArrayList();
        licenciaturas = FXCollections.observableArrayList();
        
        this.colNombreAcademia.setCellValueFactory(new PropertyValueFactory("nombreAcademia"));
        this.colNombreCoordinador.setCellValueFactory(new PropertyValueFactory("nombreCoordinador"));
        
        cargarNombreLicenciaturas();
        
        cbLicenciaturas.valueProperty().addListener(new ChangeListener <Licenciatura>(){
            @Override
            public void changed(ObservableValue<? extends Licenciatura> observable, Licenciatura oldValue, Licenciatura newValue) {
                if(newValue != null){
                    limpiarCampos();
                    extraerDatosDelCatalogo(newValue.getIdLicenciatura());
                }
            }
            
        });
        
    } 
    
    @FXML
    private void cancelar(javafx.event.ActionEvent event) {
        mostrarAlerta = Herramientas.creadorDeAlerta("Cancelar", "¿Seguro desea cancelar?", Alert.AlertType.CONFIRMATION);
        Optional<ButtonType> opcionSeleccionada = mostrarAlerta.showAndWait(); 
        
        if(opcionSeleccionada.get() == ButtonType.OK){
            salir();
        }
    }
    
    private void cargarNombreLicenciaturas(){      
        Connection conn = ConectarBD.abrirConexionMySQL();
        
        if(conn != null){
            try{
                String consulta = "SELECT * FROM licenciatura;";
                PreparedStatement ps = conn.prepareStatement(consulta);
                ResultSet rs = ps.executeQuery();
                while (rs.next()){
                    Licenciatura licenciatura = new Licenciatura();
                    licenciatura.setIdLicenciatura(rs.getInt("idLicenciatura"));
                    licenciatura.setNombreLicenciatura(rs.getString("nombreLicenciatura"));
                    licenciaturas.add(licenciatura);
                }
                
                cbLicenciaturas.setItems(licenciaturas);                                
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
    
    private void extraerDatosDelCatalogo(int idLicenciatura){
        tbTabla.getItems().clear();
        Connection conn = ConectarBD.abrirConexionMySQL();
        
        if(conn != null){
            try{
                String consulta = "SELECT * FROM catalogoDeAcademia WHERE idLicenciatura = ?";
                PreparedStatement ps = conn.prepareStatement(consulta);
                ps.setInt(1, idLicenciatura);
                ResultSet rs = ps.executeQuery();
                
                while(rs.next()){
                    CatalogoDeAcademia catalogoA = new CatalogoDeAcademia();
                    catalogoA.setIdCatalogoDeAcademia(rs.getInt("idCatalogoDeAcademia"));
                    catalogoA.setIdLicenciatura(rs.getInt("idLicenciatura"));
                    catalogoA.setNombreAcademia(rs.getString("nombreAcademia"));
                    catalogoA.setNombreCoordinador(rs.getString("nombreCoordinador"));
                    catalogoA.setEstatus(rs.getString("estatus"));  
                    catalogos.add(catalogoA);
                }
                tbTabla.setItems(catalogos);
                conn.close();
                
            }catch(SQLException ex){
                mostrarAlerta = Herramientas.creadorDeAlerta("Error de consulta", ex.getMessage(), Alert.AlertType.ERROR);
                mostrarAlerta.showAndWait();
            }
        }else{
            mostrarAlerta = Herramientas.creadorDeAlerta("Error de conexion a la base de datos", "No hay conexión a la base de datos. Intente más tarde", Alert.AlertType.ERROR);
            mostrarAlerta.showAndWait();
        }
    }
        
    private void limpiarCampos(){
        tfNombreAcademia.setText("");
        tfNombreCoordinador.setText("");
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

    @FXML
    private void clicEditar(ActionEvent event) {
        limpiarCampos();
        int seleccion = tbTabla.getSelectionModel().getSelectedIndex();     
        
        if (seleccion >= 0) {
            CatalogoDeAcademia catalogoSeleccionado = catalogos.get(seleccion);
            tfNombreAcademia.setText(catalogoSeleccionado.getNombreAcademia());
            tfNombreCoordinador.setText(catalogoSeleccionado.getNombreCoordinador());
        } else {
            mostrarAlerta = Herramientas.creadorDeAlerta("Sin selección", "Seleccione un registro para poder editarlo", Alert.AlertType.ERROR);
            mostrarAlerta.showAndWait();  
        }
    }
}
