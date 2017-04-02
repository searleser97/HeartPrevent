/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webservicepckg;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

/**
 *
 * @author Silvester
 */
@WebService(serviceName = "WebServiceHP")
public class WebServiceHP {
    db.Condb con;
    Config.Defaults def = new Config.Defaults();
    private final String workingDir = def.webContextPath + "UsrImagenes/";
public rules.Seguridad cifrado = new rules.Seguridad();

    public WebServiceHP() throws UnknownHostException {
        System.out.println(workingDir);
        this.con = new db.Condb();
    }

    /**
     * Web service operation
     *
     * @param username
     * @param password
     * @param gcmid
     * @return
     * @throws java.net.UnknownHostException
     * @throws java.sql.SQLException
     */
    @WebMethod(operationName = "LoginPrincipal")
    public boolean LoginPrincipal(@WebParam(name = "username") String username, @WebParam(name = "password") String password,@WebParam(name="gcmid") String gcmid) throws UnknownHostException, SQLException {
        System.out.println(username+" "+ password);
        System.out.println("GCMID: "+gcmid);
        boolean result = false;
        try {

            //String stringdectypted=cifrado.decripta(password);
            String pass = cifrado.encriptaSHA1(password);
            //String stringdctp=cifrado.decripta(username);
            String correo = cifrado.encriptaAES(username);
            con.conectar();

            ResultSet rs = con.consulta("call validaUsr('1','" + correo + "','" + pass + "')");
            

            if (rs.next()) {
                result = rs.getString("valida").equals("1");
                if(result){
                    con.consulta("call gcmidin('"+convierteMailaUsrid(username)+"','"+gcmid+"')");
                }
            }
            System.out.println(result);
            con.cierraConexion();
        } catch (SQLException ex) {
            System.out.print(ex.getMessage());
        }
        
        return result;
    }

    @WebMethod(operationName = "uploadImg2")
    public void upload(@WebParam(name = "ImgBytes") byte[] imageBytes, @WebParam(name = "UserId") String UsrId, @WebParam(name = "UserPass") String passwd) throws SQLException, FileNotFoundException, IOException {
        if(Validausr(UsrId,passwd)){
        con.conectar();
        ResultSet rsimg = con.consulta("call newpassimage('2','" + convierteMailaUsrid(UsrId) + "','" + "imgperfil.png" + "','0')");
        if (rsimg.next()) {
            Files.write(new File(workingDir+convierteMailaUsrid(UsrId)+"/imgperfil.png").toPath(), imageBytes);
            
        }
        con.cierraConexion();
        }
    }
    public String convierteMailaUsrid(String email) throws SQLException{
        
        email=cifrado.encriptaAES(email);
        String Usrid="";
        System.out.println("ljafsdf");
        ResultSet rsemailid= con.consulta("call datospersona('"+email+"')");
        if(rsemailid.next())
        {
            Usrid=rsemailid.getString("idpersona");
        }
        return Usrid;
    }
    
    @WebMethod(operationName = "InfoPersona")
    public String InfoPersona(@WebParam(name = "username") String username, @WebParam(name = "password") String password) throws  SQLException, UnknownHostException{
        String informacion="";
        
        if(Validausr(username,password)){
            System.out.println("holaquehcow");
            con.conectar();
          String idper=convierteMailaUsrid(username);
            System.out.println("idper=;" +idper);
          
          ResultSet rsdatosper=con.consulta("call datospersona('"+idper+"')");
          if(rsdatosper.next())
          {
          informacion=rsdatosper.getString("nombre")+" "+rsdatosper.getString("apellido")+"~~'="+rsdatosper.getString("imagen");
              System.out.println(informacion);
          }
          
        }
        con.cierraConexion();
        return informacion;
    }
    
