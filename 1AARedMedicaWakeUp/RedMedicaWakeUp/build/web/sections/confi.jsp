<%@page import="java.net.Inet4Address"%>
<%@page import="java.sql.ResultSet"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>


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
        <meta http-equiv="Content-Type: text/javascript; charset=UTF-8" content="text/html; charset=UTF-8">

        <title><%=nom%> <%=ape%></title>
        <link rel="icon" href="<%=request.getContextPath()%>/imagenes/logo.png" >
        <link rel="apple-touch-icon" href="<%=request.getContextPath()%>/imagenes/logo.png" sizes="114x114">
        <link href="<%=request.getContextPath()%>/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
        <link href="<%=request.getContextPath()%>/css/simple-sidebar.css" rel="stylesheet" type="text/css"/>
        <link href="<%=request.getContextPath()%>/css/sign.css" rel="stylesheet" type="text/css"/>
        <script src="<%=request.getContextPath()%>/js/jquery.js" type="text/javascript"></script>
        <script src="<%=request.getContextPath()%>/js/jsencrypt.js" type="text/javascript"></script>

        <%
            String opc = request.getParameter("opc");
            String UsrId = (String) sesion.getAttribute("UsrId");

            rules.Seguridad cifrado = new rules.Seguridad();
            db.Condb con = new db.Condb();
            con.conectar();
            String UsrNombre = "";
            String UsrApellido = "";
            String UsrCorreo = "";
            String UsrTel = "";
            String UsrPeso = "";
            String UsrEstat = "";
            String UsrAlergia = "";
            String UsrCatego = "";
            ResultSet datospersona = con.consulta("call datospersona('" + UsrId + "')");
            String[] Usrtipo = new String[3];
            for (int i = 0; datospersona.next(); i++) {
                Usrtipo[i] = datospersona.getString("idtipo");
                UsrNombre = datospersona.getString("nombre");
                UsrApellido = datospersona.getString("apellido");
                UsrCorreo = cifrado.decriptaAES(datospersona.getString("correo"));
                UsrTel = cifrado.decriptaAES(datospersona.getString("telefono"));
                UsrPeso = datospersona.getString("peso");
                UsrEstat = datospersona.getString("estatura");
                UsrAlergia = datospersona.getString("alergias");
                UsrCatego = datospersona.getString("idcategoria");
            }
        %>
    </head>
    <body style="background-image: url('<%=request.getContextPath()%>/imagenes/gradient_bg.jpg'); background-repeat: no-repeat; background-attachment: fixed; background-size: cover;">
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




                <div class="form">
                    <%if (opc.equals("1")) {%>
                    <div id="signup">   
                        <h1>Imagen de Perfil</h1>

                        <form  id="altaform" name='altaform' method='post' enctype="multipart/form-data" action='<%=request.getContextPath()%>/Actualiza'>
                            <div class="field-wrap">
                                <input id="imagen" required type="file" accept="image/*" name="imagen" onchange="cargarArchivo(this)" class="custom-file-input" style="outline: none;"/>
                                <div style="position:relative; margin-left: auto; margin-right:auto; width: 150px; height: 150px;"><output id="listis"></output></div>
                            </div>
                            <input type="hidden" name="opc" value="1">



                            <input type="submit" class="button button-block" onclick="return check()"/>

                        </form>

                    </div>
                    <%
                    } else if (opc.equals("2")) {
                    %>
                    <div id="signup">   

                        <h1>Cuenta</h1>

                        <form name='altaform' method='post' action='<%=request.getContextPath()%>/Actualiza'>
                            <input type="hidden" name="opc" value="2">
                            <div class="top-row">

                                <div class="field-wrap">
                                    <label class="labelo">
                                        Nombre(s)<span class="req">*</span>
                                    </label>
                                    <input type="text" required  class="inputs" value="<%=UsrNombre%>" name="nombre"/>
                                </div>

                                <div class="field-wrap">
                                    <label class="labelo">
                                        Apellido<span class="req">*</span>
                                    </label>
                                    <input type="text"required  class="inputs" value="<%=UsrApellido%>" name="apellido"/>
                                </div>

                            </div>

                            <div class="field-wrap">
                                <label class="labelo">
                                    Correo<span class="req">*</span>
                                </label>
                                <input type="email"required id="correo" class="inputs" value="<%=UsrCorreo%>"/>
                            </div>          

                            <div class="field-wrap">
                                <label class="labelo">
                                    Telefono<span class="req">*</span>
                                </label>
                                <input value="<%=UsrTel%>"id="telefono" type="text" required  class="inputs" maxlength="10" onkeypress="return numeross(event)"/>
                            </div>

                            <div class="field-wrap">
                                <label class="labelo">
                                    Peso(Kg)<span class="req">*</span>
                                </label>
                                <input type="text"required  value="<%=UsrPeso%>" class="inputs" name="peso" maxlength="3" onkeypress="return numeross(event)"/>
                            </div>

                            <div class="field-wrap">
                                <label class="labelo">
                                    Estatura(cm)<span class="req">*</span>
                                </label>
                                <input required onclick="mas()"  class="inputs" value="<%=UsrEstat%>" name="estatura" maxlength="3" onkeypress="return numeross(event)"/>
                            </div>

                            <div class="field-wrap">

                                <textarea class="inputs" placeholder="Ingrese sus Alergias" name="alergia" style="height: 75px;"><%=UsrAlergia%></textarea>
                            </div>



                            <%
                                if (UsrCatego.equals("1")) {
                            %>
                            <div class="field-wrap">
                                <p class="genero">Actualizar a Premium?</p>
                                <p class="genero">
                                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                    Si   <input id="tipoP1" type="radio" value="2" required name="catego" />
                                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                    No  <input id="tipoP2" type="radio" value="1" required name="catego"/></p>

                            </div>
                            <div class="field-wrap">
                                <label id="labpulsera" class="tipoP2">
                                    Codigo de pulsera<span class="req">*</span>
                                </label>
                                <input id="pulsera" type="text"  class="tipoP2 inputs" name="pulsera" onkeypress="return numeross(event)"/>
                            </div>
                            <%
                                }
                            %>



                            <input id="pasacorreo" type="hidden" name="correo">
                            <input id="pasacel" type="hidden" name="telefono">
                            <input type="submit" class="button button-block" onclick="return check2()"/>

                        </form>

                    </div>

                    <%} else if (opc.equals("3")) {%>
                    <div id="signup">   
                        <h1>Actualizar Contraseña</h1>

                        <form name='altaform' method='post' action='<%=request.getContextPath()%>/Actualiza'>
                            <input type="hidden" name="opc" value="3">
                            <div class="field-wrap">
                                <label>
                                    Contraseña Anterior<span class="req">*</span>
                                </label>
                                <input id="pass4" type="password"required  class="inputs"/>
                            </div>
                            <div class="field-wrap">
                                <label>
                                    Nueva Contraseña<span class="req">*</span>
                                </label>
                                <input id="pass" type="password" required  class="inputs"/>
                            </div>

                            <div class="field-wrap">
                                <label>
                                    Confirma Nueva Contraseña<span class="req">*</span>
                                </label>
                                <input id="pass1" type="password"required  class="inputs"/>
                            </div>

                            <input id="pasapass" type="hidden" name="pass">
                            <input id="pasaoldpass" type="hidden" name="oldpass">
                            <input type="submit" class="button button-block" onclick="return check3()"/>

                        </form>

                    </div>
                    <%
                    } else {
                    %>
                    <script>
                        window.history.back();
                    </script>
                    <%
                        }
                        con.cierraConexion();
                    %>

                </div><!-- tab-content -->

            </div> <!-- /form -->

            <!-- /#page-content-wrapper -->

        </div>
    </div>
    <!-- /#wrapper --> 

    <script src="<%=request.getContextPath()%>/js/bootstrap.min.js" type="text/javascript"></script>

    <script src="<%=request.getContextPath()%>/js/signjquery.js" type="text/javascript"></script>
    <script src="<%=request.getContextPath()%>/js/signup.js" charset="UTF-8"></script>
    <script src="<%=request.getContextPath()%>/js/rsaencryptt.js" type="text/javascript"></script>
    <!-- Menu Toggle Script -->
    <script src="<%=request.getContextPath()%>/js/essentialJs.js" type="text/javascript"></script>
</body>

</html>