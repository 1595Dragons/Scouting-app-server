package application;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.microedition.io.StreamConnection;

public class SPPserver extends Thread {
	StreamConnection connection = ScoutingApp.currentConnection;

	// Wait for client connection
	public void run() {
		//Info infoWindow = new Info();
		if (connection != null) {
			synchronized (connection) {
				
				//System.out.println("Retreving data...");
				Info.log("Retreving data...", false);
				//infoWindow.outputText.setText("Retreving data...");
				//infoWindow.outputText.setForeground(Color.BLACK);

				// Get the stream from the bluetooth connection
				InputStream inStream = null;
				try {
					inStream = connection.openInputStream();
				} catch (IOException e1) {
					//System.out.println("Error, cannot open stream: " + e1.getMessage());
					//infoWindow.outputText.setText(String.format("Error, cannot open stream: %s", e1.getMessage()));
					//infoWindow.outputText.setForeground(Color.RED);
					Info.log(String.format("Error, cannot open stream: %s", e1.getMessage()), true);
					e1.printStackTrace();
				}

				// Read the data from the stream
				BufferedReader bReader = new BufferedReader(new InputStreamReader(inStream));
				String lineRead = null;
				try {
					lineRead = bReader.readLine();
					if (lineRead == null) {
						//System.out.println(String.format("Error: %s", "Data recieved is either incomplete or corrupt"));
						//infoWindow.outputText.setText(String.format("Error: %s", "Data recieved is either incomplete or corrupt"));
						//infoWindow.outputText.setForeground(Color.RED);
						Info.log("Error: Data recieved is either incomplete or corrupt", true);
					}
				} catch (IOException e1) {
					//System.out.println("Error, cannot read stream: " + e1.getMessage());
					//infoWindow.outputText.setText(String.format("Error, cannot read stream: %s", e1.getMessage()));
					//infoWindow.outputText.setForeground(Color.RED);
					Info.log(String.format("Error, cannot read stream: %s", e1.getMessage()), true);
					e1.printStackTrace();
				}

				// Write the data to a CSV file
				//System.out.print("writing to file...");
				//infoWindow.outputText.setText(String.format("Writing data to file..."));
				//infoWindow.outputText.setForeground(Color.BLACK);
				Info.log("Writing data to file", false);
				FileWriter fw = null;
				try {
					fw = new FileWriter(
							new File(System.getProperty("user.dir") + "/scouting_data.csv").getAbsolutePath(), true);
				} catch (IOException e1) {
					//System.out.println("Error, cannot edit file: " + e1.getMessage());
					//infoWindow.outputText.setText(String.format("Error, cannot edit file: %s", e1.getMessage()));
					//infoWindow.outputText.setForeground(Color.RED);
					Info.log(String.format("Error, cannot edit file: %s", e1.getMessage()), true);
					e1.printStackTrace();
				}
				try {
					fw.append(System.getProperty("line.separator") + lineRead);
					fw.flush();
				} catch (IOException e1) {
					//System.out.println("Error, cannot write to file: " + e1.getMessage());
					//infoWindow.outputText.setText(String.format("Error, cannot write to file: %s", e1.getMessage()));
					//infoWindow.outputText.setForeground(Color.RED);
					Info.log(String.format("Error, cannot write to file: %s", e1.getMessage()), true);
					e1.printStackTrace();
				}

				// Try closing the writer and stream
				//System.out.print("finishing data for team: " + lineRead.split(",")[0] + "\n");
				try {
					fw.close();
					bReader.close();
					inStream.close();
				} catch (IOException e1) {
					//System.out.println("Error, cannot close streams: " + e1.getMessage());
					//infoWindow.outputText.setText(String.format("Error, cannot close stream: %s", e1.getMessage()));
					//infoWindow.outputText.setForeground(Color.RED);
					Info.log(String.format("Error, cannot close stream: %s", e1.getMessage()), true);
					e1.printStackTrace();
				}

				// Success!
				//System.out.println("Data written successfully!");
				//infoWindow.outputText.setText(String.format("Data written for team: %s!", lineRead.split(",")[0]));
				//infoWindow.outputText.setForeground(Color.BLACK);
				Info.log(String.format("Data written for team: %s!", lineRead.split(",")[0]), false);
				try {
					connection.close();
					//System.out.println("\nReady to recieve more data! (Press ctrl + c to end)\n");
				} catch (IOException e) {
					//System.out.println("Error closing connection: " + e.getMessage());
					//infoWindow.outputText.setText(String.format("Error closing connections: %s", e.getMessage()));
					//infoWindow.outputText.setForeground(Color.RED);
					Info.log(String.format("Error closing connections: %s", e.getMessage()), true);
					e.printStackTrace();
				}

			}
		} else {
			//System.out.println("Connection is null!");
			//infoWindow.outputText.setText(String.format("Conneciton is null!"));
			//infoWindow.outputText.setForeground(Color.RED);
			Info.log("Connection is null!", true);
		}
	}
}
