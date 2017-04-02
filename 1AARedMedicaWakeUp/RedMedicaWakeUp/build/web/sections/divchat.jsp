<%-- 
    Document   : divchat
    Created on : Aug 6, 2016, 2:43:35 PM
    Author     : sergioubuntu
--%>

<%@page import="java.sql.ResultSet"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div id="divchatright" class="chat">
    <%
        // this is alright, to prevent someone entering this file
        HttpSession sesion = request.getSession();
        String arrojaComponentes = request.getParameter("arrojaComponentesWakeup99");
        if (sesion.getAttribute("UsrNombre") == null || arrojaComponentes == null || !arrojaComponentes.equals("sip")) {
            response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/start"));
            return;
        }
        String UsrId = (String) sesion.getAttribute("UsrId");
        db.Condb con = new db.Condb();
        con.conectar();
        ResultSet rschat = con.consulta("call friends('0','" + UsrId + "',0)");
        String imgchat = "";
        while (rschat.next()) {
            if (!rschat.getString("idpersona").equals(UsrId)) {
                String onoff = "";
                ResultSet onlineono = con.consulta("call onlineono('2','" + rschat.getString("idpersona") + "')");
                if (onlineono.next()) {

                    onoff = onlineono.getString("css");
                }
                imgchat = request.getContextPath() + "/UsrImagenes/" + rschat.getString("Imagen");
    %>
    <div id="chatforma<%=rschat.getString("idpersona")%>" class="divchatin">
        <a href="chat.jsp?chat=<%=rschat.getString("idpersona")%>" class="linkchat puntero">
            <img src="<%=imgchat%>" class="imgchat"/>
            <%=rschat.getString("Nombre") + " " + rschat.getString("apellido")%>

            <div class="onoff" style="background:<%=onoff%>;"></div>
        </a>
    </div>
    <%
            }
        }
        con.cierraConexion();
    %>

</div>
<input id="busquedaChatInput" class="buscaChat" placeholder="Buscar" type="search" onkeyup="busquedaChat()">
