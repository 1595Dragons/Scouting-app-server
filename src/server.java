import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.microedition.io.StreamConnection;

public class server extends Thread {
	selectFile file = new selectFile();
	StreamConnection connection = ScoutingApp.currentConnection;

	// Wait for client connection
	public void run() {
		if (connection != null) {
			synchronized (connection) {
				try {
					// StreamConnection connection = scoutingapp.streamConnNotifier.acceptAndOpen();
					System.out.println("Device connected! Retreiving data...");

					// read string from spp client
					InputStream inStream = connection.openInputStream();
					BufferedReader bReader = new BufferedReader(new InputStreamReader(inStream));
					String lineRead = bReader.readLine();
					System.out.println("Writing to file...");
					BufferedWriter writer = new BufferedWriter(new FileWriter(file.file.getAbsolutePath(), true));
					writer.newLine();
					writer.append(lineRead);
					writer.flush();
					System.out.println("Finishing data for team: " + lineRead.split(",")[0]);
					writer.close();
					System.out.println("Data written successfully!");
					bReader.close();
					inStream.close();

					// Close connection
					connection.close();
					System.out.println("\nReady to recieve more data! (Press ctrl + c to end)\n");
				} catch (Exception e) {
					System.out.println("Encountered an error! Error details: " + e.getMessage());
					e.printStackTrace();
				}
			}
		} else {
			System.out.println("Connection is null!");
		}
	}
}
