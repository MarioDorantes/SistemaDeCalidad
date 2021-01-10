/*
 *Autor: Brandon Trujillo
 *fechaDeCreación: 01/12/2020
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
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import pojos.CatalogoLGCA;
import util.Herramientas;

public class FXMLRegistrarCatalogoLGCAController implements Initializable {
    
    @FXML
    private TextField tfNombre;
    @FXML
    private TextField tfClave;
    @FXML
    private TextField tfUnidadDeAdscripcion;
    @FXML
    private TextField tfAdscripcion;
    @FXML
    private TextField tfGradoConsolidacion;
    @FXML
    private TextField tfFecha;
    @FXML
    private TextField tfEstatus;
    
    Alert mostrarAlerta;
    NotificaCambios notificacion;
    boolean registroExitoso = true;
    private ObservableList<CatalogoLGCA> validacionDeClaveRepetida;
    
    String claveAuxiliar;
    String nombreAuxiliar;
    String fechaAuxiliar;
    String gradoConsolidacionAuxiliar;
    String adscripcionAuxiliar;
    String unidadAdscripcionAuxiliar;
    String estatusAuxiliar;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        validacionDeClaveRepetida = FXCollections.observableArrayList();
    } 
    
    public void inicializaCampos(NotificaCambios notificacion){
        this.notificacion = notificacion;
    }
    
    @FXML
    private void cancelar(ActionEvent e){
        Herramientas.cerrarPantalla(tfClave);
    }
    
    @FXML
    private void clicEnRegistrar(ActionEvent event) {
        obtenerClavesRegistradas();
        tfClave.setStyle("-fx-border-color: ;");
        tfNombre.setStyle("-fx-border-color: ;");
        tfFecha.setStyle("-fx-border-color: ;");
        tfGradoConsolidacion.setStyle("-fx-border-color: ;");
        tfAdscripcion.setStyle("-fx-border-color: ;");
        tfUnidadDeAdscripcion.setStyle("-fx-border-color: ;");
        tfEstatus.setStyle("-fx-border-color: ;");
        
        boolean esCorrecto = true;
        boolean esRepetido = false;
        
        int iterador = 0;
        int tamañoDeValidacionDeClaveRepetida = validacionDeClaveRepetida.size();
        
        claveAuxiliar = tfClave.getText();
        nombreAuxiliar = tfNombre.getText();
        fechaAuxiliar = tfFecha.getText();
        gradoConsolidacionAuxiliar = tfGradoConsolidacion.getText();
        adscripcionAuxiliar = tfAdscripcion.getText();
        unidadAdscripcionAuxiliar = tfUnidadDeAdscripcion.getText();
        estatusAuxiliar = tfEstatus.getText();
        
        if(claveAuxiliar.isEmpty()){
            tfClave.setStyle("-fx-border-color: red;");
            esCorrecto = false;
        }
        if(nombreAuxiliar.isEmpty()){
            tfNombre.setStyle("-fx-border-color: red;");
            esCorrecto = false;
        }
        if(fechaAuxiliar.isEmpty()){
            tfFecha.setStyle("-fx-border-color: red;");
            esCorrecto = false;
        }
        if(gradoConsolidacionAuxiliar.isEmpty()){
            tfGradoConsolidacion.setStyle("-fx-border-color: red;");
            esCorrecto = false;
        }
        if(adscripcionAuxiliar.isEmpty()){
            tfAdscripcion.setStyle("-fx-border-color: red;");
            esCorrecto = false;
        }
        if(unidadAdscripcionAuxiliar.isEmpty()){
            tfUnidadDeAdscripcion.setStyle("-fx-border-color: red;");
            esCorrecto = false;
        }
        if(estatusAuxiliar.isEmpty()){
            tfEstatus.setStyle("-fx-border-color: red;");
            esCorrecto = false;
        }
        
        while(iterador < tamañoDeValidacionDeClaveRepetida){
            if(validacionDeClaveRepetida.get(iterador).getClave().equalsIgnoreCase(claveAuxiliar)){
                tfClave.setStyle("-fx-border-color: red;");
                esRepetido = true;
            }
            iterador ++;
        }
        
        if(esCorrecto){
            if(!esRepetido){
                registrarCatalogoLgca(claveAuxiliar, nombreAuxiliar, fechaAuxiliar, gradoConsolidacionAuxiliar,
                    adscripcionAuxiliar, unidadAdscripcionAuxiliar, estatusAuxiliar);
                if(registroExitoso){
                    mostrarAlerta = Herramientas.creadorDeAlerta("Mensaje", "Registro exitoso", Alert.AlertType.INFORMATION);
                    mostrarAlerta.showAndWait();
                    Herramientas.cerrarPantalla(tfEstatus);
                    notificacion.refrescarTabla(true);
                }else{
                    mostrarAlerta = Herramientas.creadorDeAlerta("Error", "No fue posible completar el registro, "
                        + "intente más tarde", Alert.AlertType.ERROR);
                    mostrarAlerta.showAndWait();
                }
            }else{
                mostrarAlerta = Herramientas.creadorDeAlerta("Campos repetidos", 
                    "clave registrada previamente", Alert.AlertType.ERROR);
                mostrarAlerta.showAndWait();
            }
        }else{
            mostrarAlerta = Herramientas.creadorDeAlerta("Campos incorrectos o vacíos", 
                "Verifique su información", Alert.AlertType.ERROR);
            mostrarAlerta.showAndWait();
        }
    }
 
    
    private void registrarCatalogoLgca(String clave, String nombre, String fecha,
        String grado, String adscripcion, String unidadAdscripcion, String estatus){
        Connection conn = ConectarBD.abrirConexionMySQL();
        if(conn != null){
            try{
                String consulta = "insert into lgca(clave, nombre, fecha, grado, adscripcion, unidadAdscripcion,"
                    + "estatus) values (?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement declaracion = conn.prepareStatement(consulta);
                declaracion.setString(1, clave);
                declaracion.setString(2, nombre);
                declaracion.setString(3, fecha);
                declaracion.setString(4, grado);
                declaracion.setString(5, adscripcion);
                declaracion.setString(6, unidadAdscripcion);
                declaracion.setString(7, estatus);
                int resultado = declaracion.executeUpdate();
                if(resultado == 0){
                    registroExitoso = false;
                }
            }catch(SQLException ex){
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
    
    private void obtenerClavesRegistradas(){
        Connection conn = ConectarBD.abrirConexionMySQL();
        if(conn != null){
            try{
               String consulta = "Select clave from lgca";
               PreparedStatement declaracion = conn.prepareStatement(consulta);
               ResultSet resultado = declaracion.executeQuery();
               while(resultado.next()){
                   CatalogoLGCA clavesObtenidas = new CatalogoLGCA();
                   clavesObtenidas.setClave(resultado.getString("clave"));
                   validacionDeClaveRepetida.add(clavesObtenidas);
               }
               conn.close();
            }catch(SQLException ex){
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
}
