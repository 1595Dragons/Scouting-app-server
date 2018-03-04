package application;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.bluetooth.BluetoothStateException;
import javax.bluetooth.UUID;
import javax.microedition.io.Connector;
import javax.microedition.io.StreamConnection;
import javax.microedition.io.StreamConnectionNotifier;


public class ScoutingApp {

	public static StreamConnection currentConnection;
	public static StreamConnectionNotifier streamConnNotifier;

	public static void main(String args[]) {

		// Create the info GUI
		Info infoWindow = new Info();
		try {
			infoWindow.init();
		} catch (BluetoothStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Begin searching for the file to write data to
		// If no file is give, create one
		if (!(new File(System.getProperty("user.dir") + "/scouting_data.csv")).exists()) {
			// selectFile.makeFile();
			String csv = new File(System.getProperty("user.dir") + "/scouting_data.csv").getAbsolutePath();

			BufferedWriter write = null;
			try {
				write = new BufferedWriter(new FileWriter(csv, true));
			} catch (IOException e) {
				Info.log(String.format("Error creating file writer: %s", e.getMessage()), true);
				
			}
			try {
				write.append(
						"Team number, Has an auto, Can place on switch during auto, Can place on scale during auto, Can place on switch during teleOp, Can place on scale during teleOP, Number of cubes placed, Climb value");
				write.newLine();
				write.flush();
				write.close();
			} catch (IOException e) {
				Info.log(String.format("Error writing to file: %s", e.getMessage()), true);
			}

			Info.log("File missing, creating new file", false);
		} else {
			Info.log("File successfully found", false);
		}

		
		
		// Create a UUID for SPP, and then create the URL
		UUID uuid = new UUID("1101", true);
		String connectionString = "btspp://localhost:" + uuid + ";name=SpudSPPServer";

		// open server url using the created URL
		streamConnNotifier = null;
		try {
			streamConnNotifier = (StreamConnectionNotifier) Connector.open(connectionString);
		} catch (IOException e1) {
			Info.log(String.format("Error, cannot open URL: %s", e1.getMessage()), true);
			e1.printStackTrace();
		}

		Info.log("Ready to recieve data!", false);
		new Thread(new takeData()).start();
		while (true) {
			// Check if recieving a connection
			// On connecion, open it, and pass it to a newly created thread
			try {
				currentConnection = streamConnNotifier.acceptAndOpen();
				if (currentConnection != null) {
					new Thread(new SPPserver()).start();
				}
			} catch (IOException e) {
				Info.log(String.format("Error creating a new thread: %s", e.getMessage()), true);
				e.printStackTrace();
			}
		}

	}
}
