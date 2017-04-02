package chat;

import heartPrevent.MenuController;
import heartPrevent.Usuarios;
import java.io.*;
import java.net.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Cliente {

    db.cDatos sql = new db.cDatos();
    heartPrevent.Alerts alert = new heartPrevent.Alerts();

    public String IP_SERVER = "localhost";
    Chat vent;
    DataInputStream entrada = null;
    DataOutputStream salida = null;
    DataInputStream entrada2 = null;
    Socket comunication = null;
    Socket comunication2 = null;

    rules.Seguridad cifrado = new rules.Seguridad();
    String passCifrada = cifrado.encriptaSHA1(Usuarios.getInstance().getContrasenia().getText());
    String correoCifrado = cifrado.encriptaAES(Usuarios.getInstance().getCorreo().getText());
    String nomCliente = "";

    public Cliente(Chat vent) throws IOException {
        this.vent = vent;
    }

    public void conexion() throws IOException, SQLException {
        try {
            comunication = new Socket(IP_SERVER, 8081);
            comunication2 = new Socket(IP_SERVER, 8082);
            entrada = new DataInputStream(comunication.getInputStream());
            salida = new DataOutputStream(comunication.getOutputStream());
            entrada2 = new DataInputStream(comunication2.getInputStream());

            nomCliente = "Yo";
            
            vent.setNombreUser(nomCliente);
            salida.writeUTF(nomCliente);
            new threadCli(entrada2, vent).start();
        } catch (IOException e) {
            alert.Alerts("Chat Desconectado", "No hay Conexion a Servidor", null,
                    "/img/info.png", 3);
            MenuController.ventanaNubeP.close();
        }
    }

    public String getNombre() {
        return nomCliente;
    }

    public ObservableList<String> pedirUsuarios() throws SQLException, IOException {
        ObservableList<String> users = FXCollections.observableArrayList();
        sql.conectar();
        ResultSet r = sql.consulta("call friendsChat(0,'" + correoCifrado + "');");
        users.add("Chat Global");
        while (r.next()) {
            users.add(r.getString("Nombre"));
        }
//        salida.writeInt(2);
//        int numUsers = entrada.readInt();
//        for (int i = 0; i < numUsers; i++) {
//            users.add(entrada.readUTF());
//            System.out.println(entrada.readUTF());
//        }
//        if(.equals("Yhael")){
//            
//        }
        return users;
    }

    public void flujo(String mens) {
        try {
            salida.writeInt(1);
            salida.writeUTF(": " + mens);
        } catch (IOException e) {
            System.out.println("ex");
        }
    }
}
