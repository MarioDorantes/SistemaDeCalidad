/*
 *Autor: Brandon Trujillo
 *fechaCreación: 28/11/2020
 */
package JavaFXGUI.Ventanas;

import conexionBD.ConectarBD;
import interfaz.NotificaCambios;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import pojos.CatalogoDeCuerpoAcademico;
import pojos.CatalogoLGCA;
import pojos.Docente;
import pojos.RepresentanteDeCuerpoAcademico;
import util.Herramientas;

public class FXMLActualizarCatalogoDeCuerpoAcademicoController implements Initializable {
    
    @FXML
    private TableView<Docente> tvDocentesIntegrantes;
    @FXML
    private TableColumn tcNombre;
    @FXML
    private TableColumn tcCorreo;
    @FXML
    private TableColumn tcNumeroDePersonal;
    @FXML
    private TableColumn tcTelefono;
    @FXML
    private TableColumn tcGradoAcademico;
    @FXML
    private TextField tfNombre;
    @FXML
    private TextField tfFecha;
    @FXML
    private TextField tfEstatus;
    @FXML
    private TextArea taDescripcion;
    @FXML
    private TextArea taMision;
    @FXML
    private ComboBox<RepresentanteDeCuerpoAcademico> cbResponsable;
    @FXML
    private ComboBox<CatalogoLGCA> cbLgca;
    
    Alert mostrarAlerta;
    NotificaCambios notificacion;
    boolean actualizacionExitosa = true;
    
    int idCuerpoAcademicoSeleccionado = 0;
    int idResponsableSeleccionado = 0;
    int idNuevoResponsableSeleccionado = 0;
    int idLgcaSeleccionado = 0;
    int idNuevoLgcaSeleccionado = 0;
    
    private CatalogoDeCuerpoAcademico editarCatalogo;
    private ObservableList<RepresentanteDeCuerpoAcademico> responsables;
    private ObservableList<CatalogoLGCA> catalogosLgca;
    private ObservableList<Docente> integrantesDeCuerpoAcademico;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        integrantesDeCuerpoAcademico = FXCollections.observableArrayList();
        tcNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        tcNumeroDePersonal.setCellValueFactory(new PropertyValueFactory("numeroPersonal"));
        tcTelefono.setCellValueFactory(new PropertyValueFactory("telefono"));
        tcGradoAcademico.setCellValueFactory(new PropertyValueFactory("gradoAcademico"));
        tcCorreo.setCellValueFactory(new PropertyValueFactory("correo"));
        
        responsables = FXCollections.observableArrayList();
        cargarResponsables();
        
        cbResponsable.valueProperty().addListener(new ChangeListener<RepresentanteDeCuerpoAcademico>(){
            @Override
            public void changed(ObservableValue<? extends RepresentanteDeCuerpoAcademico> observable, RepresentanteDeCuerpoAcademico oldValue, RepresentanteDeCuerpoAcademico newValue) {
                if(newValue != null){
                    idNuevoResponsableSeleccionado = newValue.getIdentificacion();
                }
            }  
        });
        
        catalogosLgca = FXCollections.observableArrayList();
        cargarCatalogosLgca();
        
