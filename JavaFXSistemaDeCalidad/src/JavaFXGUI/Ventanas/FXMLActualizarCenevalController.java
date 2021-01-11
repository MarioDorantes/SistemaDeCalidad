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
import java.util.Date;
import java.util.Optional;
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import pojos.Ceneval;
import pojos.Estudiante;
import util.Herramientas;
import validaciones.Validaciones;


public class FXMLActualizarCenevalController implements Initializable {

    
    @FXML
    private Button btCancelar;
    @FXML
    private ComboBox<Estudiante> cbListaEstudiantesConCeneval;
    @FXML
    private TextField tfPuntaje;
    @FXML
    private TextField tfPeriodo;
    @FXML
    private TextField tfFechaExamen;
    
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
        mostrarAlerta = Herramientas.creadorDeAlerta("Cancelar", "¿Seguro desea cancelar?", Alert.AlertType.CONFIRMATION);
        Optional<ButtonType> opcionSeleccionada = mostrarAlerta.showAndWait(); 
        
        if(opcionSeleccionada.get() == ButtonType.OK){
            salir();
        }
    }
    
    private void cargarNombresAlumnosConCenevalBD(){
        
        Connection conn = ConectarBD.abrirConexionMySQL();
        
        if(conn != null){
            try{
                String consulta = "SELECT * FROM estudiante INNER JOIN ceneval ON estudiante.idEstudiante = ceneval.idAlumno AND estatus = 'Activo';";
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
        
        Date fechaExamenAux = ceneval.getFechaExamen();
        String fechaAString = fechaExamenAux.toString();
        tfFechaExamen.setText(fechaAString);
        
        tfPeriodo.setText(ceneval.getPeriodo());
                
        Float puntajeAux = ceneval.getPuntaje();
        String puntajeAString = puntajeAux.toString();
        tfPuntaje.setText(puntajeAString);
        
    }
    
    
    @FXML
    private void clicActualizarCeneval(ActionEvent event) {
        boolean esValido = true;
           
        int posicionNombreEstudiante = cbListaEstudiantesConCeneval.getSelectionModel().getSelectedIndex();
        String fechaAux = tfFechaExamen.getText();
        String periodoAux = tfPeriodo.getText();
        String puntajeAux = tfPuntaje.getText();
        
        if (posicionNombreEstudiante < 0){
            esValido = false;
        }
        if(fechaAux.isEmpty()){
            esValido = false;
        }
        if(periodoAux.isEmpty()){
            esValido = false;
        }
        if(puntajeAux.isEmpty()){
            esValido = false;
        }
        
        if(esValido){
            Validaciones datoAValidar = new Validaciones();
            
            if(!datoAValidar.validarFechaExamen(fechaAux)){
                mostrarAlerta = Herramientas.creadorDeAlerta("Formato de fecha incorrecto", "Formato: Año-Mes-Dia \nEjemplo: 2018-05-13", Alert.AlertType.ERROR);
                mostrarAlerta.showAndWait();
            } else if(!datoAValidar.validarPeriodo(periodoAux)){
                mostrarAlerta = Herramientas.creadorDeAlerta("Periodo incorrecto", "Formato: Periodo de 6 digitos \nEjemplo: 202101", Alert.AlertType.ERROR);
                mostrarAlerta.showAndWait();
            } else if(!datoAValidar.validarPuntajeObtenido(puntajeAux)){
                mostrarAlerta = Herramientas.creadorDeAlerta("Puntaje incorrecto", "Formato: Numero y decimal \nEjemplo: 9.50", Alert.AlertType.ERROR);
                mostrarAlerta.showAndWait();
            }
            if(datoAValidar.validarFechaExamen(fechaAux) && datoAValidar.validarPeriodo(periodoAux) && datoAValidar.validarPuntajeObtenido(puntajeAux)){
                actualizarCeneval(fechaAux, periodoAux, puntajeAux);
            }
            
        } else {
            mostrarAlerta = Herramientas.creadorDeAlerta("Campos Obligatorios", "Favor de no dejar campos vacios", Alert.AlertType.ERROR);
            mostrarAlerta.showAndWait();
        }
    }
    
    private void actualizarCeneval(String fechaExamen, String periodo, String puntaje){
        Connection conn = ConectarBD.abrirConexionMySQL();
        if(conn != null){
            try{
                int resultado;
                String consulta = "UPDATE ceneval SET fechaExamen = ?, periodo = ?, puntaje = ? WHERE idCeneval = ?";
                PreparedStatement ps = conn.prepareStatement(consulta);
                ps.setString(1, fechaExamen);
                ps.setString(2, periodo);
                ps.setString(3, puntaje);
                ps.setInt(4, ceneval.getIdCeneval());
                resultado = ps.executeUpdate();
                
                conn.close();
                
                if(resultado > 0){
                    mostrarAlerta = Herramientas.creadorDeAlerta("Mensaje de confirmación", "Ceneval actualizado exitosamente", Alert.AlertType.INFORMATION);
                    mostrarAlerta.showAndWait();
                } else {
                    mostrarAlerta = Herramientas.creadorDeAlerta("Mensaje de error", "Error al actualizar ceneval", Alert.AlertType.ERROR);
                    mostrarAlerta.showAndWait();
                }                               
              
                salir();
                
            }catch(SQLException ex){
                mostrarAlerta = Herramientas.creadorDeAlerta("Error en la conexión a la base de datos", ex.getMessage(), Alert.AlertType.ERROR);
                mostrarAlerta.showAndWait();
            }
        }
    }
    
    private void salir(){
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
      
}
