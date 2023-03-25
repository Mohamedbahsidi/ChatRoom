package chatclient;
import java.util.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;

import org.apache.*;
import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;

import chat.ChatUser;

public class ChatClientXmlRpc implements ChatUser {
    private String nickname;
    private XmlRpcClient xmlRpcClient;
    private String serverUrl;

    public ChatClientXmlRpc(String nickname, String serverUrl) throws MalformedURLException {
        this.nickname = nickname;
        this.serverUrl=serverUrl;
     
        this.xmlRpcClient=new XmlRpcClient();
       
    }

    public void subscribe() throws XmlRpcException, IOException {
        xmlRpcClient.execute(" ChatRoom.subscribe", new Object[]{nickname, this});
    }

    public void unsubscribe() throws XmlRpcException, IOException {
        xmlRpcClient.execute("ChatServer.unsubscribe", new Object[]{nickname});
    }

    public void postMessage(String message) throws XmlRpcException, IOException {
        xmlRpcClient.execute("ChatServer.postMessage", new Object[]{nickname, message});
    }

    public void displayMessage(String sender, String message) {
        System.out.println(sender + ": " + message);
    }

    public static void main(String[] args) throws Exception {
        String serverUrl = "http://localhost:8080";
        String nickname = "Alice";

        ChatClientXmlRpc client = new ChatClientXmlRpc(nickname, serverUrl);
      //  client.setEndpoint("http://www.example.com/xmlrpc");
     // create a configuration instance with the requested URL
        XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
config.setServerURL(new URL("http://localhost/RPC2"));

        // create the client and configure it with instantiated configuration
        XmlRpcClient server = new XmlRpcClient();
        server.setConfig(config);
        
        client.subscribe();

        Scanner scanner = new Scanner(System.in);
        while (true) {
            String message = scanner.nextLine();
            if (message.equals("/quit")) {
                client.unsubscribe();
                break;
            }
            client.postMessage(message);
        }
   


	public void displayMessage(String message) throws RemoteException {
		// TODO Auto-generated method stub
		
	}}