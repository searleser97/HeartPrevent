/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws;

import java.io.StringReader;
import java.io.StringWriter;
import java.net.UnknownHostException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.faces.bean.ApplicationScoped;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonWriter;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

/**
 *
 * @author Silvester
 */
@ApplicationScoped
@ServerEndpoint(value = "/echo2/{chatroom}", configurator = ChatroomServerConfigurator.class)
public class ChatroomServerEndpoint {

    static Map<String, Set<Session>> chatrooms = (Map<String, Set<Session>>) Collections.synchronizedMap(new HashMap<String, Set<Session>>());
    private String usrid = "";
    private rules.Seguridad cifrado = new rules.Seguridad();

    public Set<Session> getChatroom(String chatroomName) {
        Set<Session> chatroom = chatrooms.get(chatroomName);
        if (chatroom == null) {
            chatroom = Collections.synchronizedSet(new HashSet<Session>());
            chatrooms.put(chatroomName, chatroom);
        }
        return chatroom;
    }

    @OnOpen
    public void handleOpen(EndpointConfig config, Session userSession, @PathParam("chatroom") String chatroom) throws UnknownHostException, SQLException {
        usrid = (String) config.getUserProperties().get("UsrIdchat");
        db.Condb con = new db.Condb();
        con.conectar();
        ResultSet validaRelusu = con.consulta("call validaRelaciones('3','"+usrid+"','"+cifrado.stremplaza(chatroom)+"')");
        if (validaRelusu.next()) {
            userSession.getUserProperties().put("UsrIdchat", config.getUserProperties().get("UsrIdchat"));
            userSession.getUserProperties().put("chatroom", chatroom);
            Set<Session> chatroomUsers = getChatroom(chatroom);
            chatroomUsers.add(userSession);
        } else {
            con.cierraConexion();
            handleClose(userSession);
            return;
        }
        con.cierraConexion();

    }

    @OnClose
    public void handleClose(Session userSession) {
        String chatroom = (String) userSession.getUserProperties().get("chatroom");
        Set<Session> chatroomUsers = getChatroom(chatroom);
        chatroomUsers.remove(userSession);
    }

    @OnError
    public void handleError(Throwable t) {
        System.out.println(t + "::");
    }

    @OnMessage
    public void handleMessage(String data, Session userSession) throws UnknownHostException, SQLException {
        String username = (String) userSession.getUserProperties().get("UsrIdchat");
        String chatroom = (String) userSession.getUserProperties().get("chatroom");
        int firstmsjquotes = data.indexOf(":", data.indexOf(":") + 1) + 2;
        int lastmsjquotes = data.lastIndexOf("}") - 1;
        String JsonLimpio = new StringBuffer(data)
                .replace(firstmsjquotes, lastmsjquotes,
                        cifrado.stremplaza(data.substring(firstmsjquotes, lastmsjquotes)))
                .toString();
        String msjoutput = "";
        JsonObject JO = jsonFromString(JsonLimpio);

        if (JO.getString("tipomsj").equals("1")) {
            msjoutput = JO.getString("mensaje");
            db.Condb con = new db.Condb();
            con.conectar();
            con.consulta("call mensajes('2','" + chatroom + "','" + username + "','" + msjoutput + "')");
            con.cierraConexion();
            msjoutput = cifrado.urlToHtmlTag(msjoutput);
        } else {
            msjoutput = cifrado.stdesremplaza(JO.getString("imgtag"));
        }

        Set<Session> chatroomUsers = getChatroom(chatroom);
        final String finaloutput = msjoutput;
        chatroomUsers.stream().forEach(x -> {

            try {
                x.getBasicRemote().sendText(buildJsonData(username, finaloutput));
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        });
    }

    private String buildJsonData(String username, String message) throws UnknownHostException {

        JsonObject jsonObject = Json.createObjectBuilder().add("message", username + "| " + message).build();
        StringWriter stringWriter = new StringWriter();
        try (JsonWriter jsonWriter = Json.createWriter(stringWriter)) {
            jsonWriter.write(jsonObject);
        }
        return stringWriter.toString();
    }

    private static JsonObject jsonFromString(String jsonObjectStr) {

        JsonReader jsonReader = Json.createReader(new StringReader(jsonObjectStr));
        JsonObject object = jsonReader.readObject();
        jsonReader.close();

        return object;
    }

}
