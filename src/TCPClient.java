import java.util.List;
import java.util.Scanner;


public class TCPClient {

	static List<Node> nodeList;
	static int nodeId;
	static int totalNodes;
	
	public static void main(String[] args) {
		TCPClient tcpClient = new TCPClient();
		nodeList = readConfigFile();
		tcpClient.connectToServers(nodeList, nodeId);
	}
	
	
	private void connectToServers(List<Node> nodeList, int id){

		Client client = new Client();
		client.connectToServers(nodeList, nodeId);
	
	}
	
	
	private static List<Node> readConfigFile() {

		Scanner in = new Scanner(System.in);
		System.out.println("Please enter Node id");
		nodeId = in.nextInt();
		List<Node> nodeList = ParseFile.parseConfigFile(nodeId);
		return nodeList;

	}
}
