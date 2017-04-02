<%@page import="java.net.Inet4Address"%>
<%@page import="java.sql.ResultSet"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>


    <head>
        <%
            HttpSession sesion = request.getSession();
            String catego = (String) sesion.getAttribute("UsrCategoria");
            if (sesion.getAttribute("UsrNombre") == null) {
                response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/HPNet/start"));
                return;
            }
            String nom = (String) sesion.getAttribute("UsrNombre");
            String ape = (String) sesion.getAttribute("UsrApellido");
        %>
        <meta http-equiv="Content-Type: text/javascript; charset=UTF-8" content="text/html; charset=UTF-8">

        <title><%=nom%> <%=ape%></title>
        <link href="<%=request.getContextPath()%>/css/tablemedicamentos.css" rel="stylesheet" type="text/css"/>
        <link rel="icon" href="<%=request.getContextPath()%>/imagenes/logo.png" >
        <link rel="apple-touch-icon" href="<%=request.getContextPath()%>/imagenes/logo.png" sizes="114x114">
        <link href="<%=request.getContextPath()%>/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
        <link href="<%=request.getContextPath()%>/css/simple-sidebar.css" rel="stylesheet" type="text/css"/>
        <link href="<%=request.getContextPath()%>/css/sign.css" rel="stylesheet" type="text/css"/>
        <script src="<%=request.getContextPath()%>/js/jquery.js" type="text/javascript"></script>
        <script src="<%=request.getContextPath()%>/js/jsencrypt.js" type="text/javascript"></script>
        <link href="<%=request.getContextPath()%>/sweetalert/sweetalert.css" rel="stylesheet" type="text/css"/>
        <script src="<%=request.getContextPath()%>/sweetalert/sweetalert.min.js" type="text/javascript"></script>
        <%
            db.Condb con = new db.Condb();
            con.conectar();

            String opc = request.getParameter("opc");
            String UsrId = (String) sesion.getAttribute("UsrId");
            ResultSet datospersona = con.consulta("call datospersona('" + UsrId + "')");
            String[] Usrtipo = new String[3];
            for (int i = 0; datospersona.next(); i++) {
                Usrtipo[i] = datospersona.getString("idtipo");

            }
            if (opc == null) {
                opc = "";
            } else if (opc.equals("89")) {
                String medicamento = request.getParameter("med");
                String relusumedi = request.getParameter("usumed");
                con.consulta("call traemedicamentos('1','" + UsrId + "','" + medicamento + "','" + relusumedi + "')");
                response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/sections/medicamentos.jsp"));
            } else if (opc.equals("90")) {
                String medicamento = request.getParameter("med");
                String relusumedi = request.getParameter("usumed");
                con.consulta("call traemedicamentos('2','" + UsrId + "','" + medicamento + "','" + relusumedi + "')");
                response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/sections/medicamentos.jsp"));
            } else if (opc.equals("113")) {
                String medicamento = request.getParameter("med");
                String relusumedi = request.getParameter("usumed");
                con.consulta("call traemedicamentos('5','" + UsrId + "','" + medicamento + "','" + relusumedi + "')");
                response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/sections/medicamentos.jsp"));
            } else if (opc.equals("114")) {
                String medicamento = request.getParameter("med");
                String relusumedi = request.getParameter("usumed");
                con.consulta("call traemedicamentos('6','" + UsrId + "','" + medicamento + "','" + relusumedi + "')");
                response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/sections/medicamentos.jsp"));
            }
        %>
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
                        window.location = "InicioRed.jsp";
                    });
        </script>
        <%
                return;
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




                <div class="formmedica">

                    <div id="signup">   

                        <h1>Medicamentos</h1>

                        <form id="mediformcontainer" name='altaform' method='post' action='<%=request.getContextPath()%>/altamedicamentos'>
                            <input type="hidden" name="opc" value="1">



                            <div class="field-wrap">
                                <button id="tipoP4" class="button button-blockmedica3"/>Añadir medicamento</button>

                            </div>
                            <div id="pulsera" class="tipoP2">
                                <div id="topRow" class="top-row">

                                    <div id="dropdownmedica" class="field-wrap">
                                        Seleccione medicamento
                                        <select onchange="otromedicamento()" id="medicamentos" class="inputs" required name="combomedicamento">
                                            <option value="">Medicamentos</option>
                                            <option value="-11">Otro medicamento</option>
                                            <%
                                                ResultSet rsmedica = con.consulta("call traemedicamentos('0','0','0','0')");
                                                while (rsmedica.next()) {
                                            %>
                                            <option><%=rsmedica.getString("medicamento")%></option>
                                            <%
                                                }
                                            %>
                                        </select>

                                    </div>
                                    <div id="periodohrs" class="field-wrap">Periodo de administracion(hrs)<input type="number" required class="inputs" min="0" name="periodo"></div>
                                </div>
                                <div class="top-row">
                                    <div class="field-wrap">Fecha de start<input type="datetime-local" required class="inputs" name="fechini"></div>
                                    <div class="field-wrap">Fecha de Fin<input type="datetime-local" required class="inputs" name="fechfin"></div>
                                </div>

                                <input id="tipoP3" type="reset" class="button button-blockmedica2" value="Cancelar"/>
                                <input type="submit" value="Enviar" class="button button-blockmedica" onclick="return check2()"/>

                            </div>
                            <!-- tab-content -->
                        </form>

                    </div> <!-- /form -->
                    <div class="tablamedica" >
                        <table>
                            <tr>
                                <td>

                                </td>
                                <td>
                                    Medicamento
                                </td>
                                <td >
                                    Fecha de start
                                </td>
                                <td>
                                    Fecha de fin
                                </td>
                                <td>
                                    Periodo(hrs)
                                </td>
                                <td >
                                    Notificaciones
                                </td>
                            </tr>




                            <%
                                ResultSet rsmedicausu = con.consulta("call traemedicamentos('3'," + UsrId + ",'0','0')");
                                String notifica = "";
                                while (rsmedicausu.next()) {
                                    if (rsmedicausu.getString("estado").equals("2")) {
                                        notifica = "Activar";
                                    } else {
                                        notifica = "Desactivar";
                                    }

                            %>
                            <tr>
                                <td>
                                    <a class="red" href="?opc=113&med=<%=rsmedicausu.getString("idmedicamentos")%>&usumed=<%=rsmedicausu.getString("idrelusumedicamentos")%>">Eliminar</a>
                                </td>
                                <td >
                                    <%=rsmedicausu.getString("medicamento")%>
                                </td>
                                <td>
                                    <%=rsmedicausu.getString("fechainicio")%>
                                </td>
                                <td>
                                    <%=rsmedicausu.getString("fechafin")%>
                                </td>
                                <td>
                                    <%=rsmedicausu.getString("periodo")%>
                                </td>
                                <td>
                                    <a class="blue" href="?opc=89&med=<%=rsmedicausu.getString("idmedicamentos")%>&usumed=<%=rsmedicausu.getString("idrelusumedicamentos")%>"><%=notifica%></a>
                                </td>
                            </tr>
                            <%
                                }
                            %>
                        </table>
                    </div>
                    <!-- /#page-content-wrapper -->


                </div>
            </div>
        </div><!-- /#wrapper --> 

        <script src="<%=request.getContextPath()%>/js/bootstrap.min.js" type="text/javascript"></script>

        <script src="<%=request.getContextPath()%>/js/signjquery.js" type="text/javascript"></script>
        <script src="<%=request.getContextPath()%>/js/signup.js" charset="UTF-8"></script>
        <script src="<%=request.getContextPath()%>/js/rsaencryptt.js" type="text/javascript"></script>
        <!-- Menu Toggle Script -->
        <script src="<%=request.getContextPath()%>/js/essentialJs.js" type="text/javascript"></script>
        <script>
                                    function otromedicamento() {

                                        var asd = document.getElementById("dropdownmedica");
                                        var dropdown = document.getElementById("medicamentos");
                                        var valor = dropdown.options[dropdown.selectedIndex].value;
                                        if (valor === "-11")
                                        {

                                            asd.remove();
                                            $("#periodohrs").before("<div id=\"inputmedica\" class=\"field-wrap\">"
                                                    + "Ingrese el nombre del medicamento"
                                                    + "<input type=\"text\" id=\"medicamentos2\" class=\"inputs\" required name=\"combomedicamento\"></div>");

                                        }
                                    }

                                    $('#tipoP3').click(function (e) {
                                        $('#pulsera').fadeOut();
                                        try
                                        {
                                        var inputmedica = document.getElementById("inputmedica");
                                                inputmedica.remove();
                                                $("#periodohrs").before("<div id=\"dropdownmedica\" class=\"field-wrap\">"
                                                + "Seleccione medicamento"
                                                + "<select onchange=\"otromedicamento()\" id=\"medicamentos\" class=\"inputs\" required name=\"combomedicamento\">"
                                                + "<option value=\"\">Medicamentos</option>"
                                                + "<option value=\"-11\">Otro medicamento</option>"
            <%
                ResultSet rsmedica2 = con.consulta("call traemedicamentos('0','0','0','0')");
                while (rsmedica2.next()) {
            %>
                                        +"<option><%=rsmedica2.getString("medicamento")%></option>"
            <%
                }
                con.cierraConexion();
            %>
                                        + "</select>"

                                        + "</div>"
                                                );
                                        }
                                        catch (e)
                                        {

                                        }

                                    });
        </script>
    </body>

</html>