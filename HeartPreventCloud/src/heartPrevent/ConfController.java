package heartPrevent;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.ImageView;

public class ConfController implements Initializable {

    rules.Seguridad cifrado = new rules.Seguridad();
    String passCifrada = cifrado.encriptaSHA1(Usuarios.getInstance().getContrasenia().getText());
    String correoCifrado = cifrado.encriptaAES(Usuarios.getInstance().getCorreo().getText());

    ResultSet r;
    db.cDatos bd = new db.cDatos();
    int totalCuenta;

    @FXML
    private Label correoU;
    private Button borrarF;
    @FXML
    private ProgressIndicator tamanoCuenta;
    @FXML
    private CheckBox inicioOn;
    @FXML
    private Label totalMB;

    ImageView img5 = new ImageView("img/wakeupinc.jpeg");

    MenuController menu = new MenuController();

    @FXML
    private MenuItem linkPagNosotros1;

    @FXML
    private MenuItem linkHelp;

    @FXML
    public void handleButtonAction1(ActionEvent event) throws IOException, SQLException {
        bd.conectar();
        if (inicioOn.isSelected()) {
            bd.actualizar("call modajustes('" + correoCifrado + "',0,1);");
        } else {
            bd.actualizar("call modajustes('" + correoCifrado + "',0,0);");
        }
        MenuController.ventanaNubeP.close();
    }

    @FXML
    private void linkNosotros(ActionEvent event) {
        menu.linkNosotros(event);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            correoU.setText(cifrado.decriptaAES(correoCifrado));
            linkPagNosotros1.setGraphic(img5);
            bd.conectar();
            r = bd.consulta("call verajustes('" + correoCifrado + "');");
            if (r.next()) {
                if (r.getInt("inicio") == 1) {
                    inicioOn.setSelected(true);
                }
            }
            ResultSet r2 = bd.consulta("call archivospersonat('" + correoCifrado + "');");
            while (r2.next()) {
                totalCuenta += r2.getBlob("file").length();
            }
            tamanoCuenta.setProgress((totalCuenta / (1024 * 1024)) / 10F);
//PONER 100F
            totalMB.setText("Quedan disponibles: " + Math.ceil((double) (104857600 - totalCuenta) / (1024 * 1024)) + " MB");
        } catch (SQLException ex) {
            Logger.getLogger(ConfController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void terminosCond(ActionEvent event) throws IOException {
        Process p = Runtime.getRuntime().exec(
                "rundll32 url.dll,FileProtocolHandler "
                + "src\\CSS\\cond.pdf");
    }

    @FXML
    private void help(ActionEvent event) throws IOException {
        Process p = Runtime.getRuntime().exec(
                "rundll32 url.dll,FileProtocolHandler "
                + "src\\CSS\\help.pdf");
    }
}
