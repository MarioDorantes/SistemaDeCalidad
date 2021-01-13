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
import pojos.TrabajoRecepcional;
import util.Herramientas;
import validaciones.Validaciones;

public class FXMLActualizarTrabajoController implements Initializable {

    @FXML
    private TextField tfEstatus;
    @FXML
    private ComboBox<Docente> cbSinodal;
    @FXML
    private ComboBox<Docente> cbCoDirector;
    @FXML
    private ComboBox<Docente> cbDirector;
    @FXML
    private ComboBox<Estudiante> cbEstudiantes;
    @FXML
    private TextArea taDescripcion;
    @FXML
    private TextField tfFecha;
    @FXML
    private TextField tfNombre;
    
    Alert mostrarAlerta;
    NotificaCambios notificacion;
    TrabajoRecepcional editarTrabajo;
    boolean actualizacionExitosa = true;
    
    int idTrabajoRecepcional = 0;
    int idEstudiante = 0;
    int idDirector = 0;
    int idCoDirector = 0;
    int idSinodal = 0;
    
    private ObservableList<Estudiante> estudiantes;
    private ObservableList<Docente> directores;
    private ObservableList<Docente> coDirectores;
    private ObservableList<Docente> sinodales;
    
    String nombreAuxiliar;
    String fechaAuxiliar;
    String estatusAuxiliar;
    String descripcionAuxiliar;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       estudiantes = FXCollections.observableArrayList();
       directores = FXCollections.observableArrayList();
       coDirectores = FXCollections.observableArrayList();
       sinodales = FXCollections.observableArrayList();
       
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
    
    public void inicializaCampos(NotificaCambios notificacion, TrabajoRecepcional editarTrabajo){
        this.notificacion = notificacion;
        this.editarTrabajo = editarTrabajo;
        
        obtenerTrabajoSeleccionado();
    }
    
    private void obtenerTrabajoSeleccionado(){
        idTrabajoRecepcional = editarTrabajo.getIdentificacion();
        tfNombre.setText(editarTrabajo.getNombre());
        tfFecha.setText(editarTrabajo.getFechaRegistro().toString());
        tfEstatus.setText(editarTrabajo.getEstatus());
        taDescripcion.setText(editarTrabajo.getDescripcion());
        
        int posicionComboEstudianteSeleccionado = obtenerPosicionDeEstudianteSeleccionado(editarTrabajo.getIdAlumno());
        cbEstudiantes.getSelectionModel().select(posicionComboEstudianteSeleccionado);
        
        int posicionComboDirectorSeleccionado = obtenerPosicionDeDirectorSeleccionado(editarTrabajo.getIdDirector());
        cbDirector.getSelectionModel().select(posicionComboDirectorSeleccionado);
        
        int posicionComboCoDirectorSeleccionado = obtenerPosicionDeCoDirectorSeleccionado(editarTrabajo.getIdCoDirector());
        cbCoDirector.getSelectionModel().select(posicionComboCoDirectorSeleccionado);
        
        int posicionComboSinodalSeleccionado = obtenerPosicionDeSinodalSeleccionado(editarTrabajo.getIdSinodal());
        cbSinodal.getSelectionModel().select(posicionComboSinodalSeleccionado);
        
    }

    @FXML
    private void cancelar(ActionEvent event) {
    }

