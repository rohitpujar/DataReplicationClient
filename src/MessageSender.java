import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MessageSender {

	DataOutputStream dos;

	// nodeid is the id of the client/server to whom the message is to be sent
	public void sendMessage(String message, int nodeId) {

		try {
			dos = new DataOutputStream(SocketConnections.getSocketConnections().get(nodeId).getOutputStream());
			dos.writeUTF(message);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void sendToAllServers(String message) {

		HashMap<Integer, Socket> socketMap = (HashMap<Integer, Socket>) SocketConnections.getSocketConnections();

		try {
			Iterator<Map.Entry<Integer, Socket>> it = socketMap.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry<Integer, Socket> pairs = it.next();
				dos = new DataOutputStream(pairs.getValue().getOutputStream());
				dos.writeUTF(message + "," + SocketConnections.getMyNodeId());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
