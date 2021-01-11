/*
 *Autor: Brandon Trujillo
 *fechaCreación: 28/11/2020
 */
package JavaFXGUI.Ventanas;

import conexionBD.ConectarBD;
import interfaz.NotificaCambios;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import pojos.CatalogoDeCuerpoAcademico;
import pojos.Docente;
import util.Herramientas;

public class FXMLActualizaDocenteController implements Initializable {
    
    @FXML
    private TextField tfNombre;
    @FXML
    private TextField tfNumeroDePersonal;
    @FXML
    private TextField tfTelefono;
    @FXML
    private TextField tfCorreo;
    @FXML
    private TextField tfContraseña;
    @FXML
    private RadioButton rbLicenciatura;
    @FXML
    private ToggleGroup grupoGradosAcademicos;
    @FXML
    private RadioButton rbEspecializacion;
    @FXML
    private RadioButton rbMaestria;
    @FXML
    private RadioButton rbDoctorado;
    @FXML
    private ComboBox<CatalogoDeCuerpoAcademico> cbCuerpoAcademico;
    
    Alert mostrarAlerta;
    NotificaCambios notificacion;
    private Docente editarDocente;
    
    int idDocente = 0;
    int idCuerpoAcademico = 0;
    boolean registroExitoso = true;
    
    private ObservableList<CatalogoDeCuerpoAcademico> cuerposAcademicos;
    
    String nombreAuxiliar;
    String telefonoAuxiliar;
    String contraseñaAuxiliar;
    String gradoAcademicoAuxiliar;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cuerposAcademicos = FXCollections.observableArrayList();
        cargarCuerposAcademicos();
        
