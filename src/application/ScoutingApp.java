package application;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.bluetooth.UUID;
import javax.microedition.io.Connector;
import javax.microedition.io.StreamConnection;
import javax.microedition.io.StreamConnectionNotifier;

public class ScoutingApp {

	public static StreamConnection currentConnection;
	public static StreamConnectionNotifier streamConnNotifier;

	public static void main(String args[]) {
		// TODO: Until beta branch, use old console!
		
		/*
		// Create the info GUI
		Info infoWindow = new Info();
		try {
			infoWindow.init();
		} catch (BluetoothStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/

		

		// Begin searching for the file to write data to
		// If no file is give, vreate one
		if (!(new File(System.getProperty("user.dir") + "/scouting_data.csv")).exists()) {
			// selectFile.makeFile();
			String csv = new File(System.getProperty("user.dir") + "/scouting_data.csv").getAbsolutePath();

			BufferedWriter write = null;
			try {
				write = new BufferedWriter(new FileWriter(csv, true));
			} catch (IOException e) {
				System.out.println("Error creating file writer: " + e.getMessage());
				//infoWindow.outputText.setText(String.format("Error creating file writer: %s", e.getMessage()));
				//infoWindow.outputText.setForeground(Color.RED);
			}

			try {
				write.append(
						"Team number, hasAuto, autoSwitch, autoScale, teleSwitch, teleScale, cubeNumber, endClimb, endClimbAssist");
				write.newLine();
				write.flush();
				write.close();
			} catch (IOException e) {
				System.out.println(String.format("Error writing to file: %s", e.getMessage()));
				//infoWindow.outputText.setText(String.format("Error writing to file: %s", e.getMessage()));
				//infoWindow.outputText.setForeground(Color.RED);
			}

			System.out.println("File missing, creating new file....");
			//infoWindow.outputText.setText("File missing, creating new file...");
			//infoWindow.outputText.setForeground(Color.BLACK);
		} else {
			System.out.println("File successfully found");
			//infoWindow.outputText.setText("File successfully found");
			//infoWindow.outputText.setForeground(Color.BLACK);
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
			//infoWindow.outputText.setText(String.format("Error, cannot open URL: %s", e1.getMessage()));
			//infoWindow.outputText.setForeground(Color.RED);
			e1.printStackTrace();
		}
		
		

		// Ready to start receieving data!
		System.out.println("Ready to recieve data!");
		//infoWindow.outputText.setText("Ready to recieve data!");
		//infoWindow.outputText.setForeground(Color.BLACK);
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
				System.out.println(String.format("Error creating a new thread: %s", e.getMessage()));
				//infoWindow.outputText.setText(String.format("Error creating a new thread: %s", e.getMessage()));
				//infoWindow.outputText.setForeground(Color.RED);
				e.printStackTrace();
			}
		}

	}
}