    @WebMethod(operationName = "Publicaciones")
    public String[][] publicaciones(@WebParam(name = "opc") String opc,@WebParam(name = "username") String username, @WebParam(name = "password") String password,@WebParam(name = "query") String query) throws UnknownHostException, SQLException
    {
        System.out.println(query);
        int rows=0;
        String[][] publi=null;
        
        if(Validausr(username,password))
        {
            con.conectar();
            String usrid=convierteMailaUsrid(username);
            ResultSet traepost=con.consulta("call traepost('"+opc+"','"+query+"','0')");
            int i=0;
            
            if(traepost.last())
            {
               rows= traepost.getRow();
               traepost.beforeFirst();
            }
            publi=new String[9][rows];
            while(traepost.next())
            {
                if(i==49)
                {
                    break;
                }
                
                publi[0][i]=traepost.getString("idpersona");
                publi[1][i]=traepost.getString("descripcion");
                publi[2][i]=traepost.getString("nombre");
                publi[3][i]=traepost.getString("apellido");
                publi[4][i]=traepost.getString("fechapublicacion").substring(0, 16);
                publi[5][i]=traepost.getString("imagen");
                publi[6][i]=traepost.getString("idpublicacion");
                publi[7][i]=numcomentarios(publi[6][i]);
                publi[8][i]=usrid;
                System.out.println(publi[0][i]+" "+publi[1][i]+" "+publi[2][i]+" "+publi[3][i]+" "+publi[4][i]+" "+publi[5][i]+" "+publi[6][i]+" "+publi[7][i]);
                i++;
            }
            con.cierraConexion();
        }
        
        return publi;
    }
    
    public String numcomentarios(String idp) throws SQLException{
        String ncomentarios="sin";
        con.conectar();
        ResultSet rscomentarios=con.consulta("call getcomment('"+idp+"')");
        if(rscomentarios.last())
        {
            ncomentarios=""+rscomentarios.getRow();
        }
        con.cierraConexion();
        return ncomentarios;
    }
    
    @WebMethod(operationName="comentarios")
    public String[][] comentario(@WebParam(name = "username") String username, @WebParam(name = "password") String password,@WebParam(name = "idp") String idpublicacion) throws SQLException, UnknownHostException{
        
        String[][] comentarios=null;
        
        
        if(Validausr(username,password))
        {
            
           int rows=0;
           con.conectar();
           ResultSet getcomment=con.consulta("call getcomment('"+idpublicacion+"')");
           if(getcomment.last())
            {
               rows= getcomment.getRow();
               getcomment.beforeFirst();
            }
           comentarios=new String[4][rows];
           for(int j=0;getcomment.next();j++)
           {
               
               comentarios[0][j]=getcomment.getString("comentario");
               comentarios[1][j]=getcomment.getString("nombre")+" "+getcomment.getString("apellido");
               comentarios[2][j]=getcomment.getString("imagen");
               comentarios[3][j]=getcomment.getString("idpersona");
               System.out.println(comentarios[0][j]);
           }
           con.cierraConexion();
        }
        
        
        return comentarios;
    }
    
