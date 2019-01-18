package javacode.Wireless;

import javacode.ScoutingApp;
import javacode.UI.MainPanel;
import javafx.application.Platform;

public class HandleIncommingDevices implements Runnable {

	private volatile boolean stopRequested = false;

	public void run() {
		Platform.runLater(() -> MainPanel.log("Listening for connected devices", false));
		while (!this.stopRequested) {
			// Check if receiving a connection
			// On connection, open it, and pass it to a newly created thread
			javax.microedition.io.StreamConnection connection;
			try {
				connection = ScoutingApp.streamConnNotifier.acceptAndOpen();
				if (connection != null) {
					// Start the SPP server for that device
					new BlueThread(connection).run();
				}
			} catch (java.io.IOException e) {
				Platform.runLater(() -> MainPanel.logError(e));
			}
		}
	}

	public void stop() {
		this.stopRequested = true;
	}
}