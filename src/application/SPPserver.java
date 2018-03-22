package application;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.bluetooth.RemoteDevice;
import javax.microedition.io.StreamConnection;

public class SPPserver extends Thread {
	StreamConnection connection = ScoutingApp.currentConnection;

	// Wait for client connection
	public void run() {
		if (connection != null) {
			synchronized (connection) {
				
				Info.log("Phone connected", false);
				
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
						Info.log("Device disconnected", false);
						Info.deviceDisconnect(RemoteDevice.getRemoteDevice(connection).getFriendlyName(false));
						return;
					}
				} catch (IOException e1) {
					Info.log(String.format("Error, cannot read stream: %s", e1.getMessage()), true);
					e1.printStackTrace();
				}
				//System.out.println(lineRead);
				if (lineRead.startsWith("pit")) {
					lineRead = lineRead.replace("pit,", "");
					Info.writeToFilePitScouting(lineRead);
				} else {
					Info.writeToFileStandScouting(lineRead);
				}

				// Try closing the writer and stream
				
				try {
					//fw.close();
					Info.deviceDisconnect(RemoteDevice.getRemoteDevice(connection).getFriendlyName(false));
					bReader.close();
					inStream.close();
				} catch (IOException e1) {
					Info.log(String.format("Error, cannot close stream: %s", e1.getMessage()), true);
					e1.printStackTrace();
				}

				// Success!
				
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
