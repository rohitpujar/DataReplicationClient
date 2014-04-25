public class Constants {

	private static Boolean readOperation = false;
	private static Boolean writeOperation = false;

	public static Boolean getReadOperation() {
		return readOperation;
	}

	public static void setReadOperation(Boolean readOperation) {
		Constants.readOperation = readOperation;
	}

	public static Boolean getWriteOperation() {
		return writeOperation;
	}

	public static void setWriteOperation(Boolean writeOperation) {
		Constants.writeOperation = writeOperation;
	}

}
