/*
Autor: Mario Dorantes
fechaCreación: 25/11/2020
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import pojos.CatalogoDeAcademia;
import util.Herramientas;


public class FXMLRegistrarCatalogoDeAcademiaController implements Initializable {

    @FXML
    private Button btCancelar;
    @FXML
    private TextField tfNombreLicenciatura;
    @FXML
    private TextField tfNombreAcademia;
    @FXML
    private TextField tfNombreCoordinador;
    @FXML
    private TableView<CatalogoDeAcademia> tbTabla;
    @FXML
    private TableColumn colNombreAcademia;
    @FXML
    private TableColumn colNombreCoordinador;
    
    Alert mostrarAlerta;
    
    private static final String estatus = "Activo";
    
    private ObservableList<CatalogoDeAcademia> registrosDelCatalogo;
    
    CatalogoDeAcademia catalogo = new CatalogoDeAcademia();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        registrosDelCatalogo = FXCollections.observableArrayList();
        this.colNombreAcademia.setCellValueFactory(new PropertyValueFactory("nombreAcademia"));
        this.colNombreCoordinador.setCellValueFactory(new PropertyValueFactory("nombreCoordinador"));
    }    
    
    @FXML
    private void cancelar(javafx.event.ActionEvent event) {
        mostrarAlerta = Herramientas.creadorDeAlerta("Cancelar", "¿Seguro desea cancelar?", Alert.AlertType.CONFIRMATION);
        Optional<ButtonType> opcionSeleccionada = mostrarAlerta.showAndWait(); 
        
        if(opcionSeleccionada.get() == ButtonType.OK){
                salir();
        }
    }
    
    @FXML
    private void clicRegistrar(ActionEvent event) {
        boolean esValido = true;
        
        String nomLicenciaturaAux = tfNombreLicenciatura.getText();
        String nomAcademiaAux = tfNombreAcademia.getText();
        String nomCoordinadorAux = tfNombreCoordinador.getText();
        
        if(nomLicenciaturaAux.isEmpty()){
            esValido = false;
        } 
        if(nomAcademiaAux.isEmpty()){
            esValido = false;
        }
        if(nomCoordinadorAux.isEmpty()){
            esValido = false;
        }
        
        if(esValido){
            tfNombreLicenciatura.setEditable(false);
            limpiarCampos();
            guardarCatalogoDeAcademia(nomLicenciaturaAux, nomAcademiaAux, nomCoordinadorAux, estatus);
        } else {
            mostrarAlerta = Herramientas.creadorDeAlerta("Campos Obligatorios", "Favor de no dejar campos vacios", Alert.AlertType.ERROR);
            mostrarAlerta.showAndWait();
        }
        
    }
    
    private void limpiarCampos(){
        tfNombreAcademia.setText("");
        tfNombreCoordinador.setText("");
    }
    
    @FXML
    private void clicEliminar(ActionEvent event) {
        int seleccion = tbTabla.getSelectionModel().getSelectedIndex();
        if (seleccion >= 0) {
            CatalogoDeAcademia registroAEliminar = registrosDelCatalogo.get(seleccion);
            eliminarRegistro(registroAEliminar.getNombreAcademia());
        } else {
            Alert alertaVacio = Herramientas.creadorDeAlerta("Sin selección", "Para eliminar un registro, debe seleccionarlo de la tabla", Alert.AlertType.WARNING);
            alertaVacio.showAndWait();
        }
    }
    
    private void eliminarRegistro(String nombreAcademia){
        Connection conn = ConectarBD.abrirConexionMySQL();
        if(conn != null){
            try{
                String consulta = "DELETE FROM catalogoDeAcademia WHERE nombreAcademia = ?";
                PreparedStatement ps = conn.prepareStatement(consulta);
                ps.setString(1, nombreAcademia);
                int resultado = ps.executeUpdate();
                conn.close();
                
                limpiarTabla();
                //cargarRegistrosDelCatalogo();
                
                
            } catch(SQLException ex){
                mostrarAlerta = Herramientas.creadorDeAlerta("Error en la conexión a la base de datos", ex.getMessage(), Alert.AlertType.ERROR);
                mostrarAlerta.showAndWait();
            }
        }
    }
    
    @FXML
    private void clicFinalizarRegistro(ActionEvent event) {
        
        if(tbTabla.getItems().isEmpty()){
            mostrarAlerta = Herramientas.creadorDeAlerta("Error", "Para finalizar el registro, debe registrar al menos un elemento", Alert.AlertType.INFORMATION);
            mostrarAlerta.showAndWait();
        } else {
            mostrarAlerta = Herramientas.creadorDeAlerta("Mensaje de confirmación", "Catálogo de Academia registrado exitosamente", Alert.AlertType.INFORMATION);
            mostrarAlerta.showAndWait();
            salir();
        }
        
    }
    
    
    private void guardarCatalogoDeAcademia(String nombreLicenciatura, String nombreAcademia, String nombreCoordinador, String estatus){
        Connection conn = ConectarBD.abrirConexionMySQL();
        if(conn != null){
            try{
                int resultado;
                String consulta = "INSERT INTO catalogoDeAcademia (nombreLicenciatura, nombreAcademia, nombreCoordinador, estatus) VALUES (?, ?, ?, ?)";
                PreparedStatement ps = conn.prepareStatement(consulta);
                ps.setString(1, nombreLicenciatura);
                ps.setString(2, nombreAcademia);
                ps.setString(3, nombreCoordinador);
                ps.setString(4, estatus);
                resultado = ps.executeUpdate();
                
                conn.close();
                
                if(resultado > 0){
                    mostrarAlerta = Herramientas.creadorDeAlerta("Mensaje de confirmación", "Registro exitoso", Alert.AlertType.INFORMATION);
                    mostrarAlerta.showAndWait();
                } else {
                    mostrarAlerta = Herramientas.creadorDeAlerta("Mensaje de error", "Error al registrar", Alert.AlertType.ERROR);
                    mostrarAlerta.showAndWait();
                }                               
                
                limpiarTabla();
                //Como ya deberia estar en la base, carga el registro donde el nombrelicenciatura sea igual al que mando
                //cargarRegistrosDelCatalogo();//osea con parametro nombreLicenciatura
                cargarRegistrosPorLicenciatura(tfNombreLicenciatura.getText());
            } catch (SQLException ex){
                mostrarAlerta = Herramientas.creadorDeAlerta("Error en la conexión a la base de datos", ex.getMessage(), Alert.AlertType.ERROR);
                mostrarAlerta.showAndWait();
            }
        }
    }
    
    private void cargarRegistrosPorLicenciatura(String nombreLicenciatura){
        Connection conn = ConectarBD.abrirConexionMySQL();
        
        if(conn != null){
            try{
                String consulta = "SELECT * FROM catalogoDeAcademia WHERE nombreLicenciatura = ?";
                PreparedStatement ps = conn.prepareStatement(consulta);
                ps.setString(1, nombreLicenciatura);
                ResultSet rs = ps.executeQuery();
                
                while(rs.next()){
                    catalogo.setIdCatalogoDeAcademia(rs.getInt("idCatalogoDeAcademia"));
                    catalogo.setNombreLicenciatura(rs.getString("nombreLicenciatura"));
                    catalogo.setNombreAcademia(rs.getString("nombreAcademia"));
                    catalogo.setNombreCoordinador(rs.getString("nombreCoordinador"));
                    catalogo.setEstatus(rs.getString("estatus"));
                    registrosDelCatalogo.add(catalogo);
                }
                
                tbTabla.setItems(registrosDelCatalogo);
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
    
    //ESTE SRIVE MIENTRAS PARA EL DE ELIMINAR, PERO HAY QUE MODIFICARLO, PRIMERO QUE JALE EL REGISTRO
    /*
    private void cargarRegistrosDelCatalogo(){
        Connection conn = ConectarBD.abrirConexionMySQL();
        if(conn != null){
            try{
                String consulta = "SELECT nombreAcademia, nombreCoordinador FROM catalogoDeAcademia;";
                PreparedStatement ps = conn.prepareStatement(consulta);
                ResultSet rs = ps.executeQuery();
                
                while (rs.next()) {
                    CatalogoDeAcademia catalogo = new CatalogoDeAcademia();
                    catalogo.setNombreAcademia(rs.getString("nombreAcademia"));
                    catalogo.setNombreCoordinador(rs.getString("nombreCoordinador"));
                    registrosDelCatalogo.add(catalogo);
                }

                tbTabla.setItems(registrosDelCatalogo);
                conn.close();
                
            }catch(SQLException ex){
                mostrarAlerta = Herramientas.creadorDeAlerta("Error en la conexión a la base de datos", ex.getMessage(), Alert.AlertType.ERROR);
                mostrarAlerta.showAndWait();
            }
        } else {
            mostrarAlerta = Herramientas.creadorDeAlerta("Error en la conexión a la base de datos", "No hay conexión a la base de datos. Intente más tarde", Alert.AlertType.ERROR);
            mostrarAlerta.showAndWait();
        }
    }*/
    
    private void limpiarTabla(){
        tbTabla.getItems().clear();
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
