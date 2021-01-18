/*
 * Autor: Brandon Trujillo
 * fecha: 1/12/2020
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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import pojos.Docente;
import pojos.Estudiante;
import util.Herramientas;
import validaciones.Validaciones;

public class FXMLRegistrarTrabajoController implements Initializable {
    
    @FXML
    private TextField tfNombre;
    @FXML
    private TextField tfFecha;
    @FXML
    private TextArea taDescripcion;
    @FXML
    private ComboBox<Estudiante> cbEstudiantes;
    @FXML
    private ComboBox<Docente> cbDirector;
    @FXML
    private ComboBox<Docente> cbCoDirector;
    @FXML
    private ComboBox<Docente> cbSinodal;
    @FXML
    private TextField tfEstatus;
    
    Alert mostrarAlerta;
    private NotificaCambios notificacion;
    boolean registroExitoso = true;
    
    int idEstudiante = 0;
    int idDirector = 0;
    int idCoDirector = 0;
    int idSinodal = 0;
    
    String nombreAuxiliar;
    String fechaAuxiliar;
    String estatusAuxiliar;
    String descripcionAuxiliar;
    
    private ObservableList<Estudiante> estudiantes;
    private ObservableList<Docente> directores;
    private ObservableList<Docente> coDirectores;
    private ObservableList<Docente> sinodales;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        directores = FXCollections.observableArrayList();
        coDirectores = FXCollections.observableArrayList();
        sinodales = FXCollections.observableArrayList();
        estudiantes = FXCollections.observableArrayList();
        
        cargarEstudiantes();
        cbEstudiantes.valueProperty().addListener(new ChangeListener<Estudiante>(){
            @Override
            public void changed(ObservableValue<? extends Estudiante> observable, Estudiante oldValue, Estudiante newValue) {
                if(newValue != null){
                    idEstudiante = newValue.getIdEstudiante();
                }
            }
            
        });
        
        cargarDirectores();
        cbDirector.valueProperty().addListener(new ChangeListener<Docente>(){
            @Override
            public void changed(ObservableValue<? extends Docente> observable, Docente oldValue, Docente newValue) {
                if(newValue != null){
                    idDirector = newValue.getIdentificacion();
                }
            }   
        });
        
        cargarCoDirectores();
        cbCoDirector.valueProperty().addListener(new ChangeListener<Docente>(){
            @Override
            public void changed(ObservableValue<? extends Docente> observable, Docente oldValue, Docente newValue) {
                if(newValue != null){
                    idCoDirector = newValue.getIdentificacion();
                }
            }
        });
        
        cargarSinodales();
        cbSinodal.valueProperty().addListener(new ChangeListener<Docente>(){
            @Override
            public void changed(ObservableValue<? extends Docente> observable, Docente oldValue, Docente newValue) {
                if(newValue != null){
                    idSinodal = newValue.getIdentificacion();
                }
            }
            
        });
    } 
    
    public void inicializaCampos(NotificaCambios notificacion){
        this.notificacion = notificacion;
    }
    
    private void cargarEstudiantes(){
        Connection conn = ConectarBD.abrirConexionMySQL();
        if(conn != null){
            try {
                String consulta = "select * from estudiante where estatus = 'Activo'";
                PreparedStatement declaracion = conn.prepareStatement(consulta);
                ResultSet resultado = declaracion.executeQuery();
                while(resultado.next()){
                    Estudiante estudiantesObtenidos = new Estudiante();
                    estudiantesObtenidos.setIdEstudiante(resultado.getInt("idEstudiante"));
                    estudiantesObtenidos.setNombre(resultado.getString("nombre"));
                    estudiantes.add(estudiantesObtenidos);
                }
                cbEstudiantes.setItems(estudiantes);
                conn.close();
            } catch (SQLException ex) {
                registroExitoso = false;
                mostrarAlerta = Herramientas.creadorDeAlerta("Error de consulta", "No fue posible acceder a la base de datos "
                    + "en este momento, intente más tarde", Alert.AlertType.ERROR);
                mostrarAlerta.showAndWait();
                Herramientas.cerrarPantalla(tfEstatus);
            } 
        }else{
            registroExitoso = false;
            mostrarAlerta = Herramientas.creadorDeAlerta("Error de conexión", "No fue posible conectar con la base de datos"
                + "en este momento, intente más tarde", Alert.AlertType.ERROR);
            mostrarAlerta.showAndWait();
            Herramientas.cerrarPantalla(tfEstatus);
        }
    }
    
    private void cargarDirectores(){
        Connection conn = ConectarBD.abrirConexionMySQL();
        if(conn != null){
            try {
                String consulta = "select idAcademico, nombre from academico "
                    + "inner join rol on academico.numeroPersonal = rol.numeroPersonal " 
                    + "and tipoRol = 'Docente'";
                PreparedStatement declaracion = conn.prepareStatement(consulta);
                ResultSet resultado = declaracion.executeQuery();
                while(resultado.next()){
                    Docente docentesObtenidos = new Docente();
                    docentesObtenidos.setIdentificacion(resultado.getInt("idAcademico"));
                    docentesObtenidos.setNombre(resultado.getString("nombre"));
                    directores.add(docentesObtenidos);
                }
                cbDirector.setItems(directores);
                conn.close();
            } catch (SQLException ex) {
                registroExitoso = false;
                System.out.println(ex.getMessage());
                mostrarAlerta = Herramientas.creadorDeAlerta("Error de consulta", "No fue posible acceder a la base de datos "
                    + "en este momento, intente más tarde", Alert.AlertType.ERROR);
                mostrarAlerta.showAndWait();
                Herramientas.cerrarPantalla(tfEstatus);
            } 
        }else{
            registroExitoso = false;
            mostrarAlerta = Herramientas.creadorDeAlerta("Error de conexión", "No fue posible conectar con la base de datos"
                + "en este momento, intente más tarde", Alert.AlertType.ERROR);
            mostrarAlerta.showAndWait();
            Herramientas.cerrarPantalla(tfEstatus);
        }
    }
    
    private void cargarCoDirectores(){
        Connection conn = ConectarBD.abrirConexionMySQL();
        if(conn != null){
            try {
                String consulta = "select idAcademico, nombre from academico "
                    + "inner join rol on academico.numeroPersonal = rol.numeroPersonal " 
                    + "and tipoRol = 'Docente'";
                PreparedStatement declaracion = conn.prepareStatement(consulta);
                ResultSet resultado = declaracion.executeQuery();
                while(resultado.next()){
                    Docente docentesObtenidos = new Docente();
                    docentesObtenidos.setIdentificacion(resultado.getInt("idAcademico"));
                    docentesObtenidos.setNombre(resultado.getString("nombre"));
                    coDirectores.add(docentesObtenidos);
                }
                cbCoDirector.setItems(coDirectores);
                conn.close();
            } catch (SQLException ex) {
                registroExitoso = false;
                System.out.println(ex.getMessage());
                mostrarAlerta = Herramientas.creadorDeAlerta("Error de consulta", "No fue posible acceder a la base de datos "
                    + "en este momento, intente más tarde", Alert.AlertType.ERROR);
                mostrarAlerta.showAndWait();
                Herramientas.cerrarPantalla(tfEstatus);
            } 
        }else{
            registroExitoso = false;
            mostrarAlerta = Herramientas.creadorDeAlerta("Error de conexión", "No fue posible conectar con la base de datos"
                + "en este momento, intente más tarde", Alert.AlertType.ERROR);
            mostrarAlerta.showAndWait();
            Herramientas.cerrarPantalla(tfEstatus);
        }
    }
    
    private void cargarSinodales(){
        Connection conn = ConectarBD.abrirConexionMySQL();
        if(conn != null){
            try {
                String consulta = "select idAcademico, nombre from academico "
                    + "inner join rol on academico.numeroPersonal = rol.numeroPersonal " 
                    + "and tipoRol = 'Docente'";
                PreparedStatement declaracion = conn.prepareStatement(consulta);
                ResultSet resultado = declaracion.executeQuery();
                while(resultado.next()){
                    Docente docentesObtenidos = new Docente();
                    docentesObtenidos.setIdentificacion(resultado.getInt("idAcademico"));
                    docentesObtenidos.setNombre(resultado.getString("nombre"));
                    sinodales.add(docentesObtenidos);
                }
                cbSinodal.setItems(sinodales);
                conn.close();
            } catch (SQLException ex) {
                registroExitoso = false;
                System.out.println(ex.getMessage());
                mostrarAlerta = Herramientas.creadorDeAlerta("Error de consulta", "No fue posible acceder a la base de datos "
                    + "en este momento, intente más tarde", Alert.AlertType.ERROR);
                mostrarAlerta.showAndWait();
                Herramientas.cerrarPantalla(tfEstatus);
            } 
        }else{
            registroExitoso = false;
            mostrarAlerta = Herramientas.creadorDeAlerta("Error de conexión", "No fue posible conectar con la base de datos"
                + "en este momento, intente más tarde", Alert.AlertType.ERROR);
            mostrarAlerta.showAndWait();
            Herramientas.cerrarPantalla(tfEstatus);
        }
    }

    @FXML
    private void clicRegistrar(ActionEvent event) {
        tfNombre.setStyle("-fx-border-color: ;");
        tfFecha.setStyle("-fx-border-color: ;");
        tfEstatus.setStyle("-fx-border-color: ;");
        taDescripcion.setStyle("-fx-border-color: ;");
        cbEstudiantes.setStyle("-fx-border-color: ;");
        cbDirector.setStyle("-fx-border-color: ;");
        cbCoDirector.setStyle("-fx-border-color: ;");
        cbSinodal.setStyle("-fx-border-color: ;");
        
        boolean esCorrecto = true;
        boolean esElMismoDocente = false;
        Validaciones datoAValidar = new Validaciones();
        
        int posicionComboEstudiantes = cbEstudiantes.getSelectionModel().getSelectedIndex();
        int posicionComboDirector = cbDirector.getSelectionModel().getSelectedIndex();
        int posicionComboCoDirector = cbCoDirector.getSelectionModel().getSelectedIndex();
        int posicionComboSinodal = cbSinodal.getSelectionModel().getSelectedIndex();
        
        nombreAuxiliar = tfNombre.getText();
        fechaAuxiliar = tfFecha.getText();
        estatusAuxiliar = tfEstatus.getText();
        descripcionAuxiliar = taDescripcion.getText();
        
        if((nombreAuxiliar.isEmpty()) || (!datoAValidar.validarTextos(nombreAuxiliar))){
            tfNombre.setStyle("-fx-border-color: red;");
            esCorrecto = false;
        }
        if((fechaAuxiliar.isEmpty()) || (!datoAValidar.validarFecha(fechaAuxiliar))){
            tfFecha.setStyle("-fx-border-color: red;");
            esCorrecto = false;
        }
        if((estatusAuxiliar.isEmpty()) || (!datoAValidar.validarEstatus(estatusAuxiliar))){
            tfEstatus.setStyle("-fx-border-color: red;");
            esCorrecto = false;
        }
        if((descripcionAuxiliar.isEmpty()) || (!datoAValidar.validarTextos(descripcionAuxiliar))){
            taDescripcion.setStyle("-fx-border-color: red;");
            esCorrecto = false;
        }
        if(posicionComboEstudiantes < 0){
            cbEstudiantes.setStyle("-fx-border-color: red;");
            esCorrecto = false;
        }
        if(posicionComboDirector < 0){
            cbDirector.setStyle("-fx-border-color: red;");
            esCorrecto = false;
        }
        if(posicionComboCoDirector < 0){
            cbCoDirector.setStyle("-fx-border-color: red;");
            esCorrecto = false;
        }
        if(posicionComboSinodal < 0){
            cbSinodal.setStyle("-fx-border-color: red;");
            esCorrecto = false;
        }
        
        if((idDirector == idCoDirector) || (idDirector == idSinodal) || (idCoDirector == idSinodal)){
            esElMismoDocente = true;
        }
        
        if(esCorrecto){
            if(!esElMismoDocente){
                registrarTrabajo(nombreAuxiliar, fechaAuxiliar, descripcionAuxiliar, estatusAuxiliar,
                    idEstudiante, idDirector, idCoDirector, idSinodal);
                if(registroExitoso){
                        mostrarAlerta = Herramientas.creadorDeAlerta("Mensaje", "Registro exitoso", Alert.AlertType.INFORMATION);
                        mostrarAlerta.showAndWait();
                        Herramientas.cerrarPantalla(tfNombre);
                        notificacion.refrescarTabla(true);
                    }else{
                        mostrarAlerta = Herramientas.creadorDeAlerta("Error", "No fue posible completar el registro, "
                            + "intente más tarde", Alert.AlertType.ERROR);
                        mostrarAlerta.showAndWait();
                    }
            }else{
                mostrarAlerta = Herramientas.creadorDeAlerta("Error", 
                    "Director, CoDirector y Sinodal no pueden ser la misma persona", Alert.AlertType.ERROR);
                mostrarAlerta.showAndWait(); 
            }    
        }else{
            mostrarAlerta = Herramientas.creadorDeAlerta("Campos incorrectos o vacíos", 
                "Verifique su información", Alert.AlertType.ERROR);
            mostrarAlerta.showAndWait();
        }
    }
    
    private void registrarTrabajo(String nombre, String fecha, String estatus, String descripcion,
        int idAlumno, int idDirector, int idCoDirector, int idSinodal){
        Connection conn = ConectarBD.abrirConexionMySQL();
        if(conn != null){
            try{
                String consulta = "INSERT INTO trabajoRecepcional(nombre, fechaRegistro, descripcion, estatus,"
                    + "idAlumno, idDirector, idCodirector, idSinodal) values (?, ?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement declaracion = conn.prepareStatement(consulta);
                declaracion.setString(1, nombre);
                declaracion.setString(2, fecha);
                declaracion.setString(3, estatus);
                declaracion.setString(4, descripcion);
                declaracion.setInt(5, idAlumno);
                declaracion.setInt(6, idDirector);
                declaracion.setInt(7, idCoDirector);
                declaracion.setInt(8, idSinodal);
                int resultado = declaracion.executeUpdate();
                if(resultado == 0){
                    registroExitoso = false;
                }
                conn.close();
            }catch(SQLException ex){
                registroExitoso = false;
                System.out.println("hola"+ ex.getMessage());
                mostrarAlerta = Herramientas.creadorDeAlerta("Error de consulta", "No fue posible acceder a la base de datos "
                    + "en este momento, intente más tarde", Alert.AlertType.ERROR);
                mostrarAlerta.showAndWait();
                Herramientas.cerrarPantalla(tfEstatus);
            }
        }else{
            registroExitoso = false;
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
    private void contarCaracteresDescripcion(KeyEvent event) {
        Herramientas.contadorDeCaracteres(taDescripcion, event);
    }
    
}
