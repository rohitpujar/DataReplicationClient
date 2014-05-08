import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import com.sun.org.glassfish.external.statistics.Statistic;

public class MessageReceiver extends Thread {

	DataInputStream dis;
	Socket socket;
	int nodeId;
	static int upAck;
	static int downAck;

	public MessageReceiver(int id, Socket socket) {

		this.nodeId = id;
		this.socket = socket;

	}

	/* (non-Javadoc)
	 * @see java.lang.Thread#run()
	 */
	@Override
	public void run() {

		try {
			dis = new DataInputStream(socket.getInputStream());

			while (true) {
				String message = dis.readUTF().toString();
				System.out.println("																	Server says : " + message);

				List<String> recvdMsgTokens = new ArrayList<String>();
				StringTokenizer msgTokens = new StringTokenizer(message, ",");

				while (msgTokens.hasMoreTokens()) {
					recvdMsgTokens.add(msgTokens.nextToken());
				}
				// System.out.println("------- PRINTING TOKENS NOW -------");
				// for(String token : recvdMsgTokens){
				// System.out.println(" TOKEN present : "+token);
				// }
				if (recvdMsgTokens.get(0).equals("true")) {
					upAck++;
					System.out.println("				###### ACK count : " + upAck + "  #########");
					System.out.println(" 					READ SET : " + Constants.getReadOperation());
					System.out.println(" 					   WRITE SET : " + Constants.getWriteOperation());
					System.out.println(" 				########################################");
					
					
					// READ OPERATION
					if (Constants.getReadOperation() == true) {
//						one ack received = one message incremented
						StaticalAnalysis.setReadMessages(1);
//						***************************************
						Constants.setWriteOperation(false);
						if (upAck > 0) {
							System.out.println("Read is : " + Constants.getReadOperation());
							if (Constants.getReadOperation() == true) {
								ReadWriteOperation readOperation = new ReadWriteOperation();
								readOperation.readOperation(Integer.parseInt(recvdMsgTokens.get(1)));
								Constants.setReadOperation(false);
								upAck = 0;

							}
						}
					}
					// WRITE OPERATION
					if (Constants.getWriteOperation() == true) {
						
						StaticalAnalysis.setWriteMessages(1);

						// Toggle the Read/Write. If READ is true, set write to
						// false, to distinguish between the Ack's. Because, if
						// write is set to true and the user selects read
						// operation next,the write operation is never made
						// false

						Constants.setReadOperation(false);
						if (upAck > 1) {
							System.out.println("~ " + upAck + " ACK's received for the write operation **");
							ReadWriteOperation writeOperation = new ReadWriteOperation();
							writeOperation.writeOperation();
							System.out.println("	< Write Operation Successful >");
							Constants.setWriteOperation(false);
							upAck = 0;
							
							System.out.println("############### Total messages exchanged for WRITE : "+StaticalAnalysis.getWriteMessages());
							StaticalAnalysis.setWriteMessagesToZero();
						}
					}
				} else if (recvdMsgTokens.get(0).equals("false")) {
					downAck++;
					
					if (Constants.getReadOperation() == true && downAck == 3) {
						System.out.println("		**No Servers Up for READ Operation");
						StaticalAnalysis.setReadMessagesToZero();
						downAck=0;
					}

					if (Constants.getWriteOperation() == true && downAck > 1) {
						System.out.println(" 		**Minimum no. of Servers required for WRITE operation not UP");
						upAck = 0;
						downAck=0;
						StaticalAnalysis.setWriteMessagesToZero();
					}
					// if (downAck > 1) {
					// System.out.println("^^^^^^ Minimum no. of Servers required for this operation aren't present  ^^^^^^^");
					// }
				}
				if (recvdMsgTokens.get(0).equals(Message.READ_RESPONSE.toString())) {
					
//					*****
					StaticalAnalysis.setReadMessages(1);
//					*****
					if(recvdMsgTokens.get(1).equals(Message.OBJECT_NOT_FOUND.toString())){
						System.out.println(" 	>>>>> OBJECT NOT FOUND ");
					} else{
					// System.out.println("#### Object read request's response : ");
					System.out.println("Object/Value/Version ==>> " + recvdMsgTokens.get(1) + "  " + recvdMsgTokens.get(3) + " "
							+ recvdMsgTokens.get(2));
					// for (String read : recvdMsgTokens) {
					// System.out.print(read + " ");
					// }
					}
					
					System.out.println("############### Total messages exchanged for READ : "+StaticalAnalysis.getReadMessages());
					StaticalAnalysis.setReadMessagesToZero();
				}

			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
