import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
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
				System.out.print("Device connected! Retreiving data...");

				// read string from spp client
				InputStream inStream = null;
				try {
					inStream = connection.openInputStream();
				} catch (IOException e1) {
					System.out.println("Error, cannot open stream: " + e1.getMessage());
					e1.printStackTrace();
				}
				BufferedReader bReader = new BufferedReader(new InputStreamReader(inStream));
				String lineRead = null;
				try {
					lineRead = bReader.readLine();
					if (lineRead == null) {
						throw new java.lang.Error("Team data incomplete, missing number!");
					}
				} catch (IOException e1) {
					System.out.println("Error, cannot read stream: " + e1.getMessage());
					e1.printStackTrace();
				}
				System.out.print("writing to file...");
				FileWriter fw = null;
				try {
					fw = new FileWriter(file.file.getAbsolutePath(), true);
				} catch (IOException e1) {
					System.out.println("Error, cannot edit file: " + e1.getMessage());
					e1.printStackTrace();
				}
				try {
					fw.append(System.getProperty("line.separator")+lineRead);
					fw.flush();
				} catch (IOException e1) {
					System.out.println("Error, cannot write to file: " + e1.getMessage());
					e1.printStackTrace();
				}

				System.out.print("finishing data for team: " + lineRead.split(",")[0] + "\n");
				try {
					fw.close();
					bReader.close();
					inStream.close();
				} catch (IOException e1) {
					System.out.println("Error, cannot close streams: " + e1.getMessage());
					e1.printStackTrace();
				}
				System.out.println("Data written successfully!");
				try {
					connection.close();
					System.out.println("\nReady to recieve more data! (Press ctrl + c to end)\n");
				} catch (IOException e) {
					System.out.println("Error closing connection: " + e.getMessage());
					e.printStackTrace();
				}

			}
		} else {
			System.out.println("Connection is null!");
		}
	}
}
