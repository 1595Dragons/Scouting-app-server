package application;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.bluetooth.BluetoothStateException;
import javax.bluetooth.RemoteDevice;
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
			
			// This is the string of the CSV's file path.
			String csv = new File(System.getProperty("user.dir") + "/scouting_data.csv").getAbsolutePath();

			
			// Create a writer in order to write to the file
			BufferedWriter write = null;
			try {
				write = new BufferedWriter(new FileWriter(csv, true));
			} catch (IOException e) {
				Info.log(String.format("Error creating file writer: %s", e.getMessage()), true);
				
			}
			
			
			
			// Try writing to said file, but report if anything is wrong!
			try {
				write.append(String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s",
						"Team number",
						"Has an auto",
						"Can place on switch during auto",
						"Can place on scale during auto",
						"Number of cubes placed on switch",
						"Number of cubes placed on scale",
						"Number of cubes placed in exchange",
						"Climb value",
						"Comments"));
				write.newLine();
				write.flush();
				write.close();
			} catch (IOException e) {
				Info.log(String.format("Error writing to file: %s", e.getMessage()), true);
			}

			Info.log("File missing, creating new file", false);
			
			
			
		} 
		else {
			// File was found, all is good.
			Info.log("Files successfully found", false);
		}
		
		if (!(new File(System.getProperty("user.dir") + "/pit_data.csv")).exists()) {
			String csv = new File(System.getProperty("user.dir") + "/scouting_data.csv").getAbsolutePath();
		
			
			BufferedWriter write = null;
			try {
				write = new BufferedWriter(new FileWriter(csv, true));
			} catch (IOException e) {
				Info.log(String.format("Error creating file writer: %s", e.getMessage()), true);
				
			}
			
			
			
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
					Info.deviceConnect(RemoteDevice.getRemoteDevice(currentConnection).getFriendlyName(false));
					new Thread(new SPPserver()).start();
				}
			} catch (IOException e) {
				Info.log(String.format("Error creating a new thread: %s", e.getMessage()), true);
				e.printStackTrace();
			}
		}

	}
}
