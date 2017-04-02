<%@page import="java.sql.ResultSet"%>
<%
    HttpSession sesion = request.getSession();

    if (sesion.getAttribute("UsrNombre") == null) {
        response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/start"));
        return;
    }
    String nom = (String) sesion.getAttribute("UsrNombre");
    String ape = (String) sesion.getAttribute("UsrApellido");
    rules.Seguridad cifrado = new rules.Seguridad();
    String corre = cifrado.decriptaAES((String) sesion.getAttribute("UsrCorreo"));
    db.Condb con = new db.Condb();
%>
<!DOCTYPE html>
<html lang="en">

    <head>
        <title><%=nom%> <%=ape%></title>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="">
        <meta name="author" content="">
        <link rel="icon" href="<%=request.getContextPath()%>/imagenes/logo.png" >
        <link rel="apple-touch-icon" href="<%=request.getContextPath()%>/imagenes/logo.png" sizes="114x114">
        <link href="<%=request.getContextPath()%>/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>

        <link href="<%=request.getContextPath()%>/css/simple-sidebar.css" rel="stylesheet" type="text/css"/>
        <link href="<%=request.getContextPath()%>/sweetalert/sweetalert.css" rel="stylesheet" type="text/css"/>
        <script src="<%=request.getContextPath()%>/sweetalert/sweetalert.min.js" type="text/javascript"></script>
        <script src="<%=request.getContextPath()%>/js/jquery.js" type="text/javascript"></script>
        <script>
            function desplega(esto) {
                esto.style.display = "block";
            }
            function nodesplega(esto) {
                esto.style.display = "none";
            }
            function muestrame(esto, esto2, num) {
                if (esto[1].style.display === "block")
                {
                    esto2.innerHTML = "ver " + num + " comentarios";
                    for (var i = 0, max = esto.length; i < max; i++) {
                        esto[i].style.display = "none";
                    }
                } else
                {
                    esto2.innerHTML = "ocultar " + num + " comentarios";
                    for (var i = 0, max = esto.length; i < max; i++) {
                        esto[i].style.display = "block";
                    }
                }

            }
        </script>
    </head><body style="background-image: url('<%=request.getContextPath()%>/imagenes/gradient_bg.jpg'); background-repeat: no-repeat; background-attachment: fixed; background-size: cover;">


        <%

            //response.setIntHeader("Refresh", 60);
            con.conectar();
            String admin = "";
            String specificPost = request.getParameter("post");
            if (specificPost == null) {
                specificPost = "";
            }
            String buscado = request.getParameter("Usr");
            String opc = request.getParameter("opc");
            String UsrId = (String) sesion.getAttribute("UsrId");

            if (sesion.getAttribute("msjdoc") == null) {
                ResultSet med = con.consulta("call pd_checarmedico ('" + UsrId + "');");
                if (med.next()) {

                    String r1 = med.getString("buscar");
                    if (r1.equals("1")) {
                        ResultSet mensa = con.consulta("call pd_enviamensaje('" + UsrId + "')");
                        if (mensa.next()) {

                            String mensaje = mensa.getString("mesage");
                            if (!mensaje.equals("noTiene")) {
                                String envio = mensa.getString("envio");
                                if (envio.equals("1")) {
                                    sesion.setAttribute("msjdoc", "1");
        %>
        <script>
            swal({title: '<%=mensaje%>', type: 'warning', timer: 1500, showConfirmButton: false});
        </script>
        <%
                                }
                            }
                        }
                    }
                }
            }
            ResultSet datospersona = con.consulta("call datospersona('" + UsrId + "')");
            String estado = "";
            for (int i = 0; datospersona.next(); i++) {
                if (datospersona.getString("idtipo").equals("3")) {
                    admin = "si";
                }
                estado = datospersona.getString("idestado");
            }
            if (sesion.getAttribute("Confirmamsj") == null) {
                if (estado.equals("4")) {
        %>
        <script>
            swal({title: 'Confirma tu cuenta', text: 'Revisa tu bandeja de entrada para confirmar tu cuenta de lo contrario tu cuenta sera inhabilitada', type: 'warning'});
        </script>
        <%
        } else if (estado.equals("1")) {
        %>
        <script>
            $.post("<%=request.getContextPath()%>/Mail", {opc: 2, n1: "<%=corre%>"})
                    .done(function (data) {
                        var dato = $.trim(data);
                        if (dato !== "no")
                        {
                            swal({title: 'Confirma tu cuenta', text: 'Se ha enviado un Correo de confirmacion, revise su bandeja de entrada', type: 'warning'});
                        }
                    });
        </script>

        <%

                }
                sesion.setAttribute("Confirmamsj", "1");
            }
            if (buscado == null || buscado.equals("0")) {
                buscado = "";
            }
            if (opc == null) {
                opc = "-1";
            } else if (opc.equals("56")) {
                String pub = request.getParameter("pub");
                con.consulta("call traepost('9','" + pub + "','" + UsrId + "')");
                response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/sections/HPhelp.jsp"));

            } else if (opc.equals("57")) {
                String com = request.getParameter("com");
                con.consulta("call traepost('10','" + com + "'," + UsrId + ")");
                response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/sections/HPhelp.jsp"));

            } else if (opc.equals("1023")) {
                String Usrdoc = request.getParameter("Usrdoc");
                String doc = request.getParameter("Usr");
                if (doc.equals("") || doc.equals(UsrId)) {
        %>
        <script>function redirec() {
                window.location = "<%=request.getContextPath()%>/sections/InicioRed.jsp?Usr=<%=doc%>";
                    }
                    setTimeout("redirec()", 1000);
                    swal({title: 'Lo sentimos no te puedes dar like a ti mismo', type: 'warning', timer: 800, showConfirmButton: false});
        </script>
        <%
        } else {
            con.consulta("call puntuacion('1','" + Usrdoc + "','" + doc + "')");
        %>
        <script>
            function redirec() {
                window.location = "<%=request.getContextPath()%>/sections/InicioRed.jsp?Usr=<%=doc%>";
                    }
                    setTimeout("redirec()", 1000);
        </script>
        <%
            }
        } else if (opc.equals("1024")) {
            String Usrdoc = request.getParameter("Usrdoc");
            String doc = request.getParameter("Usr");
            if (doc.equals("") || doc.equals(UsrId)) {
        %>
        <script>
            function redirec() {
                window.location = "<%=request.getContextPath()%>/sections/InicioRed.jsp?Usr=<%=doc%>";
                    }
                    setTimeout("redirec()", 1000);
                    swal({title: 'Lo sentimos no te puedes dar dislike a ti mismo', type: 'warning', timer: 800, showConfirmButton: false});

        </script>
        <%
        } else {
            con.consulta("call puntuacion('2','" + Usrdoc + "','" + doc + "')");
        %>
        <script>
            function redirec() {
                window.location = "<%=request.getContextPath()%>/sections/InicioRed.jsp?Usr=<%=doc%>";
                    }
                    setTimeout("redirec()", 1000);
        </script>
        <%
                }
            }

            String idpublicacion = request.getParameter("idpublicacion");
            String comentario = request.getParameter("comentario");
            String publicacion = request.getParameter("publicacion");

            ResultSet catego = null;
            if (publicacion != null) {
                catego = con.consulta("call newpost('" + UsrId + "','0','" + cifrado.stremplaza(publicacion) + "','2','2')");
                publicacion = null;
                if (catego.next()) {
        %>
        <script>
            swal({title: "Nuestro equipo de profesionales pronto te respondera, recibiras una notificacion cuando esto suceda."},
                    function () {
                        window.location = "<%=request.getContextPath() + "/sections/HPhelp.jsp"%>";
                    });

        </script>
        <%

                }
            }

            if (comentario != null) {
                catego = con.consulta("call newpostforoayuda('" + UsrId + "','" + idpublicacion + "','" + cifrado.stremplaza(comentario) + "','2')");
                comentario = null;
                if (catego.next()) {
                    if (buscado.equals("")) {
                        response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/sections/HPhelp.jsp"));
                    } else {
                        response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/sections/HPhelp.jsp"));
                    }

                }
            }


        %>
        <input id="iduser" type="hidden" value="<%=UsrId%>">
        <input type="hidden" id="UsrPerfil" value="<%=buscado%>">
        <div id="wrapper">
            <jsp:include page="defaultComponents.jsp">
                <jsp:param name="arrojaComponentesWakeup99" value="sip"></jsp:param>
            </jsp:include>
            <jsp:include page="divchat.jsp">
                <jsp:param name="arrojaComponentesWakeup99" value="sip"></jsp:param>
            </jsp:include>

            <!-- Page Content -->
            <div id="page-content-wrapper">
                <h1 onclick="window.location = 'HPhelp.jsp'" style="text-align: center; font-weight: 900; color: gray; margin-top: 50px; cursor: pointer;">Foro de Ayuda HeartPrevent</h1> 
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
                    if (!rsusu.isBeforeFirst()) {
                %>
                <script>
                    function redirec() {
                        window.history.back();
                    }
                    setTimeout("redirec()", 1000);
                    swal({title: 'URL Invalida', type: 'warning', timer: 800, showConfirmButton: false});

                </script>
                <%
                        return;
                    }
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
                                if (rsusu2.getString("idcategoria").equals("2") || rsusu2.getString("idpersona").equals(UsrId)) {
                                    tipop = "<div class=\"doc\">"
                                            + "<div class=\"imgdoc\"><img class=\"imgdoc\" src=\"" + request.getContextPath() + "/imagenes/docicon.png\" alt=\"\"/></div>"
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
                    }
                    ResultSet rsenvia = con.consulta("call solicitudmensaje('" + UsrId + "','" + buscado2 + "')");
                    if (rsenvia.next()) {

                        String nombrefull = nombreusu + apeusu;
                        if (nombrefull.length() > 21) {
                            nombrefull = nombreusu + "<br>" + apeusu;
                        } else {
                            nombrefull = nombreusu + "&nbsp;" + apeusu;
                        }
                        String msjdoc = rsenvia.getString("valor2");
                        String valor = rsenvia.getString("valor");
                        if (rsenvia.getString("valida").equals("1")) {

                            valor = "";
                        }
                %>
                <form action="chat.jsp" method="post" class="usuario">
                    <div class="imagenusudiv">
                        <img class="imagenUsu" src="<%=request.getContextPath()%>/UsrImagenes/<%=imagenusu%>" alt=""/>
                    </div>

                    <div class="infoUsu" style="font-weight: 700;text-align: center;"><%=nombrefull%></div>
                    <%=tipop%>
                    <%
                        if (!UsrId.equals(buscado2)) {
                    %>
                    <div id="linksusuario" class="linksusuario"><%=msjdoc%><%=valor%></div>
                    <%
                        }
                    %>
                </form>

                <%
                    }

                    String adonde = "";
                    try {
                        String funcion = "solucionadaUsr";
                        ResultSet rs = null;
                        ResultSet rs2 = null;
                        if (buscado.equals("") && specificPost.equals("")) {
                            adonde = "0";
                            rs = con.consulta("call traepost('8','0','" + UsrId + "')");
                            if (admin.equals("")) {
                %>
                <div  class="yoposteo">
                    <form name="posteaform" method="post" >
                        <textarea id="postealotext" class="areatext tcomment" placeholder="Publique su duda o sugerencia" name="publicacion" rows="2"></textarea>
                    </form>

                </div>
                <%
                        } else {
                            funcion = "solucionada";
                        }

                    } else if (!specificPost.equals("")) {
                        if (admin.equals("")) {
                            con.consulta("call notificaciones('8','" + UsrId + "','" + specificPost + "')");
                        }
                        rs = con.consulta("call traepost('11','" + specificPost + "','0')");
                        adonde = "0";
                    }
                    String unico = "0";
                    String nounico = "z";

                    while (rs.next()) {

                        String idp = rs.getString("idpublicaciones");
                        String rutaimg = request.getContextPath() + "/UsrImagenes/" + rs.getString("Imagen");
                        String elimina = "";
                        if (rs.getString("idpersona").equals(UsrId)) {
                            elimina = "<a class='puntero' onclick='eliminarpub(" + idp + ")'>Eliminar</a>";
                        }
                        String dudasolucionada = "";
                        String styleduda = "background: none; display: none;";
                        String color = rs.getString("estatusforoayuda");

                        if ((rs.getString("idpersona").equals(UsrId) || !admin.equals("")) && rs.getString("estatusforoayuda").equals("2")) {
                            dudasolucionada = "Declarar como solucionada";
                            styleduda = "background: green; display: block;";

                        }
                        if (color.equals("0")) {
                            color = "red";
                        } else if (color.equals("1") || color.equals("2") || color.equals("4")) {
                            color = "yellow";
                        } else {
                            color = "rgba(11,212,0,1)";
                        }

                        String descripcion = rs.getString("descripcion");
                        descripcion = cifrado.urlToHtmlTag(descripcion);

                %>
                <div id="contenedor<%=idp%>" class="contenedor">
                    <div id="postearshadow<%=idp%>"  class="onLoad postear" style="box-shadow:inset 0px 0px 15px 3px <%=color%>;">
                        <div class="postIDlink" onclick="irAPost2(<%=idp%>)"></div>
                        <div style="width: 42px; height:42px; position: absolute;">
                            <img class="imagenpost" src="<%=rutaimg%>"></div>
                        <div style="float: left;position:absolute; margin-top: 15px; margin-left: 55px; width: 150px; font-weight: bold; font-size: 16px; ">
                            <a style="color: white; width: 200px; overflow: hidden; text-overflow: ellipsis; white-space: nowrap; display:block;" href="?Usr=<%=rs.getString("idpersona")%>"><%=rs.getString("nombre")%>&nbsp;<%=rs.getString("apellido")%></a>
                        </div>
                        <div id="dudasolucionada<%=idp%>" class="dudasolucionada" style="<%=styleduda%>" onclick="<%=funcion%>('dudasolucionada<%=idp%>', '<%=idp%>')"><%=dudasolucionada%></div>
                        <div class="fechapub"><%=rs.getString("fechapublicacion").substring(0, 16)%></div>
                        <div style="float: right; margin: 5px 5px 0 0;"><%=elimina%></div>
                    </div>

                    <div class="onLoad publicacionhecha">
                        <div style="position: relative; width: 90%; margin-left: auto; margin-right: auto;">
                            <%=descripcion%>
                        </div>
                    </div>



                    <div id="comentarios<%=idp%>" class="onLoad comentario">
                        <%
                            elimina = "";
                            rs2 = con.consulta("call getcomment('" + idp + "')");
                            int numcomentarios = 0;
                            int comexceso = 0;
                            String oculta = "";
                            String docpremium = "";
                            ResultSet rspremiumdoc = null;
                            boolean muestramascomentarios = false;
                            while (rs2.next()) {
                                numcomentarios++;
                                if (numcomentarios > 4) {
                                    comexceso++;
                                    oculta = "display: none;";
                                    muestramascomentarios = true;

                                }
                                String rutaimg2 = request.getContextPath() + "/UsrImagenes/" + rs2.getString("Imagen");
                                String desplega = "";
                                if (rs2.getString("idpersona").equals(UsrId)) {
                                    desplega = "onmouseover=\"desplega(document.getElementById('" + unico + "'))\" onmouseout=\"nodesplega(document.getElementById('" + unico + "'))\"";
                                    elimina = "<a id='" + unico + "' name='" + unico + "' class='desplega puntero' onclick=\"confirmadelcom('divcomentarios" + unico + "'," + rs2.getString("idcomentario") + ")\">"
                                            + "<span title='Eliminar comentario' class='glyphicon glyphicon-remove' style='position: absolute; right: 5px; height:10px;'>"
                                            + "</span>"
                                            + "</a>";
                                }
                                if (rs2.getString("idcategoria").equals("2")) {
                                    rspremiumdoc = con.consulta("call docpremium('1'," + rs2.getString("idpersona") + ")");
                                    if (rspremiumdoc.next()) {
                                        docpremium = "&nbsp;<span class=\"icomoon icon-folder-plus small\"></span>";
                                    }
                                }

                                String descripcioncomentario = rs2.getString("comentario");
                                descripcioncomentario = cifrado.urlToHtmlTag(descripcioncomentario);
                        %>

                        <div <%=desplega%> data-toggle="tooltip" data-placement="left" title="<%=rs2.getString("fechacomentario").substring(0, 16)%>" id="divcomentarios<%=unico%>" name="<%=nounico%>" style="position: relative; <%=oculta%>">
                            <div style="position: absolute; margin-left:13px; margin-top: 3px;">
                                <img class="imagencomment" src="<%=rutaimg2%>"/>
                            </div>
                            <div class="anamecomment"><a href="?Usr=<%=rs2.getString("idpersona")%>"><%=rs2.getString("nombre") + " " + rs2.getString("apellido")%><%=docpremium%>:</a>
                                &nbsp;<%=descripcioncomentario%>
                            </div>
                            <%=elimina%>
                            <hr>

                        </div>
                        <%
                                elimina = "";
                                docpremium = "";
                                unico += "0";
                            }
                            if (muestramascomentarios) {
                        %>

                        <script>

                            $("#comentarios<%=idp%>").prepend("<a style='margin-left: 6px; cursor: pointer;' onclick=\"muestrame(document.getElementsByName('<%=nounico%>'),this,<%=numcomentarios%>)\">ver <%=comexceso%> comentarios mas</a><hr>");
                        </script>
                        <%
                            }
                            if (rs.getString("idpersona").equals(UsrId) || !admin.equals("")) {
                        %>
                        <form method="post">
                            <textarea id="areacomentario-<%=idp%>" class="areatext tcomment2" placeholder="Algun Comentario?" name="comentario" rows="2" required></textarea>
                            <input type="hidden" name="idpublicacion" value="<%=idp%>">
                        </form>

                        <%
                            }
                        %>
                    </div>
                </div>
                <%
                            nounico += "z";
                        }
                        con.cierraConexion();
                    } catch (Exception ex) {
                        response.sendRedirect(response.encodeRedirectURL(ex.getMessage()));
                    }

                %>       
                <!-- /#page-content-wrapper -->

            </div>
            <!-- /#wrapper -->
        </div>




        <script src="<%=request.getContextPath()%>/js/bootstrap.min.js" type="text/javascript"></script>


        <!-- Menu Toggle Script -->
        <script type="text/javascript" src="<%=request.getContextPath()%>/js/essentialJs.js" type="text/javascript"></script>
        <script>

                            function eliminarpub(idp) {


                                swal({title: "Deseas eliminar esta pregunta?",
                                    showCancelButton: true,
                                    confirmButtonText: "S\355",
                                    cancelButtonText: "No"},
                                        function () {
                                            $.post("../solicitudes", {opc: 25, idpub: idp}).done(function (data52) {
                                                $("#contenedor" + idp).slideUp(300);
                                            });
                                        });
                            }
                            function confirmadelcom(elementodom, idcom)
                            {
                                swal({title: "Deseas eliminar esta respuesta?",
                                    showCancelButton: true,
                                    confirmButtonText: "S\355",
                                    cancelButtonText: "No"},
                                        function () {

                                            //window.location="?opc=57&com="+idcom+"&Usr="+buscado;
                                            $.post("../solicitudes", {opc: 21, idcomentario: idcom}).done(function (data52) {
                                                $("#" + elementodom).slideUp(300);

                                            });
                                        });
                            }
                            function solucionada(idelemento, idp) {
                                $.post("../solicitudes", {opc: 24, idcomentario: idp}).done(function (data52) {
                                    $("#contenedor" + idp).slideUp(300);
                                });
                            }
                            function solucionadaUsr(idelemento, idp) {
                                $.post("../solicitudes", {opc: 24, idcomentario: idp}).done(function (data52) {
                                    $("#" + idelemento).fadeOut(500, 0);
                                    $("#postearshadow" + idp).css('box-shadow', 'inset 0px 0px 15px 3px rgba(11,212,0,1)');
                                });
                            }
                            var idunicocomentariosnuevos = "unicocomentarios";
                            $(function () {
                                $('form').each(function () {
                                    $(this).find('.tcomment2').keypress(function (e) {
                                        // Enter pressed?

                                        if (e.which === 10 || e.which === 13) {
                                            e.preventDefault();
                                            var idareacomentario = e.target.id;
                                            var idpubarray = idareacomentario.split("-");
                                            var idpub = idpubarray[1];
                                            var idheaderpub = "postearshadow" + idpubarray[1];
                                            var comentario = e.target.value;
                                            //////////////////////////////////////////////////
                                            $.post("../solicitudes", {opc: 22, idpublicacion: idpub, comentario: comentario}).done(function (data52) {

                                                if (data52.estatus === "ok") {
                                                    $("#" + idareacomentario).val("");
                                                    idunicocomentariosnuevos += "1";
                                                    $("#" + idareacomentario).before("<div id=\"comentariocreado" + idunicocomentariosnuevos + "\" onmouseover=\"desplega(document.getElementById('" + idunicocomentariosnuevos + "'))\" onmouseout=\"nodesplega(document.getElementById('" + idunicocomentariosnuevos + "'))\"data-toggle=\"tooltip\" data-placement=\"left\" title=\"En este momento\" style=\"position:relative;\">"
                                                            + "<div style=\"position: absolute; margin-left:13px; margin-top: 3px;\">"
                                                            + "<img class=\"imagencomment\" src=\"<%=request.getContextPath()%>/UsrImagenes/<%=imagenusu%>\"></div>"
                                                            + "<div class=\"anamecomment\"><a href=\"?Usr=<%=UsrId%>\"><%=nombreusu + " " + apeusu%>:</a>"
                                                            + "&nbsp;" + data52.msj + "</div>"
                                                            + "<a id='" + idunicocomentariosnuevos + "' class='desplega puntero' onclick=\"confirmadelcom('comentariocreado" + idunicocomentariosnuevos + "'," + data52.idcomentario + ")\">"
                                                            + "<span title='Eliminar comentario' class='glyphicon glyphicon-remove' style='position: absolute; right: 5px; height:10px;'>"
                                                            + "</span>"
                                                            + "</a>"
                                                            + "<hr>");
                                                    $("#" + idheaderpub).css('box-shadow', 'inset 0px 0px 15px 3px yellow');

                                                } else {
                                                    alert("Ha ocurrido un error y no se pudo efectuar la peticion");
                                                }
                                            });
                                            ////////////////////////////////////////7
                                        }
                                    });

                                    $(this).find('.subb').hide();
                                });
                            });
        </script>
    </body>

</html>
