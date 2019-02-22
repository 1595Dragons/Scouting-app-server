package javacode;

import javacode.Core.Debugger;
import javacode.FileManager.Config;
import javacode.FileManager.Database;
import javacode.UI.MainPanel;
import javacode.Wireless.BlueThread;
import javacode.Wireless.Bluetooth;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import javax.bluetooth.BluetoothStateException;
import javax.bluetooth.UUID;
import javax.microedition.io.StreamConnectionNotifier;
import java.io.IOException;
import java.sql.SQLException;

public class ScoutingApp extends javafx.application.Application {

	public static boolean debug;

	public static Config config = new Config();

	public static MainPanel mainPanel = new MainPanel();

	public void start(Stage primaryWindow) {

		primaryWindow.setScene(new MainPanel().loadMainPanel());
		primaryWindow.setTitle("1595 Scouting app");
		primaryWindow.show();

		// Check for valid config
		try {
			ScoutingApp.config.validateConfig();
		} catch (IOException e) {
			// If config validation fails, just return
			MainPanel.logError(e);
			return;
		}

		// Check if the database exists
		Database database = new Database();
		boolean databaseExists = database.databaseExists(true);
		Debugger.d(getClass(), "Database exists: " + databaseExists);

		// If no database exists, create one
		if (!databaseExists) {
			try {
				database.createDatabase();
			} catch (IOException | SQLException e) {
				MainPanel.logError(e);
				return;
			}
		}

		// Check if the database is valid
		if (!database.validateDatabase()) {
			MainPanel.log("Database is invalid, creating new database", true);
			try {
				database.createDatabase();
			} catch (IOException | SQLException e) {
				MainPanel.logError(e);
				return;
			}
		}

		if (Bluetooth.isEnabled()) {
			try {
				Bluetooth bluetooth = new Bluetooth();

				// Reset the device names
				for (Label deviceText : MainPanel.connectedDevices) {
					deviceText.setText("None");
				}
				Debugger.d(this.getClass(), "Finished resetting device names");

				ScoutingApp.mainPanel.setMacAddress(bluetooth.getMACAddress());
				Debugger.d(this.getClass(), "Set MAC address to: " + bluetooth.getMACAddress());

				// Create a UUID for SPP, and then create the URL
				UUID uuid = new UUID("1101", true);
				String connectionString = "btspp://localhost:" + uuid + ";name=DragonSPPServer";
				MainPanel.log("Connection URL: " + connectionString, false);
				Debugger.d(this.getClass(), "Connection URL: " + connectionString);

				try {
					final StreamConnectionNotifier notifier = (StreamConnectionNotifier) javax.microedition.io.Connector
							.open(connectionString);
					new Thread(() -> {
						Debugger.d(this.getClass(), "Successfully created stream connection notifier");
						Platform.runLater(() -> MainPanel.log("Listening for connected devices", false));
						synchronized (notifier) {
							while (true) {
								// Check if receiving a connection
								// On connection, open it, and pass it to a newly created thread
								javax.microedition.io.StreamConnection connection;
								try {
									connection = notifier.acceptAndOpen();
									if (connection != null) {
										// Start the SPP server for that device
										new BlueThread(connection).run();
									}

								} catch (java.io.IOException e) {
									Platform.runLater(() -> MainPanel.logError(e));
								}
							}
						}
					}).start();

					primaryWindow.setOnCloseRequest((event) -> {
						// TODO Disconnect all devices
						System.exit(0);
					});


				} catch (IOException e) {
					MainPanel.logError(e);
				}

			} catch (BluetoothStateException e) {
				MainPanel.logError(e);
			}

		} else {
			MainPanel.log("Bluetooth not enabled", true);
		}

	}

	public static void main(String[] args) {

		// Check if the debugger is enabled
		try {
			for (String arg : args) {
				if (Boolean.parseBoolean(arg)) {
					debug = true;
					break;
				} else {
					debug = false;
				}
			}
		} catch (Exception e) {
			debug = false;
		}

		Debugger.d(ScoutingApp.class, "Debugging enabled: " + debug);

		launch(args);
	}
}
