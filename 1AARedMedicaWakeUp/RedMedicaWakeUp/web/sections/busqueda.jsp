<%@page import="java.nio.charset.Charset"%>
<%@page import="java.net.Inet4Address"%>
<%@page import="java.sql.ResultSet"%>
<!DOCTYPE html>
<html lang="en">

    <head>
        <%
            HttpSession sesion = request.getSession();
            if (sesion.getAttribute("UsrNombre") == null) {
                response.sendRedirect(response.encodeRedirectURL(request.getContextPath()+"/start"));
                return;
            }
            String nom = (String) sesion.getAttribute("UsrNombre");
            String ape = (String) sesion.getAttribute("UsrApellido");
        %>
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
                }
                else
                {
                    esto2.innerHTML = "ocultar " + num + " comentarios";
                    for (var i = 0, max = esto.length; i < max; i++) {
                        esto[i].style.display = "block";
                    }
                }

            }
        </script>
    </head>

   <body style="background-image: url('<%=request.getContextPath()%>/imagenes/gradient_bg.jpg'); background-repeat: no-repeat; background-attachment: fixed; background-size: cover;">

        <%
            //response.setIntHeader("Refresh", 60);
            String UsrId = (String) sesion.getAttribute("UsrId");
            
            String corre = (String) sesion.getAttribute("UsrCorreo");
            String image = (String) sesion.getAttribute("UsrImagen");
            String telefono = (String) sesion.getAttribute("UsrTelefono");
            String buscado = request.getParameter("Usr");
            String query = request.getParameter("q");
            String opc = request.getParameter("opc");
        rules.Seguridad cifrado=new rules.Seguridad();
        
            if(query!=null){
                query = cifrado.stremplaza(request.getParameter("q"));
            }
        
        db.Condb con = new db.Condb();
            con.conectar();
             if (opc == null) {
                opc = "-1";
            }
           else if (opc.equals("56")) {
                String pub = request.getParameter("pub");
                con.consulta("call traepost('4','" + pub + "','" + UsrId + "')");
                
                    response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/sections/busqueda.jsp?q=" + buscado + ""));
                
            }
            else if (opc.equals("57")) {
                String com = request.getParameter("com");
                con.consulta("call traepost('5','" + com + "'," + UsrId + ")");
                
                    response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/sections/busqueda.jsp?q=" + buscado + ""));
                
            }
            
             ResultSet datospersona = con.consulta("call datospersona('" + UsrId + "')");
            String[] Usrtipo = new String[3];
            String estado = "";
            String Usrcatego = "";
            for (int i = 0; datospersona.next(); i++) {
                Usrcatego = datospersona.getString("idcategoria");
                Usrtipo[i] = datospersona.getString("idtipo");
                estado = datospersona.getString("idestado");
            }
            if (buscado == null) {
                buscado = "";
            }
            
            if(query==null || query.equals("") || query.trim().equals(""))
            {
                %>
                                <script>
                                    
                                        //setTimeout('redirec()',2000);
                                        swal({title: "Ingresa algo en el cuadro de busqueda",type: "warning",animation:"slide-from-top"},
                                        function(){window.history.back();});
                                </script>
                                <%
                return;
            }

            String idpublicacion = request.getParameter("idpublicacion");
            String comentario = request.getParameter("comentario");
            String publicacion = request.getParameter("publicacion");

            
ResultSet catego = null;
            if (comentario != null) {
                catego = con.consulta("call newpost('" + UsrId + "','" + idpublicacion + "','" + cifrado.stremplaza(comentario) + "','" + Usrcatego + "')");
                comentario = null;
                if (catego.next()) {
                    
                        response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/sections/busqueda.jsp?q="+query));
                    

                }
            }


        %>
<input id="iduser" type="hidden" value="<%=UsrId%>">
<input type="hidden" id="querybusc" value="<%=query%>">
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
String imagenusu="";
String nombreusu="";
String apeusu="";
String correousu="";
String telusu = "";
    imagenusu=image;
    nombreusu=nom;
    apeusu=ape;
    correousu=cifrado.decriptaAES(corre);
   telusu = cifrado.decriptaAES(telefono);
   String nombrefull=nombreusu+apeusu;
                        if(nombrefull.length()>21){
                            nombrefull=nombreusu+"<br>"+apeusu;
                        }else{
                            nombrefull=nombreusu+"&nbsp;"+apeusu;
                        }
