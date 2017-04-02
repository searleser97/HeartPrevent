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
            String catego=(String) sesion.getAttribute("UsrCategoria");
            if (sesion.getAttribute("UsrNombre") == null) {
                response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/start"));
                return;
            }
            String UsrId = (String) sesion.getAttribute("UsrId");
            String nom = (String) sesion.getAttribute("UsrNombre");
            String ape = (String) sesion.getAttribute("UsrApellido");
            String corre = (String) sesion.getAttribute("UsrCorreo");
            String image = (String) sesion.getAttribute("UsrImagen");
            String telefono = (String) sesion.getAttribute("UsrTelefono");
            
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
        if (catego.equals("1")){
            %>
                <script>
                                    
                                        //setTimeout('redirec()',2000);
                                        swal({title: "Seccion solo para usuarios Premium",type: "warning",animation:"slide-from-top"},
                                        function(){window.location="InicioRed.jsp";});
                                </script>
        <%
            return;
        }
            //response.setIntHeader("Refresh", 60);
            
            
            rules.Seguridad cifrado=new rules.Seguridad();
            db.Condb con = new db.Condb();
            con.conectar();
            ResultSet datospersona=con.consulta("call datospersona('"+UsrId+"')");
            String[] Usrtipo=new String[3];
            for(int i=0;datospersona.next();i++)
            {
                Usrtipo[i]=datospersona.getString("idtipo");
                
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
                <h1 style="text-align: center; font-weight: 900; color: gray; margin-top: 50px;">Tus Pacientes</h1>
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
                
                        <%
                        ResultSet rs=null;
                        rs=con.consulta("call traepacientes('1','"+UsrId+"')");
                        if (!rs.isBeforeFirst()) {%>
                                <script>
                                    
                                        //setTimeout('redirec()',2000);
                                        swal({title: "Aun no tienes pacientes",type: "warning",animation:"slide-from-top"},
                                        function(){window.location="InicioRed.jsp";});
                                </script>
                                <% 
                                return;
                            }
                        while(rs.next())
                        {
                            ResultSet rsenvia=con.consulta("call solicitudmensaje('"+UsrId+"','"+rs.getString("idpersona")+"')");
                           if(rsenvia.next())
                           {
                               if(!rs.getString("idpersona").equals(UsrId))
                               {
                                   
                                  String msjdoc=rsenvia.getString("valor2");
                                  String valor=rsenvia.getString("valor");
                                  if(rsenvia.getString("valida").equals("1"))
                                  {
                                      
                                      valor="";
                                  }
                        %>
                        <div class="usubuscado">
                            <div data-us="<%=rs.getString("idpersona")%>" fname="goto" class="image">
                                <img data-us="<%=rs.getString("idpersona")%>" fname="goto" class="imgusubuscado" src="<%=request.getContextPath()%>/UsrImagenes/<%=rs.getString("imagen")%>">
                            </div>
                            <div id="username_<%=rs.getString("idpersona")%>" data-us="<%=rs.getString("idpersona")%>" fname="goto" class="nomusubuscado puntero">
                                <%=rs.getString("nombre")%> <%=rs.getString("apellido")%>
                            </div>
                            <form data-us="<%=rs.getString("idpersona")%>" fname="goto" id="formapaciente<%=rs.getString("idpersona")%>" action="historial.jsp" class="linksbuscado" method="post">
                                <%=msjdoc%>
                                <input type="hidden" name="paciente" value="<%=rs.getString("idpersona")%>">
                                <a class="puntero input-to-link" onclick="document.getElementById('formapaciente<%=rs.getString("idpersona")%>').submit()">
                                    <span class="icomoon icon-file-text2 medium"></span>&nbsp;&nbsp;
                                </a>
                                <%=valor%>
                            </form>
                        </div>
                        <%
                               }
                           }
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

</html>