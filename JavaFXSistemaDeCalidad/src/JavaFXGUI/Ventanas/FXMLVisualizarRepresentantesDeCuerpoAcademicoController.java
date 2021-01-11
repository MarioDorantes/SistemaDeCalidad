/*
 * Autor: Brandon Trujillo
 * fecha: 01-09-2021
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
import pojos.RepresentanteDeCuerpoAcademico;
import util.Herramientas;

public class FXMLVisualizarRepresentantesDeCuerpoAcademicoController implements Initializable, NotificaCambios {

    @FXML
    private TableView<RepresentanteDeCuerpoAcademico> tvRepresentantes;
    @FXML
    private TableColumn tcNombre;
    @FXML
    private TableColumn tcNumeroDePersonal;
    @FXML
    private TableColumn tcTelefono;
    @FXML
    private TableColumn tcGradoAcademico;
    @FXML
    private TableColumn tcCorreo;
    @FXML
    private TableColumn tcConstraseña;
    
    Alert mostrarAlerta;
    NotificaCambios notificacion;
    boolean eliminacionExitosa = true;
    boolean estaVinculadoAUnCuerpoAcademico = true;
    
    int idRepresentante = 0;
    int idRol = 0;
    
    private ObservableList<RepresentanteDeCuerpoAcademico> representantes;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        representantes = FXCollections.observableArrayList();
        this.tcNumeroDePersonal.setCellValueFactory(new PropertyValueFactory("numeroPersonal"));
        this.tcNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        this.tcTelefono.setCellValueFactory(new PropertyValueFactory("telefono"));
        this.tcGradoAcademico.setCellValueFactory(new PropertyValueFactory("gradoAcademico"));
        this.tcCorreo.setCellValueFactory(new PropertyValueFactory("correo"));
        this.tcConstraseña.setCellValueFactory(new PropertyValueFactory("contraseña"));
       
        obtenerRepresentantes();
    }    
    
    private void obtenerRepresentantes(){
        Connection conn = ConectarBD.abrirConexionMySQL();
        if(conn != null){
            try{
                String consulta = "select academico.idAcademico as idRepresentante, usuario.idUsuario, academico.numeroPersonal, nombre, "
                    + "telefono, gradoAcademico, usuario.correo, usuario.password as contraseña from academico "
                    + "inner join usuario on academico.idAcademico = usuario.idAcademico inner join rol "
                    + "on usuario.idRol = rol.idRol and rol.tipoRol = 'Representante'";
                PreparedStatement declaracion = conn.prepareStatement(consulta);
                ResultSet resultado = declaracion.executeQuery();
                while(resultado.next()){
                    RepresentanteDeCuerpoAcademico representantesObtenidos = new RepresentanteDeCuerpoAcademico();
                    representantesObtenidos.setNumeroPersonal(resultado.getString("numeroPersonal"));
                    representantesObtenidos.setNombre(resultado.getString("nombre"));
                    representantesObtenidos.setTelefono(resultado.getString("telefono"));
                    representantesObtenidos.setGradoAcademico(resultado.getString("gradoAcademico"));
                    representantesObtenidos.setCorreo(resultado.getString("correo"));
                    representantesObtenidos.setContraseña(resultado.getString("contraseña"));
                    representantesObtenidos.setIdentificacion(resultado.getInt("idRepresentante"));
                    representantes.add(representantesObtenidos);
                }
                tvRepresentantes.setItems(representantes);
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
    private void clicEliminarRepresentante(ActionEvent event) {
        int seleccion = tvRepresentantes.getSelectionModel().getSelectedIndex();
        if(seleccion >= 0){
            RepresentanteDeCuerpoAcademico eliminarRepresentante = representantes.get(seleccion);
            idRepresentante = eliminarRepresentante.getIdentificacion();
            Alert alertConfirmacion = Herramientas.creadorDeAlerta("Confirmación", "¿Seguro de eliminar?"
                + " Algún Cuerpo Académico podría quedar sin representante y deberá seleccionar otro", Alert.AlertType.CONFIRMATION);
            Optional<ButtonType> resultadoDialogo = alertConfirmacion.showAndWait();
            if(resultadoDialogo.get() == ButtonType.OK){
                obtenerIdRol(idRepresentante);
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
        }else{
            mostrarAlerta = Herramientas.creadorDeAlerta("Sin seleccion", "Para eliminar un registro, "
                    + "debes seleccionarlo de la tabla", Alert.AlertType.WARNING);
            mostrarAlerta.showAndWait();
        }
        
    }
    
    private void obtenerIdRol(int idRepresentante){
        Connection conn = ConectarBD.abrirConexionMySQL();
        if(conn != null){
            try{
                String consulta = "SELECT idRol FROM usuario WHERE idAcademico = ?";
                PreparedStatement declaracion = conn.prepareStatement(consulta);
                declaracion.setInt(1, idRepresentante);
                ResultSet resultado = declaracion.executeQuery();
                if(resultado.next()){
                    idRol = resultado.getInt("idRol");
                    if(idRol > 0){
                        eliminarUsuario(idRepresentante);
                    }else{
                        eliminacionExitosa = false;
                        mostrarAlerta = Herramientas.creadorDeAlerta("Error de consulta", "No fue posible obtener la información necesaria "
                            + "en este momento, intente más tarde", Alert.AlertType.ERROR);
                        mostrarAlerta.showAndWait(); 
                    }
                }else{
                    eliminacionExitosa = false;
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
            eliminacionExitosa = false;
            mostrarAlerta = Herramientas.creadorDeAlerta("Error de conexión", "No fue posible conectar con la base de datos"
                + "en este momento, intente más tarde", Alert.AlertType.ERROR);
            mostrarAlerta.showAndWait();
        }
    }
    
    private void verificarVinculacionDeRepresentante(int idRepresentante)throws SQLException{
        Connection conn = ConectarBD.abrirConexionMySQL();
        if(conn != null){
            String consulta = "select idCuerpoAcademico from cuerpoAcademicoIntegrantes where idAcademico = ?;";
            PreparedStatement declaracion = conn.prepareStatement(consulta);
            declaracion.setInt(1, idRepresentante);
            ResultSet resultado = declaracion.executeQuery();
            if(!resultado.next()){
                estaVinculadoAUnCuerpoAcademico = false;
            }
            conn.close();
        }else{
            eliminacionExitosa = false;
            mostrarAlerta = Herramientas.creadorDeAlerta("Error de conexión", "No fue posible conectar con la base de datos"
                + "en este momento, intente más tarde", Alert.AlertType.ERROR);
            mostrarAlerta.showAndWait(); 
        }
    }
    
    private void eliminarUsuario(int idRepresentante)throws SQLException{
        Connection conn = ConectarBD.abrirConexionMySQL();
        if(conn != null){
            String consulta = "DELETE FROM usuario WHERE idAcademico = ?";
            PreparedStatement declaracion = conn.prepareStatement(consulta);
            declaracion.setInt(1, idRepresentante);
            int resultado = declaracion.executeUpdate();
            if(resultado == 0){
                eliminacionExitosa = false;
            }else{
                verificarVinculacionDeRepresentante(idRepresentante);
                if(estaVinculadoAUnCuerpoAcademico){
                    eliminarRepresentanteDeCuerpoAcademico(idRepresentante);
                }else{
                    eliminarAcademico(idRepresentante);
                }    
            }
            conn.close();
        }else{
            eliminacionExitosa = false;
            mostrarAlerta = Herramientas.creadorDeAlerta("Error de conexión", "No fue posible conectar con la base de datos"
                + "en este momento, intente más tarde", Alert.AlertType.ERROR);
            mostrarAlerta.showAndWait(); 
        }
    }
    
    private void eliminarRepresentanteDeCuerpoAcademico(int idRepresentante) throws SQLException{
        Connection conn = ConectarBD.abrirConexionMySQL();
        if(conn != null){
            String consulta = "DELETE FROM cuerpoAcademicoIntegrantes WHERE idAcademico = ?";
            PreparedStatement declaracion = conn.prepareStatement(consulta);
            declaracion.setInt(1, idRepresentante);
            int resultado = declaracion.executeUpdate();
            if(resultado == 0){
                eliminacionExitosa = false;
                System.out.println("entre al falso");
            }else{
                eliminarAcademico(idRepresentante);
            }
            conn.close();
        }else{
            eliminacionExitosa = false;
            mostrarAlerta = Herramientas.creadorDeAlerta("Error de conexión", "No fue posible conectar con la base de datos"
                + "en este momento, intente más tarde", Alert.AlertType.ERROR);
            mostrarAlerta.showAndWait(); 
        }
    }

    private void eliminarAcademico(int idRepresentante) throws SQLException{
        Connection conn = ConectarBD.abrirConexionMySQL();
        if(conn != null){
            String consulta = "DELETE FROM academico WHERE idAcademico = ?";
            PreparedStatement declaracion = conn.prepareStatement(consulta);
            declaracion.setInt(1, idRepresentante);
            int resultado = declaracion.executeUpdate();
            if(resultado == 0){
                eliminacionExitosa = false;
            }else{
                eliminarRol(idRol);
            }
            conn.close();
        }else{
            eliminacionExitosa = false;
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
            }
            conn.close();
        }else{
            eliminacionExitosa = false;
            mostrarAlerta = Herramientas.creadorDeAlerta("Error de conexión", "No fue posible conectar con la base de datos"
                + "en este momento, intente más tarde", Alert.AlertType.ERROR);
            mostrarAlerta.showAndWait(); 
        }
    }
    
    @FXML
    private void irAActualizarRepresentante(ActionEvent event) {
        int seleccion = tvRepresentantes.getSelectionModel().getSelectedIndex();
        if(seleccion >= 0){
            RepresentanteDeCuerpoAcademico editarRepresentante = representantes.get(seleccion);
            try {
            FXMLLoader cargaPantalla = new FXMLLoader(getClass().getResource("FXMLActualizarRepresentante.fxml"));
            Parent root = cargaPantalla.load();
            FXMLActualizarRepresentanteController controlador = cargaPantalla.getController();
            controlador.inicializaCampos(this, editarRepresentante);
            Scene escenaActualizarRepresentante = new Scene(root);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(escenaActualizarRepresentante);
            stage.showAndWait();
            }catch(IOException ex){
                mostrarAlerta = Herramientas.creadorDeAlerta("Error al cargar la escena", "No se pudo cargar la ventana siguiente, "
                    + "intente más tarde", Alert.AlertType.ERROR);
                mostrarAlerta.showAndWait();
            }
        }else{
            mostrarAlerta = Herramientas.creadorDeAlerta("Atención", "Para editar un registro, "
                + "primero seleccionelo", Alert.AlertType.INFORMATION);
            mostrarAlerta.showAndWait();
        }
    }

    @FXML
    private void irARegistrarRepresentante(ActionEvent event) {
        try {
            FXMLLoader cargaPantalla = new FXMLLoader(getClass().getResource("FXMLRegistrarRepresentante.fxml"));
            Parent root = cargaPantalla.load();
            FXMLRegistrarRepresentanteController controlador = cargaPantalla.getController();
            controlador.inicializaCampos(this);
            Scene escenaRegistrarRepresentante = new Scene(root);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(escenaRegistrarRepresentante);
            stage.showAndWait();
        }catch(IOException ex){
            mostrarAlerta = Herramientas.creadorDeAlerta("Error al cargar la escena", "No se pudo cargar la ventana siguiente, "
                + "intente más tarde", Alert.AlertType.ERROR);
            mostrarAlerta.showAndWait();
        }
    }

    @FXML
    private void cancelar(ActionEvent event) {
        try {
            Stage stage = (Stage) tvRepresentantes.getScene().getWindow();
            Scene cancelar = new Scene(FXMLLoader.load(getClass().getResource("FXMLVentanaPrincipalRepresentanteDeCuerpoAcademico.fxml")));
            stage.setScene(cancelar);
            stage.show();
        } catch (IOException ex) {
            mostrarAlerta = Herramientas.creadorDeAlerta("Error", "No se pudo cargar la ventana siguiente, "
                + "intente más tarde", Alert.AlertType.ERROR);
            mostrarAlerta.showAndWait();
        } 
    }

    @Override
    public void refrescarTabla(boolean carga) {
        tvRepresentantes.getItems().clear();
        obtenerRepresentantes();
    }
    
}
