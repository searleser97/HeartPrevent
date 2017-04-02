package logic;

/**
 *
 * @author sergio silvester
 */
import java.io.IOException;
import java.io.PrintWriter;
import java.net.UnknownHostException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class ProcesoArchivo
 */
@WebServlet("/login")
@MultipartConfig
public class login extends HttpServlet {

    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public login() {
        super();
        // TODO Auto-generated constructor stub
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        System.out.println("hola que chow login");
        String opc = request.getParameter("opc");
        if (request.getAttribute("opc") != null) {
            opc = (String) request.getAttribute("opc");
        }
        try {
            doLogin(request, response, opc);
        } catch (SQLException ex) {
            Logger.getLogger(login.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnknownHostException ex) {
            Logger.getLogger(login.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        processRequest(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        processRequest(request, response);
    }

    public void doLogin(HttpServletRequest request, HttpServletResponse response, String opc) throws UnknownHostException, SQLException, ServletException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = null;
        try {
            rules.Seguridad cifrado = new rules.Seguridad();
            out = response.getWriter();
            db.Condb con = new db.Condb();
            con.conectar();
            ResultSet rs = null;
            String rememberme = null;
            switch (opc) {
                case "1":
                    rememberme = request.getParameter("rememberme");
                    String correocomun = request.getParameter("mail");
                    String passcomun = request.getParameter("pass3");
                    String stringdectypted = cifrado.decripta(passcomun);
                    String pass = cifrado.encriptaSHA1(stringdectypted);
                    String stringdctp = cifrado.decripta(correocomun);
                    String correo = cifrado.encriptaAES(stringdctp);
                    rs = con.consulta("call validausr('1','" + correo + "','" + pass + "')");
                    break;
                case "2":
                    String token = (String) request.getAttribute("token");
                    System.out.println(token);
                    rs = con.consulta("call tokenlogin('" + token + "')");
                    break;
            }
            HttpSession sesion = request.getSession(true);
            if (rs.next()) {
                if (rs.getString("valida").equals("1")) {
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
                    sesion.setAttribute("UsrEstado", rs.getString("idestado"));
                    sesion.setAttribute("UsrToken", rs.getString("token"));
                    Cookie cookie = getCookie(request, "HeartPreventTokenForUser");

                    if (cookie == null) {
                        if (rememberme != null) {
                            System.out.println("entro1");
                            cookie = new Cookie("HeartPreventTokenForUser", rs.getString("token"));
                            cookie.setMaxAge(600 * 60 * 60);
                            response.addCookie(cookie);
                        }
                    } else {
                        System.out.println("entro2");
                        cookie.setMaxAge(600 * 60 * 60);
                        cookie.setValue(rs.getString("token"));
                        response.addCookie(cookie);
                        response.sendRedirect(response.encodeRedirectURL("sections/InicioRed.jsp"));
                    }
                    out.print("exito");
                    reporte.Reporte report = new reporte.Reporte();
                    report.generateReport(rs.getString("IdPersona"));
                } else {
                    out.print(rs.getString("valida"));
                }
            } else {
                out.print("Error del Servidor");
                Cookie cookie = getCookie(request, "HeartPreventTokenForUser");
                if (cookie != null) {
                    cookie.setValue("");
                    cookie.setMaxAge(0);
                    response.addCookie(cookie);
                }
                sesion.invalidate();
                response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/start"));
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        } finally {
            out.close();
        }
    }

    public static Cookie getCookie(HttpServletRequest request, String name) {
        for (Cookie cookie : request.getCookies()) {
            if (cookie.getName().equals(name)) {
                return cookie;
            }
        }
        return null;
    }

}
