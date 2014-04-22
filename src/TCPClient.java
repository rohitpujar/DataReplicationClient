import java.util.List;
import java.util.Scanner;

public class TCPClient {

	static List<Node> nodeList;
	static int totalNodes;
	

	public static void main(String[] args) {
		TCPClient tcpClient = new TCPClient();
		nodeList = readConfigFile();
		tcpClient.connectToServers(nodeList, SocketConnections.getMyNodeId());
		clientConsole();
	}

	private void connectToServers(List<Node> nodeList, int id) {

		Client client = new Client();
		client.connectToServers(nodeList, SocketConnections.getMyNodeId());
	}

	private static List<Node> readConfigFile() {

		Scanner in = new Scanner(System.in);
		System.out.println("Please enter Node id");
		SocketConnections.setMyNodeId(in.nextInt());
		List<Node> nodeList = ParseFile.parseConfigFile(SocketConnections.getMyNodeId());
		return nodeList;

	}

	private static void clientConsole() {
		System.out.println("1. Ping Servers");
		System.out.println("2. Read objects from Server");
		MessageSender msgSender = new MessageSender();
		Scanner in = new Scanner(System.in);
		int input = in.nextInt();
		switch (input) {
		case 1:

			msgSender.sendToAllServers("Ping");
			break;

		case 2:
			ReadOperation readObjects = new ReadOperation();
			readObjects.pingServersForReadOperation();
		default:
			break;
		}

	}

	private static int calculateHash(String object) {

		int hashValue = object.hashCode() % 7;
		return hashValue;
	}

}
