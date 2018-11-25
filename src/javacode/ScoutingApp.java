package javacode;

import java.io.IOException;

import javax.bluetooth.BluetoothStateException;
import javax.bluetooth.UUID;
import javax.microedition.io.Connector;
import javax.microedition.io.StreamConnectionNotifier;

import javacode.Core.Bluetooth;
import javacode.Core.Debugger;
import javacode.Core.DeviceManagement;
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

		MainPanel mainPanel = new MainPanel();

		try {
			primaryWindow.setScene(mainPanel.loadMainPanel());
			primaryWindow.setTitle("1595 Scouting app");
			primaryWindow.setResizable(false);
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

		mainPanel.log("Loaded successfully", false);

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
					mainPanel.log(e.getMessage() + "\n" + e.getStackTrace(), true);
				}

				new DeviceManagement().new HandleIncommingDevices().start();

			} catch (BluetoothStateException e) {
				mainPanel.log(e.getMessage() + "\n" + e.getStackTrace(), true);
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
