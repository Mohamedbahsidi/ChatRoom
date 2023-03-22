package chatserver;

public class ChatServerImpl implements ChatRoom {
    private Map<String, ChatClient> clients = new ConcurrentHashMap<>();

    public void subscribe(String nickname, ChatClient client) {
        clients.put(nickname, client);
        broadcast(nickname, nickname + " joined the chat");
    }

    public void unsubscribe(String nickname) {
        clients.remove(nickname);
        broadcast(nickname, nickname + " left the chat");
    }

    public void  postMessage(String nickname, String message) {
        broadcast(nickname, nickname + ": " + message);
    }
    private void broadcast(String nickname, String message) {
        for (Map.Entry<String, ChatClient> entry : clients.entrySet()) {
            String clientNickname = entry.getKey();
            ChatClient client = entry.getValue();
            try {
                client.displayMessage(nickname, message);
            } catch (XmlRpcException | IOException e) {
                System.err.println("Error broadcasting message to " + clientNickname);
            }
        }    

}
