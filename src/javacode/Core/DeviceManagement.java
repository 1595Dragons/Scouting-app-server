package javacode.Core;

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
}
