/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reporte;

import java.io.IOException;
import java.io.PrintWriter;
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
@WebServlet(name = "pdfalert", urlPatterns = {"/sections/pdfalert"})
public class pdfalert extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession sesion = request.getSession();

            if (sesion.getAttribute("UsrNombre") == null) {
                response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/start"));
                return;
            }
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("<!DOCTYPE html>\n");
      out.write("<html>\n");
      out.write("    <head>\n");
      out.write("        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n");
      out.write("        <link rel=\"icon\" href=\"");
      out.print(request.getContextPath());
      out.write("/imagenes/logo.png\" >\n");
      out.write("         <link rel=\"apple-touch-icon\" href=\"");
      out.print(request.getContextPath());
      out.write("/imagenes/logo.png\" sizes=\"114x114\">\n");
      out.write("        <title>HP-Historial</title>\n");
      out.write("        <script src=\"");
      out.print(request.getContextPath());
      out.write("/sweetalert/sweetalert.min.js\" type=\"text/javascript\"></script>\n");
      out.write("        <link href=\"");
      out.print(request.getContextPath());
      out.write("/sweetalert/sweetalert.css\" rel=\"stylesheet\" type=\"text/css\"/>\n");
      out.write("    </head>\n");
      out.write("    <body>\n"); 
      out.write("\n");
      out.write("                                <script>\n");
      out.write("                                    \n");
      out.write("                                        //setTimeout('redirec()',2000);\n");
      out.write("                                        swal({title: \"Selecciona un Historial para visualizar\",type: \"warning\",animation:\"slide-from-top\"},\n");
      out.write("                                        function(){window.history.back();});\n");
      out.write("                                </script>\n");
      out.write("                                ");
      out.write("\n");
      out.write("    </body>\n");
      out.write("</html>\n");
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