%>
                <div class="usuario">
                    <div class="imagenusudiv">
                        <img class="imagenUsu" src="<%=request.getContextPath()%>/UsrImagenes/<%=imagenusu%>" alt=""/>
                    </div>

                        <div class="infoUsu" style="font-weight: 700;text-align: center;"><%=nombrefull%></div>
                </div>
                
                <div class="espacioBusqueda"></div>
                <div id="divbusqueda">
                        <%
                        
                        ResultSet rs=null;
                        rs=con.consulta("call traepost('2','"+query.replaceAll(" ", "")+"','0')");
                        boolean noHayUsers=false;
                        boolean noHayPosts=false;
                        if (!rs.isBeforeFirst()) {
                                noHayUsers=true;
                            }
                        
                        while(rs.next())
                        {
                           ResultSet rsenvia=con.consulta("call solicitudmensaje('"+UsrId+"','"+rs.getString("idpersona")+"')");
                           if(rsenvia.next())
                           {
                               String msjdoc="";
                               String valor="";
                               if(!rs.getString("idpersona").equals(UsrId))
                               {
                                   
                                  msjdoc=rsenvia.getString("valor2");
                                  valor=rsenvia.getString("valor");
                                  if(rsenvia.getString("valida").equals("1"))
                                  {
                                      
                                      valor="";
                                  }
                                  
                               }
                               else
                               {
                                   msjdoc="";
                                   valor="";
                               }
                        %>
                        <form class="usubuscado puntero" action="chat.jsp" method="post">
                            <div data-us="<%=rs.getString("idpersona")%>" fname="goto" class="image">
                                <img data-us="<%=rs.getString("idpersona")%>" fname="goto" class="imgusubuscado" src="<%=request.getContextPath()%>/UsrImagenes/<%=rs.getString("imagen")%>">
                            </div>
                            <div id="username_<%=rs.getString("idpersona")%>" data-us="<%=rs.getString("idpersona")%>" fname="goto" class="nomusubuscado puntero">
                                <%=rs.getString("nombre")%> <%=rs.getString("apellido")%>
                            </div>
                            <div data-us="<%=rs.getString("idpersona")%>" fname="goto" class="linksbuscado"><%=msjdoc%><%=valor%></div>
                        </form>
                        <%
                           }
                        }
                        %>
                        
                </div>
                <%
                        try{
                     ResultSet   rs1 = con.consulta("call traepost('6','" + query.replaceAll(" ", "") + "','0')");
                     if(!rs1.isBeforeFirst())
                     {
                         noHayPosts=true;
                     }
                     ResultSet rs2 = null;
                    String unico = "0";
                    String nounico = "z";
                    while (rs1.next()) {

                        String idp = rs1.getString("idpublicaciones");
                        String rutaimg = request.getContextPath() + "/UsrImagenes/" + rs1.getString("Imagen");
                        String elimina = "";
                        if (rs1.getString("idpersona").equals(UsrId)) {
                            elimina = "<a id='" + idp + "' class='puntero' onclick='confirmadelpub("+idp+",\""+query+" \")'>Eliminar</a>";
                        }
                %>
               
                    <div  class="onLoad postear">
                    <div class="postIDlink" onclick="irAPost(<%=idp%>)"></div>
                    <div style="width: 42px; height:42px; position: absolute;">
                        <img class="imagenpost" src="<%=rutaimg%>"></div>
                    <div style="float: left;position:absolute; margin-top: 15px; margin-left: 55px; width: 150px; font-weight: bold; font-size: 16px; ">
                        <a style="color: white; width: 200px; overflow: hidden; text-overflow: ellipsis; white-space: nowrap; display:block;" href="?Usr=<%=rs1.getString("idpersona")%>"><%=rs1.getString("nombre")%>&nbsp;<%=rs1.getString("apellido")%></a>
                    </div>
                    <div class="fechapub"><%=rs1.getString("fechapublicacion").substring(0, 16)%></div>
                    <div style=" position: absolute; right: 5px; "><%=elimina%></div>
                </div>

                <div class="onLoad publicacionhecha">
                    <div style="position: relative; width: 90%; margin-left: auto; margin-right: auto;">
                        <%=rs1.getString("Descripcion")%>
                    </div>
                </div>



                <div class="onLoad comentario">
                    <%
                        elimina = "";
                        rs2 = con.consulta("call getcomment('" + idp + "')");
                        int numcomentarios = 0;
                        int comexceso = 0;
                        String oculta = "";
                        String muestramas = "";
                        String docpremium = "";
                        ResultSet rspremiumdoc = null;
                        while (rs2.next()) {
                            numcomentarios++;
                            if (numcomentarios > 4) {
                                comexceso++;
                                muestramas = "<a style='margin-left: 6px; cursor: pointer;' onclick=\"muestrame(document.getElementsByName('" + nounico + "'),this," + numcomentarios + ")\">ver " + comexceso + " comentarios mas</a>";
                                oculta = "display: none;";

                            }
                            String rutaimg2 = request.getContextPath() + "/UsrImagenes/" + rs2.getString("Imagen");
                            String desplega = "";
                            if (rs2.getString("idpersona").equals(UsrId)) {
                                desplega = "onmouseover=\"desplega(document.getElementById('" + unico + "'))\" onmouseout=\"nodesplega(document.getElementById('" + unico + "'))\"";
                                elimina = "<a id='" + unico + "' name='" + unico + "' class='desplega puntero' onclick='confirmadelcom("+rs2.getString("idcomentario")+" ,\""+query+"\" )'>"
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
                    %>

                    <div <%=desplega%> data-toggle="tooltip" data-placement="left" title="<%=rs2.getString("fechacomentario").substring(0, 16)%>" id="<%=nounico%>" name="<%=nounico%>" style="position: relative; <%=oculta%>">
                        <div style="position: absolute; margin-left:13px; margin-top: 3px;">
                            <img class="imagencomment" src="<%=rutaimg2%>"/>
                        </div>
                        <div class="anamecomment"><a href="?Usr=<%=rs2.getString("idpersona")%>"><%=rs2.getString("nombre") + " " + rs2.getString("apellido")%><%=docpremium%>:</a>
                            &nbsp;<%=rs2.getString("comentario")%>
                        </div>
                        <%=elimina%>
                        <hr>

                    </div>
                    <%
                            elimina = "";
                            docpremium = "";
                            unico += "0";
                        }
                    %>
                    <%=muestramas%>
                    <form method="post">
                        <textarea id="comentalotext" class="areatext tcomment" placeholder="Algun Comentario?" name="comentario" rows="2" required></textarea>
                        <input type="hidden" name="idpublicacion" value="<%=idp%>">
                        <input type="hidden" name="q" value="<%=query%>"
                    </form>
                </div>
                <%
                            nounico += "z";
                        }
                    } catch (Exception ex) {
                        response.sendRedirect(response.encodeRedirectURL(ex.getMessage()));
                    }

                        if(noHayPosts && noHayUsers)
                        {
                            %>
                                <script>
                                    
                                        //setTimeout('redirec()',2000);
                                        swal({title: "Lo sentimos no hay resultados para:  <%=query%>",type: "warning",animation:"slide-from-top"},
                                        function(){window.history.back();});
                                </script>
                                <%
                        }
                        %>
            </div>
            <!-- /#wrapper -->
        </div>

        <script src="<%=request.getContextPath()%>/js/jquery.js" type="text/javascript"></script>


        <script src="<%=request.getContextPath()%>/js/bootstrap.min.js" type="text/javascript"></script>


        <!-- Menu Toggle Script -->
        <script src="<%=request.getContextPath()%>/js/essentialJs.js" type="text/javascript"></script>
        <script>
            function confirmafriend2(idamigo)
                {
                    var divunico=document.getElementById("divsolicitud"+idamigo);
                    
                 $.post("<%=request.getContextPath()%>/solicitudes", {opc:1,idamigo: idamigo}).done(function (data){
                     divunico.remove();
                 });
                }   
                function rechazafriend2(idamigo)
                {
                    var divunico=document.getElementById("divsolicitud"+idamigo);
                    
                 $.post("<%=request.getContextPath()%>/solicitudes", {opc:2,idamigo: idamigo}).done(function (data){
                     divunico.remove();
                 });
                }
                        function confirmadelpub(idpub,buscado)
{
    swal({title: "Deseas eliminar esta publicacion?",
        showCancelButton: true,
        confirmButtonText: "S\355",
        cancelButtonText: "No"},
    function () {
        window.location="?opc=56&pub="+idpub+"&Usr="+buscado;
    });
}
function confirmadelcom(idcom,buscado)
{
    swal({title: "Deseas eliminar este comentario?",
        showCancelButton: true,
        confirmButtonText: "S\355",
        cancelButtonText: "No"},
    function () {
        window.location="?opc=57&com="+idcom+"&Usr="+buscado;
    });
}
<%con.cierraConexion();%>
        </script>
    </body>

</html>
