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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import pojos.CatalogoDeAcademia;
import pojos.Licenciatura;
import util.Herramientas;
import validaciones.Validaciones;


public class FXMLRegistrarCatalogoDeAcademiaController implements Initializable {

    @FXML
    private Button btCancelar;
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
    @FXML
    private ComboBox<Licenciatura> cbNombreLicenciatura;
    
    Alert mostrarAlerta;
    
    private final String estatus = "Activo";
    
    private ObservableList<CatalogoDeAcademia> registrosDelCatalogo;
    private ObservableList<Licenciatura> licenciaturas;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        registrosDelCatalogo = FXCollections.observableArrayList();
        licenciaturas = FXCollections.observableArrayList();
        cargarNombresLicenciaturas();
        this.colNombreAcademia.setCellValueFactory(new PropertyValueFactory("nombreAcademia"));
        this.colNombreCoordinador.setCellValueFactory(new PropertyValueFactory("nombreCoordinador"));
        
        cbNombreLicenciatura.valueProperty().addListener(new ChangeListener <Licenciatura>(){
            @Override
            public void changed(ObservableValue<? extends Licenciatura> observable, Licenciatura oldValue, Licenciatura newValue) {
                if(newValue != null){
                    limpiarCampos();
                    limpiarTabla();
                    cargarRegistrosPorLicenciatura(newValue.getIdLicenciatura());                    
                }
            }
            
        });
    }    
    
    private void cargarNombresLicenciaturas(){
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
                
                cbNombreLicenciatura.setItems(licenciaturas);                                
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
        
        int posicionNombreLicenciatura = cbNombreLicenciatura.getSelectionModel().getSelectedIndex();
        String nomAcademiaAux = tfNombreAcademia.getText();
        String nomCoordinadorAux = tfNombreCoordinador.getText();
        
        if(posicionNombreLicenciatura < 0){
            esValido = false;
        } 
        if(nomAcademiaAux.isEmpty()){
            esValido = false;
        }
        if(nomCoordinadorAux.isEmpty()){
            esValido = false;
        }
        
        if(esValido){
            cbNombreLicenciatura.setEditable(false);
            Validaciones datoAValidar = new Validaciones();
            
            if(!datoAValidar.validarNombreAcademia(nomAcademiaAux)){
                mostrarAlerta = Herramientas.creadorDeAlerta("Nombre de Academia Incorrecto", "Formato: Solo letras. \nEjemplo: Bases de Datos", Alert.AlertType.ERROR);
                mostrarAlerta.showAndWait();
            } else if(!datoAValidar.validarNombre(nomCoordinadorAux)){
                mostrarAlerta = Herramientas.creadorDeAlerta("Nombre de Coordinador Incorrecto", "Formato: 1 o 2 nombres y apellidos. Primeras letras en mayúscula. \nEjemplo: Manuel Reyes Ochoa", Alert.AlertType.ERROR);
                mostrarAlerta.showAndWait();
            }
            if(datoAValidar.validarNombreAcademia(nomAcademiaAux) && datoAValidar.validarNombre(nomCoordinadorAux)){
                limpiarCampos();
                guardarCatalogoDeAcademia(licenciaturas.get(posicionNombreLicenciatura).getIdLicenciatura(), nomAcademiaAux, nomCoordinadorAux, estatus);
            }
            
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
            eliminarRegistro(registroAEliminar.getNombreAcademia(), registroAEliminar.getNombreCoordinador(), registroAEliminar.getIdLicenciatura());
        } else {
            Alert alertaVacio = Herramientas.creadorDeAlerta("Sin selección", "Para eliminar un registro, debe seleccionarlo de la tabla", Alert.AlertType.WARNING);
            alertaVacio.showAndWait();
        }
    }
    
    private void eliminarRegistro(String nombreAcademia, String nombreCoordinador, int idLicenciatura){
        Connection conn = ConectarBD.abrirConexionMySQL();
        if(conn != null){
            try{
                String consulta = "DELETE FROM catalogoDeAcademia WHERE nombreAcademia = ? AND nombreCoordinador = ?";
                PreparedStatement ps = conn.prepareStatement(consulta);
                ps.setString(1, nombreAcademia);
                ps.setString(2, nombreCoordinador);
                int resultado = ps.executeUpdate();
                conn.close();
                
                limpiarTabla();
                limpiarCampos();
                cargarRegistrosPorLicenciatura(idLicenciatura);
                
                
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
    
    
    private void guardarCatalogoDeAcademia(int nombreLicenciatura, String nombreAcademia, String nombreCoordinador, String estatus){
        Connection conn = ConectarBD.abrirConexionMySQL();
        if(conn != null){
            try{
                int resultado;
                String consulta = "INSERT INTO catalogoDeAcademia (idLicenciatura, nombreAcademia, nombreCoordinador, estatus) VALUES (?, ?, ?, ?)";
                PreparedStatement ps = conn.prepareStatement(consulta);
                ps.setInt(1, nombreLicenciatura);
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
                cargarRegistrosPorLicenciatura(nombreLicenciatura);
                    
                
            } catch (SQLException ex){
                mostrarAlerta = Herramientas.creadorDeAlerta("Error en la conexión a la base de datos", ex.getMessage(), Alert.AlertType.ERROR);
                mostrarAlerta.showAndWait();
            }
        }
    }
    
    private void cargarRegistrosPorLicenciatura(int idLicenciatura){
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
                    registrosDelCatalogo.add(catalogoA);
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
