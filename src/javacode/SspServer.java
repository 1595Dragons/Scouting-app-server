package javacode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.microedition.io.StreamConnection;

public class SspServer extends Thread {
	StreamConnection connection = OldScoutingApp.currentConnection;

	// Wait for client connection
	public void run() {
		if (connection != null) {
			synchronized (connection) {

				InputStream inStream = null;
				try {
					inStream = connection.openInputStream();
				} catch (IOException e1) {

					e1.printStackTrace();
				}

				// Read the data from the stream
				BufferedReader bReader = new BufferedReader(new InputStreamReader(inStream));
				String lineRead = null;
				try {
					lineRead = bReader.readLine();
					if (lineRead == null) {
						return;
					}
				} catch (IOException e1) {
					e1.printStackTrace();
				}

				ScoutingFile.writeToFile(lineRead);

				// Try closing the writer and stream
				try {
					bReader.close();
					inStream.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}

				// Success!
				try {
					connection.close();
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		}
	}
}