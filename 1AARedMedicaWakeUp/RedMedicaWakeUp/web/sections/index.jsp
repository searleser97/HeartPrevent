<%@page import="java.sql.ResultSet"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>


    <head>
        <%
            HttpSession sesion = request.getSession();
            if (sesion.getAttribute("UsrNombre") != null) {
                response.sendRedirect(response.encodeRedirectURL("sections/InicioRed.jsp"));
                return;
            }
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (int i = 0; i < cookies.length; i++) {
                    Cookie cookie = cookies[i];
                    if (cookie.getName().equals("HeartPreventTokenForUser")) {
                        RequestDispatcher rd = request.getRequestDispatcher("/login");
                        request.setAttribute("opc", "2");
                        request.setAttribute("token", cookie.getValue());
                        rd.forward(request, response);
                        return;
                    }
                }
            }
        %>
        <meta http-equiv="Content-Type: text/javascript; charset=UTF-8" content="text/html; charset=UTF-8">

        <title>RedMedica</title>
        <link rel="icon" href="<%=request.getContextPath()%>/imagenes/logo.png" >
        <link rel="apple-touch-icon" href="<%=request.getContextPath()%>/imagenes/logo.png" sizes="114x114">
        <link href="<%=request.getContextPath()%>/css/sign.css" rel="stylesheet" type="text/css"/>
        <script src="<%=request.getContextPath()%>/js/jquery.js" type="text/javascript"></script>
        <script src="<%=request.getContextPath()%>/sweetalert/sweetalert.min.js" type="text/javascript"></script>
        <link href="<%=request.getContextPath()%>/sweetalert/sweetalert.css" rel="stylesheet" type="text/css"/>
        <script src="<%=request.getContextPath()%>/js/bootstrap.min.js" type="text/javascript"></script>
        <script src="<%=request.getContextPath()%>/js/jquery-2.0.0.min.js" type="text/javascript"></script>
        <script src="<%=request.getContextPath()%>/js/jsencrypt.js" type="text/javascript"></script>
    </head>


    <body onload="fillyearss()" style="background-image: url('<%=request.getContextPath()%>/imagenes/wave1.png');">  
        <style>

        </style>
        <div class="form">

            <ul class="tab-group">
                <li class="tab active"><a href="#login">Log In</a></li>
                <li class="tab"><a href="#signup">Sign Up</a></li>

            </ul>

            <div class="tab-content">

                <div id="login">   
                    <h1>HeartPrevent</h1>

                    <form id="inicio" action="<%=request.getContextPath()%>/login" method="post" >

                        <div class="field-wrap">
                            <label>
                                Correo<span class="req" class="inputs">*</span>
                            </label>
                            <input type="text" required id="mail" class="inputs" autocomplete="off"/>
                        </div>

                        <div class="field-wrap">
                            <label>
                                Contraseña<span class="req">*</span>
                            </label>
                            <input id="pass3" type="password" class="inputs" autocomplete="off"/>
                        </div>

                        <p>
                            <input id="iniciado" class="sinoutline" name="rememberme" type="checkbox">
                            <a  onclick="checkeabox()" id="ensesion" class="Ensesion" href="#">Mantenerme Conectado</a>
                            <a id="forgot" class="forgot" href="#">Recuperar Contraseña</a>
                        </p>
                        <input type="hidden" id="pase" required name="pass3">
                        <input type="hidden" id="pasemail" required name="mail">
                        <input type="hidden" name="opc" value="1">
                        <button class="button button-block" onclick="return securitat()"/>Log In</button>

                    </form>

                </div>

                <div id="signup">   
                    <h1>Registro</h1>

                    <form id="altaform" name='altaform' method='post' enctype="multipart/form-data" action='<%=request.getContextPath()%>/Alta'>

                        <div class="top-row">

                            <div class="field-wrap">
                                <label>
                                    Nombre(s)<span class="req">*</span>
                                </label>
                                <input id="nombre" type="text" required   class="inputs" name="nombre"/>
                            </div>

                            <div class="field-wrap">
                                <label>
                                    Apellido<span class="req">*</span>
                                </label>
                                <input type="text"required  class="inputs" name="apellido" onkeypress="return validLe(event)"/>
                            </div>

                        </div>

                        <div class="field-wrap">
                            <label>
                                Correo<span class="req">*</span>
                            </label>
                            <input type="email" required id="correo" class="inputs"/>
                        </div>

                        <div class="field-wrap">
                            <label>
                                Contraseña<span class="req">*</span>
                            </label>
                            <input id="pass" type="password"required  class="inputs"/>
                        </div>

                        <div class="field-wrap">
                            <label>
                                Confirma Contraseña<span class="req">*</span>
                            </label>
                            <input id="pass1" type="password"required  class="inputs"/>
                        </div>

                        <div class="field-wrap">
                            <p class="genero">Genero:</p>
                            <p class="genero">
                                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                Hombre   <input type="radio" value="H" required name="genero" />
                                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                Mujer  <input type="radio" value="M" required name="genero"/></p>

                        </div>

                        <div class="field-wrap">
                            <p class="genero">Fecha Nacimiento:</p>

                            <select required id="yearsssh" class="nacimiento" onchange="aniochange(this, mes, dia)" required name="yearss">
                                <option value="">Año</option>
                            </select>
                            <select required id="month" class="nacimiento" name="mes" onchange="adding(yearss, mes, dia)" style="width: 25%">
                                <option value="">Mes</option>
                                <option id="1"  value="01">Enero</option>
                                <option id="3"  value="02">Febrero</option>
                                <option id="1"  value="03">Marzo</option>
                                <option id="2"  value="04">Abril</option>
                                <option id="1"  value="05">Mayo</option>
                                <option id="2"  value="06">Junio</option>
                                <option id="1"  value="07">Julio</option>
                                <option id="1"  value="08">Agosto</option>
                                <option id="2"  value="09">Septiembre</option>
                                <option id="1"  value="10">Octubre</option>
                                <option id="2"  value="11">Noviembre</option>
                                <option id="1"  value="12">Diciembre</option>
                            </select>
                            <select required name="dia" class="nacimiento">
                                <option value="">Dia</option>
                            </select>


                        </div>

                        <div class="field-wrap">
                            <label>
                                Telefono<span class="req">*</span>
                            </label>
                            <input id="telefono" type="text" required  class="inputs" maxlength="10" onkeypress="return numeross(event)"/>
                        </div>

                        <div class="field-wrap">
                            <label>
                                Peso(Kg)<span class="req">*</span>
                            </label>
                            <input type="text"required  class="inputs" name="peso" maxlength="3" onkeypress="return numeross(event)"/>
                        </div>

                        <div class="field-wrap">
                            <label>
                                Estatura(cm)<span class="req">*</span>
                            </label>
                            <input required  class="inputs" name="estatura" maxlength="3" onkeypress="return numeross(event)"/>
                        </div>

                        <div class="field-wrap">

                            <textarea class="inputs" placeholder="Ingrese sus alergias" name="alergia" style="height: 75px;"></textarea>
                        </div>

                        <div class="field-wrap">
                            <p class="genero" id="labenfer">Enfermedades:</p>

                            <p id="penfer"><select id="enfermedad" class="nacimiento enfermedad"  name="enfermedad" style="width: 80%;">
                                    <option value="">Ninguna</option>
                                    <%
                                        db.Condb con = new db.Condb();
                                        con.conectar();
                                        ResultSet enfer = null;
                                        try {
                                            enfer = con.consulta("call traeEnfer()");
                                        } catch (java.lang.NullPointerException e) {
                                            System.out.println("Check for right connection to mysql DB");
                                        }

                                        while (enfer.next()) {
                                    %>
                                    <option><%=enfer.getString("enfermedad")%></option>

                                    <%
                                        }
                                    %>
                                </select>
                                <input type="button" id="masb" value="+"></p>
                            <p id="soydoc"></p>




                        </div>

                        <div class="field-wrap">
                            <p class="genero">Soy doctor?</p>
                            <p class="genero">
                                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                Si   <input id="tipo" type="radio" value="2" required name="tipo" />
                                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                No  <input id="tipo2" type="radio" value="1" required name="tipo"/>
                            </p>

                        </div>

                        <div class="field-wrap">
                            <label id="labcedula" class="tipo">
                                Cedula Profesional<span class="req">*</span>
                            </label>
                            <input id="cedula" type="text"  class="tipo inputs" name="cedula"/>
                        </div>
                        <div class="field-wrap">
                            <p class="genero">Cuento con pulsera HeartPrevent?</p>
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
                            <input id="pulsera" type="text"  class="tipoP2 inputs" name="pulsera"/>
                        </div>

                        <div class="field-wrap">
                            <label>
                                Imagen de Perfil:
                            </label>
                            <p></p>
                            <input id="imagen" type="file" accept="image/*" name="imagen" onchange="cargarArchivo(this)" class="custom-file-input" />
                            <output id="listis"></output>
                        </div>


                        <div class="field-wrap">
                            <p class="genero"><input  id="terminos" type="checkbox" name="terminos"><a class="avisos" onclick="window.open('<%=request.getContextPath()%>/sections/terminos.html', 'HP Terminos Y Condiciones', 'height=800,width=800');">Acepto los Terminos Y Condiciones*.</a></p>
                        </div>
                        <div class="field-wrap">
                            <p class="genero"><input  id="terminos" type="checkbox" name="terminos2"><a class="avisos" onclick="window.open('<%=request.getContextPath()%>/sections/privacidad.html', 'HP Terminos Y Condiciones', 'height=800,width=800');">Acepto el aviso de privacidad.</a></p>
                        </div>




                        <input type="submit" class="button button-block" onclick="return check()"/>
                        <input id="pasacorreo" type="hidden" name="correo">
                        <input id="pasapass" type="hidden" name="pass">
                        <input id="pasapass2" type="hidden" name="passC">
                        <input id="pasacel" type="hidden" name="telefono">
                        <input class="occ" id="emailC" type="email" name="emailC">

                    </form>

                </div>



            </div><!-- tab-content -->
        </div> <!-- /form -->
        <div id="FAQdiv" onclick="window.location = 'FAQ'" class="FAQlink">FAQ</div>
        <script src="<%=request.getContextPath()%>/js/signup.js" charset="UTF-8"></script>

        <script src="<%=request.getContextPath()%>/js/signjquery.js" type="text/javascript"></script>
        <script src="<%=request.getContextPath()%>/js/rsaencryptt.js" type="text/javascript"></script>
        <script>
            var i = 1;
            $("#masb").click(function () {

                $("#soydoc").before("<p><select id='enfer" + i + "' class='nacimiento'  name='enfermedad' style='width: 80%; margin-top:0;'>"
                        + " <option value=''>Ninguna</option>"
            <%
                ResultSet enfer2 = con.consulta("call traeEnfer()");
                while (enfer2.next()) {

            %>
                +" <option><%=enfer2.getString("enfermedad")%></option>"
            <%
                }
                con.cierraConexion();
            %>
                + "</select><input type='button' id='menosb' class='menosb' value='-' style='margin-left: 5px;' onclick='quita(this)'/></p>"
                        );
                        i = i + 1;
            });

            function quita(esto)
            {
                esto.parentElement.remove();
            }


            /*$(document).ready(function () {
             $('input').bind('copy paste', function (e) {
             e.preventDefault();
             });
             });*/
        </script>

        <script>
            function redirec() {
                window.location = "<%=request.getContextPath()%>/sections/InicioRed.jsp";
            }
            $(document).ready(function () {
                $("#altaform").submit(function (event) {
                    swal({title: "Verificando datos Perfil", imageUrl: "<%=request.getContextPath()%>/imagenes/loadSpin.gif", showConfirmButton: false});
                    //disable the default form submission
                    event.preventDefault();
                    //grab all form data  

                    var formData = new FormData($(this)[0]);

                    $.ajax({
                        url: '<%=request.getContextPath()%>/Alta',
                        type: 'post',
                        data: formData,
                        async: false,
                        cache: false,
                        contentType: false,
                        processData: false,
                        success: function (data) {
                            var dato = $.trim(data);
                            if (dato === "exito") {

                                var correomail = document.getElementById("correo").value;
                                $.post("<%=request.getContextPath()%>/Mail", {opc: 2, n1: correomail})
                                        .done(function (data) {
                                            var dato = $.trim(data);
                                            if (dato === "no")
                                            {
                                                alert("Error del Servidor vuelva a intentarlo mas tarde");
                                            } else {
                                                setTimeout('redirec()', 1500);
                                                swal({title: 'Bienvenido a tu nuevo perfil de RedMedica', type: 'success', timer: 1000, showConfirmButton: false});

                                            }
                                        });
                            } else
                            {
                                swal({title: 'Oops!', text: dato, type: 'warning', animation: 'slide-from-top'});
                            }


                        },
                        error: function () {
                        }
                    });
                    return false;
                });
            });

            $(document).ready(function () {
                $("#inicio").submit(function (event) {

                    //disable the default form submission
                    event.preventDefault();
                    //grab all form data  
                    var formData = new FormData($(this)[0]);

                    $.ajax({
                        url: '<%=request.getContextPath()%>/login',
                        type: 'post',
                        data: formData,
                        async: false,
                        cache: false,
                        contentType: false,
                        processData: false,
                        success: function (data) {
                            var dato = $.trim(data);
                            if (dato === "exito") {
                                redirec();
                            } else {
                                swal({title: dato, type: 'warning'});
                            }

                        },
                        error: function (data) {
                            swal({title: 'Error del servidor!', text: data, type: 'warning', timer: 2000, showConfirmButton: false});
                        }
                    });
                    return false;
                });
            });


            $("#forgot").click(function (e) {
                swal({
                    title: "Contraseña olvidada?",
                    text: "Ingrese su Correo de recuperacion",
                    type: "input",
                    showCancelButton: true,
                    closeOnConfirm: false,
                    confirmButtonText: "Enviar",
                    confirmButtonColor: "green"
                }, function (inputValue) {

                    swal({title: "Enviando...", imageUrl: "<%=request.getContextPath()%>/imagenes/loadSpin.gif", showConfirmButton: false});

                    $.post("<%=request.getContextPath()%>/Mail", {opc: 1, n1: inputValue})

                            .done(function (data) {
                                var dato = $.trim(data);
                                if (dato === "no")
                                {
                                    swal({title: "Lo sentimos ese correo no ha sido registrado", type: "error"});
                                } else if (dato === "si") {
                                    swal({title: "Revisa tu bandeja de Entrada", type: "success"});
                                } else {
                                    swal({title: "Lo sentimos ha ocurrido un error, intenta despues", type: "warning"});
                                }
                            });



                });
            });

            function checkeabox() {
                var chckbx = document.getElementById("iniciado");
                if (!chckbx.checked) {

                    chckbx.checked = true;
                } else {
                    chckbx.checked = false;

                }
            }
        </script>
    </body>

</html>