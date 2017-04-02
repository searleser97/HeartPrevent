<%-- 
    Document   : usuariosNuevos
    Created on : 28/11/2015, 04:03:39 PM
    Author     : Computadora
--%>

<%@page import="java.net.Inet4Address"%>
<%@page import="java.sql.ResultSet"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>RedMedica</title>
        <%
        HttpSession sesion = request.getSession();
    if(sesion.getAttribute("UsrNombre") == null){
       response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/start"));
        return;
    }
    %>
        <meta http-equiv="Content-Type: text/javascript; charset=UTF-8" content="text/html; charset=UTF-8">

        <title>RedMedica</title>
        <link href="<%=request.getContextPath()%>/css/tablemedicamentos.css" rel="stylesheet" type="text/css"/>
        <link rel="icon" href="<%=request.getContextPath()%>/imagenes/logo.png" >
        <link rel="apple-touch-icon" href="<%=request.getContextPath()%>/<%=request.getContextPath()%>/imagenes/logo.png" sizes="114x114">
        <link href="<%=request.getContextPath()%>/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
        <link href="<%=request.getContextPath()%>/css/simple-sidebar.css" rel="stylesheet" type="text/css"/>
        <link href="<%=request.getContextPath()%>/css/sign.css" rel="stylesheet" type="text/css"/>
        <script src="<%=request.getContextPath()%>/js/jquery.js" type="text/javascript"></script>
        <script src="<%=request.getContextPath()%>/js/jsencrypt.js" type="text/javascript"></script>
        <link href="<%=request.getContextPath()%>/css/administrador.css" rel="stylesheet" type="text/css"/>
        <link href="<%=request.getContextPath()%>/css/administrador2.css" rel="stylesheet" type="text/css"/>
        
        <%
    db.Condb con=new db.Condb();
    con.conectar();
        String UsrId = (String) sesion.getAttribute("UsrId");
        ResultSet datospersona=con.consulta("call datospersona('"+UsrId+"')");
            String[] Usrtipo=new String[3];
            for(int i=0;datospersona.next();i++)
            {
                Usrtipo[i]=datospersona.getString("idtipo");
                
            }
        %>
    </head>
    <body style="background-image: url('<%=request.getContextPath()%>/imagenes/gradient_bg.jpg');">
        <input id="iduser" type="hidden" value="<%=UsrId%>">
        <div id="wrapper">
            <jsp:include page="defaultComponents.jsp">
                <jsp:param name="arrojaComponentesWakeup99" value="sip"></jsp:param>
            </jsp:include>
     <!-- Content-->
     
     <div class="caracter1">
         
         <div id="header" class="menu">
             <br><br><br>
             <ul class="nav">
                 <li><a href="admin_solicitudes.jsp">Solicitudes</a></li>
                 <li><a href="contarUsuarios.jsp">Usuarios</a></li>
             </ul>
                 
             <div id="info">
                 
             </div>
         </div>
         
     </div>
     <div>
         <div class="caracter2">
         <br>
         <table class="letra">
             <th colspan="2"><center>Reporte general de usuarios</center></th>
            <%      
                ResultSet rsmedicausu = con.consulta("call pd_contarTodos()");
                if(rsmedicausu.next()){
            %>
             <tr>
                 <td><strong>Total de Usuarios en la Red Medica</strong></td>
                 <td><strong><center>
                    <%=rsmedicausu.getString("tot1")%> 
                 </center></strong></td>
             </tr>
             <tr>
                 <td><strong>Total de Usuarios Premium</strong></td>
                 <td><strong><center>
                    <%=rsmedicausu.getString("tot2")%>
                 </center></strong></td>
             </tr>
             <tr>
                 <td><strong>Total de Usuarios Estandar</strong></td>
                 <td><strong><center>
                    <%=rsmedicausu.getString("tot3")%>
                 </center></strong></td>
             </tr>
             <tr>
                 <td><strong>Total de Pacientes</strong></td>
                 <td><strong><center>
                    <%=rsmedicausu.getString("tot4")%>
                 </center></strong></td>
             </tr>
             <tr>
                 <td><strong>Total de Pacientes Premium</strong></td>
                 <td><strong><center>
                    <%=rsmedicausu.getString("tot5")%>
                 </center></strong></td>
             </tr>
             <tr>
                 <td><strong>Total de Pacientes Estandar</strong></td>
                 <td><strong><center>
                    <%=rsmedicausu.getString("tot6")%>   
                 </center></strong></td>
             </tr>
             <tr>
                 <td><strong>Total de Medicos</strong></td>
                 <td><strong><center>
                    <%=rsmedicausu.getString("tot7")%>      
                 </center></strong></td>
             </tr>
             <tr>
                 <td><strong>Total de Medicos Premium</strong></td>
                 <td><strong><center>
                    <%=rsmedicausu.getString("tot8")%>    
                 </center></strong></td>
             </tr>
             <tr>
                 <td><strong>Total de Medicos Estandar</strong></td>
                 <td><strong><center>
                    <%=rsmedicausu.getString("tot9")%>     
                 </center></strong></td>
             </tr>
         </table>
         <br>
         </div>
        <%
            }
            con.cierraConexion();
        %>
     </div>
     
     <!-- /Content-->
    
        </div>
                        
<script src="<%=request.getContextPath()%>/js/bootstrap.min.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/js/signjquery.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/js/signup.js" charset="UTF-8"></script>
<script src="<%=request.getContextPath()%>/js/rsaencryptt.js" type="text/javascript"></script>
<!-- Menu Toggle Script -->
<script src="<%=request.getContextPath()%>/js/essentialJs.js" type="text/javascript"></script>
    </body>
</html>

