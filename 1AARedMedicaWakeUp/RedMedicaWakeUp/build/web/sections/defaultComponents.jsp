<link href="../css/material-design-hamburger.css" rel="stylesheet" type="text/css"/>
<%@page import="java.sql.ResultSet"%>
<%
    // this is alright, to prevent someone entering this file
    String arrojaComponentes = request.getParameter("arrojaComponentesWakeup99");
    HttpSession sesion = request.getSession();
    if (sesion.getAttribute("UsrNombre") == null || arrojaComponentes == null || !arrojaComponentes.equals("sip")) {
        response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/start"));
        return;
    }
    String UsrId = (String) sesion.getAttribute("UsrId");
    String token = (String) sesion.getAttribute("UsrToken");
    db.Condb con = new db.Condb();
    con.conectar();
    ResultSet datospersona = con.consulta("call datospersona('" + UsrId + "')");
    String[] Usrtipo = new String[3];
    for (int i = 0; datospersona.next(); i++) {
        Usrtipo[i] = datospersona.getString("idtipo");
    }
    String tipoimportant = "";
    Config.Defaults def = new Config.Defaults();

%>
<input id="NodeJsSR" type="hidden" value="<%=def.nodejsServerRoot%>">
<input id="clienttoken" type="hidden" value="<%=token%>">
<input id="clientid" type="hidden" value="<%=UsrId%>">
<!--sidebar-->
<div id="sidebar-wrapper">

    <ul class="sidebar-nav">
        <li>
            <a href="InicioRed.jsp?Usr=<%=UsrId%>">Mi Perfil&nbsp;&nbsp;&nbsp;
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                &nbsp;&nbsp;<span class="glyphicon glyphicon-user margenSide"></span></a>
        </li>
        <li>
            <a href="InicioRed.jsp">Blog&nbsp;&nbsp;&nbsp;
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;

                <span class="glyphicon glyphicon-list-alt margenSide"></span></a>
        </li>
        <li>
            <a href="contactos.jsp">Contactos
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="glyphicon glyphicon-user margenSide"><span class="glyphicon glyphicon-user amigos"></span></span></a>
        </li>
        <li>
            <a href="medicamentos.jsp">Medicamentos&nbsp;&nbsp;
                <span class="glyphicon glyphicon-bell margenSide"></span></a>
        </li>
        <li>
            <a href="doctores.jsp">Doctores&nbsp;&nbsp;
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                <span class="glyphicon glyphicon-erase margenSide"></span></a>
        </li>
        <%

            for (int i = 0; i < Usrtipo.length; i++) {
                if (Usrtipo[i] != null) {
                    if (Usrtipo[i].equals("2")) {
                        tipoimportant = "2";
        %>
        <li>
            <a href="pacientes.jsp">Pacientes&nbsp;&nbsp;
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                <span class="glyphicon glyphicon-folder-open margenSide"></span></a>
        </li>
        <%
            }
            if (Usrtipo[i].equals("3")) {
        %>
        <li>
            <a href="administrar.jsp">Administrar&nbsp;&nbsp;
                &nbsp;&nbsp;&nbsp;&nbsp;
                <span class="glyphicon glyphicon-pencil margenSide"></span></a>
        </li>

        <%
                    }
                }
            }%>
        <li>
            <a href="historial.jsp">Historial&nbsp;&nbsp;
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                <span class="glyphicon glyphicon-calendar margenSide"></span></a>
        </li>
        <li class="confi">
            <a id="sacasubmenu" href="#">Configuraciones<span class="glyphicon glyphicon-cog margenSide"></span></a>
            <ul id="submenuside" class="submenuside">
                <li><a href="confi.jsp?opc=1">Perfil&nbsp;&nbsp;&nbsp;&nbsp;
                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                        &nbsp;&nbsp;&nbsp;&nbsp;<span class="glyphicon glyphicon-picture margenSide"></span></a></li>
                <li>
                    <a href="confi.jsp?opc=2">Cuenta&nbsp;&nbsp;&nbsp;&nbsp;
                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                        <span class="glyphicon glyphicon-wrench margenSide"></span></a>
                </li>
                <li><a href="confi.jsp?opc=3">Contraseña&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="glyphicon glyphicon-lock margenSide"></span></a></li>
            </ul>
        </li>

        <li>
            <a href="http://<%=def.serverRoot%>/HeartPreventWeb">
                Nosotros&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="glyphicon glyphicon-link margenSide"></span>
            </a>
        </li>
        <li>
            <a href="HPhelp.jsp">Foro de ayuda&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="icomoon icon-question"></span></a>
        </li>
        <li>
            <a href="<%=request.getContextPath()%>/logout">Cerrar Sesión&nbsp;&nbsp;&nbsp;&nbsp;<span class="glyphicon glyphicon-off margenSide"></span></a>
        </li>
    </ul>
