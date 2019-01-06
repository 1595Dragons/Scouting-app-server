package javacode;

import java.io.IOException;
import java.sql.SQLException;

import javax.bluetooth.UUID;
import javax.microedition.io.StreamConnectionNotifier;

import javacode.Core.Bluetooth;
import javacode.Core.Debugger;
import javacode.Core.DeviceManagement;
import javacode.FileManager.Config;
import javacode.FileManager.Database;
import javacode.UI.MainPanel;

public class ScoutingApp extends javafx.application.Application {

	public static boolean debug;
	public static StreamConnectionNotifier streamConnNotifier = null;
	public static boolean stopRequested = false;

	public static Config config = new Config();

	@Override
	public void start(javafx.stage.Stage primaryWindow) {

		// Setup the main panel for the user
		MainPanel mainPanel = new MainPanel();

		primaryWindow.setScene(mainPanel.loadMainPanel());
		primaryWindow.setTitle("1595 Scouting app");
		primaryWindow.setOnCloseRequest((event) -> stopRequested = true);
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

		// Check if bluetooth is possible
		Bluetooth bluetooth = new Bluetooth();

		if (bluetooth.isEnabled()) {

			DeviceManagement devices = new DeviceManagement();

			devices.reset();
			Debugger.d(getClass(), "Finsihed resetting device names");

			try {
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

				devices.new HandleIncommingDevices().start();

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
