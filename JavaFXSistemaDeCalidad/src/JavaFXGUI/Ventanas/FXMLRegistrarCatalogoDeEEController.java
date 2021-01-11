/*
Autor: Mario Dorantes
FechaDeCreación: 30/11/2020
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
import pojos.CatalogoDeEE;
import pojos.Licenciatura;
import util.Herramientas;
import validaciones.Validaciones;


public class FXMLRegistrarCatalogoDeEEController implements Initializable {

    @FXML
    private Button btRegistrarCatalogo;
    @FXML
    private Button btCancelar;
    @FXML
    private TextField tfPrograma;
    @FXML
    private TextField tfNrc;
    @FXML
    private TextField tfCreditos;
    @FXML
    private TextField tfBloque;
    @FXML
    private TextField tfPeriodo;
    @FXML
    private TextField tfNombreEE;
    @FXML
    private TableView<CatalogoDeEE> tbTabla;
    @FXML
    private TableColumn colPrograma;
    @FXML
    private TableColumn colNrc;
    @FXML
    private TableColumn colNombreEE;
    @FXML
    private TableColumn colCreditos;
    @FXML
    private TableColumn colBloque;
    @FXML
    private TableColumn colPeriodo;
    @FXML
    private ComboBox<Licenciatura> cbLicenciaturas;
    
    Alert mostrarAlerta;
    
    private final String estatus = "Activo";
    
    private ObservableList<CatalogoDeEE> registrosDelCatalogo;
    private ObservableList<Licenciatura> licenciaturas;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        registrosDelCatalogo = FXCollections.observableArrayList();
        licenciaturas = FXCollections.observableArrayList();
        cargarNombresLicenciaturas();
        
        this.colPrograma.setCellValueFactory(new PropertyValueFactory("programa"));
        this.colNrc.setCellValueFactory(new PropertyValueFactory("nrc"));
        this.colNombreEE.setCellValueFactory(new PropertyValueFactory("nombreDeLaEE"));
        this.colCreditos.setCellValueFactory(new PropertyValueFactory("creditos"));
        this.colBloque.setCellValueFactory(new PropertyValueFactory("bloque"));
        this.colPeriodo.setCellValueFactory(new PropertyValueFactory("periodo"));
        
        cbLicenciaturas.valueProperty().addListener(new ChangeListener <Licenciatura>(){
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
    
    @FXML
    private void cancelar(javafx.event.ActionEvent event) {
        mostrarAlerta = Herramientas.creadorDeAlerta("Cancelar", "¿Seguro desea cancelar?", Alert.AlertType.CONFIRMATION);
        Optional<ButtonType> opcionSeleccionada = mostrarAlerta.showAndWait(); 
        
        if(opcionSeleccionada.get() == ButtonType.OK){
            salir();
        }
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

    @FXML
    private void clicRegistrar(ActionEvent event) {
        boolean esValido = true;
        
        int posicionNombreLicenciatura = cbLicenciaturas.getSelectionModel().getSelectedIndex();
        String programaAux = tfPrograma.getText();
        String nrcAux = tfNrc.getText();
        String nombreDeLaEEAux = tfNombreEE.getText();
        String creditosAux = tfCreditos.getText();
        String bloqueAux = tfBloque.getText();
        String periodoAux = tfPeriodo.getText();
        
        if(posicionNombreLicenciatura < 0){
            esValido = false;
        } 
        if(programaAux.isEmpty()){
            esValido = false;
        }
        if(nrcAux.isEmpty()){
            esValido = false;
        }
        if(nombreDeLaEEAux.isEmpty()){
            esValido = false;
        }
        if(creditosAux.isEmpty()){
            esValido = false;
        }
        if(bloqueAux.isEmpty()){
            esValido = false;
        }
        if(periodoAux.isEmpty()){
            esValido = false;
        }
        
        if(esValido){
            
            Validaciones datoAValidar = new Validaciones();
            
            if(!datoAValidar.validarPrograma(programaAux)){
                mostrarAlerta = Herramientas.creadorDeAlerta("Formato de programa incorrecto", "Ejemplo formato correcto: ISOF-14-E-CR", Alert.AlertType.ERROR);
                mostrarAlerta.showAndWait();
            } else if(!datoAValidar.validarNrc(nrcAux)){
                mostrarAlerta = Herramientas.creadorDeAlerta("NRC incorrecto", "Formato: NRC de 5 digitos \nEjemplo: 80612", Alert.AlertType.ERROR);
                mostrarAlerta.showAndWait();
            } else if(!datoAValidar.validarNombreDeLaEE(nombreDeLaEEAux)){
                mostrarAlerta = Herramientas.creadorDeAlerta("Nombre de la EE incorrecto", "Formato: Solo letras. Sin acentos. \nEjemplo: Introduccion a la programacion", Alert.AlertType.ERROR);
                mostrarAlerta.showAndWait();
            } else if(!datoAValidar.validarCreditos(creditosAux)){
                mostrarAlerta = Herramientas.creadorDeAlerta("Creditos incorrecto", "Formato: Solo numeros \nEjemplo: 8", Alert.AlertType.ERROR);
                mostrarAlerta.showAndWait();
            } else if(!datoAValidar.validarBloque(bloqueAux)){
                mostrarAlerta = Herramientas.creadorDeAlerta("Bloque incorrecto", "Formato: Bloque 1 o 2 \nEjemplo: 2", Alert.AlertType.ERROR);
                mostrarAlerta.showAndWait();
            } else if(!datoAValidar.validarPeriodo(periodoAux)){
                mostrarAlerta = Herramientas.creadorDeAlerta("Periodo incorrecto", "Formato: Periodo de 6 digitos \nEjemplo: 202101", Alert.AlertType.ERROR);
                mostrarAlerta.showAndWait();
            }
            
            if(datoAValidar.validarPrograma(programaAux) && datoAValidar.validarNrc(nrcAux) && datoAValidar.validarNombreDeLaEE(nombreDeLaEEAux)
                    && datoAValidar.validarCreditos(creditosAux) && datoAValidar.validarBloque(bloqueAux) && datoAValidar.validarPeriodo(periodoAux)){
                
                cbLicenciaturas.setEditable(false);
                limpiarCampos();
                guardarCatalogoDeEE(licenciaturas.get(posicionNombreLicenciatura).getIdLicenciatura(), programaAux, nrcAux, nombreDeLaEEAux, creditosAux, bloqueAux, periodoAux, estatus);
                
            }
         
        } else {
            mostrarAlerta = Herramientas.creadorDeAlerta("Campos Obligatorios", "Favor de no dejar campos vacios", Alert.AlertType.ERROR);
            mostrarAlerta.showAndWait();
        }
        
    }
    
    private void limpiarCampos(){
        tfPrograma.setText("");
        tfNrc.setText("");
        tfNombreEE.setText("");
        tfCreditos.setText("");
        tfBloque.setText("");
        tfPeriodo.setText("");
    }
    
    private void guardarCatalogoDeEE(int idLicenciatura, String programa, String nrc, String nombreDeLaEE, String creditos, String bloque, String periodo, String estatus){
        Connection conn = ConectarBD.abrirConexionMySQL();
        if(conn != null){
            try{
                int resultado;
                String consulta = "INSERT INTO catalogoDeEE (idLicenciatura, programa, nrc, nombreDeLaEE, creditos, bloque, periodo, estatus) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement ps = conn.prepareStatement(consulta);
                ps.setInt(1, idLicenciatura);
                ps.setString(2, programa);
                ps.setString(3, nrc);
                ps.setString(4, nombreDeLaEE);
                ps.setString(5, creditos);
                ps.setString(6, bloque);
                ps.setString(7, periodo);
                ps.setString(8, estatus);
                
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
                cargarRegistrosPorLicenciatura(idLicenciatura);
                    
                
            } catch (SQLException ex){
                mostrarAlerta = Herramientas.creadorDeAlerta("Error en la conexión a la base de datos", ex.getMessage(), Alert.AlertType.ERROR);
                mostrarAlerta.showAndWait();
            }
        }
    }
    
    private void limpiarTabla(){
        tbTabla.getItems().clear();
    }
    
    private void cargarRegistrosPorLicenciatura(int idLicenciatura){
        Connection conn = ConectarBD.abrirConexionMySQL();
        
        if(conn != null){
            try{
                String consulta = "SELECT * FROM catalogoDeEE WHERE idLicenciatura = ?";
                PreparedStatement ps = conn.prepareStatement(consulta);
                ps.setInt(1, idLicenciatura);
                ResultSet rs = ps.executeQuery();
                
                while(rs.next()){
                    CatalogoDeEE catalogoE = new CatalogoDeEE();
                    catalogoE.setIdCatalogoDeEE(rs.getInt("idCatalogoDeEE"));
                    catalogoE.setIdLicenciatura(rs.getInt("idLicenciatura"));
                    catalogoE.setPrograma(rs.getString("programa"));
                    catalogoE.setNrc(rs.getString("nrc"));
                    catalogoE.setNombreDeLaEE(rs.getString("nombreDeLaEE"));
                    catalogoE.setCreditos(rs.getString("creditos"));
                    catalogoE.setBloque(rs.getString("bloque"));
                    catalogoE.setPeriodo(rs.getString("periodo"));
                    catalogoE.setEstatus(rs.getString("estatus"));
                    
                    registrosDelCatalogo.add(catalogoE);
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

    @FXML
    private void clicEliminar(ActionEvent event) {
        int seleccion = tbTabla.getSelectionModel().getSelectedIndex();
        if (seleccion >= 0) {
            CatalogoDeEE registroAEliminar = registrosDelCatalogo.get(seleccion);
            eliminarRegistro(registroAEliminar.getNrc(), registroAEliminar.getPeriodo(), registroAEliminar.getIdLicenciatura());
        } else {
            Alert alertaVacio = Herramientas.creadorDeAlerta("Sin selección", "Para eliminar un registro, debe seleccionarlo de la tabla", Alert.AlertType.WARNING);
            alertaVacio.showAndWait();
        }
    }
    
    private void eliminarRegistro(String nrc, String periodo, int idLicenciatura){
        Connection conn = ConectarBD.abrirConexionMySQL();
        if(conn != null){
            try{
                String consulta = "DELETE FROM catalogoDeEE WHERE nrc = ? AND periodo = ?";
                PreparedStatement ps = conn.prepareStatement(consulta);
                ps.setString(1, nrc);
                ps.setString(2, periodo);
                int resultado = ps.executeUpdate();
                conn.close();
                
                limpiarTabla();
                cargarRegistrosPorLicenciatura(idLicenciatura);
                
                
            } catch(SQLException ex){
                mostrarAlerta = Herramientas.creadorDeAlerta("Error en la conexión a la base de datos", ex.getMessage(), Alert.AlertType.ERROR);
                mostrarAlerta.showAndWait();
            }
        }
    }

    @FXML
    private void clicFinalizar(ActionEvent event) {
        if(tbTabla.getItems().isEmpty()){
            mostrarAlerta = Herramientas.creadorDeAlerta("Error", "Para finalizar el registro, debe registrar al menos un elemento", Alert.AlertType.INFORMATION);
            mostrarAlerta.showAndWait();
        } else {
            mostrarAlerta = Herramientas.creadorDeAlerta("Mensaje de confirmación", "Catálogo de EE registrado exitosamente", Alert.AlertType.INFORMATION);
            mostrarAlerta.showAndWait();
            salir();
        }
    }

    private void salir(){
        try{
            Stage stage = (Stage) btCancelar.getScene().getWindow();
            Scene sceneVisualizarCatalogoDeEE = new Scene(FXMLLoader.load(getClass().getResource("FXMLVisualizarCatalogoDeEE.fxml")));
            stage.setScene(sceneVisualizarCatalogoDeEE);
            stage.show(); 
        } catch(IOException ex){
            mostrarAlerta = Herramientas.creadorDeAlerta("Error", "No se pudo cargar la ventana siguiente. Intente más tarde", Alert.AlertType.ERROR);
            mostrarAlerta.showAndWait();  
        }
    }
    
}
