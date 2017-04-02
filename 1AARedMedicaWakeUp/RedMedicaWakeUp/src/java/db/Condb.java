/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Ger
 */
public class Condb {

    private String usrBD;
    private String passBD;
    private String urlBD;
    private String driverClassName;
    private Connection conn = null;
    private Statement estancia;
    public String urlGen;

    public Condb(String usuarioBD, String passwordBD, String url, String driverClassName) throws UnknownHostException {
        this.usrBD = usuarioBD;
        this.passBD = passwordBD;
        this.urlBD = url;
        this.driverClassName = driverClassName;
    }

    public Condb() throws UnknownHostException {
        //poner los datos apropiados

//        this.usrBD = "usH34rt";
//        this.passBD = "Pr3v3nt16*";
//        this.urlGen="www.prograbatiz.com";
//        this.urlBD = "jdbc:mysql://"+urlGen+":3306/prograbatiz_heartprevent";        
        //LOCAL///////////////////////////////////////////////////////////////////
        this.usrBD = "root";
        this.passBD = "n0m3l0";
        this.urlGen = Inet4Address.getLocalHost().getHostAddress();

//        //LINUX//////////////
        this.urlBD = "jdbc:mysql://localhost:3306/redmedica";
        //this.urlBD = "jdbc:mysql://"+urlGen+":3306/redmedica";

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
            System.out.println("Error " + err.getMessage());
        }
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

    public Connection getConexion() {
        try {
            Class.forName(this.driverClassName).newInstance();
            this.conn = DriverManager.getConnection(this.urlBD, this.usrBD, this.passBD);
        } catch (Exception err) {
            System.out.println("Error " + err.getMessage());
        }
        return conn;
    }
}
