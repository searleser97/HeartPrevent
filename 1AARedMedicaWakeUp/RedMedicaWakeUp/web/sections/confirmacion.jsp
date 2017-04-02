<%-- 
    Document   : confirmacion
    Created on : Dec 31, 2015, 9:20:51 AM
    Author     : Silvester
--%>

<%@page import="java.sql.ResultSet"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Recuperacion de contraseña</title>
        <link rel="icon" href="<%=request.getContextPath()%>/imagenes/logo.png" >
        <link rel="apple-touch-icon" href="<%=request.getContextPath()%>/imagenes/logo.png" sizes="114x114">
        <script src="<%=request.getContextPath()%>/js/bootstrap.min.js" type="text/javascript"></script>
        <link href="<%=request.getContextPath()%>/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
        <script src="<%=request.getContextPath()%>/js/jquery-2.0.0.min.js" type="text/javascript"></script>
        <script src="<%=request.getContextPath()%>/js/jsencrypt.js" type="text/javascript"></script>
        <%

            HttpSession sesion = request.getSession();
            String IdRecibido = request.getParameter("m");
            String pass1comun = request.getParameter("pass1");

            String ID = "";
            if (sesion.getAttribute("IdRecibido") == null) {
                sesion.setAttribute("IdRecibido", IdRecibido);
            } else {
                ID = (String) sesion.getAttribute("IdRecibido");
            }
            rules.Seguridad cifrado = new rules.Seguridad();
            db.Condb con = new db.Condb();
            con.conectar();
            ResultSet rs = null;
            if (pass1comun != null) {
                String dctppass1 = cifrado.decripta(pass1comun);
                pass1comun = cifrado.encriptaSHA1(dctppass1);
                rs = con.consulta("call validaUsr('2','" + ID + "','" + pass1comun + "')");
                if (rs.next()) {
                    if (rs.getString("valida").equals("1")) {
        %>
        <script>alert("Cuenta Confirmada");window.location = "<%=request.getContextPath()%>/start";</script>
        <%
            sesion.invalidate();
        } else {
        %>
        <script>alert("<%=rs.getString("valor")%>");</script>
        <%
                    }

                }
            }
            con.cierraConexion();

        %>
    </head>
    <body>
        <h1 style="text-align: center;">Confirmacion de cuenta HeartPrevent</h1>
        <form class="formarecovery" method="post">
            <input id="pass" class="nc" placeholder="Intoduce tu Contraseña" type="password">
            <br>
            <input type="hidden" id="pasapass" name="pass1">
            <input type="submit" value="Enviar" class="rchbutton" onclick="return check5()">
        </form>
        <div class="wakeupdiv">
            <img class="wakeupimg" src="<%=request.getContextPath()%>/imagenes/logo.png" alt=""/>
        </div>
    </body>
    <script src="<%=request.getContextPath()%>/js/rsaencryptt.js" type="text/javascript"></script>
</html>
