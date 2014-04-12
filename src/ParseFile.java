import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ParseFile {

	static List<Node> nodeList = new ArrayList<Node>();

	public static List<Node> parseConfigFile(int id) {
		String line;

		BufferedReader readFile;
		try {
			readFile = new BufferedReader(new FileReader("/home/rohit/indigo_workspace/FileReplicationServer/Config"));
			while ((line = readFile.readLine()) != null) {

				if (!line.startsWith("#")) {

					String[] data = line.split(" ");
					if (data[1].equals("#")) {
						TCPClient.totalNodes = Integer.parseInt(data[0]);
						System.out.println("Total nodes : " + TCPClient.totalNodes);
					} else {

						Node n = new Node();
						int nodeid = Integer.parseInt(data[0]);
						String ip = data[1];
						int port = Integer.parseInt(data[2]);
						n.id = nodeid;
						n.ipaddr = ip;
						n.portno = port;
						nodeList.add(n);

					}
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return nodeList;
	}
}
