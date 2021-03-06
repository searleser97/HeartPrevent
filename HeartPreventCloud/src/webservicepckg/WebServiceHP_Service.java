
package webservicepckg;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceFeature;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 * 
 */
@WebServiceClient(name = "WebServiceHP", targetNamespace = "http://webservicepckg/", wsdlLocation = "http://192.168.0.8:46750/HPNet/WebServiceHP?wsdl")
public class WebServiceHP_Service
    extends Service
{

    private final static URL WEBSERVICEHP_WSDL_LOCATION;
    private final static WebServiceException WEBSERVICEHP_EXCEPTION;
    private final static QName WEBSERVICEHP_QNAME = new QName("http://webservicepckg/", "WebServiceHP");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("http://192.168.0.8:46750/HPNet/WebServiceHP?wsdl");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        WEBSERVICEHP_WSDL_LOCATION = url;
        WEBSERVICEHP_EXCEPTION = e;
    }

    public WebServiceHP_Service() {
        super(__getWsdlLocation(), WEBSERVICEHP_QNAME);
    }

    public WebServiceHP_Service(WebServiceFeature... features) {
        super(__getWsdlLocation(), WEBSERVICEHP_QNAME, features);
    }

    public WebServiceHP_Service(URL wsdlLocation) {
        super(wsdlLocation, WEBSERVICEHP_QNAME);
    }

    public WebServiceHP_Service(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, WEBSERVICEHP_QNAME, features);
    }

    public WebServiceHP_Service(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public WebServiceHP_Service(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns WebServiceHP
     */
    @WebEndpoint(name = "WebServiceHPPort")
    public WebServiceHP getWebServiceHPPort() {
        return super.getPort(new QName("http://webservicepckg/", "WebServiceHPPort"), WebServiceHP.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns WebServiceHP
     */
    @WebEndpoint(name = "WebServiceHPPort")
    public WebServiceHP getWebServiceHPPort(WebServiceFeature... features) {
        return super.getPort(new QName("http://webservicepckg/", "WebServiceHPPort"), WebServiceHP.class, features);
    }

    private static URL __getWsdlLocation() {
        if (WEBSERVICEHP_EXCEPTION!= null) {
            throw WEBSERVICEHP_EXCEPTION;
        }
        return WEBSERVICEHP_WSDL_LOCATION;
    }

}
