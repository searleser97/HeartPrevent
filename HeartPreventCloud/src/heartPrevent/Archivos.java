package heartPrevent;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

/**
 *
 * @author ACER-PC
 */
public class Archivos {

    rules.Seguridad cifrado = new rules.Seguridad();
    String passCifrada = cifrado.encriptaSHA1(Usuarios.getInstance().getContrasenia().getText());
    String correoCifrado = cifrado.encriptaAES(Usuarios.getInstance().getCorreo().getText());

    private final SimpleStringProperty nombreA;
    private final SimpleStringProperty fechaA;
    private final SimpleLongProperty sizeA;
    private final Button ver;
    private final ImageView typeA;
    static ResultSet r;
    String tipoImagen;
    MenuController menu = new MenuController();

    public Archivos(String nombreA, String fechaA, Long sizeA, Button ver, ImageView typeA,String nomfolder) {
        this.nombreA = new SimpleStringProperty(nombreA);
        this.fechaA = new SimpleStringProperty(fechaA);
        this.sizeA = new SimpleLongProperty(sizeA);
        this.ver = ver;
        this.ver.setStyle(
                "-fx-background-color:#098882"
                + "  radial-gradient(center 50% 50%, radius 100%, #d86e3a, #c54e2c);"
                + " -fx-effect: dropshadow( gaussian , rgba(0,0,0,0.75) , 4,0,0,1 );"
                + "-fx-text-fill: white;"
                + " -fx-font-weight: bold;"
                + " -fx-font-size: 1.1em;"
        );

        if (nombreA.endsWith(".pdf")) {
            tipoImagen = "/img/pdf1.png";
        } else if (nombreA.toLowerCase().endsWith(".png") || nombreA.toLowerCase().endsWith(".jpg") || nombreA.toLowerCase().endsWith(".jpeg") || nombreA.toLowerCase().endsWith(".gif") || nombreA.toLowerCase().endsWith(".ico")) {
            tipoImagen = "/img/img.png";
        }
        else if(nombreA.toLowerCase().endsWith(".docx") || nombreA.toLowerCase().endsWith(".doc")){
            tipoImagen = "/img/docimg.png";
        }
//        else if (nombreA.contains(".docx")) {
//            tipoImagen = "/img/word.png";
//        }
        this.typeA = new ImageView(tipoImagen);
        
        ver.setOnMouseReleased(new EventHandler() {
            @Override
            public void handle(Event arg) {
                try {
                    db.cDatos bd = new db.cDatos();
                    bd.conectar();
                    menu.cargarPDFs();
                    r = bd.consulta("call verajustes('" + correoCifrado + "');");
                    if (r.next()) {
                        File pdf = new File(r.getString("path") + "/"+nomfolder+"/" + nombreA);
                        if (Desktop.isDesktopSupported()) {
                            Desktop.getDesktop().open(pdf);
                        } 
                        else {
                            Process p = Runtime.getRuntime().exec(
                                    "rundll32 url.dll,FileProtocolHandler "
                                    + pdf.getAbsolutePath());
                        }
                    
                    }
                } catch (IOException | SQLException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        });
    }

    public Long getSizeA() {
        return sizeA.get();
    }

    public ImageView getTypeA() {
        return typeA;
    }

    public Button getVer() {
        return ver;
    }

    public String getNombreA() {
        return nombreA.get();
    }

    public String getFechaA() {
        return fechaA.get();
    }

    public void setNombreA(String nombreA) {
        this.nombreA.set(nombreA);
    }
}
