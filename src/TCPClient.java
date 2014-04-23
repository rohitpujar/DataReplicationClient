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
//		in.close();
		List<Node> nodeList = ParseFile.parseConfigFile(SocketConnections.getMyNodeId());
		return nodeList;

	}

	private static void clientConsole() {

		while (true) {
			System.out.println("1. Ping Servers");
			System.out.println("2. Read objects ");
			System.out.println("3. Write/Modify objects");
			MessageSender msgSender = new MessageSender();
			Scanner in = new Scanner(System.in);
			int input = in.nextInt();
//			in.close();
			System.out.println("Input : " + input);
			switch (input) {

			case 1:

				msgSender.sendToAllServers("Ping");
				break;

			case 2:
				ReadWriteOperation readObjects = new ReadWriteOperation();
				readObjects.pingServersForReadOperation();
				break;

			case 3:

				ReadWriteOperation writeObject = new ReadWriteOperation();
				writeObject.pingServersForWriteOperation();

			default:
				break;
			}
		}

	}

}
