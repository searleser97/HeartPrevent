package heartPrevent;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.JRException;

public class LoginController implements Initializable {

    public static Stage ventanaNubeLoad;
    ResultSet r;

    @FXML
    private Button log;
    @FXML
    private PasswordField pass;
    @FXML
    private ImageView img;
    @FXML
    private Hyperlink linkP;
    @FXML
    private TextField correoCT;
    File fichero = new File("sitzung.dat");

    db.cDatos sql = new db.cDatos();
    rules.Mail mail = new rules.Mail();
    Alerts alertVent = new Alerts();
    rules.Seguridad cifrado = new rules.Seguridad();

    @FXML
    public void handleButtonAction(ActionEvent event) throws IOException, Throwable {
        if (correoCT.getText().equals("") || pass.getText().equals("")) {
            alertVent.Alerts("Cloud Login Error", "Ingrese datos.",
                    "Ingrese correctamente sus datos.", null, 0);
        } else {
            log();
        }
    }

    public void log() throws IOException, InterruptedException, JRException, SQLException, FileNotFoundException {
        
        String passCifrada = cifrado.encriptaSHA1(Usuarios.getInstance().getContrasenia().getText());
        String correoCifrado = cifrado.encriptaAES(Usuarios.getInstance().getCorreo().getText());

        sql.conectar();
        r = sql.consulta("call validausrnube('" + correoCifrado + "','" + passCifrada + "');");

        if (r.next()) {
            if (r.getInt("msg") == 0) {
                alertVent.Alerts("Cloud Login Error", "Error en los datos.",
                        "Ingrese correctamente sus datos.", null, 0);
                pass.setText("");
               
            } else if (r.getInt("msg") == 1) {

                ventanaNubeLoad = new Stage();
                Parent root = FXMLLoader.load(getClass().getResource("/FXML/Menu.fxml"));
                Scene escenaMenu = new Scene(root);
                ventanaNubeLoad.setScene(escenaMenu);
                ventanaNubeLoad.setTitle("Cloud - Sesion iniciada como " + correoCT.getText());
                ventanaNubeLoad.getIcons().add(new Image("/img/logo.png"));
                ventanaNubeLoad.setResizable(false);

                if (!fichero.exists()) {
                    try {
                        creaArchivo(Usuarios.getInstance().getCorreo().getText(), Usuarios.getInstance().getContrasenia().getText());
                        heartPrevent.ventanaNubeLogin.close();
                    } catch (IOException ex) {
                        Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                ventanaNubeLoad.show();
            } else if (r.getInt("msg") == 2) {
                alertVent.Alerts("Cloud Login Error", "No tiene permisos para entrar.",
                        "Es necesario que actualize a Premium.", "/img/warn.png", 1);
                pass.setText("");
                pass.requestFocus();
            }
        }
    }

    @FXML
    public void handleButtonAction1(ActionEvent event) throws IOException, Throwable {
        rules.Seguridad cifrado = new rules.Seguridad();
        TextInputDialog dialog = new TextInputDialog();
        Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(this.getClass().getResource("/img/logo.png").toString()));
        dialog.setTitle("Pass recovery");
        dialog.setHeaderText("Ha olvidado su contraseña?");
        dialog.setContentText("Ingrese su Correo de Recuperacion:");
        dialog.setGraphic(new ImageView(this.getClass().getResource("/img/icon-email.png").toString()));

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            mail.Mail(cifrado.encriptaAES(result.get()));
        }
    }

    @FXML
    public void link(ActionEvent event) throws URISyntaxException {
        if (java.awt.Desktop.isDesktopSupported()) {
            try {
                Desktop dk = Desktop.getDesktop();
                dk.browse(new URI(sql.webdir+"start"));
            } catch (URISyntaxException | IOException e) {
                System.out.println("Error al abrir URL: " + e.getMessage());
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (fichero.exists()) {
            FileReader fr = null;
            try {
                fr = new FileReader(fichero);
                BufferedReader br = new BufferedReader(fr);
                String recordar1 = br.readLine();
                String recordar2 = br.readLine();
                correoCT.setText(cifrado.decriptaAES(recordar1));
                pass.setText(recordar2);
                Usuarios.getInstance().setCorreo(correoCT);
                Usuarios.getInstance().setContrasenia(pass);
                log();
            } catch (FileNotFoundException ex) {
                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InterruptedException | JRException | SQLException ex) {
                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    fr.close();
                } catch (IOException ex) {
                    Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else {
            Usuarios.getInstance().setCorreo(correoCT);
            Usuarios.getInstance().setContrasenia(pass);
        }
//        correoCT.requestFocus();
    }

    public void creaArchivo(String user, String psw) throws IOException {
        fichero.createNewFile();
        FileWriter fw = new FileWriter("sitzung.dat");
        PrintWriter pw = new PrintWriter(fw);
        pw.println(cifrado.encriptaAES(user));
        pw.println(psw);
        fw.close();
        fichero.setReadOnly();
    }
}
