/*
Autor: Mario Dorantes
FechaDeCreación: 02/12/2020
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
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import pojos.CatalogoDeEE;
import pojos.Licenciatura;
import util.Herramientas;


public class FXMLActualizarCatalogoDeEEController implements Initializable {

    @FXML
    private Button btCancelar;
    @FXML
    private TextField tfNombreEE;
    @FXML
    private TextField tfPeriodo;
    @FXML
    private TextField tfBloque;
    @FXML
    private TextField tfCreditos;
    @FXML
    private TextField tfNrc;
    @FXML
    private TextField tfPrograma;
    @FXML
    private TableView<CatalogoDeEE> tbTabla;
    @FXML
    private TableColumn colPrograma;
    @FXML
    private TableColumn  colNrc;
    @FXML
    private TableColumn colNombreDeLaEE;
    @FXML
    private TableColumn colCreditos;
    @FXML
    private TableColumn colBloque;
    @FXML
    private TableColumn colPeriodo;
    @FXML
    private RadioButton rbActivo;
    @FXML
    private ToggleGroup tgEstatus;
    @FXML
    private RadioButton rbInactivo;
    @FXML
    private ComboBox<Licenciatura> cbLicenciaturas;
    
    Alert mostrarAlerta;
    
    private ObservableList<CatalogoDeEE> registrosDelCatalogo;
    private ObservableList<Licenciatura> licenciaturas;
    
    int idLicenciaturaCatalogoAux;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        registrosDelCatalogo = FXCollections.observableArrayList();
        licenciaturas = FXCollections.observableArrayList();
        cargarNombresLicenciaturas();
        
        this.colPrograma.setCellValueFactory(new PropertyValueFactory("programa"));
        this.colNrc.setCellValueFactory(new PropertyValueFactory("nrc"));
        this.colNombreDeLaEE.setCellValueFactory(new PropertyValueFactory("nombreDeLaEE"));
        this.colCreditos.setCellValueFactory(new PropertyValueFactory("creditos"));
        this.colBloque.setCellValueFactory(new PropertyValueFactory("bloque"));
        this.colPeriodo.setCellValueFactory(new PropertyValueFactory("periodo"));
        
        cbLicenciaturas.valueProperty().addListener(new ChangeListener <Licenciatura>(){
            @Override
            public void changed(ObservableValue<? extends Licenciatura> observable, Licenciatura oldValue, Licenciatura newValue) {
                if(newValue != null){
                    limpiarCampos();
                    limpiarTabla();
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
    
    private void limpiarCampos(){
        tfPrograma.setText("");
        tfNrc.setText("");
        tfNombreEE.setText("");
        tfCreditos.setText("");
        tfBloque.setText("");
        tfPeriodo.setText("");
    }
    
    private void limpiarTabla(){
        tbTabla.getItems().clear();
    }
    
    private void extraerDatosDelCatalogo(int idLicenciatura){
        limpiarTabla();
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
                    
                    llenarRadioButton(catalogoE.getEstatus());
                    idLicenciaturaCatalogoAux = catalogoE.getIdLicenciatura();
                    
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
    
    private void llenarRadioButton(String estatus){
        rbActivo.setSelected(false);
        rbInactivo.setSelected(false);
        
        if("Activo".equals(estatus)){
            rbActivo.setSelected(true);
        }
        if("Inactivo".equals(estatus))
            rbInactivo.setSelected(true);
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

    @FXML
    private void clicActualizar(ActionEvent event) {
         boolean esValido = true;
        
        int posicionTabla = tbTabla.getSelectionModel().getSelectedIndex();
        String programaAux = tfPrograma.getText();
        String nrcAux = tfNrc.getText();
        String nombreDeLaEEAux = tfNombreEE.getText();
        String creditosAux = tfCreditos.getText();
        String bloqueAux = tfBloque.getText();
        String periodoAux = tfPeriodo.getText();
        
        if(posicionTabla < 0){
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
            CatalogoDeEE registroAActualizar = registrosDelCatalogo.get(posicionTabla);
            actualizarCatalogoDeEE(programaAux, nrcAux, nombreDeLaEEAux, creditosAux, bloqueAux, periodoAux, registroAActualizar.getNrc(), registroAActualizar.getNombreDeLaEE(), registroAActualizar.getPeriodo());
        } else {
            mostrarAlerta = Herramientas.creadorDeAlerta("Alerta", "Seleccione un registro de la tabla y de clic en el botón 'Editar'. \n \nSi desea agregar un "
                    + "nuevo registro al catalogo, dirijase a la sección 'Registrar Catálogo' ", Alert.AlertType.ERROR);
            mostrarAlerta.showAndWait();
        }
    }

    private void actualizarCatalogoDeEE(String programa, String nrc, String nombreDeLaEE, String creditos, String bloque, String periodo, String nrcParametro, String nombreEEParametro, String periodoParametro){
        Connection conn = ConectarBD.abrirConexionMySQL();
        if(conn != null){
            try{
                int resultado;
                String consulta = "UPDATE catalogoDeEE SET programa = ?, nrc = ?, nombreDeLaEE = ?, creditos = ?, bloque = ?, periodo = ? WHERE nrc = ? AND nombreDeLaEE = ? AND periodo = ?";
                PreparedStatement ps = conn.prepareStatement(consulta);
                ps.setString(1, programa);
                ps.setString(2, nrc);
                ps.setString(3, nombreDeLaEE);
                ps.setString(4, creditos);
                ps.setString(5, bloque);
                ps.setString(6, periodo);
                ps.setString(7, nrcParametro);
                ps.setString(8, nombreEEParametro);
                ps.setString(9, periodoParametro);
                resultado = ps.executeUpdate();
                
                conn.close();
                
                if(resultado > 0){
                    mostrarAlerta = Herramientas.creadorDeAlerta("Mensaje de confirmación", "Registro actualizado", Alert.AlertType.INFORMATION);
                    mostrarAlerta.showAndWait();
                } else {
                    mostrarAlerta = Herramientas.creadorDeAlerta("Mensaje de error", "Error al actualizar registro", Alert.AlertType.ERROR);
                    mostrarAlerta.showAndWait();
                }                               
                
                limpiarCampos();
                extraerDatosDelCatalogo(idLicenciaturaCatalogoAux);
                
            }catch(SQLException ex){
                mostrarAlerta = Herramientas.creadorDeAlerta("Error en la conexión a la base de datos", ex.getMessage(), Alert.AlertType.ERROR);
                mostrarAlerta.showAndWait();
            }
        }
    }

    @FXML
    private void clicEliminar(ActionEvent event) {
        int seleccion = tbTabla.getSelectionModel().getSelectedIndex();
        if (seleccion >= 0) {
            CatalogoDeEE registroAEliminar = registrosDelCatalogo.get(seleccion);
            eliminarRegistro(registroAEliminar.getNrc(), registroAEliminar.getNombreDeLaEE(), registroAEliminar.getPeriodo());
        } else {
            Alert alertaVacio = Herramientas.creadorDeAlerta("Sin selección", "Para eliminar un registro, debe seleccionarlo de la tabla", Alert.AlertType.WARNING);
            alertaVacio.showAndWait();
        }
    }
    
    private void eliminarRegistro(String nrc, String nombreDeLaEE, String periodo){
        Connection conn = ConectarBD.abrirConexionMySQL();
        if(conn != null){
            try{
                String consulta = "DELETE FROM catalogoDeEE WHERE nrc = ? AND nombreDeLaEE = ? AND periodo = ?";
                PreparedStatement ps = conn.prepareStatement(consulta);
                ps.setString(1, nrc);
                ps.setString(2, nombreDeLaEE);
                ps.setString(3, periodo);
                int resultado = ps.executeUpdate();
                conn.close();
                
                extraerDatosDelCatalogo(idLicenciaturaCatalogoAux);
                                
            } catch(SQLException ex){
                mostrarAlerta = Herramientas.creadorDeAlerta("Error en la conexión a la base de datos", ex.getMessage(), Alert.AlertType.ERROR);
                mostrarAlerta.showAndWait();
            }
        }
    }

    @FXML
    private void clicFinalizar(ActionEvent event) {
        if(tbTabla.getItems().isEmpty()){
            mostrarAlerta = Herramientas.creadorDeAlerta("Error", "Para finalizar la actualizacion, debe modificar al menos un elemento del catalogo seleccionado", Alert.AlertType.INFORMATION);
            mostrarAlerta.showAndWait();
        } else {
            if(rbInactivo.isSelected()){
                String estatus = "Inactivo";
                cambiarEstatusDelCatalogo(estatus);
            }
            if(rbActivo.isSelected()){
                String estatus = "Activo";
                cambiarEstatusDelCatalogo(estatus);
            }
            mostrarAlerta = Herramientas.creadorDeAlerta("Mensaje de confirmación", "Catálogo de EE actualizado exitosamente", Alert.AlertType.INFORMATION);
            mostrarAlerta.showAndWait();
            salir();
        }
    }
    
    private void cambiarEstatusDelCatalogo(String estatus){
        Connection conn = ConectarBD.abrirConexionMySQL();
        if(conn != null){
            try{
                int resultado;                
                String consulta = "UPDATE catalogoDeEE SET estatus = ? WHERE idLicenciatura = ?";
                PreparedStatement ps = conn.prepareStatement(consulta);
                ps.setString(1, estatus);
                ps.setInt(2, idLicenciaturaCatalogoAux);
                resultado = ps.executeUpdate();
                
                conn.close();
                
                if(resultado > 0){
                    mostrarAlerta = Herramientas.creadorDeAlerta("Mensaje de confirmación", "Estatus del catalogo: " + estatus, Alert.AlertType.INFORMATION);
                    mostrarAlerta.showAndWait();
                } else {
                    mostrarAlerta = Herramientas.creadorDeAlerta("Mensaje de error", "Error al actualizar el estatus del catalogo", Alert.AlertType.ERROR);
                    mostrarAlerta.showAndWait();
                }                               
                              
            }catch(SQLException ex){
                mostrarAlerta = Herramientas.creadorDeAlerta("Error en la conexión a la base de datos. Intente más tarde", ex.getMessage(), Alert.AlertType.ERROR);
                mostrarAlerta.showAndWait();
            }
        }
    }

    @FXML
    private void clicEditar(ActionEvent event) {
        limpiarCampos();
        int seleccion = tbTabla.getSelectionModel().getSelectedIndex();     
        
        if (seleccion >= 0) {
            CatalogoDeEE catalogoSeleccionado = registrosDelCatalogo.get(seleccion);
            tfPrograma.setText(catalogoSeleccionado.getPrograma());
            tfNrc.setText(catalogoSeleccionado.getNrc());
            tfNombreEE.setText(catalogoSeleccionado.getNombreDeLaEE());
            tfCreditos.setText(catalogoSeleccionado.getCreditos());
            tfBloque.setText(catalogoSeleccionado.getBloque());
            tfPeriodo.setText(catalogoSeleccionado.getPeriodo());
            
        } else {
            mostrarAlerta = Herramientas.creadorDeAlerta("Sin selección", "Seleccione un registro para poder editarlo", Alert.AlertType.ERROR);
            mostrarAlerta.showAndWait();  
        }
    }
}