</div>
<!-------------------------------------------------------------/sidebar----------------------------------------------------------------------------->
<!----------------------------------------------------------------------------encabezado------------------------------------------------------------------------------------------->
<div class="encabezado">

    <section class="material-design-hamburger">
        <button id="mdhi" class="material-design-hamburger__icon">
            <span class="material-design-hamburger__layer"></span>
        </button>
    </section>
    <!--<span class="glyphicon glyphicon-menu-hamburger white large menu-toggle puntero"></span>-->
    <form  class="formbusca" method="get" action="busqueda.jsp">
        <input type="search" class="buscausu" name="q"/>
        <button type="submit" class="botonbuscausu">
            &nbsp;&nbsp;&nbsp;<i class="glyphicon glyphicon-search"></i>&nbsp;&nbsp;
        </button>
    </form>
    <div id="apuntanoti" class="apuntanoti"></div>
    <span id="notifriend" class="glyphicon glyphicon-link medium white iconss1 puntero"></span>
    <span id="notimsj" class="glyphicon glyphicon-comment  medium white iconss2 puntero"></span>
    <span id="notipost" class="glyphicon glyphicon-globe medium white iconss3 puntero"></span>
    <%
        if (tipoimportant.equals("2")) {
    %>
    <span id="notipacientes" class="glyphicon glyphicon-folder-open medium white iconss4 puntero"></span>
    <div id="pacientesnoti" class="msjnoti">

        <div id="divsolicitud" class="msjnotiIn titulonoti">
            <span>
                Solicitudes de pacientes:
            </span>
        </div>
        <%
            ResultSet rspacientenoti = con.consulta("call notificaciones('6','" + UsrId + "','0')");
            int cuantospacientes = 0;
            boolean yopaciente = false;
            while (rspacientenoti.next()) {
                if (!rspacientenoti.getString("idpersona").equals(UsrId)) {
                    String rutaimg = request.getContextPath() + "/UsrImagenes/" + rspacientenoti.getString("Imagen");
                    cuantospacientes = rspacientenoti.getInt("cuantos");
        %>
        <div id="divsolicitudpaciente<%=rspacientenoti.getString("idpersona")%>" class="msjnotiIn">
            <span>
                <div class="imgnoti">
                    <img src="<%=rutaimg%>" style="height:50px; width: 50px;">
                </div>
                <div class="nombresolicitud"><a href="InicioRed.jsp?Usr=<%=rspacientenoti.getString("idpersona")%>">
                        <%=rspacientenoti.getString("nombre")%> <%=rspacientenoti.getString("apellido")%>
                    </a>
                </div>
                <button class="puntero acepta" onclick="confirmapaciente(<%=rspacientenoti.getString("idpersona")%>)">Aceptar</button>
                <button class="puntero rechaza" onclick="rechazapaciente(<%=rspacientenoti.getString("idpersona")%>)">Rechazar</button>
            </span>
        </div>

        <%
                } else {
                    yopaciente = true;
                }
            }
            if (yopaciente == true) {
                cuantospacientes--;
            }

        %>

    </div>

    <div id="notipacientesicon" class="cuantospacientes puntero"></div>
    <%                }
    %>
</div>
<!--/encabezado-->

<div class="heartprevent">HeartPrevent</div>

