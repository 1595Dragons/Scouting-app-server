package javacode.Wireless;

import javacode.UI.MainPanel;
import javafx.application.Platform;

import javax.microedition.io.StreamConnectionNotifier;
import java.io.IOException;

@Deprecated
public class HandleIncommingDevices implements Runnable {

	private final StreamConnectionNotifier notifier;

	public HandleIncommingDevices(StreamConnectionNotifier streamConnectionNotifier) {
		this.notifier = streamConnectionNotifier;
	}

	public void run() {
		synchronized (notifier) {
			while (true) {

				// Check if receiving a connection
				// On connection, open it, and pass it to a newly created thread
				javax.microedition.io.StreamConnection connection;
				try {
					connection = notifier.acceptAndOpen();
					/*
					if (connection != null) {
						// Start the SPP server for that device
						new BlueThread(connection).run();
					}
					*/
				} catch (java.io.IOException e) {
					Platform.runLater(() -> MainPanel.logError(e));
				}
			}
		}
	}

	public void close() {
		try {
			this.notifier.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}