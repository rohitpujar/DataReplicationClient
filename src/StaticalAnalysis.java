
public class StaticalAnalysis {

	
	private static int readMessages;
	private static int writeMessages;
	
	
	public static int getReadMessages() {
		return readMessages;
	}
	public static void setReadMessages(int readMsgs) {
		StaticalAnalysis.readMessages = readMessages+readMsgs;
		System.out.println("						((readMessages "+readMessages+"))");
	}
	public static int getWriteMessages() {
		return writeMessages;
	}
	public static void setWriteMessages(int writeMsgs) {
		StaticalAnalysis.writeMessages = writeMessages+writeMsgs;
		System.out.println("						[[WRITEMessages "+writeMessages+"]]");
	}
	
	public static void setReadMessagesToZero(){
		readMessages = 0;
	}
	
	public static void setWriteMessagesToZero(){
		writeMessages = 0;
	}
	
	
}
