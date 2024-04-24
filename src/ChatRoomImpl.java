import Interface.ChatRoom;
import Interface.ChatUser;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.ConcurrentHashMap;

public class ChatRoomImpl extends UnicastRemoteObject implements ChatRoom {

    private ConcurrentHashMap<String, ChatUser> users = new ConcurrentHashMap<>();

    public ChatRoomImpl() throws RemoteException {
        super();
    }

    @Override
    public void subscribe(ChatUser user, String pseudo) throws RemoteException {
        users.put(pseudo, user);
        postMessage(pseudo, pseudo + " a rejoint le chat");
    }

    @Override
    public void unsubscribe(String pseudo) throws RemoteException {
        users.remove(pseudo);
        postMessage(pseudo, pseudo + "a quitté le chat.");
    }

    @Override
    public void postMessage(String pseudo, String message) throws RemoteException {
        String fullMessage = pseudo + ">>>" + message;
        for (ChatUser user : users.values()) {
            user.displayMessage(fullMessage);
        }
    }
}
