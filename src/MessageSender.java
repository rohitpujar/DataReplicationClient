import java.io.DataOutputStream;
import java.io.IOException;

public class MessageSender {

	DataOutputStream dos;

	public void sendMessage(int nodeId, String message) {

		try {
			dos = new DataOutputStream(SocketConnections.getSocketConnections().get(nodeId).getOutputStream());
			dos.writeUTF(message);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
