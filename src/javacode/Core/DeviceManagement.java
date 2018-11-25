package javacode.Core;

import java.io.IOException;

import javax.bluetooth.RemoteDevice;
import javax.microedition.io.StreamConnection;

import javacode.ScoutingApp;
import javacode.UI.MainPanel;
import javafx.scene.control.Label;

public class DeviceManagement {

	public void deviceConnected(String deviceName) {
		Debugger.d(getClass(), "Device connected: " + deviceName);
		for (Label deviceText : MainPanel.connectedDevices) {
			if (deviceText.getText().equals("None")) {
				deviceText.setText(deviceName);
				break;
			}
		}
	}

	public void deviceDisconnected(String deviceName) {
		Debugger.d(getClass(), "Device disconnected: " + deviceName);
		for (Label deviceText : MainPanel.connectedDevices) {
			if (deviceText.getText().equals(deviceName)) {
				deviceText.setText("None");
				break;
			}
		}
	}

	public void reset() {
		for (Label deviceText : MainPanel.connectedDevices) {
			deviceText.setText("None");
		}
	}

	// TODO: Just merge this with the SSPServer class
	public class HandleIncommingDevices extends Thread {
		public void run() {
			while (!ScoutingApp.stopRequested) {
				// Check if receiving a connection
				// On connection, open it, and pass it to a newly created thread
				StreamConnection connection = null;
				try {
					connection = ScoutingApp.streamConnNotifier.acceptAndOpen();
					if (connection != null) {
						// Start the SPP server for that device
						new DeviceManagement()
								.deviceConnected(RemoteDevice.getRemoteDevice(connection).getFriendlyName(false));
						new Thread(new Bluetooth().new SSPServer(connection)).start();
					}
				} catch (IOException e) {
					new MainPanel().log(e.getMessage() + "\n" + e.getStackTrace(), true);
				}
			}
		}

	}
}