    @WebMethod(operationName="traeMedicamentos")
    public String[][] medicamentos(@WebParam(name = "username") String username, @WebParam(name = "password") String password) throws UnknownHostException, SQLException{
        String[][] medicamentosarray=null;
         if(Validausr(username,password))
        {
            con.conectar();
            int rows=0;
            String UsrId=convierteMailaUsrid(username);
            ResultSet traemedicamentos=con.consulta("call traemedicamentos('3','"+UsrId+"','0','0')");
            ResultSet traemedicamentospersonalizados=con.consulta("call traemedicamentos('4','"+UsrId+"','0','0')");
            
            if(traemedicamentos.last())
            {
               rows= traemedicamentos.getRow();
               traemedicamentos.beforeFirst();
            }
            
            if(traemedicamentospersonalizados.last())
            {
               rows+= traemedicamentospersonalizados.getRow();
               traemedicamentospersonalizados.beforeFirst();
            }
            medicamentosarray=new String[8][rows];
            int k=0;
            while(traemedicamentos.next()){
               medicamentosarray[0][k]=traemedicamentos.getString("idmedicamentos");
               medicamentosarray[1][k]= traemedicamentos.getString("medicamento");
               medicamentosarray[2][k]= traemedicamentos.getString("fechainicio");
               medicamentosarray[3][k]= traemedicamentos.getString("fechafin");
               medicamentosarray[4][k]= traemedicamentos.getString("periodo");
               medicamentosarray[5][k]= traemedicamentos.getString("estado");
               medicamentosarray[6][k]=traemedicamentos.getString("idrelusumedicamentos");
               medicamentosarray[7][k]="1";
               k++;
            }
            while(traemedicamentospersonalizados.next()){
               medicamentosarray[0][k]=traemedicamentospersonalizados.getString("idmedicamentospersonalizados");
               medicamentosarray[1][k]=traemedicamentospersonalizados.getString("medicamentopersonalizado");
               medicamentosarray[2][k]= traemedicamentospersonalizados.getString("fechainicio");
               medicamentosarray[3][k]= traemedicamentospersonalizados.getString("fechafin");
               medicamentosarray[4][k]= traemedicamentospersonalizados.getString("periodo");
               medicamentosarray[5][k]= traemedicamentospersonalizados.getString("estado");
               medicamentosarray[6][k]=traemedicamentospersonalizados.getString("idrelusumedipersonalizado");
               medicamentosarray[7][k]="2";
                k++;
            }
            con.cierraConexion();
        }
        return medicamentosarray;
    }
    
    @WebMethod(operationName="hazpublicacion")
    public boolean hazpublicacion(@WebParam(name = "username") String username, @WebParam(name = "password") String password,@WebParam(name = "descripcion") String descripcionpublicacion) throws UnknownHostException, SQLException{
        if(Validausr(username,password))
        {
            con.conectar();
            con.consulta("call newpost('" + convierteMailaUsrid(username) + "','0','" + cifrado.stremplaza(descripcionpublicacion) + "','2','1')");
            con.cierraConexion();
            return true;
        }
        else
        {
            return false;
        }
        
    }
    
    @WebMethod(operationName="hazcomentario")
    public boolean hazcomentario(@WebParam(name = "username") String username, @WebParam(name = "password") String password,@WebParam(name = "descripcion") String descripcioncomentario,@WebParam(name="idpublic") String idp) throws SQLException, UnknownHostException{
       if(Validausr(username,password))
        {
            con.conectar();
            con.consulta("call newpost('" + convierteMailaUsrid(username) + "','"+idp+"','" + cifrado.stremplaza(descripcioncomentario) + "','2','2')");
            con.cierraConexion();
            return true;
        }
        else
        {
            return false;
        }
    }
    
    @WebMethod(operationName="contactos")
    public String[][] contacts(@WebParam(name = "username") String username, @WebParam(name = "password") String password) throws UnknownHostException, SQLException{
        String[][] contactarray=null;
        
        if(Validausr(username,password)){
            System.out.println(username);
            int rows=0;
            con.conectar();
            ResultSet traecontacts=con.consulta("call friends('0','"+convierteMailaUsrid(username)+"','0')");
            if(traecontacts.last())
            {
               rows= traecontacts.getRow();
               traecontacts.beforeFirst();
            }
            contactarray=new String[4][rows];
            for(int k=0;traecontacts.next();k++)
            {
                contactarray[0][k]=traecontacts.getString("idpersona");
                contactarray[1][k]=traecontacts.getString("imagen");
                contactarray[2][k]=traecontacts.getString("nombre")+" "+traecontacts.getString("apellido");
                contactarray[3][k]=cifrado.decriptaAES(traecontacts.getString("telefono"));
                System.out.println(contactarray[0][k]+" "+contactarray[1][k]+" "+contactarray[2][k]);
            }
            con.cierraConexion();
            }
        
        
        return contactarray;
    }
    
