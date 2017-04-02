<%-- 
    Document   : FAQ
    Created on : Apr 1, 2016, 8:02:41 PM
    Author     : vaioubuntu
--%>

<%@page import="java.sql.ResultSet"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!doctype html>
<html lang="en" class="no-js">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="icon" href="<%=request.getContextPath()%>/imagenes/logo.png" >
        <link rel="apple-touch-icon" href="<%=request.getContextPath()%>/imagenes/logo.png" sizes="114x114">
	<link href='http://fonts.googleapis.com/css?family=Open+Sans:400,300,600,700' rel='stylesheet' type='text/css'>

	<link rel="stylesheet" href="css/reset.css"> <!-- CSS reset -->
	<link rel="stylesheet" href="css/style.css"> <!-- Resource style -->
	<script src="js/modernizr.js"></script> <!-- Modernizr -->
	<title>HeartPrevent FAQ</title>
</head>
<body>
<header>
	<h1>HeartPrevent FAQ</h1>
</header>
<section class="cd-faq">
    <ul class="cd-faq-categories">
    <%
        db.Condb con=new db.Condb();
        con.conectar(); 
        ResultSet traeFaqcatego=con.consulta("call traefaq('1')");
        ResultSet traeFaq=con.consulta("call traefaq('0')");
       while(traeFaqcatego.next())
       {
        
    %>
	<li><a href="#<%=traeFaqcatego.getString("categoriafaq")%>"><%=traeFaqcatego.getString("categoriafaq")%></a></li>
	<%
          }

        %>
	</ul> <!-- cd-faq-categories -->

        
	<div class="cd-faq-items">
            
            <%
                String catego="";
            while(traeFaq.next())
            {
                if (catego.equals(""))
                {
                    %>
            <ul id="<%=traeFaq.getString("categoriafaq")%>" class="cd-faq-group">
                <li class="cd-faq-title"><h2><%=traeFaq.getString("categoriafaq")%></h2></li>
            <%
                }
                else if(!catego.equals(traeFaq.getString("categoriafaq")))
                {
                    %>
            </ul>
            <ul id="<%=traeFaq.getString("categoriafaq")%>" class="cd-faq-group">
                <li class="cd-faq-title"><h2><%=traeFaq.getString("categoriafaq")%></h2></li>
            <%
                }
                
            %>
			<li>
				<a class="cd-faq-trigger" href="#0"><%=traeFaq.getString("faq")%></a>
				<div class="cd-faq-content">
                                    <p><%=traeFaq.getString("respuestafaq")%></p>
                                </div> <!-- cd-faq-content -->
			</li>
		
                <%
                
                catego=traeFaq.getString("categoriafaq");
            }
con.cierraConexion();
                %>
	</ul> <!-- cd-faq-group -->
        </div> <!-- cd-faq-items -->
	<a href="#0" class="cd-close-panel">Close</a>
</section> <!-- cd-faq -->
<script src="js/jquery-2.1.1.js"></script>
<script src="js/jquery.mobile.custom.min.js"></script>
<script src="js/main.js"></script> <!-- Resource jQuery -->
</body>
</html>
