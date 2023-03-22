package chatserver;

import org.apache.xmlrpc.server.PropertyHandlerMapping;
import org.apache.xmlrpc.server.XmlRpcServer;
import org.apache.xmlrpc.webserver.WebServer;

import chat.ChatRoom;

public class ChatServerXmlRpc {
    public static void main(String[] args) throws Exception {
        int port = 8080;
        
        WebServer  webServer=new WebServer(port);


        ChatRoom chatServer = (ChatRoom) new ChatServerImpl();
        XmlRpcServer xmlRpcServer = webServer.getXmlRpcServer();
        PropertyHandlerMapping phm = new PropertyHandlerMapping();
        phm.addHandler(  ChatRoom.class.getName(), chatServer.getClass());
        xmlRpcServer.setHandlerMapping(phm);

        webServer.start();
        System.out.println("Chat server listening on port " + port);
    }
}
