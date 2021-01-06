/*
 *Autor: Brandon Trujillo
 *FechaCreación: 28/11/2020
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
import pojos.Docente;
import util.Herramientas;

public class FXMLVisualizarDocentesController implements Initializable, NotificaCambios {

    @FXML
    private TableColumn tcNombre;
    @FXML
    private TableColumn tcNumeroDePersonal;
    @FXML
    private TableColumn tcCorreo;
    @FXML
    private TableColumn tcTelefono;
    @FXML
    private TableView<Docente> tvDocentes;
    @FXML
    private TableColumn tcConstraseña;
       
    Alert mostrarAlerta;
    private ObservableList<Docente> docentes;
    int idRol = 0;
    int idDocente = 0;
    boolean eliminacionExitosa = true;
    @FXML
    private TableColumn<?, ?> tcGradoAcademico;
   
    
     
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        docentes = FXCollections.observableArrayList();
        this.tcNumeroDePersonal.setCellValueFactory(new PropertyValueFactory("numeroPersonal"));
        this.tcNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        this.tcTelefono.setCellValueFactory(new PropertyValueFactory("telefono"));
        this.tcCorreo.setCellValueFactory(new PropertyValueFactory("correo"));
        this.tcConstraseña.setCellValueFactory(new PropertyValueFactory("contraseña"));
        
        obtenerDocentes();
    }
    
    private void obtenerDocentes(){
        Connection conn = ConectarBD.abrirConexionMySQL();
        if(conn != null){
            try{
                String consulta = "select academico.idAcademico, usuario.idUsuario, academico.numeroPersonal, nombre, "
                    + "telefono, gradoAcademico, usuario.correo, usuario.password as contraseña from academico "
                    + "inner join usuario on academico.idAcademico = usuario.idAcademico inner join rol "
                    + "on usuario.idRol = rol.idRol and rol.tipoRol = 'Docente'";
                PreparedStatement declaracion = conn.prepareStatement(consulta);
                ResultSet resultado = declaracion.executeQuery();
                while(resultado.next()){
                    Docente docente = new Docente();
                    docente.setNumeroPersonal(resultado.getString("numeroPersonal"));
                    docente.setNombre(resultado.getString("nombre"));
                    docente.setTelefono(resultado.getString("telefono"));
                    docente.setGradoAcademico(resultado.getString("gradoAcademico"));
                    docente.setCorreo(resultado.getString("usuario.correo"));
                    docente.setContraseña(resultado.getString("contraseña"));
                    docente.setIdentificacion(resultado.getInt("academico.idAcademico"));
                    docentes.add(docente);
                }
                tvDocentes.setItems(docentes);
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
    private void irARegistrarDocente(ActionEvent e){
        try {
            FXMLLoader cargaPantalla = new FXMLLoader(getClass().getResource("FXMLRegistrarDocente.fxml"));
            Parent root = cargaPantalla.load();
            FXMLRegistrarDocenteController controlador = cargaPantalla.getController();
            controlador.inicializaCampos(this);
            Scene escenaRegistrarDocente = new Scene(root);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(escenaRegistrarDocente);
            stage.showAndWait();
        }catch(IOException ex){
            mostrarAlerta = Herramientas.creadorDeAlerta("Error al cargar la escena", ex.getMessage(), Alert.AlertType.ERROR);
            mostrarAlerta.showAndWait();
        }
    }
    
    @FXML
    private void irAActualizarDocente(ActionEvent e){
        int seleccion = tvDocentes.getSelectionModel().getSelectedIndex();
        if(seleccion >= 0){
            Docente editarDocente = docentes.get(seleccion);
            try {
            FXMLLoader cargaPantalla = new FXMLLoader(getClass().getResource("FXMLActualizaDocente.fxml"));
            Parent root = cargaPantalla.load();
            FXMLActualizaDocenteController controlador = cargaPantalla.getController();
            controlador.inicializaCampos(this, editarDocente);
            Scene escenaActualizarDocente = new Scene(root);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(escenaActualizarDocente);
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
    private void irAEliminarDocente(ActionEvent event) {
        int seleccion = tvDocentes.getSelectionModel().getSelectedIndex();
        if(seleccion >= 0){
            Docente docenteEliminar = docentes.get(seleccion);
            idDocente = docenteEliminar.getIdentificacion();
            Alert alertConfirmacion = Herramientas.creadorDeAlerta("Confirmación", "¿Seguro de eliminar?", Alert.AlertType.CONFIRMATION);
            Optional<ButtonType> resultadoDialogo = alertConfirmacion.showAndWait();
            if(resultadoDialogo.get() == ButtonType.OK){
                obtenerIdRol(idDocente);
            }
        }else{
            mostrarAlerta = Herramientas.creadorDeAlerta("Sin seleccion", "Para eliminar un registro, "
                    + "debes seleccionarlo de la tabla", Alert.AlertType.WARNING);
            mostrarAlerta.showAndWait();
        }
        
    }
    
    private void eliminarAcademico(int idDocente) throws SQLException{
        Connection conn = ConectarBD.abrirConexionMySQL();
        if(conn != null){
            String consulta = "DELETE FROM academico WHERE idAcademico = ?";
            PreparedStatement declaracion = conn.prepareStatement(consulta);
            declaracion.setInt(1, idDocente);
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
    
    private void eliminarIntegranteDeCuerpoAcademico(int idDocente) throws SQLException{
        Connection conn = ConectarBD.abrirConexionMySQL();
        if(conn != null){
            String consulta = "DELETE FROM usuario WHERE idAcademico = ?";
            PreparedStatement declaracion = conn.prepareStatement(consulta);
            declaracion.setInt(1, idDocente);
            int resultado = declaracion.executeUpdate();
            if(resultado == 0){
                eliminacionExitosa = false;
            }else{
                eliminarUsuario(idDocente);
            }
            conn.close();
        }else{
            mostrarAlerta = Herramientas.creadorDeAlerta("Error de conexión", "No fue posible conectar con la base de datos"
                + "en este momento, intente más tarde", Alert.AlertType.ERROR);
            mostrarAlerta.showAndWait(); 
        }
    }
    
    private void eliminarUsuario(int idDocente)throws SQLException{
        Connection conn = ConectarBD.abrirConexionMySQL();
        if(conn != null){
            String consulta = "DELETE FROM cuerpoAcademicoIntegrantes WHERE idAcademico = ?";
            PreparedStatement declaracion = conn.prepareStatement(consulta);
            declaracion.setInt(1, idDocente);
            int resultado = declaracion.executeUpdate();
            if(resultado == 0){
                eliminacionExitosa = false;
            }else{
                eliminarAcademico(idDocente);
            }
            conn.close();
        }else{
            mostrarAlerta = Herramientas.creadorDeAlerta("Error de conexión", "No fue posible conectar con la base de datos"
                + "en este momento, intente más tarde", Alert.AlertType.ERROR);
            mostrarAlerta.showAndWait(); 
        }
    }
    
    private void obtenerIdRol(int idDocente){
        Connection conn = ConectarBD.abrirConexionMySQL();
        if(conn != null){
            try{
                String consulta = "SELECT idRol FROM usuario WHERE idAcademico = ?";
                PreparedStatement declaracion = conn.prepareStatement(consulta);
                declaracion.setInt(1, idDocente);
                ResultSet resultado = declaracion.executeQuery();
                if(resultado.next()){
                    idRol = resultado.getInt("idRol");
                    if(idRol > 0){
                        eliminarIntegranteDeCuerpoAcademico(idDocente);
                    }else{
                        mostrarAlerta = Herramientas.creadorDeAlerta("Error de consulta", "No fue posible obtener la información necesaria "
                            + "en este momento, intente más tarde", Alert.AlertType.ERROR);
                        mostrarAlerta.showAndWait(); 
                    }
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
    private void cancelar(ActionEvent e){
        regresarAVentanaDirector();
    } 
    
    private void regresarAVentanaDirector(){
        try {
            Stage stage = (Stage) tvDocentes.getScene().getWindow();
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
        tvDocentes.getItems().clear();
        obtenerDocentes();
    }  
   
}
