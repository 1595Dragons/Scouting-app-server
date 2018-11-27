package javacode;

import java.io.IOException;

import javax.bluetooth.UUID;
import javax.microedition.io.Connector;
import javax.microedition.io.StreamConnection;
import javax.microedition.io.StreamConnectionNotifier;

import javacode.Core.Updater;

@Deprecated
public class OldScoutingApp {

	public static boolean debug = false;

	public static StreamConnection currentConnection;

	public static StreamConnectionNotifier streamConnNotifier;

	public static void main(String args[]) {
		try {
			debug = Boolean.parseBoolean(args[0]);
		} catch (ArrayIndexOutOfBoundsException noArgs) {
			debug = false;
		}

		if (Updater.updateAvalible()) {
			Updater.showUpdatePrompt();
		}

		// Create a UUID for SPP, and then create the URL
		UUID uuid = new UUID("1101", true);
		String connectionString = "btspp://localhost:" + uuid + ";name=SpudSPPServer";

		// open server URL using the created URL
		streamConnNotifier = null;
		try {
			streamConnNotifier = (StreamConnectionNotifier) Connector.open(connectionString);
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		while (true) {
			// Check if receiving a connection
			// On connection, open it, and pass it to a newly created thread
			try {
				currentConnection = streamConnNotifier.acceptAndOpen();
				if (currentConnection != null) {
					new Thread(new SspServer()).start();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
