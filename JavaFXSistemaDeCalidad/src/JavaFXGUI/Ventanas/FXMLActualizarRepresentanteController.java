/*
 * Autor: Brandon Trujillo
 * fecha: 01-09-2021
 */
package JavaFXGUI.Ventanas;

import conexionBD.ConectarBD;
import interfaz.NotificaCambios;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import pojos.RepresentanteDeCuerpoAcademico;
import util.Herramientas;

public class FXMLActualizarRepresentanteController implements Initializable {

    @FXML
    private RadioButton rbDoctorado;
    @FXML
    private ToggleGroup grupoGradosAcademicos;
    @FXML
    private RadioButton rbMaestria;
    @FXML
    private RadioButton rbEspecializacion;
    @FXML
    private RadioButton rbLicenciatura;
    @FXML
    private TextField tfContraseña;
    @FXML
    private TextField tfCorreo;
    @FXML
    private TextField tfTelefono;
    @FXML
    private TextField tfNumeroDePersonal;
    @FXML
    private TextField tfNombre;
    
    Alert mostrarAlerta;
    NotificaCambios notificacion;
    RepresentanteDeCuerpoAcademico editarRepresentante;
    boolean actualizacionExitosa = true;
    
    int idRepresentante = 0;
    
    String nombreAuxiliar;
    String telefonoAuxiliar;
    String contraseñaAuxiliar;
    String gradoAcademicoAuxiliar;    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    
    public void inicializaCampos(NotificaCambios notificacion, RepresentanteDeCuerpoAcademico editarRepresentante){
        this.notificacion = notificacion;
        this.editarRepresentante = editarRepresentante;
        
        obtenerRepresentanteSeleccionado();
    }

    private void obtenerRepresentanteSeleccionado(){
        idRepresentante = editarRepresentante.getIdentificacion();
        tfNombre.setText(editarRepresentante.getNombre());
        tfNumeroDePersonal.setText(editarRepresentante.getNumeroPersonal());
        tfNumeroDePersonal.setEditable(false);
        tfTelefono.setText(editarRepresentante.getTelefono());
        tfCorreo.setText(editarRepresentante.getCorreo());
        tfCorreo.setEditable(false);
        tfContraseña.setText(editarRepresentante.getContraseña());
        
        if(editarRepresentante.getGradoAcademico().equalsIgnoreCase("Licenciatura")){
            rbLicenciatura.setSelected(true);
        }else if(editarRepresentante.getGradoAcademico().equalsIgnoreCase("Especialización")){
            rbEspecializacion.setSelected(true);
        }else if(editarRepresentante.getGradoAcademico().equalsIgnoreCase("Maestría")){
            rbMaestria.setSelected(true);
        }else if(editarRepresentante.getGradoAcademico().equalsIgnoreCase("Doctorado")){
            rbDoctorado.setSelected(true);
        }
    }
    
    @FXML
    private void cancelar(ActionEvent event) {
        Herramientas.cerrarPantalla(tfContraseña);
    }

    @FXML
    private void clicActualizar(ActionEvent event) {
        tfNombre.setStyle("-fx-border-color: ;");
        tfNumeroDePersonal.setStyle("-fx-border-color: ;");
        tfTelefono.setStyle("-fx-border-color: ;");
        tfCorreo.setStyle("-fx-border-color: ;");
        tfContraseña.setStyle("-fx-border-color: ;");
        
        boolean esCorrecto = true;
        boolean esRepetido = false;
        
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
            if(idRepresentante> 0){
                actualizarAcademico(nombreAuxiliar, telefonoAuxiliar, gradoAcademicoAuxiliar, idRepresentante);
            }else{
                actualizacionExitosa= false;
            }
                
            if(actualizacionExitosa){
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
            mostrarAlerta = Herramientas.creadorDeAlerta("Campos incorrectos o vacíos", 
                "Verifique su información", Alert.AlertType.ERROR);
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
                    actualizacionExitosa = false;
                }else{
                    actualizarUsuario(contraseñaAuxiliar, idDocente);
                }
                conn.close();
            }catch(SQLException ex){
                System.out.println(ex.getMessage());
                actualizacionExitosa = false;
                mostrarAlerta = Herramientas.creadorDeAlerta("Error de consulta", "No fue posible acceder a la base de datos "
                    + "en este momento, intente más tarde", Alert.AlertType.ERROR);
                mostrarAlerta.showAndWait();
                Herramientas.cerrarPantalla(tfContraseña);
            }
        }else{
            actualizacionExitosa = false;
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
                    actualizacionExitosa = false;
                }
                conn.close();
        }else{
            actualizacionExitosa = false;
            mostrarAlerta = Herramientas.creadorDeAlerta("Error de conexión", "No fue posible conectar con la base de datos"
                + "en este momento, intente más tarde", Alert.AlertType.ERROR);
            mostrarAlerta.showAndWait();
            Herramientas.cerrarPantalla(tfContraseña);
        }
    }
    
}
