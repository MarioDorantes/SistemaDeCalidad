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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import pojos.TrabajoRecepcional;
import util.Herramientas;

public class FXMLVisualizarTrabajosRecepcionalesController implements Initializable, NotificaCambios {

    @FXML
    private TableView<TrabajoRecepcional> tvTrabajosRecepcionales;
    @FXML
    private TableColumn tcNombreTrabajo;
    @FXML
    private TableColumn tcFecha;
    @FXML
    private TableColumn tcNombreAlumno;
    @FXML
    private TableColumn tcNombreDirector;
    @FXML
    private TableColumn tcNombreCoDirector;
    @FXML
    private TableColumn tcNombreSinodal;
    @FXML
    private TableColumn tcDescripcion;
    @FXML
    private TableColumn tcEstatus;
    
    Alert mostrarAlerta;
    NotificaCambios notificacion;
    
    private ObservableList<TrabajoRecepcional> trabajosRecepcionales;    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        trabajosRecepcionales = FXCollections.observableArrayList();
        this.tcNombreTrabajo.setCellValueFactory(new PropertyValueFactory("nombre"));
        this.tcFecha.setCellValueFactory(new PropertyValueFactory("fechaRegistro"));
        this.tcDescripcion.setCellValueFactory(new PropertyValueFactory("descripcion"));
        this.tcEstatus.setCellValueFactory(new PropertyValueFactory("estatus"));
        this.tcNombreAlumno.setCellValueFactory(new PropertyValueFactory("nombreAlumno"));
        this.tcNombreDirector.setCellValueFactory(new PropertyValueFactory("nombreDirector"));
        this.tcNombreCoDirector.setCellValueFactory(new PropertyValueFactory("nombreCoDirector"));
        this.tcNombreSinodal.setCellValueFactory(new PropertyValueFactory("nombreSinodal"));
        
        obtenerTrabajosRecepcionales();
                 
    }   
    
    private void obtenerTrabajosRecepcionales(){
        Connection conn = ConectarBD.abrirConexionMySQL();
        if(conn != null){
            try{
                String consulta = "select recepcional.*, estudiante.nombre as nombreAlumno, academicoA.nombre as nombreDirector,"
                    + " academicoB.nombre as nombreCoDirector, academicoC.nombre as nombreSinodal" 
                    + " from trabajorecepcional recepcional inner join academico academicoA"
                    + " on recepcional.idDirector = academicoA.idAcademico"
                    + " inner join academico academicoB on recepcional.idCoDirector = academicoB.idAcademico" 
                    + " inner join academico academicoC on recepcional.idSinodal = academicoC.idAcademico"
                    + " inner join estudiante on recepcional.idAlumno = estudiante.idEstudiante;";
                PreparedStatement declaracion = conn.prepareStatement(consulta);
                ResultSet resultado = declaracion.executeQuery();
                while(resultado.next()){
                    TrabajoRecepcional trabajosObtenidos = new TrabajoRecepcional();
                    trabajosObtenidos.setIdentificacion(resultado.getInt("idTrabajoRecepcional"));
                    trabajosObtenidos.setNombre(resultado.getString("nombre"));
                    trabajosObtenidos.setFechaRegistro(resultado.getDate("fechaRegistro"));
                    trabajosObtenidos.setDescripcion(resultado.getString("descripcion"));
                    trabajosObtenidos.setEstatus(resultado.getString("estatus"));
                    trabajosObtenidos.setIdAlumno(resultado.getInt("idAlumno"));
                    trabajosObtenidos.setIdDirector(resultado.getInt("idDirector"));
                    trabajosObtenidos.setIdCoDirector(resultado.getInt("idCoDirector"));
                    trabajosObtenidos.setIdSinodal(resultado.getInt("idSinodal"));
                    trabajosObtenidos.setNombreAlumno(resultado.getString("nombreAlumno"));
                    trabajosObtenidos.setNombreDirector(resultado.getString("nombreDirector"));
                    trabajosObtenidos.setNombreCoDirector(resultado.getString("nombreCoDirector"));
                    trabajosObtenidos.setNombreSinodal(resultado.getString("nombreSinodal"));
                    trabajosRecepcionales.add(trabajosObtenidos);
                }
                tvTrabajosRecepcionales.setItems(trabajosRecepcionales);
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
    private void irARegistrarTrabajo(ActionEvent event) {
        try {
            FXMLLoader cargaPantalla = new FXMLLoader(getClass().getResource("FXMLRegistrarTrabajo.fxml"));
            Parent root = cargaPantalla.load();
            FXMLRegistrarTrabajoController controlador = cargaPantalla.getController();
            controlador.inicializaCampos(this);
            Scene escenaRegistrarTrabajo = new Scene(root);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(escenaRegistrarTrabajo);
            stage.showAndWait();
        }catch(IOException ex){
            mostrarAlerta = Herramientas.creadorDeAlerta("Error al cargar la escena", "No se pudo cargar la ventana siguiente, "
                + "intente más tarde", Alert.AlertType.ERROR);
            mostrarAlerta.showAndWait();
        }
    }

    @FXML
    private void irAActualizar(ActionEvent event) {
        int seleccion = tvTrabajosRecepcionales.getSelectionModel().getSelectedIndex();
        if(seleccion >= 0){
            TrabajoRecepcional editarTrabajo = trabajosRecepcionales.get(seleccion);
            try {
                FXMLLoader cargaPantalla = new FXMLLoader(getClass().getResource("FXMLActualizarTrabajo.fxml"));
                Parent root = cargaPantalla.load();
                FXMLActualizarTrabajoController controlador = cargaPantalla.getController();
                controlador.inicializaCampos(this, editarTrabajo);
                Scene escenaActualizarTrabajo = new Scene(root);
                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setScene(escenaActualizarTrabajo);
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
    private void cancelar(ActionEvent event) {
         try {
            Stage stage = (Stage) tvTrabajosRecepcionales.getScene().getWindow();
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
        tvTrabajosRecepcionales.getItems().clear();
        obtenerTrabajosRecepcionales();
    }
    
}
