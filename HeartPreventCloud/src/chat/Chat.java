package chat;

import heartPrevent.Usuarios;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.NodeOrientation;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;

public class Chat implements Initializable {

    heartPrevent.Alerts alerts = new heartPrevent.Alerts();
    rules.Seguridad cifrado = new rules.Seguridad();
    String passCifrada = cifrado.encriptaSHA1(Usuarios.getInstance().getContrasenia().getText());
    String correoCifrado = cifrado.encriptaAES(Usuarios.getInstance().getCorreo().getText());

    @FXML
    private Label label;
    @FXML
    private Button button;
    @FXML
    private TextArea panMostrar;
    @FXML
    private ListView<String> lstActivos;
    @FXML
    private Label lblNomUser;
    @FXML
    private TextField txtMensaje;

    Cliente cliente;
    ObservableList<String> nomUsers = FXCollections.observableArrayList();
    db.cDatos sql = new db.cDatos();

    @FXML
    private Button p;
    @FXML
    private Label ultConexion;
    @FXML
    private ImageView imgFriend;

    @FXML
    private void enviarButton(ActionEvent event) throws SQLException {
        if (txtMensaje.getText().length() < 150) {
            sendMessage();
        } else {
            alerts.Alerts("Heart Prevent Chat", "No es posible enviar el mensaje.",
                    "El mensaje contiene más caracteres de los permitidos.", null, 0);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        try {
            cliente = new Cliente(this);
            cliente.conexion();
            ponerActivos(cliente.pedirUsuarios());
            lstActivos.getSelectionModel().select(0);
            setNombreUser(lstActivos.getSelectionModel().getSelectedItem());
            cargarConver(lblNomUser.getText(), lstActivos.getSelectionModel().getSelectedItem());

            lstActivos.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observable,
                        String oldValue, String newValue) {
                    try {
                        setNombreUser(newValue);
                        cargarConver(lblNomUser.getText(), newValue);
                    } catch (SQLException ex) {
                        Logger.getLogger(Chat.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
        } catch (IOException ex) {
            Logger.getLogger(Chat.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Chat.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setNombreUser(String user) {
        lblNomUser.setText(user);
    }

    public void mostrarMsg(String msg) {
        int cont = 0;
        int fin = 34;
        if (msg.length() > 35) {
            while (msg.length() > cont) {
//              ultima fila
                if (fin > msg.length()) {
                    panMostrar.appendText(msg.substring(cont, msg.length()) + "\n");
                } else {
//                    si hay espacio en la cadena q voy
//                    if (msg.substring(cont, fin).contains(" ")) {
//                        msg.substring(cont, fin).
//                    } else {
//                        normal
                    panMostrar.appendText(msg.substring(cont, fin) + "\n");
//                    }
                }
                cont += 34;
                fin += 34;
//                if ((cant % fin) < 25) {
//                    break;
//                }
            }
        } else {
            panMostrar.appendText(msg + "\n");
        }
    }

    public void ponerActivos(ObservableList datos) {
        nomUsers = datos;
        ponerDatosList(this.lstActivos, nomUsers);
    }

    public void agregarUser(String user) {
        nomUsers.add(user);
        ponerDatosList(this.lstActivos, nomUsers);
    }

    public void retiraUser(String user) {
        nomUsers.remove(user);
        ponerDatosList(this.lstActivos, nomUsers);
    }

    private void ponerDatosList(ListView list, final ObservableList datos) {
        list.setItems((ObservableList) datos);
    }

    @FXML
    private void desconectar(ActionEvent event) throws SQLException {
        sql.conectar();
        ResultSet rNom = sql.consulta("call mensajeschat(5,'','" + correoCifrado + "','');");
        while (rNom.next()) {
            retiraUser(rNom.getString("Nombre"));
        }
        heartPrevent.MenuController.ventanaNubeP.close();
    }

    public void cargarConver(String yo, String friend) throws SQLException {
        panMostrar.clear();
        sql.conectar();
        if (lstActivos.getSelectionModel().getSelectedItem().equals("Chat Global")) {
            Image imgFr = new Image("img/global.jpg");
            imgFriend.setImage(imgFr);

            ResultSet rMensajes = sql.consulta("call mensajeschat(3,'','" + correoCifrado + "','');");
            boolean check = true;
            while (rMensajes.next()) {
                check = false;
                if (rMensajes.getString("Correo").equals(correoCifrado)) {
//                    Text text1 = new Text(rMensajes.getString("Nombre") + ": ");
//                    text1.setStyle("-fx-font-weight: bold");
//                    Text text2 = new Text(rMensajes.getString("Mensaje") + "\n");
//                    text2.setStyle("-fx-font-weight: regular");
//                    TextFlow flow = new TextFlow(text1, text2);
                    panMostrar.appendText("Yo: " + rMensajes.getString("Mensaje") + "\n");
                } else {
                    panMostrar.appendText(rMensajes.getString("Nombre") + ": ");
                    panMostrar.appendText(": " + rMensajes.getString("Mensaje") + "\n");
                }
                if (rMensajes.isLast()) {
                    ultConexion.setText("Ultimo mensaje recibido a las " + rMensajes.getString("horaChat")
                            + " el " + rMensajes.getString("fechaChat") + ".");
                }
            }
            if (check == true) {
                ultConexion.setText("No se tienen mensajes de este usuario.");
                panMostrar.appendText("Inicie una conversacion con " + lstActivos.getSelectionModel().getSelectedItem() + ".\n");
            }
        } else {
            ResultSet rMensajes = sql.consulta("call mensajeschat(1,'" + lstActivos.getSelectionModel().getSelectedItem() + "','" + correoCifrado + "','');");
            boolean check = true;
            while (rMensajes.next()) {
                check = false;
                if (rMensajes.getString("Correo").equals(correoCifrado)) {
                    panMostrar.appendText("Yo: " + rMensajes.getString("Mensaje")
                            + "\n");
                } else {
//                    panMostrar.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
                    panMostrar.appendText(lstActivos.getSelectionModel().getSelectedItem());
                    panMostrar.appendText(": " + rMensajes.getString("Mensaje") + "\n");
                }
                if (rMensajes.isLast()) {
                    Image imgFr = new Image("http://" + sql.ip + ":8080/HPNet/UsrImagenes/" + rMensajes.getString("Imagen"));
                    imgFriend.setImage(imgFr);
                    ultConexion.setText("Ultimo mensaje recibido a las " + rMensajes.getString("horaChat") + " el " + rMensajes.getString("fechaChat") + ".");
                }
            }
            if (check == true) {
                ultConexion.setText("No se tienen mensajes de este usuario.");
                panMostrar.appendText("Inicie una conversacion con " + lstActivos.getSelectionModel().getSelectedItem() + ".\n");
            }
        }
        sql.cierraConexion();
    }

    @FXML
    private void enviarButtonEnter(KeyEvent event) throws SQLException {
        if (event.getCode().getName().equals("Enter")) {
            sendMessage();
        }
    }

    public void sendMessage() throws SQLException {
        String mensaje = txtMensaje.getText();
        if (!mensaje.equals("") && !mensaje.equals(" ")) {
            if (panMostrar.getText().equals("Inicie una conversacion con " + lstActivos.getSelectionModel().getSelectedItem() + ".\n")) {
                panMostrar.clear();
            }
            cliente.flujo(mensaje);
            txtMensaje.setText("");
            sql.conectar();
            if (lstActivos.getSelectionModel().getSelectedItem().equals("Chat Global")) {
                sql.insertar("call mensajeschat(4,'','" + correoCifrado + "','" + mensaje + "');");
            } else {
                sql.insertar("call mensajeschat(2,'" + lstActivos.getSelectionModel().getSelectedItem() + "','" + correoCifrado + "','" + mensaje + "');");
            }
            txtMensaje.setText("");
            sql.cierraConexion();
        }
    }
}
