<%-- 
    Document   : admin_solicitudes
    Created on : 24/11/2015, 06:14:24 PM
    Author     : Computadora
--%>
<%@page import="java.net.Inet4Address"%>
<%@page import="java.sql.ResultSet"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>RedMedica</title>
        <%
            HttpSession sesion = request.getSession();
            if (sesion.getAttribute("UsrNombre") == null) {
                response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/start"));
                return;
            }
        %>
        <meta http-equiv="Content-Type: text/javascript; charset=UTF-8" content="text/html; charset=UTF-8">

        <title>RedMedica</title>
        <link href="<%=request.getContextPath()%>/css/tablemedicamentos.css" rel="stylesheet" type="text/css"/>
        <link rel="icon" href="<%=request.getContextPath()%>/imagenes/logo.png" >
        <link rel="apple-touch-icon" href="<%=request.getContextPath()%>/<%=request.getContextPath()%>/imagenes/logo.png" sizes="114x114">
        <link href="<%=request.getContextPath()%>/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
        <link href="<%=request.getContextPath()%>/css/simple-sidebar.css" rel="stylesheet" type="text/css"/>
        <link href="<%=request.getContextPath()%>/css/sign.css" rel="stylesheet" type="text/css"/>
        <script src="<%=request.getContextPath()%>/js/jquery.js" type="text/javascript"></script>
        <script src="<%=request.getContextPath()%>/js/jsencrypt.js" type="text/javascript"></script>
        <link href="<%=request.getContextPath()%>/css/administrador.css" rel="stylesheet" type="text/css"/>

        <%
            db.Condb con = new db.Condb();
            con.conectar();

            String opc = request.getParameter("opc");
            String UsrId = (String) sesion.getAttribute("UsrId");
            ResultSet datospersona = con.consulta("call datospersona('" + UsrId + "')");
            String[] Usrtipo = new String[3];
            for (int i = 0; datospersona.next(); i++) {
                Usrtipo[i] = datospersona.getString("idtipo");

            }
            if (opc == null) {
                opc = "";
            }

            if (opc.equals("77")) {
                String edo = request.getParameter("edo");

                if (edo.equals("SI")) {
                    String doc = request.getParameter("doc");
                    con.consulta("call pd_EstadoCuenta('" + doc + "','2')");
                    response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/sections/admin_solicitudes.jsp"));
                } else if (edo.equals("NO")) {
                    String doc = request.getParameter("doc");
                    con.consulta("call pd_EstadoCuenta('" + doc + "','3')");
                    //con.consulta("call pd_BorraDoctor('"+doc+"')");
                    response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/sections/admin_solicitudes.jsp"));
                }
            }
        %>
    </head>
    <body style="background-image: url('<%=request.getContextPath()%>/imagenes/gradient_bg.jpg');">
        <input id="iduser" type="hidden" value="<%=UsrId%>">
        <div id="wrapper">
            <jsp:include page="defaultComponents.jsp">
                <jsp:param name="arrojaComponentesWakeup99" value="sip"></jsp:param>
            </jsp:include>
            <!-- Content-->

            <div class="caracter1">

                <div id="header" class="menu">
                    <br><br><br>
                    <ul class="nav">
                        <li><a href="admin_solicitudes.jsp">Solicitudes</a></li>
                        <li><a href="contarUsuarios.jsp">Usuarios</a></li>
                        <li><a href="Pulseras.jsp">Pulseras</a></li>
                    </ul>
                </div>

            </div>
            <div>
                <div class="caracter2">
                    <br>
                    <table class="dis">
                        <th colspan="4">Cedulas Medicas</th>
                        <tr>
                            <td><strong><center>idMedico</center></strong></td>
                            <td><strong><center>Nombre</center></strong></td>
                            <td><strong><center>Apellido</center></strong></td>
                            <td><strong><center>Genero</center></strong></td>
                            <td><strong><center>Cedula</center></strong></td>
                            <td><strong><center>Aceptar</center></strong></td>
                            <td><strong><center>Rechazar</center></strong></td>
                        </tr>
                        <%
                            ResultSet rsmedicausu = con.consulta("call pd_traeCedulas()");

                            while (rsmedicausu.next()) {
                        %>
                        <tr>
                            <td >
                                <%=rsmedicausu.getString("idPersona")%>
                            </td>
                            <td>
                                <%=rsmedicausu.getString("Nombre")%>
                            </td>
                            <td>
                                <%=rsmedicausu.getString("apellido")%>
                            </td>
                            <td>
                                <%=rsmedicausu.getString("Genero")%>
                            </td>
                            <td>
                                <%=rsmedicausu.getString("cedula")%>
                            </td>
                            <td>
                                <a class="green" href="?opc=77&edo=SI&doc=<%=rsmedicausu.getString("idPersona")%>">Activar</a>
                            </td>
                            <td>
                                <a class="red" href="?opc=77&edo=NO&doc=<%=rsmedicausu.getString("idPersona")%>">Rechazar</a>
                            </td>
                        </tr>
                        <br>
                        <%
                            }
                            con.cierraConexion();
                        %>
                    </table>
                    <br>
                </div>
                <div class="caracter3">
                    <!--<iframe src="http://www.cedulaprofesional.sep.gob.mx/cedula/indexAvanzada.action" width="100%" height="100%">Lo sentimos, hubo un error al cargar la pagina</iframe>-->
                </div>
                <div class="caracter2">
                    &nbsp;&nbsp;&nbsp; Por favor, no olvides verificar que la cedula coincida con el nombre, el apellido y genero del usuario, así como revizar que la profesion sea médico.
                </div>
            </div>

            <!-- /Content-->

        </div>

        <script src="<%=request.getContextPath()%>/js/bootstrap.min.js" type="text/javascript"></script>
        <script src="<%=request.getContextPath()%>/js/signjquery.js" type="text/javascript"></script>
        <script src="<%=request.getContextPath()%>/js/signup.js" charset="UTF-8"></script>
        <script src="<%=request.getContextPath()%>/js/rsaencryptt.js" type="text/javascript"></script>
        <!-- Menu Toggle Script -->
        <script src="<%=request.getContextPath()%>/js/essentialJs.js" type="text/javascript"></script>
    </body>
</html>

