/*
Autor: Brandon trujillo
fechaDeCreación: 28/11/2020
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
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import pojos.CatalogoDeCuerpoAcademico;
import pojos.CatalogoLGCA;
import pojos.RepresentanteDeCuerpoAcademico;
import util.Herramientas;

public class FXMLRegistrarCatalogoDeCuerpoAcademicoController implements Initializable {

    @FXML
    private TextField tfNombreCatalogo;
    @FXML
    private TextField tfFechaRegistro;
    @FXML
    private TextField tfEstatus;
    @FXML
    private ComboBox<RepresentanteDeCuerpoAcademico> cbRepresentante;
    @FXML
    private ComboBox<CatalogoLGCA> cbLgca;
    @FXML
    private TextArea taMision;
    @FXML
    private TextArea taDescripcion;

    Alert mostrarAlerta;
    boolean registroExitoso = true;
    NotificaCambios notificacion;
    
    int idRepresentante = 0;
    int idCuerpoAcademico = 0;
    int idLgca = 0;
    
    private ObservableList<RepresentanteDeCuerpoAcademico> representantes;
    private ObservableList<CatalogoLGCA> catalogosLgca;
    
    String nombreAuxiliar;
    String fechaAuxiliar;
    String estatusAuxiliar;
    String misionAuxiliar;
    String descripcionAuxiliar;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        representantes = FXCollections.observableArrayList(); 
        catalogosLgca = FXCollections.observableArrayList();
        
        cargarRepresentantes();
        cargarLgca();
        
        cbRepresentante.valueProperty().addListener(new ChangeListener<RepresentanteDeCuerpoAcademico>(){
            @Override
            public void changed(ObservableValue<? extends RepresentanteDeCuerpoAcademico> observable, RepresentanteDeCuerpoAcademico oldValue, RepresentanteDeCuerpoAcademico newValue) {
               if(newValue != null){
                    idRepresentante = newValue.getIdentificacion();
               }
            }
        });
        
        cbLgca.valueProperty().addListener(new ChangeListener<CatalogoLGCA>(){
            @Override
            public void changed(ObservableValue<? extends CatalogoLGCA> observable, CatalogoLGCA oldValue, CatalogoLGCA newValue) {
                if(newValue != null){
                    idLgca = newValue.getIdentificacion();
                }
            }
            
        });
    }   
    
    private void cargarRepresentantes(){
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
                    representantes.add(representantesObtenidos);
                }
                cbRepresentante.setItems(representantes);
                conn.close();
            }catch(SQLException ex){
                registroExitoso = false;
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
    
    private void cargarLgca(){
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
                registroExitoso = false;
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

    public void inicializaCampos(NotificaCambios notificacion){
        this.notificacion = notificacion;
    }
    
    @FXML
    private void clicRegistrar(ActionEvent event) {
        tfNombreCatalogo.setStyle("-fx-border-color: ;");
        tfFechaRegistro.setStyle("-fx-border-color: ;");
        tfEstatus.setStyle("-fx-border-color: ;");
        cbRepresentante.setStyle("-fx-border-color: ;");
        cbLgca.setStyle("-fx-border-color: ;");
        taMision.setStyle("-fx-border-color: ;");
        taDescripcion.setStyle("-fx-border-color: ;");
        
        nombreAuxiliar = tfNombreCatalogo.getText();
        fechaAuxiliar = tfFechaRegistro.getText();
        estatusAuxiliar = tfEstatus.getText();
        
        int posicionDelComboBoxRepresentante = cbRepresentante.getSelectionModel().getSelectedIndex();
        int posicionDeComboBoxLgca = cbLgca.getSelectionModel().getSelectedIndex();
        
        misionAuxiliar = taMision.getText();
        descripcionAuxiliar = taDescripcion.getText();
        
        boolean esCorrecto = true;
        
        if(nombreAuxiliar.isEmpty()){
            tfNombreCatalogo.setStyle("-fx-border-color: red;");
            esCorrecto = false;
        }
        if(fechaAuxiliar.isEmpty()){
            tfFechaRegistro.setStyle("-fx-border-color: red;");
            esCorrecto = false;
        }
        if(estatusAuxiliar.isEmpty()){
            tfEstatus.setStyle("-fx-border-color: red;");
            esCorrecto = false;
        }
        if(posicionDelComboBoxRepresentante < 0){
            cbRepresentante.setStyle("-fx-border-color: red;");
            esCorrecto = false;
        }
        if(posicionDeComboBoxLgca < 0){
            cbLgca.setStyle("-fx-border-color: ;");
            esCorrecto = false;
        }
        if(misionAuxiliar.isEmpty()){
            taMision.setStyle("-fx-border-color: red;");
            esCorrecto = false;
        }
        if(descripcionAuxiliar.isEmpty()){
            taDescripcion.setStyle("-fx-border-color: red;");
            esCorrecto = false;
        }
        
        if(esCorrecto){
            registrarCuerpoAcademico(nombreAuxiliar, fechaAuxiliar, descripcionAuxiliar, misionAuxiliar, estatusAuxiliar, idLgca);
        }else{
            mostrarAlerta = Herramientas.creadorDeAlerta("Campos incorrectos o vacíos", 
                "Verifique su información", Alert.AlertType.ERROR);
            mostrarAlerta.showAndWait();
        }
    }
    
    private void registrarCuerpoAcademico(String nombre, String fecha, String descripcion, 
        String mision, String estatus, int idLGCA){
        Connection conn = ConectarBD.abrirConexionMySQL();
        if(conn != null){
            try{
                String consulta = "insert into cuerpoAcademico(nombre, fecha, descripcion, mision, estatus, idLgca) values"
                    + "(?, ?, ?, ?, ?, ?)";
                PreparedStatement declaracion = conn.prepareStatement(consulta);
                declaracion.setString(1, nombre);
                declaracion.setString(2, fecha);
                declaracion.setString(3, descripcion);
                declaracion.setString(4, mision);
                declaracion.setString(5, estatus);
                declaracion.setInt(6, idLGCA);
                int resultado = declaracion.executeUpdate();
                if(resultado == 0){
                    registroExitoso = false;
                }else{
                    obtenerIdCuerpoAcademicoRegistrado();
                }
            }catch(SQLException ex){
                registroExitoso = false;
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
    
    private void obtenerIdCuerpoAcademicoRegistrado() throws SQLException{
        Connection conn = ConectarBD.abrirConexionMySQL();
        if(conn != null){
                String consulta = "select max(idCuerpoAcademico) as idCuerpoAcademico from cuerpoAcademico";
                PreparedStatement declaracion = conn.prepareStatement(consulta);
                ResultSet resultado = declaracion.executeQuery();
                if(resultado.next()){
                    idCuerpoAcademico = resultado.getInt("idCuerpoAcademico");
                    if(idCuerpoAcademico > 0 && idRepresentante > 0){
                    vincularRepresentanteACuerpoAcademico(idCuerpoAcademico, idRepresentante);
                    }else{
                        registroExitoso = false;
                    }
                }else{
                    registroExitoso = false;
                }
                conn.close();
        }else{
            mostrarAlerta = Herramientas.creadorDeAlerta("Error de conexión", "No fue posible conectar con la base de datos"
                + "en este momento, intente más tarde", Alert.AlertType.ERROR);
            mostrarAlerta.showAndWait();
            Herramientas.cerrarPantalla(tfEstatus);
        }
    }
    
    private void vincularRepresentanteACuerpoAcademico(int idCuerpoAcademico, int idRepresentante)throws SQLException{
        Connection conn = ConectarBD.abrirConexionMySQL();
        if(conn != null){
                String consulta = "insert into cuerpoAcademicoIntegrantes(idCuerpoAcademico, idAcademico, rol) values"
                        + "(?, ?, 'Representante')";
                PreparedStatement declaracion = conn.prepareStatement(consulta);
                declaracion.setInt(1, idCuerpoAcademico);
                declaracion.setInt(2, idRepresentante);
                int resultado = declaracion.executeUpdate();
                if(resultado == 0){
                    registroExitoso = false;
                }else{
                    if(registroExitoso){
                        mostrarAlerta = Herramientas.creadorDeAlerta("Mensaje", "Registro exitoso", Alert.AlertType.INFORMATION);
                        mostrarAlerta.showAndWait();
                        Herramientas.cerrarPantalla(tfEstatus);
                        notificacion.refrescarTabla(true);
                    }else{
                        mostrarAlerta = Herramientas.creadorDeAlerta("Error", "No fue posible completar el registro, "
                            + "intente más tarde", Alert.AlertType.ERROR);
                        mostrarAlerta.showAndWait();
                    }
                }
        }else{
            mostrarAlerta = Herramientas.creadorDeAlerta("Error de conexión", "No fue posible conectar con la base de datos"
                + "en este momento, intente más tarde", Alert.AlertType.ERROR);
            mostrarAlerta.showAndWait();
            Herramientas.cerrarPantalla(tfEstatus);
        }
    }

    @FXML
    private void cancelar(ActionEvent event) {
        Herramientas.cerrarPantalla(tfEstatus);
    }
    
}
