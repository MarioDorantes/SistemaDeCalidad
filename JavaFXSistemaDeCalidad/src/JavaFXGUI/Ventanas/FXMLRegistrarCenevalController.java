/*
Autor: Mario Dorantes
fechaCreación: 25/11/2020
 */

package JavaFXGUI.Ventanas;

import conexionBD.ConectarBD;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
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
import pojos.Estudiante;
import util.Herramientas;
import validaciones.Validaciones;


public class FXMLRegistrarCenevalController implements Initializable {

    @FXML
    private Button btCancelar;
    @FXML
    private TextField tfPeriodo;
    @FXML
    private TextField tfPuntaje;
    @FXML
    private ComboBox<Estudiante> cbListaEstudiantes;
    @FXML
    private TextField tfFechaExamen;
    
    private ObservableList<Estudiante> estudiantes;
    
    Alert mostrarAlerta;
       
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        estudiantes = FXCollections.observableArrayList();
        cargarNombresAlumnosBD();
        
    }    
    
    @FXML
    private void cancelar () {
        mostrarAlerta = Herramientas.creadorDeAlerta("Cancelar", "¿Seguro desea cancelar?", Alert.AlertType.CONFIRMATION);
        Optional<ButtonType> opcionSeleccionada = mostrarAlerta.showAndWait(); 
        
        if(opcionSeleccionada.get() == ButtonType.OK){
            salir();
        }
    }
    
    
    private void cargarNombresAlumnosBD(){
        Connection conn = ConectarBD.abrirConexionMySQL();
        
        if(conn != null){
            try{
                String consulta = "SELECT * FROM estudiante LEFT JOIN ceneval ON estudiante.idEstudiante = ceneval.idAlumno WHERE ceneval.idAlumno IS NULL AND estatus = 'Activo';";
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
                
                cbListaEstudiantes.setItems(estudiantes);                                
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
    
 
    @FXML
    private void clicRegistrarCeneval(ActionEvent event){
        boolean esValido = true;
           
        int posicionNombreEstudiante = cbListaEstudiantes.getSelectionModel().getSelectedIndex();
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
                guardarCeneval(estudiantes.get(posicionNombreEstudiante).getIdEstudiante(), fechaAux, periodoAux, puntajeAux);
            }
            
        } else {
            mostrarAlerta = Herramientas.creadorDeAlerta("Campos Obligatorios", "Favor de no dejar campos vacios", Alert.AlertType.ERROR);
            mostrarAlerta.showAndWait();
        }
                
    }
    
    private void guardarCeneval(int idAlumno, String fechaExamen, String periodo, String puntaje){
        Connection conn = ConectarBD.abrirConexionMySQL();
        if(conn != null){
            try{
                int resultado;
                String consulta = "INSERT INTO ceneval (idAlumno, fechaExamen, periodo, puntaje) VALUES (?, ?, ?, ?)";
                PreparedStatement ps = conn.prepareStatement(consulta);
                ps.setInt(1, idAlumno);
                ps.setString(2, fechaExamen);
                ps.setString(3, periodo);
                ps.setString(4, puntaje);
                resultado = ps.executeUpdate();
                
                conn.close();
                
                if(resultado > 0){
                    mostrarAlerta = Herramientas.creadorDeAlerta("Mensaje de confirmación", "Ceneval registrado exitosamente", Alert.AlertType.INFORMATION);
                    mostrarAlerta.showAndWait();
                } else {
                    mostrarAlerta = Herramientas.creadorDeAlerta("Mensaje de error", "Error al registrar ceneval", Alert.AlertType.ERROR);
                    mostrarAlerta.showAndWait();
                }                               
              
                salir();
                
            } catch (SQLException ex){
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
