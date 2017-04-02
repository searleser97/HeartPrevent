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
        <%
            HttpSession sesion = request.getSession();
            if (sesion.getAttribute("UsrNombre") == null) {
                response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/start"));
                return;
            }
            String nom = (String) sesion.getAttribute("UsrNombre");
            String ape = (String) sesion.getAttribute("UsrApellido");
        %>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="">
        <meta name="author" content="">

        <title><%=nom%> <%=ape%></title>
        <link rel="icon" href="<%=request.getContextPath()%>/imagenes/logo.png" >
        <link rel="apple-touch-icon" href="<%=request.getContextPath()%>/imagenes/logo.png" sizes="114x114">
        <link href="<%=request.getContextPath()%>/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>

        <link href="<%=request.getContextPath()%>/css/simple-sidebar.css" rel="stylesheet" type="text/css"/>
        <link href="<%=request.getContextPath()%>/sweetalert/sweetalert.css" rel="stylesheet" type="text/css"/>
        <script src="<%=request.getContextPath()%>/sweetalert/sweetalert.min.js" type="text/javascript"></script>
        <script src="<%=request.getContextPath()%>/js/jquery.js" type="text/javascript"></script>


        <script src="<%=request.getContextPath()%>/js/bootstrap.min.js" type="text/javascript"></script>



    </head>

    <body style="background-image: url('<%=request.getContextPath()%>/imagenes/gradient_bg.jpg'); background-repeat: no-repeat; background-attachment: fixed; background-size: cover;">

        <%
            String UsrId = (String) sesion.getAttribute("UsrId");
            String corre = (String) sesion.getAttribute("UsrCorreo");
            String image = (String) sesion.getAttribute("UsrImagen");
            String telefono = (String) sesion.getAttribute("UsrTelefono");
            rules.Seguridad cifrado = new rules.Seguridad();
            db.Condb con = new db.Condb();
            con.conectar();
            ResultSet datospersona = con.consulta("call datospersona('" + UsrId + "')");
            String[] Usrtipo = new String[3];
            for (int i = 0; datospersona.next(); i++) {
                Usrtipo[i] = datospersona.getString("idtipo");

            }


        %>
        <input id="iduser" type="hidden" value="<%=UsrId%>">
        <div id="wrapper">
            <jsp:include page="defaultComponents.jsp">
                <jsp:param name="arrojaComponentesWakeup99" value="sip"></jsp:param>
            </jsp:include> 
            <jsp:include page="divchat.jsp">
                <jsp:param name="arrojaComponentesWakeup99" value="sip"></jsp:param>
            </jsp:include>
            <!-- Page Content -->
            <div id="page-content-wrapper">
                <%
                    String imagenusu = "";
                    String nombreusu = "";
                    String apeusu = "";
                    String correousu = "";
                    String telusu = "";
                    imagenusu = image;
                    nombreusu = nom;
                    apeusu = ape;
                    correousu = cifrado.decriptaAES(corre);
                    telusu = cifrado.decriptaAES(telefono);
                    String nombrefull = nombreusu + apeusu;
                    if (nombrefull.length() > 21) {
                        nombrefull = nombreusu + "<br>" + apeusu;
                    } else {
                        nombrefull = nombreusu + "&nbsp;" + apeusu;
                    }

                %>
                <div id="divuserantesdeusubuscado" class="usuario">
                    <div class="imagenusudiv">
                        <img class="imagenUsu" src="<%=request.getContextPath()%>/UsrImagenes/<%=imagenusu%>" alt=""/>
                    </div>
                    <div class="infoUsu" style="font-weight: 700;text-align: center;"><%=nombrefull%></div>
                </div>


                <div id="encierraUsubuscado" class="encierraUsubuscado"></div>
                <%
                    ResultSet rs = null;
                    rs = con.consulta("call friends('0','" + UsrId + "','0')");
                    if (!rs.isBeforeFirst()) {%>
                <script>

                    //setTimeout('redirec()',2000);
                    swal({title: "Aun no tienes contactos", type: "warning", animation: "slide-from-top"},
                            function () {
                                window.location = "InicioRed.jsp";
                            });
                </script>
                <%
                        return;
                    }

                    boolean menuconfianza = false;
                    while (rs.next()) {
                        String heartConfianza;
                        String msjConfianza;
                        String clickConfianza;
                        if (rs.getString("usuconfianza").equals("1")) {
                            menuconfianza = true;
                            heartConfianza = "red";
                            msjConfianza = "Quitar contacto de confianza";
                            clickConfianza = "opc=20&idamigo=" + rs.getString("idpersona");
                        } else {
                            clickConfianza = "opc=19&idamigo=" + rs.getString("idpersona");;
                            heartConfianza = "linkC";
                            msjConfianza = "Añadir a contactos de confianza";
                        }
                        ResultSet rsenvia = con.consulta("call solicitudmensaje('" + UsrId + "','" + rs.getString("idpersona") + "')");
                        if (rsenvia.next()) {
                            if (!rs.getString("idpersona").equals(UsrId)) {

                                String msjdoc = rsenvia.getString("valor2");
                                String valor = rsenvia.getString("valor");
                                if (rsenvia.getString("valida").equals("1")) {

                                    valor = "";
                                }
                %>
                <form class="usubuscado puntero" action="chat.jsp" method="post">
                    <div data-us="<%=rs.getString("idpersona")%>" fname="goto" class="image">
                        <img data-us="<%=rs.getString("idpersona")%>" fname="goto" class="imgusubuscado" src="<%=request.getContextPath()%>/UsrImagenes/<%=rs.getString("imagen")%>">
                    </div>
                        <div id="username_<%=rs.getString("idpersona")%>" data-us="<%=rs.getString("idpersona")%>" fname="goto" class="nomusubuscado">
                        <%=rs.getString("nombre")%> <%=rs.getString("apellido")%>
                    </div>
                    <div data-us="<%=rs.getString("idpersona")%>" fname="goto" class="linksbuscado">
                        <a href="../solicitudes?<%=clickConfianza%>">
                            <span title="<%=msjConfianza%>" class="icomoon heart <%=heartConfianza%> medium puntero"></span>
                        </a>
                            &nbsp;&nbsp;<%=msjdoc%><%=valor%>
                    </div>
                </form>
                <%
                            }
                        }
                    }
                %>
                <script src="<%=request.getContextPath()%>/js/essentialJs.js" type="text/javascript"></script>
                <%
                    if (menuconfianza) {
                %>
                <script>
                    addmenuconfianza();
                </script>
                <%
                } else {
                %>
                <script>
                    addespaciocontacts();
                </script>
                <%
                    }
                %>

                <!-- /#page-content-wrapper -->
                <%
                    ResultSet rs2 = null;
                    rs2 = con.consulta("call friends('10','" + UsrId + "','0')");
                    while (rs2.next()) {
                        ResultSet rsenvia2 = con.consulta("call solicitudmensaje('" + UsrId + "','" + rs2.getString("idpersona") + "')");
                        if (rsenvia2.next()) {
                            if (!rs2.getString("idpersona").equals(UsrId)) {

                                String msjdoc2 = rsenvia2.getString("valor2");
                                String valor2 = rsenvia2.getString("valor");
                                if (rsenvia2.getString("valida").equals("1")) {

                                    valor2 = "";
                                }
                %>
                <form  class="usubuscado2 puntero" action="chat.jsp" method="post">
                    <div data-us="<%=rs2.getString("idpersona")%>" fname="goto" class="image">
                        <img data-us="<%=rs2.getString("idpersona")%>" fname="goto" class="imgusubuscado" src="<%=request.getContextPath()%>/UsrImagenes/<%=rs2.getString("imagen")%>">
                    </div>
                    <div id="username_<%=rs2.getString("idpersona")%>" data-us="<%=rs2.getString("idpersona")%>" fname="goto" class="nomusubuscado puntero">
                        <%=rs2.getString("nombre")%> <%=rs2.getString("apellido")%>
                    </div>
                    <div data-us="<%=rs2.getString("idpersona")%>" fname="goto" class="linksbuscado"><%=msjdoc2%><%=valor2%></div>
                </form>
                <%
                            }
                        }
                    }
                    con.cierraConexion();
                %>
            </div>
            <!-- /#wrapper -->
        </div>

        <!-- Menu Toggle Script -->


    </body>

</html>