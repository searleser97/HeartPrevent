
package webservicepckg;

import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.Action;
import javax.xml.ws.FaultAction;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;
import net.java.dev.jaxb.array.StringArray;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 * 
 */
@WebService(name = "WebServiceHP", targetNamespace = "http://webservicepckg/")
@XmlSeeAlso({
    net.java.dev.jaxb.array.ObjectFactory.class,
    webservicepckg.ObjectFactory.class
})
public interface WebServiceHP {


    /**
     * 
     * @param userPass
     * @param imgBytes
     * @param userId
     * @throws IOException_Exception
     * @throws SQLException_Exception
     * @throws FileNotFoundException_Exception
     */
    @WebMethod
    @RequestWrapper(localName = "uploadImg2", targetNamespace = "http://webservicepckg/", className = "webservicepckg.UploadImg2")
    @ResponseWrapper(localName = "uploadImg2Response", targetNamespace = "http://webservicepckg/", className = "webservicepckg.UploadImg2Response")
    @Action(input = "http://webservicepckg/WebServiceHP/uploadImg2Request", output = "http://webservicepckg/WebServiceHP/uploadImg2Response", fault = {
        @FaultAction(className = SQLException_Exception.class, value = "http://webservicepckg/WebServiceHP/uploadImg2/Fault/SQLException"),
        @FaultAction(className = FileNotFoundException_Exception.class, value = "http://webservicepckg/WebServiceHP/uploadImg2/Fault/FileNotFoundException"),
        @FaultAction(className = IOException_Exception.class, value = "http://webservicepckg/WebServiceHP/uploadImg2/Fault/IOException")
    })
    public void uploadImg2(
        @WebParam(name = "ImgBytes", targetNamespace = "")
        byte[] imgBytes,
        @WebParam(name = "UserId", targetNamespace = "")
        String userId,
        @WebParam(name = "UserPass", targetNamespace = "")
        String userPass)
        throws FileNotFoundException_Exception, IOException_Exception, SQLException_Exception
    ;

