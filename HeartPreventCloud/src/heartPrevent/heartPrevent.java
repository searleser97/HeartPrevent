package heartPrevent;

import java.net.Socket;
import java.io.File;
import javafx.scene.image.Image;
import java.io.IOException;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class heartPrevent extends Application {
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    public static Stage ventanaNubeLogin;
    File fichero = new File("sitzung.dat");
    db.cDatos bd = new db.cDatos();

    @Override
    public void start(Stage stage) throws Exception {
        String dirWeb = "www.google.com";
        int puerto = 80;
        try {
            Socket s = new Socket(dirWeb, puerto);
            if (s.isConnected()) {
                ventanaNubeLogin = stage;
                inicializar(ventanaNubeLogin);

                if (!fichero.exists()) {
                    ventanaNubeLogin.show();
                }
            }
            s.close();
        } catch (Exception e) {
//            Alert alert = new Alert(Alert.AlertType.INFORMATION);
//            Stage stage1 = (Stage) alert.getDialogPane().getScene().getWindow();
//            stage1.getIcons().add(new Image(this.getClass().getResource("/img/logo.png").toString()));
//            alert.setGraphic(new ImageView(this.getClass().getResource("/img/warn.png").toString()));
//            alert.setTitle("HeartCloud");
//            alert.setHeaderText("No hay conexion.");
//            alert.setContentText("Mueva sus archivos a la carpeta de HeartCloud y despues seran sincronizados.");
//            alert.show();
        }

    }

    private void inicializar(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/FXML/Log.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.getIcons().add(new Image("/img/logo.png"));
        stage.setResizable(false);
        stage.setTitle("HeartCloud");
    }

    
}
