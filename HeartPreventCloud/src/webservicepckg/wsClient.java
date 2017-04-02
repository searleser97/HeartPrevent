/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webservicepckg;

import java.net.MalformedURLException;

/**
 *
 * @author Silvester
 */
public class wsClient {

    
    public static void main(String args[]) throws MalformedURLException, SQLException_Exception, UnknownHostException_Exception {
        hh();
    }
    public static void hh() throws SQLException_Exception, UnknownHostException_Exception {
        WebServiceHP_Service service = new WebServiceHP_Service();
        WebServiceHP server = service.getWebServiceHPPort();
        boolean valida = server.validausr("searlese@hotmail.com", "contra");
        System.out.println("server: " + valida);
    }
}