    /**
     * 
     * @param password
     * @param gcmid
     * @param username
     * @return
     *     returns boolean
     * @throws SQLException_Exception
     * @throws UnknownHostException_Exception
     */
    @WebMethod(operationName = "LoginPrincipal")
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "LoginPrincipal", targetNamespace = "http://webservicepckg/", className = "webservicepckg.LoginPrincipal")
    @ResponseWrapper(localName = "LoginPrincipalResponse", targetNamespace = "http://webservicepckg/", className = "webservicepckg.LoginPrincipalResponse")
    @Action(input = "http://webservicepckg/WebServiceHP/LoginPrincipalRequest", output = "http://webservicepckg/WebServiceHP/LoginPrincipalResponse", fault = {
        @FaultAction(className = UnknownHostException_Exception.class, value = "http://webservicepckg/WebServiceHP/LoginPrincipal/Fault/UnknownHostException"),
        @FaultAction(className = SQLException_Exception.class, value = "http://webservicepckg/WebServiceHP/LoginPrincipal/Fault/SQLException")
    })
    public boolean loginPrincipal(
        @WebParam(name = "username", targetNamespace = "")
        String username,
        @WebParam(name = "password", targetNamespace = "")
        String password,
        @WebParam(name = "gcmid", targetNamespace = "")
        String gcmid)
        throws SQLException_Exception, UnknownHostException_Exception
    ;

    /**
     * 
     * @param password
     * @param username
     * @return
     *     returns java.util.List<net.java.dev.jaxb.array.StringArray>
     * @throws UnknownHostException_Exception
     * @throws SQLException_Exception
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "contactos", targetNamespace = "http://webservicepckg/", className = "webservicepckg.Contactos")
    @ResponseWrapper(localName = "contactosResponse", targetNamespace = "http://webservicepckg/", className = "webservicepckg.ContactosResponse")
    @Action(input = "http://webservicepckg/WebServiceHP/contactosRequest", output = "http://webservicepckg/WebServiceHP/contactosResponse", fault = {
        @FaultAction(className = UnknownHostException_Exception.class, value = "http://webservicepckg/WebServiceHP/contactos/Fault/UnknownHostException"),
        @FaultAction(className = SQLException_Exception.class, value = "http://webservicepckg/WebServiceHP/contactos/Fault/SQLException")
    })
    public List<StringArray> contactos(
        @WebParam(name = "username", targetNamespace = "")
        String username,
        @WebParam(name = "password", targetNamespace = "")
        String password)
        throws SQLException_Exception, UnknownHostException_Exception
    ;

    /**
     * 
     * @param password
     * @param query
     * @param username
     * @return
     *     returns java.util.List<net.java.dev.jaxb.array.StringArray>
     * @throws UnknownHostException_Exception
     * @throws SQLException_Exception
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "buscapublicacion", targetNamespace = "http://webservicepckg/", className = "webservicepckg.Buscapublicacion")
    @ResponseWrapper(localName = "buscapublicacionResponse", targetNamespace = "http://webservicepckg/", className = "webservicepckg.BuscapublicacionResponse")
    @Action(input = "http://webservicepckg/WebServiceHP/buscapublicacionRequest", output = "http://webservicepckg/WebServiceHP/buscapublicacionResponse", fault = {
        @FaultAction(className = UnknownHostException_Exception.class, value = "http://webservicepckg/WebServiceHP/buscapublicacion/Fault/UnknownHostException"),
        @FaultAction(className = SQLException_Exception.class, value = "http://webservicepckg/WebServiceHP/buscapublicacion/Fault/SQLException")
    })
    public List<StringArray> buscapublicacion(
        @WebParam(name = "username", targetNamespace = "")
        String username,
        @WebParam(name = "password", targetNamespace = "")
        String password,
        @WebParam(name = "query", targetNamespace = "")
        String query)
        throws SQLException_Exception, UnknownHostException_Exception
    ;

    /**
     * 
     * @param descripcion
     * @param password
     * @param username
     * @return
     *     returns boolean
     * @throws SQLException_Exception
     * @throws UnknownHostException_Exception
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "hazpublicacion", targetNamespace = "http://webservicepckg/", className = "webservicepckg.Hazpublicacion")
    @ResponseWrapper(localName = "hazpublicacionResponse", targetNamespace = "http://webservicepckg/", className = "webservicepckg.HazpublicacionResponse")
    @Action(input = "http://webservicepckg/WebServiceHP/hazpublicacionRequest", output = "http://webservicepckg/WebServiceHP/hazpublicacionResponse", fault = {
        @FaultAction(className = UnknownHostException_Exception.class, value = "http://webservicepckg/WebServiceHP/hazpublicacion/Fault/UnknownHostException"),
        @FaultAction(className = SQLException_Exception.class, value = "http://webservicepckg/WebServiceHP/hazpublicacion/Fault/SQLException")
    })
    public boolean hazpublicacion(
        @WebParam(name = "username", targetNamespace = "")
        String username,
        @WebParam(name = "password", targetNamespace = "")
        String password,
        @WebParam(name = "descripcion", targetNamespace = "")
        String descripcion)
        throws SQLException_Exception, UnknownHostException_Exception
    ;

    /**
     * 
     * @param password
     * @param username
     * @return
     *     returns java.lang.String
     * @throws SQLException_Exception
     * @throws UnknownHostException_Exception
     */
    @WebMethod(operationName = "InfoPersona")
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "InfoPersona", targetNamespace = "http://webservicepckg/", className = "webservicepckg.InfoPersona")
    @ResponseWrapper(localName = "InfoPersonaResponse", targetNamespace = "http://webservicepckg/", className = "webservicepckg.InfoPersonaResponse")
    @Action(input = "http://webservicepckg/WebServiceHP/InfoPersonaRequest", output = "http://webservicepckg/WebServiceHP/InfoPersonaResponse", fault = {
        @FaultAction(className = SQLException_Exception.class, value = "http://webservicepckg/WebServiceHP/InfoPersona/Fault/SQLException"),
        @FaultAction(className = UnknownHostException_Exception.class, value = "http://webservicepckg/WebServiceHP/InfoPersona/Fault/UnknownHostException")
    })
    public String infoPersona(
        @WebParam(name = "username", targetNamespace = "")
        String username,
        @WebParam(name = "password", targetNamespace = "")
        String password)
        throws SQLException_Exception, UnknownHostException_Exception
    ;

    /**
     * 
     * @param password
     * @param username
     * @return
     *     returns java.util.List<net.java.dev.jaxb.array.StringArray>
     * @throws UnknownHostException_Exception
     * @throws SQLException_Exception
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "traeMedicamentos", targetNamespace = "http://webservicepckg/", className = "webservicepckg.TraeMedicamentos")
    @ResponseWrapper(localName = "traeMedicamentosResponse", targetNamespace = "http://webservicepckg/", className = "webservicepckg.TraeMedicamentosResponse")
    @Action(input = "http://webservicepckg/WebServiceHP/traeMedicamentosRequest", output = "http://webservicepckg/WebServiceHP/traeMedicamentosResponse", fault = {
        @FaultAction(className = UnknownHostException_Exception.class, value = "http://webservicepckg/WebServiceHP/traeMedicamentos/Fault/UnknownHostException"),
        @FaultAction(className = SQLException_Exception.class, value = "http://webservicepckg/WebServiceHP/traeMedicamentos/Fault/SQLException")
    })
    public List<StringArray> traeMedicamentos(
        @WebParam(name = "username", targetNamespace = "")
        String username,
        @WebParam(name = "password", targetNamespace = "")
        String password)
        throws SQLException_Exception, UnknownHostException_Exception
    ;

    /**
     * 
     * @param password
     * @param opc
     * @param query
     * @param username
     * @return
     *     returns java.util.List<net.java.dev.jaxb.array.StringArray>
     * @throws SQLException_Exception
     * @throws UnknownHostException_Exception
     */
    @WebMethod(operationName = "Publicaciones")
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "Publicaciones", targetNamespace = "http://webservicepckg/", className = "webservicepckg.Publicaciones")
    @ResponseWrapper(localName = "PublicacionesResponse", targetNamespace = "http://webservicepckg/", className = "webservicepckg.PublicacionesResponse")
    @Action(input = "http://webservicepckg/WebServiceHP/PublicacionesRequest", output = "http://webservicepckg/WebServiceHP/PublicacionesResponse", fault = {
        @FaultAction(className = UnknownHostException_Exception.class, value = "http://webservicepckg/WebServiceHP/Publicaciones/Fault/UnknownHostException"),
        @FaultAction(className = SQLException_Exception.class, value = "http://webservicepckg/WebServiceHP/Publicaciones/Fault/SQLException")
    })
    public List<StringArray> publicaciones(
        @WebParam(name = "opc", targetNamespace = "")
        String opc,
        @WebParam(name = "username", targetNamespace = "")
        String username,
        @WebParam(name = "password", targetNamespace = "")
        String password,
        @WebParam(name = "query", targetNamespace = "")
        String query)
        throws SQLException_Exception, UnknownHostException_Exception
    ;

    /**
     * 
     * @param descripcion
     * @param password
     * @param idpublic
     * @param username
     * @return
     *     returns boolean
     * @throws UnknownHostException_Exception
     * @throws SQLException_Exception
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "hazcomentario", targetNamespace = "http://webservicepckg/", className = "webservicepckg.Hazcomentario")
    @ResponseWrapper(localName = "hazcomentarioResponse", targetNamespace = "http://webservicepckg/", className = "webservicepckg.HazcomentarioResponse")
    @Action(input = "http://webservicepckg/WebServiceHP/hazcomentarioRequest", output = "http://webservicepckg/WebServiceHP/hazcomentarioResponse", fault = {
        @FaultAction(className = SQLException_Exception.class, value = "http://webservicepckg/WebServiceHP/hazcomentario/Fault/SQLException"),
        @FaultAction(className = UnknownHostException_Exception.class, value = "http://webservicepckg/WebServiceHP/hazcomentario/Fault/UnknownHostException")
    })
    public boolean hazcomentario(
        @WebParam(name = "username", targetNamespace = "")
        String username,
        @WebParam(name = "password", targetNamespace = "")
        String password,
        @WebParam(name = "descripcion", targetNamespace = "")
        String descripcion,
        @WebParam(name = "idpublic", targetNamespace = "")
        String idpublic)
        throws SQLException_Exception, UnknownHostException_Exception
    ;

    /**
     * 
     * @param password
     * @param idp
     * @param username
     * @return
     *     returns java.util.List<net.java.dev.jaxb.array.StringArray>
     * @throws UnknownHostException_Exception
     * @throws SQLException_Exception
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "comentarios", targetNamespace = "http://webservicepckg/", className = "webservicepckg.Comentarios")
    @ResponseWrapper(localName = "comentariosResponse", targetNamespace = "http://webservicepckg/", className = "webservicepckg.ComentariosResponse")
    @Action(input = "http://webservicepckg/WebServiceHP/comentariosRequest", output = "http://webservicepckg/WebServiceHP/comentariosResponse", fault = {
        @FaultAction(className = SQLException_Exception.class, value = "http://webservicepckg/WebServiceHP/comentarios/Fault/SQLException"),
        @FaultAction(className = UnknownHostException_Exception.class, value = "http://webservicepckg/WebServiceHP/comentarios/Fault/UnknownHostException")
    })
    public List<StringArray> comentarios(
        @WebParam(name = "username", targetNamespace = "")
        String username,
        @WebParam(name = "password", targetNamespace = "")
        String password,
        @WebParam(name = "idp", targetNamespace = "")
        String idp)
        throws SQLException_Exception, UnknownHostException_Exception
    ;

    /**
     * 
     * @param arg0
     * @return
     *     returns java.lang.String
     * @throws SQLException_Exception
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "numcomentarios", targetNamespace = "http://webservicepckg/", className = "webservicepckg.Numcomentarios")
    @ResponseWrapper(localName = "numcomentariosResponse", targetNamespace = "http://webservicepckg/", className = "webservicepckg.NumcomentariosResponse")
    @Action(input = "http://webservicepckg/WebServiceHP/numcomentariosRequest", output = "http://webservicepckg/WebServiceHP/numcomentariosResponse", fault = {
        @FaultAction(className = SQLException_Exception.class, value = "http://webservicepckg/WebServiceHP/numcomentarios/Fault/SQLException")
    })
    public String numcomentarios(
        @WebParam(name = "arg0", targetNamespace = "")
        String arg0)
        throws SQLException_Exception
    ;

    /**
     * 
     * @return
     *     returns java.util.List<java.lang.String>
     * @throws SQLException_Exception
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "listamedica", targetNamespace = "http://webservicepckg/", className = "webservicepckg.Listamedica")
    @ResponseWrapper(localName = "listamedicaResponse", targetNamespace = "http://webservicepckg/", className = "webservicepckg.ListamedicaResponse")
    @Action(input = "http://webservicepckg/WebServiceHP/listamedicaRequest", output = "http://webservicepckg/WebServiceHP/listamedicaResponse", fault = {
        @FaultAction(className = SQLException_Exception.class, value = "http://webservicepckg/WebServiceHP/listamedica/Fault/SQLException")
    })
    public List<String> listamedica()
        throws SQLException_Exception
    ;

    /**
     * 
     * @param password
     * @param username
     * @return
     *     returns boolean
     * @throws UnknownHostException_Exception
     * @throws SQLException_Exception
     */
    @WebMethod(operationName = "Validausr")
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "Validausr", targetNamespace = "http://webservicepckg/", className = "webservicepckg.Validausr")
    @ResponseWrapper(localName = "ValidausrResponse", targetNamespace = "http://webservicepckg/", className = "webservicepckg.ValidausrResponse")
    @Action(input = "http://webservicepckg/WebServiceHP/ValidausrRequest", output = "http://webservicepckg/WebServiceHP/ValidausrResponse", fault = {
        @FaultAction(className = UnknownHostException_Exception.class, value = "http://webservicepckg/WebServiceHP/Validausr/Fault/UnknownHostException"),
        @FaultAction(className = SQLException_Exception.class, value = "http://webservicepckg/WebServiceHP/Validausr/Fault/SQLException")
    })
    public boolean validausr(
        @WebParam(name = "username", targetNamespace = "")
        String username,
        @WebParam(name = "password", targetNamespace = "")
        String password)
        throws SQLException_Exception, UnknownHostException_Exception
    ;

    /**
     * 
     * @param password
     * @param username
     * @return
     *     returns java.util.List<net.java.dev.jaxb.array.StringArray>
     * @throws SQLException_Exception
     * @throws UnknownHostException_Exception
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "alertasu", targetNamespace = "http://webservicepckg/", className = "webservicepckg.Alertasu")
    @ResponseWrapper(localName = "alertasuResponse", targetNamespace = "http://webservicepckg/", className = "webservicepckg.AlertasuResponse")
    @Action(input = "http://webservicepckg/WebServiceHP/alertasuRequest", output = "http://webservicepckg/WebServiceHP/alertasuResponse", fault = {
        @FaultAction(className = UnknownHostException_Exception.class, value = "http://webservicepckg/WebServiceHP/alertasu/Fault/UnknownHostException"),
        @FaultAction(className = SQLException_Exception.class, value = "http://webservicepckg/WebServiceHP/alertasu/Fault/SQLException")
    })
    public List<StringArray> alertasu(
        @WebParam(name = "username", targetNamespace = "")
        String username,
        @WebParam(name = "password", targetNamespace = "")
        String password)
        throws SQLException_Exception, UnknownHostException_Exception
    ;

    /**
     * 
     * @param password
     * @param fechaini
     * @param periodo
     * @param medicamento
     * @param fechafin
     * @param username
     * @return
     *     returns boolean
     * @throws SQLException_Exception
     * @throws UnknownHostException_Exception
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "inmedicamentos", targetNamespace = "http://webservicepckg/", className = "webservicepckg.Inmedicamentos")
    @ResponseWrapper(localName = "inmedicamentosResponse", targetNamespace = "http://webservicepckg/", className = "webservicepckg.InmedicamentosResponse")
    @Action(input = "http://webservicepckg/WebServiceHP/inmedicamentosRequest", output = "http://webservicepckg/WebServiceHP/inmedicamentosResponse", fault = {
        @FaultAction(className = UnknownHostException_Exception.class, value = "http://webservicepckg/WebServiceHP/inmedicamentos/Fault/UnknownHostException"),
        @FaultAction(className = SQLException_Exception.class, value = "http://webservicepckg/WebServiceHP/inmedicamentos/Fault/SQLException")
    })
    public boolean inmedicamentos(
        @WebParam(name = "username", targetNamespace = "")
        String username,
        @WebParam(name = "password", targetNamespace = "")
        String password,
        @WebParam(name = "medicamento", targetNamespace = "")
        String medicamento,
        @WebParam(name = "fechaini", targetNamespace = "")
        String fechaini,
        @WebParam(name = "fechafin", targetNamespace = "")
        String fechafin,
        @WebParam(name = "periodo", targetNamespace = "")
        String periodo)
        throws SQLException_Exception, UnknownHostException_Exception
    ;

    /**
     * 
     * @param password
     * @param idpublicacion
     * @param username
     * @return
     *     returns boolean
     * @throws UnknownHostException_Exception
     * @throws SQLException_Exception
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "eliminapub", targetNamespace = "http://webservicepckg/", className = "webservicepckg.Eliminapub")
    @ResponseWrapper(localName = "eliminapubResponse", targetNamespace = "http://webservicepckg/", className = "webservicepckg.EliminapubResponse")
    @Action(input = "http://webservicepckg/WebServiceHP/eliminapubRequest", output = "http://webservicepckg/WebServiceHP/eliminapubResponse", fault = {
        @FaultAction(className = UnknownHostException_Exception.class, value = "http://webservicepckg/WebServiceHP/eliminapub/Fault/UnknownHostException"),
        @FaultAction(className = SQLException_Exception.class, value = "http://webservicepckg/WebServiceHP/eliminapub/Fault/SQLException")
    })
    public boolean eliminapub(
        @WebParam(name = "username", targetNamespace = "")
        String username,
        @WebParam(name = "password", targetNamespace = "")
        String password,
        @WebParam(name = "idpublicacion", targetNamespace = "")
        String idpublicacion)
        throws SQLException_Exception, UnknownHostException_Exception
    ;

    /**
     * 
     * @param password
     * @param lon
     * @param ppm
     * @param lat
     * @param username
     * @return
     *     returns boolean
     * @throws IOException_Exception
     * @throws SQLException_Exception
     * @throws UnknownHostException_Exception
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "inemergencia", targetNamespace = "http://webservicepckg/", className = "webservicepckg.Inemergencia")
    @ResponseWrapper(localName = "inemergenciaResponse", targetNamespace = "http://webservicepckg/", className = "webservicepckg.InemergenciaResponse")
    @Action(input = "http://webservicepckg/WebServiceHP/inemergenciaRequest", output = "http://webservicepckg/WebServiceHP/inemergenciaResponse", fault = {
        @FaultAction(className = UnknownHostException_Exception.class, value = "http://webservicepckg/WebServiceHP/inemergencia/Fault/UnknownHostException"),
        @FaultAction(className = SQLException_Exception.class, value = "http://webservicepckg/WebServiceHP/inemergencia/Fault/SQLException"),
        @FaultAction(className = IOException_Exception.class, value = "http://webservicepckg/WebServiceHP/inemergencia/Fault/IOException")
    })
    public boolean inemergencia(
        @WebParam(name = "username", targetNamespace = "")
        String username,
        @WebParam(name = "password", targetNamespace = "")
        String password,
        @WebParam(name = "lat", targetNamespace = "")
        String lat,
        @WebParam(name = "lon", targetNamespace = "")
        String lon,
        @WebParam(name = "ppm", targetNamespace = "")
        String ppm)
        throws IOException_Exception, SQLException_Exception, UnknownHostException_Exception
    ;

    /**
     * 
     * @param arg0
     * @return
     *     returns java.lang.String
     * @throws SQLException_Exception
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "convierteMailaUsrid", targetNamespace = "http://webservicepckg/", className = "webservicepckg.ConvierteMailaUsrid")
    @ResponseWrapper(localName = "convierteMailaUsridResponse", targetNamespace = "http://webservicepckg/", className = "webservicepckg.ConvierteMailaUsridResponse")
    @Action(input = "http://webservicepckg/WebServiceHP/convierteMailaUsridRequest", output = "http://webservicepckg/WebServiceHP/convierteMailaUsridResponse", fault = {
        @FaultAction(className = SQLException_Exception.class, value = "http://webservicepckg/WebServiceHP/convierteMailaUsrid/Fault/SQLException")
    })
    public String convierteMailaUsrid(
        @WebParam(name = "arg0", targetNamespace = "")
        String arg0)
        throws SQLException_Exception
    ;

    /**
     * 
     * @param password
     * @param tipo
     * @param idrelusumedicamento
     * @param idmedicamento
     * @param username
     * @return
     *     returns boolean
     * @throws UnknownHostException_Exception
     * @throws SQLException_Exception
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "togglemedicamentos", targetNamespace = "http://webservicepckg/", className = "webservicepckg.Togglemedicamentos")
    @ResponseWrapper(localName = "togglemedicamentosResponse", targetNamespace = "http://webservicepckg/", className = "webservicepckg.TogglemedicamentosResponse")
    @Action(input = "http://webservicepckg/WebServiceHP/togglemedicamentosRequest", output = "http://webservicepckg/WebServiceHP/togglemedicamentosResponse", fault = {
        @FaultAction(className = UnknownHostException_Exception.class, value = "http://webservicepckg/WebServiceHP/togglemedicamentos/Fault/UnknownHostException"),
        @FaultAction(className = SQLException_Exception.class, value = "http://webservicepckg/WebServiceHP/togglemedicamentos/Fault/SQLException")
    })
    public boolean togglemedicamentos(
        @WebParam(name = "username", targetNamespace = "")
        String username,
        @WebParam(name = "password", targetNamespace = "")
        String password,
        @WebParam(name = "idrelusumedicamento", targetNamespace = "")
        String idrelusumedicamento,
        @WebParam(name = "tipo", targetNamespace = "")
        String tipo,
        @WebParam(name = "idmedicamento", targetNamespace = "")
        String idmedicamento)
        throws SQLException_Exception, UnknownHostException_Exception
    ;

}