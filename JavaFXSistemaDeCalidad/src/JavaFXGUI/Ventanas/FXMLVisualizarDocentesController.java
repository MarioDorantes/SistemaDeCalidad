/*
 *Autor: Brandon Trujillo
 *FechaCreación: 28/11/2020
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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import pojos.Docente;
import util.Herramientas;

public class FXMLVisualizarDocentesController implements Initializable {

    @FXML
    private TableColumn tcNombre;
    @FXML
    private TableColumn tcNumeroDePersonal;
    @FXML
    private TableColumn tcCorreo;
    @FXML
    private TableColumn tcTelefono;
    @FXML
    private TableView<Docente> tvDocentes;
    @FXML
    private TableColumn tcConstraseña;
       
    Alert mostrarAlerta;
    private ObservableList<Docente> docentes;
   
    
     
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        docentes = FXCollections.observableArrayList();
        this.tcNumeroDePersonal.setCellValueFactory(new PropertyValueFactory("numeroPersonal"));
        this.tcNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        this.tcTelefono.setCellValueFactory(new PropertyValueFactory("telefono"));
        this.tcCorreo.setCellValueFactory(new PropertyValueFactory("correo"));
        this.tcConstraseña.setCellValueFactory(new PropertyValueFactory("contraseña"));
        
        obtenerDocentes();
    }
    
    private void obtenerDocentes(){
        Connection conn = ConectarBD.abrirConexionMySQL();
        if(conn != null){
            try{
                //Modificar para que verifique que solo se obtengan docentes
                String consulta = "select numeroPersonal, nombre, telefono,usuario.correo, "
                    +"usuario.password as contraseña from academico inner join usuario on academico.idAcademico = usuario.idAcademico";
                PreparedStatement declaracion = conn.prepareStatement(consulta);
                ResultSet resultado = declaracion.executeQuery();
                while(resultado.next()){
                    Docente docente = new Docente();
                    docente.setNumeroPersonal(resultado.getString("numeroPersonal"));
                    docente.setNombre(resultado.getString("nombre"));
                    docente.setTelefono(resultado.getString("telefono"));
                    docente.setCorreo(resultado.getString("usuario.correo"));
                    docente.setContraseña(resultado.getString("contraseña"));
                    docentes.add(docente);
                }
                tvDocentes.setItems(docentes);
                conn.close();
            }catch(SQLException ex){
                mostrarAlerta = Herramientas.creadorDeAlerta("Error", "No se puede acceder a la base de datos en este momento, "
                    + "intente más tarde", Alert.AlertType.ERROR);
                mostrarAlerta.showAndWait();
            }
        }else{
            mostrarAlerta = Herramientas.creadorDeAlerta("Error", "No se puede conectar con la base de datos en este momento, "
                + "intente más tarde", Alert.AlertType.ERROR);
            mostrarAlerta.showAndWait();
        }
    }
    
    @FXML
    private void irARegistrarDocente(ActionEvent e){
        try {
            FXMLLoader cargaPantalla = new FXMLLoader(getClass().getResource("FXMLRegistrarDocente.fxml"));
            Parent root = cargaPantalla.load();
            Scene escenaRegistrarDocente = new Scene(root);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(escenaRegistrarDocente);
            stage.showAndWait();
        }catch(IOException ex){
            mostrarAlerta = Herramientas.creadorDeAlerta("Error al cargar la escena", ex.getMessage(), Alert.AlertType.ERROR);
            mostrarAlerta.showAndWait();
        }
    }
    
    @FXML
    private void irAActualizarDocente(ActionEvent e){
        int seleccion = tvDocentes.getSelectionModel().getSelectedIndex();
        if(seleccion > 0){
            Docente editarDocente = docentes.get(seleccion);
            try {
            FXMLLoader cargaPantalla = new FXMLLoader(getClass().getResource("FXMLActualizaDocente.fxml"));
            Parent root = cargaPantalla.load();
            FXMLActualizaDocenteController controlador = cargaPantalla.getController();
            controlador.inicializaCampos(editarDocente);
            Scene escenaRegistrarDocente = new Scene(root);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(escenaRegistrarDocente);
            stage.showAndWait();
            }catch(IOException ex){
                mostrarAlerta = Herramientas.creadorDeAlerta("Error al cargar la escena", ex.getMessage(), Alert.AlertType.ERROR);
                mostrarAlerta.showAndWait();
            }
        }else{
            mostrarAlerta = Herramientas.creadorDeAlerta("Atención", "Para editar un registro, "
                + "primero seleccionelo", Alert.AlertType.INFORMATION);
            mostrarAlerta.showAndWait();
        }
    }
    
    @FXML
    private void irAEliminarDocente(ActionEvent e){
        try {
            Stage stage = (Stage) tvDocentes.getScene().getWindow();
            Scene eliminarDocente = new Scene(FXMLLoader.load(getClass().getResource("FXMLEliminarDocente.fxml")));
            stage.setScene(eliminarDocente);
            stage.show();
        } catch (IOException ex) {
            mostrarAlerta = Herramientas.creadorDeAlerta("Error", ex.getMessage(), Alert.AlertType.ERROR);
            mostrarAlerta.showAndWait();
        } 
    }
    
    @FXML
    private void cancelar(ActionEvent e){
        try {
            Stage stage = (Stage) tvDocentes.getScene().getWindow();
            Scene cancelar = new Scene(FXMLLoader.load(getClass().getResource("FXMLVentanaPrincipalDirectorDeLaFacultad.fxml")));
            stage.setScene(cancelar);
            stage.show();
        } catch (IOException ex) {
            mostrarAlerta = Herramientas.creadorDeAlerta("Error", ex.getMessage(), Alert.AlertType.ERROR);
            mostrarAlerta.showAndWait();
        } 
    } 
   
}
