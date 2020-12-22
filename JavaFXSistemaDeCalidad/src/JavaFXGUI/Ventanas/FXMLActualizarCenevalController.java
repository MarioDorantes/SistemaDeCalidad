/*
Autor: Mario Dorantes
fechaCreación: 02/12/2020
 */

package JavaFXGUI.Ventanas;

import conexionBD.ConectarBD;
import java.io.IOException;
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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Callback;
import pojos.Ceneval;
import pojos.Estudiante;
import util.Herramientas;


public class FXMLActualizarCenevalController implements Initializable {

    
    @FXML
    private Button btCancelar;
    @FXML
    private DatePicker dpFechaExamen;
    @FXML
    private ComboBox<Estudiante> cbListaEstudiantesConCeneval;
    @FXML
    private TextField tfPuntaje;
    @FXML
    private TextField tfPeriodo;
    
    private ObservableList<Estudiante> estudiantes;
    
    Alert mostrarAlerta;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        estudiantes = FXCollections.observableArrayList();
        
        cargarNombresAlumnosConCenevalBD();
        cbListaEstudiantesConCeneval.valueProperty().addListener(new ChangeListener <Estudiante>(){
            @Override
            public void changed(ObservableValue<? extends Estudiante> observable, Estudiante oldValue, Estudiante newValue) {
                if(newValue != null){
                    extraerCenevalBD(newValue.getIdEstudiante());
                }
            }
            
        });
    }    
    
    @FXML
    private void cancelar () {
        try{
            Stage stage = (Stage) btCancelar.getScene().getWindow();
            Scene sceneVisualizarCeneval = new Scene(FXMLLoader.load(getClass().getResource("FXMLVisualizarCeneval.fxml")));
            stage.setScene(sceneVisualizarCeneval);
            stage.show();
        } catch(IOException ex){
            mostrarAlerta = Herramientas.creadorDeAlerta("Error", "No se pudo cargar la ventana siguiente. Intente más tarde", Alert.AlertType.ERROR);
            mostrarAlerta.showAndWait(); 
        }
    }
    
    private void cargarNombresAlumnosConCenevalBD(){
        
        Connection conn = ConectarBD.abrirConexionMySQL();
        
        if(conn != null){
            try{
                String consulta = "SELECT * FROM estudiante INNER JOIN ceneval ON estudiante.idEstudiante = ceneval.idAlumno;";
                PreparedStatement ps = conn.prepareStatement(consulta);
                ResultSet rs = ps.executeQuery();
                while (rs.next()){
                    Estudiante estudiante = new Estudiante();
                    estudiante.setIdEstudiante(rs.getInt("idEstudiante"));
                    estudiante.setNombre(rs.getString("nombre"));
                    estudiante.setMatricula(rs.getString("matricula"));
                    estudiante.setCorreo(rs.getString("correo"));                    
                    estudiantes.add(estudiante);
                }
                
                cbListaEstudiantesConCeneval.setItems(estudiantes);    
                conn.close();
            } catch(SQLException ex){
                mostrarAlerta = Herramientas.creadorDeAlerta("Error de consulta", ex.getMessage(), Alert.AlertType.ERROR);
                mostrarAlerta.showAndWait();
            }
        }else{
            mostrarAlerta = Herramientas.creadorDeAlerta("Error de conexion a la base de datos", "No hay conexión a la base de datos. Intente más tarde", Alert.AlertType.ERROR);
            mostrarAlerta.showAndWait();
        }
    }    
    
   
    Ceneval ceneval = new Ceneval();
    
    private void extraerCenevalBD(int idEstudiante){
        Connection conn = ConectarBD.abrirConexionMySQL();
        
        if(conn != null){
            try{
                String consulta = "SELECT * FROM ceneval WHERE idAlumno = ?";
                PreparedStatement ps = conn.prepareStatement(consulta);
                ps.setInt(1, idEstudiante);
                ResultSet rs = ps.executeQuery();
                
                while(rs.next()){
                    ceneval.setIdCeneval(rs.getInt("idCeneval"));
                    ceneval.setIdAlumno(rs.getInt("idAlumno"));
                    ceneval.setFechaExamen(rs.getDate("fechaExamen"));
                    ceneval.setPeriodo(rs.getString("periodo"));
                    ceneval.setPuntaje(rs.getFloat("puntaje"));  
                }
                llenarCamposCenevalSeleccionado();
                conn.close();
                
            }catch(SQLException ex){
                mostrarAlerta = Herramientas.creadorDeAlerta("Error de consulta", ex.getMessage(), Alert.AlertType.ERROR);
                mostrarAlerta.showAndWait();
            }
        }else{
            mostrarAlerta = Herramientas.creadorDeAlerta("Error de conexion a la base de datos", "No hay conexión a la base de datos. Intente más tarde", Alert.AlertType.ERROR);
            mostrarAlerta.showAndWait();
        }
    }
    
    private void llenarCamposCenevalSeleccionado(){
        
        //CREO JALA, SI SE CAMBIA DEL LUGAR EL FORMATO
        //dpFechaExamen.setDayCellFactory((Callback<DatePicker, DateCell>) ceneval.getFechaExamen());
        
        
        tfPeriodo.setText(ceneval.getPeriodo());
                
        Float puntajeAux = ceneval.getPuntaje();
        String puntajeAString = puntajeAux.toString();
        tfPuntaje.setText(puntajeAString);
        
    }
    
    
    @FXML
    private void clicActualizarCeneval(ActionEvent event) {
        
    }
    
      
}
