import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class MessageReceiver extends Thread {

	DataInputStream dis;
	Socket socket;
	int nodeId;
	static int ack;

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
				System.out.println("Server says : " + message);
				
				List<String> recvdMsgTokens = new ArrayList<String>();
				StringTokenizer msgTokens = new StringTokenizer(message, ",");

				while (msgTokens.hasMoreTokens()) {
					recvdMsgTokens.add(msgTokens.nextToken());
				}
				System.out.println("------- PRINTING TOKENS NOW -------");
				for(String token : recvdMsgTokens){
					System.out.println(" TOKEN present : "+token);
				}
				if(recvdMsgTokens.get(0).equals("true")){
					ack++;
					System.out.println("Ack incremented");
					if(ack>=2){
						System.out.println("Read is : "+Constants.getReadOperation());
						if(Constants.getReadOperation()==true){
							System.out.println("2 ACKS received.. Good to go!!!");
							ReadOperation readOperation = new ReadOperation();
							readOperation.readOperation();
						} else if(Constants.getWriteOperation()==true){
							
						}
					}
				}
				
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
