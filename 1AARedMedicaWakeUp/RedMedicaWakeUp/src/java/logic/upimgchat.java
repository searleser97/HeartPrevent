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
@MultipartConfig
@WebServlet(name = "upimgchat", urlPatterns = {"/sections/upimgchat"})
public class upimgchat extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession sesion = request.getSession();
        PrintWriter out = response.getWriter();
        String UsrId = (String) sesion.getAttribute("UsrId");
        Part arch = request.getPart("inimgmsg");
        String workingDir = getServletContext().getRealPath("/");

        String Inombre = arch.getSubmittedFileName().replaceAll(" ", "").toLowerCase();
        boolean isImgValid = Inombre.endsWith(".png")
                || Inombre.endsWith(".jpg")
                || Inombre.endsWith(".jpeg")
                || Inombre.endsWith(".gif")
                || Inombre.endsWith(".ico");
        if (!isImgValid) {
            out.print("El archivo seleccionado no corresponde a un tipo de imagen.");
            return;
        }
        try (InputStream is = arch.getInputStream()) {

            File f = new File(workingDir + "/chatimgs/" + UsrId + "/" + Inombre);

            try (FileOutputStream ous = new FileOutputStream(f)) {
                int dato = is.read();
                while (dato != -1) {
                    ous.write(dato);
                    dato = is.read();
                }
                is.close();
                ous.close();
            }

        } catch (java.io.FileNotFoundException e) {
            try (InputStream is = arch.getInputStream()) {
                File dirs = new File(workingDir + "/chatimgs/" + UsrId);
                dirs.mkdir();
                File f = new File(workingDir + "/chatimgs/" + UsrId + "/" + Inombre);

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
        try {
            String idfriend = request.getParameter("salachat");
            db.Condb con = new db.Condb();
            con.conectar();
            con.consulta("call mensajes('3','" + idfriend + "','" + UsrId + "','" + UsrId + "/" + Inombre + "')");
            con.cierraConexion();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        Config.Defaults def = new Config.Defaults();
        out.print("ok||http://" + def.serverRoot + "HPNet/chatimgs/" + UsrId + "/" + Inombre+"||"+UsrId);
    }

}
