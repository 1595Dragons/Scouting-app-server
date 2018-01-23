import java.io.IOException;

import javax.bluetooth.BluetoothStateException;
import javax.bluetooth.LocalDevice;
import javax.bluetooth.UUID;
import javax.microedition.io.Connector;
import javax.microedition.io.StreamConnection;
import javax.microedition.io.StreamConnectionNotifier;

public class ScoutingApp {

	public static StreamConnection currentConnection;

	public static void main(String args[]) {
		System.out.println("1595 Scouting App Server\nVersion 1.5\n\nLooking for scouting data file...");
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

		if (selectFile.fileMissing()) {
			selectFile.makeFile();
			System.out.println("File is missing, creating new file at: " + file.file.getPath() + "\n");
		} else {
			System.out.println("Scouting data file found at " + file.file.getPath() + "\n");
		}

		try {
			System.out.println("\n\nThe MAC Address for this device is: "
					+ LocalDevice.getLocalDevice().getBluetoothAddress().replaceAll("..(?!$)", "$0:").toUpperCase());
		} catch (BluetoothStateException e) {
			System.out.println("Cannot get device MAC address: " + e.getMessage());
		}
		boolean running = true;

		// Create a UUID for SPP, and then create the URL
		UUID uuid = new UUID("1101", true);
		String connectionString = "btspp://localhost:" + uuid + ";name=SpudSPPServer";
		if (debug) {
			System.out.println("Connection URL: " + connectionString);
		}
		// open server url
		StreamConnectionNotifier streamConnNotifier = null;
		try {
			 streamConnNotifier = (StreamConnectionNotifier) Connector.open(connectionString);
		} catch (IOException e1) {
			System.out.println("Error, cannot open URL: " + e1.getMessage());
			e1.printStackTrace();
		}
		System.out.println("Ready to recieve more data! (Press ctrl + c to end)\n");
		while (running) {
			// Check if recieving a connectio
			try {
				currentConnection = streamConnNotifier.acceptAndOpen();
				if (currentConnection != null) {
					new Thread(new server()).start();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