    @FXML
    private void clicActualizar(ActionEvent event) {
        tfNombre.setStyle("-fx-border-color: ;");
        tfFecha.setStyle("-fx-border-color: ;");
        tfEstatus.setStyle("-fx-border-color: ;");
        taDescripcion.setStyle("-fx-border-color: ;");
        
        boolean esCorrecto = true;
        boolean esElMismoDocente = false;
        Validaciones datoAValidar = new Validaciones();
        
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
        
        if((idDirector == idCoDirector) || (idDirector == idSinodal) || (idCoDirector == idSinodal)){
            esElMismoDocente = true;
        }
        
        if(esCorrecto){
            if(!esElMismoDocente){
                actualizarTrabajo(nombreAuxiliar, fechaAuxiliar, estatusAuxiliar, descripcionAuxiliar,
                    idEstudiante, idDirector, idCoDirector, idSinodal, idTrabajoRecepcional);
                if(actualizacionExitosa){
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
    
    private void actualizarTrabajo(String nombre, String fecha, String estatus, String descripcion,
        int idAlumno, int idDirector, int idCoDirector, int idSinodal, int idTrabajoRecepcional){
        Connection conn = ConectarBD.abrirConexionMySQL();
        if(conn != null){
            try{
                String consulta = "update trabajoRecepcional set nombre = ?, fechaRegistro = ?, estatus = ?,"
                    + " descripcion = ?, idAlumno = ?, idDirector = ?, idCoDirector = ?, idSinodal = ?"
                    + " where idTrabajoRecepcional = ? ";
                PreparedStatement declaracion = conn.prepareStatement(consulta);
                declaracion.setString(1, nombre);
                declaracion.setString(2, fecha);
                declaracion.setString(3, estatus);
                declaracion.setString(4, descripcion);
                declaracion.setInt(5, idAlumno);
                declaracion.setInt(6, idDirector);
                declaracion.setInt(7, idCoDirector);
                declaracion.setInt(8, idSinodal);
                declaracion.setInt(9, idTrabajoRecepcional);
                int resultado = declaracion.executeUpdate();
                if(resultado == 0){
                    actualizacionExitosa = false;
                }
                conn.close();
            }catch(SQLException ex){
                actualizacionExitosa = false;
                System.out.println("hola"+ ex.getMessage());
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
    
    private void cargarEstudiantes(){
        Connection conn = ConectarBD.abrirConexionMySQL();
        if(conn != null){
            try {
                String consulta = "select * from estudiante";
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
                actualizacionExitosa = false;
                System.out.println(ex.getMessage());
                mostrarAlerta = Herramientas.creadorDeAlerta("Error de consulta", "No fue posible acceder a la base de datos "
                    + "en este momento, intente más tarde", Alert.AlertType.ERROR);
                mostrarAlerta.showAndWait();
                Herramientas.cerrarPantalla(tfEstatus);
            } 
        }else{
            actualizacionExitosa= false;
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
    
    private int obtenerPosicionDeEstudianteSeleccionado(int idEstudiante){
        int value = 0;
        if(estudiantes.size() > 0){
            for (int i = 0; i < estudiantes.size(); i++) {
                Estudiante get = estudiantes.get(i);
                if(get.getIdEstudiante()== idEstudiante){
                    return i;
                }
            }
        }
        return value;
    }
    
    private int obtenerPosicionDeDirectorSeleccionado(int idDirector){
        int value = 0;
        if(directores.size() > 0){
            for (int i = 0; i < directores.size(); i++) {
                Docente get = directores.get(i);
                if(get.getIdentificacion()== idDirector){
                    return i;
                }
            }
        }
        return value;
    }
    
    private int obtenerPosicionDeCoDirectorSeleccionado(int idCoDirector){
        int value = 0;
        if(coDirectores.size() > 0){
            for (int i = 0; i < coDirectores.size(); i++) {
                Docente get = coDirectores.get(i);
                if(get.getIdentificacion()== idCoDirector){
                    return i;
                }
            }
        }
        return value;
    }
    
    private int obtenerPosicionDeSinodalSeleccionado(int idSinodal){
        int value = 0;
        if(sinodales.size() > 0){
            for (int i = 0; i < sinodales.size(); i++) {
                Docente get = sinodales.get(i);
                if(get.getIdentificacion()== idSinodal){
                    return i;
                }
            }
        }
        return value;
    }

    @FXML
    private void contadorDeCaracteresDescripcion(KeyEvent event) {
        Herramientas.contadorDeCaracteres(taDescripcion, event);
    }
    
}
