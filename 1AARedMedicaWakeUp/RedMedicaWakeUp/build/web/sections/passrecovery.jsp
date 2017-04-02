<%-- 
    Document   : passrecovery
    Created on : Nov 15, 2015, 9:55:47 PM
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
            String AESmail=request.getParameter("m");
            String pass1comun=request.getParameter("pass1");
            if(AESmail==null)
            {
                %>
                    <script>alert("no tienes acceso a este sitio, chao!");window.location="<%=request.getContextPath()%>/start";</script>
        <%
            }
            String mail="";
            if (sesion.getAttribute("Usrmail") == null) {
                sesion.setAttribute("Usrmail",AESmail);
            }
            else{
                mail=(String)sesion.getAttribute("Usrmail");
            }
            rules.EncryptionUtil rsa=new rules.EncryptionUtil();
            rules.Seguridad cifrado=new rules.Seguridad();
            db.Condb con=new db.Condb();
            con.conectar();
            ResultSet rs=null;
            if(pass1comun!=null){
                String dctppass1=rsa.decripta(pass1comun);
            pass1comun=cifrado.encriptaSHA1(dctppass1);
            rs=con.consulta("call newpassimage('3','0','"+pass1comun+"','"+mail+"')");
            if(rs.next())
            {
                if(rs.getString("esigual").equals("sip")){
                    %>
                    <script>alert("contraseña actualizada ya puedes iniciar sesion");window.location="<%=request.getContextPath()%>/start";</script>
        <%
                sesion.invalidate();
                }
                else{
                     %>
                    <script>alert("Usuario Invalido");window.location="<%=request.getContextPath()%>/start";</script>
        <%
                }
                
            }
            }
            con.cierraConexion();
        %>
    </head>
    <body>
        <h1 style="text-align: center;">Recuperación de Contraseña HeartPrevent</h1>
        <form class="formarecovery" method="post">
            <input id="pass" class="nc" placeholder="nueva Contraseña" type="password">
            <br>
            <input id="pass1" class="cnc" placeholder="confirma contraseña" type="password">
            <br>
            <input type="hidden" id="pasapass" name="pass1">
            <input type="submit" value="Enviar" class="rchbutton" onclick="return check4()">
        </form>
        <div class="wakeupdiv">
            <img class="wakeupimg" src="<%=request.getContextPath()%>/imagenes/logo.png" alt=""/>
        </div>
    </body>
    <script src="<%=request.getContextPath()%>/js/rsaencryptt.js" type="text/javascript"></script>
</html>
