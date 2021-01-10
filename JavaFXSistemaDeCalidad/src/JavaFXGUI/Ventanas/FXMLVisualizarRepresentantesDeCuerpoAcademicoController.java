/*
 * Autor: Brandon Trujillo
 * fecha: 01-09-2021
 */
package JavaFXGUI.Ventanas;

import conexionBD.ConectarBD;
import interfaz.NotificaCambios;
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
import pojos.RepresentanteDeCuerpoAcademico;
import util.Herramientas;

public class FXMLVisualizarRepresentantesDeCuerpoAcademicoController implements Initializable, NotificaCambios {

    @FXML
    private TableView<RepresentanteDeCuerpoAcademico> tvRepresentantes;
    @FXML
    private TableColumn tcNombre;
    @FXML
    private TableColumn tcNumeroDePersonal;
    @FXML
    private TableColumn tcTelefono;
    @FXML
    private TableColumn tcGradoAcademico;
    @FXML
    private TableColumn tcCorreo;
    @FXML
    private TableColumn tcConstraseña;
    
    Alert mostrarAlerta;
    NotificaCambios notificacion;
    
    private ObservableList<RepresentanteDeCuerpoAcademico> representantes;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        representantes = FXCollections.observableArrayList();
        this.tcNumeroDePersonal.setCellValueFactory(new PropertyValueFactory("numeroPersonal"));
        this.tcNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        this.tcTelefono.setCellValueFactory(new PropertyValueFactory("telefono"));
        this.tcGradoAcademico.setCellValueFactory(new PropertyValueFactory("gradoAcademico"));
        this.tcCorreo.setCellValueFactory(new PropertyValueFactory("correo"));
        this.tcConstraseña.setCellValueFactory(new PropertyValueFactory("contraseña"));
       
        obtenerRepresentantes();
    }    
    
    private void obtenerRepresentantes(){
        Connection conn = ConectarBD.abrirConexionMySQL();
        if(conn != null){
            try{
                String consulta = "select academico.idAcademico, usuario.idUsuario, academico.numeroPersonal, nombre, "
                    + "telefono, gradoAcademico, usuario.correo, usuario.password as contraseña from academico "
                    + "inner join usuario on academico.idAcademico = usuario.idAcademico inner join rol "
                    + "on usuario.idRol = rol.idRol and rol.tipoRol = 'Representante'";
                PreparedStatement declaracion = conn.prepareStatement(consulta);
                ResultSet resultado = declaracion.executeQuery();
                while(resultado.next()){
                    RepresentanteDeCuerpoAcademico representantesObtenidos = new RepresentanteDeCuerpoAcademico();
                    representantesObtenidos.setNumeroPersonal(resultado.getString("numeroPersonal"));
                    representantesObtenidos.setNombre(resultado.getString("nombre"));
                    representantesObtenidos.setTelefono(resultado.getString("telefono"));
                    representantesObtenidos.setGradoAcademico(resultado.getString("gradoAcademico"));
                    representantesObtenidos.setCorreo(resultado.getString("correo"));
                    representantesObtenidos.setContraseña(resultado.getString("contraseña"));
                    representantesObtenidos.setIdentificacion(resultado.getInt("academico.idAcademico"));
                    representantes.add(representantesObtenidos);
                }
                tvRepresentantes.setItems(representantes);
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
    private void clicEliminarRepresentante(ActionEvent event) {
    }

    @FXML
    private void irAActualizarRepresentante(ActionEvent event) {
    }

    @FXML
    private void irARegistrarRepresentante(ActionEvent event) {
        try {
            FXMLLoader cargaPantalla = new FXMLLoader(getClass().getResource("FXMLRegistrarRepresentante.fxml"));
            Parent root = cargaPantalla.load();
            FXMLRegistrarRepresentanteController controlador = cargaPantalla.getController();
            controlador.inicializaCampos(this);
            Scene escenaRegistrarRepresentante = new Scene(root);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(escenaRegistrarRepresentante);
            stage.showAndWait();
        }catch(IOException ex){
            mostrarAlerta = Herramientas.creadorDeAlerta("Error al cargar la escena", "No se pudo cargar la ventana siguiente, "
                + "intente más tarde", Alert.AlertType.ERROR);
            mostrarAlerta.showAndWait();
        }
    }

    @FXML
    private void cancelar(ActionEvent event) {
        try {
            Stage stage = (Stage) tvRepresentantes.getScene().getWindow();
            Scene cancelar = new Scene(FXMLLoader.load(getClass().getResource("FXMLVentanaPrincipalRepresentanteDeCuerpoAcademico.fxml")));
            stage.setScene(cancelar);
            stage.show();
        } catch (IOException ex) {
            mostrarAlerta = Herramientas.creadorDeAlerta("Error", "No se pudo cargar la ventana siguiente, "
                + "intente más tarde", Alert.AlertType.ERROR);
            mostrarAlerta.showAndWait();
        } 
    }

    @Override
    public void refrescarTabla(boolean carga) {
        tvRepresentantes.getItems().clear();
        obtenerRepresentantes();
    }
    
}
