import java.util.ArrayList;
import java.util.Scanner;

public class ReadWriteOperation {
	MessageSender msgSender;
	String objectToBeRead;
	static int[] hashedServers;
	static String object;
	static String objectToWrite;

	// send the actual object you want to read, to the server
	public void readOperation(int serverNodeID) {
		msgSender = new MessageSender();
		// msgSender.sendMessage(Message.READ.toString() + "," + object + "," +
		// SocketConnections.getMyNodeId(), hashedServers[0]);
		// msgSender.sendMessage(Message.READ.toString() + "," + object + "," +
		// SocketConnections.getMyNodeId(), hashedServers[1]);
		// msgSender.sendMessage(Message.READ.toString() + "," + object + "," +
		// SocketConnections.getMyNodeId(), hashedServers[2]);

		for (int i : hashedServers) {
			if (i == serverNodeID) {
				System.out.println("					**Sending READ requests to Server : " + i);
				String message = Message.READ.toString() + "," + object + "," + SocketConnections.getMyNodeId();
				msgSender.sendMessage(message, i);
				
				StaticalAnalysis.setReadMessages(1);
			}
		}
	}

	public void writeOperation() {

		
		
		
		msgSender = new MessageSender();
		msgSender.sendMessage(Message.WRITE.toString() + "," + objectToWrite + "," + SocketConnections.getMyNodeId() + ",", hashedServers[0]);
		msgSender.sendMessage(Message.WRITE.toString() + "," + objectToWrite + "," + SocketConnections.getMyNodeId() + ",", hashedServers[1]);
		msgSender.sendMessage(Message.WRITE.toString() + "," + objectToWrite + "," + SocketConnections.getMyNodeId() + ",", hashedServers[2]);
		System.out.println(" * Write request sent along with object *");
		
		StaticalAnalysis.setWriteMessages(3);
	}

	public void pingServersForReadOperation() {

//		StaticalAnalysis.setReadMessagesToZero();
		
		Constants.setReadOperation(true);
		System.out.println("Enter the object you want to read");
		Scanner in = new Scanner(System.in);
		objectToBeRead = in.next();
		int[] hashServers = getHashedServers(objectToBeRead);

		msgSender = new MessageSender();
		msgSender.sendMessage(Message.PING.toString() + "," + SocketConnections.getMyNodeId(), hashServers[0]);
		msgSender.sendMessage(Message.PING.toString() + "," + SocketConnections.getMyNodeId(), hashServers[1]);
		msgSender.sendMessage(Message.PING.toString() + "," + SocketConnections.getMyNodeId(), hashServers[2]);
		
//		sending requests to three serves, so increment three
		StaticalAnalysis.setReadMessages(3);
	}

	public void pingServersForWriteOperation() {
		
//		StaticalAnalysis.setWriteMessagesToZero();
		
		Constants.setWriteOperation(true);
		System.out.println("Please enter object,value");
		Scanner in = new Scanner(System.in);
		objectToWrite = in.nextLine();
		String[] dataForWrite = objectToWrite.split(",");

		int[] hashServers = getHashedServers(dataForWrite[0]);
		msgSender = new MessageSender();
		msgSender.sendMessage(Message.PING.toString() + "," + SocketConnections.getMyNodeId(), hashServers[0]);
		msgSender.sendMessage(Message.PING.toString() + "," + SocketConnections.getMyNodeId(), hashServers[1]);
		msgSender.sendMessage(Message.PING.toString() + "," + SocketConnections.getMyNodeId(), hashServers[2]);

		StaticalAnalysis.setWriteMessages(3);
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
		System.out.println("*** hash for " + object + " is : " + index);
		for (int j : hashedServers) {
			System.out.print("	" + j + " ");
		}
		System.out.println();
		System.out.println("----------------");
		return hashedServers;
	}
}
