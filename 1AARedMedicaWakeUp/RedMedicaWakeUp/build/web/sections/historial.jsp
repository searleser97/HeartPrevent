<%-- 
    Document   : contactos
    Created on : Nov 14, 2015, 7:10:44 PM
    Author     : Silvester
--%>

<%@page import="java.net.Inet4Address"%>
<%@page import="java.sql.ResultSet"%>
<!DOCTYPE html>
<html lang="en">

    <head>
        <link rel="icon" href="<%=request.getContextPath()%>/imagenes/logo.png" >
        <link rel="apple-touch-icon" href="<%=request.getContextPath()%>/imagenes/logo.png" sizes="114x114">
        <link href="<%=request.getContextPath()%>/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>

        <link href="<%=request.getContextPath()%>/css/simple-sidebar.css" rel="stylesheet" type="text/css"/>
        <link href="<%=request.getContextPath()%>/sweetalert/sweetalert.css" rel="stylesheet" type="text/css"/>
        <script src="<%=request.getContextPath()%>/sweetalert/sweetalert.min.js" type="text/javascript"></script>
        <%
            HttpSession sesion = request.getSession();
            String catego = (String) sesion.getAttribute("UsrCategoria");
            if (sesion.getAttribute("UsrNombre") == null) {
                response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/start"));
                return;
            }

            String paciente = request.getParameter("paciente");
            if (paciente == null) {
                paciente = "";
            }
            String opc = request.getParameter("opc");
            String UsrId = (String) sesion.getAttribute("UsrId");
            String nom = (String) sesion.getAttribute("UsrNombre");
            String ape = (String) sesion.getAttribute("UsrApellido");

        %>
        <title><%=nom%> <%=ape%></title>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="">
        <meta name="author" content="">



    </head>

    <body style="background-image: url('<%=request.getContextPath()%>/imagenes/gradient_bg.jpg'); background-repeat: no-repeat; background-attachment: fixed; background-size: cover;">
        <input id="iduser" type="hidden" value="<%=UsrId%>">
        <%
            if (catego.equals("1")) {
        %>
        <script>

            //setTimeout('redirec()',2000);
            swal({title: "Seccion solo para usuarios Premium", type: "warning", animation: "slide-from-top"},
                    function () {
                        window.history.back();
                    });
        </script>
        <%
                return;
            }
            //response.setIntHeader("Refresh", 60);

            rules.Seguridad cifrado = new rules.Seguridad();
            db.Condb con = new db.Condb();
            con.conectar();
            ResultSet datospersona = con.consulta("call datospersona('" + UsrId + "')");
            String[] Usrtipo = new String[3];
            for (int i = 0; datospersona.next(); i++) {
                Usrtipo[i] = datospersona.getString("idtipo");

            }
            if (opc == null) {
                opc = "-1";
            } else if (opc.equals("1023")) {
                String Usrdoc = request.getParameter("Usrdoc");
                String doc = request.getParameter("Usr");
                if (doc.equals("") || doc.equals(UsrId)) {
        %>
        <script>function redirec() {
                window.location = "<%=request.getContextPath()%>/sections/historial.jsp?paciente=<%=doc%>";
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
                window.location = "<%=request.getContextPath()%>/sections/historial.jsp?paciente=<%=doc%>";
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
                window.location = "<%=request.getContextPath()%>/sections/historial.jsp?paciente=<%=doc%>";
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
                window.location = "<%=request.getContextPath()%>/sections/historial.jsp?paciente=<%=doc%>";
                    }
                    setTimeout("redirec()", 1000);
        </script>
        <%
                }
            }


        %>

        <div id="wrapper">
            <jsp:include page="defaultComponents.jsp">
                <jsp:param name="arrojaComponentesWakeup99" value="sip"></jsp:param>
            </jsp:include> 
            <jsp:include page="divchat.jsp">
                <jsp:param name="arrojaComponentesWakeup99" value="sip"></jsp:param>
            </jsp:include>
            <!-- Page Content -->
            <div id="page-content-wrapper">
                <h1 style="text-align: center; font-weight: 900; color: gray; margin-top: 50px;">Historiales Clinicos</h1> 
                <%
                    ResultSet rsusu = null;
                    ResultSet rsusu2 = null;
                    String buscado2 = paciente;
                    if (paciente.equals("")) {
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
                                if (rsusu2.getString("idcategoria").equals("2") || rsusu2.getString("idpersona").equals(UsrId)) {
                                    tipop = "<div class=\"doc\">"
                                            + "<div class=\"imgdoc\"><img class=\"imgdoc\" src=\"" + request.getContextPath() + "/imagenes/docicon.png\" alt=\"\"/></div>"
                                            + "<div class=\"contenedorthumbs\">"
                                            + "<a href=\"historial.jsp?opc=1023&Usrdoc=" + UsrId + "&Usr=" + paciente + "\"\"><span class=\"inline thumbpos glyphicon glyphicon-thumbs-up dlarge " + tipo1 + "\"></span></a>"
                                            + "<a href=\"historial.jsp?opc=1024&Usrdoc=" + UsrId + "&Usr=" + paciente + "\"><span class=\"inline2 thumbneg glyphicon glyphicon-thumbs-down dlarge " + tipo2 + "\"></span></a>"
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

                        String msjdoc = rsenvia.getString("valor2");
                        String valor = rsenvia.getString("valor");
                        if (rsenvia.getString("valida").equals("1")) {

                            valor = "";
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
                        <img class="imagenUsu" src="<%=request.getContextPath()%>/UsrImagenes/<%=imagenusu%>" alt=""/>
                    </div>

                    <div class="infoUsu" style="font-weight: 700;text-align: center;"><%=nombrefull%></div>
                    <%=tipop%>
                    <%
                        if (!UsrId.equals(buscado2)) {
                    %>
                    <div class="linksusuario"><%=msjdoc%><%=valor%></div>
                    <%
                        }
                    %>
                </div>



                <%
                    }
                    ResultSet rs = null;
                    if (paciente.equals("")) {
                        rs = con.consulta("call archivospersonasergio('" + UsrId + "','1')");
                    } else {
                        rs = con.consulta("call archivospersonasergio('" + paciente + "','1')");
                            }

                            if (!rs.isBeforeFirst()) {%>
                <script>

                    //setTimeout('redirec()',2000);
                    swal({title: "Aun no se generan historiales", type: "warning", animation: "slide-from-top"},
                            function () {
                                window.history.back();
                            });
                </script>
                <%
                        return;
                    }
                    int solouno = 1;
                    while (rs.next()) {
                %>
                <div class="usubuscado puntero" onclick="document.getElementById('pdforma<%=rs.getString("idArchNube")%>').submit()">
                    <div class="image"><img class="imgusubuscado" style="border-radius:0;" src="<%=request.getContextPath()%>/imagenes/pdfimage.png"></div>
                    <form id="pdforma<%=rs.getString("idArchNube")%>" class="nomusubuscado" style="color: #3889D0;" action="verHistorial" method="post">
                        <input type="hidden" value="<%=rs.getString("idArchNube")%>" name="pdf">
                        <%=rs.getString("name")%>
                    </form>

                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    <form id="pdforma<%=rs.getString("idArchNube")%>" class="linksbuscado" action="verHistorial" method="post">
                        <input type="hidden" value="<%=rs.getString("idArchNube")%>" name="pdf">
                        <a class="puntero" onclick="document.getElementById('pdforma<%=rs.getString("idArchNube")%>').submit()">
                        </a> 
                    </form>
                </div>
                <%
                    }
                    con.cierraConexion();
                %>

                <!-- /#page-content-wrapper -->

            </div>
            <!-- /#wrapper -->
        </div>

        <script src="<%=request.getContextPath()%>/js/jquery.js" type="text/javascript"></script>


        <script src="<%=request.getContextPath()%>/js/bootstrap.min.js" type="text/javascript"></script>


        <!-- Menu Toggle Script -->
        <script src="<%=request.getContextPath()%>/js/essentialJs.js" type="text/javascript"></script>

    </body>

</html>