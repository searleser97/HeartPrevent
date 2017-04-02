<%@page import="java.sql.ResultSet"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>


    <head>
        <link href="<%=request.getContextPath()%>/sweetalert/sweetalert.css" rel="stylesheet" type="text/css">
        <link href="<%=request.getContextPath()%>/css/tablemedicamentos.css" rel="stylesheet" type="text/css">
        <link rel="icon" href="<%=request.getContextPath()%>/imagenes/logo.png" >
        <link rel="apple-touch-icon" href="<%=request.getContextPath()%>/imagenes/logo.png" sizes="114x114">
        <link href="<%=request.getContextPath()%>/css/bootstrap.min.css" rel="stylesheet" type="text/css">
        <link href="<%=request.getContextPath()%>/css/simple-sidebar.css" rel="stylesheet" type="text/css">
        <!--<script src="<%=request.getContextPath()%>/js/jquery.js" type="text/javascript"></script>-->
        
        <%
            HttpSession sesion = request.getSession(true);

            String catego = (String) sesion.getAttribute("UsrCategoria");
            if (sesion.getAttribute("UsrNombre") == null) {
                response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/start"));
                return;
            }
            String opc = request.getParameter("opc");
            String UsrId = (String) sesion.getAttribute("UsrId");
            Config.Defaults def = new Config.Defaults();
            db.Condb con = new db.Condb();
            con.conectar();
            if (opc == null) {
                opc = "";
            }

            String nom = (String) sesion.getAttribute("UsrNombre");
            String ape = (String) sesion.getAttribute("UsrApellido");
            rules.Seguridad cifrado = new rules.Seguridad();
        %>
        <meta http-equiv="Content-Type: text/javascript; charset=UTF-8" content="text/html; charset=UTF-8">

        <title><%=nom%> <%=ape%></title>


    </head>
    <body style="background-image: url('<%=request.getContextPath()%>/imagenes/gradient_bg.jpg'); background-repeat: no-repeat; background-attachment: fixed; background-size: cover;">
        <div class="imgbigoptions">X</div>
        <div class="divimgbig">
            <img id="imgbigid" class="imgbig" src="<%=def.serverRoot%>HPNet/imagenes/logo.png">
        </div>
        <%

            String idfriend = request.getParameter("chat");
            if (idfriend == null || idfriend.equals("")) {
        %>
        <script>

            //setTimeout('redirec()',2000);
            swal({title: "Selecciona un Usuario para conversar", type: "warning", animation: "slide-from-top"},
                    function () {
                        window.history.back();
                    });
        </script>
        <%
                return;
            }
            String idrelusu = "";
            ResultSet validaRelacion = con.consulta("call validarelaciones('2','" + idfriend + "','" + UsrId + "')");
            if (validaRelacion.next()) {
                if (validaRelacion.getString("sonamigos").equals("no")) {
        %>
        <script>
            alert("No tienes acceso a esta conversación.");
            window.history.back();
        </script>
        <%
                    return;
                }
                idrelusu = validaRelacion.getString("idrelusu");
            }
            sesion.setAttribute("UsrIdchat", UsrId);
            ResultSet datospersona = con.consulta("call datospersona('" + UsrId + "')");

            String[] Usrtipo = new String[3];
            for (int i = 0; datospersona.next(); i++) {
                Usrtipo[i] = datospersona.getString("idtipo");

            }

            String buscado = request.getParameter("chat");

            ResultSet cate = con.consulta("call datospersona('" + buscado + "')");

            con.consulta("call notificaciones('4','" + UsrId + "','" + buscado + "')");
            if (cate.next()) {
                if (cate.getString("idcategoria").equals("1")) {
        %>
        <script>

            //setTimeout('redirec()',2000);
            swal({title: "Este usuario tiene el chat inhabilitado debido a que no es un usuario premium", type: "warning", animation: "slide-from-top"},
                    function () {
                        window.location = "InicioRed.jsp";
                    });
        </script>
        <%
                    return;
                }
            }

            if (catego.equals("1")) {
        %>
        <script>

            //setTimeout('redirec()',2000);
            swal({title: "Para acceder a la funcion del chat debes de tener una cuenta premium", type: "warning", animation: "slide-from-top"},
                    function () {
                        window.location = "InicioRed.jsp";
                    });
        </script>
        <%
                return;
            }
        %>
        <input id="idusuariosession" type="hidden" value="<%=UsrId%>">
        <input id="iduser" type="hidden" value="<%=UsrId%>">
        <div id="wrapper">
            <jsp:include page="defaultComponents.jsp">
                <jsp:param name="arrojaComponentesWakeup99" value="sip"></jsp:param>
            </jsp:include>
            <jsp:include page="divchat.jsp">
                <jsp:param name="arrojaComponentesWakeup99" value="sip"></jsp:param>
            </jsp:include>
            <%
                ResultSet rsusu = null;
                ResultSet rsusu2 = null;
                String buscado2 = buscado;
                if (buscado.equals("")) {
                    rsusu = con.consulta("call datospersona('" + UsrId + "')");
                    buscado2 = UsrId;
                } else {
                    rsusu = con.consulta("call datospersona('" + buscado2 + "')");
                }
                String imagenusu = "";
                String nombreusu = "";
                String apeusu = "";
                String correousu = "";
                String telusu = "";
                String tipop = "";
                while (rsusu.next()) {
                    imagenusu = rsusu.getString("imagen");
                    nombreusu = rsusu.getString("nombre");
                    apeusu = rsusu.getString("apellido");
                    correousu = cifrado.decriptaAES(rsusu.getString("correo"));
                    telusu = cifrado.decriptaAES(rsusu.getString("telefono"));
                    if (rsusu.getString("idtipo").equals("2")) {
                        rsusu2 = con.consulta("call puntuacion('0','" + UsrId + "','" + buscado2 + "')");

                        if (rsusu2.next()) {
                            String pos = "";
                            String neg = "";
                            String tipo1 = "";
                            String tipo2 = "";
                            if (!rsusu2.getString("existe").equals("0")) {
                                pos = rsusu2.getString("positiv");
                                neg = rsusu2.getString("negative");
                                if (!rsusu2.getString("existe2").equals("0")) {
                                    if (rsusu2.getString("tipo").equals("1")) {
                                        tipo1 = "green";
                                    } else {
                                        tipo2 = "red";
                                    }
                                }
                            }
                            tipop = "<div class=\"doc\">"
                                    + "<div class=\"imgdoc\"><img class=\"imgdoc\" src=\"" + request.getContextPath() + "/imagenes/docicon.png\"></div>"
                                    + "<div class=\"contenedorthumbs\">"
                                    + "<a href=\"InicioRed.jsp?opc=1023&Usrdoc=" + UsrId + "&Usr=" + buscado + "\"\"><span class=\"inline thumbpos glyphicon glyphicon-thumbs-up dlarge " + tipo1 + "\"></span></a>"
                                    + "<a href=\"InicioRed.jsp?opc=1024&Usrdoc=" + UsrId + "&Usr=" + buscado + "\"><span class=\"inline2 thumbneg glyphicon glyphicon-thumbs-down dlarge " + tipo2 + "\"></span></a>"
                                    + "</div>"
                                    + "<div class=\"contenedordelikes\">"
                                    + "<div class=\"positivo inline\">+" + pos + "</div>"
                                    + "<div class=\"negativo inline2\">-" + neg + "</div>"
                                    + "</div>"
                                    + "</div>";

                        }
                    }
                }
                String nombrefull = nombreusu + apeusu;
                if (nombrefull.length() > 21) {
                    nombrefull = nombreusu + "<br>" + apeusu;
                } else {
                    nombrefull = nombreusu + "&nbsp;" + apeusu;
                }

            %>
            <div class="usuario">
                <div class="imagenusudiv">
                    <img class="imagenUsu" src="<%=request.getContextPath()%>/UsrImagenes/<%=imagenusu%>">
                </div>

                <div class="infoUsu" style="font-weight: 700;text-align: center;"><%=nombrefull%></div>
                <%=tipop%>
            </div>
            <!-- Page Content -->
            <div id="page-content-wrapper">

                <div class="chatdiv">
                    <div class="puntero chatheader" onclick="irAperfil('<%=idfriend%>')">
                        <%=nombrefull%>
                    </div>
                    <div id="divchatmsj" class="chatmessages">
                        <ul>

                            <%

                                System.out.print(idfriend);
                                String msj = "";
                                ResultSet rsmsj = con.consulta("call mensajes('1','" + idfriend + "','" + UsrId + "','0')");

                                while (rsmsj.next()) {

                                    msj = rsmsj.getString("mensaje");
                                    if (rsmsj.getString("tipomsj").equals("1")) {
                                        msj = cifrado.urlToHtmlTag(msj);
                                    } else {
                                        msj = "<img id='imgschat" + rsmsj.getString("idmensaje") + "' class='imageneschat' src='http://" + def.serverRoot + "HPNet/chatimgs/" + msj + "'>";
                                    }
                                    if (rsmsj.getString("usuario").equals(UsrId)) {

                            %>
                            <li class="lichatmessages">
                                <span data-toggle="tooltip" data-placement="right" title="<%=rsmsj.getString("fecha").substring(0, 16)%>" class="right">
                                    <%=msj%>
                                </span>
                                <div class="clear"></div>
                            </li>
                            <%
                            } else {
                            %>

                            <li class="lichatmessages">
                                <span data-toggle="tooltip" data-placement="left" title="<%=rsmsj.getString("fecha").substring(0, 16)%>" class="left">
                                    <%=msj%>
                                </span>
                                <div class="clear"></div>
                            </li>
                            <%

                                    }
                                }
                                con.cierraConexion();

                            %>
                        </ul>
                        <div class="clear"></div>
                    </div>
                    <div class="msgareacontainer">
                        <form id="msjform" class="inputchat" method="post" action="<%=request.getContextPath()%>/mensaje">

                            <div id="progressContainer" class="progress">
                                <div  id="divprogress"class="progress-bar progress-bar-striped active" role="progressbar"></div>
                            </div>
                            <textarea id="msjtext" class="areamsj msjarea" placeholder="Mensaje..." name="mensaje"></textarea>

                            <div id="imgmsg" class="cameraup ">
                                <span id="imgspan" class="icomoon icon-camera puntero"></span>
                            </div>


                        </form>
                        <form id="sendimgmsg" class="cameraform" enctype="multipart/form-data">
                            <input style="display: none;" type="file" accept="image/*" id="inimgmsg" name="inimgmsg">
                            <input id="salachat" type="hidden" value="<%=idfriend%>" name="salachat">
                        </form>

                    </div>

                </div>

                <!-- /#page-content-wrapper -->
                <input id="wsdir" type="hidden" value="<%=def.serverRoot%>HPNet/echo2/<%=idrelusu%>">
            </div>
        </div><!-- /#wrapper --> 

        <script src="<%=request.getContextPath()%>/sweetalert/sweetalert.min.js" type="text/javascript"></script>
        <script src="<%=request.getContextPath()%>/js/jsencrypt.js" type="text/javascript"></script>
        <script src="<%=request.getContextPath()%>/js/jquery-2.0.0.min.js" type="text/javascript"></script>
        <script src="<%=request.getContextPath()%>/js/bootstrap.min.js" type="text/javascript"></script>
        <script src="<%=request.getContextPath()%>/js/signjquery.js" type="text/javascript"></script>
        <script src="<%=request.getContextPath()%>/js/signup.js" charset="UTF-8"></script>
        <script src="<%=request.getContextPath()%>/js/rsaencryptt.js" type="text/javascript"></script>
        <!-- Menu Toggle Script -->
        <script src="<%=request.getContextPath()%>/js/essentialJs.js" type="text/javascript"></script>
        <script src="<%=request.getContextPath()%>/js/chat.js" type="text/javascript"></script>
    </body>

</html>