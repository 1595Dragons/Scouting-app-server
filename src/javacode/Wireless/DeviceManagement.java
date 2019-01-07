package javacode.Wireless;

import javacode.Core.Debugger;
import javacode.UI.MainPanel;
import javafx.scene.control.Label;

public class DeviceManagement {
	
	public static void deviceConnected(String deviceName) {
		Debugger.d(DeviceManagement.class, "Device connected: " + deviceName);
		for (Label deviceText : MainPanel.connectedDevices) {
			if (deviceText.getText().equals("None")) {
				deviceText.setText(deviceName);
				break;
			}
		}
	}

	public static void deviceDisconnected(String deviceName) {
		Debugger.d(DeviceManagement.class, "Device disconnected: " + deviceName);
		for (Label deviceText : MainPanel.connectedDevices) {
			if (deviceText.getText().equals(deviceName)) {
				deviceText.setText("None");
				break;
			}
		}
	}

	public static void reset() {
		for (Label deviceText : MainPanel.connectedDevices) {
			deviceText.setText("None");
		}
	}
}