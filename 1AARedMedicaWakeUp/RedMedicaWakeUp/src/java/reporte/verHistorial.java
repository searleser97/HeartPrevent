/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reporte;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Silvester
 */
@WebServlet(name = "verHistorial", urlPatterns = {"/sections/verHistorial"})
public class verHistorial extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            String idpdf = request.getParameter("pdf");
            if (idpdf == null) {
                response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/sections/pdfalert"));
                return;
            }
            db.Condb con = new db.Condb();
            Config.Defaults defaul=new Config.Defaults();
            con.conectar();
            ResultSet rs = con.consulta("call traepdfsergio('" + idpdf + "')");
            File file = new File(defaul.tempFolder + idpdf + ".pdf");
            if (rs.next()) {

                InputStream input;
                try (FileOutputStream output = new FileOutputStream(file)) {
                    input = rs.getBinaryStream("File");
                    byte[] buffer = new byte[1024];
                    while (input.read(buffer) > 0) {
                        output.write(buffer);
                    }
                }
                input.close();
                byte[] pdfarray = Files.readAllBytes(file.toPath());
                response.setHeader("Content-Disposition", "inline; filename=\"" + rs.getString("name") + "\"");
                response.setHeader("Content-Type", getServletContext().getMimeType(file.getName()));
                response.setHeader("Content-Length", String.valueOf(file.length()));
                response.setContentType("application/pdf");
                response.getOutputStream().write(pdfarray);
//Files.copy(file.toPath(), response.getOutputStream());

                file.delete();
            }
        } catch (SQLException ex) {
            Logger.getLogger(verHistorial.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/sections/pdfalert"));
    }

}
