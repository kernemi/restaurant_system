package RS.Chat;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RemoteChatService extends Remote {
    String sendMessage(String message) throws RemoteException;
}
