package javacode;

import java.io.IOException;
import java.sql.SQLException;

import javax.bluetooth.BluetoothStateException;
import javax.bluetooth.UUID;
import javax.microedition.io.Connector;
import javax.microedition.io.StreamConnectionNotifier;

import javacode.Core.Bluetooth;
import javacode.Core.Debugger;
import javacode.Core.DeviceManagement;
import javacode.FileManager.Config;
import javacode.FileManager.Database;
import javacode.UI.MainPanel;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class ScoutingApp extends Application {

	public static boolean debug;
	public static StreamConnectionNotifier streamConnNotifier = null;
	public static boolean stopRequested = false;

	@Override
	public void start(Stage primaryWindow) {

		// Setup the main panel for the user
		MainPanel mainPanel = new MainPanel();

		try {
			primaryWindow.setScene(mainPanel.loadMainPanel());
			primaryWindow.setTitle("1595 Scouting app");
			primaryWindow.setOnCloseRequest(new EventHandler<WindowEvent>() {
				@Override
				public void handle(WindowEvent event) {
					stopRequested = true;
				}
			});
			primaryWindow.show();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Check for valid config
		try {
			new Config().validateConfig();
		} catch (IOException e1) {
			// If config validation fails, just return
			MainPanel.logError(e1);
			return;
		}

		new DeviceManagement().reset();
		Debugger.d(getClass(), "Finsihed resetting device names");

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
			}
		}

		// Check if bluetooth is possible
		Bluetooth bluetooth = new Bluetooth();

		if (bluetooth.isEnabled()) {
			try {
				mainPanel.setMacAddress(bluetooth.getMACAddress());

				// Create a UUID for SPP, and then create the URL
				UUID uuid = new UUID("1101", true);
				String connectionString = "btspp://localhost:" + uuid + ";name=DragonSPPServer";
				mainPanel.log("Connection URL: " + connectionString, false);

				try {
					streamConnNotifier = (StreamConnectionNotifier) Connector.open(connectionString);
				} catch (IOException e) {
					MainPanel.logError(e);
				}

				new DeviceManagement().new HandleIncommingDevices().start();

			} catch (BluetoothStateException e) {
				MainPanel.logError(e);
			}
		} else {
			mainPanel.log("Bluetooth not enabled", true);
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
