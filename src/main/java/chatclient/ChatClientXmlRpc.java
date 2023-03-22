package chatclient;
import java.util.*;
import java.io.IOException;
import java.net.MalformedURLException;

import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;

import chat.ChatUser;

public class ChatClientXmlRpc implements ChatUser {
    private String nickname;
    private XmlRpcClient xmlRpcClient;
    private String serverUrl;

    public ChatClientXmlRpc(String nickname, String serverUrl) throws MalformedURLException {
        this.nickname = nickname;
        this.serverUrl=serverUrl;
        this.xmlRpcClient = new XmlRpcClient(this.ser);
    }

    public void subscribe() throws XmlRpcException, IOException {
        xmlRpcClient.execute(" ChatRoom.join", new Object[]{nickname, this});
    }

    public void unsubscribe() throws XmlRpcException, IOException {
        xmlRpcClient.execute("ChatServer.leave", new Object[]{nickname});
    }

    public void postMessage(String message) throws XmlRpcException, IOException {
        xmlRpcClient.execute("ChatServer.sendMessage", new Object[]{nickname, message});
    }

    public void displayMessage(String sender, String message) {
        System.out.println(sender + ": " + message);
    }

    public static void main(String[] args) throws Exception {
        String serverUrl = "http://localhost:8080";
        String nickname = "Alice";

        ChatClientXmlRpc client = new ChatClientXmlRpc(nickname, serverUrl);
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
   
