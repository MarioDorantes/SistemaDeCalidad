/*
 *Autor: Brandon Trujillo
 *fechaDeCreación: 28/11/2020
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
import pojos.Docente;
import util.Herramientas;

public class FXMLVisualizarCatalogoDeCuerpoAcademicoController implements Initializable, NotificaCambios {
    
    @FXML
    private TableView<CatalogoDeCuerpoAcademico> tvCatalogosDeCuerpoAcademico;
    @FXML
    private TableColumn tcNombreDeCatalogo;
    @FXML
    private TableColumn tcFechaDeRegistroCatalogo;
    @FXML
    private TableColumn tcDescripcionCatalogo;
    @FXML
    private TableColumn tcMisionCatalogo;
    @FXML
    private TableColumn tcResponsable;
    @FXML
    private TableColumn tcEstatusCatalogo;
    
    Alert mostrarAlerta;
    NotificaCambios notificacion;
    private ObservableList<CatalogoDeCuerpoAcademico> catalogosDeCuerpoacademico;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        catalogosDeCuerpoacademico = FXCollections.observableArrayList();
        this.tcNombreDeCatalogo.setCellValueFactory(new PropertyValueFactory("nombre"));
        this.tcFechaDeRegistroCatalogo.setCellValueFactory(new PropertyValueFactory("fecha"));
        this.tcDescripcionCatalogo.setCellValueFactory(new PropertyValueFactory("Descripcion"));
        this.tcMisionCatalogo.setCellValueFactory(new PropertyValueFactory("mision"));
        this.tcResponsable.setCellValueFactory(new PropertyValueFactory("academico.nombre"));
        this.tcEstatusCatalogo.setCellValueFactory(new PropertyValueFactory("estatus"));
        
        obtenerCatalogos();
    }
    
    private void obtenerCatalogos(){
         Connection conn = ConectarBD.abrirConexionMySQL();
        if(conn != null){
            try{
                String consulta = "select * from cuerpoAcademico";
                PreparedStatement declaracion = conn.prepareStatement(consulta);
                ResultSet resultado = declaracion.executeQuery();
                while(resultado.next()){
                    CatalogoDeCuerpoAcademico catalogosObtenidos = new CatalogoDeCuerpoAcademico();
                    catalogosObtenidos.setIdentificacion(resultado.getInt("idCuerpoAcademico"));
                    catalogosObtenidos.setNombre(resultado.getString("nombre"));
                    catalogosObtenidos.setFecha(resultado.getDate("fecha"));
                    catalogosObtenidos.setDescripcion(resultado.getString("descripcion"));
                    catalogosObtenidos.setMision(resultado.getString("mision"));
                    catalogosObtenidos.setEstatus(resultado.getString("estatus"));
                    catalogosDeCuerpoacademico.add(catalogosObtenidos);
                }
                tvCatalogosDeCuerpoAcademico.setItems(catalogosDeCuerpoacademico);
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
    private void cancelar(ActionEvent e){
        try{
            Stage stage = (Stage) tvCatalogosDeCuerpoAcademico.getScene().getWindow();
            Scene cancelar = new Scene(FXMLLoader.load(getClass().getResource("FXMLVentanaPrincipalDirectorDeLaFacultad.fxml")));
            stage.setScene(cancelar);
            stage.show();
        }catch(IOException ex){
            mostrarAlerta = Herramientas.creadorDeAlerta("Error", ex.getMessage(), Alert.AlertType.ERROR);
            mostrarAlerta.showAndWait();
        }
    }
    
    @FXML
    private void irARegistrarCatalogo(ActionEvent event) {
        try {
            FXMLLoader cargaPantalla = new FXMLLoader(getClass().getResource("FXMLRegistrarCatalogoDeCuerpoAcademico.fxml"));
            Parent root = cargaPantalla.load();
            FXMLRegistrarCatalogoDeCuerpoAcademicoController controlador = cargaPantalla.getController();
            controlador.inicializaCampos(this);
            Scene escenaRegistrarCatalogo = new Scene(root);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(escenaRegistrarCatalogo);
            stage.showAndWait();
        }catch(IOException ex){
            mostrarAlerta = Herramientas.creadorDeAlerta("Error al cargar la escena", ex.getLocalizedMessage(), Alert.AlertType.ERROR);
            mostrarAlerta.showAndWait();
        }
    }

    @FXML
    private void irAActualizarCatalogo(ActionEvent event) {
        try {
            FXMLLoader cargaPantalla = new FXMLLoader(getClass().getResource("FXMLActualizarCatalogoDeCuerpoAcademico.fxml"));
            Parent root = cargaPantalla.load();
            FXMLActualizarCatalogoDeCuerpoAcademicoController controlador = cargaPantalla.getController();
            controlador.inicializaCampos(this);
            Scene escenaActualizarCatalogo = new Scene(root);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(escenaActualizarCatalogo);
            stage.showAndWait();
        }catch(IOException ex){
            mostrarAlerta = Herramientas.creadorDeAlerta("Error al cargar la escena", ex.getMessage(), Alert.AlertType.ERROR);
            mostrarAlerta.showAndWait();
        }
    }   

    @Override
    public void refrescarTabla(boolean carga) {
       obtenerCatalogos();
    }
}

