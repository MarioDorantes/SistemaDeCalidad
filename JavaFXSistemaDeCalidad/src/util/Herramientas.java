/*
Autor: Mario Dorantes
fechaCreación: 06/12/2020
 */

package util;

import javafx.scene.control.Alert;


public class Herramientas {
    
    public static Alert creadorDeAlerta(String titulo, String mensaje, Alert.AlertType tipo){
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        return alert;
    }
    
}
