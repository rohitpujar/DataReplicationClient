import java.util.ArrayList;
import java.util.Scanner;

public class ReadOperation {
	MessageSender msgSender;
	String objectToBeRead;
	static int[] hashedServers;
	static String object;

	// send the actual object you want to read, to the server
	public void readOperation() {

		msgSender = new MessageSender();
		msgSender.sendMessage(Message.READ.toString() + "," + object + "," + SocketConnections.getMyNodeId(), hashedServers[0]);
		msgSender.sendMessage(Message.READ.toString() + "," + object + "," + SocketConnections.getMyNodeId(), hashedServers[1]);
		msgSender.sendMessage(Message.READ.toString() + "," + object + "," + SocketConnections.getMyNodeId(), hashedServers[2]);

	}

	public void pingServersForReadOperation() {

		Constants.setReadOperation(true);
		msgSender = new MessageSender();
		System.out.println("Enter the object you want to read");
		Scanner in = new Scanner(System.in);
		objectToBeRead = in.next();
		int[] hashServers = getHashedServers(objectToBeRead);

		msgSender.sendMessage(Message.PING.toString() + "," + SocketConnections.getMyNodeId(), hashServers[0]);
		msgSender.sendMessage(Message.PING.toString() + "," + SocketConnections.getMyNodeId(), hashServers[1]);
		msgSender.sendMessage(Message.PING.toString() + "," + SocketConnections.getMyNodeId(), hashServers[2]);

	}

	private int[] getHashedServers(String object) {
		this.object = object;
		int totalNodes = TCPClient.totalNodes;

		ArrayList<Integer> values = new ArrayList<>();

		for (int i = 0; i < totalNodes; i++) {
			values.add(i);
		}
		values.add(0);
		values.add(1); // the array will look like {0,1,2,3,4,5,0,1}

		int hashValue = object.hashCode();
		// to generate a POSITIVE hash code value
		int index = (hashValue & 0x7FFFFFFF) % totalNodes;
		System.out.println("*** hash for " + object + " is : " + index);
		System.out.println("These servers will be selected");
		hashedServers = new int[3];
		for (int i = 0; i < values.size(); i++) {
			if (values.get(i) == index) {
				hashedServers[0] = values.get(i);
				hashedServers[1] = values.get(i + 1);
				hashedServers[2] = values.get(i + 2);
				break;
			}
		}
		System.out.println("----------- Final selected servers ---------");
		for (int j : hashedServers) {
			System.out.println(j);
		}
		return hashedServers;
	}
}
