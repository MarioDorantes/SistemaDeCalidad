/*
 * Autor: Brandon Trujillo
 * fecha: 1/12/2020
 */
package JavaFXGUI.Ventanas;

import conexionBD.ConectarBD;
import interfaz.NotificaCambios;
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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import pojos.CoordinadorDeAcademia;
import util.Herramientas;

public class FXMLVisualizarCoordinadoresController implements Initializable, NotificaCambios {

    @FXML
    private TableView<CoordinadorDeAcademia> tvCoordinadores;
    @FXML
    private TableColumn tcNombre;
    @FXML
    private TableColumn tcNumeroDePersonal;
    @FXML
    private TableColumn tcTelefono;
    @FXML
    private TableColumn tcCorreo;
    @FXML
    private TableColumn tcConstraseña;
    
    Alert mostrarAlerta;
    private ObservableList<CoordinadorDeAcademia> coordinadores;
    int idCoordinador = 0;
    int idRol = 0;
    boolean eliminacionExitosa = true;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        coordinadores = FXCollections.observableArrayList();
        this.tcNumeroDePersonal.setCellValueFactory(new PropertyValueFactory("numeroPersonal"));
        this.tcNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        this.tcTelefono.setCellValueFactory(new PropertyValueFactory("telefono"));
        this.tcCorreo.setCellValueFactory(new PropertyValueFactory("correo"));
        this.tcConstraseña.setCellValueFactory(new PropertyValueFactory("contraseña"));
        
