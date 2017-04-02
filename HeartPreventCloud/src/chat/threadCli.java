package chat;

import heartPrevent.MenuController;
import java.io.*;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;

class threadCli extends Thread {

    DataInputStream entrada;
    Chat vcli;
    heartPrevent.Alerts alert=new heartPrevent.Alerts();

    public threadCli(DataInputStream entrada, Chat vcli) throws IOException {
        this.entrada = entrada;
        this.vcli = vcli;
    }

    public void run() {
        String menser = "", amigo = "";
        int opcion = 0;
        while (true) {
            try {
                opcion = entrada.readInt();
                switch (opcion) {
                    case 1:
                        menser = entrada.readUTF();
                        vcli.mostrarMsg(menser);
                        break;
                    case 2:
                        menser = entrada.readUTF();
                        vcli.agregarUser(menser);
                        break;
                    case 3:
                        amigo = entrada.readUTF();
                        menser = entrada.readUTF();
                        break;
                }
            } catch (IOException e) {
                System.out.println("Se ha perdido la conexion");
                break;
            }
        }
        alert.Alerts("Cloud Configuracion", "Conexion Perdida.", "Intente nuevamente.", null, 2);
        MenuController.ventanaNubeP.close();
    }
}
