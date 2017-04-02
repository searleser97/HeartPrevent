package heartPrevent;

import java.io.File;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

public class SyncController implements Initializable {

    rules.Seguridad cifrado = new rules.Seguridad();
    String passCifrada = cifrado.encriptaSHA1(Usuarios.getInstance().getContrasenia().getText());
    String correoCifrado = cifrado.encriptaAES(Usuarios.getInstance().getCorreo().getText());

    db.cDatos sql = new db.cDatos();
    ResultSet r;
    ResultSet r2;
    final ToggleGroup group = new ToggleGroup();
    @FXML
    private Button ubicacionSync;
    @FXML
    private TextField pathSync;
    @FXML
    private RadioButton todo;
    @FXML
    private RadioButton no;
    String path;
    ImageView img5 = new ImageView("img/wakeupinc.jpeg");
    @FXML
    private MenuItem linkPagNosotros;
    MenuController menu = new MenuController();
    Alerts alertVent = new Alerts();

    @FXML
    public void handleButtonAction(ActionEvent event) throws IOException, Throwable {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Elige carpeta para descargar archivos");
        File file = directoryChooser.showDialog(null);
        if (file != null) {
            pathSync.setText(file.getPath());
            path = file.getPath();
        }
    }

    @FXML
    public void guardar(ActionEvent event) throws IOException, Throwable {
        sql.conectar();
        if (todo.isSelected()) {
            r = sql.consulta("call verajustes('" + correoCifrado + "');");
            if (r.next()) {
                if (!r.getString("path").contains(pathSync.getText())) {
                    eliminar();
                    sql.actualizar("call path('" + correoCifrado + "','" + path.replace('\\', '/') + "/HeartCloud');");
                    menu.cargarPDFs();
                    ResultSet r2 = sql.consulta("call traepacientes2('" + correoCifrado + "')");
                    if (r2.next()) {
                        if (r2.getInt("msj") == 2) {
                            menu.cargarArchivosPacientes();
                        }
                    }
                }
            }
            sql.actualizar("call modajustes('" + correoCifrado + "',1,1);");
        } else if (no.isSelected()) {
            sql.actualizar("call modajustes('" + correoCifrado + "',1,0);");
        }
        MenuController.ventanaNubeP.close();
    }

    public void eliminar() throws SQLException {
        sql.conectar();
        r = sql.consulta("call archivospersonat('" + correoCifrado + "');");
        ResultSet r2 = sql.consulta("call verajustes('" + correoCifrado + "');");
        ResultSet r3 = sql.consulta("call traepacientesarch('" + correoCifrado + "');");

         Alert alert = new Alert(Alert.AlertType.INFORMATION);
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image(this.getClass().getResource("/img/logo.png").toString()));
            alert.setTitle("Cloud Sync");
            alert.setHeaderText("Eliminando archivos...");
            alert.setContentText( "Por favor espere.");

        if (r2.next()) {
            String q = r2.getString("path");
            while (r3.next()) {
                File archUsuarios = new File(q + "/" + r3.getString("Nombre") + "/" + r3.getString("name"));
                archUsuarios.delete();
                if (r3.last()) {
                    File carUsuarios = new File(q + "/" + r3.getString("Nombre"));
                    carUsuarios.delete();
                }
            }
            while (r.next()) {
                File archivosNube = new File(q + "/" + r.getString("name"));
                if (archivosNube.exists()) {
                    archivosNube.delete();
                }
            }
            if (r2.last()) {
                File carp = new File(q);
                carp.delete();
                if (carp.list() != null || carp.exists()) {
                    Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                    alert2.setTitle("Cloud Sync");
                    alert2.setHeaderText("Aviso");
                    alert2.setContentText("Hay archivos todavia en la carpeta HeartCloud. Espere a que se borren.");
                    alert2.setGraphic(new ImageView(this.getClass().getResource("/img/Recycle-Bin.png").toString()));
                    alert2.show();

                    String[] ficheros = carp.list();
                    String nomA;
                    for (String fichero : ficheros) {
                        nomA = fichero;
                        File noBorrado = new File(q + nomA);
                        noBorrado.delete();
                    }
                    alert.close();
                    if (carp.list() != null || carp.exists()) {
                        alertVent.Alerts("Cloud Sync", "Aviso",
                                "Imposible borrar todos los archivos.", "/img/Recycle-Bin.png", 1);
                    }
                }
                alert.close();
            }
        }
    }

    @FXML
    private void linkNosotros(ActionEvent event) {
        menu.linkNosotros(event);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            linkPagNosotros.setGraphic(img5);
            sql.conectar();
            todo.setToggleGroup(group);
            no.setToggleGroup(group);
            r = sql.consulta("call verajustes('" + correoCifrado + "');");
            if (r.next()) {
                if (r.getInt("syncact") == 1) {
                    todo.setSelected(true);
                } else if (r.getInt("syncact") == 0) {
                    no.setSelected(true);
                }
                pathSync.setText(r.getString("path"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(SyncController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
