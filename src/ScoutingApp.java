import java.io.IOException;

import javax.bluetooth.LocalDevice;

public class ScoutingApp {

	public static void main(String args[]) throws IOException {
		System.out.println("1595 Scouting App Server\nVersion 1.2\n\nLooking for scouting data file...");
		selectFile file = new selectFile();
		boolean debug;
		if (args.length != 0) {
			debug = (boolean) args[0].equals("debug");
		} else {
			debug = false;
		}

		if (debug) {
			System.out.println("Debug mode enabled!");
		}

		server blueToothServer = new server();
		if (selectFile.fileMissing()) {
			selectFile.makeFile();
			System.out.println("File is missing, creating new file at: " + file.file.getPath() + "\n");
		} else {
			System.out.println("Scouting data file found at " + file.file.getPath() + "\n");
		}

		System.out.println("\n\nThe MAC Address for this device is: "
				+ LocalDevice.getLocalDevice().getBluetoothAddress().replaceAll("..(?!$)", "$0:").toUpperCase());
		System.out.println("\nStarting Scouting App server...");

		blueToothServer.startServer(debug);

	}

}
