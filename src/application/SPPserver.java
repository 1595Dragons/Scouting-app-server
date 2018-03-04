package application;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.microedition.io.StreamConnection;

public class SPPserver extends Thread {
	StreamConnection connection = ScoutingApp.currentConnection;

	// Wait for client connection
	public void run() {
		if (connection != null) {
			synchronized (connection) {
				
				Info.log("Retreving data...", false);

				// Get the stream from the bluetooth connection
				InputStream inStream = null;
				try {
					inStream = connection.openInputStream();
				} catch (IOException e1) {
					Info.log(String.format("Error, cannot open stream: %s", e1.getMessage()), true);
					e1.printStackTrace();
				}

				// Read the data from the stream
				BufferedReader bReader = new BufferedReader(new InputStreamReader(inStream));
				String lineRead = null;
				try {
					lineRead = bReader.readLine();
					if (lineRead == null) {
						Info.log("Error: Data recieved is either incomplete or corrupt", true);
						return;
					}
				} catch (IOException e1) {
					Info.log(String.format("Error, cannot read stream: %s", e1.getMessage()), true);
					e1.printStackTrace();
				}

				/*
				// Write the data to a CSV file
				Info.log("Writing data to file", false);
				FileWriter fw = null;
				try {
					fw = new FileWriter(
							new File(System.getProperty("user.dir") + "/scouting_data.csv").getAbsolutePath(), true);
				} catch (IOException e1) {
					Info.log(String.format("Error, cannot edit file: %s", e1.getMessage()), true);
					e1.printStackTrace();
				}
				try {
					fw.append(System.getProperty("line.separator") + lineRead);
					fw.flush();
				} catch (IOException e1) {
					Info.log(String.format("Error, cannot write to file: %s", e1.getMessage()), true);
					e1.printStackTrace();
				}
				*/
				Info.writeToFile(lineRead);
				

				// Try closing the writer and stream
				try {
					//fw.close();
					bReader.close();
					inStream.close();
				} catch (IOException e1) {
					Info.log(String.format("Error, cannot close stream: %s", e1.getMessage()), true);
					e1.printStackTrace();
				}

				// Success!
				//Info.log(String.format("Data written for team: %s!", lineRead.split(",")[0]), false);
				try {
					connection.close();
				} catch (IOException e) {
					Info.log(String.format("Error closing connections: %s", e.getMessage()), true);
					e.printStackTrace();
				}

			}
		} else {
			Info.log("Connection is null!", true);
		}
	}
}
