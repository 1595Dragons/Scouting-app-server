package javacode;

import javacode.Core.Debugger;
import javacode.FileManager.Config;
import javacode.UI.MainPanel;
import javacode.UI.ScoutingAppGUI;
import javacode.Wireless.Bluetooth;
import javacode.Wireless.HandleIncommingDevices;
import javafx.scene.control.Label;

import javax.bluetooth.UUID;
import javax.microedition.io.StreamConnectionNotifier;
import java.io.IOException;

public class ScoutingApp {

	public static boolean debug;

	public static Config config = new Config();

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

		// TODO Run the GUI stuff on a new thread
		new Thread(new ScoutingAppGUI()).run();

		// TODO Run the bluetooth stuff on a new thread

		if (Bluetooth.isEnabled()) {
			/*
			try {
				Bluetooth bluetooth = new Bluetooth();

				// Reset the device names
				for (Label deviceText : ScoutingApp.mainPanel.connectedDevices) {
					deviceText.setText("None");
				}
				Debugger.d(this.getClass(), "Finished resetting device names");

				mainPanel.setMacAddress(bluetooth.getMACAddress());
				Debugger.d(this.getClass(), "Set MAC address to: " + bluetooth.getMACAddress());

				// Create a UUID for SPP, and then create the URL
				UUID uuid = new UUID("1101", true);
				String connectionString = "btspp://localhost:" + uuid + ";name=DragonSPPServer";
				MainPanel.log("Connection URL: " + connectionString, false);
				Debugger.d(this.getClass(), "Connection URL: " + connectionString);

				try {
					final HandleIncommingDevices handler = new HandleIncommingDevices((StreamConnectionNotifier) javax.microedition.io.Connector
							.open(connectionString));

					Debugger.d(this.getClass(), "Successfully created stream connection notifier");

					primaryWindow.setOnCloseRequest((event) -> {
						// TODO Disconnect all devices
						handler.close();
						System.exit(0);
					});

					MainPanel.log("Listening for connected devices", false);
					Debugger.d(this.getClass(), "Foo");

					handler.run();
				} catch (IOException e) {
					MainPanel.logError(e);
				}

			} catch (javax.bluetooth.BluetoothStateException e) {
				MainPanel.logError(e);
			}
			*/
		} else {
			MainPanel.log("Bluetooth not enabled", true);
		}

	}

}
