package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.application.Platform;

/**
 *
 * @author Ger
 */
public class cDatos {

    private String usrBD;
    public String ip;
    private String passBD;
    private String urlBD;
    private String driverClassName;
    private Connection conn = null;
    private Statement estancia;
    public String webdir="http://192.168.0.8:46750/HPNet/";
    public String webdirnosotros="http://localhost:46750/HeartPreventWeb/";
    heartPrevent.Alerts alert=new heartPrevent.Alerts();

    public cDatos(String usuarioBD, String passwordBD, String url, String driverClassName) {
        this.usrBD = usuarioBD;
        this.passBD = passwordBD;
        this.urlBD = url;
        this.driverClassName = driverClassName;
    }

    public cDatos() {
        //poner los datos apropiados
//        this.usrBD = "usH34rt";
        this.usrBD = "root";
//        this.passBD = "Pr3v3nt16*";
        this.passBD = "n0m3l0";
//        this.ip = "www.prograbatiz.com";
        this.ip = "localhost";
//        this.urlBD = "jdbc:mysql://" + ip + ":3306/prograbatiz_heartprevent";
        this.urlBD = "jdbc:mysql://" + ip + ":3306/redmedica";
        this.driverClassName = "com.mysql.jdbc.Driver";
    }

    //metodos para establecer los valores de conexion a la BD
    public void setUsuarioBD(String usuario) throws SQLException {
        this.usrBD = usuario;
    }

    public void setPassBD(String pass) {
        this.passBD = pass;
    }

    public void setUrlBD(String url) {
        this.urlBD = url;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public void setConn(Connection conn) {
        this.conn = conn;
    }

    public void setDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
    }

    //Conexion a la BD
    public void conectar() throws SQLException {
        try {
            Class.forName(this.driverClassName).newInstance();
            this.conn = DriverManager.getConnection(this.urlBD, this.usrBD, this.passBD);
        } catch (Exception err) {
            Platform.exit();
            alert.Alerts("Cloud Error", "Error en la conexion", 
                    "No se ha podido conectar", null, 0);            
        } 
    }

    public Connection getConexion() {
        try {
            Class.forName(this.driverClassName).newInstance();
            this.conn = DriverManager.getConnection(this.urlBD, this.usrBD, this.passBD);
        } catch (Exception err) {
            System.out.println("Error " + err.getMessage());
        }
        return conn;
    }

    //Cerrar la conexion de BD
    public void cierraConexion() throws SQLException {
        this.conn.close();
    }

    //Metodos para ejecutar sentencias SQL
    public ResultSet consulta(String consulta) throws SQLException {
        this.estancia = (Statement) conn.createStatement();
        return this.estancia.executeQuery(consulta);
    }

    public void actualizar(String actualiza) throws SQLException {
        this.estancia = (Statement) conn.createStatement();
        estancia.executeUpdate(actualiza);
    }

    public ResultSet borrar(String borra) throws SQLException {
        Statement st = (Statement) this.conn.createStatement();
        return (ResultSet) st.executeQuery(borra);
    }

    public int insertar(String inserta) throws SQLException {
        Statement st = (Statement) this.conn.createStatement();
        return st.executeUpdate(inserta);
    }
}
