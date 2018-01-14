import java.io.IOException;

import javax.bluetooth.LocalDevice;

public class ScoutingApp {

	public static void main(String args[]) throws IOException {
		
		server blueToothServer = new server();
		if (selectFile.fileMissing()) {
			selectFile.makeFile();
			System.out.println("File is missing, creating new file at: " + selectFile.file.getPath());
		} else {
			System.out.println("Scouting data file found at " + selectFile.file.getPath());
		}
		
		System.out.println("The MAC Address for this device is: " + LocalDevice.getLocalDevice().getBluetoothAddress().replace("-", ":").toUpperCase());
		System.out.println("Starting Scouting App server...");
		boolean debug = args[0].equals("debug");
		blueToothServer.startServer(debug);
		
	}
	
}
