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
 * @author Alumno
 */
@WebServlet(name = "solicitudes", urlPatterns = {"/solicitudes"})
public class solicitudes extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        rules.Seguridad cifrado = new rules.Seguridad();
        try {
            String referer = request.getHeader("Referer");

            PrintWriter out = response.getWriter();
            HttpSession sesion = request.getSession();
            String UsrId = (String) sesion.getAttribute("UsrId");
            String opc = request.getParameter("opc");
            String IdAmigo = request.getParameter("idamigo");
            String comentario = request.getParameter("comentario");
            String idpublicacion = request.getParameter("idpublicacion");
            String idcom = request.getParameter("idcomentario");
            String Usrcatego = "2";
            db.Condb con = new db.Condb();
            con.conectar();
            switch (opc) {
                case "1":
                    response.setContentType("text/html;charset=UTF-8");
                    ResultSet amigos = con.consulta("call friends('1','" + UsrId + "','" + IdAmigo + "')");
                    if (amigos.next()) {
                        if (amigos.getString("quefue").equals("confirma")) {
                            ResultSet rschat = con.consulta("call friends('0','" + UsrId + "',0)");
                            String imgchat = "";
                            while (rschat.next()) {
                                if (!rschat.getString("idpersona").equals(UsrId)) {
                                    String onoff = "";
                                    ResultSet onlineono = con.consulta("call onlineono('2','" + rschat.getString("idpersona") + "')");
                                    if (onlineono.next()) {

                                        onoff = onlineono.getString("css");
                                    }
                                    imgchat = request.getContextPath() + "/UsrImagenes/" + rschat.getString("Imagen");
                                    out.print("<form id=\"chatforma" + rschat.getString("idpersona") + "\" action=\"chat.jsp\" method=\"post\" class=\"divchatin\">\n"
                                            + "<input type=\"hidden\" name=\"chat\" value=\"" + rschat.getString("idpersona") + "\">\n"
                                            + "                    <a class=\"linkchat puntero\" onclick=\"document.getElementById('chatforma" + rschat.getString("idpersona") + "').submit()\">\n"
                                            + "                        <img src=\"" + imgchat + "\" class=\"imgchat\"/>\n"
                                            + rschat.getString("Nombre") + " " + rschat.getString("apellido") + "\n"
                                            + "                        <div class=\"onoff\" style=\"background:" + onoff + "\"></div>\n"
                                            + "                    </a>\n"
                                            + "\n"
                                            + "                </form>\n");

                                }
                            }
                        } else {

                            response.sendRedirect(referer);
                        }
                    }
                    break;
                case "2":
                    response.setContentType("text/html;charset=UTF-8");
                    con.consulta("call friends('2','" + UsrId + "','" + IdAmigo + "')");
                    response.sendRedirect(referer);
                    break;
                case "3":
                    response.setContentType("text/html;charset=UTF-8");
                    con.consulta("call notificaciones('5','" + UsrId + "','0')");
                    break;
                case "4":
                    response.setContentType("text/html;charset=UTF-8");
                    con.consulta("call friends('3','" + UsrId + "','" + IdAmigo + "')");
                    response.sendRedirect(referer);
                    break;
                case "5":
                    response.setContentType("text/html;charset=UTF-8");
                    ResultSet amigos2 = con.consulta("call friends('1','" + UsrId + "','" + IdAmigo + "')");
                    if (amigos2.next()) {
                        if (amigos2.getString("quefue").equals("confirma")) {
                            response.sendRedirect(referer);
                        }

                    }
                    break;
                case "6":
                    response.setContentType("text/html;charset=UTF-8");
                    con.consulta("call friends('6','" + UsrId + "','" + IdAmigo + "')");
                    response.sendRedirect(referer);
                    break;
                case "7":
                    response.setContentType("text/html;charset=UTF-8");
                    con.consulta("call friends('5','" + UsrId + "','" + IdAmigo + "')");
                    break;
                case "8":
                    response.setContentType("text/html;charset=UTF-8");
                    con.consulta("call friends('7','" + UsrId + "','" + IdAmigo + "')");
                    break;
                case "9":
                    response.setContentType("text/html;charset=UTF-8");
                    ResultSet docpaciente = con.consulta("call friends('3','" + UsrId + "','" + IdAmigo + "')");
                    if (docpaciente.next()) {
                        ResultSet rschat2 = con.consulta("call friends('0','" + UsrId + "',0)");
                        String imgchat = "";
                        while (rschat2.next()) {
                            if (!rschat2.getString("idpersona").equals(UsrId)) {
                                String onoff = "";
                                ResultSet onlineono = con.consulta("call onlineono('2','" + rschat2.getString("idpersona") + "')");
                                if (onlineono.next()) {

                                    onoff = onlineono.getString("css");
                                }
                                imgchat = request.getContextPath() + "/UsrImagenes/" + rschat2.getString("Imagen");
                                out.print("<form id=\"chatforma" + rschat2.getString("idpersona") + "\" action=\"chat.jsp\" method=\"post\" class=\"divchatin\">\n"
                                        + "<input type=\"hidden\" name=\"chat\" value=\"" + rschat2.getString("idpersona") + "\">\n"
                                        + "                    <a class=\"linkchat puntero\" onclick=\"document.getElementById('chatforma" + rschat2.getString("idpersona") + "').submit()\">\n"
                                        + "                        <img src=\"" + imgchat + "\" class=\"imgchat\"/>\n"
                                        + rschat2.getString("Nombre") + " " + rschat2.getString("apellido") + "\n"
                                        + "                        <div class=\"onoff\" style=\"background:" + onoff + "\"></div>\n"
                                        + "                    </a>\n"
                                        + "\n"
                                        + "                </form>\n");

                            }
                        }

                    }
                    break;
                case "10":
                    response.setContentType("text/html;charset=UTF-8");
                    ResultSet rschat3 = con.consulta("call friends('0','" + UsrId + "',0)");
                    String guardaOnline = "";
                    String imgchat = "";
                    while (rschat3.next()) {
                        if (!rschat3.getString("idpersona").equals(UsrId)) {
                            String onoff = "";
                            ResultSet onlineono = con.consulta("call onlineono('2','" + rschat3.getString("idpersona") + "')");
                            if (onlineono.next()) {

                                onoff = onlineono.getString("css");
                            }
                            imgchat = request.getContextPath() + "/UsrImagenes/" + rschat3.getString("Imagen");
                            guardaOnline += "<form id=\"chatforma" + rschat3.getString("idpersona") + "\" action=\"chat.jsp\" method=\"post\" class=\"divchatin\">\n"
                                    + "<input type=\"hidden\" name=\"chat\" value=\"" + rschat3.getString("idpersona") + "\">\n"
                                    + "                    <a class=\"linkchat puntero\" onclick=\"document.getElementById('chatforma" + rschat3.getString("idpersona") + "').submit()\">\n"
                                    + "                        <img src=\"" + imgchat + "\" class=\"imgchat\"/>\n"
                                    + rschat3.getString("Nombre") + " " + rschat3.getString("apellido") + "\n"
                                    + "                        <div class=\"onoff\" style=\"background:" + onoff + "\"></div>\n"
                                    + "                    </a>\n"
                                    + "\n"
                                    + "                </form>\n";
                        }
                    }

                    String guardaOnline2 = (String) sesion.getAttribute("guardaOnline");
                    if (guardaOnline2 == null) {
                        guardaOnline2 = "";
                    }
                    if (guardaOnline2.equals(guardaOnline)) {
                        out.print("igual");
                    } else {
                        out.print(guardaOnline);
                        sesion.setAttribute("guardaOnline", guardaOnline);
                    }
                    break;
                case "11":
                    response.setContentType("text/html;charset=UTF-8");
                    con.consulta("call notificaciones('5','" + UsrId + "','0')");
                    String Noti = "";
                    ResultSet rsnoti = con.consulta("call notificaciones('3','" + UsrId + "','0')");
                    int cuantosnoti = 0;
                    while (rsnoti.next()) {
                        String rutaimg = request.getContextPath() + "/UsrImagenes/" + rsnoti.getString("Imagen");
                        if (rsnoti.getInt("cuantos") > 0) {
                            cuantosnoti = rsnoti.getInt("cuantos");
                        }
                        String textodelanoti = "";
                        String urldelapub = "";

                        if (rsnoti.getString("tipo").equals("2")) {
                            urldelapub = "HPhelp.jsp?post=" + rsnoti.getString("idpublicaciones");
                            textodelanoti = "Ha respondido a tu duda del <br>" + rsnoti.getString("fechapublicacion").substring(0, 16) + " en el Foro de Ayuda";
                        } else {
                            urldelapub = "InicioRed.jsp?post=" + rsnoti.getString("idpublicaciones");
                            textodelanoti = "Ha comentado en tu publicación del <br>" + rsnoti.getString("fechapublicacion").substring(0, 16);
                        }
                        Noti += "<a class=\"linknoti\" href=\"" + urldelapub + "\">\n"
                                + "                <div class=\"msjnotiIn linknoti\">\n"
                                + "                        <span>\n"
                                + "                            <div class=\"imgnoti\">\n"
                                + "                                <img src=\"" + rutaimg + "\" style=\"height:50px; width: 50px;\">\n"
                                + "                            </div>\n"
                                + "                            <div class=\"infonoti\">\n"
                                + "                                <strong>" + rsnoti.getString("nombre") + " " + rsnoti.getString("apellido") + "</strong>\n"
                                + "                                " + textodelanoti + "\n"
                                + "                            </div>\n"
                                + "                            \n"
                                + "                        </span>\n"
                                + "                    </div>\n"
                                + "                </a>";

                    }
                    String guardaNoti = (String) sesion.getAttribute("guardaNoti");
                    if (guardaNoti == null) {
                        guardaNoti = "";
                    }
                    if (guardaNoti.equals(Noti)) {
                        out.print("igual||||0");
                    } else {
                        out.print(Noti + "||||" + cuantosnoti);
                        sesion.setAttribute("guardaNoti", Noti);
                    }
                    break;
                case "12":
                    response.setContentType("text/html;charset=UTF-8");
                    String msj = "";
                    ResultSet rsmsjnoti = con.consulta("call notificaciones('2','" + UsrId + "','0')");
                    String cuantosmsj = "0";
                    while (rsmsjnoti.next()) {
                        String rutaimg = request.getContextPath() + "/UsrImagenes/" + rsmsjnoti.getString("Imagen");
                        cuantosmsj = rsmsjnoti.getString("cuantos");
                        String msjportipo = "";
                        if (rsmsjnoti.getString("tipomsj").equals("1")) {
                            msjportipo = rsmsjnoti.getString("mensaje");
                        } else {
                            msjportipo = "Te ha enviado una imagen";
                        }
                        msj += "<a class=\"linknoti\" href=\"chat.jsp?chat=" + rsmsjnoti.getString("idpersona") + "\">\n"
                                + "                    <div class=\"msjnotiIn linknoti\">\n"
                                + "\n"
                                + "                        <span>\n"
                                + "                            <div class=\"imgnoti\">\n"
                                + "                                <img src=\"" + rutaimg + "\" style=\"height:50px; width: 50px;\">\n"
                                + "                            </div>\n"
                                + "                            <div class=\"infonoti\">\n"
                                + "                            <strong>\n"
                                + "                                " + rsmsjnoti.getString("nombre") + " " + rsmsjnoti.getString("apellido") + ":\n"
                                + "                            </strong>\n"
                                + "                            <br>\n"
                                + "                            " + msjportipo + "\n"
                                + "                            </div>\n"
                                + "                        </span>\n"
                                + "                    </div>\n"
                                + "                </a>";
                    }
                    String guardaMsj = (String) sesion.getAttribute("guardaMsj");
                    if (guardaMsj == null) {
                        guardaMsj = "";
                    }
                    if (guardaMsj.equals(msj)) {
                        out.print("igual||||0");
                    } else {
                        out.print(msj + "||||" + cuantosmsj);
                        sesion.setAttribute("guardaMsj", msj);
                    }
                    break;
                case "13":
                    response.setContentType("text/html;charset=UTF-8");
                    String amigo = "";
                    ResultSet rsamigonoti = con.consulta("call notificaciones('1','" + UsrId + "','0')");
                    String cuantosamigos = "0";
                    while (rsamigonoti.next()) {
                        String rutaimg = request.getContextPath() + "/UsrImagenes/" + rsamigonoti.getString("Imagen");
                        cuantosamigos = rsamigonoti.getString("cuantos");
                        amigo += "<div id=\"divsolicitud" + rsamigonoti.getString("idpersona") + "\" class=\"msjnotiIn\">\n"
                                + "                        <span>\n"
                                + "                            <div class=\"imgnoti\">\n"
                                + "                                <img src=\"" + rutaimg + "\" style=\"height:50px; width: 50px;\">\n"
                                + "                            </div>\n"
                                + "                            <div class=\"nombresolicitud\"><a href=\"InicioRed.jsp?Usr=" + rsamigonoti.getString("idpersona") + "\">\n"
                                + "                                " + rsamigonoti.getString("nombre") + " " + rsamigonoti.getString("apellido") + "\n"
                                + "                                </a>\n"
                                + "                            </div>\n"
                                + "                            <button class=\"puntero acepta\" onclick=\"confirmafriend(" + rsamigonoti.getString("idpersona") + ")\">Aceptar</button>\n"
                                + "                            <button class=\"puntero rechaza\" onclick=\"rechazafriend(" + rsamigonoti.getString("idpersona") + ")\">Rechazar</button>\n"
                                + "                        </span>\n"
                                + "                    </div>";
                    }
                    String guardaAmigo = (String) sesion.getAttribute("guardaAmigo");
                    if (guardaAmigo == null) {
                        guardaAmigo = "";
                    }
                    if (guardaAmigo.equals(amigo)) {
                        out.print("igual||||0");
                    } else {
                        sesion.setAttribute("guardaAmigo", amigo);
                        out.print(amigo + "||||" + cuantosamigos);
                    }
                    break;
                case "14":
                    response.setContentType("text/html;charset=UTF-8");
                    String paciente = "";
                    ResultSet rspacientenoti = con.consulta("call notificaciones('6','" + UsrId + "','0')");
                    int cuantospacientes = 0;
                    boolean yopaciente = false;
                    while (rspacientenoti.next()) {
                        if (!rspacientenoti.getString("idpersona").equals(UsrId)) {
                            String rutaimg = request.getContextPath() + "/UsrImagenes/" + rspacientenoti.getString("Imagen");
                            cuantospacientes = rspacientenoti.getInt("cuantos");
                            paciente += "<div id=\"divsolicitudpaciente" + rspacientenoti.getString("idpersona") + "\" class=\"msjnotiIn\">\n"
                                    + "                        <span>\n"
                                    + "                            <div class=\"imgnoti\">\n"
                                    + "                                <img src=\"" + rutaimg + "\" style=\"height:50px; width: 50px;\">\n"
                                    + "                            </div>\n"
                                    + "                            <div class=\"nombresolicitud\"><a href=\"InicioRed.jsp?Usr=" + rspacientenoti.getString("idpersona") + "\">\n"
                                    + "                                " + rspacientenoti.getString("nombre") + " " + rspacientenoti.getString("apellido") + "\n"
                                    + "                                </a>\n"
                                    + "                            </div>\n"
                                    + "                            <button class=\"puntero acepta\" onclick=\"confirmapaciente(" + rspacientenoti.getString("idpersona") + ")\">Aceptar</button>\n"
                                    + "                            <button class=\"puntero rechaza\" onclick=\"rechazapaciente(" + rspacientenoti.getString("idpersona") + ")\">Rechazar</button>\n"
                                    + "                        </span>\n"
                                    + "                    </div>";
                        } else {
                            yopaciente = true;
                        }
                    }
                    if (yopaciente == true) {
                        cuantospacientes--;
                    }
                    String guardaPaciente = (String) sesion.getAttribute("guardaPaciente");
                    if (guardaPaciente == null) {
                        guardaPaciente = "";
                    }
                    if (guardaPaciente.equals(paciente)) {
                        out.print("igual");
                    } else {
                        sesion.setAttribute("guardaPaciente", paciente);
                        out.print(paciente + "||||" + cuantospacientes);
                    }
                    break;
                case "15":
                    response.setContentType("text/html;charset=UTF-8");
                    String busqueda = "";
                    ResultSet rs = con.consulta("call traepost('2','" + IdAmigo.replaceAll(" ", "") + "','0')");
                    while (rs.next()) {
                        ResultSet rsenvia = con.consulta("call solicitudmensaje('" + UsrId + "','" + rs.getString("idpersona") + "')");
                        if (rsenvia.next()) {
                            String msjdoc;
                            String valor;
                            if (!rs.getString("idpersona").equals(UsrId)) {

                                msjdoc = rsenvia.getString("valor2");
                                valor = rsenvia.getString("valor");
                                if (rsenvia.getString("valida").equals("1")) {

                                    valor = "";
                                }

                            } else {
                                msjdoc = "";
                                valor = "";
                            }

                            busqueda += "<form class=\"usubuscado puntero\" action=\"chat.jsp\" method=\"post\" onclick=\"goToUser(" + rs.getString("idpersona") + ")\">\n"
                                    + "                            <div class=\"image\"><img class=\"imgusubuscado\" src=\"../UsrImagenes/" + rs.getString("imagen") + "\"></div>\n"
                                    + "                            <div class=\"nomusubuscado puntero\" onclick=\"goToUser(" + rs.getString("idpersona") + ")\">" + rs.getString("nombre") + "&nbsp;" + rs.getString("apellido") + "</div>\n"
                                    + "                            <div class=\"linksbuscado\">" + msjdoc + valor + "</div>\n"
                                    + "                        </form>";
                        }
                    }
                    String guardaBusqueda = (String) sesion.getAttribute("guardaBusqueda");
                    if (guardaBusqueda == null) {
                        guardaBusqueda = "";
                    }
                    if (guardaBusqueda.equals(busqueda)) {
                        out.print("igual");
                    } else {
                        out.print(busqueda);
                        sesion.setAttribute("guardaBusqueda", busqueda);
                    }
                    break;
                case "16":
                    response.setContentType("text/html;charset=UTF-8");
                    String UsrPerfil = "";
                    ResultSet rsenvia = con.consulta("call solicitudmensaje('" + UsrId + "','" + IdAmigo + "')");
                    if (rsenvia.next()) {
                        if (!IdAmigo.equals(UsrId)) {

                            String msjdoc = rsenvia.getString("valor2");
                            String valor = rsenvia.getString("valor");
                            if (rsenvia.getString("valida").equals("1")) {

                                valor = "";
                            }
                            UsrPerfil = msjdoc + valor;
                        }
                    }

                    String guardaUsrPerfil = (String) sesion.getAttribute("guardaUsrPerfil");
                    if (guardaUsrPerfil == null) {
                        guardaUsrPerfil = "";
                    }
                    if (guardaUsrPerfil.equals(UsrPerfil)) {
                        out.print("igual");
                    } else {
                        out.print(UsrPerfil);
                        sesion.setAttribute("guardaUsrPerfil", UsrPerfil);
                    }
                    break;
                case "17":
                    response.setContentType("text/html;charset=UTF-8");
                    ResultSet rschat4 = con.consulta("call friends('8','" + UsrId + "','" + IdAmigo + "')");
                    String guardaOnline6 = "";
                    String imgchat6 = "";
                    while (rschat4.next()) {
                        if (!rschat4.getString("idpersona").equals(UsrId)) {
                            String onoff = "";
                            ResultSet onlineono = con.consulta("call onlineono('2','" + rschat4.getString("idpersona") + "')");
                            if (onlineono.next()) {

                                onoff = onlineono.getString("css");
                            }
                            imgchat6 = request.getContextPath() + "/UsrImagenes/" + rschat4.getString("Imagen");
                            guardaOnline6 += "<form id=\"chatforma" + rschat4.getString("idpersona") + "\" action=\"chat.jsp\" method=\"post\" class=\"divchatin\">\n"
                                    + "<input type=\"hidden\" name=\"chat\" value=\"" + rschat4.getString("idpersona") + "\">\n"
                                    + "                    <a class=\"linkchat puntero\" onclick=\"document.getElementById('chatforma" + rschat4.getString("idpersona") + "').submit()\">\n"
                                    + "                        <img src=\"" + imgchat6 + "\" class=\"imgchat\"/>\n"
                                    + "                        " + rschat4.getString("Nombre") + " " + rschat4.getString("apellido") + "\n"
                                    + "                        <div class=\"onoff\" style=\"background:" + onoff + ";\"></div>\n"
                                    + "                    </a>\n"
                                    + "\n"
                                    + "                </form>\n";
                        }
                    }
                    out.print(guardaOnline6);
                    break;
                case "18":
                    response.setContentType("application/json");
                    String comment = cifrado.stremplaza(comentario);
                    ResultSet newcomment = con.consulta("call newpost('" + UsrId + "','" + idpublicacion + "','" + comment + "','" + Usrcatego + "','1')");
                    if (newcomment.next()) {
                        comment = cifrado.urlToHtmlTag(comment);
                        out.print("{\"estatus\": \"ok\",\"idcomentario\": \"" + newcomment.getString("valor") + "\", \"msj\": \"" + comment + "\"}");
                    } else {
                        out.print("{\"estatus\":\"fallo\"");
                    }
                    break;
                case "19":
                    response.setContentType("text/html;charset=UTF-8");
                    con.consulta("call friends('9','" + UsrId + "','" + IdAmigo + "')");
                    response.sendRedirect(referer);
                    break;
                case "20":
                    response.setContentType("text/html;charset=UTF-8");
                    con.consulta("call friends('11','" + UsrId + "','" + IdAmigo + "')");
                    response.sendRedirect(referer);
                    break;
                case "21":
                    response.setContentType("text/html;charset=UTF-8");
                    con.consulta("call traepost('5','" + idcom + "'," + UsrId + ")");

                    break;
                case "22":
                    response.setContentType("application/json");
                    String commentHpHelp = cifrado.stremplaza(comentario);
                    ResultSet newcomment2 = con.consulta("call newpost('" + UsrId + "','" + idpublicacion + "','" + commentHpHelp + "','" + Usrcatego + "','2')");
                    if (newcomment2.next()) {
                        commentHpHelp = cifrado.urlToHtmlTag(commentHpHelp);
                        out.print("{\"estatus\": \"ok\",\"idcomentario\": \"" + newcomment2.getString("valor") + "\", \"msj\": \"" + commentHpHelp + "\"}");
                    } else {
                        out.print("{\"estatus\":\"fallo\"");
                    }
                    break;
                case "23":
                    response.setContentType("text/html;charset=UTF-8");
                    con.consulta("call traepost('10','" + idcom + "'," + UsrId + ")");

                    break;
                case "24":
                    response.setContentType("text/html;charset=UTF-8");
                    con.consulta("call traepost('12','" + idcom + "'," + UsrId + ")");
                    break;
                case "25":
                    response.setContentType("text/html;charset=UTF-8");
                    String pub = request.getParameter("idpub");
                    con.consulta("call traepost('4','" + pub + "','" + UsrId + "')");
                    break;
            }

            con.cierraConexion();

        } catch (SQLException ex) {
            System.out.println("Solicitudes:" + ex.getMessage());
        } catch (java.lang.NullPointerException e) {
            //pagina de error redireccion
        }

    }

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
        processRequest(request, response);
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
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
