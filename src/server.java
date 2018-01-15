import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import javax.bluetooth.RemoteDevice;
import javax.bluetooth.UUID;
import javax.microedition.io.Connector;
import javax.microedition.io.StreamConnection;
import javax.microedition.io.StreamConnectionNotifier;

public class server {
	public void startServer(boolean debug) throws IOException {
		selectFile file = new selectFile();

		System.out.println("Server started!\n");
		while (true) {
			System.out.println("\nReady to recieve more data! (Press ctrl + c to end)\n");
			// Create a UUID for SPP
			UUID uuid = new UUID("1101", true);
			// UUID uuid = new UUID("00001101-0000-1000-8000-00805F9B34FB", false);
			// Create the servicve url
			String connectionString = "btspp://localhost:" + uuid + ";name=SpudSPPServer";
			if (debug) {
				System.out.println("Connection URL: " + connectionString);
			}
			// open server url
			StreamConnectionNotifier streamConnNotifier = (StreamConnectionNotifier) Connector.open(connectionString);

			// Wait for client connection
			// System.out.println("Awaiting data...");
			StreamConnection connection = streamConnNotifier.acceptAndOpen();

			RemoteDevice dev = RemoteDevice.getRemoteDevice(connection);
			System.out.println("Remote device address: " + dev.getBluetoothAddress().replaceAll("..(?!$)", "$0:").toUpperCase());
			System.out.println("Remote device name: " + dev.getFriendlyName(true));

			// read string from spp client
			InputStream inStream = connection.openInputStream();
			BufferedReader bReader = new BufferedReader(new InputStreamReader(inStream));
			String lineRead = bReader.readLine();
			BufferedWriter writer= new BufferedWriter(new FileWriter(file.file.getAbsolutePath(), true));
			writer.newLine();
			writer.append(lineRead);
			writer.flush();
			writer.close();
			System.out.println("Recieved info on team: " + lineRead.split(",")[0]);
			bReader.close();
			inStream.close();

			// send response to spp client
			OutputStream outStream = connection.openOutputStream();
			PrintWriter pWriter = new PrintWriter(new OutputStreamWriter(outStream));
			pWriter.write("Data received!");
			pWriter.flush();
			pWriter.close();
			outStream.close();

			// Close URL
			streamConnNotifier.close();
			
		}

	}
}
