/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Silvester
 */
@WebServlet("/sections/mensaje")
public class mensaje extends HttpServlet {

    private static final long serialVersionUID = 1L;

    public mensaje() {
        super();

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        rules.Seguridad cifrado = new rules.Seguridad();
        try {
            HttpSession sesion = request.getSession();
            String UsrId = (String) sesion.getAttribute("UsrId");
            String msj = cifrado.stremplaza(request.getParameter("mensaje"));
            String idrelusus = request.getParameter("salachat");
            db.Condb con = new db.Condb();
            con.conectar();
            con.consulta("call mensajes('2','" + idrelusus + "','" + UsrId + "','" + msj + "')");
            con.cierraConexion();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

}
