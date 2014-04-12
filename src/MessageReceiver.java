import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class MessageReceiver extends Thread {

	DataInputStream dis;
	Socket socket;
	int nodeId;

	public MessageReceiver(int id, Socket socket) {

		this.nodeId = id;
		this.socket = socket;

	}

	@Override
	public void run() {

		try {
			dis = new DataInputStream(socket.getInputStream());

			while (true) {
				String message = dis.readUTF().toString();
				System.out.println("Client says : " + message);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