<div id="postnoti" class="msjnoti">
    <div id="divsolicitud" class="msjnotiIn titulonoti">
        <span>
            Notificaciones:
        </span>
    </div>
    <%
        ResultSet rsnoti = con.consulta("call notificaciones('3','" + UsrId + "','0')");

        while (rsnoti.next()) {
            String rutaimg = request.getContextPath() + "/UsrImagenes/" + rsnoti.getString("Imagen");
            String urldelapub = "";

            if (rsnoti.getString("tipo").equals("2")) {
                urldelapub = "HPhelp.jsp?post=" + rsnoti.getString("idpublicaciones");
            } else {
                urldelapub = "InicioRed.jsp?post=" + rsnoti.getString("idpublicaciones");
            }
    %>
    <a class="linknoti" href="<%=urldelapub%>">
        <div class="msjnotiIn linknoti">
            <span>
                <div class="imgnoti">
                    <img src="<%=rutaimg%>" style="height:50px; width: 50px;">
                </div>
                <div class="infonoti">
                    <strong><%=rsnoti.getString("nombre")%> <%=rsnoti.getString("apellido")%></strong>
                    <%
                        if (rsnoti.getString("tipo").equals("1")) {
                    %>
                    Ha comentado en tu publicacion del
                    <br>
                    <%=rsnoti.getString("fechapublicacion").substring(0, 16)%>
                    <%
                    } else {
                    %>
                    Ha repondido a tu duda del
                    <br>
                    <%=rsnoti.getString("fechapublicacion").substring(0, 16)%> en el Foro de Ayuda
                    <%
                        }
                    %>

                </div>

            </span>
        </div>
    </a>
    <%
        }
    %>

</div>


<div id="amigonoti" class="msjnoti">

    <div id="divsolicitud" class="msjnotiIn titulonoti">
        <span>
            Solicitudes de amistad:
        </span>
    </div>
    <%
        ResultSet rsamigonoti = con.consulta("call notificaciones('1','" + UsrId + "','0')");
        String cuantosamigos = "";
        while (rsamigonoti.next()) {
            String rutaimg = request.getContextPath() + "/UsrImagenes/" + rsamigonoti.getString("Imagen");
            cuantosamigos = rsamigonoti.getString("cuantos");
    %>
    <div id="divsolicitud<%=rsamigonoti.getString("idpersona")%>" class="msjnotiIn">
        <span>
            <div class="imgnoti">
                <img src="<%=rutaimg%>" style="height:50px; width: 50px;">
            </div>
            <div class="nombresolicitud"><a href="InicioRed.jsp?Usr=<%=rsamigonoti.getString("idpersona")%>">
                    <%=rsamigonoti.getString("nombre")%> <%=rsamigonoti.getString("apellido")%>
                </a>
            </div>
            <button class="puntero acepta" onclick="confirmafriend(<%=rsamigonoti.getString("idpersona")%>)">Aceptar</button>
            <button class="puntero rechaza" onclick="rechazafriend(<%=rsamigonoti.getString("idpersona")%>)">Rechazar</button>
        </span>
    </div>

    <%
        }
    %>

</div>
<!--<div class="cuantosnoti">50</div>
<div class="cuantosamigos">50</div>
<div class="cuantosmensajes">50</div>-->
<div id="msjnoti" class="msjnoti">
    <div id="divsolicitud" class="msjnotiIn titulonoti">
        <span>
            Mensajes:
        </span>
    </div>

    <%
        ResultSet rsmsjnoti = con.consulta("call notificaciones('2','" + UsrId + "','0')");
        String cuantosmsj = "";
        while (rsmsjnoti.next()) {
            String rutaimg = request.getContextPath() + "/UsrImagenes/" + rsmsjnoti.getString("Imagen");
            cuantosmsj = rsmsjnoti.getString("cuantos");
            String msjportipo = "";
            if (rsmsjnoti.getString("tipomsj").equals("1")) {
                msjportipo = rsmsjnoti.getString("mensaje");
            } else {
                msjportipo = "Te ha enviado una imagen";
            }
    %>

    <a class="linknoti" href="chat.jsp?chat=<%=rsmsjnoti.getString("idpersona")%>">
        <div class="msjnotiIn linknoti">

            <span>
                <div class="imgnoti">
                    <img src="<%=rutaimg%>" style="height:50px; width: 50px;">
                </div>
                <div class="infonoti">
                    <strong>
                        <%=rsmsjnoti.getString("nombre")%> <%=rsmsjnoti.getString("apellido")%>:
                    </strong>
                    <br>
                    <%=msjportipo%>
                </div>
            </span>
        </div>
    </a>
    <%
        }
    %>
</div>


<div id="notiamigoicon" class="cuantosamigos puntero"></div>
<div id="notimsjicon" class="cuantosmensajes puntero"></div>
<div id="notinotiicon" class="cuantosnoti puntero"></div>


<script src='http://<%=def.nodejsServerRoot%>/socket.io/socket.io.js'></script>
<script src="../js/nodefunctions.js" type="text/javascript"></script>
<%
    con.cierraConexion();
%>