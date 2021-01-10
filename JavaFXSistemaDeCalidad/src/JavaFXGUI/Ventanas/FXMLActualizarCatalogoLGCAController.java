/*
 *Autor: Brandon Trujillo
 *fechaDeCreacion: 01/12/2020
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javax.xml.bind.DatatypeConverter;
import pojos.CatalogoDeCuerpoAcademico;
import pojos.CatalogoLGCA;
import util.Herramientas;

public class FXMLActualizarCatalogoLGCAController implements Initializable {
   
    @FXML
    private TextField tfEstatus;
    @FXML
    private TextField tfFecha;
    @FXML
    private TextField tfGradoConsolidacion;
    @FXML
    private TextField tfAdscripcion;
    @FXML
    private TextField tfUnidadDeAdscripcion;
    @FXML
    private TextField tfClave;
    @FXML
    private TextField tfNombre;
    
    @FXML
    private TableView<CatalogoDeCuerpoAcademico> tvCuerposAcademicos;
    @FXML
    private TableColumn tcNombre;
    @FXML
    private TableColumn tcFecha;
    @FXML
    private TableColumn tcEstatus;
    
    
    Alert mostrarAlerta;
    NotificaCambios notificacion;
    CatalogoLGCA editarCatalogo;
    boolean actualizacionExitosa = true;
    private ObservableList<CatalogoDeCuerpoAcademico> catalogosDeCuerpoAcademico;
    
    int idCatalogoLgca = 0;
    
    String claveAuxiliar;
    String nombreAuxiliar;
    String fechaAuxiliar;
    String gradoConsolidacionAuxiliar;
    String adscripcionAuxiliar;
    String unidadAdscripcionAuxiliar;
    String estatusAuxiliar;
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        catalogosDeCuerpoAcademico = FXCollections.observableArrayList();
        tcNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        tcFecha.setCellValueFactory(new PropertyValueFactory("fecha"));
        tcEstatus.setCellValueFactory(new PropertyValueFactory("estatus"));
        
        
    }  
    
    private void obtenerCuerposAcademicos(int idLgca){
        Connection conn = ConectarBD.abrirConexionMySQL();
        if(conn != null){
            try{
                String consulta = "select cuerpoAcademico.nombre, cuerpoAcademico.fecha, "
                    + "cuerpoAcademico.estatus from cuerpoAcademico inner join "
                    + "lgca where cuerpoAcademico.idLgca = lgca.idLgca and cuerpoAcademico.idLgca = ?; ";
                PreparedStatement declaracion = conn.prepareStatement(consulta);
                declaracion.setInt(1, idLgca);
                ResultSet resultado = declaracion.executeQuery();
                while(resultado.next()){
                    CatalogoDeCuerpoAcademico catalogosObtenidos = new CatalogoDeCuerpoAcademico();
                    catalogosObtenidos.setNombre(resultado.getString("nombre"));
                    catalogosObtenidos.setFecha(resultado.getDate("fecha"));
                    catalogosObtenidos.setEstatus(resultado.getString("estatus"));
                    catalogosDeCuerpoAcademico.add(catalogosObtenidos);
                }
                tvCuerposAcademicos.setItems(catalogosDeCuerpoAcademico);
                conn.close();
            }catch(SQLException ex){
                actualizacionExitosa = false;
                mostrarAlerta = Herramientas.creadorDeAlerta("Error", "No se puede acceder a la base de datos en este momento, "
                    + "intente más tarde", Alert.AlertType.ERROR);
                mostrarAlerta.showAndWait();
            }
        }else{
            actualizacionExitosa = false;
            mostrarAlerta = Herramientas.creadorDeAlerta("Error", "No se puede conectar con la base de datos en este momento, "
                + "intente más tarde", Alert.AlertType.ERROR);
            mostrarAlerta.showAndWait();
        }
    }

    public void inicializaCampos(NotificaCambios notificacion, CatalogoLGCA editarCatalogo){
        this.notificacion = notificacion;
        this.editarCatalogo = editarCatalogo;
        
        obtenerCatalogoSeleccionado();
    }
    
    private void obtenerCatalogoSeleccionado(){
        idCatalogoLgca = editarCatalogo.getIdentificacion();
        tfClave.setText(editarCatalogo.getClave());
        tfClave.setEditable(false);
        tfNombre.setText(editarCatalogo.getNombre());
        tfFecha.setText(editarCatalogo.getFecha().toString());
        tfGradoConsolidacion.setText(String.valueOf(editarCatalogo.getGrado()));
        tfAdscripcion.setText(editarCatalogo.getAdscripcion());
        tfUnidadDeAdscripcion.setText(editarCatalogo.getUnidadAdscripcion());
        tfEstatus.setText(editarCatalogo.getEstatus());
        
        obtenerCuerposAcademicos(idCatalogoLgca);
    }
    
    @FXML
    private void cancelar(ActionEvent e){
        Herramientas.cerrarPantalla(tfEstatus);
    }

    @FXML
    private void clicActualizar(ActionEvent event) {
        tfClave.setStyle("-fx-border-color: ;");
        tfNombre.setStyle("-fx-border-color: ;");
        tfFecha.setStyle("-fx-border-color: ;");
        tfGradoConsolidacion.setStyle("-fx-border-color: ;");
        tfAdscripcion.setStyle("-fx-border-color: ;");
        tfUnidadDeAdscripcion.setStyle("-fx-border-color: ;");
        tfEstatus.setStyle("-fx-border-color: ;");
        
        boolean esCorrecto = true;
        
        claveAuxiliar = tfClave.getText();
        nombreAuxiliar = tfNombre.getText();
        fechaAuxiliar = tfFecha.getText();
        gradoConsolidacionAuxiliar = tfGradoConsolidacion.getText();
        adscripcionAuxiliar = tfAdscripcion.getText();
        unidadAdscripcionAuxiliar = tfUnidadDeAdscripcion.getText();
        estatusAuxiliar = tfEstatus.getText();
        
        if(claveAuxiliar.isEmpty()){
            tfClave.setStyle("-fx-border-color: red;");
            esCorrecto = false;
        }
        if(nombreAuxiliar.isEmpty()){
            tfNombre.setStyle("-fx-border-color: red;");
            esCorrecto = false;
        }
        if(fechaAuxiliar.isEmpty()){
            tfFecha.setStyle("-fx-border-color: red;");
            esCorrecto = false;
        }
        if(gradoConsolidacionAuxiliar.isEmpty()){
            tfGradoConsolidacion.setStyle("-fx-border-color: red;");
            esCorrecto = false;
        }
        if(adscripcionAuxiliar.isEmpty()){
            tfAdscripcion.setStyle("-fx-border-color: red;");
            esCorrecto = false;
        }
        if(unidadAdscripcionAuxiliar.isEmpty()){
            tfUnidadDeAdscripcion.setStyle("-fx-border-color: red;");
            esCorrecto = false;
        }
        if(estatusAuxiliar.isEmpty()){
            tfEstatus.setStyle("-fx-border-color: red;");
            esCorrecto = false;
        }
        
        if(esCorrecto){
            actualizarCatalogoLgca(nombreAuxiliar, fechaAuxiliar, gradoConsolidacionAuxiliar, adscripcionAuxiliar, 
                unidadAdscripcionAuxiliar, estatusAuxiliar, idCatalogoLgca);
            if(actualizacionExitosa){
                mostrarAlerta = Herramientas.creadorDeAlerta("Mensaje", "Actualización exitosa", Alert.AlertType.INFORMATION);
                mostrarAlerta.showAndWait();
                Herramientas.cerrarPantalla(tfEstatus);
                notificacion.refrescarTabla(true);
            }else{
                mostrarAlerta = Herramientas.creadorDeAlerta("Error", "No fue posible completar la actualización, "
                    + "intente más tarde", Alert.AlertType.ERROR);
                mostrarAlerta.showAndWait();
            }
        }else{
            mostrarAlerta = Herramientas.creadorDeAlerta("Campos incorrectos o vacíos", 
               "Verifique su información", Alert.AlertType.ERROR);
            mostrarAlerta.showAndWait();
        }
        
    }
    
    private void actualizarCatalogoLgca(String nombre, String fecha,
        String grado, String adscripcion, String unidadAdscripcion, String estatus, int idCatalogoLgca){
        Connection conn = ConectarBD.abrirConexionMySQL();
        if(conn != null){
            try{
                String consulta = "update lgca set nombre = ?, fecha = ?, grado = ?, adscripcion = ?,"
                    + " unidadAdscripcion = ?, estatus = ? where idLGCA = ?";
                PreparedStatement declaracion = conn.prepareStatement(consulta);
                declaracion.setString(1, nombre);
                declaracion.setString(2, fecha);
                declaracion.setString(3, grado);
                declaracion.setString(4, adscripcion);
                declaracion.setString(5, unidadAdscripcion);
                declaracion.setString(6, estatus);
                declaracion.setInt(7, idCatalogoLgca);
                int resultado = declaracion.executeUpdate();
                if(resultado == 0){
                    actualizacionExitosa = false;
                }
            }catch(SQLException ex){
                actualizacionExitosa = false;
                mostrarAlerta = Herramientas.creadorDeAlerta("Error de consulta", "No fue posible acceder a la base de datos "
                    + "en este momento, intente más tarde", Alert.AlertType.ERROR);
                mostrarAlerta.showAndWait();
                Herramientas.cerrarPantalla(tfEstatus);
            }
        }else{
            actualizacionExitosa = false;
            mostrarAlerta = Herramientas.creadorDeAlerta("Error de conexión", "No fue posible conectar con la base de datos"
                + "en este momento, intente más tarde", Alert.AlertType.ERROR);
            mostrarAlerta.showAndWait();
            Herramientas.cerrarPantalla(tfEstatus);
        }
    }

}
