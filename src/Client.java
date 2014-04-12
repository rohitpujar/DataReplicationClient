import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;

public class Client {

	MessageReceiver msgReceive;
	int nodeId;

	public void connectToServers(List<Node> nodeList, int id) {
		this.nodeId = id;
		// ++id;
		System.out.println("Connect requests to servers will be sent in 3 seconds...");
		try {
			Thread.sleep(3000);
			for (int i = 0; i < nodeList.size(); i++) {
				Socket clientSocket = new Socket(nodeList.get(i).ipaddr, nodeList.get(i).portno);
				SocketConnections.addToSocketConnections(nodeList.get(i).id, clientSocket);
				System.out.println("					Successfully connected to #Server# no : " + nodeList.get(i).id + " , HostName : "
						+ clientSocket.getInetAddress().getHostName());

				// System.out.println("Total socket connections : " + SocketConnections.getSocketConnectionsSize());
				msgReceive = new MessageReceiver(nodeList.get(i).id, clientSocket);
				msgReceive.start();
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