    @WebMethod(operationName="buscapublicacion")
    public String[][] buscapublicacion(@WebParam(name = "username") String username, @WebParam(name = "password") String password,@WebParam(name = "query") String query) throws UnknownHostException, SQLException{
        String[][] pubarray=null;
        if(Validausr(username,password))
        {
            int rows=0;
            con.conectar();
            ResultSet   rs1 = con.consulta("call traepost('6','" + query + "','0')");
             if(rs1.last())
            {
               rows= rs1.getRow();
               rs1.beforeFirst();
            }
             pubarray=new String[3][rows];
             for(int i=0;rs1.next();i++)
            {
                pubarray[0][i]=rs1.getString("idpersona");
                pubarray[1][i]=rs1.getString("descripcion");
                pubarray[2][i]=rs1.getString("nombre");
                pubarray[3][i]=rs1.getString("apellido");
                pubarray[4][i]=rs1.getString("fechapublicacion").substring(0, 16);
                pubarray[5][i]=rs1.getString("imagen");
                pubarray[6][i]=rs1.getString("idpublicacion");
                pubarray[7][i]=numcomentarios(pubarray[6][i]);
            }
            con.cierraConexion();
        }
        return pubarray;
    }
    
    @WebMethod(operationName="togglemedicamentos")
    public boolean togglemedicamentos(@WebParam(name = "username") String username, @WebParam(name = "password") String password, @WebParam(name = "idrelusumedicamento") String idrelusumedicamento,@WebParam(name="tipo") String tipo,@WebParam(name="idmedicamento") String idmedicamento) throws UnknownHostException, SQLException{
        if(Validausr(username,password))
        {
            
            con.conectar();
            System.out.println("('"+tipo+"','"+convierteMailaUsrid(username)+"','"+idmedicamento+"','"+idrelusumedicamento+"')");
                con.consulta("call traemedicamentos('"+tipo+"','"+convierteMailaUsrid(username)+"','"+idmedicamento+"','"+idrelusumedicamento+"')");
            System.out.println("('"+tipo+"','"+convierteMailaUsrid(username)+"','"+idmedicamento+"','"+idrelusumedicamento+"')");
                con.cierraConexion();
        }
        return true;
    }
    
    @WebMethod(operationName="inemergencia")
    public boolean inemergencia(@WebParam(name = "username") String username, @WebParam(name = "password") String password,@WebParam(name = "lat") String lat,@WebParam(name = "lon") String lon, @WebParam(name = "ppm") String ppm) throws UnknownHostException, SQLException, IOException{
        if(Validausr(username,password))
        {
            
            
             
            con.conectar();
            con.consulta("call emergencias('1','"+convierteMailaUsrid(username)+"','"+lat+"','"+lon+"','"+ppm+"')");
            ResultSet traecontactosconfianza=con.consulta("call friends('10','"+convierteMailaUsrid(username)+"','0')");
            ResultSet userdata=con.consulta("call datospersona("+convierteMailaUsrid(username)+")");
            String NombreUsr="";
            if(userdata.next())
            {
                NombreUsr=userdata.getString("nombre")+" "+userdata.getString("apellido");
            }
            
            while(traecontactosconfianza.next()){
                
                String GOOGLE_SERVER_KEY = "AIzaSyBLDvZjkAUsV0_qxIDC4OuLz2-fGzXdiCc";
             String MESSAGE_KEY = "message";	
                String userMessage = "Alerta! el ritmo cardiaco de "+NombreUsr+" ha alcanzado niveles criticos.";
				Sender sender = new Sender(GOOGLE_SERVER_KEY);
				Message message = new Message.Builder().timeToLive(30)
						.delayWhileIdle(true).addData(MESSAGE_KEY, userMessage).build();
				Result result = sender.send(message,traecontactosconfianza.getString("gcmid") , 1);
                                System.out.println("regId: " + traecontactosconfianza.getString("gcmid"));    
                                System.out.println(result);
            }
            con.cierraConexion();
        }
        return true;
    }
    
