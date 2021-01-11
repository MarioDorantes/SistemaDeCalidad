/*
Autor: Mario Dorantes
fechaCreación: 11/01/2020
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import pojos.Estudiante;
import util.Herramientas;
import validaciones.Validaciones;

public class FXMLCatalogoEstudiantesController implements Initializable {

    @FXML
    private TextField tfNombreEstudiante;
    @FXML
    private TextField tfMatricula;
    @FXML
    private TextField tfCorreo;
    @FXML
    private TableView<Estudiante> tbTablaEstudiantes;
    @FXML
    private TableColumn colNombreEstudiante;
    @FXML
    private TableColumn colMatricula;
    @FXML
    private TableColumn colCorreo;
    @FXML
    private Button btSalir;
    
    Alert mostrarAlerta;
    
    private ObservableList<Estudiante> estudiantes;
    
    private final String estatus = "Activo";
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        estudiantes = FXCollections.observableArrayList();
        
        this.colNombreEstudiante.setCellValueFactory(new PropertyValueFactory("nombre"));
        this.colMatricula.setCellValueFactory(new PropertyValueFactory("matricula"));
        this.colCorreo.setCellValueFactory(new PropertyValueFactory("correo"));
        
        cargarEstudiantesBD();
        
    }

    private void cargarEstudiantesBD(){
        Connection conn = ConectarBD.abrirConexionMySQL();
        
        if(conn != null){
            try{
                String consulta = "SELECT * FROM estudiante WHERE estatus = 'Activo';";
                PreparedStatement ps = conn.prepareStatement(consulta);
                ResultSet rs = ps.executeQuery();
                while (rs.next()){
                    Estudiante estudiante = new Estudiante();
                    estudiante.setIdEstudiante(rs.getInt("idEstudiante"));
                    estudiante.setNombre(rs.getString("nombre"));
                    estudiante.setMatricula(rs.getString("matricula"));
                    estudiante.setCorreo(rs.getString("correo"));
                    estudiante.setEstatus(rs.getString("estatus"));
                    estudiantes.add(estudiante);
                }
                
                tbTablaEstudiantes.setItems(estudiantes);                                
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
    private void clicRegistrar(ActionEvent event) {
        boolean esValido = true;
           
        String nombreEstudianteAux = tfNombreEstudiante.getText();
        String matriculaAux = tfMatricula.getText();
        String correoAux = tfCorreo.getText();
        
        if(nombreEstudianteAux.isEmpty()){
            esValido = false;
        }
        if(matriculaAux.isEmpty()){
            esValido = false;
        }
        if(correoAux.isEmpty()){
            esValido = false;
        }
        
        if(esValido){
            Validaciones datoAValidar = new Validaciones();
            
            if(!datoAValidar.validarNombre(nombreEstudianteAux)){
                mostrarAlerta = Herramientas.creadorDeAlerta("Formato de nombre incorrecto", "Formato: 1 o 2 nombres y apellidos. Sin acentos. Primeras letras en mayúscula. \nEjemplo: Carlos Herrera Garcia ", Alert.AlertType.ERROR);
                mostrarAlerta.showAndWait();
            } else if(!datoAValidar.validarMatricula(matriculaAux)){
                mostrarAlerta = Herramientas.creadorDeAlerta("Formato de matrícula incorrecto", "Formato: ZS + 8 dígitos numericos.  \nEjemplo: ZS18018456 ", Alert.AlertType.ERROR);
                mostrarAlerta.showAndWait();
            } else if(!datoAValidar.validarCorreo(correoAux)){
                mostrarAlerta = Herramientas.creadorDeAlerta("Formato de Correo incorrecto", "Formato: correo + @ + [gmail.com, estudiantes.uv.mx, uv.mx] \nEjemplo: Carlos@gmail.com", Alert.AlertType.ERROR);
                mostrarAlerta.showAndWait();
            }
            if(datoAValidar.validarNombre(nombreEstudianteAux) && datoAValidar.validarMatricula(matriculaAux) && datoAValidar.validarCorreo(correoAux)){
                limpiarCampos();
                guardarEstudiante(nombreEstudianteAux, matriculaAux, correoAux, estatus);
            }
            
        } else {
            mostrarAlerta = Herramientas.creadorDeAlerta("Campos Obligatorios", "Favor de no dejar campos vacios", Alert.AlertType.ERROR);
            mostrarAlerta.showAndWait();
        }
    }
    
    private void guardarEstudiante(String nombreEstudiante, String matricula, String correo, String estatus){
        Connection conn = ConectarBD.abrirConexionMySQL();
        if(conn != null){
            try{
                int resultado;
                String consulta = "INSERT INTO estudiante (nombre, matricula, correo, estatus) VALUES (?, ?, ?, ?)";
                PreparedStatement ps = conn.prepareStatement(consulta);
                ps.setString(1, nombreEstudiante);
                ps.setString(2, matricula);
                ps.setString(3, correo);
                ps.setString(4, estatus);
                resultado = ps.executeUpdate();
                
                conn.close();
                
                if(resultado > 0){
                    mostrarAlerta = Herramientas.creadorDeAlerta("Mensaje de confirmación", "Estudiante registrado exitosamente", Alert.AlertType.INFORMATION);
                    mostrarAlerta.showAndWait();
                } else {
                    mostrarAlerta = Herramientas.creadorDeAlerta("Mensaje de error", "Error al registrar a estudiante", Alert.AlertType.ERROR);
                    mostrarAlerta.showAndWait();
                }                               
                
                limpiarTabla();
                cargarEstudiantesBD();
                
            } catch (SQLException ex){
                mostrarAlerta = Herramientas.creadorDeAlerta("Error en la conexión a la base de datos", ex.getMessage(), Alert.AlertType.ERROR);
                mostrarAlerta.showAndWait();
            }
        }
    }
    
    private void limpiarCampos(){
        tfNombreEstudiante.setText("");
        tfCorreo.setText("");
        tfMatricula.setText("");
    }
    
    private void limpiarTabla(){
        tbTablaEstudiantes.getItems().clear();
    }

    @FXML
    private void clicEditar(ActionEvent event) {
        limpiarCampos();
        int seleccion = tbTablaEstudiantes.getSelectionModel().getSelectedIndex();     
        
        if (seleccion >= 0) {
            Estudiante estudianteSeleccionado = estudiantes.get(seleccion);
            tfNombreEstudiante.setText(estudianteSeleccionado.getNombre());
            tfMatricula.setText(estudianteSeleccionado.getMatricula());
            tfCorreo.setText(estudianteSeleccionado.getCorreo());
        } else {
            mostrarAlerta = Herramientas.creadorDeAlerta("Sin selección", "Seleccione un registro para poder editarlo", Alert.AlertType.ERROR);
            mostrarAlerta.showAndWait();  
        }
    }

    @FXML
    private void clicActualizar(ActionEvent event) {
        boolean esValido = true;
        
        int posicionTabla = tbTablaEstudiantes.getSelectionModel().getSelectedIndex();
        String nombreEstudianteAux = tfNombreEstudiante.getText();
        String matriculaAux = tfMatricula.getText();
        String correoAux = tfCorreo.getText();
        
        if(posicionTabla < 0){
            esValido = false;
        }
        if(nombreEstudianteAux.isEmpty()){
            esValido = false;
        }
        if(matriculaAux.isEmpty()){
            esValido = false;
        }
        if(correoAux.isEmpty()){
            esValido = false;
        }
        
        if(esValido){
            Validaciones datoAValidar = new Validaciones();
            
            if(!datoAValidar.validarNombre(nombreEstudianteAux)){
                mostrarAlerta = Herramientas.creadorDeAlerta("Formato de nombre incorrecto", "Formato: 1 o 2 nombres y apellidos. Sin acentos. Primeras letras en mayúscula. \nEjemplo: Carlos Herrera Garcia ", Alert.AlertType.ERROR);
                mostrarAlerta.showAndWait();
            } else if(!datoAValidar.validarMatricula(matriculaAux)){
                mostrarAlerta = Herramientas.creadorDeAlerta("Formato de matrícula incorrecto", "Formato: ZS + 8 dígitos numericos.  \nEjemplo: ZS18018456 ", Alert.AlertType.ERROR);
                mostrarAlerta.showAndWait();
            } else if(!datoAValidar.validarCorreo(correoAux)){
                mostrarAlerta = Herramientas.creadorDeAlerta("Formato de Correo incorrecto", "Formato: correo + @ + [gmail.com, estudiantes.uv.mx, uv.mx] \nEjemplo: Carlos@gmail.com", Alert.AlertType.ERROR);
                mostrarAlerta.showAndWait();
            }
            if(datoAValidar.validarNombre(nombreEstudianteAux) && datoAValidar.validarMatricula(matriculaAux) && datoAValidar.validarCorreo(correoAux)){
                limpiarCampos();
                Estudiante estudianteAActualizar = estudiantes.get(posicionTabla);
                actualizarEstudiante(nombreEstudianteAux, matriculaAux, correoAux, estudianteAActualizar.getNombre(), estudianteAActualizar.getMatricula());
            }
            
        } else {
            mostrarAlerta = Herramientas.creadorDeAlerta("Alerta", "Seleccione un registro de la tabla y de clic en el botón 'Editar'. \nRealice los cambios y de clic en el botón 'Actualizar'", Alert.AlertType.ERROR);
            mostrarAlerta.showAndWait();
        }
    }
    
    private void actualizarEstudiante (String nombre, String matricula, String correo, String nombreParametro, String matriculaParametro){
        Connection conn = ConectarBD.abrirConexionMySQL();
        if(conn != null){
            try{
                int resultado;
                String consulta = "UPDATE estudiante SET nombre = ?, matricula = ?, correo = ? WHERE nombre = ? AND matricula = ?";
                PreparedStatement ps = conn.prepareStatement(consulta);
                ps.setString(1, nombre);
                ps.setString(2, matricula);
                ps.setString(3, correo);
                ps.setString(4, nombreParametro);
                ps.setString(5, matriculaParametro);
                resultado = ps.executeUpdate();
                
                conn.close();
                
                if(resultado > 0){
                    mostrarAlerta = Herramientas.creadorDeAlerta("Mensaje de confirmación", "Registro actualizado", Alert.AlertType.INFORMATION);
                    mostrarAlerta.showAndWait();
                } else {
                    mostrarAlerta = Herramientas.creadorDeAlerta("Mensaje de error", "Error al actualizar registro", Alert.AlertType.ERROR);
                    mostrarAlerta.showAndWait();
                }                               
                
                limpiarTabla();
                cargarEstudiantesBD();
                
            }catch(SQLException ex){
                mostrarAlerta = Herramientas.creadorDeAlerta("Error en la conexión a la base de datos", ex.getMessage(), Alert.AlertType.ERROR);
                mostrarAlerta.showAndWait();
            }
        }
    }

    @FXML
    private void clicEliminar(ActionEvent event) {
        int posicionNombreEstudiante = tbTablaEstudiantes.getSelectionModel().getSelectedIndex();
        if(posicionNombreEstudiante >= 0){
            Estudiante estudianteAEliminar = estudiantes.get(posicionNombreEstudiante);
            
            mostrarAlerta = Herramientas.creadorDeAlerta("Confirmar Eliminación", "¿Seguro desea eliminar al estudiante seleccionado?", Alert.AlertType.CONFIRMATION);
            Optional<ButtonType> opcionSeleccionada = mostrarAlerta.showAndWait();
            
            if(opcionSeleccionada.get() == ButtonType.OK){
                eliminarEstudiante(estudianteAEliminar.getIdEstudiante());
            }
            
        } else {
            mostrarAlerta = Herramientas.creadorDeAlerta("Error", "Seleccione un estudiante para poder eliminarlo", Alert.AlertType.WARNING);
            mostrarAlerta.showAndWait();
        }
    }
    
    private void eliminarEstudiante(int idEstudiante){
        Connection conn = ConectarBD.abrirConexionMySQL();
        if(conn != null){
            try{
                int resultado;                
                String consulta = "UPDATE estudiante SET estatus = 'Inactivo' WHERE idEstudiante = ?";
                PreparedStatement ps = conn.prepareStatement(consulta);
                ps.setInt(1, idEstudiante);
                resultado = ps.executeUpdate();
                
                conn.close();
                
                if(resultado > 0){
                    mostrarAlerta = Herramientas.creadorDeAlerta("Mensaje de confirmación", "Estudiante eliminado exitosamente", Alert.AlertType.INFORMATION);
                    mostrarAlerta.showAndWait();
                } else {
                    mostrarAlerta = Herramientas.creadorDeAlerta("Mensaje de error", "Error al eliminar a estudiante", Alert.AlertType.ERROR);
                    mostrarAlerta.showAndWait();
                }   
                
                limpiarTabla();
                cargarEstudiantesBD();
                              
            }catch(SQLException ex){
                mostrarAlerta = Herramientas.creadorDeAlerta("Error en la conexión a la base de datos. Intente más tarde", ex.getMessage(), Alert.AlertType.ERROR);
                mostrarAlerta.showAndWait();
            }
        }
    }

    @FXML
    private void clicSalir(ActionEvent event) {
        try{
            Stage stage = (Stage) btSalir.getScene().getWindow();
            Scene sceneVentanaPrincipalDirectorDeLaFacultad = new Scene(FXMLLoader.load(getClass().getResource("FXMLVentanaPrincipalDirectorDeLaFacultad.fxml")));
            stage.setScene(sceneVentanaPrincipalDirectorDeLaFacultad);
            stage.show(); 
        } catch(IOException ex){
            mostrarAlerta = Herramientas.creadorDeAlerta("Error", "No se pudo cargar la ventana siguiente. Intente más tarde", Alert.AlertType.ERROR);
            mostrarAlerta.showAndWait(); 
        }
    }
    
}
