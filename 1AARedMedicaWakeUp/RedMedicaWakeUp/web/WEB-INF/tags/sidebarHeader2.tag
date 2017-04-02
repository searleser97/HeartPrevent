<%-- 
    Document   : sidebarHeader2
    Created on : Nov 14, 2015, 2:10:05 PM
    Author     : Silvester
--%>

<%@tag description="sidebarHeader2" pageEncoding="UTF-8"%>
                <li>
                    <a href="InicioRed.jsp">Blog&nbsp;&nbsp;&nbsp;
                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                        
                        <span class="glyphicon glyphicon-list-alt margenSide"></span></a>
                </li>
                <li>
                    <a href="contactos.jsp">Amigos&nbsp;&nbsp;&nbsp;
                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="glyphicon glyphicon-user margenSide"><span class="glyphicon glyphicon-user amigos"></span></span></a>
                </li>
                <li>
                    <a href="medicamentos.jsp">Medicamentos&nbsp;&nbsp;
                        <span class="glyphicon glyphicon-bell margenSide"></span></a>
                </li>
                <li>
                    <a href="historial.jsp">Historial&nbsp;&nbsp;
                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                        <span class="glyphicon glyphicon-folder-open margenSide"></span></a>
                </li>
                <li class="confi">
                    <a id="sacasubmenu" href="#">Configuraciones<span class="glyphicon glyphicon-cog margenSide"></span></a>
                    <ul id="submenuside" class="submenuside">
                        <li><a href="confi.jsp?opc=1">Perfil&nbsp;&nbsp;&nbsp;&nbsp;
                                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                &nbsp;&nbsp;&nbsp;&nbsp;<span class="glyphicon glyphicon-picture margenSide"></span></a></li>
                        <li>
                            <a href="confi.jsp?opc=2">Cuenta&nbsp;&nbsp;&nbsp;&nbsp;
                                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                <span class="glyphicon glyphicon-wrench margenSide"></span></a>
                        </li>
                        <li><a href="confi.jsp?opc=3">Contraseña&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="glyphicon glyphicon-lock margenSide"></span></a></li>
                    </ul>
                </li>
               
                <li>
                    <a href="http://localhost:8080/HeartPreventWeb">Nosotros</a>
                </li>
                <li>
                    <a href="?opc=5">Cerrar Sesión&nbsp;&nbsp;&nbsp;&nbsp;<span class="glyphicon glyphicon-off margenSide"></span></a>
                </li>
            </ul>
        </div>
        <!-- /#sidebar-wrapper -->
        <div class="encabezado">
            
            <a href="#menu-toggle" class="menu-toggle">
                <span class="glyphicon glyphicon-menu-hamburger black large "></span></a>
            <form  class="formbusca" method="get" action="busqueda.jsp">
                <input type="search" class="buscausu" name="q"/>
                <input type="submit" class="botonbuscausu" value="buscar"/>
            </form>
                
                
                <a href="chat"><span class="glyphicon glyphicon-envelope large black iconss1"></span></a>
                <a href="notificactions"><span class="glyphicon glyphicon-globe large black iconss"></span></a>
        </div>
        <div style="position: fixed; top:5px; left: 80px; z-index: 1000001; font-family: Copperplate, 'Copperplate Gothic Light', fantasy; font-size: 30px; color: #23527c;">HeartPrevent</div>