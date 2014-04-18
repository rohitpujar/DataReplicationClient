import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class SocketConnections {

	static private Map<Integer, Socket> socketConnections = new HashMap<Integer, Socket>();

	public static Map<Integer, Socket> getSocketConnections() {
		return socketConnections;
	}

	public static void addToSocketConnections(Integer id, Socket socket) {

		socketConnections.put(id, socket);
	}

	public static void displaySocketConnections() {

		System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
		Iterator<Map.Entry<Integer, Socket>> it = socketConnections.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<Integer, Socket> pairs = it.next();
			System.out.println(pairs.getKey() + " = " + pairs.getValue());
		}

	}

	public static int getSocketConnectionsSize() {

		return socketConnections.size();
	}

}
