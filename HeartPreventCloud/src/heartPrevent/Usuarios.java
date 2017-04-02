package heartPrevent;

import javafx.scene.control.TextField;

public class Usuarios {

    private static Usuarios instance=new Usuarios();
    
    public static Usuarios getInstance(){
        return instance;
    }
    
    private TextField correo;
    private TextField contrasenia;
    private String ip;

    public TextField getCorreo() {
        return correo;
    }

    /**
     * @param correo the correo to set
     */
    public void setCorreo(TextField correo) {
        this.correo = correo;
    }

    /**
     * @return the contrasenia
     */
    public TextField getContrasenia() {
        return contrasenia;
    }

    /**
     * @param contrasenia the contrasenia to set
     */
    public void setContrasenia(TextField contrasenia) {
        this.contrasenia = contrasenia;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
