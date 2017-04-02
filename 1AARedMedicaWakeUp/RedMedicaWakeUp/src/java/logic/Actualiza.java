/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

/**
 *
 * @author Silvester
 */
@WebServlet("/Actualiza")
@MultipartConfig
public class Actualiza extends HttpServlet {

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
        rules.Seguridad cifrado=new rules.Seguridad();
        rules.EncryptionUtil rsa=new rules.EncryptionUtil();
        String workingDir = getServletContext().getRealPath("/");
        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<title>ServletAlta</title>");
        out.println("<script src='" + request.getContextPath() + "/sweetalert/sweetalert.min.js'></script>");
        out.println("<link rel='stylesheet' href='" + request.getContextPath() + "/sweetalert/sweetalert.css' type='text/css' media='all'/>");
        out.println("</head>");
        out.println("<body>");
        db.Condb con = new db.Condb();

        HttpSession sesion = request.getSession();
        String UsrId = (String) sesion.getAttribute("UsrId");
        int opc = Integer.parseInt(request.getParameter("opc"));
        ResultSet rs = null;
        try {
            con.conectar();
            if (opc == 1) {
                Part arch = request.getPart("imagen");
                 String Inombre = arch.getSubmittedFileName().replaceAll(" ", "");
                if (!Inombre.equals("")) {
                    ResultSet rsimg = con.consulta("call newpassimage('2','" + UsrId + "','" + Inombre.trim() + "','0')");
                    if (rsimg.next()) {
                        try (InputStream is = arch.getInputStream()) {
                            
                            File f = new File(workingDir + "/UsrImagenes/" + UsrId + "/" + Inombre.trim());
                            

                            try (FileOutputStream ous = new FileOutputStream(f)) {
                                int dato = is.read();
                                while (dato != -1) {
                                    ous.write(dato);
                                    dato = is.read();
                                }
                                is.close();
                                ous.close();
                            }
                            
                        }
                            catch(java.io.FileNotFoundException e)
                            {
                                try (InputStream is = arch.getInputStream()) {
                            File dirs = new File(workingDir + "/UsrImagenes/" + UsrId);
                            dirs.mkdir();
                            File f = new File(workingDir + "/UsrImagenes/" + UsrId + "/" + Inombre);

                            try (FileOutputStream ous = new FileOutputStream(f)) {
                                int dato = is.read();
                                while (dato != -1) {
                                    ous.write(dato);
                                    dato = is.read();
                                }
                                is.close();
                                ous.close();
                            }
                            
                        }
                            }
                        out.println("<script>"
                                + "function redirec(){window.location='" + request.getContextPath() + "/sections/InicioRed.jsp';}"
                                + " setTimeout('redirec()',1500);"
                                + "swal({title: 'Imagen de perfil actualizada',animation:'slide-from-top',type: 'success',showConfirmButton: false});");
                        out.println("</script>");
                    }

                }
                else{
                 out.println("<script>"
                                + "function redirec(){window.location='" + request.getContextPath() + "/sections/InicioRed.jsp';}"
                                + " setTimeout('redirec()',1500);"
                                + "swal({title: 'Selecciona una Imagen',animation:'slide-from-top',type: 'warning',showConfirmButton: false});");
                        out.println("</script>");
                }
            } else if (opc == 2) { 
                String nombre = request.getParameter("nombre");
                String apellido = request.getParameter("apellido");
                String correocomun = request.getParameter("correo");
                String celcomun = request.getParameter("telefono");
                String peso = request.getParameter("peso");
                String estatura = request.getParameter("estatura");
                String alergia = request.getParameter("alergia");
                String catego = request.getParameter("catego");
                String pulsera = request.getParameter("pulsera");
                 String stringdectypted=rsa.decripta(celcomun);
                String cel=cifrado.encriptaAES(stringdectypted);
            String stringdctp=rsa.decripta(correocomun);
            String correo =cifrado.encriptaAES(stringdctp);
            
                if(catego==null){
                    catego="1";
                }
                if(pulsera==null)
                {
                    pulsera="0";
                }
                else if(pulsera.equals(""))
                {
                    pulsera="0";
                }
                
                switch (catego) {
                    case "2":
                        rs = con.consulta("call validaPuls('0','" + nombre + "','0','" + correo + "','1111-11-11','0','" + peso + "','" + estatura + "','" + cel + "','" + catego + "','" + alergia + "','" + apellido + "','1','" + pulsera + "','0','" + UsrId + "','0')");
                        break;
                    case "1":
                        rs = con.consulta("call EdicionUsu('2','0','" + nombre + "','0','" + correo + "','1111-11-11','0','" + peso + "','" + estatura + "','" + cel + "','0','" + alergia + "','" + apellido + "','1','0','0','" + UsrId + "','0')");
                        break;
                }
                if (rs.next()) {
                    if (rs.getString("valor").equals("Cambios efectuados y ha sido Actualizado a premium") || rs.getString("valor").equals("Cambios efectuados")) {
                        out.println("<script>"
                                + "function redirec(){window.location='" + request.getContextPath() + "/sections/InicioRed.jsp';}"
                                + " setTimeout('redirec()',1500);"
                                + "swal({title: '" + rs.getString("valor") + "',animation:'slide-from-top',type: 'success',showConfirmButton: false});");
                        out.println("</script>");
                    } else {

                        out.println("<script>"
                                + "function redirec(){window.location='" + request.getContextPath() + "/sections/confi.jsp?opc=2';}"
                                + " setTimeout('redirec()',1500);"
                                + "swal({title: '" + rs.getString("valor") + "',animation:'slide-from-top',showConfirmButton: false});");
                        out.println("</script>");
                    }
                }
                reporte.Reporte report = new reporte.Reporte();
                report.generateReport(UsrId);

            } else if (opc == 3) {
                String oldpasscomun = request.getParameter("oldpass");
                String passcomun = request.getParameter("pass");
                String oldpassdctp=rsa.decripta(oldpasscomun);
                String oldpass=cifrado.encriptaSHA1(oldpassdctp);
                String passdctp=rsa.decripta(passcomun);
                String pass=cifrado.encriptaSHA1(passdctp);
                rs = con.consulta("call newpassimage('1','" + UsrId + "','" + pass + "','" + oldpass + "')");
                if (rs.next()) {
                    if (rs.getString("esigual").equals("exito")) {

                        out.println("<script>"
                                + "function redirec(){window.location='" + request.getContextPath() + "/sections/InicioRed.jsp';}"
                                + " setTimeout('redirec()',1000);"
                                + "swal({title: 'Contraseña Actualizada',type: 'success',animation:'slide-from-top',showConfirmButton: false});");
                        out.println("</script>");
                        //out.print("exito");
                    } else {
                        out.println("<script>"
                                + "function redirec(){window.location='" + request.getContextPath() + "/sections/confi.jsp?opc=3';}"
                                + "swal({title: '" + rs.getString("esigual") + "',type: 'error',animation:'slide-from-top'},function(){redirec();});");
                        out.println("</script>");
                        //out.print("error");
                    }
                }
            } else {
                out.println("<script>"
                        + "function redirec(){window.location='" + request.getContextPath() + "/sections/InicioRed.jsp';}"
                        + "swal({title: 'Opcion no valida',type: 'error',animation:'slide-from-top'},function(){redirec();});");
                out.println("</script>");
            }

        } catch (SQLException ex) {
            System.out.printf(ex.getMessage());
            response.sendRedirect(response.encodeRedirectURL(ex.getMessage()));
        }

    }
}
