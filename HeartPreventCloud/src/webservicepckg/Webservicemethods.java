/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webservicepckg;

/**
 *
 * @author Silvester
 */
public class Webservicemethods {

    WebServiceHP_Service service = new WebServiceHP_Service();
    WebServiceHP methods = service.getWebServiceHPPort();

    public void uploadImg(byte[] imgbytes, String username, String password) throws FileNotFoundException_Exception, IOException_Exception, SQLException_Exception {
        methods.uploadImg2(imgbytes, username, password);
    }
}
