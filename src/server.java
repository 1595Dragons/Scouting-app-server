import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.bluetooth.UUID;
import javax.microedition.io.Connector;
import javax.microedition.io.StreamConnection;
import javax.microedition.io.StreamConnectionNotifier;

public class server {
	public void startServer(boolean debug) throws IOException {
		selectFile file = new selectFile();

		System.out.println("Server started!\n");

		boolean running = true;

		// Create a UUID for SPP, and then create the URL
		UUID uuid = new UUID("1101", true);
		String connectionString = "btspp://localhost:" + uuid + ";name=SpudSPPServer";
		if (debug) {
			System.out.println("Connection URL: " + connectionString);
		}
		// open server url
		StreamConnectionNotifier streamConnNotifier = (StreamConnectionNotifier) Connector.open(connectionString);

		while (running) {
			// Wait for client connection
			try {
				System.out.println("\nReady to recieve more data! (Press ctrl + c to end)\n");
				StreamConnection connection = streamConnNotifier.acceptAndOpen();

				System.out.println("Device connected! Retreiving data...");

				// read string from spp client
				InputStream inStream = connection.openInputStream();
				BufferedReader bReader = new BufferedReader(new InputStreamReader(inStream));
				String lineRead = bReader.readLine();
				System.out.println("Recieved info on team: " + lineRead.split(",")[0] + "\nWriting to file...");
				BufferedWriter writer = new BufferedWriter(new FileWriter(file.file.getAbsolutePath(), true));
				writer.newLine();
				writer.append(lineRead);
				writer.flush();
				writer.close();
				System.out.println("Data written successfully!");
				bReader.close();
				inStream.close();

				// Close connection
				connection.close();
			} catch (Exception e) {
				System.out.println("Encountered an error, shutting down. Error details:\n");
				e.printStackTrace();
				running = false;
			}

		}
		streamConnNotifier.close();

	}
}
