import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.bluetooth.RemoteDevice;
import javax.bluetooth.UUID;
import javax.microedition.io.Connector;
import javax.microedition.io.StreamConnection;
import javax.microedition.io.StreamConnectionNotifier;

public class server {
	// TODO: Update this to use the window, instead of the console
	public void startServer(boolean debug) throws IOException {

		// Create a UUID for SPP
		UUID uuid = new UUID("1101", true);
		//UUID uuid = new UUID("00001101-0000-1000-8000-00805F9B34FB", false);
		// Create the servicve url
		String connectionString = "btspp://localhost:" + uuid + ";name=SpudSPPServer";
		if (debug) {
			System.out.println("Connection URL: " + connectionString);
		}
		// open server url
		StreamConnectionNotifier streamConnNotifier = (StreamConnectionNotifier) Connector.open(connectionString);

		// Wait for client connection
		System.out.println("Awaiting data...");
		StreamConnection connection = streamConnNotifier.acceptAndOpen();

		RemoteDevice dev = RemoteDevice.getRemoteDevice(connection);
		System.out.println("Remote device address: " + dev.getBluetoothAddress());
		System.out.println("Remote device name: " + dev.getFriendlyName(true));

		// read string from spp client
		InputStream inStream = connection.openInputStream();
		BufferedReader bReader = new BufferedReader(new InputStreamReader(inStream));
		String lineRead = bReader.readLine();
		System.out.println(lineRead);

		// send response to spp client
		/*
		OutputStream outStream = connection.openOutputStream();
		PrintWriter pWriter = new PrintWriter(new OutputStreamWriter(outStream));
		pWriter.write("Response String from SPP Server\r\n");
		pWriter.flush();
		pWriter.close();
		*/

		
		// Close URL
		streamConnNotifier.close();

	}
}
