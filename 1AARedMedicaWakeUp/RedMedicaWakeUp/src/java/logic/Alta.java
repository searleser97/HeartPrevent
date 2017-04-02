/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

/**
 *
 * @author sergio silvester
 */
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

@WebServlet("/Alta")
@MultipartConfig
public class Alta extends HttpServlet {

    private static final long serialVersionUID = 1L;

    public Alta() {
        super();

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
            
        String workingDir = getServletContext().getRealPath("/");
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            
            rules.Seguridad cifrado = new rules.Seguridad();

            db.Condb con = new db.Condb();
            con.conectar();

            String enfermedades;
            String[] enfer = new String[10];
            enfer = request.getParameterValues("enfermedad");
            int longitud = enfer.length - 1;
            String ced=request.getParameter("cedula");
            if(ced.equals(""))
            {
                ced="nulo";
            }
            String captcha=request.getParameter("emailC");
            String nombre = cifrado.stremplaza(request.getParameter("nombre"));
            String apellido = cifrado.stremplaza(request.getParameter("apellido"));
            String correocomun = request.getParameter("correo");
            String passcomun = request.getParameter("pass");
            String passcomun2 = request.getParameter("passC");
            String genero = request.getParameter("genero");
            String year = request.getParameter("yearss");
            String mes = request.getParameter("mes");
            String dia = request.getParameter("dia");
            String celcomun = request.getParameter("telefono");
            String peso = cifrado.stremplaza(request.getParameter("peso"));
            String estatura = cifrado.stremplaza(request.getParameter("estatura"));
            String alergia = cifrado.stremplaza(request.getParameter("alergia"));
            String tipoP = request.getParameter("tipo");
            String catego = request.getParameter("catego");
            String pulsera = request.getParameter("pulsera");
            String terminos=request.getParameter("terminos");
            String terminos2=request.getParameter("terminos2");
            System.out.println("ter:"+terminos);
            System.out.println("ter2:"+terminos2);
            
            
            Part arch = request.getPart("imagen");
            String Inombre = arch.getSubmittedFileName().replaceAll(" ", "");
            String passdectypted = cifrado.decripta(passcomun);
            String passdectypted2 = cifrado.decripta(passcomun2);
            String correodctp = cifrado.decripta(correocomun);
            String stringdctpcel = cifrado.decripta(celcomun);

            if(!captcha.equals(""))
            {
                out.print("Tuvimos un problema porfavor recarga la página");
                return;
            }
            else if(passdectypted.length()<6)
            {
                out.print("La contraseña debe contener almenos 6 caracteres");
                return;
            }
            else if(!passdectypted2.equals(passdectypted))
            {
                out.print("Las contraseñas no coinciden");
                return;
            }
            else if (cifrado.validaOPC("1",correodctp))
            {
                out.print("Introduce una direccion de correo valida");
                return;
            }
            else if(cifrado.validaOPC("2",stringdctpcel))
            {
                out.print("Introduce un telefono valido");
                return;
            }
            else if(cifrado.validaOPC("3",peso))
            {
                out.print("Introduce un peso valido");
                return;
            }
            else if(cifrado.validaOPC("3",estatura))
            {
                out.print("Introduce una estatura valida");
                return;
            }
            else if(cifrado.validaLe(nombre))
            {
                out.print("Tu nombre no debe contener caracteres especiales\n ó numeros");
                return;
            }
            else if(cifrado.validaLe(apellido))
            {
                out.print("Tu apellido no debe contener caracteres especiales\n ó numeros");
                return;
            }
            else if(cifrado.validaImg(Inombre))
            {
                out.print("Ingrese un archivo de imagen valido");
                return;
            }
            else if(terminos==null)
            {
                out.print("Debe aceptar los terminos y condiciones para poder continuar");
                return;
            }
            else if(terminos2==null)
            {
                out.print("Debe aceptar el aviso de privacidad para poder continuar");
                return;
            }
            
            
            String pass = cifrado.encriptaSHA1(cifrado.stremplaza(passdectypted));
            String correo = cifrado.encriptaAES(cifrado.stremplaza(correodctp));
            String cel = cifrado.encriptaAES(cifrado.stremplaza(stringdctpcel));
            ResultSet rs = null;
            
            switch (catego) {
                case "2":
                    rs = con.consulta("call validaPuls('" + tipoP + "','" + nombre + "','" + pass + "','" + correo + "','" + year + "-" + mes + "-" + dia + "','" + genero + "','" + peso + "','" + estatura + "','" + cel + "','" + catego + "','" + alergia + "','" + apellido + "','1','" + pulsera + "','" + Inombre.trim() + "','0','"+ced+"');");
                    break;
                case "1":
                    rs = con.consulta("call EdicionUsu('0','" + tipoP + "','" + nombre + "','" + pass + "','" + correo + "','" + year + "-" + mes + "-" + dia + "','" + genero + "','" + peso + "','" + estatura + "','" + cel + "','" + catego + "','" + alergia + "','" + apellido + "','1','0','" + Inombre.trim() + "','0','"+ced+"');");
                    break;
            }

            if (rs.next()) {
                if (rs.getString("valor").equals("premium") || rs.getString("valor").equals("estandar")) {
                String rutaid = rs.getString("idpersona");

                for (int i = 0; i < enfer.length; i++) {
                    if (longitud == i) {
                        enfermedades = enfer[i];
                        con.consulta("call UsuEnfer('" + rutaid + "','" + enfermedades + "')");
                    } else {
                        enfermedades = enfer[i];
                        con.consulta("call UsuEnfer('" + rutaid + "','" + enfermedades + "')");

                    }
                }
                File dirss = new File(workingDir + "/UsrImagenes/" + rutaid);
                        dirss.mkdir();
                if (!Inombre.equals("")) {
                    try (InputStream is = arch.getInputStream()) {

                        
                        File f = new File(workingDir + "/UsrImagenes/" + rutaid + "/" + Inombre);

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
                
                
                    HttpSession sesion = request.getSession();
                    sesion.setAttribute("UsrId", rs.getString("IdPersona"));
                    sesion.setAttribute("UsrNombre", rs.getString("Nombre"));
                    sesion.setAttribute("UsrApellido", rs.getString("Apellido"));
                    sesion.setAttribute("UsrCorreo", rs.getString("Correo"));
                    sesion.setAttribute("UsrImagen", rs.getString("Imagen"));
                    sesion.setAttribute("UsrPass", rs.getString("Contraseña"));
                    sesion.setAttribute("UsrTelefono", rs.getString("Telefono"));
                    sesion.setAttribute("UsrGenero", rs.getString("Genero"));
                    sesion.setAttribute("UsrPeso", rs.getString("Peso"));
                    sesion.setAttribute("UsrEstatura", rs.getString("Estatura"));
                    sesion.setAttribute("UsrAlergia", rs.getString("Alergias"));
                    sesion.setAttribute("UsrCategoria", rs.getString("idcategoria"));
                    out.print("exito");
                }
                else{
                    out.print(rs.getString("valor"));
                }
                
                
            }
            

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            out.print(ex.getMessage());
//            out.print("La fecha introducida no es valida");
        }
//        catch(java.lang.NullPointerException e)
//        {
//            System.out.println(e.getMessage());
//            out.println("Asegurate de haber llenado todos los campos con *.");
//        }

    }
}
