import java.io.IOException;
import java.io.PrintStream;

import javax.bluetooth.BluetoothStateException;
import javax.bluetooth.LocalDevice;
import javax.bluetooth.UUID;
import javax.microedition.io.Connector;
import javax.microedition.io.StreamConnection;
import javax.microedition.io.StreamConnectionNotifier;

public class ScoutingApp {

	public static StreamConnection currentConnection;
	public static StreamConnectionNotifier streamConnNotifier;

	public static void main(String args[]) {
		// Header
		System.out.println("1595 Scouting App Server\nVersion 1.5\n");
		PrintStream printStream = System.out;
		printStream.print("Looking for scouting data file...");
		
		
		// Begin searching for the file to write data to
		// If no file is give, vreate one
		selectFile file = new selectFile();
		if (selectFile.fileMissing()) {
			selectFile.makeFile();
			// System.out.println("File is missing, creating new file at: " + file.file.getPath() + "\n");
			printStream.append("file missing, creating new file at: " + file.file.getPath() + "\n");
		} else {
			// System.out.println("Scouting data file found at " + file.file.getPath() + "\n");
			printStream.append("found at: " + file.file.getPath() + "\n");
		}
		
		// Display the MAC Address, and then start searching...
		// TODO: On newer phones, gets error, but can still retrieve device name... hmm...
		try {
			System.out.println("\n\nThe MAC Address for this device is: "
					+ LocalDevice.getLocalDevice().getBluetoothAddress().replaceAll("..(?!$)", "$0:").toUpperCase() + "\n\n");
		} catch (BluetoothStateException e) {
			System.out.println("Cannot get device MAC address: " + e.getMessage());
		}

		// Create a UUID for SPP, and then create the URL
		UUID uuid = new UUID("1101", true);
		String connectionString = "btspp://localhost:" + uuid + ";name=SpudSPPServer";
		
		// open server url using the created URL
		streamConnNotifier = null;
		try {
			 streamConnNotifier = (StreamConnectionNotifier) Connector.open(connectionString);
		} catch (IOException e1) {
			System.out.println("Error, cannot open URL: " + e1.getMessage());
			e1.printStackTrace();
		}
		System.out.println("Ready to recieve more data! (Press ctrl + c to end)\n");
		while (true) {
			
			
			// Check if recieving a connection
			// On connecion, open it, and pass it to a newly created thread
			try {
				currentConnection = streamConnNotifier.acceptAndOpen();
				if (currentConnection != null) {
					new Thread(new server()).start();
				}
			} catch (IOException e) {
				System.out.println("Error creating a new thread: " + e.getMessage());
				e.printStackTrace();
			}
		}
	}
}
