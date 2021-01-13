/*
Autor: Mario Dorantes
fechaCreación: 06/12/2020
 */

package util;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;


public class Herramientas {
    
    public static Alert creadorDeAlerta(String titulo, String mensaje, Alert.AlertType tipo){
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        return alert;
    }
    
    public static void cerrarPantalla (TextField tfParaObtenerEscena){
        Stage stage = (Stage) tfParaObtenerEscena.getScene().getWindow();
        stage.close();
    }
    
    public static void contadorDeCaracteres(TextArea areaAEscuchar, KeyEvent evento){
        int numeroCaracteresMision = areaAEscuchar.getText().length();
        if(numeroCaracteresMision >= 200){
           evento.consume();
        }
    }
    
}
