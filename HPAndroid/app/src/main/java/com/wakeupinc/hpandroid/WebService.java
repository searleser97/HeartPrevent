package com.wakeupinc.hpandroid;

import android.os.StrictMode;
import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.MarshalBase64;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

public class WebService {
    //Namespace of the Webservice - can be found in WSDL
    private static String NAMESPACE = "http://webservicepckg/";
    //Webservice URL - WSDL File location
    private static String URL = "http://"+Config.serverUrl+"/HPNet/WebServiceHP?WSDL";
    //SOAP Action URI again Namespace + Web method name
    private static String SOAP_ACTION = "http://webservicepckg/";

    public static boolean invokeLoginWS(String userName, String passWord,String gcmid, String webMethName) throws IOException, XmlPullParserException {
        boolean loginStatus = false;
        // Create request
        SoapObject request = new SoapObject(NAMESPACE, webMethName);
        // Property which holds input parameters
        PropertyInfo unamePI = new PropertyInfo();
        PropertyInfo passPI = new PropertyInfo();
        PropertyInfo gcmPI=new PropertyInfo();
        // Set Username
        unamePI.setName("username");
        // Set Value
        unamePI.setValue(userName);
        // Set dataType
        unamePI.setType(String.class);
        // Add the property to request object
        request.addProperty(unamePI);
        //Set Password
        passPI.setName("password");
        //Set dataType
        passPI.setValue(passWord);
        //Set dataType
        passPI.setType(String.class);
        //Add the property to request object
        request.addProperty(passPI);

        gcmPI.setName("gcmid");
        gcmPI.setValue(gcmid);
        gcmPI.setType(String.class);
        request.addProperty(gcmPI);
        // Create envelope
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        // Set output SOAP object
        envelope.setOutputSoapObject(request);
        // Create HTTP call object
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);


        // Invoke web service
        androidHttpTransport.call(SOAP_ACTION + webMethName, envelope);
        // Get the response
        SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
        // Assign it to  boolean variable variable
        loginStatus = Boolean.parseBoolean(response.toString());


