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
import java.time.LocalDate;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import pojos.Estudiante;
import util.Herramientas;


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
    private DatePicker dpFechaExamen;
    
    private ObservableList<Estudiante> estudiantes;
    
    Alert mostrarAlerta;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        estudiantes = FXCollections.observableArrayList();
        cargarNombresAlumnosBD();
        
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
    
    
    private void cargarNombresAlumnosBD(){
        Connection conn = ConectarBD.abrirConexionMySQL();
        
        if(conn != null){
            try{
                String consulta = "SELECT * FROM estudiante";
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
    
    //clic guardar 
    @FXML
    private void clicRegistrarCeneval(ActionEvent event){
        boolean esValido = true;
           
        int posicionNombreEstudiante = cbListaEstudiantes.getSelectionModel().getSelectedIndex();
        LocalDate fechaAux = dpFechaExamen.getValue();  
        String periodoAux = tfPeriodo.getText();
        float puntajeAux = tfPuntaje.getLength();   
        
        if (posicionNombreEstudiante < 0){
            esValido = false;
        }
        if(fechaAux == null){
            esValido = false;
        }
        if(periodoAux.isEmpty()){
            esValido = false;
        }
        if(puntajeAux < 0){
            esValido = false;
        }
        
        if(esValido){
            //metodo de guardar
            mostrarAlerta = Herramientas.creadorDeAlerta("Se guardo xdd", "mensaje de prueba, que llega aqui", Alert.AlertType.ERROR);
            mostrarAlerta.showAndWait();
        } else {
            mostrarAlerta = Herramientas.creadorDeAlerta("Campos Obligatorios", "Favor de no dejar campos vacios", Alert.AlertType.ERROR);
            mostrarAlerta.showAndWait();
        }
                
    }

    
       
}
