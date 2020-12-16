package JavaFXGUI.Ventanas;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class SistemaDeCalidadMain extends Application {

   
    @Override
    public void start(Stage stage) throws Exception {
        
        //HASTA NO TENER EL LOGIN Y SABER QUE TIPO DE USUARIO ENTRA, DEBEMOS COMENTAR EL CONTRARIO 
        
        Parent root = FXMLLoader.load(getClass().getResource("FXMLInicioDeSesion.fxml"));
        
        //Parent root = FXMLLoader.load(getClass().getResource("FXMLVentanaPrincipalDirectorDeLaFacultad.fxml"));
                        
        //Parent root = FXMLLoader.load(getClass().getResource("FXMLVentanaPrincipalDocente.fxml"));
        
              
        //Parent root = FXMLLoader.load(getClass().getResource("FXMLVentanaPrincipalCoordinador.fxml"));
       
                        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
