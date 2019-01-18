package javacode;

import javacode.Core.Debugger;
import javacode.FileManager.Config;
import javacode.FileManager.Database;
import javacode.UI.MainPanel;
import javacode.Wireless.Bluetooth;
import javacode.Wireless.HandleIncommingDevices;
import javafx.scene.control.Label;

import javax.bluetooth.UUID;
import javax.microedition.io.StreamConnectionNotifier;
import java.io.IOException;
import java.sql.SQLException;

public class ScoutingApp extends javafx.application.Application {

	public static boolean debug;
	public static StreamConnectionNotifier streamConnNotifier = null;

	public static Config config = new Config();

	public void start(javafx.stage.Stage primaryWindow) {

		// Setup the main panel for the user
		MainPanel mainPanel = new MainPanel();

		primaryWindow.setScene(mainPanel.loadMainPanel());
		primaryWindow.setTitle("1595 Scouting app");
		primaryWindow.show();

		// Check for valid config
		try {
			config.validateConfig();
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

				mainPanel.setMacAddress(bluetooth.getMACAddress());

				// Create a UUID for SPP, and then create the URL
				UUID uuid = new UUID("1101", true);
				String connectionString = "btspp://localhost:" + uuid + ";name=DragonSPPServer";
				MainPanel.log("Connection URL: " + connectionString, false);

				try {
					streamConnNotifier = (StreamConnectionNotifier) javax.microedition.io.Connector
							.open(connectionString);
				} catch (IOException e) {
					MainPanel.logError(e);
				}
				
				HandleIncommingDevices wirelessHandler = new HandleIncommingDevices();

				primaryWindow.setOnCloseRequest((event) -> {
					wirelessHandler.stop();
					System.exit(0);
				});

				wirelessHandler.run();
				

			} catch (javax.bluetooth.BluetoothStateException e) {
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
