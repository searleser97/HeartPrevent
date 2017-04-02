<%-- 
    Document   : administrador
    Created on : 24/11/2015, 12:53:33 PM
    Author     : Alumno
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
    if(sesion.getAttribute("UsrNombre") == null){
        response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/start"));
        return;
    }
    %>
        <meta http-equiv="Content-Type: text/javascript; charset=UTF-8" content="text/html; charset=UTF-8">

        <title>RedMedica</title>
        <link href="<%=request.getContextPath()%>/css/tablemedicamentos.css" rel="stylesheet" type="text/css"/>
        <link rel="icon" href="<%=request.getContextPath()%>/imagenes/logo.png" >
        <link rel="apple-touch-icon" href="<%=request.getContextPath()%>/imagenes/logo.png" sizes="114x114">
        <link href="<%=request.getContextPath()%>/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
        <link href="<%=request.getContextPath()%>/css/simple-sidebar.css" rel="stylesheet" type="text/css"/>
        <link href="<%=request.getContextPath()%>/css/sign.css" rel="stylesheet" type="text/css"/>
        <script src="<%=request.getContextPath()%>/js/jquery.js" type="text/javascript"></script>
        <script src="<%=request.getContextPath()%>/js/jsencrypt.js" type="text/javascript"></script>
        <link href="<%=request.getContextPath()%>/css/administrador.css" rel="stylesheet" type="text/css"/>
        
        <%
        String UsrId = (String) sesion.getAttribute("UsrId");
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
             </ul>
                 
             <div id="info">
                 
             </div>
         </div>
         
     </div>
     <div class="caracter2">
         <br>
         <h1 class="ver"><center>Bienvenido Administrador</center></h1>
         <br>
         <h2>En esta página podrás aceptar las solicitudes que los médicos hacen para tener una cuenta en ésta Red Médica, <br>
             podrás ver los usuarios que se acaban de agregar y la cantidad de usuarios que tienen una cuenta en la Red, así como <br>
             verificar aquellas publicaciones que han sido reportadas por otros usuarios.<br><br>
             Es muy importante que conserves tu cuenta y tu contraseña como un secreto.</h2>
         <br>
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