    @WebMethod(operationName="Validausr")
    public boolean Validausr(@WebParam(name = "username") String username, @WebParam(name = "password") String password) throws UnknownHostException, SQLException {
        System.out.println(username+"-"+ password);
        boolean result = false;
        try {

            //String stringdectypted=cifrado.decripta(password);
            String pass = cifrado.encriptaSHA1(password);
            //String stringdctp=cifrado.decripta(username);
            String correo = cifrado.encriptaAES(username);
            con.conectar();

            ResultSet rs = con.consulta("call validaUsr('1','" + correo + "','" + pass + "')");
            

            if (rs.next()) {
                result = rs.getString("valida").equals("1");
            }
            System.out.println(result);
            con.cierraConexion();
        } catch (SQLException  ex) {
            System.out.print(ex.getMessage());
        }
        return result;
    }
    
     @WebMethod(operationName="alertasu")
     public String[][] alertasu(@WebParam(name = "username") String username, @WebParam(name = "password") String password) throws UnknownHostException, SQLException{
         String alertasarray[][]=null;
         if(Validausr(username,password)){
             System.out.println("alertasmen");
             int rows =0;
             con.conectar();
             ResultSet rs=con.consulta("call emergencias('2','"+convierteMailaUsrid(username)+"','0','0','0')");
              if(rs.last())
            {
               rows= rs.getRow();
               rs.beforeFirst();
            }
              alertasarray=new String[3][rows];
             for(int i=0;rs.next();i++)
             {
                 alertasarray[0][i]=rs.getString("ubicacionx");
                 alertasarray[1][i]=rs.getString("ubicaciony");
                 alertasarray[2][i]=rs.getString("ppm");
                 System.out.println(alertasarray[0][i]+" "+alertasarray[1][i]+" "+alertasarray[2][i]);
             }
             con.cierraConexion();
         }
         return alertasarray;
     }
     
     @WebMethod(operationName="inmedicamentos")
     public boolean inmedicamentos(@WebParam(name = "username") String username, @WebParam(name = "password") String password,@WebParam(name="medicamento") String medicamento,@WebParam(name="fechaini") String fechini,@WebParam(name="fechafin") String fechfin,@WebParam(name="periodo") String periodo) throws UnknownHostException, SQLException{
         System.out.println(medicamento);
         if(Validausr(username,password)){
             con.conectar();
             ResultSet rs = con.consulta("call medicamentos('1','" + convierteMailaUsrid(username) + "','" + medicamento + "','" + fechini + "','" + fechfin + "','" + periodo + "')");
            if(rs.next())
            {
                if(rs.getString("valor").equals("exito"))
                {
                    con.cierraConexion();
                     return true;
                }
                else
                {
                    con.cierraConexion();
                    return false;
                }
            }
            else{
                con.cierraConexion();
                return false;
            }
             
         }
         else{
             return false;
         }
     }
     
     @WebMethod(operationName="listamedica")
     public String[] listamedica() throws SQLException{
         con.conectar();
         String medicamentos[]=null;
         ResultSet rsmedica2=con.consulta("call traemedicamentos('0','0','0','0')");
         int rows =0;
         if(rsmedica2.last())
            {
               rows= rsmedica2.getRow();
               rsmedica2.beforeFirst();
            }
         medicamentos=new String[rows];
         for(int i=0;rsmedica2.next();i++){
             medicamentos[i]=rsmedica2.getString("medicamento");
         }
         con.cierraConexion();
         return medicamentos;
     }
     
     @WebMethod(operationName = "eliminapub")
    public boolean eliminapub(@WebParam(name = "username") String username, @WebParam(name = "password") String password,@WebParam(name="idpublicacion") String idp) throws UnknownHostException, SQLException {
         System.out.println(username+" "+password+" "+idp);
        boolean result = false;
        try {
            if(Validausr(username,password))
            {
            con.conectar();
                System.out.println("entro: "+convierteMailaUsrid(username));
            con.consulta("call traepost('4','" + idp + "','" + convierteMailaUsrid(username) + "')");
            con.cierraConexion();
            }
        } catch (SQLException ex) {
            System.out.print(ex.getMessage());
        }
        
        return result;
    }
}

//    @WebMethod
//    public byte[] download(String fileName){
//        return fileName;
//}