        //Return boolean to calling object
        return loginStatus;
    }

    public static String subeImg(byte[] imagen, String webMethName, String mail, String pass) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);
        boolean loginStatus = false;
        // Create request
        SoapObject request = new SoapObject(NAMESPACE, webMethName);
        request.addProperty("ImgBytes", imagen);
        request.addProperty("UserId", mail);
        request.addProperty("UserPass", pass);
        // Create envelope
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        new MarshalBase64().register(envelope); // serialization
        envelope.setOutputSoapObject(request);
        // Set dataType
        //unamePI.setType(MarshalBase64.BYTE_ARRAY_CLASS);
        // Add the property to request object


        // Set output SOAP object


        //envelope.env = "http://192.168.0.4:8080/webservicepckg/";
        // Create HTTP call object
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
        String regresa = "123$";
        try {
            // Invoke web service
            androidHttpTransport.call(SOAP_ACTION + webMethName, envelope);

            // Get the response
//            SoapObject response = (SoapObject)envelope.getResponse();
//            response.toString();
            SoapObject result = (SoapObject) envelope.bodyIn;
            regresa = result.toString();

        } catch (Exception e) {
            //Assign Error Status true in static variable 'errored'
            e.printStackTrace();
        }
        return regresa;
    }

    public static String datospersona(String userName, String passWord, String webMethName) throws IOException, XmlPullParserException {
        String datosper = "";
        // Create request
        SoapObject request = new SoapObject(NAMESPACE, webMethName);
        // Property which holds input parameters
        PropertyInfo unamePI = new PropertyInfo();
        PropertyInfo passPI = new PropertyInfo();
        // Set Username
        unamePI.setName("username");
        // Set Value
        unamePI.setValue(userName);
        // Set dataType
        unamePI.setType(String.class);
        // Add the property to request object
        request.addProperty(unamePI);
        //Set Password
        passPI.setName("password");
        //Set dataType
        passPI.setValue(passWord);
        //Set dataType
        passPI.setType(String.class);
        //Add the property to request object
        request.addProperty(passPI);
        // Create envelope
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        // Set output SOAP object
        envelope.setOutputSoapObject(request);
        // Create HTTP call object
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

        // Invoke web service
        androidHttpTransport.call(SOAP_ACTION + webMethName, envelope);
        // Get the response
        SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
        datosper = response.toString();

        //Return boolean to calling object
        return datosper;
    }

    public static String[][] publicaciones(String opc, String userName, String passWord, String query, String webMethName) throws IOException, XmlPullParserException {
        String[][] arraypublicaciones = new String[6][50];
        SoapObject request = new SoapObject(NAMESPACE, webMethName);
        PropertyInfo unamePI = new PropertyInfo();
        PropertyInfo passPI = new PropertyInfo();
        PropertyInfo opcPI = new PropertyInfo();
        PropertyInfo queryPI = new PropertyInfo();

        unamePI.setName("username");
        unamePI.setValue(userName);
        unamePI.setType(String.class);
        request.addProperty(unamePI);

        passPI.setName("password");
        passPI.setValue(passWord);
        passPI.setType(String.class);
        request.addProperty(passPI);

        opcPI.setName("opc");
        opcPI.setValue(opc);
        opcPI.setType(String.class);
        request.addProperty(opcPI);

        queryPI.setName("query");
        queryPI.setValue(query);
        queryPI.setType(String.class);
        request.addProperty(queryPI);

        // Create envelope
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        // Set output SOAP object
        envelope.setOutputSoapObject(request);
        // Create HTTP call object
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

        // Invoke web service
        androidHttpTransport.call(SOAP_ACTION + webMethName, envelope);
        // Get the response
        SoapObject Table = (SoapObject) envelope.bodyIn;

        /////////////////////////////////////////////////////

        String[][] output = null;
        if (Table != null) {
            SoapObject row = (SoapObject) Table.getProperty(0);

            if (row != null) {
                int rCount = Table.getPropertyCount();
                int cCount = ((SoapObject) Table.getProperty(0)).getPropertyCount();
                output = new String[rCount][cCount];
                for (int i = 0; i < rCount; i++) {
                    for (int j = 0; j < cCount; j++)
                        output[i][j] = ((SoapObject) Table.getProperty(i)).getProperty(j).toString();
                }

            }
        }
        return output;////////////////////////////////////////////////////
        //return arraypublicaciones;
    }

    public static String[][] comentarios(String idp, String userName, String passWord, String usrId2, String webMethName) throws IOException, XmlPullParserException {

        SoapObject request = new SoapObject(NAMESPACE, webMethName);
        PropertyInfo unamePI = new PropertyInfo();
        PropertyInfo passPI = new PropertyInfo();
        PropertyInfo opcPI = new PropertyInfo();

        unamePI.setName("username");
        unamePI.setValue(userName);
        unamePI.setType(String.class);
        request.addProperty(unamePI);

        passPI.setName("password");
        passPI.setValue(passWord);
        passPI.setType(String.class);
        request.addProperty(passPI);

        opcPI.setName("idp");
        opcPI.setValue(idp);
        opcPI.setType(String.class);
        request.addProperty(opcPI);

        // Create envelope
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        // Set output SOAP object
        envelope.setOutputSoapObject(request);
        // Create HTTP call object
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

        // Invoke web service
        androidHttpTransport.call(SOAP_ACTION + webMethName, envelope);
        // Get the response

        SoapObject Table = (SoapObject) envelope.bodyIn;

        String[][] output = null;
        try {
            if (Table != null) {
                SoapObject row = (SoapObject) Table.getProperty(0);

                if (row != null) {
                    int rCount = Table.getPropertyCount();
                    int cCount = ((SoapObject) Table.getProperty(0)).getPropertyCount();
                    output = new String[rCount][cCount];
                    for (int i = 0; i < rCount; i++) {
                        for (int j = 0; j < cCount; j++)
                            output[i][j] = ((SoapObject) Table.getProperty(i)).getProperty(j).toString();
                    }

                }
            }
        }
        catch(java.lang.ArrayIndexOutOfBoundsException e){
            return null;
        }
        return output;////////////////////////////////////////////////////

    }

    public static boolean hazpublicacion(String userName, String passWord,String descripcion, String webMethName) throws IOException, XmlPullParserException {
        boolean loginStatus = false;
        // Create request
        SoapObject request = new SoapObject(NAMESPACE, webMethName);
        // Property which holds input parameters
        PropertyInfo unamePI = new PropertyInfo();
        PropertyInfo passPI = new PropertyInfo();
        PropertyInfo descripcionPI=new PropertyInfo();
        // Set Username
        unamePI.setName("username");
        // Set Value
        unamePI.setValue(userName);
        // Set dataType
        unamePI.setType(String.class);
        // Add the property to request object
        request.addProperty(unamePI);
        //Set Password
        passPI.setName("password");
        //Set dataType
        passPI.setValue(passWord);
        //Set dataType
        passPI.setType(String.class);
        //Add the property to request object
        request.addProperty(passPI);

        descripcionPI.setName("descripcion");
        descripcionPI.setValue(descripcion);
        descripcionPI.setType(String.class);
        request.addProperty(descripcionPI);
        // Create envelope
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        // Set output SOAP object
        envelope.setOutputSoapObject(request);
        // Create HTTP call object
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);


        // Invoke web service
        androidHttpTransport.call(SOAP_ACTION + webMethName, envelope);
        // Get the response
        SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
        // Assign it to  boolean variable variable
        loginStatus = Boolean.parseBoolean(response.toString());


        //Return boolean to calling object
        return loginStatus;
    }

    public static boolean hazcomentario(String userName, String passWord,String descripcion,String idp, String webMethName) throws IOException, XmlPullParserException {
        boolean loginStatus = false;
        // Create request
        SoapObject request = new SoapObject(NAMESPACE, webMethName);
        // Property which holds input parameters
        PropertyInfo unamePI = new PropertyInfo();
        PropertyInfo passPI = new PropertyInfo();
        PropertyInfo descripcionPI=new PropertyInfo();
        PropertyInfo idpIP=new PropertyInfo();
        unamePI.setName("username");
        unamePI.setValue(userName);
        unamePI.setType(String.class);
        request.addProperty(unamePI);
        passPI.setName("password");
        passPI.setValue(passWord);
        passPI.setType(String.class);
        request.addProperty(passPI);
        descripcionPI.setName("descripcion");
        descripcionPI.setValue(descripcion);
        descripcionPI.setType(String.class);
        request.addProperty(descripcionPI);
        idpIP.setName("idpublic");
        idpIP.setValue(idp);
        idpIP.setType(String.class);
        request.addProperty(idpIP);
        // Create envelope
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        // Set output SOAP object
        envelope.setOutputSoapObject(request);
        // Create HTTP call object
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);


        // Invoke web service
        androidHttpTransport.call(SOAP_ACTION + webMethName, envelope);
        // Get the response
        SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
        // Assign it to  boolean variable variable
        loginStatus = Boolean.parseBoolean(response.toString());


        //Return boolean to calling object
        return loginStatus;
    }

    public static String[][] contactos(String userName, String passWord, String webMethName) throws IOException, XmlPullParserException {

        SoapObject request = new SoapObject(NAMESPACE, webMethName);
        PropertyInfo unamePI = new PropertyInfo();
        PropertyInfo passPI = new PropertyInfo();
        PropertyInfo opcPI = new PropertyInfo();

        unamePI.setName("username");
        unamePI.setValue(userName);
        unamePI.setType(String.class);
        request.addProperty(unamePI);

        passPI.setName("password");
        passPI.setValue(passWord);
        passPI.setType(String.class);
        request.addProperty(passPI);

        // Create envelope
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        // Set output SOAP object
        envelope.setOutputSoapObject(request);
        // Create HTTP call object
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

        // Invoke web service
        androidHttpTransport.call(SOAP_ACTION + webMethName, envelope);
        // Get the response

        SoapObject Table = (SoapObject) envelope.bodyIn;

        String[][] output = null;
        try {
            if (Table != null) {
                SoapObject row = (SoapObject) Table.getProperty(0);

                if (row != null) {
                    int rCount = Table.getPropertyCount();
                    int cCount = ((SoapObject) Table.getProperty(0)).getPropertyCount();
                    output = new String[rCount][cCount];
                    for (int i = 0; i < rCount; i++) {
                        for (int j = 0; j < cCount; j++)
                            output[i][j] = ((SoapObject) Table.getProperty(i)).getProperty(j).toString();
                    }

                }
            }
        }
        catch(java.lang.ArrayIndexOutOfBoundsException e){
            return null;
        }
        return output;////////////////////////////////////////////////////

    }

    public static String[][] medicamentos(String userName, String passWord, String webMethName) throws IOException, XmlPullParserException {

        SoapObject request = new SoapObject(NAMESPACE, webMethName);
        PropertyInfo unamePI = new PropertyInfo();
        PropertyInfo passPI = new PropertyInfo();

        unamePI.setName("username");
        unamePI.setValue(userName);
        unamePI.setType(String.class);
        request.addProperty(unamePI);

        passPI.setName("password");
        passPI.setValue(passWord);
        passPI.setType(String.class);
        request.addProperty(passPI);

        // Create envelope
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        // Set output SOAP object
        envelope.setOutputSoapObject(request);
        // Create HTTP call object
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

        // Invoke web service
        androidHttpTransport.call(SOAP_ACTION + webMethName, envelope);
        // Get the response

        SoapObject Table = (SoapObject) envelope.bodyIn;

        String[][] output = null;
        try {
            if (Table != null) {
                SoapObject row = (SoapObject) Table.getProperty(0);

                if (row != null) {
                    int rCount = Table.getPropertyCount();
                    int cCount = ((SoapObject) Table.getProperty(0)).getPropertyCount();
                    output = new String[rCount][cCount];
                    for (int i = 0; i < rCount; i++) {
                        for (int j = 0; j < cCount; j++)
                            output[i][j] = ((SoapObject) Table.getProperty(i)).getProperty(j).toString();
                    }

                }
            }
        }
        catch(java.lang.ArrayIndexOutOfBoundsException e){
            return null;
        }
        return output;////////////////////////////////////////////////////

    }

    public static boolean togglemedicina(String userName, String passWord,String idrelusumedicamento,String tipo,String idmedicamento, String webMethName) throws IOException, XmlPullParserException {

        boolean loginStatus = false;
        // Create request
        SoapObject request = new SoapObject(NAMESPACE, webMethName);
        // Property which holds input parameters
        PropertyInfo unamePI = new PropertyInfo();
        PropertyInfo passPI = new PropertyInfo();
        PropertyInfo idrelusumedicaPI=new PropertyInfo();
        PropertyInfo tipoPI=new PropertyInfo();
        PropertyInfo idmedicamentoPI=new PropertyInfo();
        // Set Username
        unamePI.setName("username");
        // Set Value
        unamePI.setValue(userName);
        // Set dataType
        unamePI.setType(String.class);
        // Add the property to request object
        request.addProperty(unamePI);
        //Set Password
        passPI.setName("password");
        //Set dataType
        passPI.setValue(passWord);
        //Set dataType
        passPI.setType(String.class);
        //Add the property to request object
        request.addProperty(passPI);
        idrelusumedicaPI.setName("idrelusumedicamento");
        idrelusumedicaPI.setValue(idrelusumedicamento);
        idrelusumedicaPI.setType(String.class);
        request.addProperty(idrelusumedicaPI);
        tipoPI.setName("tipo");
        tipoPI.setValue(tipo);
        tipoPI.setType(String.class);
        request.addProperty(tipoPI);
        idmedicamentoPI.setName("idmedicamento");
        idmedicamentoPI.setValue(idmedicamento);
        idmedicamentoPI.setType(String.class);
        request.addProperty(idmedicamentoPI);
        // Create envelope
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        // Set output SOAP object
        envelope.setOutputSoapObject(request);
        // Create HTTP call object
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);


        // Invoke web service
        androidHttpTransport.call(SOAP_ACTION + webMethName, envelope);
        // Get the response
        SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
        // Assign it to  boolean variable variable
        loginStatus = Boolean.parseBoolean(response.toString());


        //Return boolean to calling object
        return loginStatus;

    }

    public static String[][] bajaalertas(String userName, String passWord, String webMethName) throws IOException, XmlPullParserException {

        SoapObject request = new SoapObject(NAMESPACE, webMethName);
        PropertyInfo unamePI = new PropertyInfo();
        PropertyInfo passPI = new PropertyInfo();

        unamePI.setName("username");
        unamePI.setValue(userName);
        unamePI.setType(String.class);
        request.addProperty(unamePI);

        passPI.setName("password");
        passPI.setValue(passWord);
        passPI.setType(String.class);
        request.addProperty(passPI);

        // Create envelope
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        // Set output SOAP object
        envelope.setOutputSoapObject(request);
        // Create HTTP call object
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

        // Invoke web service
        androidHttpTransport.call(SOAP_ACTION + webMethName, envelope);
        // Get the response

        SoapObject Table = (SoapObject) envelope.bodyIn;

        String[][] output = null;
        try {
            if (Table != null) {
                SoapObject row = (SoapObject) Table.getProperty(0);

                if (row != null) {
                    int rCount = Table.getPropertyCount();
                    int cCount = ((SoapObject) Table.getProperty(0)).getPropertyCount();
                    output = new String[rCount][cCount];
                    for (int i = 0; i < rCount; i++) {
                        for (int j = 0; j < cCount; j++)
                            output[i][j] = ((SoapObject) Table.getProperty(i)).getProperty(j).toString();
                    }

                }
            }
        }
        catch(java.lang.ArrayIndexOutOfBoundsException e){
            return null;
        }
        return output;////////////////////////////////////////////////////

    }

    public static boolean altamedicamentos(String userName, String passWord,String medicamento,String fechini,String fechfin,String periodo, String webMethName) throws IOException, XmlPullParserException {

        boolean loginStatus = false;
        // Create request
        SoapObject request = new SoapObject(NAMESPACE, webMethName);
        // Property which holds input parameters
        PropertyInfo unamePI = new PropertyInfo();
        PropertyInfo passPI = new PropertyInfo();
        PropertyInfo fechaini=new PropertyInfo();
        PropertyInfo fechafin=new PropertyInfo();
        PropertyInfo medicamentoPI=new PropertyInfo();
        PropertyInfo periodoPI=new PropertyInfo();
        // Set Username
        unamePI.setName("username");
        // Set Value
        unamePI.setValue(userName);
        // Set dataType
        unamePI.setType(String.class);
        // Add the property to request object
        request.addProperty(unamePI);
        //Set Password
        passPI.setName("password");
        //Set dataType
        passPI.setValue(passWord);
        //Set dataType
        passPI.setType(String.class);
        //Add the property to request object
        request.addProperty(passPI);
        fechaini.setName("fechaini");
        fechaini.setValue(fechini);
        fechaini.setType(String.class);
        request.addProperty(fechaini);

        fechafin.setName("fechafin");
        fechafin.setValue(fechfin);
        fechafin.setType(String.class);
        request.addProperty(fechafin);

        periodoPI.setName("periodo");
        periodoPI.setValue(periodo);
        periodoPI.setType(String.class);
        request.addProperty(periodoPI);


        medicamentoPI.setName("medicamento");
        medicamentoPI.setValue(medicamento);
        medicamentoPI.setType(String.class);
        request.addProperty(medicamentoPI);
        // Create envelope
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        // Set output SOAP object
        envelope.setOutputSoapObject(request);
        // Create HTTP call object
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);


        // Invoke web service
        androidHttpTransport.call(SOAP_ACTION + webMethName, envelope);
        // Get the response
        SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
        // Assign it to  boolean variable variable
        loginStatus = Boolean.parseBoolean(response.toString());


        //Return boolean to calling object
        return loginStatus;

    }
    public static String[] bajalistamedica() throws IOException, XmlPullParserException {
        boolean loginStatus = false;
        // Create request
        SoapObject request = new SoapObject(NAMESPACE, "listamedica");
        // Property which holds input parameters

        // Create envelope
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        // Set output SOAP object
        envelope.setOutputSoapObject(request);
        // Create HTTP call object
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);


        // Invoke web service
        androidHttpTransport.call(SOAP_ACTION + "listamedica", envelope);

        SoapObject Table = (SoapObject)envelope.bodyIn;

        String []output=null;
        if(Table!=null)
        {
            int count= Table.getPropertyCount();
            output = new String[count];
            for(int i=0;i<count;i++)
            {
                output[i]=Table.getProperty(i).toString();
            }
        }
        return output;
    }


    public static boolean eliminapub(String userName, String passWord,String idpub, String webMethName) throws IOException, XmlPullParserException {

        boolean loginStatus = false;
        // Create request
        SoapObject request = new SoapObject(NAMESPACE, webMethName);
        // Property which holds input parameters
        PropertyInfo unamePI = new PropertyInfo();
        PropertyInfo passPI = new PropertyInfo();
        PropertyInfo idpPI=new PropertyInfo();
        // Set Username
        unamePI.setName("username");
        // Set Value
        unamePI.setValue(userName);
        // Set dataType
        unamePI.setType(String.class);
        // Add the property to request object
        request.addProperty(unamePI);
        //Set Password
        passPI.setName("password");
        //Set dataType
        passPI.setValue(passWord);
        //Set dataType
        passPI.setType(String.class);
        request.addProperty(passPI);

        idpPI.setName("idpublicacion");
        idpPI.setValue(idpub);
        idpPI.setType(String.class);
        request.addProperty(idpPI);

        // Create envelope
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        // Set output SOAP object
        envelope.setOutputSoapObject(request);
        // Create HTTP call object
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);


        // Invoke web service
        androidHttpTransport.call(SOAP_ACTION + webMethName, envelope);
        // Get the response
        SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
        // Assign it to  boolean variable variable
        loginStatus = Boolean.parseBoolean(response.toString());


        //Return boolean to calling object
        return loginStatus;

    }
}

