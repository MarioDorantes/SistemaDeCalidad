/*
 *Autor: Brandon Trujillo
 *fechaDeCreación: 01/12/2020
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
import pojos.CatalogoDeCuerpoAcademico;
import pojos.CatalogoLGCA;
import util.Herramientas;

public class FXMLVisualizarCatalogoLGCAController implements Initializable, NotificaCambios {

    @FXML
    private TableView<CatalogoLGCA> tvCatalogosLgca;
    @FXML
    private TableColumn tcClave;
    @FXML
    private TableColumn tcFechaRegistro;
    @FXML
    private TableColumn tcNombre;
    @FXML
    private TableColumn tcGrado;
    @FXML
    private TableColumn tcAdscripcion;
    @FXML
    private TableColumn tcUnidad;
    @FXML
    private TableColumn tcEstatus;    
    
    Alert mostrarAlerta;
    NotificaCambios notificacion;
    
    private ObservableList<CatalogoLGCA> catalogosLgca;
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        catalogosLgca = FXCollections.observableArrayList();
        tcClave.setCellValueFactory(new PropertyValueFactory("clave"));
        tcNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        tcFechaRegistro.setCellValueFactory(new PropertyValueFactory("fecha"));
        tcGrado.setCellValueFactory(new PropertyValueFactory("grado"));
        tcAdscripcion.setCellValueFactory(new PropertyValueFactory("adscripcion"));
        tcUnidad.setCellValueFactory(new PropertyValueFactory("unidadAdscripcion"));
        tcEstatus.setCellValueFactory(new PropertyValueFactory("estatus"));
        
        obtenerCatalogos();
    }  
    
    private void obtenerCatalogos(){
        Connection conn = ConectarBD.abrirConexionMySQL();
        if(conn != null){
            try{
                String consulta = "Select * from lgca";
                PreparedStatement declaracion = conn.prepareStatement(consulta);
                ResultSet resultado = declaracion.executeQuery();
                while(resultado.next()){
                    CatalogoLGCA catalogosObtenidos = new CatalogoLGCA();
                    catalogosObtenidos.setIdentificacion(resultado.getInt("idLGCA"));
                    catalogosObtenidos.setClave(resultado.getString("clave"));
                    catalogosObtenidos.setNombre(resultado.getString("nombre"));
                    catalogosObtenidos.setFecha(resultado.getDate("fecha"));
                    catalogosObtenidos.setGrado(resultado.getInt("grado"));
                    catalogosObtenidos.setAdscripcion(resultado.getString("adscripcion"));
                    catalogosObtenidos.setUnidadAdscripcion(resultado.getString("unidadAdscripcion"));
                    catalogosObtenidos.setEstatus(resultado.getString("estatus"));
                    catalogosLgca.add(catalogosObtenidos);
                }
                tvCatalogosLgca.setItems(catalogosLgca);
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
    private void irARegistrarCatalogo(ActionEvent e){
        try {
            FXMLLoader cargaPantalla = new FXMLLoader(getClass().getResource("FXMLRegistrarCatalogoLGCA.fxml"));
            Parent root = cargaPantalla.load();
            FXMLRegistrarCatalogoLGCAController controlador = cargaPantalla.getController();
            controlador.inicializaCampos(this);
            Scene escenaRegistrarCatalogo = new Scene(root);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(escenaRegistrarCatalogo);
            stage.showAndWait();
        }catch(IOException ex){
            mostrarAlerta = Herramientas.creadorDeAlerta("Error al cargar la escena", "No fue posible acceder a la siguiente ventana,"
                + "intente más tarde", Alert.AlertType.ERROR);
            mostrarAlerta.showAndWait();
        }
    }
    
    @FXML
    private void irAActualizarCatalogo(ActionEvent e){
        int seleccion = tvCatalogosLgca.getSelectionModel().getSelectedIndex();
        if(seleccion > 0){
            CatalogoLGCA editarCatalogo = catalogosLgca.get(seleccion);
            try {
                FXMLLoader cargaPantalla = new FXMLLoader(getClass().getResource("FXMLActualizarCatalogoLGCA.fxml"));
                Parent root = cargaPantalla.load();
                FXMLActualizarCatalogoLGCAController controlador = cargaPantalla.getController();
                controlador.inicializaCampos(this, editarCatalogo);
                Scene escenaActualizarCatalogo = new Scene(root);
                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setScene(escenaActualizarCatalogo);
                stage.showAndWait();
            }catch(IOException ex){
                mostrarAlerta = Herramientas.creadorDeAlerta("Error al cargar la escena", "No fue posible acceder a la siguiente ventana,"
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
    private void cancelar(ActionEvent e){
        irAVentanaInicial();
    } 
    
    private void irAVentanaInicial(){
        try{
            Stage stage = (Stage) tvCatalogosLgca.getScene().getWindow();
            Scene cancelar = new Scene(FXMLLoader.load(getClass().getResource("FXMLVentanaPrincipalDirectorDeLaFacultad.fxml")));
            stage.setScene(cancelar);
            stage.show();
        }catch(IOException ex){
            mostrarAlerta = Herramientas.creadorDeAlerta("Error", "No fue posible acceder a la siguiente ventana, "
                + "intente más tarde", Alert.AlertType.ERROR);
            mostrarAlerta.showAndWait();
        }
    }

    @Override
    public void refrescarTabla(boolean carga) {
        tvCatalogosLgca.getItems().clear();
        obtenerCatalogos();
    }
    
}
