/*
 * Autor: Brandon Trujillo
 * fecha: 1/12/2020
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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import pojos.CoordinadorDeAcademia;
import util.Herramientas;
import validaciones.Validaciones;

public class FXMLRegistrarCoordinadorController implements Initializable {

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
    @FXML
    private RadioButton rbLicenciatura;
    @FXML
    private ToggleGroup gradosAcademicos;
    @FXML
    private RadioButton rbEspecializacion;
    @FXML
    private RadioButton rbMaestria;
    @FXML
    private RadioButton rbDoctorado;
    
    Alert mostrarAlerta;
    int idRol = 0;
    int idAcademico = 0;
    boolean registroExitoso = true;
    NotificaCambios notificacion;
    
    private ObservableList<CoordinadorDeAcademia> validacionDeCoordinador;
    
    String nombreAuxiliar;
    String numeroPersonalAuxiliar;
    String telefonoAuxiliar;
    String correoAuxiliar;
    String contraseñaAuxiliar;
    String gradoAcademicoAuxiliar;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
       validacionDeCoordinador = FXCollections.observableArrayList();
    }    
    
    public void inicializaCampos(NotificaCambios notificacion){
        this.notificacion = notificacion;
    }
    
    private void obtenerNumeroPersonalYCorreo(){
        Connection conn = ConectarBD.abrirConexionMySQL();
        if(conn != null){
            try {
                String consulta = "select numeroPersonal, "
                    + "correo from academico inner join usuario on academico.idAcademico = usuario.idAcademico;";
                PreparedStatement declaracion = conn.prepareStatement(consulta);
                ResultSet resultado = declaracion.executeQuery();
                while(resultado.next()){
                    CoordinadorDeAcademia coordinadoresRegistrados = new CoordinadorDeAcademia();
                    coordinadoresRegistrados.setNumeroPersonal(resultado.getString("numeroPersonal"));
                    coordinadoresRegistrados.setCorreo(resultado.getString("Correo"));
                    validacionDeCoordinador.add(coordinadoresRegistrados);
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
    private void cancelar(ActionEvent event) {
        Herramientas.cerrarPantalla(tfContraseña);
    }

    @FXML
    private void clicRegistrar(ActionEvent event) {
        obtenerNumeroPersonalYCorreo();
        tfNombre.setStyle("-fx-border-color: ;");
        tfNumeroDePersonal.setStyle("-fx-border-color: ;");
        tfTelefono.setStyle("-fx-border-color: ;");
        tfCorreo.setStyle("-fx-border-color: ;");
        tfContraseña.setStyle("-fx-border-color: ;");
        rbLicenciatura.setStyle("-fx-border-color: ;");
        rbEspecializacion.setStyle("-fx-border-color: ;");
        rbMaestria.setStyle("-fx-border-color: ;");
        rbDoctorado.setStyle("-fx-border-color: ;");
        
        boolean esCorrecto = true;
        boolean esRepetido = false;
        int iterador = 0;
        int tamañoDeValidacionDocente = validacionDeCoordinador.size();
        Validaciones datoAValidar = new Validaciones();
        
        nombreAuxiliar = tfNombre.getText();
        numeroPersonalAuxiliar = tfNumeroDePersonal.getText();
        telefonoAuxiliar = tfTelefono.getText();
        correoAuxiliar = tfCorreo.getText();
        contraseñaAuxiliar = tfContraseña.getText();
        
        if((nombreAuxiliar.isEmpty()) || (!datoAValidar.validarNombre(nombreAuxiliar))){
            esCorrecto = false;
            tfNombre.setStyle("-fx-border-color: red;");
        }
        if((numeroPersonalAuxiliar.isEmpty()) || (!datoAValidar.validarNumeroDePersonal(numeroPersonalAuxiliar))){
            esCorrecto = false;
            tfNumeroDePersonal.setStyle("-fx-border-color: red;");
        }
        if((telefonoAuxiliar.isEmpty()) || (!datoAValidar.validarTelefono(telefonoAuxiliar))){
            esCorrecto = false;
            tfTelefono.setStyle("-fx-border-color: red;");
        }
        if((correoAuxiliar.isEmpty()) || (!datoAValidar.validarCorreo(correoAuxiliar))){
            esCorrecto = false;
            tfCorreo.setStyle("-fx-border-color: red;");
        }
        if((contraseñaAuxiliar.isEmpty()) || (!datoAValidar.validarContraseña(contraseñaAuxiliar))){
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
            esCorrecto = false;
            rbLicenciatura.setStyle("-fx-border-color: red;");
            rbEspecializacion.setStyle("-fx-border-color: red;");
            rbMaestria.setStyle("-fx-border-color: red;");
            rbDoctorado.setStyle("-fx-border-color: red;");
        }
        
        while(iterador < tamañoDeValidacionDocente){
            if (validacionDeCoordinador.get(iterador).getNumeroPersonal().equals(numeroPersonalAuxiliar)){
                esRepetido = true;
                tfNumeroDePersonal.setStyle("-fx-border-color: red;");
            }
            if(validacionDeCoordinador.get(iterador).getCorreo().equals(correoAuxiliar)){
                esRepetido = true;
                tfCorreo.setStyle("-fx-border-color: red;");
            }
            iterador ++;
        }
        
      if(esCorrecto){
            if(!esRepetido){
                registrarAcademico(numeroPersonalAuxiliar, nombreAuxiliar, telefonoAuxiliar, gradoAcademicoAuxiliar);
                if(idRol > 0 && idAcademico > 0){
                    registrarUsuario(correoAuxiliar, contraseñaAuxiliar, idRol, idAcademico);
                }else{
                    registroExitoso = false;
                }
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
            }else{
                mostrarAlerta = Herramientas.creadorDeAlerta("Campos repetidos", 
                    "El campo o los campos marcados ya fueron registrados previamente", Alert.AlertType.WARNING);
                mostrarAlerta.showAndWait();
            }    
        }else{
            mostrarAlerta = Herramientas.creadorDeAlerta("Campos incorrectos o vacíos", 
                "Verifique su información", Alert.AlertType.WARNING);
            mostrarAlerta.showAndWait();
        }
    }
    
    private void registrarAcademico(String numeroDePersonal, String nombre, String telefono, String gradoAcademico){
        Connection conn = ConectarBD.abrirConexionMySQL();
        if(conn != null){
            try{
                String consulta = "INSERT INTO academico(numeroPersonal, nombre, telefono, gradoAcademico) VALUES (?, ?, ?, ?)";
                PreparedStatement declaracion = conn.prepareStatement(consulta);
                declaracion.setString(1, numeroDePersonal);
                declaracion.setString(2, nombre);
                declaracion.setString(3, telefono);
                declaracion.setString(4, gradoAcademico);
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
            registroExitoso = false;
            mostrarAlerta = Herramientas.creadorDeAlerta("Error de conexión", "No fue posible conectar con la base de datos"
                + "en este momento, intente más tarde", Alert.AlertType.ERROR);
            mostrarAlerta.showAndWait();
            Herramientas.cerrarPantalla(tfContraseña);
        }
    }

    
    private void registrarRolAcademico(String numeroPersonal) throws SQLException{
        Connection conn = ConectarBD.abrirConexionMySQL();
        if(conn != null){
                String consulta = "INSERT INTO rol (tipoRol, numeroPersonal) VALUES ('Coordinador', ?)";
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
