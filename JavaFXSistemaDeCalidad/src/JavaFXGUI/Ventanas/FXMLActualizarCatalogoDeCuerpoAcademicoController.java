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
import javafx.scene.input.KeyEvent;
import pojos.CatalogoDeCuerpoAcademico;
import pojos.CatalogoLGCA;
import pojos.Docente;
import pojos.RepresentanteDeCuerpoAcademico;
import util.Herramientas;
import validaciones.Validaciones;

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
    
    int idCuerpoAcademico = 0;
    int idResponsable = 0;
    int idLgca = 0;
   
    private CatalogoDeCuerpoAcademico editarCatalogo;
    private ObservableList<RepresentanteDeCuerpoAcademico> responsables;
    private ObservableList<CatalogoLGCA> catalogosLgca;
    private ObservableList<Docente> integrantesDeCuerpoAcademico;
    
    String nombreAuxiliar;
    String estatusAuxiliar;
    String fechaAuxiliar;
    String descripcionAuxiliar;
    String misionAuxiliar;
    
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
                    idResponsable = newValue.getIdentificacion();
                }
            }  
        });
        
        catalogosLgca = FXCollections.observableArrayList();
        cargarCatalogosLgca();
        
        cbLgca.valueProperty().addListener(new ChangeListener<CatalogoLGCA>(){
            @Override
            public void changed(ObservableValue<? extends CatalogoLGCA> observable, CatalogoLGCA oldValue, CatalogoLGCA newValue) {
                if(newValue != null){
                    idLgca = newValue.getIdentificacion();
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
            actualizacionExitosa = false;
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
            actualizacionExitosa = false;
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
        idCuerpoAcademico = editarCatalogo.getIdentificacion();
        tfNombre.setText(editarCatalogo.getNombre());
        tfFecha.setText(editarCatalogo.getFecha().toString());
        tfEstatus.setText(editarCatalogo.getEstatus());
        taDescripcion.setText(editarCatalogo.getDescripcion());
        taMision.setText(editarCatalogo.getMision());
        
        obtenerIdResponsableSeleccionado(idCuerpoAcademico);
        int posicionSelecciodaDelComboBoxResponsables = obtenerPosicionDeResponsableSeleccionado(idResponsable);
        cbResponsable.getSelectionModel().select(posicionSelecciodaDelComboBoxResponsables);
        
        idLgca = editarCatalogo.getIdLgca();
        int posicionDelComboBoxLgcaSeleccionado = obtenerPosicionDeLgcaSeleccionado(idLgca);
        cbLgca.getSelectionModel().select(posicionDelComboBoxLgcaSeleccionado);
        
        obtenerIntegrantesDeCuerpoAcademico(idCuerpoAcademico);
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
                    idResponsable = resultado.getInt("idAcademico");
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
            actualizacionExitosa = false;
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


    @FXML
    private void cancelar(ActionEvent event) {
        Herramientas.cerrarPantalla(tfNombre);
    }
    
    @FXML
    private void clicActualizar(ActionEvent event) {
        tfNombre.setStyle("-fx-border-color: ;");
        tfEstatus.setStyle(("-fx-border-color: ;"));
        tfFecha.setStyle("-fx-border-color: ;");
        taDescripcion.setStyle("-fx-border-color: ;");
        taMision.setStyle("-fx-border-color: ;");
        
        boolean esCorrecto = true;
        Validaciones datoAValidar = new Validaciones();
        
        nombreAuxiliar = tfNombre.getText();
        estatusAuxiliar = tfEstatus.getText();
        fechaAuxiliar = tfFecha.getText();
        descripcionAuxiliar = taDescripcion.getText();
        misionAuxiliar = taMision.getText();
        
        if((nombreAuxiliar.isEmpty()) || (!datoAValidar.validarTextos(nombreAuxiliar))){
            esCorrecto = false;
            tfNombre.setStyle("-fx-border-color: red;");
        }
        if((estatusAuxiliar.isEmpty()) || (!datoAValidar.validarEstatus(estatusAuxiliar))){
            esCorrecto = false;
            tfEstatus.setStyle(("-fx-border-color: red;"));
        }
        if((fechaAuxiliar.isEmpty()) || (!datoAValidar.validarFecha(fechaAuxiliar))){
            esCorrecto = false;
            tfFecha.setStyle("-fx-border-color: red;");
        }
        if((descripcionAuxiliar.isEmpty()) || (!datoAValidar.validarTextos(descripcionAuxiliar))){
            esCorrecto = false;
            taDescripcion.setStyle("-fx-border-color: red;");
        }
        if((misionAuxiliar.isEmpty()) || (!datoAValidar.validarTextos(misionAuxiliar))){
            esCorrecto = false;
            taMision.setStyle("-fx-border-color: red;");
        }
        
        if(esCorrecto){
            if(idLgca>0){
                actualizarCuerpoAcademico(nombreAuxiliar, fechaAuxiliar, descripcionAuxiliar, 
                        misionAuxiliar, estatusAuxiliar,  idLgca, idCuerpoAcademico);
            }else{
                actualizacionExitosa = false;
            }
            
            if(actualizacionExitosa){
                mostrarAlerta = Herramientas.creadorDeAlerta("Mensaje", "Actualizacion exitosa", Alert.AlertType.INFORMATION);
                mostrarAlerta.showAndWait();
                Herramientas.cerrarPantalla(tfNombre);
                notificacion.refrescarTabla(true);
            }else{
                mostrarAlerta = Herramientas.creadorDeAlerta("Error", "No fue posible completar el registro, "
                    + "intente más tarde", Alert.AlertType.ERROR);
                mostrarAlerta.showAndWait();    
            } 
        }else{
            mostrarAlerta = Herramientas.creadorDeAlerta("Campos incorrectos o vacíos", 
                "Verifique su información", Alert.AlertType.ERROR);
            mostrarAlerta.showAndWait();
        }
    }
    
    private void actualizarCuerpoAcademico(String nombre, String fecha, String descripcion, String mision, 
        String estatus, int idLgca, int idCuerpoAcademico){
        Connection conn = ConectarBD.abrirConexionMySQL();
        if(conn != null){
            try{
                String consulta = "update cuerpoAcademico set nombre = ?, fecha = ?, descripcion = ?,  mision = ?, "
                    + "estatus = ?, idLgca = ? where idCuerpoAcademico = ?";
                    PreparedStatement declaracion = conn.prepareStatement(consulta);
                    declaracion.setString(1, nombre);
                    declaracion.setString(2, fecha);
                    declaracion.setString(3, descripcion);
                    declaracion.setString(4, mision);
                    declaracion.setString(5, estatus);
                    declaracion.setInt(6, idLgca);
                    declaracion.setInt(7, idCuerpoAcademico);
                    int resultado = declaracion.executeUpdate();
                    if(resultado == 0){
                        actualizacionExitosa = false;
                    }else{
                        vincularUnNuevoRepresentante(idResponsable, idCuerpoAcademico);
                    }
            }catch(SQLException ex){
                actualizacionExitosa = false;
                System.out.println(ex.getMessage());
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
    
    private void vincularUnNuevoRepresentante(int idRepresentanteNuevo, int idCuerpoAcademico) throws SQLException{
        Connection conn = ConectarBD.abrirConexionMySQL();
        if(conn != null){
                String consulta = "update cuerpoAcademicoIntegrantes set idAcademico = ? where idCuerpoAcademico = ? "
                    + "and rol = 'Representante'";
                    PreparedStatement declaracion = conn.prepareStatement(consulta);
                    declaracion.setInt(1, idRepresentanteNuevo);
                    declaracion.setInt(2, idCuerpoAcademico);
                    int resultado = declaracion.executeUpdate();
                    if(resultado == 0){
                        actualizacionExitosa = false;
                    }
        }else{
            actualizacionExitosa = false;
            mostrarAlerta = Herramientas.creadorDeAlerta("Error de conexión", "No fue posible conectar con la base de datos"
                + "en este momento, intente más tarde", Alert.AlertType.ERROR);
            mostrarAlerta.showAndWait();
            Herramientas.cerrarPantalla(tfEstatus);
        }
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
    
    

    @FXML
    private void contarCaracteresDescripcion(KeyEvent event) {
        Herramientas.contadorDeCaracteres(taDescripcion, event);
    }

    @FXML
    private void contarCaracteresMision(KeyEvent event) {
        Herramientas.contadorDeCaracteres(taMision, event); 
    }
}