        cbLgca.valueProperty().addListener(new ChangeListener<CatalogoLGCA>(){
            @Override
            public void changed(ObservableValue<? extends CatalogoLGCA> observable, CatalogoLGCA oldValue, CatalogoLGCA newValue) {
                if(newValue != null){
                    idNuevoLgcaSeleccionado = newValue.getIdentificacion();
                }
            }
        });     
    }    
    
    private void cargarResponsables(){
        Connection conn = ConectarBD.abrirConexionMySQL();
        if(conn != null){
            try{
                String consulta = "select idAcademico, "
                    + "nombre from academico inner join rol on tipoRol = 'Representante' and academico.numeroPersonal = rol.numeroPersonal";
                PreparedStatement declaracion = conn.prepareStatement(consulta);
                ResultSet resultado = declaracion.executeQuery();
                while(resultado.next()){
                    RepresentanteDeCuerpoAcademico representantesObtenidos = new RepresentanteDeCuerpoAcademico();
                    representantesObtenidos.setIdentificacion(resultado.getInt("idAcademico"));
                    representantesObtenidos.setNombre(resultado.getString("nombre"));
                    responsables.add(representantesObtenidos);
                }
                cbResponsable.setItems(responsables);
                conn.close();
            }catch(SQLException ex){
                actualizacionExitosa = false;
                mostrarAlerta = Herramientas.creadorDeAlerta("Error de consulta", "No fue posible acceder a la base de datos "
                    + "en este momento, intente más tarde", Alert.AlertType.ERROR);
                mostrarAlerta.showAndWait();
                Herramientas.cerrarPantalla(tfEstatus);
            }
        }else{
            mostrarAlerta = Herramientas.creadorDeAlerta("Error de conexión", "No fue posible conectar con la base de datos"
                + "en este momento, intente más tarde", Alert.AlertType.ERROR);
            mostrarAlerta.showAndWait();
            Herramientas.cerrarPantalla(tfEstatus);
        }
    }

    private void cargarCatalogosLgca(){
         Connection conn = ConectarBD.abrirConexionMySQL();
        if(conn != null){
            try{
                String consulta = "select idLgca, nombre from lgca";
                PreparedStatement declaracion = conn.prepareStatement(consulta);
                ResultSet resultado = declaracion.executeQuery();
                while(resultado.next()){
                    CatalogoLGCA catalogosLgcaObtenidos = new CatalogoLGCA();
                    catalogosLgcaObtenidos.setIdentificacion(resultado.getInt("idLGCA"));
                    catalogosLgcaObtenidos.setNombre(resultado.getString("nombre"));
                    catalogosLgca.add(catalogosLgcaObtenidos);
                }
                cbLgca.setItems(catalogosLgca);
                conn.close();
            }catch(SQLException ex){
                actualizacionExitosa = false;
                mostrarAlerta = Herramientas.creadorDeAlerta("Error de consulta", "No fue posible acceder a la base de datos "
                    + "en este momento, intente más tarde", Alert.AlertType.ERROR);
                mostrarAlerta.showAndWait();
                Herramientas.cerrarPantalla(tfEstatus);
            }
        }else{
            mostrarAlerta = Herramientas.creadorDeAlerta("Error de conexión", "No fue posible conectar con la base de datos"
                + "en este momento, intente más tarde", Alert.AlertType.ERROR);
            mostrarAlerta.showAndWait();
            Herramientas.cerrarPantalla(tfEstatus);
        }
    }
    
    public void inicializaCampos(NotificaCambios notificacion, CatalogoDeCuerpoAcademico editarCatalogo){
        this.notificacion = notificacion;
        this.editarCatalogo = editarCatalogo;
        
        obtenerCatalogoSeleccionado();
    }
    
    private void obtenerCatalogoSeleccionado(){
        idCuerpoAcademicoSeleccionado = editarCatalogo.getIdentificacion();
        tfNombre.setText(editarCatalogo.getNombre());
        tfFecha.setText(editarCatalogo.getFecha().toString());
        tfEstatus.setText(editarCatalogo.getEstatus());
        taDescripcion.setText(editarCatalogo.getDescripcion());
        taMision.setText(editarCatalogo.getMision());
        
        obtenerIdResponsableSeleccionado(idCuerpoAcademicoSeleccionado);
        int posicionSelecciodaDelComboBoxResponsables = obtenerPosicionDeResponsableSeleccionado(idResponsableSeleccionado);
        cbResponsable.getSelectionModel().select(posicionSelecciodaDelComboBoxResponsables);
        
        idLgcaSeleccionado = editarCatalogo.getIdLgca();
        int posicionDelComboBoxLgcaSeleccionado = obtenerPosicionDeLgcaSeleccionado(idLgcaSeleccionado);
        cbLgca.getSelectionModel().select(posicionDelComboBoxLgcaSeleccionado);
        
        System.out.println(idCuerpoAcademicoSeleccionado);
        obtenerIntegrantesDeCuerpoAcademico(idCuerpoAcademicoSeleccionado);
    }
    
    private void obtenerIdResponsableSeleccionado(int idCuerpoAcademicoSeleccionado){
        Connection conn = ConectarBD.abrirConexionMySQL();
        if(conn != null){
            try {
                String consulta = "select idAcademico from cuerpoAcademicoIntegrantes where idCuerpoAcademico = ? and rol = 'Representante'";
                PreparedStatement declaracion = conn.prepareStatement(consulta);
                declaracion.setInt(1, idCuerpoAcademicoSeleccionado);
                ResultSet resultado = declaracion.executeQuery();
                if(resultado.next()){
                    idResponsableSeleccionado = resultado.getInt("idAcademico");
                }
                conn.close();
            } catch (SQLException ex) {
                actualizacionExitosa = false;
                mostrarAlerta = Herramientas.creadorDeAlerta("Error de consulta", "No fue posible acceder a la base de datos "
                    + "en este momento, intente más tarde", Alert.AlertType.ERROR);
                mostrarAlerta.showAndWait();
                Herramientas.cerrarPantalla(tfEstatus);
            } 
        }else{
            mostrarAlerta = Herramientas.creadorDeAlerta("Error de conexión", "No fue posible conectar con la base de datos"
                + "en este momento, intente más tarde", Alert.AlertType.ERROR);
            mostrarAlerta.showAndWait();
            Herramientas.cerrarPantalla(tfEstatus);
        }
    }
    
    private void obtenerIntegrantesDeCuerpoAcademico(int idCuerpoAcademicoSeleccionado){
        Connection conn = ConectarBD.abrirConexionMySQL();
        if(conn != null){
            try {
                String consulta = "select academico.nombre as nombre, "
                    + "numeroPersonal, telefono, gradoAcademico, correo from academico inner join usuario "
                    + "on academico.idAcademico = usuario.idAcademico inner join cuerpoAcademico "
                    + "inner join cuerpoAcademicoIntegrantes on academico.idAcademico = cuerpoAcademicoIntegrantes.idAcademico "
                    + "and cuerpoAcademicoIntegrantes.idCuerpoAcademico = ? " 
                    + "and cuerpoAcademico.idCuerpoAcademico = cuerpoAcademicoIntegrantes.idCuerpoAcademico "
                    + "and cuerpoAcademicoIntegrantes.rol = 'Docente';";
                PreparedStatement declaracion = conn.prepareStatement(consulta);
                declaracion.setInt(1, idCuerpoAcademicoSeleccionado);
                ResultSet resultado = declaracion.executeQuery();
                while(resultado.next()){
                    Docente integrantesObtenidos = new Docente();
                    integrantesObtenidos.setNombre(resultado.getString("nombre"));
                    integrantesObtenidos.setNumeroPersonal(resultado.getString("numeroPersonal"));
                    integrantesObtenidos.setTelefono(resultado.getString("telefono"));
                    integrantesObtenidos.setGradoAcademico(resultado.getString("gradoAcademico"));
                    integrantesObtenidos.setCorreo(resultado.getString("correo"));
                    integrantesDeCuerpoAcademico.add(integrantesObtenidos);
                }
                tvDocentesIntegrantes.setItems(integrantesDeCuerpoAcademico);
                conn.close();
            } catch (SQLException ex) {
                actualizacionExitosa = false;
                System.out.println(ex.getMessage());
                mostrarAlerta = Herramientas.creadorDeAlerta("Error de consulta", "No fue posible acceder a la base de datos "
                    + "en este momento, intente más tarde", Alert.AlertType.ERROR);
                mostrarAlerta.showAndWait();
                Herramientas.cerrarPantalla(tfEstatus);
            } 
        }else{
            mostrarAlerta = Herramientas.creadorDeAlerta("Error de conexión", "No fue posible conectar con la base de datos"
                + "en este momento, intente más tarde", Alert.AlertType.ERROR);
            mostrarAlerta.showAndWait();
            Herramientas.cerrarPantalla(tfEstatus);
        }
    }

    @FXML
    private void irAVisualizarDocentes(ActionEvent event) {
    }

    @FXML
    private void cancelar(ActionEvent event) {
        Herramientas.cerrarPantalla(tfNombre);
    }
    
    private int obtenerPosicionDeResponsableSeleccionado(int idRepresentanteSeleccionado){
        int value = 0;
        if(responsables.size() > 0){
            for (int i = 0; i < responsables.size(); i++) {
                RepresentanteDeCuerpoAcademico get = responsables.get(i);
                if(get.getIdentificacion() == idRepresentanteSeleccionado){
                    return i;
                }
            }
        }
        return value;
    }
    
    private int obtenerPosicionDeLgcaSeleccionado(int idLgcaSeleccionado){
        int value = 0;
        if(catalogosLgca.size() > 0){
            for (int i = 0; i < catalogosLgca.size(); i++) {
                CatalogoLGCA get = catalogosLgca.get(i);
                if(get.getIdentificacion() == idLgcaSeleccionado){
                    return i;
                }
            }
        }
        return value;
    }
}