        obtenerCoordinadores();
    }   
    
    private void obtenerCoordinadores(){
        Connection conn = ConectarBD.abrirConexionMySQL();
        if(conn != null){
            try{
                String consulta = "select academico.idAcademico, usuario.idUsuario, academico.numeroPersonal, nombre, "
                    + "telefono,usuario.correo, usuario.password as contraseña from academico "
                    + "inner join usuario on academico.idAcademico = usuario.idAcademico inner join rol "
                    + "on usuario.idRol = rol.idRol and rol.tipoRol = 'Coordinador'";
                PreparedStatement declaracion = conn.prepareStatement(consulta);
                ResultSet resultado = declaracion.executeQuery();
                while(resultado.next()){
                    CoordinadorDeAcademia coordinador = new CoordinadorDeAcademia();
                    coordinador.setNumeroPersonal(resultado.getString("numeroPersonal"));
                    coordinador.setNombre(resultado.getString("nombre"));
                    coordinador.setTelefono(resultado.getString("telefono"));
                    coordinador.setCorreo(resultado.getString("usuario.correo"));
                    coordinador.setContraseña(resultado.getString("contraseña"));
                    coordinador.setIdentificacion(resultado.getInt("academico.idAcademico"));
                    coordinadores.add(coordinador);
                }
                tvCoordinadores.setItems(coordinadores);
                conn.close();
            }catch(SQLException ex){
                mostrarAlerta = Herramientas.creadorDeAlerta("Error", "No se puede acceder a la base de datos en este momento, "
                    + "intente más tarde", Alert.AlertType.ERROR);
                mostrarAlerta.showAndWait();
                
            }
        }else{
            mostrarAlerta = Herramientas.creadorDeAlerta("Error", "No se puede conectar con la base de datos en este momento, "
                + "intente más tarde", Alert.AlertType.ERROR);
            mostrarAlerta.showAndWait();
        }
    }

    @FXML
    private void clicEliminar(ActionEvent event) {
        int seleccion = tvCoordinadores.getSelectionModel().getSelectedIndex();
        if(seleccion >= 0){
            CoordinadorDeAcademia eliminarCoordinador = coordinadores.get(seleccion);
            idCoordinador = eliminarCoordinador.getIdentificacion();
            Alert alertConfirmacion = Herramientas.creadorDeAlerta("Confirmación", "¿Seguro de eliminar?", Alert.AlertType.CONFIRMATION);
            Optional<ButtonType> resultadoDialogo = alertConfirmacion.showAndWait();
            if(resultadoDialogo.get() == ButtonType.OK){
                obtenerIdRol(idCoordinador);
            }
        }else{
            mostrarAlerta = Herramientas.creadorDeAlerta("Sin seleccion", "Para eliminar un registro, "
                    + "debes seleccionarlo de la tabla", Alert.AlertType.WARNING);
            mostrarAlerta.showAndWait();
        }
    }
    
    private void obtenerIdRol(int idCoordinador){
        Connection conn = ConectarBD.abrirConexionMySQL();
        if(conn != null){
            try{
                String consulta = "SELECT idRol FROM usuario WHERE idAcademico = ?";
                PreparedStatement declaracion = conn.prepareStatement(consulta);
                declaracion.setInt(1, idCoordinador);
                ResultSet resultado = declaracion.executeQuery();
                if(resultado.next()){
                    idRol = resultado.getInt("idRol");
                    eliminarUsuario(idCoordinador);
                }else{
                    mostrarAlerta = Herramientas.creadorDeAlerta("Error de consulta", "No fue posible obtener la información necesaria "
                        + "en este momento, intente más tarde", Alert.AlertType.ERROR);
                    mostrarAlerta.showAndWait();
                }
                conn.close();
            }catch(SQLException ex){
                eliminacionExitosa = false;
                mostrarAlerta = Herramientas.creadorDeAlerta("Error de consulta", "No fue posible acceder a la base de datos "
                    + "en este momento, intente más tarde", Alert.AlertType.ERROR);
                mostrarAlerta.showAndWait();
            }
        }else{
            mostrarAlerta = Herramientas.creadorDeAlerta("Error de conexión", "No fue posible conectar con la base de datos"
                + "en este momento, intente más tarde", Alert.AlertType.ERROR);
            mostrarAlerta.showAndWait();
        }
    }
    
     private void eliminarUsuario(int idCoordinador)throws SQLException{
        Connection conn = ConectarBD.abrirConexionMySQL();
        if(conn != null){
            String consulta = "DELETE FROM usuario WHERE idAcademico = ?";
            PreparedStatement declaracion = conn.prepareStatement(consulta);
            declaracion.setInt(1, idCoordinador);
            int resultado = declaracion.executeUpdate();
            if(resultado == 0){
                eliminacionExitosa = false;
            }else{
                eliminarAcademico(idCoordinador);
            }
            conn.close();
        }else{
            mostrarAlerta = Herramientas.creadorDeAlerta("Error de conexión", "No fue posible conectar con la base de datos"
                + "en este momento, intente más tarde", Alert.AlertType.ERROR);
            mostrarAlerta.showAndWait(); 
        }
    }
     
    private void eliminarAcademico(int idCoordinador) throws SQLException{
        Connection conn = ConectarBD.abrirConexionMySQL();
        if(conn != null){
            String consulta = "DELETE FROM academico WHERE idAcademico = ?";
            PreparedStatement declaracion = conn.prepareStatement(consulta);
            declaracion.setInt(1, idCoordinador);
            int resultado = declaracion.executeUpdate();
            if(resultado == 0){
                eliminacionExitosa = false;
            }else{
                eliminarRol(idRol);
            }
            conn.close();
        }else{
            mostrarAlerta = Herramientas.creadorDeAlerta("Error de conexión", "No fue posible conectar con la base de datos"
                + "en este momento, intente más tarde", Alert.AlertType.ERROR);
            mostrarAlerta.showAndWait(); 
        }
    }
    
    private void eliminarRol(int idRol)throws SQLException{
         Connection conn = ConectarBD.abrirConexionMySQL();
        if(conn != null){
            String consulta = "DELETE FROM rol WHERE idRol = ?";
            PreparedStatement declaracion = conn.prepareStatement(consulta);
            declaracion.setInt(1, idRol);
            int resultado = declaracion.executeUpdate();
            if(resultado == 0){
                eliminacionExitosa = false;
            }else{
                if(eliminacionExitosa){
                    mostrarAlerta = Herramientas.creadorDeAlerta("Mensaje", "Eliminacion exitosa", Alert.AlertType.INFORMATION);
                    mostrarAlerta.showAndWait();
                    this.refrescarTabla(true);
                }else{
                    mostrarAlerta = Herramientas.creadorDeAlerta("Error", "No se pudo completar la eliminación, "
                        + "intente más tarde", Alert.AlertType.ERROR);
                    mostrarAlerta.showAndWait();
                }
            }
            conn.close();
        }else{
            mostrarAlerta = Herramientas.creadorDeAlerta("Error de conexión", "No fue posible conectar con la base de datos"
                + "en este momento, intente más tarde", Alert.AlertType.ERROR);
            mostrarAlerta.showAndWait(); 
        }
    }

    @FXML
    private void irAActualizarCoordinador(ActionEvent event) {
        int seleccion = tvCoordinadores.getSelectionModel().getSelectedIndex();
        if(seleccion >= 0){
            CoordinadorDeAcademia editarCoordinador = coordinadores.get(seleccion);
            try {
            FXMLLoader cargaPantalla = new FXMLLoader(getClass().getResource("FXMLActualizarCoordinador.fxml"));
            Parent root = cargaPantalla.load();
            FXMLActualizarCoordinadorController controlador = cargaPantalla.getController();
            controlador.inicializaCampos(this, editarCoordinador);
            Scene escenaActualizarCoordinador = new Scene(root);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(escenaActualizarCoordinador);
            stage.showAndWait();
            }catch(IOException ex){
                mostrarAlerta = Herramientas.creadorDeAlerta("Error al cargar la escena", ex.getMessage(), Alert.AlertType.ERROR);
                mostrarAlerta.showAndWait();
            }
        }else{
            mostrarAlerta = Herramientas.creadorDeAlerta("Atención", "Para editar un registro, "
                + "primero seleccionelo", Alert.AlertType.INFORMATION);
            mostrarAlerta.showAndWait();
        }
    }

    @FXML
    private void irARegistrarCoordinador(ActionEvent event) {
        try {
            FXMLLoader cargaPantalla = new FXMLLoader(getClass().getResource("FXMLRegistrarCoordinador.fxml"));
            Parent root = cargaPantalla.load();
            FXMLRegistrarCoordinadorController controlador = cargaPantalla.getController();
            controlador.inicializaCampos(this);
            Scene escenaRegistrarCoordinador = new Scene(root);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(escenaRegistrarCoordinador);
            stage.showAndWait();
        }catch(IOException ex){
            mostrarAlerta = Herramientas.creadorDeAlerta("Error al cargar la escena", ex.getMessage(), Alert.AlertType.ERROR);
            mostrarAlerta.showAndWait();
        }
    }

    @FXML
    private void cancelar(ActionEvent event) {
        regresarAVentanaDirector();
    }
    
    private void regresarAVentanaDirector(){
        try {
            Stage stage = (Stage) tvCoordinadores.getScene().getWindow();
            Scene cancelar = new Scene(FXMLLoader.load(getClass().getResource("FXMLVentanaPrincipalDirectorDeLaFacultad.fxml")));
            stage.setScene(cancelar);
            stage.show();
        } catch (IOException ex) {
            mostrarAlerta = Herramientas.creadorDeAlerta("Error", ex.getMessage(), Alert.AlertType.ERROR);
            mostrarAlerta.showAndWait();
        } 
    }

    @Override
    public void refrescarTabla(boolean carga) {
        tvCoordinadores.getItems().clear();
        obtenerCoordinadores();
    }
}
