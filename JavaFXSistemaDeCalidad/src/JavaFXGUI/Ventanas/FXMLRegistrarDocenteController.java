/*
 *Autor: Brandon Trujillo
 *fechaCreación: 02/12/2020
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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import util.Herramientas;

public class FXMLRegistrarDocenteController implements Initializable {

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
    private ComboBox<?> cbCuerpoAcademico; 
    @FXML
    private ToggleGroup gradosAcademicos;
    @FXML
    private RadioButton rbLicenciatura;
    @FXML
    private RadioButton rbMaestria;
    @FXML
    private RadioButton rbEspecializacion;
    @FXML
    private RadioButton rbDoctorado;
    
    
    Alert mostrarAlerta;
    boolean registroExitoso = true;
    int idRol = 0;
    int idAcademico = 0;
    NotificaCambios notificacion;
    
    String nombreAuxiliar;
    String numeroPersonalAuxiliar;
    String telefonoAuxiliar;
    String correoAuxiliar;
    String contraseñaAuxiliar;
    String gradoAcademicoAuxiliar;
    int posicionCuerpoAcademico;
     
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }  
    
    public void inicializaCampos(NotificaCambios notificacion){
        this.notificacion = notificacion;
    }
    
    @FXML
    private void cancelar(ActionEvent event) {
        Herramientas.cerrarPantalla(tfNombre);
    }

    @FXML
    private void clicRegistrar(ActionEvent event) {
        tfNombre.setStyle("-fx-border-color: ;");
        tfNumeroDePersonal.setStyle("-fx-border-color: ;");
        tfTelefono.setStyle("-fx-border-color: ;");
        tfCorreo.setStyle("-fx-border-color: ;");
        tfContraseña.setStyle("-fx-border-color: ;");
        cbCuerpoAcademico.setStyle("-fx-border-color: ;");
        rbLicenciatura.setStyle("-fx-border-color: ;");
        rbEspecializacion.setStyle("-fx-border-color: ;");
        rbMaestria.setStyle("-fx-border-color: ;");
        rbDoctorado.setStyle("-fx-border-color: ;");
        
        boolean esCorrecto = true;
        nombreAuxiliar = tfNombre.getText();
        numeroPersonalAuxiliar = tfNumeroDePersonal.getText();
        telefonoAuxiliar = tfTelefono.getText();
        correoAuxiliar = tfCorreo.getText();
        contraseñaAuxiliar = tfContraseña.getText();
        posicionCuerpoAcademico = cbCuerpoAcademico.getSelectionModel().getSelectedIndex();
      
        if(nombreAuxiliar.isEmpty()){
            esCorrecto = false;
            tfNombre.setStyle("-fx-border-color: red;");
        }
        
        if(numeroPersonalAuxiliar.isEmpty()){
            esCorrecto = false;
            tfNumeroDePersonal.setStyle("-fx-border-color: red;");
        }
        
        if(telefonoAuxiliar.isEmpty()){
            esCorrecto = false;
            tfTelefono.setStyle("-fx-border-color: red;");
        }
        
        if(correoAuxiliar.isEmpty()){
            esCorrecto = false;
            tfCorreo.setStyle("-fx-border-color: red;");
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
        }else{
            rbLicenciatura.setStyle("-fx-border-color: red;");
            rbEspecializacion.setStyle("-fx-border-color: red;");
            rbMaestria.setStyle("-fx-border-color: red;");
            rbDoctorado.setStyle("-fx-border-color: red;");
        }
        
        if(posicionCuerpoAcademico < 0){
            esCorrecto = false;
            cbCuerpoAcademico.setStyle("-fx-border-color: red;");
        }
      
        
        if(esCorrecto){
            registrarAcademico(numeroPersonalAuxiliar, nombreAuxiliar, telefonoAuxiliar);
            if(idRol > 0 && idAcademico > 0){
                registrarUsuario(correoAuxiliar, contraseñaAuxiliar, idRol, idAcademico);
            }else{
                registroExitoso = false;
                mostrarAlerta = Herramientas.creadorDeAlerta("Error", "No fue posible completar el registro, "
                    + "intente más tarde", Alert.AlertType.ERROR);
                mostrarAlerta.showAndWait();
            }
        }else{
            mostrarAlerta = Herramientas.creadorDeAlerta("Campos incorrectos o vacíos", "Verifique su información", Alert.AlertType.ERROR);
            mostrarAlerta.showAndWait();
        }
    }
    
    private void registrarAcademico(String numeroDePersonal, String nombre, String telefono){
        Connection conn = ConectarBD.abrirConexionMySQL();
        if(conn != null){
            try{
                String consulta = "INSERT INTO academico(numeroPersonal, nombre, telefono) VALUES (?, ?, ?)";
                PreparedStatement declaracion = conn.prepareStatement(consulta);
                declaracion.setString(1, numeroDePersonal);
                declaracion.setString(2, nombre);
                declaracion.setString(3, telefono);
                int resultado = declaracion.executeUpdate();
                if(resultado == 0){
                    registroExitoso = false;
                }else{
                    registrarRolAcademico(numeroPersonalAuxiliar);
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
            mostrarAlerta = Herramientas.creadorDeAlerta("Error de conexión", "No fue posible conectar con la base de datos"
                + "en este momento, intente más tarde", Alert.AlertType.ERROR);
            mostrarAlerta.showAndWait();
            Herramientas.cerrarPantalla(tfContraseña);
        }
    }
    
    private void registrarRolAcademico(String numeroPersonal) throws SQLException{
        Connection conn = ConectarBD.abrirConexionMySQL();
        if(conn != null){
                String consulta = "INSERT INTO rol (tipoRol, numeroPersonal) VALUES ('Docente', ?)";
                PreparedStatement declaracion = conn.prepareStatement(consulta);
                declaracion.setString(1, numeroPersonal);
                int resultado = declaracion.executeUpdate();
                if(resultado == 0){
                    registroExitoso = false;
                }else{
                    obtenerIdAcademico(numeroPersonalAuxiliar);
                    obtenerIdRolAcademico(numeroPersonalAuxiliar);
                }
                conn.close();
        }else{
            mostrarAlerta = Herramientas.creadorDeAlerta("Error de conexión", "No fue posible conectar con la base de datos"
                + "en este momento, intente más tarde", Alert.AlertType.ERROR);
            mostrarAlerta.showAndWait();
            Herramientas.cerrarPantalla(tfContraseña);
        }
    }
        
    private int obtenerIdAcademico(String numeroDePersonal) throws SQLException{
        Connection conn = ConectarBD.abrirConexionMySQL();
        if(conn != null){
                String consulta = "SELECT idAcademico FROM academico WHERE numeroPersonal = ?";
                PreparedStatement declaracion = conn.prepareStatement(consulta);
                declaracion.setString(1, numeroDePersonal);
                ResultSet resultado = declaracion.executeQuery();
                if(resultado.next()){
                    idAcademico = resultado.getInt("idAcademico");
                }
                conn.close();
        }else{
            mostrarAlerta = Herramientas.creadorDeAlerta("Error de conexión", "No fue posible conectar con la base de datos"
                + "en este momento, intente más tarde", Alert.AlertType.ERROR);
            mostrarAlerta.showAndWait();
            Herramientas.cerrarPantalla(tfContraseña);
        }
        return idAcademico;
    }
    
    private int obtenerIdRolAcademico(String numeroPersonal) throws SQLException{
        Connection conn = ConectarBD.abrirConexionMySQL();
        if(conn != null){
                String consulta = "SELECT idRol FROM rol WHERE numeroPersonal = ?";
                PreparedStatement declaracion = conn.prepareStatement(consulta);
                declaracion.setString(1, numeroPersonal);
                ResultSet resultado = declaracion.executeQuery();
                if(resultado.next()){
                    idRol = resultado.getInt("idRol");
                }
                conn.close();
        }else{
            mostrarAlerta = Herramientas.creadorDeAlerta("Error de conexión", "No fue posible conectar con la base de datos"
                + "en este momento, intente más tarde", Alert.AlertType.ERROR);
            mostrarAlerta.showAndWait();
            Herramientas.cerrarPantalla(tfContraseña);
        }
        return idRol;
    }
    
    private void registrarUsuario(String correo, String contraseña, int idRol, int idAcademico){
        Connection conn = ConectarBD.abrirConexionMySQL();
        if(conn != null){
            try{
                String consulta = "INSERT INTO usuario(correo, password, idRol, idAcademico) VALUES (?, ?, ?, ?)";
                PreparedStatement declaracion = conn.prepareStatement(consulta);
                declaracion.setString(1, correo);
                declaracion.setString(2, contraseña);
                declaracion.setInt(3, idRol);
                declaracion.setInt(4, idAcademico);
                int resultado = declaracion.executeUpdate();
                if(resultado == 0){
                    registroExitoso = false;
                }else{
                    if(registroExitoso){
                        mostrarAlerta = Herramientas.creadorDeAlerta("Mensaje", "Registro exitoso", Alert.AlertType.INFORMATION);
                        mostrarAlerta.showAndWait();
                        Herramientas.cerrarPantalla(tfNombre);
                        notificacion.refrescarTabla(true);
                    }else{
                        mostrarAlerta = Herramientas.creadorDeAlerta("Error", "No fue posible completar el registro, "
                            + "intente más tarde", Alert.AlertType.ERROR);
                        mostrarAlerta.showAndWait();
                    }
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
            mostrarAlerta = Herramientas.creadorDeAlerta("Error de conexión", "No fue posible conectar con la base de datos"
                + "en este momento, intente más tarde", Alert.AlertType.ERROR);
            mostrarAlerta.showAndWait();
            Herramientas.cerrarPantalla(tfContraseña);
        }
    }
    
}
