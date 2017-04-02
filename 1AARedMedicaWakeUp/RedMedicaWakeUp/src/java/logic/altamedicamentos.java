/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
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
@WebServlet(name = "altamedicamentos", urlPatterns = {"/altamedicamentos"})
public class altamedicamentos extends HttpServlet {


    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        //processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<title>ServletAlta</title>");
        out.println("<script src='" + request.getContextPath() + "/sweetalert/sweetalert.min.js'></script>");
        out.println("<link rel='stylesheet' href='" + request.getContextPath() + "/sweetalert/sweetalert.css' type='text/css' media='all'/>");
        out.println("</head>");
        out.println("<body>");
        HttpSession sesion = request.getSession();
        String UsrId = (String) sesion.getAttribute("UsrId");
        String medicamento = request.getParameter("combomedicamento");
        String fechini = request.getParameter("fechini");
        String fechfin = request.getParameter("fechfin");
        String periodo = request.getParameter("periodo");
        System.out.println("m:" + medicamento + "fi:" + fechini + "ff:" + fechfin + "p:" + periodo);
        db.Condb con = new db.Condb();
        try {
            con.conectar();
            ResultSet rs = con.consulta("call medicamentos('1','" + UsrId + "','" + medicamento + "','" + fechini + "','" + fechfin + "','" + periodo + "')");
            if(rs.next())
            {
                if(rs.getString("valor").equals("exito"))
                {
                     //response.sendRedirect(response.encodeRedirectURL("http://localhost:8080/HPNet/sections/medicamentos.jsp"));
            response.sendRedirect(response.encodeRedirectURL(request.getContextPath()+"/sections/medicamentos.jsp"));
                }
                else
                {
                    out.println("<script>"
                                + "function redirec(){window.location='" + request.getContextPath() + "/sections/InicioRed.jsp';}"
                                + " setTimeout('redirec()',2000);"
                                + "swal({title: 'Sección solo para usuarios premium',animation:'slide-from-top',type: 'warning'});");
                        out.println("</script>");
                }
            }
           

        } catch (SQLException ex) {
            response.sendRedirect(response.encodeRedirectURL(ex.getMessage()));
        }

    }


}