        cbCuerpoAcademico.valueProperty().addListener(new ChangeListener<CatalogoDeCuerpoAcademico>(){
            @Override
            public void changed(ObservableValue<? extends CatalogoDeCuerpoAcademico> observable, CatalogoDeCuerpoAcademico oldValue, CatalogoDeCuerpoAcademico newValue) {
                if(newValue != null){
                   idCuerpoAcademico = newValue.getIdentificacion();
                }
            } 
        });
    } 
  
    private void cargarCuerposAcademicos(){
        Connection conn = ConectarBD.abrirConexionMySQL();
        if(conn != null){
            try {
                String consulta = "Select * from cuerpoAcademico";
                PreparedStatement declaracion = conn.prepareStatement(consulta);
                ResultSet resultado = declaracion.executeQuery();
                while(resultado.next()){
                    CatalogoDeCuerpoAcademico cuerposRegistrados = new CatalogoDeCuerpoAcademico();
                    cuerposRegistrados.setIdentificacion(resultado.getInt("idCuerpoAcademico"));
                    cuerposRegistrados.setNombre(resultado.getString("nombre"));
                    cuerposAcademicos.add(cuerposRegistrados);
                }
                cbCuerpoAcademico.setItems(cuerposAcademicos);
                conn.close();
            } catch (SQLException ex) {
                registroExitoso = false;
                mostrarAlerta = Herramientas.creadorDeAlerta("Error de consulta", "No fue posible acceder a la base de datos "
                    + "en este momento, intente más tarde", Alert.AlertType.ERROR);
                mostrarAlerta.showAndWait();
                Herramientas.cerrarPantalla(tfContraseña);
            } 
        }else{
            registroExitoso = false;
            mostrarAlerta = Herramientas.creadorDeAlerta("Error de conexión", "No fue posible conectar con la base de datos"
                + "en este momento, intente más tarde", Alert.AlertType.ERROR);
            mostrarAlerta.showAndWait();
            Herramientas.cerrarPantalla(tfContraseña);
        }
    }
    
    @FXML
    private void cancelar(ActionEvent e){
        Herramientas.cerrarPantalla(tfNombre);
    }
    
    public void inicializaCampos(NotificaCambios notificacion, Docente editarDocente){
        this.notificacion = notificacion;
        this.editarDocente = editarDocente;
        
        obtenerDocenteSeleccionado();
    }
    
    private void obtenerDocenteSeleccionado(){
        idDocente = editarDocente.getIdentificacion();
        tfNombre.setText(editarDocente.getNombre());
        tfNumeroDePersonal.setText(editarDocente.getNumeroPersonal());
        tfNumeroDePersonal.setEditable(false);
        tfTelefono.setText(editarDocente.getTelefono());
        tfCorreo.setText(editarDocente.getCorreo());
        tfCorreo.setEditable(false);
        tfContraseña.setText(editarDocente.getContraseña());
        
        obtenerIdCuerpoAcademicoVinculadoAlDocente(idDocente);
        int posicionCuerpoAcademico = obtenerPosicionDeCuerpoAcademicoSeleccionado(idCuerpoAcademico);
        cbCuerpoAcademico.getSelectionModel().select(posicionCuerpoAcademico);
        
        if(editarDocente.getGradoAcademico().equalsIgnoreCase("Licenciatura")){
            rbLicenciatura.setSelected(true);
        }else if(editarDocente.getGradoAcademico().equalsIgnoreCase("Especialización")){
            rbEspecializacion.setSelected(true);
        }else if(editarDocente.getGradoAcademico().equalsIgnoreCase("Maestría")){
            rbMaestria.setSelected(true);
        }else if(editarDocente.getGradoAcademico().equalsIgnoreCase("Doctorado")){
            rbDoctorado.setSelected(true);
        }
    }
    
    private void obtenerIdCuerpoAcademicoVinculadoAlDocente(int idDocenteSeleccionado){
        Connection conn = ConectarBD.abrirConexionMySQL();
        if(conn != null){
            try {
                String consulta = "Select idCuerpoAcademico from cuerpoAcademicoIntegrantes where idAcademico = ?";
                PreparedStatement declaracion = conn.prepareStatement(consulta);
                declaracion.setInt(1, idDocenteSeleccionado);
                ResultSet resultado = declaracion.executeQuery();
                if(resultado.next()){
                    CatalogoDeCuerpoAcademico cuerposVinculadoAlDocente = new CatalogoDeCuerpoAcademico();
                    cuerposVinculadoAlDocente.setIdentificacion(resultado.getInt("idCuerpoAcademico"));
                    idCuerpoAcademico = cuerposVinculadoAlDocente.getIdentificacion();
                }
                conn.close();
            } catch (SQLException ex) {
                registroExitoso = false;
                mostrarAlerta = Herramientas.creadorDeAlerta("Error de consulta", "No fue posible acceder a la base de datos "
                    + "en este momento, intente más tarde", Alert.AlertType.ERROR);
                mostrarAlerta.showAndWait();
                Herramientas.cerrarPantalla(tfContraseña);
            } 
        }else{
            registroExitoso = false;
            mostrarAlerta = Herramientas.creadorDeAlerta("Error de conexión", "No fue posible conectar con la base de datos"
                + "en este momento, intente más tarde", Alert.AlertType.ERROR);
            mostrarAlerta.showAndWait();
            Herramientas.cerrarPantalla(tfContraseña);
        }
    }

    @FXML
    private void clicActualizar(ActionEvent event) {
        tfNombre.setStyle("-fx-border-color: ;");
        tfNumeroDePersonal.setStyle("-fx-border-color: ;");
        tfTelefono.setStyle("-fx-border-color: ;");
        tfCorreo.setStyle("-fx-border-color: ;");
        tfContraseña.setStyle("-fx-border-color: ;");
        
        boolean esCorrecto = true;
        
        nombreAuxiliar = tfNombre.getText();
        telefonoAuxiliar = tfTelefono.getText();
        contraseñaAuxiliar = tfContraseña.getText();
        
        if(nombreAuxiliar.isEmpty()){
            esCorrecto = false;
            tfNombre.setStyle("-fx-border-color: red;");
        }
        if(telefonoAuxiliar.isEmpty()){
            esCorrecto = false;
            tfTelefono.setStyle("-fx-border-color: red;");
        }
        if(contraseñaAuxiliar.isEmpty()){
            esCorrecto = false;
            tfContraseña.setStyle("-fx-border-color: red;");
        }
        if(rbLicenciatura.isSelected()){
            gradoAcademicoAuxiliar = rbLicenciatura.getText();
        }else if(rbEspecializacion.isSelected()){
            gradoAcademicoAuxiliar = rbEspecializacion.getText();
        }else if(rbMaestria.isSelected()){
            gradoAcademicoAuxiliar = rbMaestria.getText();
        }else if(rbDoctorado.isSelected()){
            gradoAcademicoAuxiliar = rbDoctorado.getText();
        }
        
        if(esCorrecto){
            if(idDocente > 0){
                actualizarAcademico(nombreAuxiliar, telefonoAuxiliar, gradoAcademicoAuxiliar, idDocente);
            }else{
                registroExitoso = false;
            }
                
            if(registroExitoso){
                mostrarAlerta = Herramientas.creadorDeAlerta("Mensaje", "Actualizacion exitosa", Alert.AlertType.INFORMATION);
                mostrarAlerta.showAndWait();
                Herramientas.cerrarPantalla(tfNombre);
                notificacion.refrescarTabla(true);
            }else{
                mostrarAlerta = Herramientas.creadorDeAlerta("Error", "No fue posible completar el registro, "
                    + "intente más tarde", Alert.AlertType.ERROR);
                mostrarAlerta.showAndWait();
            } 
                
        }else{
            mostrarAlerta = Herramientas.creadorDeAlerta("Error", "correo previamente registrado", Alert.AlertType.INFORMATION);
            mostrarAlerta.showAndWait();
        }
    }
    
    private void actualizarAcademico(String nombre, String telefono, String gradoAcademico, int idDocente){
        Connection conn = ConectarBD.abrirConexionMySQL();
        if(conn != null){
            try{
                String consulta = "UPDATE academico set nombre = ?,  telefono = ?, "
                    + "gradoAcademico = ? WHERE idAcademico = ?";
                PreparedStatement declaracion = conn.prepareStatement(consulta);
                declaracion.setString(1, nombre);
                declaracion.setString(2, telefono);
                declaracion.setString(3, gradoAcademico);
                declaracion.setInt(4, idDocente);
                int resultado = declaracion.executeUpdate();
                if(resultado == 0){
                    registroExitoso = false;
                }else{
                    actualizarVinculacionDelCatalogo(idCuerpoAcademico, idDocente);
                }
                conn.close();
            }catch(SQLException ex){
                registroExitoso = false;
                mostrarAlerta = Herramientas.creadorDeAlerta("Error de consulta", "No fue posible acceder a la base de datos "
                    + "en este momento, intente más tarde", Alert.AlertType.ERROR);
                mostrarAlerta.showAndWait();
                Herramientas.cerrarPantalla(tfContraseña);
            }
        }else{
            registroExitoso = false;
            mostrarAlerta = Herramientas.creadorDeAlerta("Error de conexión", "No fue posible conectar con la base de datos"
                + "en este momento, intente más tarde", Alert.AlertType.ERROR);
            mostrarAlerta.showAndWait();
            Herramientas.cerrarPantalla(tfContraseña);
        }
    }
    
    private void actualizarVinculacionDelCatalogo(int idNuevoCuerpoAcademico, int idDocente) throws SQLException{
        Connection conn = ConectarBD.abrirConexionMySQL();
        if(conn != null){
                String consulta = "UPDATE cuerpoAcademicoIntegrantes set idCuerpoAcademico = ?  WHERE idAcademico = ?";
                PreparedStatement declaracion = conn.prepareStatement(consulta);
                declaracion.setInt(1, idNuevoCuerpoAcademico);
                declaracion.setInt(2, idDocente);
                int resultado = declaracion.executeUpdate();
                if(resultado == 0){
                    registroExitoso = false;
                }else{       
                   actualizarUsuario(contraseñaAuxiliar, idDocente);
                }
                conn.close();
        }else{
            registroExitoso = false;
            mostrarAlerta = Herramientas.creadorDeAlerta("Error de conexión", "No fue posible conectar con la base de datos"
                + "en este momento, intente más tarde", Alert.AlertType.ERROR);
            mostrarAlerta.showAndWait();
            Herramientas.cerrarPantalla(tfContraseña);
        }
    }
    
    private void actualizarUsuario(String contraseña, int idDocente) throws SQLException{
        Connection conn = ConectarBD.abrirConexionMySQL();
        if(conn != null){
                String consulta = "UPDATE usuario set password = ? WHERE idAcademico = ?";
                PreparedStatement declaracion = conn.prepareStatement(consulta);
                declaracion.setString(1, contraseña);
                declaracion.setInt(2, idDocente);
                int resultado = declaracion.executeUpdate();
                if(resultado == 0){
                    registroExitoso = false;
                }
                conn.close();
        }else{
            registroExitoso = false;
            mostrarAlerta = Herramientas.creadorDeAlerta("Error de conexión", "No fue posible conectar con la base de datos"
                + "en este momento, intente más tarde", Alert.AlertType.ERROR);
            mostrarAlerta.showAndWait();
            Herramientas.cerrarPantalla(tfContraseña);
        }
    }
    
    private int obtenerPosicionDeCuerpoAcademicoSeleccionado(int idCuerpoAcademico){
        int value = 0;
        if(cuerposAcademicos.size() > 0){
            for (int i = 0; i < cuerposAcademicos.size(); i++) {
                CatalogoDeCuerpoAcademico get = cuerposAcademicos.get(i);
                if(get.getIdentificacion() == idCuerpoAcademico){
                    return i;
                }
            }
        }
        return value;
    }
}
